package by.minsk.pipe.creditcalc.MVP.Presenter;

import by.minsk.pipe.creditcalc.MVP.View.MakeCreditView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class MakeCreditPresenter extends BasePresenter {


    private MakeCreditView view;


    public MakeCreditPresenter(MakeCreditView view) {
        this.view = view;
    }




    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    public void addCredit(Credit credit) {


    }

    public void calcCredit(Credit credit) {



    }

    private boolean checkValidCredit(Credit credit) {

        return false;
    }
}
