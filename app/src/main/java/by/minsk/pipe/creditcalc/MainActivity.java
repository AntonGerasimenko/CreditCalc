package by.minsk.pipe.creditcalc;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import by.minsk.pipe.creditcalc.Fragments.ActualPays;
import by.minsk.pipe.creditcalc.Fragments.CalcAllPays;
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

       if (savedInstanceState==null) creditList();
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ActualPays.TAG);
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
        fragment = getSupportFragmentManager().findFragmentByTag(CalcAllPays.TAG);
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

        Fragment fragment = ActualPays.newInstance(credit.getId());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, ActualPays.TAG).commit();
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

        Fragment fragment = CalcAllPays.newInstance(credit);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, CalcAllPays.TAG);
        ft.addToBackStack(CalcAllPays.TAG);
        ft.commit();
    }

    @Override
    public void showStatistic(Credit credit) {
        Fragment fragment = new CalcAllPays();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_bottom);
        ft.replace(R.id.temp_container, fragment, PieChartFr.TAG).commit();
    }
}


