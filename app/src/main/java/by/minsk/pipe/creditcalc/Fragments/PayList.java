package by.minsk.pipe.creditcalc.Fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.CurrencySpinAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Pay;

/**
 * Created by gerasimenko on 18.09.2015.
 */
public abstract class PayList extends ListFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    protected FragmentListener showFragment;

    protected List<Pay> pays;
    protected List<Pay> printList;

    protected View footer;
    protected View header;

    @Bind(R.id.summa)    TextView summa;
    @Bind(R.id.all_pays) TextView allPays;
    @Bind(R.id.overpay) TextView overpay;
    @Bind(R.id.currency) Spinner currency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflateHeater(inflater);
        copyList(Currency.BYR);


        if (!pays.isEmpty()) {
            int size = pays.size();
            Pay pay = (pays.size()==1) ? pays.get(0):pays.get(size - 1);
            if ((int)pay.getBalance() == 0)  inflateHeaderTotal(inflater);
            else inflateFooter(inflater);
        } else inflateFooter(inflater);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        showFragment = (FragmentListener) getActivity();

        if (!pays.isEmpty())  contentHeader();
        getListView().addFooterView(footer);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.unbind(this);
    }

    protected void setHeaderTitle(int id,String title){
        TextView text = (TextView) header.findViewById(id);
        text.setText(title);
    }

    protected void inflateHeaderTotal(LayoutInflater inflater){

        footer = inflater.inflate(R.layout.total_pay,null);
        ButterKnife.bind(this,footer);

        recalcTotal();
        setSpinner();
    }

    protected abstract void inflateFooter(LayoutInflater inflater);

    protected abstract void inflateHeater(LayoutInflater inflater);

    protected abstract void contentHeader();

    private double getAllPays() {

        double summa=0;
        for (Pay pay:printList) {
            summa += pay.getPay();
        }

        return summa;
    }

    protected void recalc(Currency currency, List<Pay> collect ) {

        for (Pay pay:collect) {
            Convert.recalculate(currency,pay);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Currency currency = Currency.getInstance(position);
        copyList(currency);
        if (summa != null) recalcTotal();

        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void copyList(Currency currency) {
        printList = new ArrayList<>();

        for (Pay pay:pays)
            try {
                printList.add(pay.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        recalc(currency,pays);
    }

    private void recalcTotal(){

        double sum = pays.get(0).getBalance();
        summa.setText(Convert.money(sum));

        double allPa = getAllPays();
        allPays.setText(Convert.money(allPa));
        overpay.setText(Convert.money(allPa - sum));
    }

    protected void setSpinner(){

        ArrayAdapter<Currency> adapter = new CurrencySpinAdapter(getActivity(),R.layout.currency_spinner_item,Currency.getAllInstance());
        currency.setAdapter(adapter);
        currency.setOnItemSelectedListener(this);
    }
}
