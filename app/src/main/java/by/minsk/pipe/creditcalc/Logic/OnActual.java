package by.minsk.pipe.creditcalc.Logic;

import java.util.List;

import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public interface OnActual {

    List<Pay> getAllPays();
    Pay getPay();
    void getRate(OnRateListener listener, Currency currency);
}
