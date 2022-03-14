package com.example.myyiji.ListView_ZhiChu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.myyiji.R;
import com.example.myyiji.JiZhang.JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter;
import com.example.myyiji.JiZhang.JiZhang_ShouRu_ZhiZhu_listview_Item_Message;

import java.util.ArrayList;
import java.util.List;

public class CanYinShiPing extends Activity {
    private List<JiZhang_ShouRu_ZhiZhu_listview_Item_Message> CanYinShiPingList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.can_yin_shi_ping);
        initListViewItem();
        JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter adapter3 = new JiZhang_ShouRu_ZhiZhu_listView_Item_Adapter(CanYinShiPing.this,R.layout.listview_message_item,CanYinShiPingList);
        ListView listView = findViewById(R.id.canyinshiping_list_view);
        listView.setAdapter(adapter3);
    }

    private void initListViewItem(){
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm12 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("外卖点餐",R.drawable.food1,"-200.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm13 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("街边小吃",R.drawable.food2,"-100.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm14 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("饮料购买",R.drawable.food3,"-100.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm15 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("蛋糕购买",R.drawable.food4,"-200.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm16 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("名贵茶叶",R.drawable.food5,"-400.00","2019.10.21");
        JiZhang_ShouRu_ZhiZhu_listview_Item_Message lm17 = new JiZhang_ShouRu_ZhiZhu_listview_Item_Message("美味水果",R.drawable.food6,"-200.00","2019.10.21");

        CanYinShiPingList.add(lm12);
        CanYinShiPingList.add(lm13);
        CanYinShiPingList.add(lm14);
        CanYinShiPingList.add(lm15);
        CanYinShiPingList.add(lm16);
        CanYinShiPingList.add(lm17);


    }
}
