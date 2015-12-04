package by.minsk.pipe.creditcalc.MVP.View;

import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public interface MakeCreditView extends BaseView{

    void setCredit(Credit credit);

    void toast(String message);

    void setBeginData(String data);

    void setEndData(String data);

    String getBeginCreditData();
}
