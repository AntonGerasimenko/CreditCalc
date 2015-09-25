package by.minsk.pipe.creditcalc.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Exception.IsTooLarge;
import by.minsk.pipe.creditcalc.Exception.IsTooSmall;
import by.minsk.pipe.creditcalc.Logic.Actual;

import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.SeparNum;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.Networks.BLRconnect;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;

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
    @InjectView(R.id.curr1)         TextView smallCurr;
    @InjectView(R.id.make_pay)      Button makePay;
    @InjectView(R.id.progressBar) ProgressBar progressBar;

    private Payment payment;
    private Pay lastPay;
    private double overpayment;
    private double minNextPay;

    private Currency currency;
    private FragmentListener showFragment;


    public static MakePay newInstance(@NonNull Actual actual, int id, FragmentListener showFragment) {

        MakePay instance = new MakePay();

        instance.payment = new Payment(actual);
        final Pay pay = DBservice.pay().getLast(id);

        if (pay.isEmpty()) {
            Credit newCredit = DBservice.credit().get(id);
            pay.setCredit(newCredit);
        }
        instance.lastPay = pay;
        instance.overpayment = calculateOverpayment(id);

        final Rate[] rate = {pay.getRate()};

        if (rate[0].getDate() == 0) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    BLRconnect xmLconnect = new BLRconnect();
                    rate[0] = xmLconnect.run(new Date());
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pay.setRate(rate[0]);
        }
        instance.showFragment = showFragment;

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calc_pay, container, false);
        ButterKnife.inject(this, view);

        setValues();

        balanceCurrency.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.currency)));
        balanceCurrency.setOnItemSelectedListener(this);

        currency = Currency.getInstance(lastPay.getCredit().getCurrency());
        balanceCurrency.setSelection(currency.getInt());

        makePay.setOnClickListener(this);

        nextPay.addTextChangedListener(new SeparNum(nextPay));

        return  view;
    }

    @Override
    public void onClick(View v) {

        final double sum = Convert.money(nextPay.getText());


        progressBar.setVisibility(View.VISIBLE);
        makePay.setVisibility(View.GONE);


        try {
            payment.make(sum, lastPay, new Payment.ResultAddPay() {
                @Override
                public void result() {

                    showFragment.payList(lastPay.getCredit());
                }
            });

        } catch (IsTooSmall isTooSmall) {
            Toast.makeText(getActivity(),R.string.pay_is_small,Toast.LENGTH_LONG).show();
        } catch (IsTooLarge isTooLarge) {
            Toast.makeText(getActivity(),R.string.pay_is_large,Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);
        makePay.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        currency = Currency.getInstance(position);
        if (currency != null) {

            Rate rate = lastPay.getRate();
            if (rate == null) return;
            double exchangeRate = rate.getExchangeRate(currency);

            balance.setText(Convert.money(lastPay.getBalance(), exchangeRate));
            smallCurr.setText(currency.toString());
            overpayment = Convert.money(overpay.getText());
            overpay.setText(Convert.money(overpayment, exchangeRate));

            minNextPay = Convert.money(nextPay.getText());
            nextPay.setText(Convert.money(minNextPay, exchangeRate));
        }
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
        overpay.setText(Convert.money(overpayment));

        String endData = Convert.date(lastPay.getCredit().getEndData());
        termin.setText(endData);
        intetestRate.setText(Convert.percent(lastPay.getCredit().getInterestRate()));

        minNextPay = payment.getMinPayment(lastPay);
        nextPay.setText(Convert.money(minNextPay));
    }

    private static double calculateOverpayment(int id) {
        List<Pay> pays = DBservice.pay().getAll(id);

        if (pays.isEmpty()) return 0;
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
