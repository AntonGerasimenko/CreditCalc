package by.minsk.pipe.creditcalc.models;

/**
 * Created by gerasimenko on 31.08.2015.
 */
public enum Currency {
    USD,
    EU,
    RUR,
    BYR,
    UA;

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
}





