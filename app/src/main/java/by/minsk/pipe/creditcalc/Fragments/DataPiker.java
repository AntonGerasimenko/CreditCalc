package by.minsk.pipe.creditcalc.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by gerasimenko on 25.09.2015.
 */
public class DataPiker extends DatePickerDialog {
    public DataPiker(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }
}
