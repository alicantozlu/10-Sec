package com.example.a10sec.models;

public class SingeltonAppData {

    //Singleton'un > app ayağa kalktığında objelerin app ayakta kaldığı sürece burada tutulur.
    private static final SingeltonAppData ourInstance = new SingeltonAppData();
    public static SingeltonAppData getInstance(){
        return ourInstance;
    }

    public SingeltonAppData() {

    }

    private String userToken;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
