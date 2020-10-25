package com.example.a10sec.services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit=null;
    private static String BASE_URL = "https://sec-eeac6.firebaseio.com/";

    public static Retrofit getClient(){
        if(retrofit ==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient())
                    .build();
            return retrofit;
        }
        return retrofit;
    }


}
