package by.minsk.pipe.creditcalc.models;

/**
 * Created by gerasimenko on 31.08.2015.
 */
public enum Currency {
    USD(1),
    EU(2),
    RUR(3),
    BYR(4),
    UA(5);

    private  int currency;
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
        return null;
    }

    public  int getInt() {
        return currency;
    }
}





