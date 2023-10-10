package com.example.sharepoint;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SharePointRestService {
    private final String siteUrl;
    private final String username;
    private final String password;

    public SharePointRestService(String siteUrl, String username, String password) {
        this.siteUrl = siteUrl;
        this.username = username;
        this.password = password;
    }

    public InputStream getRequest(String endpoint) throws Exception {
        String urlStr = this.siteUrl + endpoint;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set up basic authentication header
        String userCredentials = this.username + ":" + this.password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        connection.setRequestProperty("Authorization", basicAuth);

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json;odata=verbose");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return connection.getInputStream();
        } else {
            throw new Exception("Failed to make the request. HTTP error code: " + responseCode);
        }
    }
}
