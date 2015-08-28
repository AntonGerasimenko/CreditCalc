package by.minsk.pipe.creditcalc.Logic;

import java.util.Date;
import java.util.List;

import by.minsk.pipe.creditcalc.models.Pays;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public interface OnActual {

    List<Pays> getAllPays();
    Pays getPay();
    void getRate(OnRateListener listener);


}
