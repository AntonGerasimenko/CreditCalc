package by.minsk.pipe.creditcalc.MVP.Presenter;

import android.app.Fragment;

import by.minsk.pipe.creditcalc.MVP.View.MainView;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class MainPresenter extends BasePresenter {


    private MainView mView;





    public MainPresenter(MainView mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

        Fragment fragment = new


        mView.openFragment();
    }

    @Override
    public void stop() {

    }
}
