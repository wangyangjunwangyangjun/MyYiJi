package com.example.myyiji.JiZhang;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.myyiji.DongTai.Activity_DongTai;
import com.example.myyiji.JiYiBi.ActivityJiYiBi;
import com.example.myyiji.ListView_ShouRu.CunKunLiXi;
import com.example.myyiji.ListView_ShouRu.GongZuoShouRu;
import com.example.myyiji.ListView_ZhiChu.CanYinShiPing;
import com.example.myyiji.ListView_ZhiChu.YiFuShiPing;
import com.example.myyiji.Login.ActivityLogin;
import com.example.myyiji.MutipleThreadDownloading.DownloadActivity;
import com.example.myyiji.MyDatabase;
import com.example.myyiji.R;
import com.example.myyiji.Wode.WodeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class Activity_JiZhang extends AppCompatActivity {
    private List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> ShouRuList = new ArrayList<>();
    private List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> ZhiChuList = new ArrayList<>();
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        if(ContextCompat.checkSelfPermission(Activity_JiZhang.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Activity_JiZhang.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //加载navigation和toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        Toast.makeText(Activity_JiZhang.this,"action_search",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_more:
                        Intent intent1 = new Intent(Activity_JiZhang.this, ActivityJiYiBi.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //设置侧边栏按钮点击事件
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.home);
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.beifen:
                        new Post_DataBase_Data_Task().execute("http://192.168.43.187/receive_file.php");
                        break;
                    case R.id.recover:
                        new Get_DataBase_Data_Task().execute("http://192.168.43.187/data.json");
                        break;
                    case R.id.download:
                        startActivity( new Intent(Activity_JiZhang.this, DownloadActivity.class));
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });
        //navigation设置颜色
        Resources resource = getBaseContext().getResources();
        ColorStateList csl= resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        //加载ListView原先的数据
        initListViewItem();
        JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter adapter1 = new JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(Activity_JiZhang.this,R.layout.shouru_listview_message_item,ShouRuList);
        ListView listView1 = findViewById(R.id.list_view1);
        JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter adapter2 = new JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(Activity_JiZhang.this,R.layout.zhichu_listview_message_item,ZhiChuList);
        ListView listView2 = findViewById(R.id.list_view2);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        //浮点控件设置监听器
        FloatingActionButton fab =  navigationView.getHeaderView(0).findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_JiZhang.this, ActivityLogin.class);
                startActivityForResult(intent,1);

            }
        });
        //设置ListView1和ListView2每项点击触发事件
        AdapterView.OnItemClickListener mMessageClickedHandlerListView1 = new
                AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (id == 0) {
                    Intent intent1 = new Intent(Activity_JiZhang.this, GongZuoShouRu.class);
                    startActivity(intent1);
                } else if (id == 1) {
                    Intent intent2 = new Intent(Activity_JiZhang.this, CunKunLiXi.class);
                    startActivity(intent2);
                }
            }
         };
        AdapterView.OnItemClickListener mMessageClickedHandlerListView2 = new
            AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    if (id == 0) {
                        Intent intent1 = new Intent(Activity_JiZhang.this, CanYinShiPing.class);
                        startActivity(intent1);
                    } else if (id == 1) {
                        Intent intent2 = new Intent(Activity_JiZhang.this, YiFuShiPing.class);
                        startActivity(intent2);
                    }
                }
            };
        listView1.setOnItemClickListener(mMessageClickedHandlerListView1);
        listView2.setOnItemClickListener(mMessageClickedHandlerListView2);
        //设置导航栏与侧边界面相关联
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //设置底部工具栏跳转页面
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                switch (item.getItemId()){
                    case R.id.JiZhangButtom:
                        Toast.makeText(Activity_JiZhang.this,"你不就在在记账界面吗？点啥呢？！", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.TongJiButtom:
                        break;
                    case R.id.DongTaiButtom:
                        Intent intent = new Intent(Activity_JiZhang.this, Activity_DongTai.class);
                        startActivity(intent);
                        break;
                    case R.id.wodebuttom:
                        intent = new Intent(Activity_JiZhang.this, WodeActivity.class);
                        startActivity(intent);
                        break;
                }
                BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view_bottom);
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return true;
            }
        });
    }
    //设置导航栏菜单项Item选中监听
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    private void initListViewItem(){
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm1 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("工作收入",R.drawable.work,"+2700.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm2 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("存款利息",R.drawable.bank,"+2700.00","2019.10.24");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm3 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("业绩奖金",R.drawable.award,"+2700.00","2019.10.28");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm4 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("长辈给与",R.drawable.family,"+2700.00","2019.10.29");
        ShouRuList.add(lm1);
        ShouRuList.add(lm2);
        ShouRuList.add(lm3);
        ShouRuList.add(lm4);
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm5 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("餐饮食品",R.drawable.food,"-200.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm6 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("衣服饰品",R.drawable.clothes,"-120.00","2019.10.22");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm7 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("行车交通",R.drawable.subway,"-330.00","2019.10.22");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm8 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("电子设备",R.drawable.electric,"-130.00","2019.10.24");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm9 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("女友消费",R.drawable.girlfriend,"-130.00","2019.10.25");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm10 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("住宅装修",R.drawable.building,"-130.00","2019.10.26");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm11 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("身体健康",R.drawable.hospital,"-130.00","2019.10.27");
        ZhiChuList.add(lm5);
        ZhiChuList.add(lm6);
        ZhiChuList.add(lm7);
        ZhiChuList.add(lm8);
        ZhiChuList.add(lm9);
        ZhiChuList.add(lm10);
        ZhiChuList.add(lm11);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("你要离开我吗?");
            builder.setNegativeButton("是的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                }
            });
            builder.setPositiveButton("再陪会儿",null);
            builder.create().show();
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                FloatingActionButton fab = navigationView.getHeaderView(0).findViewById(R.id.fab);
                fab.setImageResource(R.drawable.myloginlogo);
                TextView textView = findViewById(R.id.LoginStatue);
                textView.setText("已登录");
                TextView textView1 = findViewById(R.id.textView1);
                textView1.setText("点击注销可切换用户登录");
                Context context = this;
                Toast.makeText(context, "欢迎主人回来!", Toast.LENGTH_LONG).show();
            }
        } else {
            Context context = this;
            Toast.makeText(context, "未登录", Toast.LENGTH_LONG).show();
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class Post_DataBase_Data_Task extends AsyncTask<String,Integer,Boolean>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Activity_JiZhang.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean){
                Log.i("WYJ","Post DataBase is successful");
            }else{
                Log.i("WYJ","Post DataBase is unsuccessful");
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setMessage("GetJsonData"+values[0]+"%");
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            MyDatabase myDatabase = new MyDatabase(Activity_JiZhang.this,"yiji_db",null,1);
            SQLiteDatabase db = myDatabase.getWritableDatabase();
            try{
                Cursor cursor = db.query("yiji", new String[]{"name","type","time","costicon","cost","costdate","place","note","image"}, null, null, null, null, null);
                JSONArray jSONArray = new JSONArray();
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
                    JSONObject json = new JSONObject();
                    json.put("name", name);
                    json.put("type", type);
                    json.put("time", time);
                    json.put("costicon", costicon);
                    json.put("cost", cost);
                    json.put("costdate", costdate);
                    json.put("place", place);
                    json.put("note", note);
                    json.put("image", image);
                    jSONArray.put(json);
                }
                postData(jSONArray,strings[0]);
            }catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
    public void postData(JSONArray json,String URL) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost(URL);
            List<NameValuePair> nvp = new ArrayList<>(2);
            nvp.add(new BasicNameValuePair("json", json.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nvp));
            HttpResponse response = httpclient.execute(httppost);
            if(response != null) {
                InputStream is;
                is = response.getEntity().getContent();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    @SuppressLint("StaticFieldLeak")
    public class Get_DataBase_Data_Task extends AsyncTask<String,Integer,Boolean> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Activity_JiZhang.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean){
                Log.i("WYJ","Get DataBase is successful");
            }else{
                Log.i("WYJ","Get DataBase is unsuccessful");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setMessage("GetJsonData"+values[0]+"%");
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(strings[0])
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                showResponse(responseData);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        private void showResponse(String response){
            try {
                JSONArray jsonArray = new JSONArray(response);
                DeleteAndCreateDatabase(Activity_JiZhang.this);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String type = jsonObject.getString("type");
                    String time = jsonObject.getString("time");
                    int costicon = Integer.valueOf(jsonObject.getString("costicon"));
                    int cost = Integer.valueOf(jsonObject.getString("cost"));
                    String costdate = jsonObject.getString("costdate");
                    String place = jsonObject.getString("place");
                    String note = jsonObject.getString("note");
                    int image = Integer.valueOf(jsonObject.getString("image"));
                    Log.i("WYJ",name);
                    Log.i("WYJ",type);
                    Log.i("WYJ",time);
                    Log.i("WYJ",costicon+"");
                    Log.i("WYJ",cost+"");
                    Log.i("WYJ",costdate);
                    Log.i("WYJ",place);
                    Log.i("WYJ",note);
                    Log.i("WYJ",image+"");
                    InsertDataIntoDataBase(Activity_JiZhang.this,name,type,time,costicon,cost,costdate,place,note,image);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void DeleteAndCreateDatabase(Context context) {
        context.deleteDatabase("yiji_db");
        new MyDatabase(context,"yiji_db",null,1);
    }
    public void InsertDataIntoDataBase(Context context,String name,String type,String time,int costicon,int cost,String costdate,String place,String note,int image){
        MyDatabase myDatabase = new MyDatabase(context,"yiji_db",null,1);
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);//2
        values.put("type",type);//3
        values.put("time",time);//6
        values.put("costicon",costicon);//1
        values.put("cost",cost);//1
        values.put("costdate",costdate);//4
        values.put("place",place);//5
        values.put("note",note);//7
        values.put("image",image);//9
        db.insert("yiji", null, values);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}