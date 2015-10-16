package by.minsk.pipe.creditcalc.Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 14.09.2015.
 */
public abstract class PaysAdapter extends ArrayAdapter {

    protected List<Pay> pays;
    protected int resource;
    protected Currency currency;


    public PaysAdapter(Context context, int resource, List objects,Currency currency) {
        super(context, resource, objects);
        pays = objects;
        this.resource = resource;
        this.currency = currency;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        init(convertView);
        complete(pays.get(position));

        return convertView;
    }

    public void setCurrency(Currency currency){
        assert (currency!=null);
        this.currency = currency;
    }

    protected double getExchangeRate(Rate rate){

        if (rate!= null) {
            switch (currency){
                case USD:
                    return rate.getUsaRate();
                case EU:
                    return rate.getEuRate();
                case RUR:
                    return  rate.getRuRate();
                case UA:
                    return rate.getUaRate();
                case BYR:
                case UNKNOWN:
                    return 1;
            }
        }
        return 1;
    }

    protected abstract void init(View view);
    protected abstract void complete(Pay pay);
}
