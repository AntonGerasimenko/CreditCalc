package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Pay;

/**
 * Created by gerasimenko on 04.09.2015.
 */

public final class PayListAdapter extends PaysAdapter
{
    private   TextView balance;
    private   TextView lastPay;
    private   TextView overpay;
    private   TextView percent;
    private   TextView date;

    public PayListAdapter(Context context, int resource, List<Pay> objects) {
        super(context, resource, objects, Currency.BYR);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position,convertView,parent);

        calculateOverpayment(position);
        return view;
    }
    @Override
    protected void init(View view) {

        balance = (TextView) view.findViewById(R.id.balance);
        lastPay = (TextView) view.findViewById(R.id.last_pay);
        overpay = (TextView) view.findViewById(R.id.overpay);
        percent = (TextView) view.findViewById(R.id.percent);
        date = (TextView) view.findViewById(R.id.date);
    }
    @Override
    protected void complete(Pay pay) {

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
