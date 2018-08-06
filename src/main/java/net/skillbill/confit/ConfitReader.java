package net.skillbill.confit;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ConfitReader {
    private int status;
    private InputStream response;

    private ConfitReader(int status, InputStream response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public InputStream getResponse() {
        return response;
    }

    private static String buildHost() {
        String proto = System.getenv("CONFIT_INSECURE") == null ? "https://" : "http://";
        String hostname = System.getenv("CONFIT_HOST");
        String port = System.getenv("CONFIT_PORT");
        return proto + (hostname == null ? "confit.skillbill.net" : hostname) + ":" + (port == null ? "443" : port);
    }

    public static ConfitReader getConfByPath(String uuid, String path, String ref, String secret) throws IOException {
        return ConfitReader.getConf(buildHost() + "/api/repo/" + uuid + "/path/" + path + (ref != null ? "?ref=" + ref : ""), secret);
    }

    public static ConfitReader getConfByAlias(String uuid, String alias, String ref, String secret) throws IOException {
        return ConfitReader.getConf(buildHost() + "/api/repo/" + uuid + "/alias/" + alias + (ref != null ? "?ref=" + ref : ""), secret);
    }

    private static ConfitReader getConf(String urlString, String secret) throws IOException {
        URLConnection connection = new URL(urlString).openConnection();
        if(secret != null) {
            connection.setRequestProperty("Authorization", "secret " + secret);
        }
        return new ConfitReader(((HttpURLConnection) connection).getResponseCode(), connection.getInputStream());
    }
}
