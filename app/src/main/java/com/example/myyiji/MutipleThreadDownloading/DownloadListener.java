package com.example.myyiji.MutipleThreadDownloading;

public interface DownloadListener {
    void onProgress(int position, int progress, int currentDownload, int totalContentLength);
    void onSuccess(int positionDownload);
    void onFailure();
    void onPause();
    void onCancel();
}
