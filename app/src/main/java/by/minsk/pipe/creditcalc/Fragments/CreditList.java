package by.minsk.pipe.creditcalc.Fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.CreditList.CreditListListener;
import by.minsk.pipe.creditcalc.Logic.CreditListAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Credit;

/**
 * Created by gerasimenko on 02.09.2015.
 */
public class CreditList extends ListFragment implements CreditListListener{

    public static final String TAG = "CreditList";

    private List<Credit> credits;
    private FragmentListener showFragment;
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        credits = DBservice.credit().getAll();

        footerView = inflater.inflate(R.layout.add_credit_footer,null);
        ButterKnife.bind(this,footerView);

        ListAdapter adapter = new CreditListAdapter(getActivity(), R.layout.short_credit_item,credits,this);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        showFragment = (FragmentListener) getActivity();
        getListView().addFooterView(footerView);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showFragment.payList(credits.get(position));
        super.onListItemClick(l, v, position, id);
    }

    @OnClick(R.id.add_credit)
    public void addCredit(){

        showFragment.makeCredit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.unbind(this);
    }

    @Override
    public void delete(int position) {

        DBservice.credit().del(credits.get(position));
        credits.remove(position);

        ((ArrayAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void statistic(int position) {

        showFragment.showStatistic(credits.get(position));
    }
}
