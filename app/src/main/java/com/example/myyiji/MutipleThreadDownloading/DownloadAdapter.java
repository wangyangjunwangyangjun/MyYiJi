package com.example.myyiji.MutipleThreadDownloading;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myyiji.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter {
    private ArrayList<SoftwareBean> softwareList;
    private DownloadService.DownloadBinder downloadBinder;
    private Context mContext;

    public DownloadAdapter(Context context){
        softwareList = new ArrayList<>();
        initSoftwareList();
        this.mContext = context;
    }

    private void initSoftwareList() {
        SoftwareBean software = new SoftwareBean();
        software.appIcon = "";
        software.appName = "易记补丁1";
        software.downloadUrl = "https://cdn.modao.cc/win32-ia32/MockingBot-win32-ia32-zh-0.7.6.exe";
        softwareList.add(software);

        software = new SoftwareBean();
        software.appIcon = "";
        software.appName = "易记补丁2";
        software.downloadUrl = "http://pcclient.download.youku.com/youkuclient/youkuclient_setup_7.9.0.12166.exe";
        softwareList.add(software);

        software = new SoftwareBean();
        software.appIcon = "";
        software.appName = "易记补丁3";
        software.downloadUrl = "http://fastsoft.onlinedown.net/down/eclipseinstwin64.exe";
        softwareList.add(software);

        software = new SoftwareBean();
        software.appIcon = "";
        software.appName = "易记补丁4";
        software.downloadUrl = "https://qd.myapp.com/myapp/qqteam/pcqq/PCQQ2019.exe";
        softwareList.add(software);
    }

    public void setDownloadBinder(DownloadService.DownloadBinder downloadBinder){
        this.downloadBinder = downloadBinder;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_download_manager, parent, false);
        RecyclerView.ViewHolder viewHolder = new DownloadViewHolder(view);
        viewHolder.itemView.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DownloadViewHolder viewHolder = (DownloadViewHolder) holder;
        final SoftwareBean item = softwareList.get(position);
        final MyDownloadListener listener = new MyDownloadListener(viewHolder);

        if(downloadBinder != null && downloadBinder.isDownloading(item.downloadUrl)){
            downloadBinder.updateDownload(item.downloadUrl, listener);
            item.downloadStatus = SoftwareBean.DOWNLOAD;
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button deleteButton = (Button) v.findViewById(R.id.bt_delete);
                ImageView arrowImageView = (ImageView) v.findViewById(R.id.iv_arrow);
                if(deleteButton.getVisibility() == View.GONE){
                    deleteButton.setVisibility(View.VISIBLE);
                    arrowImageView.setImageResource(R.drawable.arrow);
                    item.isExpanded = true;

                } else{
                    deleteButton.setVisibility(View.GONE);
                    arrowImageView.setImageResource(R.drawable.arrow);
                    item.isExpanded = false;
                }
            }
        });
        //viewHolder.mAppIcon.setImageResource(R.mipmap.ic_launcher);
        viewHolder.mAppName.setText(item.appName);
        viewHolder.mProgressDetail.setText(item.currentProgressDetail);
        viewHolder.mDownloadProgress.setProgress(item.currentProgress);


        switch (item.downloadStatus){
            case SoftwareBean.DOWNLOAD:
                viewHolder.mDownload.setText("停止下载");
                break;
            case SoftwareBean.PAUSE:
            case SoftwareBean.IDLE:
                viewHolder.mDownload.setText(R.string.download);
                break;
            case SoftwareBean.SUCCESS:
                viewHolder.mProgressDetail.setText("");
                viewHolder.mDownload.setText("下载");
                break;
        }

        if(item.downloadStatus == SoftwareBean.SUCCESS){
            viewHolder.mDownload.setOnClickListener(new InstallOnClickListener(item.downloadUrl));
        } else{
            viewHolder.mDownload.setOnClickListener(new DownloadOnClickListener(viewHolder, listener));
        }

        if(item.isExpanded){
            viewHolder.mArrow.setImageResource(R.drawable.arrow);
            viewHolder.mDelete.setVisibility(View.VISIBLE);
        } else{
            viewHolder.mArrow.setImageResource(R.drawable.arrow);
            viewHolder.mDelete.setVisibility(View.GONE);
        }
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(downloadBinder == null) return;
                downloadBinder.cancelDownload(item.downloadUrl);
                item.currentProgressDetail = "";
                item.currentProgress = 0;
                item.downloadStatus = SoftwareBean.IDLE;

                viewHolder.mDownloadProgress.setProgress(0);
                viewHolder.mDownload.setText(R.string.download);
                viewHolder.mDownload.setOnClickListener(new DownloadOnClickListener(viewHolder, listener));
            }
        });
        viewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("WYJ","i click the mDownload Button");
                if(downloadBinder == null){
                    Log.i("WYJ","DownloadAdapter ViewHolder downloadBinder is null");
                    return;
                }
                int position = viewHolder.getAdapterPosition();
                final SoftwareBean item = softwareList.get(position);
                Button download = (Button) v;
                if(item.downloadStatus == SoftwareBean.IDLE || item.downloadStatus == SoftwareBean.PAUSE){
                    downloadBinder.startDownload(item.downloadUrl, position, listener);
                    item.downloadStatus = SoftwareBean.DOWNLOAD;
                    download.setText("停止");
                } else if(item.downloadStatus == SoftwareBean.DOWNLOAD){
                    downloadBinder.pauseDownload(item.downloadUrl);
                    item.downloadStatus = SoftwareBean.PAUSE;
                    download.setText(R.string.download);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return softwareList.size();
    }

    class DownloadViewHolder extends RecyclerView.ViewHolder{
        ImageView mAppIcon;
        TextView mAppName;
        TextView mProgressDetail;
        ProgressBar mDownloadProgress;
        ImageView mArrow;
        Button mDownload;
        Button mDelete;

        public DownloadViewHolder(View itemView) {
            super(itemView);
            mAppIcon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
            mAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
            mProgressDetail = (TextView) itemView.findViewById(R.id.tv_progress_detail);
            mDownloadProgress = (ProgressBar) itemView.findViewById(R.id.pb_download);
            mArrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            mDownload = (Button) itemView.findViewById(R.id.bt_download);
            mDelete = (Button) itemView.findViewById(R.id.bt_delete);
        }
    }

    private class MyDownloadListener implements DownloadListener{
        private DownloadViewHolder viewHolder;

        public MyDownloadListener(DownloadViewHolder viewHolder){
            this.viewHolder = viewHolder;
        }

        @Override
        public void onProgress(int position, int progress, int currentDownload, int totalContentLength) {
            int currentPosition = viewHolder.getAdapterPosition();
            if(position == currentPosition){
                SoftwareBean item = softwareList.get(position);
                item.currentProgress = progress;

                viewHolder.mDownloadProgress.setProgress(progress);
                String currentLength = Formatter.formatFileSize(mContext, currentDownload);
                String totalLength = Formatter.formatFileSize(mContext, totalContentLength);
                String currentDetail = currentLength + "/" + totalLength;
                item.currentProgressDetail = currentDetail;
                viewHolder.mProgressDetail.setText(currentDetail);
            }
        }

        @Override
        public void onSuccess(int positionDownload) {
            final SoftwareBean item = softwareList.get(positionDownload);
            item.downloadStatus = SoftwareBean.SUCCESS;
            item.currentProgress = 100;
            downloadBinder.downloadSuccess(item.downloadUrl);

            int currentPosition = viewHolder.getAdapterPosition();
            if(positionDownload == currentPosition){
                viewHolder.mDownload.setText("下载");
                viewHolder.mProgressDetail.setText("");
                viewHolder.mDownload.setOnClickListener(new InstallOnClickListener(item.downloadUrl));
            }
        }

        @Override
        public void onFailure() {
            Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            Toast.makeText(mContext, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadOnClickListener implements View.OnClickListener{
        private DownloadViewHolder viewHolder;
        MyDownloadListener listener;
        public DownloadOnClickListener(DownloadViewHolder viewHolder, MyDownloadListener listener){
            this.viewHolder = viewHolder;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(downloadBinder == null){
                Log.i("WYJ","DownloadAdapter downloadBinder is null");
                return;
            }
            int position = viewHolder.getAdapterPosition();
            final SoftwareBean item = softwareList.get(position);
            Button download = (Button) v;
            if(item.downloadStatus == SoftwareBean.IDLE || item.downloadStatus == SoftwareBean.PAUSE){
                downloadBinder.startDownload(item.downloadUrl, position, listener);
                item.downloadStatus = SoftwareBean.DOWNLOAD;
                download.setText("停止");
            } else if(item.downloadStatus == SoftwareBean.DOWNLOAD){
                downloadBinder.pauseDownload(item.downloadUrl);
                item.downloadStatus = SoftwareBean.PAUSE;
                download.setText(R.string.download);
            }

        }
    }

    private class InstallOnClickListener implements View.OnClickListener{
        private String downloadUrl;

        public InstallOnClickListener(String downloadUrl){
            this.downloadUrl = downloadUrl;
        }

        @Override
        public void onClick(View v) {
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            File file = new File(directory + fileName);

            Uri appUri = null;
            if(Build.VERSION.SDK_INT >= 24){
                appUri = FileProvider.getUriForFile(mContext, "com.rickge.downloadservice.fileprovider", file);
            } else{
                appUri = Uri.fromFile(file);
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(appUri, "application/vnd.android.package-archive");
            mContext.startActivity(intent);
            //startActivityForResult(intent, 0);
        }
    }
}
