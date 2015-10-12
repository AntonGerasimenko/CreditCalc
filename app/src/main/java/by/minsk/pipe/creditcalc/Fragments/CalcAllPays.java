package by.minsk.pipe.creditcalc.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;

import by.minsk.pipe.creditcalc.Logic.Actual;
import by.minsk.pipe.creditcalc.Logic.AllPaysListAdapter;
import by.minsk.pipe.creditcalc.Logic.Payment;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;


/**
 * Created by gerasimenko on 18.09.2015.
 */
public final class CalcAllPays extends PayList {

    public static final String TAG = "CreditAllPays";
    private Credit credit;

    public static CalcAllPays newInstance(Credit credit) {
        CalcAllPays instance = new CalcAllPays();

        Payment payment = new Payment(new Actual());
        instance.pays = payment.calculateAllCredit(credit);
        instance.credit = credit;

        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ListAdapter adapter = new AllPaysListAdapter(getActivity(), R.layout.all_pays, list, Currency.getInstance(credit.getCurrency()));
        setListAdapter(adapter);


        super.onActivityCreated(savedInstanceState);
        currency.setSelection(credit.getCurrency());
    }

    @Override
    protected void inflateFooter(LayoutInflater inflater) {

    }

    @Override
    protected void inflateHeater(LayoutInflater inflater) {

        header = inflater.inflate(R.layout.short_pay_item, null);
    }

    @Override
    protected void contentHeader() {
        getListView().addHeaderView(header);

        Resources resources = getActivity().getResources();

        setHeaderTitle(R.id.balance,resources.getString(R.string.balance));
        setHeaderTitle(R.id.last_pay,resources.getString(R.string.pay));
        setHeaderTitle(R.id.overpay,resources.getString(R.string.percent_pay));
        setHeaderTitle(R.id.percent,resources.getString(R.string.body_credit));
        setHeaderTitle(R.id.date, resources.getString(R.string.date));
    }

    @Override
    public void onClick(View v) {

    }

    public Credit getCredit(){

        return credit;
    }
}
