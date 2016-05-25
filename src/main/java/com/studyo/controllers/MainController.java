package com.studyo.controllers;

import com.google.protobuf.InvalidProtocolBufferException;
import com.studyo.econom.EconomProtocol;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class MainController {
    @RequestMapping("/")
    public String index(Model model) {
        return "hello";
    }

    @RequestMapping(value = "/proto", method = RequestMethod.POST)
//    public void insert(@RequestBody byte[] str)
    public void proto(@RequestBody EconomProtocol.Shopping sh)
            throws InvalidProtocolBufferException, UnsupportedEncodingException {
        System.out.println(sh.getName());
    }

    @RequestMapping(value = "/object", method = RequestMethod.POST)
    public void object(@RequestBody List<String> sh)
            throws InvalidProtocolBufferException, UnsupportedEncodingException {
        System.out.println();
//        System.out.println(sh.getName());
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(@RequestParam(value = "test", required = true) String str) {
        System.out.println(str);
    }
}

/*
URL myURL = new URL(serviceURL);
HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
String userCredentials = "username:password";
String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
myURLConnection.setRequestProperty ("Authorization", basicAuth);
myURLConnection.setRequestMethod("POST");
myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
myURLConnection.setRequestProperty("Content-Length", "" + postData.getBytes().length);
myURLConnection.setRequestProperty("Content-Language", "en-US");
myURLConnection.setUseCaches(false);
myURLConnection.setDoInput(true);
myURLConnection.setDoOutput(true);
 */