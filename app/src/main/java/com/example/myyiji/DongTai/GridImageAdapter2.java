package com.example.myyiji.DongTai;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageAdapter2 extends BaseAdapter {
    private Context context;
    private int[]datalist;
    private DisplayMetrics dm;
    public GridImageAdapter2(Context context, int[] datalist){
        this.context = context;
        this.datalist = datalist;
        dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }
    @Override
    public int getCount() {
        return datalist.length;
    }

    @Override
    public Object getItem(int i) {
        return datalist[i];
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = null;
        if(view==null) imageView = new ImageView(context);
        else imageView = (ImageView) view;
        imageView.setLayoutParams(new GridView.LayoutParams(350,800));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(datalist[i]);
        return imageView;
    }
}
