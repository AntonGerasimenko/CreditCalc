package by.minsk.pipe.creditcalc.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.CurrencySpinAdapter;
import by.minsk.pipe.creditcalc.Logic.SeparNum;
import by.minsk.pipe.creditcalc.Logic.convert.MyDates;
import by.minsk.pipe.creditcalc.MVP.Presenter.MakeCreditPresenter;
import by.minsk.pipe.creditcalc.MVP.View.MakeCreditView;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Currency;

/**
 * Created by gerasimenko on 01.09.2015.
 */
public class MakeCredit extends Fragment implements MakeCreditView{

    public static final String TAG = "MakeCredit";

    @Bind(R.id.image_credit)    Spinner imageCredit;
    @Bind(R.id.credit_target)   EditText targetCredit;
    @Bind(R.id.summa)           EditText summa;
    @Bind(R.id.end_termin)      TextView endLendingData;
    @Bind(R.id.begin_termin)    TextView beginLendingData;
    @Bind(R.id.percent)         EditText percent;
    @Bind(R.id.use_refin_rate)  CheckBox useRefinRate;
    @Bind(R.id.currency)        Spinner currency;

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

        Log.d("Fragment", "CreateView MakeCredit");

        presenter = new MakeCreditPresenter(this);

        return view;
    }

    @Override
    public void onStart() {
        presenter.start();
        super.onStart();
        Log.d("Fragment", "MakeCredit onStart");
    }

    @Override
    public void onStop() {
        presenter.stop();
        ButterKnife.unbind(this);
        super.onStop();
    }

    @OnClick({R.id.begin_termin,R.id.end_termin}) public void clickData(View view){
        switch (view.getId()){
            case R.id.begin_termin:
                presenter.setBeginCreditData();
                return;
            case R.id.end_termin:
                presenter.setEndCreditData();
        }
    }

    @OnClick({R.id.add_credit,R.id.calculate_pays}) public void clickBtns(View view) {
        Credit credit =  new Credit();

        credit.setTarget(String.valueOf(targetCredit.getText()));
        try {
            long date = MyDates.date(beginLendingData.getText());
            credit.setStartData(date);
        } catch (ParseException e) {
            credit.setStartData(0);
        }
        try {
            long date = MyDates.date(endLendingData.getText());
            credit.setEndData(date);
        } catch (ParseException e) {
            credit.setEndData(0);
        }
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

    @Override
    public void setCredit(Credit credit) {

        targetCredit.setText(credit.getTarget());
        summa.setText(Convert.money(credit.getSumma()));
        beginLendingData.setText(MyDates.date(credit.getStartData()));
        endLendingData.setText(MyDates.date(credit.getEndData()));
        percent.setText(Convert.money(credit.getInterestRate()));

        currency.setSelection(credit.getCurrency());
    }

    @Override
    public void toast(String message) {
        if (message!= null && !message.isEmpty()){
            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setBeginData(String data) {
        if (data!= null && !data.isEmpty()) {
            beginLendingData.setText(data);
        }
    }

    @Override
    public void setEndData(String data) {
        if (data!= null && !data.isEmpty()) {
            endLendingData.setText(data);
        }
    }

    @Override
    public String getBeginCreditData() {
        return (String) beginLendingData.getText();
    }


    @Override
    public Context getCont() {
        return getActivity();
    }
}
