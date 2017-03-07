package fr.univ_reims.julien.healthassistant;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Julien on 14/02/2017.
 */

public abstract class ApiHttpRequest {
    public static final String API_URL = "http://46.101.155.73/julien/HealthApp/";
    protected String requestUrlExtension;
    protected String urlParameters;

    public ApiHttpResponse sendRequest() {
        assert requestUrlExtension != null;

        ApiHttpResponse response = new ApiHttpResponse();

        URL url = null;
        try {
            url = new URL(API_URL + requestUrlExtension);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        connection.setDoOutput(true);

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            if(urlParameters != null)
                out.writeBytes(urlParameters);
            out.flush();
            out.close();
        }
        catch (ConnectException e) {
            e.printStackTrace();
            response.setStatusCode(504);
            response.setResponseBody("Serveur inaccessible");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        try {
            response.setStatusCode(connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        try {
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }

        response.setResponseBody(responseBuffer.toString());

        return response;
    }

}
