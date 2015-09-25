package by.minsk.pipe.creditcalc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerasimenko on 31.08.2015.
 */
public enum Currency {

    USD(0),
    EU(1),
    RUR(2),
    BYR(3),
    UA(4),
    BTK(5),
    UNKNOWN(-1);

    private  int currency;

    private final static List<Currency> list;

    static {
        list = new ArrayList<>();
        list.add(Currency.USD);
        list.add(Currency.EU);
        list.add(Currency.RUR);
        list.add(Currency.BYR);
        list.add(Currency.UA);
    }

    Currency(int currency) {
        this.currency = currency;
    }

    public static Currency getInstance(String type) {
        switch (type){
            case "$": return Currency.USD;
            case "€": return Currency.EU;
            case "₱": return Currency.RUR;
            case "р": return Currency.BYR;
            case "₴": return Currency.UA;
        }
        return Currency.UNKNOWN;
    }

    public static Currency getInstance(int type) {
        switch (type) {
            case 0: return Currency.USD;
            case 1: return Currency.EU;
            case 2: return Currency.RUR;
            case 3: return Currency.BYR;
            case 4: return Currency.UA;
            default:
                return Currency.UNKNOWN;
        }
    }

    public  int getInt() {
        return currency;
    }

    @Override
    public String toString() {
        switch (currency) {
            case 0: return "$";
            case 1: return "€";
            case 2: return "₱";
            case 3: return "р";
            case 4: return "₴";
        }
        return null;
    }

    public static List<Currency> getAllInstance(){
        return list;
    }
}





