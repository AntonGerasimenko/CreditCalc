package by.minsk.pipe.creditcalc.Logic;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Networks.BLRconnect;
import by.minsk.pipe.creditcalc.models.Pay;
import by.minsk.pipe.creditcalc.models.Rate;


/**
 * Created by gerasimenko on 28.08.2015.
 */
public class Actual implements OnActual {

    private final Calendar now = Calendar.getInstance();

    @Override
    public List<Pay> getAllPays() {
        return null;
    }

    @Override
    public Pay getPay() {
        try {
            Pay pay = DBservice.pay().getLast();
            if (pay != null) {
                DBManager.getInstance().getHelper().getCreditDao().refresh(pay.getCredit());
                return pay;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Pay.empty();
    }

    @Override
    public void getRate(final OnRateListener listener) {

        final Rate[] rate = {new Rate()};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                BLRconnect xmLconnect = new BLRconnect();
                rate[0] = xmLconnect.run(new Date());
            }
        });
        thread.start();
        try {
            thread.join();
            listener.getRate(rate[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Rate getRateInDB(){

        return DBservice.rate().getLast();
    }

    public long getNowDate() {

        return  now.getTimeInMillis();
    }


    public boolean hasNoCredits() {

        List list = DBservice.credit().getAll();
        return list.isEmpty();
    }
}
