package com.example.healthcareapp.data_local;

import android.content.Context;
import android.content.SharedPreferences;

public class MyShareReference {
    private Context mContext;

    public MyShareReference(Context mContext) {
        this.mContext = mContext;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("fileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("fileName", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
