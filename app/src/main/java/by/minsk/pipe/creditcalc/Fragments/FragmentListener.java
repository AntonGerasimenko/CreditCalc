package by.minsk.pipe.creditcalc.Fragments;

import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.models.Credit;

/**
 * Created by gerasimenko on 17.09.2015.
 */
public interface FragmentListener {

    void creditList();
    void makeCredit();

    void payList(Credit credit);
    void makePay(int creditId);

    void calcAllPays(Credit credit);

    void showStatistic(Credit credit);
}
