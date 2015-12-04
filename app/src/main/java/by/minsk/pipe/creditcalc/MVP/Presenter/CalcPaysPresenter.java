package by.minsk.pipe.creditcalc.MVP.Presenter;

import java.util.List;

import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.MVP.View.ListPaysView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Pay;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class CalcPaysPresenter extends BasePresenter {

    private ListPaysView view;
    private List<Pay> pays;
    private Credit credit;

    public CalcPaysPresenter(ListPaysView view, Credit credit) {
        this.credit = credit;
        this.view = view;
    }

    @Override
    public void start() {

        calculatePays();

        view.showPays(pays,Currency.getInstance(credit.getCurrency()));
    }

    @Override
    public void stop() {

    }

    private void calculatePays(){

        Payment payment = new Payment(new Actual(),credit);
        pays = payment.calculateAllCredit();
    }
}
