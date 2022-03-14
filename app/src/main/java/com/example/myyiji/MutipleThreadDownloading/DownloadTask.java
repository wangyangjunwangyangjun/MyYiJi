package com.example.myyiji.MutipleThreadDownloading;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILURE = 1;
    public static final int TYPE_PAUSE = 2;
    public static final int TYPE_CANCEL = 3;

    public int positionDownload;

    private boolean isPaused = false;
    private boolean isCancelled = false;

    private DownloadListener downloadListener;
    private int lastProgress;

    public DownloadTask(DownloadListener downloadListener){
        this.downloadListener = downloadListener;
    }

    public void setDownloadListener(DownloadListener downloadListener){
        this.downloadListener = downloadListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;

        long downloadLength = 0;
        String downloadUrl = params[0];
        positionDownload = Integer.parseInt(params[1]);
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        file = new File(directory + fileName);

        if(file.exists()){
            downloadLength = file.length();
        }

        long contentLength = getContentLength(downloadUrl);
        if(contentLength == 0){
            return TYPE_FAILURE;
        } else if(contentLength == downloadLength){
            return TYPE_SUCCESS;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("RANGE", "bytes="+downloadLength+"-")
                .url(downloadUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response != null){
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength);
                byte[] buffer = new byte[1024];
                int total = 0;
                int length;

                while((length = is.read(buffer)) != -1){
                    if(isCancelled){
                        response.body().close();
                        return TYPE_CANCEL;
                    } else if(isPaused) {
                        response.body().close();
                        return TYPE_PAUSE;
                    }

                    total += length;
                    savedFile.write(buffer, 0, length);

                    int progress = (int) ((total + downloadLength) * 100 / contentLength);
                    int currentDownload = (int) (total + downloadLength);
                    publishProgress(positionDownload, progress, currentDownload, (int) contentLength);
                }

                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) is.close();
                if(savedFile != null) savedFile.close();
                if(isCancelled && file != null) file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TYPE_FAILURE;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[1];
        if(progress > lastProgress){
            downloadListener.onProgress(values[0], progress, values[2], values[3]);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                downloadListener.onSuccess(positionDownload);
                break;
            case TYPE_FAILURE:
                downloadListener.onFailure();
                break;
            case TYPE_PAUSE:
                downloadListener.onPause();
                break;
            case TYPE_CANCEL:
                downloadListener.onCancel();
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCancelled = true;
    }

    private long getContentLength(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response != null && response.isSuccessful()){
                long contentLength = response.body().contentLength();
                response.body().close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
