package com.example.a10sec.services;

import com.example.a10sec.models.QuestionModel;
import com.example.a10sec.models.UserModel;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IApiInterface {
    @GET("users/.json")
    Call<Map<String,UserModel>> getUsers();

    @PUT("users/{token}.json")
    Call<UserModel> postUser(@Path("token") String token, @Body UserModel model);

    @GET("questions.json")
    Call<ArrayList<QuestionModel>> getQuestions();
}
