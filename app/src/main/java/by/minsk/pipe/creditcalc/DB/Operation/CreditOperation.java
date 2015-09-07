package by.minsk.pipe.creditcalc.DB.Operation;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.models.Credit;

/**
 * Created by gerasimenko on 04.09.2015.
 */
public class CreditOperation {

    private Dao<Credit,String> dao;
    private QueryBuilder<Credit, String> builder;

    public CreditOperation() {
        try {
            dao = DBManager.getInstance().getHelper().getCreditDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Credit get(int id) {
        try {
            builder = dao.queryBuilder();
            builder.where().eq("id", id);

            List<Credit> list = dao.query(builder.prepare());
            if (list.isEmpty()) return Credit.empty();

            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Credit.empty();
    }

    public  boolean create(Credit credit) {
        if (credit == null) return false;
        try {
            dao.create(credit);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public  List<Credit> getAll(){
        try {
            builder = dao.queryBuilder();

            builder.limit(dao.countOf());
            builder.orderBy("id", false);
            return dao.query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public int del(Credit credit) {

        DBservice.pay().del(credit.getId());

        try {

            return dao.delete(credit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
