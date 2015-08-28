package by.minsk.pipe.creditcalc.DB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import by.minsk.pipe.creditcalc.models.Pays;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 26.08.2015.
 */
public class DBservice {

    public static void putRate(Rate rate) {
        try {
            Dao<Rate,String> daoRate = DBManager.getInstance().getHelper().getRateDao();
            daoRate.create(rate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Rate getLastRate() {

        List<Rate> list = getRates(1);
        if (list!= null) return list.get(0);

        return null;
    }

    public static Pays getLastPay(){

        List <Pays> list = getPays(1);
        if (list!=null) return list.get(0);

        return null;
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

    public static  List<Pays> getPays(long quantity){
        try {
            Dao<Pays,String> daoPays = DBManager.getInstance().getHelper().getPaysDao();
            QueryBuilder<Pays, String> builder = daoPays.queryBuilder();
            builder.limit(quantity);
            builder.orderBy("id", false);

            return daoPays.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createPayment(double sum, double overpayment) {

        Pays lastPay = getLastPay();
        if (lastPay != null) {

            double lastBalance = lastPay.getBalance();
            double lastOverpayment = lastPay.getOverpayment();

            Pays pays = new Pays();
            pays.newRecord(lastBalance - sum,sum,lastOverpayment + overpayment);

            loadRecord(pays);
        }
    }

    public static void loadRecord(Pays pays) {
        try {
            Dao<Pays,String> dao =  DBManager.getInstance().getHelper().getPaysDao();
            dao.create(pays);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
