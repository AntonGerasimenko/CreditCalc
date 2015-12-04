package by.minsk.pipe.creditcalc.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import by.minsk.pipe.creditcalc.Logic.AllPaysListAdapter;
import by.minsk.pipe.creditcalc.MVP.Presenter.CalcPaysPresenter;
import by.minsk.pipe.creditcalc.MVP.View.ListPaysView;
import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Pay;
import by.minsk.pipe.creditcalc.MVP.models.Total;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Currency;


/**
 * Created by gerasimenko on 18.09.2015.
 */
public final class CalcPays extends Fragment implements ListPaysView {

    public static final String TAG = "CreditAllPays";

    private CalcPaysPresenter presenter;
    private Credit credit;


    @Bind(R.id.pays_list)
    ListView listView;

    public static Fragment getInstance(Credit credit){
        CalcPays instance = new CalcPays();
        instance.credit = credit;

        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.pay_list,container,false);
        ButterKnife.bind(this,view);

        presenter = new CalcPaysPresenter(this,credit);
        return view;
    }

    @Override
    public void showPays(List<Pay> pays, Currency currency) {

        ListAdapter adapter = new AllPaysListAdapter(getActivity(), R.layout.all_pays, pays, currency);
        listView.setAdapter(adapter);
    }

    @Override
    public void recalcCurrency(Total total) {



    }

    @Override
    public void onStart() {
        presenter.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
