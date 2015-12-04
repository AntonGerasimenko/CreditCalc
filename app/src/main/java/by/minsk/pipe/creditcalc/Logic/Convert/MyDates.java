package by.minsk.pipe.creditcalc.Logic.Convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by gerasimenko on 04.12.2015.
 */
public class MyDates {

    private final static  String DATE_PATTERN = "'('yyyy/MM/dd')'";

    public static long date(String date) {
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            java.util.Date parsDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return
    }

    public static String get(long date) {
        if (date == 0) return "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);

        return format.format(date);
    }

}
