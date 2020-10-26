package com.example.a10sec.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmScoreboardBinding;
import com.example.a10sec.models.SingeltonAppData;
import com.example.a10sec.models.UserModel;

import java.util.Map;

public class ScoreBoardFragment extends BaseFragment {

    View view;
    private FragmScoreboardBinding scoreboardBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        scoreboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_scoreboard,container,false);
        view = scoreboardBinding.getRoot();
        users();
        click();
        return view;
    }

    void click() {
    }

    private void users() {
        for(Map.Entry<String, UserModel> entry : SingeltonAppData.getInstance().getUsersMap().entrySet()) {
            String key = entry.getKey();
            UserModel value = entry.getValue();
            Log.e("Response Key", "Key :" +key);
            Log.e("Response User", "User :" +value.getEmail());
            Log.e("Response User", "User :" +value.getUsername());
            Log.e("Response User", "User :" +value.getUrl());
            Log.e("Response User", "User :" +value.getScore());
        }
    }
}
