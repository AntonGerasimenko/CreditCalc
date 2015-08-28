package by.minsk.pipe.creditcalc.DB;

import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 26.08.2015.
 */
public class DBservice {

    public static boolean  compareLastRecRate(@NonNull Rate rate) {

        List<Rate> list = getLastRate(1);
        if (list == null || list.isEmpty()) return false;
        Rate lastRate = list.get(0);
        Log.d("DB", "Last Rate: " + lastRate.toString());
        return rate.equals(lastRate);
    }

    public static void putRate(Rate rate) {
        try {
            Dao<Rate,String> daoRate = DBManager.getInstance().getHelper().getRateDao();
            daoRate.create(rate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Rate> getLastRate(long quantity) {
        try {
            Dao<Rate,String> daoRate = DBManager.getInstance().getHelper().getRateDao();
            QueryBuilder<Rate, String> builder = daoRate.queryBuilder();
            builder.limit(quantity);
            builder.orderBy("id", false);

            return daoRate.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
