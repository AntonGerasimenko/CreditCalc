package by.minsk.pipe.creditcalc.Logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooLarge;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Pay;
import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public class Payment {

    public final static int DAYS_IN_YEAR = 365;
    public final static int DAYS_IN_MONTH = 30;
    private double minPay;
    private Actual actual;


    private Credit credit;
    private Currency mainCurrency;
    private Currency location;

    public Payment(Actual actual, Credit credit) {
        this.actual = actual;
        this.credit = credit;

        mainCurrency = Currency.getInstance(credit.getCurrency());
        location = Currency.getInstance(credit.getLocation());
    }

    public void make(double sum, Pay lastPay,final ResultAddPay result) throws IsTooSmall, IsTooLarge {

        if (sum == 0) throw new IsTooSmall("Payment is 0");


        double overpayment = sum - minPay;
        if (sum > lastPay.getBalance()) throw new IsTooLarge();
        if (overpayment > -1) {

            final Pay newPay = new Pay();

            if (sum>lastPay.getBalance()) {
                newPay.setBalance(0);
            } else {
                newPay.setBalance(lastPay.getBalance() - sum);
            }
            newPay.setPay(sum);
            newPay.setOverpayment(overpayment);
            newPay.setDate(actual.getNowDate());
            newPay.setCredit(lastPay.getCredit());

            Credit credit = lastPay.getCredit();

            actual.getRate(new OnRateListener() {
                @Override
                public void getRate(Rate rate) {
                    newPay.setRate(rate);
                    DBservice.pay().put(newPay);
                    result.result();
                }
            }, Currency.getInstance(credit.getCurrency()));


        } else throw new IsTooSmall("Payment is too small");
    }

    public double getMinPayment(Pay pay) {

        long lastPayTime = pay.getDate();
        if (lastPayTime == 0) {
            lastPayTime = pay.getCredit().getStartData();
        }
        if (pay.getBalance() == 0) pay.setBalance(pay.getCredit().getSumma());
        int lastPayDays = (int) periodDays(lastPayTime);

        minPay = interestPayment(pay,lastPayDays) + debtPayment(pay,lastPayDays);
        return minPay;
    }

    public  List<Pay> calculateAllCredit() {

        final Calendar calendar = Calendar.getInstance();
        long size = (-1)*periodDays(credit.getEndData());

        final double summa = credit.getSumma();
        final int creditMonth = (int) (size/DAYS_IN_MONTH);
        final double deptPay = summa/creditMonth;
        final double percent = credit.getInterestRate();

        final List<Pay> pays = new ArrayList<>();
        final Pay pay1 = new Pay();
        pay1.setDate(calendar.getTimeInMillis());
        pay1.setDeptPay(0);
        pay1.setInterestPay(0);
        pay1.setBalance(summa);
        pay1.setCredit(credit);

        actual.getRate(new OnRateListener() {
            @Override
            public void getRate(Rate rate) {
                pay1.setRate(rate);
                pays.add(pay1);
               // recalcPay(pay1);
            }
        },Currency.getInstance(credit.getLocation()));

        for (int i=0;i<creditMonth;i++){
            Pay pay = new Pay();

            calendar.add(Calendar.DATE, DAYS_IN_MONTH);
            pay.setDate(calendar.getTimeInMillis());

            pay.setDeptPay(deptPay);
            double balance;
            if (pays.isEmpty()) {
                balance = summa;
            } else {
                int j = pays.size()-1;
                balance = pays.get(j).getBalance();
            }
            double interestPay = calculateInterest(balance,percent,DAYS_IN_MONTH);

            pay.setInterestPay(interestPay);
            pay.setPay(deptPay + interestPay);

            balance = balance - deptPay;
            pay.setBalance(balance);
            pay.setCredit(credit);
            pay.setRate(pay1.getRate());
           // recalcPay(pay);
            pays.add(pay);
        }

        for (Pay pay:pays) recalcPay(pay);

        return pays;
    }

    private double calculateInterest(double summa, double percent, int period){

        return  summa/100*percent/period;
    }

    private double interestPayment(Pay pay,int days) {

        double balance = pay.getBalance();
        double rate = checkRate(pay.getCredit());

        return balance/100*rate/DAYS_IN_YEAR*days;
    }

    private double debtPayment(Pay pay, int days) {

        Credit credit = pay.getCredit();

        double balance = pay.getBalance();
        int daysBeforeClosure = (int) periodDays(credit.getEndData());
        if (daysBeforeClosure<0)daysBeforeClosure *= -1;

        return balance/daysBeforeClosure*days;
    }

    private double checkRate(Credit credit) {
        if (credit.isUseVat()) {
            Rate rate = actual.getRateInDB();
            return rate.getVat() + credit.getInterestRate();
        } else {
            return credit.getInterestRate();
        }
    }

    private long periodDays (long end) {

        long now = new Date().getTime();
        long diffTime = now - end;

        return diffTime / (1000 * 60 * 60 * 24);
    }

    public interface ResultAddPay {

        void result();
    }

    private void recalcPay(Pay pay) {
        switch (location) {
            case BYR:
            case USD:
            case BTK:
            case EU:
            case RUR:
            case UA:
                Rate rate = pay.getRate();
                double exRate = rate.getExchangeRate(mainCurrency);

                double balance  = (pay.getBalance()*exRate);
                double paySum = pay.getPay() *exRate;
                double overpayment = pay.getOverpayment()*exRate;
                double interestRate = pay.getInterestPay()*exRate;

                pay.setBalance(balance);

                pay.setPay(paySum);
                pay.setOverpayment(overpayment);
                pay.setInterestPay(interestRate);
                break;
        }
    }
}

//todo переплата окончательного кредита рассмотреть при получении записей с БД.