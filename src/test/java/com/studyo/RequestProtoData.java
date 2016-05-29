package com.studyo;


import com.studyo.domain.CSRFToken;
import com.studyo.econom.EconomProtocol;
import com.studyo.econom.EconomProtocol.Shopping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestProtoData {
    public static final String CSRF_TOKEN = "CSRF-TOKEN";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String COOKIE = "Cookie";
    private static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    public static void main(String[] args) throws IOException, URISyntaxException {
        RequestProtoData req = new RequestProtoData();
//        CSRFToken token0 = req.requestToken();
//        CSRFToken token1 = req.login(token0);
        req.simpleGet(null);
        req.protoGet(req.getProto(), null);
//        req.objectGet(req.getProto());
    }

    public void simpleGet(CSRFToken token) throws IOException, URISyntaxException {
        String url = "http://localhost:8080/test";
        String query = "?test=test";//+"&_csrf=" + token.getToken();
        HttpURLConnection connection = (HttpURLConnection) new URL(url + query).openConnection();
        connection.setRequestMethod("GET");

        String csrfLine = getResponseContent(connection.getInputStream());
        System.out.println("\n\n" + csrfLine);
        System.out.println("SimpleGet: " + connection.getResponseMessage());
    }

    public CSRFToken login(CSRFToken token) throws IOException, URISyntaxException {

        String url = "http://localhost:8080/login";
        String params = "?username=econom_beta&password=$2a$10$pR9KzK4u/0sGJ//cBPqA9.1bFBuyN15HViubTpyrHWgM7EfE0qFve&_csrf=" + token.getToken();
        HttpURLConnection connection = (HttpURLConnection) new URL(url + params).openConnection();
        addCSRF(connection, token);

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");

        String csrfLine = getResponseContent(connection.getInputStream());
        System.out.println("\n\n" + csrfLine);

        StringBuilder builder = new StringBuilder();
        builder.append(connection.getResponseCode())
                .append(" ")
                .append(connection.getResponseMessage())
                .append("\n");
        System.out.println("Login: " + builder.toString());

        List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
        String sessId = cookies != null ? cookies.get(0).replaceAll("JSESSIONID=", "").replaceAll("; .*", "") : "";
        return new CSRFToken(sessId, token.getToken());
    }

    private void addCSRF(HttpURLConnection connection, CSRFToken token) throws URISyntaxException {
        connection.setRequestProperty(CSRF_TOKEN, token.getToken());
        connection.setRequestProperty("Origin", "http://localhost:8080");
        connection.setRequestProperty("Referer", "http://localhost:8080/login");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        HttpCookie cookieSession = new HttpCookie(JSESSIONID, token.getJSESSIONID());
        cookieSession.setDomain("localhost");
        cookieSession.setPath("/");
        cookieSession.setMaxAge(3600);

        connection.setRequestProperty(COOKIE, cookieSession.toString());
    }

    private CSRFToken requestToken() throws IOException {
        String url = "http://localhost:8080/hello";
        String params = "";

        HttpURLConnection connection = (HttpURLConnection) new URL(url + params).openConnection();
        connection.setRequestMethod("GET");

        String csrfLine = getResponseContent(connection.getInputStream());

        if (csrfLine.isEmpty()) return new CSRFToken();

        String sessId = connection.getHeaderFields().get("Set-Cookie").get(0).replaceAll("JSESSIONID=", "").replaceAll("; .*", "");
        String token = csrfLine.replaceAll("\"", "").replaceAll(".*_csrf value=", "").replaceAll(" />.*", "");
        CSRFToken csrfToken = new CSRFToken(sessId, token);

        System.out.println("TOKEN  : " + token);
        System.out.println("SESSID : " + sessId);
        System.out.println();

        return csrfToken;
    }

    public void protoGet(Shopping sh, CSRFToken token) throws IOException, URISyntaxException {
        String url = "http://localhost:8080/proto";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/x-protobuf");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
//        addCSRF(connection, token);

        sh.writeTo(connection.getOutputStream());

        String csrfLine = getResponseContent(connection.getInputStream());
        System.out.println("\n\n" + csrfLine);

        System.out.println("Proto: " + connection.getResponseMessage());
    }

    public void objectGet(Shopping sh) throws IOException {
        String d1 = URLEncoder.encode(new String(sh.toByteArray()), "UTF-8");
        String d2 = URLEncoder.encode(new String(sh.toByteArray()), "UTF-8");
        List<String> data = new ArrayList<>();
        data.add(d1);
        data.add(d2);

        String url = "http://localhost:8080/object?data=" + data;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        WritableByteChannel rbc = Channels.newChannel(connection.getOutputStream());
        InputStream response = connection.getInputStream();
    }

    private String getResponseContent(InputStream in) throws IOException {
        String line, csrfLine = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.contains("_csrf")) csrfLine = line;
        }
        return csrfLine;
    }

    private Shopping getProto() {
        long actionTime = new Date().getTime();
        long date = new Date().getTime();

        String shName = "Bread";
        double price = 10.0;
        double amount = 1;
        double total = price * amount;

        EconomProtocol.Buying buying = EconomProtocol.Buying.newBuilder()
                .setAction(0)
                .setActionTime(actionTime)
                .setOwnid(actionTime)
                .setName(shName)
                .setShop("Market")
                .setPrice(price)
                .setAmount(amount)
                .setTotal(total)
                .setDate(date)
                .setGroupName(1)
                .build();

        List<EconomProtocol.Buying> buyings = new ArrayList();
        buyings.add(buying);

        Shopping.Builder sh = Shopping.newBuilder();
        sh.addAllBuying(buyings);

        return sh.build();
    }
}
