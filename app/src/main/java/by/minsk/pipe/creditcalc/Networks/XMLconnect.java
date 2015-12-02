package by.minsk.pipe.creditcalc.Networks;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.MVP.models.Currency;
import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 25.09.2015.
 */
public abstract class XMLconnect implements Connect  {

    protected  Rate rate;

    protected  String urlCurrRate;
    protected  String urlRefinRate;

    public static Connect factory(Currency currency){
        switch (currency) {
            case BYR:
                return new BLRconnect();
            case RUR:
                return new RURconnect();
        }

        return new RURconnect();
    }


    @Override
    public Rate getRate(Date date) {
        if (date != null) {
            String sDate = convert(date);
            rate = new Rate();
            rate.setDate(date.getTime());

            loadRate(sDate);
            //loadReginRate(sDate);
        }
        Log.d("RATE", "rate");

        return compareLastRate(rate);
    }

    protected NodeList getXML(String urlName, String nodeName) throws Exception {

        URL url = new URL(urlName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName(nodeName);
    }

    private String convert(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }

    private void loadRate(String date) {
        try {
            String url = urlCurrRate + date;
            NodeList list = getXML(url, getNodeNameRate());
            parsingCurrRate(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadReginRate(String date) {
        try {
            String url = urlCurrRate + date;
            //parsingRefinRate(getXML(url, /*getNodeNameRate()*/));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Rate compareLastRate(Rate rate) {

        Rate lastRate = DBservice.rate().getLast();
        if (lastRate == null && rate == null) {
            return Rate.empty();
        } else if (lastRate != null && rate == null) {
            return lastRate;
        } else if (lastRate == null && rate != null) {
            return rate;
        } else {
            if (!rate.equals(lastRate)) {
                DBservice.rate().put(rate);
                Log.d("Connection","Put to DB: " + rate.toString());
            }else Log.d("Connection", "Not new data: " + rate.toString());
            return rate;
        }
    }

    protected abstract void parsingCurrRate(NodeList nodeList);
    protected abstract void parsingRefinRate(NodeList nodeList);
    protected abstract String getNodeNameRate();
}
