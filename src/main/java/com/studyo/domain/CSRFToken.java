package com.studyo.domain;


public class CSRFToken {
    String JSESSIONID;
    String token;

    public CSRFToken() {
    }

    public CSRFToken(String JSESSIONID, String token) {
        this.JSESSIONID = JSESSIONID;
        this.token = token;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public String getToken() {
        return token;
    }
}
