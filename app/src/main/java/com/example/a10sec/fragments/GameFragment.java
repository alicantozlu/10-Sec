package com.example.a10sec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmGameBinding;

public class GameFragment extends BaseFragment {
    View view;
    private FragmGameBinding gameBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gameBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_game,container,false);
        view = gameBinding.getRoot();
        click();
        return view;
    }

    void click() {
    }
}
