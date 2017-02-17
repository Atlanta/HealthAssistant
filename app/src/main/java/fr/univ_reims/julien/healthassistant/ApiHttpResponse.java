package fr.univ_reims.julien.healthassistant;

/**
 * Created by Julien on 14/02/2017.
 */

public class ApiHttpResponse {
    private int statusCode;
    private String responseBody;

    public ApiHttpResponse() {
        this.statusCode = 0;
        this.responseBody = null;
    }

    public ApiHttpResponse(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
