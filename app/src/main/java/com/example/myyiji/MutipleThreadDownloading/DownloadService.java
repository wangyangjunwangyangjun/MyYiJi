package com.example.myyiji.MutipleThreadDownloading;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myyiji.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DownloadService extends Service {
    public Map<String, DownloadTask> downloadTaskMap = new HashMap<>();
    public DownloadBinder mBinder = new DownloadBinder();
    public class DownloadBinder extends Binder {
        public void startDownload(String url, int position, DownloadListener listener){
            if(!downloadTaskMap.containsKey(url)){
                DownloadTask downloadTask = new DownloadTask(listener);
                downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url, position+"");
                downloadTaskMap.put(url, downloadTask);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createNotificationChannel("download", "下载", NotificationManager.IMPORTANCE_HIGH);
                }
                if(downloadTaskMap.size() == 1){
                    startForeground(1, getNotification("正在下载" + downloadTaskMap.size(), -1));
                } else{
                    getNotificationManager().notify(1, getNotification("正在下载" + downloadTaskMap.size(), -1));
                }
            }
        }

        public void updateDownload(String url, DownloadListener listener){
            if(downloadTaskMap.containsKey(url)){
                DownloadTask downloadTask = downloadTaskMap.get(url);
                if(downloadTask != null){
                    downloadTask.setDownloadListener(listener);
                }
            }

        }

        public void pauseDownload(String url){
            if(downloadTaskMap.containsKey(url)){
                DownloadTask downloadTask = downloadTaskMap.get(url);
                if(downloadTask != null){
                    downloadTask.pauseDownload();
                }

                downloadTaskMap.remove(url);

                if(downloadTaskMap.size() > 0){
                    getNotificationManager().notify(1, getNotification("正在下载" + downloadTaskMap.size(), -1));
                } else {
                    stopForeground(true);
                    getNotificationManager().notify(1, getNotification("全部暂停下载", -1));
                }
            }
        }

        public void downloadSuccess(String url){
            if(downloadTaskMap.containsKey(url)){
                DownloadTask downloadTask = downloadTaskMap.get(url);
                downloadTaskMap.remove(url);
                if(downloadTask != null){
                    downloadTask = null;
                }

                if(downloadTaskMap.size() > 0){
                    getNotificationManager().notify(1, getNotification("正在下载" + downloadTaskMap.size(), -1));
                } else {
                    stopForeground(true);
                    getNotificationManager().notify(1, getNotification("下载成功", -1));
                }

            }
        }

        public boolean isDownloading(String url){
            if(downloadTaskMap.containsKey(url)){
                return true;
            }

            return  false;
        }

        public void cancelDownload(String url){
            if(downloadTaskMap.containsKey(url)){
                DownloadTask downloadTask = downloadTaskMap.get(url);
                if(downloadTask != null){
                    downloadTask.cancelDownload();
                }
                downloadTaskMap.remove(url);

                if(downloadTaskMap.size() > 0){
                    getNotificationManager().notify(1, getNotification("正在下载" + downloadTaskMap.size(), -1));
                } else {
                    stopForeground(true);
                    getNotificationManager().notify(1, getNotification("全部取消下载", -1));
                }
            }

            if(url != null){
                String fileName = url.substring(url.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory + fileName);

                if(file.exists()){
                    file.delete();
                    Toast.makeText(DownloadService.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("WYJ","i return mbinder");
        return mBinder;
    }
    private Notification getNotification(String title, int progress) {
        Intent intent = new Intent(this, DownloadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setChannelId("download");
        builder.setSmallIcon(R.drawable.food);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.food));
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        if(progress > 0){
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder.build();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


}

































































































































