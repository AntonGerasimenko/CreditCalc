package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by gerasimenko on 24.08.2015.
 */
@DatabaseTable(tableName="pays")
public class Pay {
    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(foreign = true) private Credit credit;
    @DatabaseField(foreign = true) private Rate rate;

    @DatabaseField(dataType = DataType.DOUBLE) private double balance;
    @DatabaseField(dataType = DataType.DOUBLE) private double pay;
    @DatabaseField(dataType = DataType.DOUBLE) private double overpayment;

    public static final Pay EMPTY;

    static {
        EMPTY = new Pay();
        EMPTY.credit = Credit.empty();
        EMPTY.rate = Rate.empty();
    }

    public void newRecord(double balance,double pay,double overpayment) {

        this.balance = balance;
        this.pay = pay;
        this.overpayment = overpayment;
        date = new Date().getTime();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getPay() {
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

    public double getOverpayment() {
        return overpayment;
    }

    public void setOverpayment(double overpayment) {
        this.overpayment = overpayment;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public  boolean isEmpty() {
        return  this.equals(EMPTY);
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
