package com.example.sharepoint;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SPRestService {
    private final String siteUrl;
    private final String username;
    private final String password;

    public SPRestService(String siteUrl, String username, String password) {
        this.siteUrl = siteUrl;
        this.username = username;
        this.password = password;
    }

    public InputStream downloadFile(String fileRelativeUrl) throws Exception {
        String endpoint = "/_api/web/GetFileByServerRelativeUrl('" + fileRelativeUrl + "')/$value";
        String urlStr = this.siteUrl + endpoint;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String userCredentials = this.username + ":" + this.password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        connection.setRequestProperty("Authorization", basicAuth);

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/octet-stream");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return connection.getInputStream();
        } else {
            throw new Exception("Failed to download the file. HTTP error code: " + responseCode);
        }
    }
}
