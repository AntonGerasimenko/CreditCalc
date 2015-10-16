package by.minsk.pipe.creditcalc.Logic;

import android.text.Editable;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.minsk.pipe.creditcalc.models.Currency;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 03.09.2015.
 */
public class Convert {

    private static Calendar calendar = Calendar.getInstance();
    public static final char SEP_CHAR = ' ';
    public static final char POINT = '.';

    public static long date(CharSequence text) {

        String without = text.toString().substring(5);
        int year = Integer.decode(text.toString().substring(0, 4));
        int pos = without.indexOf(".");

        int month = Integer.decode(without.substring(0, pos));

        without = without.substring(pos+1);

        int day = Integer.decode(without);

       calendar.set(year,month,day);
       return calendar.getTimeInMillis();
    }

    public static String date(int year,int month, int day) {

        return year + "."+ month+ "."+day;
    }

    public static void recalculate(Currency currency, Pay pay) {

        double coeff = 0;
        Rate rate = pay.getRate();
        switch (currency) {
            case UA:
                coeff = rate.getUaRate();
                break;
            case RUR:
                coeff = rate.getRuRate();
                break;
            case USD:
                coeff = rate.getUsaRate();
                break;
            case EU:
                coeff = rate.getEuRate();
                break;
            case BYR:
                coeff = 1;
                break;
        }
        pay.setBalance(pay.getBalance()/coeff);
        pay.setPay(pay.getPay()/coeff);
    }

    public static String date(long date) {

        long now = Calendar.getInstance().getTimeInMillis();
        long diff = date - now;

        //String years = String.valueOf(diff/1000/60/60/24/365);

        calendar.setTime(new Date(date));

        int year = calendar.get(Calendar.MONTH)+1;

        return "("+ year+"."+ calendar.get(Calendar.YEAR) + ")";
    }

    public static String nowDate(long date) {


        calendar.setTime(new Date(date));

        int year = calendar.get(Calendar.MONTH)+1;

        return calendar.get(Calendar.DATE)+"."+year+"."+ calendar.get(Calendar.YEAR);
    }

    public static String percent(double percent) {

        return percent +" %";
    }

    public static String money(double money) {

        DecimalFormat df = new DecimalFormat("#.##");

        String out =  separate(df.format(money));
        if (out.equals("")) return "0";
        return out;
    }

    public static String money(double money, double exchangeRate) {
        if (exchangeRate == 0) return "";
        return money(money / exchangeRate);
    }

    public static String money(String money, double exchangeRate) {
        if (money == null || money.equals("") || exchangeRate == 0) return "";

        double convert = Double.valueOf(money);
        return money(convert / exchangeRate);
    }

    public static double money(Editable money) {

        String result = collect(money.toString());
        if (result.equals("")) return 0;
        return Double.parseDouble(result);
    }

    public static double money(CharSequence text) {

        return Double.parseDouble(collect(text.toString()));
    }

    public static String collect(String text) {
        assert text != null;

        char [] out = new char[text.length()];
        int i=0;
        for(char letter:text.toCharArray()) {

            if (letter != SEP_CHAR) {
                out[i] = letter;
                i++;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char letter:out) builder.append(letter);

        return builder.toString();
    }

    public static String separate(String text) {
        text = trimFirstZero(text);

        int length = text.length();
        char[] convert = text.toCharArray();

        int div = length/3;
        char[] result = new char[length+div];
        int count=0;
        int j=length+div-1;
        for (int i=length-1;i>=0;i--){
            if (convert[i] == SEP_CHAR) {
                count = 0;
            } else if (convert[i]==POINT) {
                count = 0;
                result[j]=convert[i];
                j--;
            }
            else {
                result[j] = convert[i];
                count++;

                if (count==3) {
                    j--;
                    result[j] = SEP_CHAR;
                    count = 0;
                }
                j--;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char aResult : result) builder.append(aResult);

        return trimFirstZero(builder.toString().trim());
    }

    private static String trimFirstZero(String input) {
        return input.replaceFirst("^ *", "");
    }
}
