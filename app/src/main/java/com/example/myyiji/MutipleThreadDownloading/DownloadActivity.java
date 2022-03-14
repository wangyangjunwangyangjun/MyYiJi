package com.example.myyiji.MutipleThreadDownloading;
import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myyiji.R;
public class DownloadActivity extends AppCompatActivity {
    private DownloadService.DownloadBinder downloadBinder;
    private DownloadAdapter downloadAdapter;

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
            downloadAdapter.setDownloadBinder(downloadBinder);
            if(downloadBinder==null){
                Log.i("WYJ","ServiceConnection connection : downloadBinder is null");
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);

        Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);

        if(connection==null){
            Log.i("WYJ","connextion is null");
        }

        if(downloadBinder==null){
            Log.i("WYJ","DownloadActivity onCreate downloadBinder is null");
        }

        if(ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DownloadActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView downloadRecyclerView = findViewById(R.id.rv_download);
        downloadRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        downloadAdapter = new DownloadAdapter(this);
        downloadRecyclerView.setAdapter(downloadAdapter);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("WYJ","Service is unbinding");
         unbindService(connection);
    }
}