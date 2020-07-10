package com.example.retrofit.storage;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.retrofit.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharedPrefManager instance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized Context getInstance(Context context){
        if (instance == null){
            instance = new SharedPrefManager(context);
        }
        return context;
    }

    public void saveUser(User user){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());
        editor.putString("school", user.getSchool());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sp.getInt("id", -1) != -1;
//        if (sp.getInt("id", -1) != -1){
//            return true;
//        }
    }

    public User getUser(){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sp.getInt("id", -1),
                sp.getString("email", null),
                sp.getString("name", null),
                sp.getString("school", null)
        );

    }

    public void clear(){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
