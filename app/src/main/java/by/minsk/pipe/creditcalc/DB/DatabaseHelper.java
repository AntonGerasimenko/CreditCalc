package by.minsk.pipe.creditcalc.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import by.minsk.pipe.creditcalc.MVP.models.Credit;
import by.minsk.pipe.creditcalc.MVP.models.Pay;
import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 19.08.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Dao<Rate, String> rateDao = null;
    private Dao<Pay, String> paysDao = null;
    private Dao<Credit, String> creditDao = null;

    private static final String DATABASE_NAME = "CreditCalc.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** * This is called when the database is first created. Usually you should call createTable statements here to create * the tables that will store your data. */
    @Override public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Rate.class);
            TableUtils.createTable(connectionSource, Pay.class);
            TableUtils.createTable(connectionSource, Credit.class);
        } catch (SQLException e) {
            throw new RuntimeException(e); }
    }
    /** * This is called when your application is upgraded and it has a higher version number. This allows you to adjust * the various data to match the new version number. */
    @Override public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Rate.class, true);
            TableUtils.dropTable(connectionSource, Pay.class, true);
            TableUtils.dropTable(connectionSource, Credit.class, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void close() {
        super.close();
        rateDao = null;
        paysDao = null;
        creditDao = null;
    }

    public Dao<Rate, String> getRateDao() throws SQLException {
        if (rateDao == null) {
            rateDao = getDao(Rate.class);
        }
        return rateDao;
    }

    public Dao<Pay,String> getPaysDao() throws SQLException{
        if (paysDao == null) {

            paysDao = getDao(Pay.class);
        }
        return paysDao;
    }

    public Dao<Credit,String> getCreditDao() throws SQLException{
        if (creditDao == null) {

            creditDao = getDao(Credit.class);
        }
        return creditDao;
    }
}
