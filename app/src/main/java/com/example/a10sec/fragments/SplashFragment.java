package com.example.a10sec.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmSplashBinding;
import com.example.a10sec.models.QuestionModel;
import com.example.a10sec.models.SingeltonAppData;
import com.example.a10sec.models.UserModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashFragment extends BaseFragment {
    private static int splashTimeOut=3000;
    View view;
    private FragmSplashBinding splashBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        splashBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_splash,container,false);
        view = splashBinding.getRoot();
        getQuesitons();
        getUsers();
        return view;
    }
    void animation(){
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashBinding.animLoading.cancelAnimation();
                    if (MainActivity.myPreferences.isLoggedIn()){
                        activity.changeNonStackPage(new HomePageFragment());
                    }else{
                        activity.changeNonStackPage(new LoginFragment());
                    }
                }
            },splashTimeOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUsers(){
        Call<Map<String,UserModel>> call= MainActivity.iApiInterface.getUsers();
        call.enqueue(new Callback<Map<String,UserModel>>() {
            @Override
            public void onResponse(@NotNull Call<Map<String,UserModel>> call, @NotNull Response<Map<String,UserModel>> response) {
                if(response.body()!=null){
                    Map<String,UserModel> usersMap = new HashMap<String, UserModel>();
                    usersMap = response.body();
                    SingeltonAppData.getInstance().setUsersMap(usersMap);
                }
                animation();
            }

            @Override
            public void onFailure(@NotNull Call<Map<String,UserModel>> call, @NotNull Throwable t) {
                Log.e("Response onFailure", "onFailure:" + t.toString());
            }
        });
    }

    private void getQuesitons(){
        Call<ArrayList<QuestionModel>> call= MainActivity.iApiInterface.getQuestions();
        call.enqueue(new Callback<ArrayList<QuestionModel>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<QuestionModel>> call, @NotNull Response<ArrayList<QuestionModel>> response) {
                if(response.body()!=null){
                    ArrayList<QuestionModel> questions = new ArrayList<>();
                    questions = response.body();
                    Collections.shuffle(questions);
                    SingeltonAppData.getInstance().setQuestionCount(0);
                    SingeltonAppData.getInstance().setQuestions(questions);
                }
            }
            @Override
            public void onFailure(@NotNull Call<ArrayList<QuestionModel>> call, @NotNull Throwable t) {
                Log.e("Response onFailure", "onFailure:" + t.toString());
            }
        });
    }
}
