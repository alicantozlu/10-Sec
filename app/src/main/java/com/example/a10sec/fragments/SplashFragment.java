package com.example.a10sec.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmSplashBinding;

public class SplashFragment extends BaseFragment {
    private static int splashTimeOut=5000;
    View view;
    private FragmSplashBinding splashBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        splashBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_splash,container,false);
        view = splashBinding.getRoot();
        animation();
        return view;
    }
    void animation(){
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashBinding.animLoading.cancelAnimation();
                    if (MainActivity.myPreferences.isLoggedIn()){
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomePageFragment()).commitAllowingStateLoss();
                    }else{
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LoginFragment()).commitAllowingStateLoss();
                    }
                }
            },splashTimeOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
