package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Logic.CreditList.CreditListListener;
import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.models.Credit;
import by.minsk.pipe.creditcalc.models.Currency;

/**
 * Created by gerasimenko on 04.09.2015.
 */
public final class CreditListAdapter extends ArrayAdapter  {

    private List<Credit> credits;
    private CreditListListener listener;

    public CreditListAdapter(Context context, int resource, List<Credit> objects,CreditListListener listener) {
        super(context, resource, objects);
        credits = objects;
        this.listener = listener;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.short_credit_item, parent, false);
        }

        Credit credit = credits.get(position);

        TextView target = (TextView) convertView.findViewById(R.id.target);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView currency = (TextView) convertView.findViewById(R.id.currency);

        currency.setText(Currency.getInstance(credit.getCurrency()).toString());

        Button delete = (Button) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          listener.delete(position);
                                      }
                                  }
        );

        Button statistic = (Button) convertView.findViewById(R.id.statistic);
        statistic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        listener.statistic(position);
                                    }
        });

        target.setText(credit.getTarget());
        balance.setText(Convert.money(credit.getSumma()));

        date.setText(Convert.date(credit.getStartData()) +" - " + Convert.date(credit.getEndData()));

        return convertView;
    }
}
