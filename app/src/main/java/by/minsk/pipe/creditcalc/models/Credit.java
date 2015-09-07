package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerasimenko on 28.08.2015.
 */
@DatabaseTable(tableName="Credits")
public class Credit {
    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(dataType = DataType.STRING) private String target;
    @DatabaseField(dataType = DataType.DOUBLE) private double summa;
    @DatabaseField(dataType = DataType.INTEGER) private int currency;
    @DatabaseField(dataType = DataType.DOUBLE) private double interestRate;
    @DatabaseField(dataType = DataType.LONG) private long startData;
    @DatabaseField(dataType = DataType.LONG) private long endData;
    @DatabaseField(dataType = DataType.BOOLEAN) private boolean useVat;
    @DatabaseField(foreign = true) private Credit parent;

    public static Credit empty(){

        return new Credit();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUseVat() {
        return useVat;
    }

    public void setUseRefinRate(boolean useVat) {
        this.useVat = useVat;
    }

    public long getStartData() {
        return startData;
    }

    public void setStartData(long startData) {
        this.startData = startData;
    }

    public long getEndData() {
        return endData;
    }

    public void setEndData(long endData) {
        this.endData = endData;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Credit getParent() {
        return parent;
    }

    public void setParent(Credit parent) {
        this.parent = parent;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
