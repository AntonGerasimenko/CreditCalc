package by.minsk.pipe.creditcalc.Logic;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import by.minsk.pipe.creditcalc.DB.DBManager;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.Networks.XMLconnect;
import by.minsk.pipe.creditcalc.models.Pays;
import by.minsk.pipe.creditcalc.models.Rate;


/**
 * Created by gerasimenko on 28.08.2015.
 */
public class Actual implements OnActual {


    @Override
    public List<Pays> getAllPays() {
        return null;
    }

    @Override
    public Pays getPay() {
        try {
            Pays pays = DBservice.getLastPay();
            if (pays!= null) {
                DBManager.getInstance().getHelper().getLendingTermsDao().refresh(pays.getLendigTerms());
            }
            return pays;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Pays.empty();
    }

    @Override
    public void getRate(OnRateListener listener) {

        Calendar calendar = Calendar.getInstance();
        new XMLconnect(listener).execute(calendar.getTime());
    }

    public Rate getRateInDB(){

        return DBservice.getLastRate();
    }

}
