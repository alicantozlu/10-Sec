package com.example.a10sec.services;

import android.util.ArrayMap;

import com.example.a10sec.models.SingeltonAppData;
import com.example.a10sec.models.UserModel;
import com.firebase.ui.auth.data.model.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiInterface {
    @GET("users/.json")
    Call<Map<String,UserModel>> getUsers();

    @POST("users/{token}.json")
    Call<UserModel> postUser(@Path("token") String token, @Body JSONObject model);

    /*@FormUrlEncoded
    @POST("users/.json")
    Call<Map<String, Object>> postUser(@Body Map<String, Object> body);*/
}
