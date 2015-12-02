package by.minsk.pipe.creditcalc.DB.Operation;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 04.09.2015.
 */
public class RateOperation {

    public  void put(Rate rate) {
        try {
            Dao<Rate,String> daoRate = DBManager.getInstance().getHelper().getRateDao();
            daoRate.create(rate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Rate getLast() {

        List<Rate> list = getRates(1);
        if (list!= null && !list.isEmpty()) return list.get(0);

        return Rate.empty();
    }

    public static List<Rate> getRates(long quantity) {
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
