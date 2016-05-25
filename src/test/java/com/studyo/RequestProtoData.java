package com.studyo;


import com.studyo.econom.EconomProtocol.Shopping;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestProtoData {
    public static void main(String[] args) throws IOException {
        RequestProtoData req = new RequestProtoData();
//        req.simpleGet();
//        req.protoGet(req.getProto());
        req.objectGet(req.getProto());
    }

    public void simpleGet() throws IOException {
        String url = "http://localhost:8080/test";
        String query = "test=hello";
        HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + query).openConnection();
        connection.setRequestMethod("GET");
//        connection.setRequestProperty("Content-Type", "application/x-protobuf");
        InputStream response = connection.getInputStream();
    }

    public void protoGet(Shopping sh) throws IOException {
;
        String url = "http://localhost:8080/proto";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "application/x-protobuf");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        sh.writeTo(connection.getOutputStream());
        InputStream response = connection.getInputStream();
    }

    public void objectGet(Shopping sh) throws IOException {
        String d1 = URLEncoder.encode(new String(sh.toByteArray()), "UTF-8");
        String d2 = URLEncoder.encode(new String(sh.toByteArray()), "UTF-8");
        List<String> data = new ArrayList<>();
        data.add(d1);
        data.add(d2);

        String url = "http://localhost:8080/object?data="+data;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        WritableByteChannel rbc = Channels.newChannel(connection.getOutputStream());
        InputStream response = connection.getInputStream();
    }

    private Shopping getProto() {
        long actionTime = new Date().getTime();
        long date = new Date().getTime();

        String shName = "Bread";
        double price = 10.0;
        double amount = 1;
        double total = price * amount;

        Shopping sh = Shopping.newBuilder()
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

        return sh;
    }
}
