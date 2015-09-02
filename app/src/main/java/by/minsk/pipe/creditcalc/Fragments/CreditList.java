package by.minsk.pipe.creditcalc.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import by.minsk.pipe.creditcalc.DB.DBservice;

/**
 * Created by gerasimenko on 02.09.2015.
 */
public class CreditList extends ListFragment {

    public static final String TAG = "CreditList";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,  DBservice.getCredits()));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
    }
}
