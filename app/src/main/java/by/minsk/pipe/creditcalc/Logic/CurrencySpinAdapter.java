package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.minsk.pipe.creditcalc.R;
import by.minsk.pipe.creditcalc.MVP.models.Currency;

/**
 * Created by gerasimenko on 18.09.2015.
 */
public class CurrencySpinAdapter extends ArrayAdapter<Currency> {

    private  List<Currency> objects;
    private int resource;
    private Resources resources;

    public CurrencySpinAdapter(Context context, int resource, List<Currency> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;

        resources = context.getResources();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        content(objects.get(position),convertView);
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        content(objects.get(position),convertView);

        return convertView;
    }

    private void content(Currency currency, View view){
        int image =0;
        String text =null;
        switch (currency) {
            case BYR:
                image = R.drawable.bel;
                text = resources.getString(R.string.by_rub);
                break;
            case EU:
                image = R.drawable.euro;
                text = resources.getString(R.string.euro);
                break;
            case USD:
                image = R.drawable.usd_icon;
                text = resources.getString(R.string.usd);
                break;
            case RUR:
                image = R.drawable.icon_rub;
                text = resources.getString(R.string.rur);
                break;
            case UA:
                image = R.drawable.ua_128;
                text = resources.getString(R.string.ua);
                break;
        }
        set(image, text, view);
    }

    private void set(int image, String text, View view) {

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setBackgroundResource(image);

        TextView currency = (TextView) view.findViewById(R.id.text);
        currency.setText(text);
    }
}
