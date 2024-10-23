package com.storng.passwordgenerater;

import android.app.Application;
import android.content.Context;

public class AppHandler extends Application {
    public static Context currentContext;

    public static Context getCurrentContext() {
        return currentContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentContext = this;
    }
}
