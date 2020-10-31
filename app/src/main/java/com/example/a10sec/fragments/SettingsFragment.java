package com.example.a10sec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmSettingsBinding;

public class SettingsFragment extends BaseFragment {
    View view;
    private FragmSettingsBinding settingsBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        settingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_settings,container,false);
        view = settingsBinding.getRoot();
        click();
        return view;
    }

    void click() {
        // yapılacak olan her ayarı Preferences classında oluşturup burda set ediceksin örngein : MainActivity.myPreferences.setLoggedIn(true);
        // En başta default değerler ataman gerekiyor yoksa app patlar.
    }
}