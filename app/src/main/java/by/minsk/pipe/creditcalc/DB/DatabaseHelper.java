package by.minsk.pipe.creditcalc.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by gerasimenko on 19.08.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Dao<Rate, String> rateDao = null;
    private static final String DATABASE_NAME = "CreditCalc.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** * This is called when the database is first created. Usually you should call createTable statements here to create * the tables that will store your data. */
    @Override public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Rate.class);
        } catch (SQLException e) {
            throw new RuntimeException(e); }
    }
    /** * This is called when your application is upgraded and it has a higher version number. This allows you to adjust * the various data to match the new version number. */
    @Override public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Rate.class, true); // after we drop the old databases, we create the new ones onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /** * Returns the Database Access Object (DAO) for our Book class. It will create it or just give the cached * value. */
    public Dao<Rate, String> getViolationDao() throws SQLException {
        if (rateDao == null) {
            rateDao = getDao(Rate.class);
        }
        return rateDao;
    }

    @Override public void close() {
        super.close();
        rateDao = null;
    }
}
