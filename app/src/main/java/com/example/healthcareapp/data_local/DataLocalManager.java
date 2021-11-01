package com.example.healthcareapp.data_local;


import android.content.Context;

import com.example.healthcareapp.model.Cart;
import com.example.healthcareapp.model.Users;
import com.google.gson.Gson;

public class DataLocalManager {
    public MyShareReference myShareReference;
    private static DataLocalManager dataLocalManager;

    public static void intit(Context context){
        dataLocalManager = new DataLocalManager();
        dataLocalManager.myShareReference = new MyShareReference(context);
    }
    public static DataLocalManager getInstance(){
        if(dataLocalManager == null){
            dataLocalManager = new DataLocalManager();
        }
        return dataLocalManager;
    }

    public static void setUser(Users users){
        Gson gson = new Gson();
        String strJsonUsers = gson.toJson(users);
        DataLocalManager.getInstance().myShareReference.putStringValue("fieldName",strJsonUsers);
    }

    public static Users getUsers(){
        String strJsonUsers = DataLocalManager.getInstance().myShareReference.getStringValue("fieldName");
        Gson gson = new Gson();
        Users users = gson.fromJson(strJsonUsers,Users.class);
        return users;
    }
}
