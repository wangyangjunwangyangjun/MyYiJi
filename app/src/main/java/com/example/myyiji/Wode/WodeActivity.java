package com.example.myyiji.Wode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.myyiji.R;

public class WodeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        Switch s1 = findViewById(R.id.switch1);
        Switch s2 = findViewById(R.id.switch2);
        Switch s3 = findViewById(R.id.switch3);
        final SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch s1 = findViewById(R.id.switch1);
                editor.putBoolean("value1",s1.isChecked());editor.apply();
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch s2 = findViewById(R.id.switch2);
                editor.putBoolean("value2",s2.isChecked());editor.apply();
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Switch s3 = findViewById(R.id.switch3);
                editor.putBoolean("value3",s3.isChecked());editor.apply();
            }
        });
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        s1.setChecked(preferences.getBoolean("value1",false));
        s2.setChecked(preferences.getBoolean("value2",false));
        s3.setChecked(preferences.getBoolean("value3",false));

        Toolbar toolbar = (Toolbar)findViewById(R.id.shezhi_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
