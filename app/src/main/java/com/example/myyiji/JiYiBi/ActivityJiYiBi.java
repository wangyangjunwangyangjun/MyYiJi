package com.example.myyiji.JiYiBi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myyiji.DataBaseItem;
import com.example.myyiji.MyDatabase;
import com.example.myyiji.R;

public class ActivityJiYiBi extends AppCompatActivity {
    RecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_yi_bi);
        //设置标题栏
        Toolbar toolbar = (Toolbar)findViewById(R.id.jiyibi_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置recycleview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecycleViewAdapter(ActivityJiYiBi.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //新增加一条记录
        Button button = findViewById(R.id.button_baocun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase myDatabase = new MyDatabase(ActivityJiYiBi.this,"yiji_db",null,1);
                SQLiteDatabase db = myDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                DataBaseItem dataBaseItem = adapter.getBaseItem();
                values.put("name",dataBaseItem.getName());//2
                values.put("type",dataBaseItem.getType());//3
                values.put("time",dataBaseItem.getTime());//6
                values.put("costicon",dataBaseItem.getCosticon());//1
                values.put("cost",dataBaseItem.getCost());//4
                values.put("costdate",dataBaseItem.getCostdate());//5
                values.put("place",dataBaseItem.getPlace());//7
                values.put("note",dataBaseItem.getNote());//9
                values.put("image",dataBaseItem.getImage());//8
                db.insert("yiji", null, values);
                finish();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
