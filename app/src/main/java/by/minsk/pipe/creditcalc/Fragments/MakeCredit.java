package by.minsk.pipe.creditcalc.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnClick;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.CurrencySpinAdapter;
import by.minsk.pipe.creditcalc.Logic.SeparNum;
import by.minsk.pipe.creditcalc.MVP.Presenter.MakeCreditPresenter;
import by.minsk.pipe.creditcalc.MVP.View.MakeCreditView;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Currency;

/**
 * Created by gerasimenko on 01.09.2015.
 */
public class MakeCredit extends Fragment implements View.OnClickListener, MakeCreditView{

    public static final String TAG = "MakeCredit";

    @Bind(R.id.image_credit)   Spinner imageCredit;
    @Bind(R.id.credit_target)   EditText targetCredit;
    @Bind(R.id.summa)  EditText summa;
    @Bind(R.id.end_termin)  TextView endLendingData;
    @Bind(R.id.begin_termin)  TextView beginLendingData;
    @Bind(R.id.percent)  EditText percent;
    @Bind(R.id.use_refin_rate)   CheckBox useRefinRate;

    @Bind(R.id.currency)  Spinner currency;

    private MakeCreditPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.make_credit, container, false);
        ButterKnife.bind(this, view);

        summa.addTextChangedListener(new SeparNum(summa));

        ArrayAdapter<Currency> adapter = new CurrencySpinAdapter(getActivity(),R.layout.currency_spinner_item,Currency.getAllInstance());
        currency.setAdapter(adapter);
        currency.setSelection(Currency.BYR.getInt());

        endLendingData.setOnClickListener(this);
        beginLendingData.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStop() {

        ButterKnife.unbind(this);
        super.onStop();
    }

    @Override
    public void onClick(View v) {

        DatePickerDialog dialog;
        Calendar calendar;
        int year;
        int month;
        int day;
        switch (v.getId()) {
            case R.id.begin_termin:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);

                dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        beginLendingData.setText(Convert.date(year,monthOfYear,dayOfMonth));
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.end_termin:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);

                dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        long begin = Convert.date(beginLendingData.getText());
                        long end = Convert.date(Convert.date(year, monthOfYear, dayOfMonth));

                        if (begin < end) endLendingData.setText(Convert.date(year,monthOfYear,dayOfMonth));
                    }
                },year,month,day);
                dialog.show();
                break;
        }
    }

    @OnClick({R.id.add_credit,R.id.calculate_pays}) public void clickBtns(View view) {
        Credit credit =  new Credit();

        credit.setTarget(String.valueOf(targetCredit.getText()));
        credit.setDate(Convert.date(beginLendingData.getText()));
        credit.setEndData(Convert.date(endLendingData.getText()));
        credit.setCurrency(currency.getSelectedItemPosition());
        credit.setInterestRate(Convert.money(percent.getText()));
        credit.setLocation(Currency.BYR.getInt());
        credit.setSumma(Convert.money(summa.getText()));

        switch (view.getId()) {
            case R.id.add_credit:
                presenter.addCredit(credit);
                break;
            case R.id.calculate_pays:
                presenter.calcCredit(credit);
        }
    }

    private Credit collectCredit(){

        Credit credit = new Credit();
        long now  = new Date().getTime();
        credit.setDate(now);
        credit.setTarget(String.valueOf(targetCredit.getText()));

        double creditSumm = Convert.money(summa.getText());

        if (creditSumm == 0) {
            Toast.makeText(getActivity(),R.string.err_credit_null,Toast.LENGTH_LONG).show();
            return Credit.empty();
        }
        credit.setSumma(creditSumm);

       /* credit.setCurrency(Currency.BYR.getInt());*/

        String string = String.valueOf(percent.getText());
        if (string.isEmpty()) {
            Toast.makeText(getActivity(),R.string.err_interest_rate_null,Toast.LENGTH_LONG).show();
            return Credit.empty();
        }
        credit.setInterestRate(check(string));

        CharSequence date = beginLendingData.getText();
        String empty = getResources().getString(R.string.enter_term);
        if (empty.equals(date)) {
            Toast.makeText(getActivity(),R.string.err_term_begin_null,Toast.LENGTH_LONG).show();
            return Credit.empty();
        }
        credit.setStartData(Convert.date(date));

        date = endLendingData.getText();
        if (empty.equals(date)) {
            Toast.makeText(getActivity(),R.string.err_term_end_null,Toast.LENGTH_LONG).show();
            return Credit.empty();
        }
        credit.setEndData(Convert.date(date));

        credit.setUseRefinRate(useRefinRate.isChecked());

        Currency curr = (Currency) currency.getSelectedItem();
        credit.setCurrency(curr.getInt());
        credit.setLocation(Currency.BYR.getInt());

        return credit;
    }

    private double check(String string) {
        if (string!= null && !string.isEmpty()) {
            return Double.parseDouble(string);
        }
        return 0;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public Credit getCredit() {
        return null;
    }

    @Override
    public void setCredit(Credit credit) {

        targetCredit.setText(credit.getTarget());
        summa.setText(Convert.money(credit.getSumma()));
        beginLendingData.setText(Convert.money(credit.getStartData()));
        endLendingData.setText(Convert.money(credit.getEndData()));
        percent.setText(Convert.money(credit.getInterestRate()));

        currency.setSelection(credit.getCurrency());
    }
}
