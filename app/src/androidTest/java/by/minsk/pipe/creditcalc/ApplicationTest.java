package by.minsk.pipe.creditcalc;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.TestCase;
import junit.framework.TestResult;

import java.util.Calendar;
import java.util.List;

import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends /*ApplicationTestCase<Application>*/ TestCase {

    @Override
    public TestResult run() {
        return super.run();
    }

    public void testName() throws Exception {

    }

    @Override
    protected void setUp() throws Exception {


        Payment payment = new Payment(new Actual());
        List<Pay> pays = payment.calculateAllCredit(makeCredit(12000000,15,3));



        super.setUp();
    }

    private Credit makeCredit (double summa, double percent, int year){

        Credit credit = new Credit();

        credit.setSumma(summa);
        credit.setInterestRate(percent);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR,year);

        credit.setEndData(now.getTimeInMillis());

        return credit;
    }
}