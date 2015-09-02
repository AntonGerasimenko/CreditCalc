package by.minsk.pipe.creditcalc.DB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.minsk.pipe.creditcalc.models.LendingTerms;
import by.minsk.pipe.creditcalc.models.Pay;
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

    public static boolean putPay(Pay pay) {

        if (!putLendingTerms(pay.getLendingTerms())) return  false;

        try {
            Dao<Pay,String> daoPay = DBManager.getInstance().getHelper().getPaysDao();
            daoPay.create(pay);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean putLendingTerms(LendingTerms lendingTerms) {
        if (lendingTerms == null) return false;
        try {
            Dao<LendingTerms,String> daoRate = DBManager.getInstance().getHelper().getLendingTermsDao();
            daoRate.create(lendingTerms);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Rate getLastRate() {

        List<Rate> list = getRates(1);
        if (list!= null && !list.isEmpty()) return list.get(0);

        return null;
    }

    public static Pay getLastPay(){

        List <Pay> list = getPays(1);
        if (list != null && !list.isEmpty()) return list.get(0);

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

    public static  List<Pay> getPays(long quantity){
        try {
            Dao<Pay,String> daoPays = DBManager.getInstance().getHelper().getPaysDao();
            QueryBuilder<Pay, String> builder = daoPays.queryBuilder();
            builder.limit(quantity);
            builder.orderBy("id", false);

            return daoPays.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createPayment(double sum, double overpayment) {

        Pay lastPay = getLastPay();
        if (lastPay != null) {

            double lastBalance = lastPay.getBalance();
            double lastOverpayment = lastPay.getOverpayment();

            Pay pay = new Pay();
            pay.newRecord(lastBalance - sum,sum,lastOverpayment + overpayment);

            loadRecord(pay);
        }
    }

    public static void loadRecord(Pay pay) {
        try {
            Dao<Pay,String> dao =  DBManager.getInstance().getHelper().getPaysDao();
            dao.create(pay);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getCredits(){
        try {
            Dao<Pay,String> dao = DBManager.getInstance().getHelper().getPaysDao();
            QueryBuilder<Pay, String> builder = dao.queryBuilder();

            builder.limit(dao.countOf());
            builder.orderBy("id", false);
            List <Pay> list = dao.query(builder.prepare());

            List<String> out = new ArrayList<>();
            for(Pay item:list) {
                String target = item.getTarget();
                if (target != null && !target.isEmpty())  out.add(item.getTarget());
            }
            return out;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
