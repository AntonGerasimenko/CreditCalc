package by.minsk.pipe.creditcalc.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.CurrencySpinAdapter;
import by.minsk.pipe.creditcalc.Logic.SeparNum;
import by.minsk.pipe.creditcalc.MainActivity;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Currency;


/**
 * Created by gerasimenko on 01.09.2015.
 */
public class MakeCredit extends Fragment implements View.OnClickListener{

    public static final String TAG = "MakeCredit";

    @InjectView(R.id.image_credit)   Spinner imageCredit;
    @InjectView(R.id.credit_target)   EditText targetCredit;
    @InjectView(R.id.summa)  EditText summa;
    @InjectView(R.id.end_termin)  TextView endLendingData;
    @InjectView(R.id.begin_termin)  TextView beginLendingData;
    @InjectView(R.id.percent)  EditText percent;
    @InjectView(R.id.use_refin_rate)   CheckBox useRefinRate;
    @InjectView(R.id.add_credit)  Button addCredit;
    @InjectView(R.id.calculate_pays)  Button calculateAllPays;
    @InjectView(R.id.currency)  Spinner currency;

    private FragmentListener showFragment;

    private String[] dates;


    public static MakeCredit newInstance(FragmentListener showFragment) {
        assert (showFragment!=null);

        MakeCredit instance = new MakeCredit();
        instance.showFragment = showFragment;

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.make_credit, container, false);
        ButterKnife.inject(this, view);

        addCredit.setOnClickListener(this);
        calculateAllPays.setOnClickListener(this);
        summa.addTextChangedListener(new SeparNum(summa));

        ArrayAdapter<Currency> adapter = new CurrencySpinAdapter(getActivity(),R.layout.currency_spinner_item,Currency.getAllInstance());
        currency.setAdapter(adapter);

        endLendingData.setOnClickListener(this);
        beginLendingData.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String end;
        String begin;

        if (savedInstanceState == null) {
            if (dates!= null) {

                begin = dates[0];
                end = dates[1];
                dates = null;
            } else {
                end = begin = getResources().getString(R.string.enter_term);
            }
        } else {
            end = savedInstanceState.getString("endLendingData");
            begin = savedInstanceState.getString("beginLendingData");
        }

        endLendingData.setText(end);
        beginLendingData.setText(begin);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {

        dates = new String[]{beginLendingData.getText().toString(), endLendingData.getText().toString()};
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        Credit credit;
        DatePickerDialog dialog;
        Calendar calendar;
        int year;
        int month;
        int day;
        switch (v.getId()) {
            case R.id.calculate_pays:
                credit = collectCredit();
                if (credit.isEmpty()) return;
                showFragment.calcAllPays(credit);
                break;
            case R.id.add_credit:
                credit = collectCredit();
                if (credit.isEmpty()) return;
                showFragment.creditList();
                DBservice.credit().create(credit);
                break;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("beginLendingData",beginLendingData.getText().toString());
        outState.putString("endLendingData",endLendingData.getText().toString());
        super.onSaveInstanceState(outState);
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
        credit.setCurrency(Currency.BYR.getInt());

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

        return credit;
    }

    private double check(String string) {
        if (string!= null && !string.isEmpty()) {
            return Double.parseDouble(string);
        }
        return 0;
    }
}
