package com.storng.passwordgenerater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Intro_Activity extends AppCompatActivity {

    ImageView layout_letstart,layout_pp,layout_ra,layout_sa;


    private static final long DOUBLE_BACK_PRESS_TIME_DELTA = 2000;
    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        layout_sa = findViewById(R.id.layout_sa);
        layout_letstart = findViewById(R.id.layout_letstart);
        layout_pp = findViewById(R.id.layout_pp);
        layout_ra = findViewById(R.id.layout_ra);

        layout_ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateApp();
            }
        });

        layout_sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareApp();
            }
        });

        layout_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://eazyearning.com/PrivacyPolicy.html")));
            }
        });

        layout_letstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intro_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void RateApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void ShareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "\nLet me recommend you this Best application For Password Generate\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBackPressTime < DOUBLE_BACK_PRESS_TIME_DELTA) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            lastBackPressTime = currentTime;
        }
    }
}