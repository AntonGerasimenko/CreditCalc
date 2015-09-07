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

/**
 * Created by gerasimenko on 04.09.2015.
 */
public class CreditListAdapter extends ArrayAdapter {

    private List<String> credits;
    private CreditListListener listener;

    public CreditListAdapter(Context context, int resource, List<String> objects,CreditListListener listener ) {
        super(context, resource, objects);
        credits = objects;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.short_credit_item, parent, false);
        }
        Button delete = (Button) convertView.findViewById(R.id.delete_credit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(position);
            }
        });
        TextView creditTarget = (TextView) convertView.findViewById(R.id.credit);
        creditTarget.setText(credits.get(position));

        creditTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(position);
            }
        });

        return convertView;
    }
}
