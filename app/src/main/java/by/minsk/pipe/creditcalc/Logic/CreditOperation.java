package by.minsk.pipe.creditcalc.Logic;

import android.util.Log;

import java.util.Date;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.MakeNewCreditFault;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 01.09.2015.
 */
public class CreditOperation {

    private double interestRate;
    private Date startLendingDate;
    private Date endLendingDate;
    private double sum;
    private boolean useVat;

    public void make(double interestRate,double sum,boolean useVat, Date endLendingDate) throws MakeNewCreditFault {

        if (sum < 0) throw  new MakeNewCreditFault();
        if (interestRate<0) throw  new MakeNewCreditFault();

        Date now = new Date();

        Pay pay = new Pay();
        pay.newRecord(sum, 0, 0);
        by.minsk.pipe.creditcalc.models.Credit credit = new by.minsk.pipe.creditcalc.models.Credit();
        credit.setDate(now.getTime());
        credit.setStartData(now.getTime());
        credit.setEndData(endLendingDate.getTime());
        credit.setInterestRate(interestRate);

        pay.setCredit(credit);

        boolean result = DBservice.pay().put(pay);
        if (result) {
            Log.i("CreditCalc","make new credit");
        } else Log.i("CreditCalc","error new credit");
    }
}
