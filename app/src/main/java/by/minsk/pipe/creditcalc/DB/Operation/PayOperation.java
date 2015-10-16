package by.minsk.pipe.creditcalc.DB.Operation;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.models.Pay;

/**
 * Created by gerasimenko on 04.09.2015.
 */
public class PayOperation {

    private  Dao<Pay,String> dao;
    private QueryBuilder<Pay, String> builder;

    public PayOperation() {
        try {
            dao = DBManager.getInstance().getHelper().getPaysDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  List<Pay> getAll(int idCredit) {
        try {
            builder = dao.queryBuilder();
            builder.where().eq("credit_id",idCredit);

            List<Pay> list = dao.query(builder.prepare());
            refresh(list);

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean put(Pay pay) {
        try {
            dao = DBManager.getInstance().getHelper().getPaysDao();
            dao.create(pay);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  Pay getLast(){

        List <Pay> list = get(1);
        if (list != null && !list.isEmpty()) return list.get(0);

        return Pay.empty();
    }

    public  Pay getLast(int idCredit) {

        builder = dao.queryBuilder();
        try {
            builder
                    .orderBy("id",false)
                    .where()
                    .eq("credit_id",idCredit);

            List<Pay> list = dao.query(builder.prepare());
            refresh(list);

            if (!list.isEmpty()) return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Pay.empty();
    }

    public   List<Pay> get(long quantity){
        try {
            builder = dao.queryBuilder();
            builder.limit(quantity);
            builder.orderBy("id", false);

            return dao.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public int del(int idCredit) {

        List<Pay> pays = getAll(idCredit);

        try {
            return dao.delete(pays);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void refresh(List<Pay> pays) {
        for (Pay pay:pays) {
            try {
                DBManager.getInstance().getHelper().getCreditDao().refresh(pay.getCredit());
                DBManager.getInstance().getHelper().getRateDao().refresh(pay.getRate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
