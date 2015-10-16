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

import by.minsk.pipe.creditcalc.Logic.Convert;
import by.minsk.pipe.creditcalc.Logic.CurrencySpinAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 18.09.2015.
 */
public abstract class PayList extends ListFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    protected List<Pay> pays;
    protected List<Pay> list = new ArrayList<>();

    protected FragmentListener showFragment;
    protected View footer;
    protected View header;

    protected TextView summa;
    protected TextView allPays;
    protected TextView overpay;

    protected Spinner currency;


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

        for (Pay pay:pays)
            try {
                list.add(pay.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        if (!pays.isEmpty())  contentHeader();
        getListView().addFooterView(footer);

        super.onActivityCreated(savedInstanceState);
    }

    protected void setHeaderTitle(int id,String title){
        TextView text = (TextView) header.findViewById(id);
        text.setText(title);
    }

    protected void inflateHeaderTotal(LayoutInflater inflater){

        footer = inflater.inflate(R.layout.total_pay,null);

        summa = (TextView) footer.findViewById(R.id.summa);
        allPays =  (TextView) footer.findViewById(R.id.all_pays);
        overpay = (TextView) footer.findViewById(R.id.overpay);
        currency = (Spinner) footer.findViewById(R.id.currency);



        recalcTotal();
        setSpinner();
    }

    protected abstract void inflateFooter(LayoutInflater inflater);

    protected abstract void inflateHeater(LayoutInflater inflater);

    protected abstract void contentHeader();

    private double getAllPays() {

        double summa=0;
        for (Pay pay:list) {
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
        list.clear();

        for (Pay pay:pays)
            try {
                list.add(pay.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        recalc(currency,list);
    }

    private void recalcTotal(){

        double sum = list.get(0).getBalance();
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
