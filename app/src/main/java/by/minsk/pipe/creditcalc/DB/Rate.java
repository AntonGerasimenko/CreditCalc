package by.minsk.pipe.creditcalc.DB;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerasimenko on 19.08.2015.
 */


@DatabaseTable(tableName="rate")
public class Rate {

    @DatabaseField(generatedId = true) private int id;

    @DatabaseField(dataType = DataType.STRING) private String date;

    @DatabaseField(dataType = DataType.INTEGER) private int rate;

    @DatabaseField(dataType = DataType.INTEGER) private int vat;



    public void newRecord(String date, int rate, int vat){

        this.date = date;
        this.rate = rate;
        this.vat = vat;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getRate() {
        return rate;
    }

    public int getVat() {
        return vat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }
}
