package com.storng.passwordgenerater;

import android.content.Context;

import com.github.rtoshiro.secure.SecureSharedPreferences;

public class SharedPreferences {

    private static SharedPreferences ObjectInstance = null;
    private SecureSharedPreferences sharedPreferences;

    public static final String History = "History";

    public SharedPreferences(Context context) {
        this.sharedPreferences = new SecureSharedPreferences(AppHandler.currentContext);
    }

    public static SharedPreferences getObjectInstance() {
        if (ObjectInstance != null) {
            return ObjectInstance;
        } else {
            return new SharedPreferences(AppHandler.currentContext);
        }
    }

    public void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
