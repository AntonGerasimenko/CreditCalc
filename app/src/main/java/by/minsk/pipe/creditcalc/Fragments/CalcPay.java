package by.minsk.pipe.creditcalc.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Annotation;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.Exception.MakeNewCreditFault;
import by.minsk.pipe.creditcalc.Logic.Actual;

import by.minsk.pipe.creditcalc.Logic.Credit;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Currency;

/**
 * Created by gerasimenko on 31.08.2015.
 */
public class CalcPay extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "CalcPay";

    @InjectView(R.id.balance_currency_spinner) Spinner balanceCurrency;

    @InjectView(R.id.balance)    TextView balance;
    @InjectView(R.id.overpay)    TextView overpay;
    @InjectView(R.id.next_pay)   EditText nextPay;

    @InjectView(R.id.make_pay)   Button makePay;

    private Actual actual;
    private Payment payment;


    public static CalcPay newInstance(@NonNull Actual actual) {

        CalcPay instance = new CalcPay();
        instance.actual = actual;
        instance.payment = new Payment(actual);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calc_pay, container, false);
        ButterKnife.inject(this, view);


        balanceCurrency.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.currency)));

        makePay.setOnClickListener(this);

        double minPay = payment.getMinPayment();
        nextPay.setText(String.valueOf(minPay));

        return  view;
    }

    @Override
    public void onClick(View v) {

        Credit newCredit = new Credit();


        try {
            Date date = new Date(2020,07,22);
            newCredit.make(35,200000000,true,date);
        } catch (MakeNewCreditFault makeNewCreditFault) {
            makeNewCreditFault.printStackTrace();
        }

        double sum = Double.parseDouble(String.valueOf(nextPay.getText()));
        try {
            payment.makePayment(sum);
        } catch (IsTooSmall isTooSmall) {
            Toast.makeText(getActivity(),R.string.pay_is_small,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String currenc = (String) balanceCurrency.getItemAtPosition(position);
        Currency currency = Currency.getInstance(currenc);
        balance.setText(actual.getBalance(currency));


        String text = String.valueOf(actual.getPay().getOverpayment());
        overpay.setText(text);

        text = String.valueOf(payment.getMinPayment());
        nextPay.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}