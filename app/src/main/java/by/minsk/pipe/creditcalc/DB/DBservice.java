package by.minsk.pipe.creditcalc.DB;

import by.minsk.pipe.creditcalc.DB.Operation.CreditOperation;
import by.minsk.pipe.creditcalc.DB.Operation.PayOperation;
import by.minsk.pipe.creditcalc.DB.Operation.RateOperation;

/**
 * Created by gerasimenko on 26.08.2015.
 */
public class DBservice {

    private static final PayOperation payOperation = new PayOperation();
    private static final CreditOperation creditOperation = new CreditOperation();
    private static final RateOperation rateOperation = new RateOperation();

    public static PayOperation pay() {
        return payOperation;
    }

    public static CreditOperation credit() {
        return creditOperation;
    }

    public static RateOperation rate() {
        return rateOperation;
    }
}
