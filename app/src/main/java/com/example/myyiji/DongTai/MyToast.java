package com.example.myyiji.DongTai;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyToast {
    public static void myTosat(Context context , int imageId ,String content , int duration){
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.FILL, 0, 0);
        LinearLayout toastLayout = new LinearLayout(context);
        toastLayout.setOrientation(LinearLayout.VERTICAL);
        toastLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(imageId);
        toastLayout.addView(imageView);
        toastLayout.setBackgroundColor(Color.BLACK);
        toast.setView(toastLayout);
        toast.show();
    }
}