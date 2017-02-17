package fr.univ_reims.julien.healthassistant;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Julien on 14/02/2017.
 */

public class ApiLoginRequest extends ApiHttpRequest {
    private String login;
    private String password;

    public ApiLoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
        this.requestUrlExtension = "login";
        this.urlParameters = "login=" + login + "&password=" + new String(Hex.encodeHex(DigestUtils.sha1(password)));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApiHttpResponse sendRequest() {
        return super.sendRequest();
    }
}
