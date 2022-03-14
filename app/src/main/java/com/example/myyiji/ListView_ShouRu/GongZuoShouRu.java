package com.example.myyiji.ListView_ShouRu;
import android.app.Activity;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myyiji.DataBaseItem;
import com.example.myyiji.JiZhang.JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter;
import com.example.myyiji.JiZhang.JiZhang_ShouRu_ZhiZhu_listview_Item_Message;
import com.example.myyiji.MyDatabase;
import com.example.myyiji.R;

import java.util.ArrayList;
import java.util.List;

public class GongZuoShouRu extends AppCompatActivity {
    private List<DataBaseItem> GongZuoShouRuList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gong_zuo_shou_ru);

        Toolbar toolbar = (Toolbar)findViewById(R.id.gongzuoshouru_toolbar);
        //设置标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //进入查询
        MyDatabase myDatabase = new MyDatabase(GongZuoShouRu.this,"yiji_db",null,1);
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        Cursor cursor = db.query("yiji", new String[]{"name","type","time","costicon","cost","costdate","place","note","image"}, "type=?", new String[]{"work_input"}, null, null, null);
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int costicon = cursor.getInt(cursor.getColumnIndex("costicon"));
            double cost = cursor.getDouble(cursor.getColumnIndex("cost"));
            String costdate = cursor.getString(cursor.getColumnIndex("costdate"));
            String place = cursor.getString(cursor.getColumnIndex("place"));
            String note = cursor.getString(cursor.getColumnIndex("note"));
            int image = cursor.getInt(cursor.getColumnIndex("image"));
            InserttListViewItem(name,type,time,costicon,cost,costdate,place,note,image);
        }
        ShouRuListView_Item_Adapter adapter = new ShouRuListView_Item_Adapter(GongZuoShouRu.this,R.layout.listview_message_item,GongZuoShouRuList,db);
        ListView listView = findViewById(R.id.gongzuoshouru_list_view);
        listView.setAdapter(adapter);
        cursor.close();
//        db.close();
//        myDatabase.close();
    }
    private void InserttListViewItem(String name, String type, String time, int costicon, double cost, String costdate, String place, String note, int image){
        DataBaseItem lm1 = new DataBaseItem( name,  type,  time,  costicon,  cost,  costdate,  place,  note,  image);
        GongZuoShouRuList.add(lm1);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        onCreate(null);
//    }
}
