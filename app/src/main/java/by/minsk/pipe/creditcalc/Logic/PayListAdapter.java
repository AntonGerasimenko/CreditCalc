package by.minsk.pipe.creditcalc.Logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import java.util.List;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 04.09.2015.
 */

public class PayListAdapter extends ArrayAdapter
{
    private   EditText balance;
    private   EditText lastPay;
    private   EditText overpay;
    private   EditText percent;


    private List<Pay> pays;

    public PayListAdapter(Context context, int resource, List<Pay> objects) {
        super(context, resource, objects);

        pays = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.short_pay_item, parent, false);
        }

        init(convertView);
        complete(pays.get(position));

        return convertView;
    }

    @SuppressLint("WrongViewCast")
    private void init(View view) {

        balance = (EditText) view.findViewById(R.id.balance);
        lastPay = (EditText) view.findViewById(R.id.last_pay);
        overpay = (EditText) view.findViewById(R.id.overpay);
        percent = (EditText) view.findViewById(R.id.percent);
    }

    private void complete(Pay pay) {

        balance.setText(Convert.money(pay.getBalance()));
        lastPay.setText(Convert.money(pay.getPay()));
        overpay.setText(Convert.money(pay.getOverpayment()));
        percent.setText(Convert.percent(pay.getCredit().getInterestRate()));
    }
}
