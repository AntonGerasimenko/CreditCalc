package by.minsk.pipe.creditcalc.Networks;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import by.minsk.pipe.creditcalc.DB.DBservice;
import by.minsk.pipe.creditcalc.models.Rate;

/**
 * Created by gerasimenko on 26.08.2015.
 */
public class BLRconnect {

    private String belRateUrl = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=";
    private String belVatUrl = "http://www.nbrb.by/Services/XmlRefRate.aspx?ondate=";

    private final static String NODE_NAME_RATE = "Currency";
    private final static String NODE_NAME_VAT = "Item";

    private final static String RATE_NAME = "Rate";
    private final static String VAT_VALUE = "Value";

    private final static String USD_ID = "145";
    private final static String EU_ID = "19";

    private Rate rate;

    public BLRconnect() {

    }

    private void loadRate(String date) {
        try {
            String url = belRateUrl + date;
            parsingRate(getXML(url, NODE_NAME_RATE));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadVat(String date){
        try {
            String url = belVatUrl + date;
            parsingVat(getXML(url, NODE_NAME_VAT));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convert(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        return dateFormat.format(date);
    }

    private NodeList getXML(String urlName, String nodeName) throws Exception {

        URL url = new URL(urlName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName(nodeName);
    }

    private void parsingRate(NodeList nodeList) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NamedNodeMap map = node.getAttributes();

            if (map != null && map.getLength()>0) {
                String value = map.item(0).getNodeValue();
                if (value!= null) {
                    switch (value) {
                        case USD_ID:
                            rate.setUsaRate(parseNode(RATE_NAME,node));
                            break;
                        case EU_ID:
                            rate.setEuRate(parseNode(RATE_NAME,node));
                            break;
                    }
                }
            }
        }
    }

    private void parsingVat(NodeList nodeList){

        Node node = nodeList.item(0);
        if (node != null) {

            rate.setVat((int) parseNode(VAT_VALUE,node));

        }
    }

    private double parseNode(String nameNode, Node node) {

        NodeList list = node.getChildNodes();

        if (list!= null){

            for (int i=0;i<list.getLength();i++){
                Node n = list.item(i);
                if (nameNode.equals(n.getNodeName())) {

                    return Double.parseDouble(n.getChildNodes().item(0).getNodeValue());
                }
            }
        }
        return 0;
    }

    public Rate run(Date date) {
        Log.d("RATE","inBackground");

        if (date != null) {
            String sDate = convert(date);
            rate = new Rate();
            rate.setDate(date.getTime());

            loadRate(sDate);
            loadVat(sDate);
        }
        Log.d("RATE", "rate");

        return  onPostExecute(rate);
    }

    protected Rate onPostExecute(Rate rate) {

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
}

