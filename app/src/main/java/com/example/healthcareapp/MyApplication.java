package com.example.healthcareapp;

import android.app.Application;

import com.example.healthcareapp.data_local.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.intit(getApplicationContext());
    }
}
