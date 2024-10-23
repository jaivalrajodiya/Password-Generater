package com.storng.passwordgenerater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.suke.widget.SwitchButton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

   SeekBar seekbar;

   TextView max_leth,min_leth,tv_length,txt_password;

   SwitchButton switch_uppercase,switch_Lowercase,switch_Numbers,switch_spcial;

   AppCompatButton genarte_password;

   ImageView copy,iv_history;

    ArrayList<String> arrayList = new ArrayList<>();


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


        tv_length.setText("LENGTH "+seekbar.getProgress());

        switch_uppercase.setChecked(true);

        txt_password.setText(getpasswordgenerator().generate(seekbar.getProgress()));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    tv_length.setText("LENGTH "+String.valueOf(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,History_Activity.class);
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
            }
        });




        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_password.getText().toString().equals("") && !txt_password.getText().toString().isEmpty()){
                    copyPassword(txt_password.getText().toString());
                    storeCopiedText(txt_password.getText().toString());

                }
            }
        });



    }

    private void storeCopiedText(String text) {

        try {
            arrayList.clear();
            Gson gson = new Gson();
            String json =  SharedPreferences.getObjectInstance().getString(SharedPreferences.History);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> arrayList1 =  gson.fromJson(json, type);

            if (arrayList1 != null && arrayList1.size() > 0 ){
                arrayList.addAll(arrayList1);
                arrayList.add(text);
            }else {
                arrayList.add(text);
            }


            Gson gson1 = new Gson();
            String json1 = gson1.toJson(arrayList);
            SharedPreferences.getObjectInstance().putString(SharedPreferences.History, json1);
        }catch (Exception e){
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
}