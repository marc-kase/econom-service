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
        System.out.println(sh.getBuyingList().get(0).getName());
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
