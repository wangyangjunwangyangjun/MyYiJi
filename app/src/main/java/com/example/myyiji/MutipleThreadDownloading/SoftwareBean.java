package com.example.myyiji.MutipleThreadDownloading;

public class SoftwareBean {
    public final static int IDLE = 0;
    public final static int DOWNLOAD = 1;
    public final static int PAUSE = 2;
    public final static int SUCCESS = 3;
    public String appIcon;
    public String appName;
    public String downloadUrl;
    public boolean isExpanded;
    public int downloadStatus;
    public int currentProgress;
    public String currentProgressDetail;
}
