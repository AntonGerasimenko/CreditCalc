package by.minsk.pipe.creditcalc.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 01.09.2015.
 */
public class MakeCredit extends Fragment implements View.OnClickListener{

    public static final String TAG = "MakeCredit";

    @InjectView(R.id.make_credit_spinner)   Spinner currency;
    @InjectView(R.id.credit_target)   EditText targetCredit;
    @InjectView(R.id.summa)  EditText summa;
    @InjectView(R.id.termin)  EditText endLendingData;
    @InjectView(R.id.percent)  EditText percent;
    @InjectView(R.id.use_refin_rate)   CheckBox useRefinRate;
    @InjectView(R.id.add_credit)  Button addCredit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.make_credit, container, false);
        ButterKnife.inject(this, view);

        addCredit.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        Credit credit = new Credit();
        long now  = new Date().getTime();
        credit.setDate(now);
        credit.setTarget(String.valueOf(targetCredit.getText()));

        double creditSumm =  Double.parseDouble(String.valueOf(summa.getText()));
        if (creditSumm == 0) {
            Toast.makeText(getActivity(),R.string.err_credit_null,Toast.LENGTH_LONG).show();
            return;
        }
        credit.setSumma(creditSumm);
        credit.setCurrency(Currency.BYR.getInt());

        String string = String.valueOf(percent.getText());
        if (string.isEmpty()) {
            Toast.makeText(getActivity(),R.string.err_interest_rate_null,Toast.LENGTH_LONG).show();
            return;
        }
        credit.setInterestRate(check(string));


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        credit.setStartData(calendar.getTimeInMillis());


        int year = (int) check(String.valueOf(endLendingData.getText()));
        if (year == 0) {
            Toast.makeText(getActivity(),R.string.err_term_null,Toast.LENGTH_LONG).show();
            return;
        }
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.add(Calendar.YEAR, year);
        credit.setEndData(nowCalendar.getTimeInMillis());

        credit.setUseRefinRate(useRefinRate.isChecked());

        DBservice.credit().create(credit);

        getFragmentManager().beginTransaction().remove(this).commit();
    }

    private double check(String string) {
        if (string!= null && !string.isEmpty()) {
            return Double.parseDouble(string);
        }
        return 0;
    }
}
