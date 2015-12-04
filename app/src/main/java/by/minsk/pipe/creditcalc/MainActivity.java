package by.minsk.pipe.creditcalc;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;


import by.minsk.pipe.creditcalc.Fragments.ActualPaysList;
import by.minsk.pipe.creditcalc.Fragments.AllPaysList;


import by.minsk.pipe.creditcalc.Fragments.PieChartFr;
import by.minsk.pipe.creditcalc.MVP.Presenter.MainPresenter;
import by.minsk.pipe.creditcalc.MVP.View.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {

        presenter.start();
        super.onStart();
    }

    @Override
    protected void onStop() {

        presenter.stop();
        super.onStop();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

   /*     Fragment fragment = getSupportFragmentManager().findFragmentByTag(ActualPaysList.TAG);
        if (fragment != null && fragment.isVisible()) {
         //   creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(MakeCredit.TAG);
        if (fragment!=null && fragment.isVisible()) {
          //  creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(PieChartFr.TAG);
        if (fragment!=null && fragment.isVisible()) {
           // creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(AllPaysList.TAG);
        if (fragment!=null && fragment.isVisible()) {

            super.onBackPressed();
            return;
        }*/

        super.onBackPressed();
    }

    @Override
    public void openFragment(Fragment fragment, String tag) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container,fragment,tag)
                .commit();
        Log.d("Fragment", "openFragment: "+ tag);
    }
}

/*@Override
    public void creditList() {
        Fragment fragment = new CreditList();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, CreditList.TAG).commit();
    }

    @Override
    public void makeCredit() {

        Fragment fragment = new MakeCredit();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, MakeCredit.TAG).commit();
    }

    @Override
    public void payList(Credit credit) {

        Fragment fragment = ActualPaysList.newInstance(credit);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, ActualPaysList.TAG).commit();
    }

    @Override
    public void makePay(int  idCredit) {
        Fragment fragment = MakePay.newInstance(new Actual(), idCredit);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, MakePay.TAG).commit();
    }

    @Override
    public void calcAllPays(Credit credit) {

        Fragment fragment = new AllPaysList();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, AllPaysList.TAG);
        ft.addToBackStack(AllPaysList.TAG);
        ft.commit();
    }
*/


