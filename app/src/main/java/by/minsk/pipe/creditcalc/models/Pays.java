package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by gerasimenko on 24.08.2015.
 */
@DatabaseTable(tableName="pays")
public class Pays {
    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(dataType = DataType.INTEGER) private int balance;
    @DatabaseField(dataType = DataType.INTEGER) private int pay;

    public void newRecord(long date,int balance,int pay) {

        this.date = date;
        this.balance = balance;
        this.pay = pay;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
