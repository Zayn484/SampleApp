package com.example.zayn.sampleapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static final String fileName = "loginPref";

    public static String readSharefPref(Context context, String name, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name,value);
    }

    public static void writeSharedPref(Context context, String name, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

}
