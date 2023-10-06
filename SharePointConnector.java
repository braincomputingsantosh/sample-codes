package com.example.mulesoft.connectors;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SharePointConnector {

    private String sharePointUrl;
    private String username;
    private String password;

    public SharePointConnector(String sharePointUrl, String username, String password) {
        this.sharePointUrl = sharePointUrl;
        this.username = username;
        this.password = password;
    }

    public byte[] getFile(String fileRelativeUrl) throws Exception {
        String fullUrl = sharePointUrl + "/_api/web/GetFileByServerRelativeUrl('" + fileRelativeUrl + "')/$value";

        URL url = new URL(fullUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/octet-stream");

        String userCredentials = username + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        conn.setRequestProperty("Authorization", basicAuth);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            return conn.getInputStream().readAllBytes();
        } else {
            throw new Exception("Failed to retrieve file from SharePoint. HTTP error code: " + responseCode);
        }
    }
}
