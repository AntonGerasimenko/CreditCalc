package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerasimenko on 19.08.2015.
 */


@DatabaseTable(tableName="rate")
public class Rate {

    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(dataType = DataType.DOUBLE) private double usaRate;
    @DatabaseField(dataType = DataType.DOUBLE) private double euRate;

    @DatabaseField(dataType = DataType.DOUBLE) private double vat;

    public void newRecord(long date, double usaRate, double euRate, double vat){

        this.date = date;
        this.usaRate = usaRate;
        this.euRate = euRate;
        this.vat = vat;
    }

    public double getUsaRate() {
        return usaRate;
    }

    public void setUsaRate(double usaRate) {
        this.usaRate = usaRate;
    }

    public double getEuRate() {
        return euRate;
    }

    public void setEuRate(double euRate) {
        this.euRate = euRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getDate() {
        return date;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Rate) {
            if (((Rate) o).usaRate == usaRate) {
                if (((Rate) o).euRate == euRate) {
                    if (((Rate) o).vat == vat) return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "USD = "+usaRate+" EU = "+euRate+" VAT = "+vat;
    }

    public static Rate empty(){


        return new Rate();
    }
}
