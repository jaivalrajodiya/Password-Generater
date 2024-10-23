package com.storng.passwordgenerater;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.suke.widget.SwitchButton;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;

    TextView max_leth, min_leth, tv_length, txt_password;

    SwitchButton switch_uppercase, switch_Lowercase, switch_Numbers, switch_spcial;

    AppCompatButton genarte_password;

    ImageView copy, iv_history, iv_menu;

    ArrayList<String> arrayList = new ArrayList<>();

    LinearLayout layout_RateApp, layout_shareapp, layout_History, layout_privacy;

    TextView tv_versionCode;

    DrawerLayout drawerlayout;

    boolean iscopy = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekbar = findViewById(R.id.seekbar);
        max_leth = findViewById(R.id.max_leth);
        min_leth = findViewById(R.id.min_leth);
        switch_uppercase = findViewById(R.id.switch_uppercase);
        switch_Lowercase = findViewById(R.id.switch_Lowercase);
        switch_Numbers = findViewById(R.id.switch_Numbers);
        switch_spcial = findViewById(R.id.switch_spcial);
        tv_length = findViewById(R.id.tv_length);
        genarte_password = findViewById(R.id.genarte_password);
        txt_password = findViewById(R.id.txt_password);
        copy = findViewById(R.id.copy);
        iv_history = findViewById(R.id.iv_history);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_History = findViewById(R.id.layout_History);
        layout_shareapp = findViewById(R.id.layout_shareapp);
        layout_RateApp = findViewById(R.id.layout_RateApp);
        tv_versionCode = findViewById(R.id.tv_versionCode);
        iv_menu = findViewById(R.id.iv_menu);
        drawerlayout = findViewById(R.id.drawerlayout);


        tv_length.setText("LENGTH " + seekbar.getProgress());

        switch_uppercase.setChecked(true);

        txt_password.setText(getpasswordgenerator().generate(seekbar.getProgress()));
        txt_password.setSelected(true);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    tv_length.setText("LENGTH " + String.valueOf(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tv_versionCode.setText("v " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        layout_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, History_Activity.class);
                startActivity(intent);
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        layout_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://eazyearning.com/PrivacyPolicy.html")));
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        layout_RateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateApp();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        layout_shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareApp();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, History_Activity.class);
                startActivity(intent);
            }
        });

        genarte_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!switch_uppercase.isChecked() && !switch_Lowercase.isChecked() && !switch_Numbers.isChecked() && !switch_spcial.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please Select One Options", Toast.LENGTH_SHORT).show();
                    return;
                }
                txt_password.setText(getpasswordgenerator().generate(seekbar.getProgress()));
                iscopy = true;
            }
        });


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_password.getText().toString().equals("") && !txt_password.getText().toString().isEmpty()) {
                    copyPassword(txt_password.getText().toString());
                    if (iscopy){
                        storeCopiedText(txt_password.getText().toString());
                    }


                }
            }
        });


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

    private void RateApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void storeCopiedText(String text) {

        try {
            arrayList.clear();
            Gson gson = new Gson();
            String json = SharedPreferences.getObjectInstance().getString(SharedPreferences.History);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> arrayList1 = gson.fromJson(json, type);

            if (arrayList1 != null && arrayList1.size() > 0) {
                arrayList.addAll(arrayList1);
                arrayList.add(text);
            } else {
                arrayList.add(text);
            }


            Gson gson1 = new Gson();
            String json1 = gson1.toJson(arrayList);
            SharedPreferences.getObjectInstance().putString(SharedPreferences.History, json1);
            iscopy = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void copyPassword(String copiedText) {
        try {
            ClipboardManager clipboard = (ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", copiedText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PasswordGenerator getpasswordgenerator() {
        return new PasswordGenerator.Builder().useLower(this.switch_Lowercase.isChecked()).useUpper(this.switch_uppercase.isChecked()).useDigits(this.switch_Numbers.isChecked()).usePunctuation(this.switch_spcial.isChecked()).build();
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}