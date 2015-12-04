package by.minsk.pipe.creditcalc.MVP.Presenter;

import android.app.Fragment;

import by.minsk.pipe.creditcalc.Fragments.CalcPays;
import by.minsk.pipe.creditcalc.Fragments.MakeCredit;
import by.minsk.pipe.creditcalc.MVP.View.FragmentPresenter;
import by.minsk.pipe.creditcalc.MVP.View.MainView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class MainPresenter extends BasePresenter implements FragmentPresenter {



    private MainView mView;

    public MainPresenter(MainView mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

        openMakeCredit();
    }

    @Override
    public void stop() {

    }

    @Override
    public void openCreditsList(){



    }
    @Override
    public void openMakeCredit() {

        Fragment fragment = MakeCredit.getInstance(this);
        mView.openFragment(fragment,MakeCredit.TAG);
    }
    @Override
    public  void openCalcCredit(Credit credit) {

        Fragment fragment = CalcPays.getInstance(credit);
        mView.openFragment(fragment,CalcPays.TAG);
    }

    @Override
    public void openAddPay() {

    }
}
