package by.minsk.pipe.creditcalc;

import junit.framework.TestCase;
import junit.framework.TestResult;

import java.util.Calendar;

import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Pay;

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