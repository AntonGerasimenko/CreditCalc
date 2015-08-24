package by.minsk.pipe.creditcalc;

import android.app.Application;

import by.minsk.pipe.creditcalc.DB.DBManager;

/**
 * Created by gerasimenko on 19.08.2015.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.getInstance().release();
    }
}
