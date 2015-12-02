package by.minsk.pipe.creditcalc.MVP.View;

import java.util.List;

import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Pay;
import by.minsk.pipe.creditcalc.MVP.models.Total;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public interface ListPaysView {

    void showPays(List<Pay> pays, Currency currency);

    void recalcCurrency(Total total);
}
