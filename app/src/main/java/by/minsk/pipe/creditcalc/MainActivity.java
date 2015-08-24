package by.minsk.pipe.creditcalc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.DB.Rate;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dao<Rate, String> rateDao;
        try {
            rateDao = DBManager.getInstance().getHelper().getViolationDao();

            Rate rate = new Rate();
            rate.newRecord("19.08.2015", 16300, 25);

            rateDao.create(rate);




        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
