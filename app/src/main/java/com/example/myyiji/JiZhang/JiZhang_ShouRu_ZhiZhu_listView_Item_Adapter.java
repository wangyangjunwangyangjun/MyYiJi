package com.example.myyiji.JiZhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myyiji.R;

import java.util.List;

public class JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter extends ArrayAdapter<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> {
    private int resourceId;
    public JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(Context context, int ViewResourceId, List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> objects){
        super(context,ViewResourceId,objects);
        resourceId = ViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        ImageView listviewImage = (ImageView) view.findViewById(R.id.listview_item_image);
        TextView listviewName = (TextView) view.findViewById(R.id.listview_item_name);
        TextView listviewMoney = (TextView) view.findViewById(R.id.listview_item_money);
        TextView listviewTime = (TextView) view.findViewById(R.id.listview_item_time);

        listviewImage.setImageResource(lm.getImaged());
        listviewName.setText(lm.getName());
        listviewMoney.setText(lm.getMoney());
        listviewTime.setText((lm.getTime()));
        return view;
    }
}
