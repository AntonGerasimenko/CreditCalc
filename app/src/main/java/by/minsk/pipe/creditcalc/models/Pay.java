package by.minsk.pipe.creditcalc.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.NativeUuidType;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gerasimenko on 24.08.2015.
 */
@DatabaseTable(tableName="pays")
public class Pay implements Cloneable{
    @DatabaseField(generatedId = true) private int id;
    @DatabaseField(dataType = DataType.LONG) private long date;

    @DatabaseField(foreign = true) private Credit credit;
    @DatabaseField(foreign = true) private Rate rate;

    @DatabaseField(dataType = DataType.DOUBLE) private double balance;
    @DatabaseField(dataType = DataType.DOUBLE) private double pay;
    @DatabaseField(dataType = DataType.DOUBLE) private double overpayment;

    @DatabaseField(dataType = DataType.DOUBLE) private double interestPay;

    public double getDeptPay() {
        return deptPay;
    }

    public void setDeptPay(double deptPay) {
        this.deptPay = deptPay;
    }

    @DatabaseField(dataType = DataType.DOUBLE) private double deptPay;

    private static final Pay EMPTY;

    static {
        EMPTY = new Pay();
        EMPTY.credit = Credit.empty();
        EMPTY.rate = Rate.empty();
    }

    public Pay() {
    }

    public static Pay empty() {

        EMPTY.balance = 0;
        EMPTY.pay = 0;
        EMPTY.overpayment = 0;
        EMPTY.id = 0;
        EMPTY.date = 0;

        return EMPTY;
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

    public void setPay(double pay) {
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

    public double getInterestPay() {
        return interestPay;
    }

    public void setInterestPay(double interestPay) {
        this.interestPay = interestPay;
    }

    @Override
    public Pay clone() throws CloneNotSupportedException {

        Pay out = new Pay();
        out.setCredit(credit);
        out.setRate(rate);

        out.setBalance(balance);
        out.setDate(date);
        out.setId(id);
        out.setDeptPay(deptPay);
        out.setPay(pay);
        out.setInterestPay(interestPay);

        return out;
    }
}
