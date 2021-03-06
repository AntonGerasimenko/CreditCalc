package by.minsk.pipe.creditcalc.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.PayListAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 18.09.2015.
 */
public final class ActualPaysList extends PayList {

    public static final String TAG = "ActualPays";

    private Credit credit;
    private Button addPay;

    public static ActualPaysList newInstance(Credit credit){

        ActualPaysList instance = new ActualPaysList();
        instance.pays = DBservice.pay().getAll(credit.getId());
        instance.credit = credit;

        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ListAdapter adapter = new PayListAdapter(getActivity(),R.layout.short_pay_item, printList);
        setListAdapter(adapter);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void inflateFooter(LayoutInflater inflater) {

        footer = inflater.inflate(R.layout.add_pay_footer,null);
        addPay = (Button) footer.findViewById(R.id.add_pay);
        addPay.setOnClickListener(this);

        currency = (Spinner) footer.findViewById(R.id.spinner);
        setSpinner();
        currency.setSelection(credit.getCurrency());
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
        setHeaderTitle(R.id.overpay,resources.getString(R.string.overpay));
        setHeaderTitle(R.id.percent,resources.getString(R.string.percent));
        setHeaderTitle(R.id.date, resources.getString(R.string.date));
    }

    @Override
    public void onClick(View v) {
        showFragment.makePay(credit.getId());
    }
}
