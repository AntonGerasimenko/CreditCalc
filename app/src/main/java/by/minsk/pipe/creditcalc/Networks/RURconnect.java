package by.minsk.pipe.creditcalc.Networks;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by gerasimenko on 25.09.2015.
 */
public final class RURconnect extends XMLconnect {

    private final static String NODE_NAME_RATE = "Valute";
    private final static String USD_ID = "145";
    private final static String EU_ID = "19";

    public RURconnect() {
        urlCurrRate = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
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
                            //rate.setUsaRate(parseNode(RATE_NAME,node));
                            break;
                        case EU_ID:
                           // rate.setEuRate(parseNode(RATE_NAME,node));
                            break;
                    }
                }
            }
        }
    }

    @Override
    protected void parsingRefinRate(NodeList nodeList) {

    }

    @Override
    protected String getNodeNameRate() {
        return NODE_NAME_RATE;
    }


}
