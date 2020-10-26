package com.example.a10sec.models;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.Map;

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

    private Map<String,UserModel> usersMap;

    public Map <String,UserModel>getUsersMap(){return usersMap;}

    public void setUsersMap(Map<String,UserModel> usersMap){this.usersMap = usersMap;}

    private ArrayList<QuestionModel> questions;

    public  ArrayList<QuestionModel>getQuestions(){return questions;}

    public void setQuestions( ArrayList<QuestionModel> questions){this.questions = questions;}
}
