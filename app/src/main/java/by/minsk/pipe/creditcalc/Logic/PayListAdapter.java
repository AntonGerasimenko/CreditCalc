package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 04.09.2015.
 */

public class PayListAdapter extends ArrayAdapter
{
    private   TextView balance;
    private   TextView lastPay;
    private   TextView overpay;
    private   TextView percent;
    private   TextView date;

    private List<Pay> pays;

    public PayListAdapter(Context context, int resource, List<Pay> objects) {
        super(context, resource, objects);

        pays = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.short_pay_item, parent, false);
        }

        init(convertView);
        complete(pays.get(position));
        calculateOverpayment(position);

        return convertView;
    }

    private void init(View view) {

        balance = (TextView) view.findViewById(R.id.balance);
        lastPay = (TextView) view.findViewById(R.id.last_pay);
        overpay = (TextView) view.findViewById(R.id.overpay);
        percent = (TextView) view.findViewById(R.id.percent);
        date = (TextView) view.findViewById(R.id.date);
    }

    private void complete(Pay pay) {

        balance.setText(Convert.money(pay.getBalance()));
        lastPay.setText(Convert.money(pay.getPay()));

        percent.setText(Convert.percent(pay.getCredit().getInterestRate()));
        date.setText(Convert.nowDate(pay.getDate()));
    }

    private void calculateOverpayment(int position) {

        double accum = 0;
        int i=0;
        position++;
        do {
            Pay pay = pays.get(i);
            accum += pay.getOverpayment();
            i++;
        } while(i<position);

        overpay.setText(Convert.money(accum));
    }
}
