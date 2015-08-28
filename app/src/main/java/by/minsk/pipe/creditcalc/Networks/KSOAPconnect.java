package by.minsk.pipe.creditcalc.Networks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.client.HttpResponseException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by gerasimenko on 24.08.2015.
 */
public class KSOAPconnect  extends AsyncTask<Void,Void,Void>{


    private static final String SOAP_ACTION = "http://www.nbrb.by/ExRatesDaily";
    private static final String METHOD_NAME = "ExRatesDaily";
    private static final String NAMESPACE = "http://www.nbrb.by/";
    private static final String URL = "http://www.nbrb.by/Services/ExRates.asmx";



    private SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);;



    @Override
    protected Void doInBackground(Void... params) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("onDate","08/26/2015" );

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(URL);


        try {
            httpTransport.call(SOAP_ACTION, envelope);
        } catch (IOException | XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  //send request
        SoapObject result = null;
        try {
            result = (SoapObject)envelope.getResponse();
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    return null;
    }

    void dls(){

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("iTopN", "5");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //send request
        SoapObject result = null;
        try {
            result = (SoapObject)envelope.getResponse();
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("App", "" + result.getProperty(1).toString());
        String response = result.getProperty(1).toString();

    }
}
