package by.minsk.pipe.creditcalc.Logic;


import java.util.Date;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.models.LendigTerms;
import by.minsk.pipe.creditcalc.models.Pays;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public class Payment {

    public final static int DAYS_IN_YEAR = 365;
    private Actual actual = new Actual();

    public void makePayment(double sum) throws IsTooSmall {

        double overpayment = sum - getMinPayment();
        checkLoanRepayment();
        if (overpayment > -1) {

            DBservice.createPayment(sum,overpayment);
        } else throw new IsTooSmall("Payment is too small");
    }

    public double getMinPayment() {

        Pays pays = actual.getPay();

        int lastPayDays = (int) periodDays(pays.getDate());

        return interestPayment(pays,lastPayDays) + debtPayment(pays,lastPayDays);
    }

    private double interestPayment(Pays pays,int days) {

        double balance = pays.getBalance();
        double rate = checkRate(pays.getLendigTerms());

        return balance/100*rate/DAYS_IN_YEAR*days;
    }

    private double debtPayment(Pays pays, int days) {

        LendigTerms lendigTerms = pays.getLendigTerms();

        double balance = pays.getBalance();
        int daysBeforeClosure = (int) periodDays(lendigTerms.getEndLendingData());

        return balance/daysBeforeClosure*days;
    }

    private double checkRate(LendigTerms lendigTerms) {
        if (lendigTerms.isUseVat()) {
            Rate rate = actual.getRateInDB();
            return rate.getVat() + lendigTerms.getInterestRate();
        } else {
            return lendigTerms.getInterestRate();
        }
    }

    private long periodDays (long end) {

        long now = new Date().getTime();
        long diffTime = end - now;

        return diffTime / (1000 * 60 * 60 * 24);
    }
}

