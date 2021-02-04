package org.simbirsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlPageLoader {
    private String url;

    public HtmlPageLoader(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String load() throws IOException {
        URL url = new URL(getUrl());
        URLConnection connection = url.openConnection();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String data;

            while ((data = reader.readLine()) != null) {
                sb.append(data);
            }
        }

        return sb.toString();
    }
}
