package by.minsk.pipe.creditcalc.Logic;


import java.util.Date;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.models.LendingTerms;
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

            DBservice.createPayment(sum,overpayment);
        } else throw new IsTooSmall("Payment is too small");
    }

    public double getMinPayment() {

        return getMinPayment(actual.getPay());
    }

    private double getMinPayment(Pay pay) {



        int lastPayDays = (int) periodDays(pay.getDate());

        return interestPayment(pay,lastPayDays) + debtPayment(pay,lastPayDays);
    }

    private double interestPayment(Pay pay,int days) {

        double balance = pay.getBalance();
        double rate = checkRate(pay.getLendingTerms());

        return balance/100*rate/DAYS_IN_YEAR*days;
    }

    private double debtPayment(Pay pay, int days) {

        LendingTerms lendingTerms = pay.getLendingTerms();

        double balance = pay.getBalance();
        int daysBeforeClosure = (int) periodDays(lendingTerms.getEndLendingData());

        return balance/daysBeforeClosure*days;
    }

    private double checkRate(LendingTerms lendingTerms) {
        if (lendingTerms.isUseVat()) {
            Rate rate = actual.getRateInDB();
            return rate.getVat() + lendingTerms.getInterestRate();
        } else {
            return lendingTerms.getInterestRate();
        }
    }

    private long periodDays (long end) {

        long now = new Date().getTime();
        long diffTime = end - now;

        return diffTime / (1000 * 60 * 60 * 24);
    }
}

//todo переплата окончательного кредита рассмотреть при получении записей с БД.