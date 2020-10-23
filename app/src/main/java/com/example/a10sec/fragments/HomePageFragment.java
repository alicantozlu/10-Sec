package com.example.a10sec.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmHomepageBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomePageFragment  extends BaseFragment{

    View view;
    private FragmHomepageBinding homepageBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        homepageBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_homepage,container,false);
        view = homepageBinding.getRoot();
        click();
        return view;
    }

    void click() {
        homepageBinding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mAuth.signOut();
                AuthUI.getInstance().signOut(activity).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MainActivity.myPreferences.setLoggedIn(false);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new LoginFragment()).commitAllowingStateLoss();
                    }
                });
            }
        });
    }
}
