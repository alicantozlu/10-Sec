package com.example.a10sec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a10sec.fragments.SplashFragment;
import com.example.a10sec.local.Preferences;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static Preferences myPreferences;
    public static FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences = new Preferences(this);
        mAuth=FirebaseAuth.getInstance();

        MainActivity.this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new SplashFragment())
                .commitAllowingStateLoss();
    }
}
