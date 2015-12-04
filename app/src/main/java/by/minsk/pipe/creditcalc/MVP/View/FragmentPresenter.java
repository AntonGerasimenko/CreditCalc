package by.minsk.pipe.creditcalc.MVP.View;

import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 04.12.2015.
 */
public interface FragmentPresenter {

    void openCreditsList();
    void openMakeCredit() ;
    void openCalcCredit(Credit credit);
    void openAddPay();
}
