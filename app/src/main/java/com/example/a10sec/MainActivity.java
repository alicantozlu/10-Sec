package com.example.a10sec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a10sec.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MainActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new SplashFragment())
                .commitAllowingStateLoss();
    }
}