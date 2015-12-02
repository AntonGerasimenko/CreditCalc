package by.minsk.pipe.creditcalc.MVP.Presenter;

import java.util.List;

import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.MVP.View.ListPaysView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Pay;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class AllPaysPresenter extends BasePresenter {

    private ListPaysView view;
    private List<Pay> pays;
    private Credit credit;

    public void showPays(Credit credit){

        this.credit = credit;

        Payment payment = new Payment(new Actual(),credit);
        pays = payment.calculateAllCredit();
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
