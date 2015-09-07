package by.minsk.pipe.creditcalc.Logic;


import java.util.Date;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public class Payment {

    public final static int DAYS_IN_YEAR = 365;
    private Actual actual;

    public Payment(Actual actual) {
        this.actual = actual;
    }

    public void makePayment(double sum) throws IsTooSmall {

        Pay pay = actual.getPay();
        double overpayment = sum - getMinPayment(pay);

        if (overpayment > -1) {
            DBservice.pay().create(sum, overpayment);
        } else throw new IsTooSmall("Payment is too small");
    }

    public double getMinPayment(Pay pay) {

        long lastPayTime = pay.getDate();
        if (lastPayTime == 0) {
            lastPayTime = pay.getCredit().getStartData();
        }
        int lastPayDays = (int) periodDays(lastPayTime);

        return interestPayment(pay,lastPayDays) + debtPayment(pay,lastPayDays);
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
}

//todo переплата окончательного кредита рассмотреть при получении записей с БД.