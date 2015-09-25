package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 14.09.2015.
 */
public final class AllPaysListAdapter extends PaysAdapter {

    private TextView balance;
    private TextView allPay;
    private TextView interestPay;
    private TextView deptPay;
    private TextView date;
    private LinearLayout item;

    private final int red;
    private final int green;

    public AllPaysListAdapter(Context context, int resource, List objects, Currency currency) {
        super(context, resource, objects,currency);
        red = getContext().getResources().getColor(R.color.red);
        green = getContext().getResources().getColor(R.color.green);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if ((position % 12)== 0 || position == 0){
            item.setBackgroundColor(red);
        } else item.setBackgroundColor(green);

        return view;
    }

    @Override
    protected void complete(Pay pay) {

        Rate rate = pay.getRate();
        double exchRate;
        if (rate!=null) {
            exchRate  = getExchangeRate(rate);
        } else {
            exchRate = 1;
        }

        balance.setText(Convert.money(pay.getBalance(),exchRate));
        allPay.setText(Convert.money(pay.getPay(),exchRate));
        interestPay.setText(Convert.money(pay.getInterestPay(),exchRate));
        deptPay.setText(Convert.money(pay.getDeptPay(),exchRate));
        date.setText(Convert.date(pay.getDate()));
    }
    @Override
    protected void init(View view) {
        balance = (TextView) view.findViewById(R.id.balance);
        allPay = (TextView) view.findViewById(R.id.all_pay);
        interestPay = (TextView) view.findViewById(R.id.interest_pay);
        deptPay = (TextView) view.findViewById(R.id.debt_pay);
        date = (TextView) view.findViewById(R.id.date);

        item = (LinearLayout) view.findViewById(R.id.item);
    }
}
