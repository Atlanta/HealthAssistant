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
    public static final String API_URL = "http://10.0.3.2/";
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

        /*URL url = null;
        HttpURLConnection connection = null;
        BufferedReader in = null;
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        ApiHttpResponse response = null;

        url = new URL(API_URL + apiUrlExtension);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(urlParameters);
        out.flush();
        out.close();

        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }

        in.close();

        response = new ApiHttpResponse(connection.getResponseCode(), responseBuffer.toString());

        return response;*/
    }

    /*public static void login(ApiRequestResponse response, String login, String password) throws Exception {
        URL url = new URL(API_URL + "login");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        String urlParameters = "login=" + login + "&password=" + new String(Hex.encodeHex(DigestUtils.sha1(password)));

        connection.setDoOutput(true);
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.writeBytes(urlParameters);
            out.flush();
            out.close();
        }
        catch (ConnectException e) {
            e.printStackTrace();
            response.setReturnCode(400);
            response.setReturnMessage("Can't access server");
        }

        response.setReturnCode(connection.getResponseCode());

        if(response.getReturnCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer responseBuffer = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }

            in.close();

            if (responseBuffer.equals("error")) {
            }

            response.setReturnMessage(responseBuffer.toString());
        }
        else if(response.getReturnCode() == 400) {
            response.setReturnMessage("Wrong credidentials");
        }
        else {
            response.setReturnMessage("Erreur serveur");
        }
    }*/
}
