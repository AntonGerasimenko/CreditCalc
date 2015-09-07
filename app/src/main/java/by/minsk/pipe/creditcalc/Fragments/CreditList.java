package by.minsk.pipe.creditcalc.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.CreditList.CreditListListener;
import by.minsk.pipe.creditcalc.Logic.CreditListAdapter;
import by.minsk.pipe.creditcalc.Logic.CreditOperation;
import by.minsk.pipe.creditcalc.MainActivity;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;

/**
 * Created by gerasimenko on 02.09.2015.
 */
public class CreditList extends ListFragment implements CreditListListener {

    public static final String TAG = "CreditList";
    private List<Credit> credits;
    private List<String> targets = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        credits = DBservice.credit().getAll();


        for(Credit credit:credits) targets.add(credit.getTarget());
        ListAdapter adapter = new CreditListAdapter(getActivity(), R.layout.short_credit_item,targets,this);
        setListAdapter(adapter);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void delete(int position) {
        DBservice.credit().del(credits.get(position));
        targets.remove(position);
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void click(int position) {
        ((MainActivity)getActivity()).showPayList(credits.get(position).getId());
    }
}
