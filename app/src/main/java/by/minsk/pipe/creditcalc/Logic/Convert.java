package by.minsk.pipe.creditcalc.Logic;

import android.provider.ContactsContract;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gerasimenko on 03.09.2015.
 */
public class Convert {

    private static Calendar calendar = Calendar.getInstance();

    public static String date(long date) {

        long now = Calendar.getInstance().getTimeInMillis();
        long diff = date - now;

        String years = String.valueOf(diff/1000/60/60/24/365);

        calendar.setTime(new Date(date));

        int year = calendar.get(Calendar.MONTH)+1;

        return years +" ("+ year+"."+ calendar.get(Calendar.YEAR) + ")";
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

        if (money<0) money*=-1;
        DecimalFormat df = new DecimalFormat("#.##");

        return df.format(money);
    }


}
