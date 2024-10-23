package com.storng.passwordgenerater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class History_Activity extends AppCompatActivity {

    TextView tv_nodata;

    RecyclerView rcv_history;

    History_Adapter history_adapter;

    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tv_nodata = findViewById(R.id.tv_nodata);
        rcv_history = findViewById(R.id.rcv_history);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Gson gson = new Gson();
        String json =  SharedPreferences.getObjectInstance().getString(SharedPreferences.History);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> passwordarraylist =  gson.fromJson(json, type);

        rcv_history.setLayoutManager(new LinearLayoutManager(History_Activity.this));

        Log.e("aaaa", "onCreate: "+passwordarraylist );

        try {

            if ( passwordarraylist != null && passwordarraylist.size() > 0){
                rcv_history.setVisibility(View.VISIBLE);
                tv_nodata.setVisibility(View.GONE);

                history_adapter = new History_Adapter(passwordarraylist, History_Activity.this, new History_Adapter.Onclickevent() {
                    @Override
                    public void onItemClick(int position) {
                        try {
                            passwordarraylist.remove(position);
                            history_adapter.notifyDataSetChanged();

                            if (passwordarraylist.isEmpty()){
                                rcv_history.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                            }

                            Gson gson = new Gson();
                            String json = gson.toJson(passwordarraylist);
                            SharedPreferences.getObjectInstance().putString(SharedPreferences.History, json);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                rcv_history.setAdapter(history_adapter);
            }else {
                rcv_history.setVisibility(View.GONE);
                tv_nodata.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}