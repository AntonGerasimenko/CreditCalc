package by.minsk.pipe.creditcalc.Networks;

import java.util.Date;

import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 25.09.2015.
 */
public interface Connect {

    public Rate getRate(Date date);
}
