package com.unbxd;

/**
 * Created by albin on 4/29/15.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @author albin
 *
 */

public class GetFromURL {

    private String url;
    private String response;
    private JSONObject jsonResponse;

    public GetFromURL(String url) {
        this.url = url;
        this.response = "";
    }

    public GetFromURL callURL() {
        System.out.println("Requeted URL:" + this.url);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(this.url);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ this.url, e);
        }

        this.response = sb.toString();
        return this;
    }

    public String getResponse() {
        return this.response.trim();
    }

    public JSONObject getJSONResponse() throws JSONException {
        return new JSONObject(this.response);
    }
}