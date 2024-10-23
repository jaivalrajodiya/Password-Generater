package com.storng.passwordgenerater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash_Activity.this.startActivity(new Intent(Splash_Activity.this, Intro_Activity.class));
                Splash_Activity.this.finish();
            }
        }, 3000);

    }
}