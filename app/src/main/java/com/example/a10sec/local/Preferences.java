package com.example.a10sec.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
    private SharedPreferences sharedPreferences;

    public Preferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLoggedIn(Boolean login)  {
        sharedPreferences.edit().putBoolean("login", login).apply();
    }
    public Boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean("login",false);
    }
}
