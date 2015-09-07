package by.minsk.pipe.creditcalc.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ListAdapter;
import java.util.List;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.PayListAdapter;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 04.09.2015.
 */
public class PayList extends ListFragment {

    public static final String TAG = "PayList";
    private List<Pay> pays;

    private int idCredit;

    public static PayList newInstance(int idCredit) {

        PayList instance = new PayList();
        instance.pays = DBservice.pay().getAll(idCredit);
        instance.idCredit = idCredit;

        return instance;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        ListAdapter adapter = new PayListAdapter(getActivity(),R.layout.short_pay_item,pays);
        setListAdapter(adapter);

        super.onActivityCreated(savedInstanceState);
    }

    public int getIdCredit(){
        return idCredit;
    }
}
