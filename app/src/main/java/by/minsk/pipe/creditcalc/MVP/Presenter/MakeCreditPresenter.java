package by.minsk.pipe.creditcalc.MVP.Presenter;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.widget.DatePicker;

import java.util.Calendar;

import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.MVP.View.FragmentPresenter;
import by.minsk.pipe.creditcalc.MVP.View.MakeCreditView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.R;

/**
 * Created by gerasimenko on 02.12.2015.
 */
public class MakeCreditPresenter extends BasePresenter {

    private MakeCreditView view;
    private FragmentPresenter mainPresenter;

    public MakeCreditPresenter(MakeCreditView view,FragmentPresenter mainPresenter) {
        this.view = view;
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void start() {
       showDebugCredit();
    }

    @Override
    public void stop() {

    }

    public void addCredit(Credit credit) {
        if (checkValidCredit(credit)) {


        }
    }

    public void calcCredit(Credit credit) {
        if (checkValidCredit(credit)) {

            mainPresenter.openCalcCredit(credit);
        }
    }

    public void setBeginCreditData(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = Convert.date(year, monthOfYear, dayOfMonth);
                MakeCreditPresenter.this.view.setBeginData(date);
            }
        };
        showDataPickerDialog(listener);
    }

    public void setEndCreditData(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                long begin = Convert.date( MakeCreditPresenter.this.view.getBeginCreditData());
                long end = Convert.date(Convert.date(year, monthOfYear, dayOfMonth));
                if (begin < end) {
                    String date = Convert.date(year, monthOfYear, dayOfMonth);
                    MakeCreditPresenter.this.view.setEndData(date);
                }
            }
        };
        showDataPickerDialog(listener);
    }

    private void showDataPickerDialog(DatePickerDialog.OnDateSetListener listener){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        DatePickerDialog dialog = new DatePickerDialog(view.getCont(), listener ,year,month,day);
        dialog.show();
    }

    private boolean checkValidCredit(Credit credit) {
        Resources resources = view.getCont().getResources();
        if (credit.getSumma()<=0) {
            view.toast(resources.getString(R.string.err_credit_null));
            return false;
        }
        if (credit.getInterestRate()==0) {
            view.toast(resources.getString(R.string.err_interest_rate_null));
            return false;
        }
        if (credit.getStartData()==0) {
            view.toast(resources.getString(R.string.err_term_begin_null));
            return false;
        }
        if (credit.getEndData()==0) {
            view.toast(resources.getString(R.string.err_term_end_null));
            return false;
        }
        return true;
    }

    private void showDebugCredit(){

        Calendar calendar = Calendar.getInstance();

        Credit credit = new Credit();
        credit.setTarget("Phone");
        credit.setSumma(30000);
        credit.setInterestRate(20);
        credit.setDate(calendar.getTimeInMillis());

        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DATE, 10);
        credit.setStartData(calendar.getTimeInMillis());

        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DATE, 10);
        credit.setEndData(calendar.getTimeInMillis());
        credit.setLocation(Currency.BYR.getInt());
        credit.setUseRefinRate(true);
        credit.setCurrency(Currency.BYR.getInt());

        view.setCredit(credit);
    }
}
