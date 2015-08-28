package by.minsk.pipe.creditcalc.Logic;

/**
 * Created by gerasimenko on 28.08.2015.
 */
public interface OnPay {

    void makePayment(double sum);
    double getBalance();

}
