package com.studyo;


import com.studyo.econom.EconomProtocol;
import org.json.JSONObject;

import java.util.Date;

public class CompareJsonProtobuf {
    private enum ACTION {INSERT, UPDATE, DELETE}

    public static void main(String[] args) {

        String grName = "Food";
        long actionTime = new Date().getTime();
        long date = new Date().getTime();

        String shName = "Bread";
        double price = 10.0;
        double amount = 1;
        double total = price * amount;

        EconomProtocol.Shopping sh = EconomProtocol.Shopping.newBuilder()
                .setAction(ACTION.INSERT.ordinal())
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

        JSONObject jso = new JSONObject();
        jso.put("action", ACTION.INSERT.ordinal());
        jso.put("actionTime", actionTime);
        jso.put("name", shName);
        jso.put("shop", "Market");
        jso.put("price", price);
        jso.put("amount", amount);
        jso.put("total", total);
        jso.put("date", date);
        jso.put("groupId", 1);

        JSONObject jso2 = new JSONObject();
        jso2.put("proto", sh);

        int l1 = sh.toByteArray().length;
        int l2 = jso.toString().getBytes().length;
        int l3 = jso2.toString().getBytes().length;

        System.out.println("Length:" +
                "\nProtobuf:  " + l1 +
                "\nJson:      " + l2 +
                "\nJsonProto: " + l3
        );
    }
}
