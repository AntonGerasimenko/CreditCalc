package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerasimenko on 28.08.2015.
 */
@DatabaseTable(tableName="Lending_terms")
public class LendigTerms {
    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(dataType = DataType.DOUBLE) private double interestRate;
    @DatabaseField(dataType = DataType.LONG) private long  startLendingData;
    @DatabaseField(dataType = DataType.LONG) private long  endLendingData;

    @DatabaseField(dataType = DataType.BOOLEAN) private boolean useVat;

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

    public void setUseVat(boolean useVat) {
        this.useVat = useVat;
    }

    public long getStartLendingData() {
        return startLendingData;
    }

    public void setStartLendingData(long startLendingData) {
        this.startLendingData = startLendingData;
    }

    public long getEndLendingData() {
        return endLendingData;
    }

    public void setEndLendingData(long endLendingData) {
        this.endLendingData = endLendingData;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
