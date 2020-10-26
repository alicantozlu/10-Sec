package com.example.a10sec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.a10sec.fragments.SplashFragment;
import com.example.a10sec.local.Preferences;
import com.example.a10sec.services.ApiClient;
import com.example.a10sec.services.IApiInterface;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static Preferences myPreferences;
    public static FirebaseAuth mAuth;
    public static IApiInterface iApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPreferences = new Preferences(this);
        mAuth=FirebaseAuth.getInstance();
        iApiInterface= ApiClient.getClient().create(IApiInterface.class);

        changeNonStackPage(new SplashFragment());

    }
    public void changeStackPage(Fragment fragment){
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragment).addToBackStack(null).commitAllowingStateLoss();
    }
    public void changeNonStackPage(Fragment fragment){
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragment).commitAllowingStateLoss();
    }
}
