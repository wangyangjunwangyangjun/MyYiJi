package com.example.myyiji.DongTai;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myyiji.MyGridView;
import com.example.myyiji.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DongTaiRecycleViewMultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private  List<DongTaiRecycleItemMessage> item;
    private Context context;
    //建立枚举 4个item 类型
    public enum ITEM_TYPE {
        picture,
        music,
        video,
        onlyword,
        linklist
    }
    DongTaiRecycleViewMultipleAdapter(List<DongTaiRecycleItemMessage> item, Context context) {
        this.item = item;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
        return judgetype(position);
    }
    private int judgetype(int i){
        if(!item.get(i).getMusic().equals("")){
            return ITEM_TYPE.music.ordinal();
        }else if(!item.get(i).getVideo().equals("")){
            return ITEM_TYPE.video.ordinal();
        }else if(item.get(i).getPicturesGroup().length!=0){
            return ITEM_TYPE.picture.ordinal();
        }else if(!item.get(i).getLinklisttext().equals("")){
            return ITEM_TYPE.linklist.ordinal();
        } else{
            return ITEM_TYPE.onlyword.ordinal();
        }
    }
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.picture.ordinal()) {
            return new pictureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dongtai_recycleview_message_item_picture, parent, false));
        }else if(viewType==ITEM_TYPE.music.ordinal()){
            return new musicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dongtai_recycleview_message_item_music, parent, false));
        }else if(viewType==ITEM_TYPE.video.ordinal()){
            return new videoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dongtai_recycleview_message_item_video, parent, false));
        }else if(viewType==ITEM_TYPE.linklist.ordinal()){
            return new linklistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dongtai_recycleview_message_item_linklist, parent, false));
        }else{
            return new onlywordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dongtai_recycleview_message_item_onlyword, parent, false));
        }
    }
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final DongTaiRecycleItemMessage lm = item.get(position);
        int pictures_num = lm.getPicturesGroup().length;
        if (holder instanceof pictureViewHolder) {
        //设置多张图片摆放格式，并设置点击
            if(pictures_num==1){
                ((pictureViewHolder) holder).gridView.setNumColumns(1);((pictureViewHolder) holder).gridView.setAdapter(new GridImageAdapter1(context,lm.getPicturesGroup()));
            } else if (pictures_num==2){
                ((pictureViewHolder) holder).gridView.setNumColumns(2);((pictureViewHolder) holder).gridView.setAdapter(new GridImageAdapter2(context,lm.getPicturesGroup()));
            } else{
                ((pictureViewHolder) holder).gridView.setNumColumns(3);((pictureViewHolder) holder).gridView.setAdapter(new GridImageAdapter3to9(context,lm.getPicturesGroup()));
            }
            ((pictureViewHolder) holder).imageView.setImageResource(lm.getLogo());
            ((pictureViewHolder) holder).textView.setText(lm.getPersonID());
            if(lm.getContent().equals("")) ((pictureViewHolder) holder).textView1.setVisibility(View.GONE);
            else ((pictureViewHolder) holder).textView1.setText(lm.getContent());
            ((pictureViewHolder) holder).textView2.setText(lm.getTime());
            ((pictureViewHolder) holder).textstate.setText(lm.getTextstate());
            ((pictureViewHolder) holder).likelist.setText(getlikelist(lm));
            ((pictureViewHolder) holder).comments.setText(getcomment(lm));
            ((pictureViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.menulayout, null);
                    PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popWnd.setContentView(contentView);
                    popWnd.showAsDropDown(v,-250, -75);
                }
            });
            ((pictureViewHolder) holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                    DongTaiRecycleItemMessage lm = item.get(position);
                    MyToast.myTosat(context, lm.getPicturesGroup()[position1], "", Toast.LENGTH_SHORT);
                }
            });
        } else if (holder instanceof musicViewHolder) {
            ((musicViewHolder) holder).imageView.setImageResource(lm.getLogo());
            ((musicViewHolder) holder).textView.setText(lm.getPersonID());
            ((musicViewHolder) holder).textView1.setText(lm.getContent());
            ((musicViewHolder) holder).MusicView.setText(lm.getMusic().substring(47));
            ((musicViewHolder) holder).textView2.setText(lm.getTime());
            ((musicViewHolder) holder).textstate.setText(lm.getTextstate());
            ((musicViewHolder) holder).likelist.setText(getlikelist(lm));
            ((musicViewHolder) holder).comments.setText(getcomment(lm));
            ((musicViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.menulayout, null);
                    PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popWnd.setContentView(contentView);
                    popWnd.showAsDropDown(v,-250, -75);
                }
            });
            final MediaPlayer mMediaPlayer = new MediaPlayer();
            File file = new File(lm.getMusic());
            try {

                mMediaPlayer.setDataSource(file.getPath());
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((musicViewHolder) holder).start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mMediaPlayer.isPlaying()) mMediaPlayer.start();
                }
            });
            ((musicViewHolder) holder).pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mMediaPlayer.isPlaying()){
                        mMediaPlayer.pause();
                    }
                }
            });
            ((musicViewHolder) holder).stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mMediaPlayer.isPlaying()){
                        mMediaPlayer.reset();
                        try{
                            File file = new File(lm.getMusic());
                            mMediaPlayer.setDataSource(file.getPath());
                            mMediaPlayer.prepare();
                        }
                        catch(IOException ignored){}
                    }

                }
            });
        } else if (holder instanceof videoViewHolder) {
            ((videoViewHolder) holder).imageView.setImageResource(lm.getLogo());
            ((videoViewHolder) holder).textView.setText(lm.getPersonID());
            ((videoViewHolder) holder).textView1.setText(lm.getContent());
             File videofile = new File(lm.getVideo());
            ((videoViewHolder) holder).VideoView.setVideoPath(videofile.getAbsolutePath());
            ((videoViewHolder) holder).VideoView.setMediaController(new MediaController(context));
            ((videoViewHolder) holder).VideoView.start();
            ((videoViewHolder) holder).textView2.setText(lm.getTime());
            ((videoViewHolder) holder).textstate.setText(lm.getTextstate());
            ((videoViewHolder) holder).likelist.setText(getlikelist(lm));
            ((videoViewHolder) holder).comments.setText(getcomment(lm));
            ((videoViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.menulayout, null);
                    PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popWnd.setContentView(contentView);
                    popWnd.showAsDropDown(v,-250, -75);
                }
            });
        }else if (holder instanceof onlywordViewHolder) {
            ((onlywordViewHolder) holder).imageView.setImageResource(lm.getLogo());
            ((onlywordViewHolder) holder).textView.setText(lm.getPersonID());
            ((onlywordViewHolder) holder).textView1.setText(lm.getContent());
            ((onlywordViewHolder) holder).textView2.setText(lm.getTime());
            ((onlywordViewHolder) holder).textstate.setText(lm.getTextstate());
            ((onlywordViewHolder) holder).likelist.setText(getlikelist(lm));
            ((onlywordViewHolder) holder).comments.setText(getcomment(lm));
            ((onlywordViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.menulayout, null);
                    PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popWnd.setContentView(contentView);
                    popWnd.showAsDropDown(v,-250, -75);
                }
            });
        }else if(holder instanceof linklistViewHolder){
            ((linklistViewHolder)holder).imageView.setImageResource(lm.getLogo());
            ((linklistViewHolder) holder).textView.setText(lm.getPersonID());
            ((linklistViewHolder) holder).textView1.setText(lm.getContent());
            ((linklistViewHolder) holder).textView2.setText(lm.getTime());
            ((linklistViewHolder) holder).textstate.setText(lm.getTextstate());
            ((linklistViewHolder)holder).imageView1.setImageResource(lm.getLinklistimage());
            ((linklistViewHolder) holder).textView4.setText(lm.getLinklisttext());
            ((linklistViewHolder) holder).likelist.setText(getlikelist(lm));
            ((linklistViewHolder) holder).comments.setText(getcomment(lm));
            ((linklistViewHolder) holder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("InflateParams") View contentView = LayoutInflater.from(context).inflate(R.layout.menulayout, null);
                    PopupWindow popWnd = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    popWnd.setContentView(contentView);
                    popWnd.showAsDropDown(v,-250, -75);
                }
            });
        }

    }
    private String getlikelist(DongTaiRecycleItemMessage lm){
        StringBuilder strlikelist = new StringBuilder("       ");
        List<String>list = lm.getLikelist();
        for(String i:list){
            if(!i.equals(list.get(list.size() - 1)))
                strlikelist.append(i).append(",");
        }
        strlikelist.append(list.get(list.size() - 1));
        return strlikelist.toString();
    }
    private String getcomment(DongTaiRecycleItemMessage lm){
        StringBuilder strcomment = new StringBuilder();
        Map<String,String>map = lm.getRemark();
        for(Map.Entry<String,String>entry:map.entrySet()){
            strcomment.append(entry.getKey());
            strcomment.append(" : ");
            strcomment.append(entry.getValue());
            strcomment.append("\n");
        }
        return strcomment.toString();
    }
    @Override
    public int getItemCount() {
        if(item==null){
            return 0;
        }else{
            return item.size();
        }
    }
    public static class pictureViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView textView ;
        TextView textView1;
        TextView textView2;
        TextView textstate;
        MyGridView gridView;
        Button button;
        TextView comments;
        TextView likelist;
        ImageView imageView1;

        pictureViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.user_logo);
            textView = view.findViewById(R.id.user_id);
            textView1 = view.findViewById(R.id.picture_user_describtion);
            textView2 = view.findViewById(R.id.time_picture);
            textstate = view.findViewById(R.id.picture_textState);
            gridView = view.findViewById(R.id.pictures);
            button = view.findViewById(R.id.more_button_picture);
            comments = view.findViewById(R.id.picturecomment);
            likelist = view.findViewById(R.id.picturelikelist);
            imageView1 = view.findViewById(R.id.picturelikelogo);
            textstate.setClickable(true);
            textstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textstate.getText().toString().equals("全文")){
                        textstate.setText("收缩");
                        textView1.getLayoutParams().height = 700;
                    }else{
                        textstate.setText("全文");
                        textView1.getLayoutParams().height = 220;
                    }
                }
            });

        }
    }

    public static class musicViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        ImageView imageView1;
        TextView textView ;
        TextView textView1;
        TextView MusicView ;
        TextView textView2;
        TextView textstate;
        Button button;
        Button start;
        Button pause;
        Button stop;
        TextView comments;
        TextView likelist;
        musicViewHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.user_logo);
            textView = view.findViewById(R.id.user_id);
            textView1 = view.findViewById(R.id.music_user_describtion);
            MusicView = view.findViewById(R.id.music);
            textView2 = view.findViewById(R.id.time_music);
            textstate = view.findViewById(R.id.music_textState);
            button = view.findViewById(R.id.more_button_music);
            start = view.findViewById(R.id.music_play);
            pause = view.findViewById(R.id.music_pause);
            stop = view.findViewById(R.id.music_stop);
            comments = view.findViewById(R.id.music_commment);
            likelist = view.findViewById(R.id.musiclikelist);
            imageView1 = view.findViewById(R.id.musiclikelogo);
            textstate.setClickable(true);

            textstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("全文".equals(textstate.getText().toString())){
                        textstate.setText("收缩");
                        textView1.getLayoutParams().height = 700;
                    }else{
                        textstate.setText("全文");
                        textView1.getLayoutParams().height = 220;
                    }
                }
            });
        }
    }
    public static class videoViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView textView ;
        TextView textView1;
        VideoView VideoView ;
        TextView textView2;
        TextView textstate;
        Button button;
        TextView comments;
        TextView likelist;
        ImageView imageView1;
        videoViewHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.user_logo);
            textView = view.findViewById(R.id.user_id);
            textView1 = view.findViewById(R.id.video_user_describtion);
            VideoView = view.findViewById(R.id.video);
            textView2 = view.findViewById(R.id.time_video);
            textstate = view.findViewById(R.id.video_textState);
            button = view.findViewById(R.id.more_button_video);
            comments = view.findViewById(R.id.videocomment);
            likelist = view.findViewById(R.id.videolikelist);
            imageView1 = view.findViewById(R.id.videolikelogo);
            textstate.setClickable(true);
            textstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textstate.getText().toString().equals("全文")){
                        textstate.setText("收缩");
                        textView1.getLayoutParams().height = 700;
                    }else{
                        textstate.setText("全文");
                        textView1.getLayoutParams().height = 220;
                    }
                }
            });
        }
    }
    public static class onlywordViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        ImageView imageView1;
        TextView textView ;
        TextView textView1;
        TextView textView2;
        TextView textstate;
        Button button;
        TextView comments;
        TextView likelist;
        onlywordViewHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.user_logo);
            textView = view.findViewById(R.id.user_id);
            textView1 = view.findViewById(R.id.onlyword_user_describtion);
            textView2 = view.findViewById(R.id.time_onlyword);
            textstate = view.findViewById(R.id.onlyword_textState);
            button = view.findViewById(R.id.more_button_only_word);
            comments = view.findViewById(R.id.only_word_commment);
            likelist = view.findViewById(R.id.only_wordlikelist);
            imageView1 = view.findViewById(R.id.onlywordlikelogo);
            textstate.setClickable(true);
            textstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textstate.getText().toString().equals("全文")){
                        textstate.setText("收缩");
                        textView1.getLayoutParams().height = 700;
                    }else{
                        textstate.setText("全文");
                        textView1.getLayoutParams().height = 220;
                    }
                }
            });
        }
    }
    public static class linklistViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView textView ;
        TextView textView1;
        TextView textView2;
        TextView textstate;
        ImageView imageView1;
        ImageView imageView2;
        TextView textView4;
        Button button;
        TextView comments;
        TextView likelist;
        linklistViewHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.user_logo);
            textView = view.findViewById(R.id.user_id);
            textView1 = view.findViewById(R.id.linklist_user_describtion);
            textView2 = view.findViewById(R.id.time_linklist);
            textstate = view.findViewById(R.id.linklist_textState);
            button = view.findViewById(R.id.more_button_linklist);
            comments = view.findViewById(R.id.linklist_commment);
            likelist = view.findViewById(R.id.linklistlikelist);
            textView4 = view.findViewById(R.id.linklist_text);
            imageView1 = view.findViewById(R.id.linklist_image);
            imageView2 = view.findViewById(R.id.linklistlikelogo);
            textstate.setClickable(true);
            textstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(textstate.getText().toString().equals("全文")){
                        textstate.setText("收缩");
                        textView1.getLayoutParams().height = 700;
                    }else{
                        textstate.setText("全文");
                        textView1.getLayoutParams().height = 220;
                    }
                }
            });
        }
    }
}


