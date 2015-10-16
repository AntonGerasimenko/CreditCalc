package by.minsk.pipe.creditcalc;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import by.minsk.pipe.creditcalc.Fragments.ActualPaysList;
import by.minsk.pipe.creditcalc.Fragments.AllPaysList;
import by.minsk.pipe.creditcalc.Fragments.CreditList;
import by.minsk.pipe.creditcalc.Fragments.FragmentListener;
import by.minsk.pipe.creditcalc.Fragments.MakeCredit;
import by.minsk.pipe.creditcalc.Fragments.MakePay;
import by.minsk.pipe.creditcalc.Fragments.PieChartFr;
import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.models.Credit;

public class MainActivity extends AppCompatActivity implements FragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if (savedInstanceState==null) makeCredit();//creditList();
    }


    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ActualPaysList.TAG);
        if (fragment != null && fragment.isVisible()) {
            creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(MakeCredit.TAG);
        if (fragment!=null && fragment.isVisible()) {
            creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(PieChartFr.TAG);
        if (fragment!=null && fragment.isVisible()) {
            creditList();
            return;
        }
        fragment = getSupportFragmentManager().findFragmentByTag(AllPaysList.TAG);
        if (fragment!=null && fragment.isVisible()) {

            super.onBackPressed();
            return;
        }

        super.onBackPressed();
    }

    @Override
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

        Fragment fragment = AllPaysList.newInstance(credit);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, AllPaysList.TAG);
        ft.addToBackStack(AllPaysList.TAG);
        ft.commit();
    }

    @Override
    public void showStatistic(Credit credit) {
        Fragment fragment = new AllPaysList();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, PieChartFr.TAG).commit();
    }
}


