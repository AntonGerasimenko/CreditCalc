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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooLarge;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.Logic.Actual;

import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.MainActivity;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 31.08.2015.
 */
public class MakePay extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = "CalcPay";

    @InjectView(R.id.balance_currency_spinner) Spinner balanceCurrency;
    @InjectView(R.id.credit_target) TextView target;
    @InjectView(R.id.termin)        TextView termin;
    @InjectView(R.id.interest_rate) TextView intetestRate;
    @InjectView(R.id.balance)       TextView balance;
    @InjectView(R.id.overpay)       TextView overpay;
    @InjectView(R.id.next_pay)      EditText nextPay;

    @InjectView(R.id.make_pay)   Button makePay;

    private Actual actual;
    private Payment payment;
    private Pay lastPay;

    public static MakePay newInstance(@NonNull Actual actual, int id) {

        MakePay instance = new MakePay();
        instance.actual = actual;
        instance.payment = new Payment(actual);
        Pay pay = DBservice.pay().getLast(id);

        if (pay.isEmpty()) {
            Credit newCredit = DBservice.credit().get(id);
            pay.setCredit(newCredit);
        }
        instance.lastPay = pay;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calc_pay, container, false);
        ButterKnife.inject(this, view);

        setValues();

        balanceCurrency.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.currency)));
        makePay.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {

        double sum = Double.parseDouble(String.valueOf(nextPay.getText()));
        try {
            payment.make(sum, lastPay);
        } catch (IsTooSmall ex) {
            Toast.makeText(getActivity(),R.string.pay_is_small,Toast.LENGTH_LONG).show();
            return;
        } catch (IsTooLarge ex) {
            Toast.makeText(getActivity(),R.string.pay_is_large,Toast.LENGTH_LONG).show();
            return;
        }
        ((MainActivity)getActivity()).showPayList(lastPay.getCredit().getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       /* String currenc = (String) balanceCurrency.getItemAtPosition(position);
        Currency currency = Currency.getInstance(currenc);
        balance.setText(actual.getBalance(currency));


        String text = String.valueOf(actual.getPay().getOverpayment());
        overpay.setText(text);

        text = String.valueOf(payment.getMinPayment());
        nextPay.setText(text);*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setValues(){

        Credit credit = lastPay.getCredit();

        target.setText(credit.getTarget());
        balance.setText(
                Convert.money(
                        lastPay.isEmpty() ?
                                credit.getSumma() :
                                lastPay.getBalance()
                )
        );

        overpay.setText(Convert.money(calculateOverpayment()));

        String endData = Convert.date(lastPay.getCredit().getEndData());
        termin.setText(endData);
        intetestRate.setText(Convert.percent(lastPay.getCredit().getInterestRate()));

        nextPay.setText(Convert.money(
                        payment.getMinPayment(lastPay)
                )
        );
    }

    private double calculateOverpayment() {

        List<Pay> pays = DBservice.pay().getAll(lastPay.getCredit().getId());

        double accum = 0;
        int i=0;
        int position = pays.size();
        do {
            Pay pay = pays.get(i);
            accum += pay.getOverpayment();
            i++;
        } while(i<position);

        return accum;
    }
}
