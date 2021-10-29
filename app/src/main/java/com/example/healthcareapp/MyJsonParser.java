package com.example.healthcareapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyJsonParser {
    private HashMap<String,String> parserJsonObject(JSONObject object){
        HashMap<String,String> data = new HashMap<>();
        try {
            String name = object.getString("name");
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            String longtitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            data.put("name",name);
            data.put("lat",latitude);
            data.put("lng",longtitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    private List<HashMap<String,String>> parserJsonArray(JSONArray jsonArray){
        List<HashMap<String,String>> datas = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                HashMap<String,String> data = parserJsonObject((JSONObject) jsonArray.get(i));
                datas.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return datas;
    }

    public  List<HashMap<String,String>> parseResult(JSONObject object){
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parserJsonArray(jsonArray);
    }

}
