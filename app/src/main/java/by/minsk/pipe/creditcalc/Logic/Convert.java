package by.minsk.pipe.creditcalc.Logic;

import android.provider.ContactsContract;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by gerasimenko on 03.09.2015.
 */
public class Convert {

    private static Calendar calendar = Calendar.getInstance();

    public static String date(long date) {

        calendar.setTime(new Date(date));
        return calendar.get(Calendar.MONTH)+"."+ calendar.get(Calendar.YEAR);
    }

    public static String percent(double percent) {

        return percent +" %";
    }

    public static String money(double money) {



        return money + " ";
    }
}
