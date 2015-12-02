package by.minsk.pipe.creditcalc.Networks;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.minsk.pipe.creditcalc.MVP.models.Rate;

/**
 * Created by gerasimenko on 26.08.2015.
 */
public final class BLRconnect extends XMLconnect {


    private final static String NODE_NAME_RATE = "Currency";
    private final static String NODE_NAME_VAT = "Item";

    private final static String RATE_NAME = "Rate";
    private final static String VAT_VALUE = "Value";

    private final static String USD_ID = "145";
    private final static String EU_ID = "19";
    private final static String RU_ID = "190";
    private final static String UA_ID = "224";

    public BLRconnect() {
        super();
        urlCurrRate = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=";
        urlRefinRate = "http://www.nbrb.by/Services/XmlRefRate.aspx?ondate=";
    }

    @Override
    protected void parsingCurrRate(NodeList nodeList) {

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
                        case RU_ID:
                            rate.setRuRate(parseNode(RATE_NAME,node));
                            break;
                        case UA_ID:
                            rate.setUaRate(parseNode(RATE_NAME,node));
                            break;
                    }
                }
            }
        }
    }

    @Override
    protected void parsingRefinRate(NodeList nodeList){

        Node node = nodeList.item(0);
        if (node != null) {

            rate.setVat((int) parseNode(VAT_VALUE,node));

        }
    }

    @Override
    protected String getNodeNameRate() {
        return NODE_NAME_RATE;
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
}

