package by.minsk.pipe.creditcalc.Fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.List;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.CreditList.CreditListListener;
import by.minsk.pipe.creditcalc.Logic.CreditListAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;

/**
 * Created by gerasimenko on 02.09.2015.
 */
public class CreditList extends ListFragment implements View.OnClickListener, CreditListListener{

    public static final String TAG = "CreditList";
    private List<Credit> credits;
    private FragmentListener showFragment;

    private View footerView;
    private Button addButton;

    public static CreditList newInstance(FragmentListener showFragment) {
        assert (showFragment!=null);

        CreditList instance = new CreditList();
        instance.showFragment = showFragment;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        credits = DBservice.credit().getAll();

        ListAdapter adapter = new CreditListAdapter(getActivity(), R.layout.short_credit_item,credits,this);
        setListAdapter(adapter);

        footerView = inflater.inflate(R.layout.add_credit_footer,null);
        addButton = (Button) footerView.findViewById(R.id.add_credit);
        addButton.setOnClickListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        getListView().addFooterView(footerView);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showFragment.payList(credits.get(position));
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_credit:

                showFragment.makeCredit();
                break;
        }
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
