package com.example.myyiji.DongTai;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myyiji.JiZhang.Activity_JiZhang;
import com.example.myyiji.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Activity_DongTai extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mediaPlayer = new MediaPlayer();
    SwipeRefreshLayout swipeRefresh;
    List<Integer> randlist = new ArrayList<>();
    List<DongTaiRecycleItemMessage> list_dongtai = new ArrayList<DongTaiRecycleItemMessage>();
    DongTaiRecycleViewMultipleAdapter adapter;
    DongTaiRecycleItemMessage[] dongtais;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_dongtai);
        initData();

        //设置视频播放权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        //初始化recycleview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dongtai_Recyclerview);
        initDongTai();
        adapter = new DongTaiRecycleViewMultipleAdapter(list_dongtai, Activity_DongTai.this);
        recyclerView.setAdapter(adapter);

        //设置recycleview的布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //工具栏设置
        Toolbar toolbar = (Toolbar)findViewById(R.id.dongtai_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //刷新操作
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDongTai();
            }
        });
        //设置底部工具栏跳转页面
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view_bottom);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){
                    case R.id.JiZhangButtom:
                        Intent intent = new Intent(Activity_DongTai.this, Activity_JiZhang.class);
                        startActivity(intent);
                        break;
                    case R.id.TongJiButtom:
                        break;
                    case R.id.DongTaiButtom:
                        Toast t = Toast.makeText((Context) Activity_DongTai.this,"你不就在在动态界面吗？点啥呢？！", Toast.LENGTH_LONG);
                        t.show();
                        break;
                    case R.id.wodebuttom:
                        break;
                }
                BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view_bottom);
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return true;
            }
        });
    }
    public void initData() {
        int[] pictures_0 = {};
        int[] pictures_1 = {R.drawable.p40};
        int[] pictures_2 = {R.drawable.p34,R.drawable.p35};
        int[] pictures_3 = {R.drawable.p41,R.drawable.p39,R.drawable.p38};
        int[] pictures_4 = {R.drawable.p50,R.drawable.p51,R.drawable.p53,R.drawable.p57};
        int[] pictures_5 = {R.drawable.p14,R.drawable.p18,R.drawable.p19,R.drawable.p20,R.drawable.p21};
        int[] pictures_6 = {R.drawable.p27,R.drawable.p28,R.drawable.p29,R.drawable.p30,R.drawable.p31,R.drawable.p32};
        int[] pictures_7 = {R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food1};
        int[] pictures_8 = {R.drawable.p82,R.drawable.p83,R.drawable.p84,R.drawable.p85,R.drawable.p86,R.drawable.p87,R.drawable.p88,R.drawable.p89};
        int[] pictures_9 = {R.drawable.p91,R.drawable.p92,R.drawable.p93,R.drawable.p94,R.drawable.p95,R.drawable.p96,R.drawable.p97,R.drawable.p98,R.drawable.p99};
        List<String> likelist1 = new ArrayList<>();
        likelist1.add("可乐汽水");
        likelist1.add("苹果香蕉");
        likelist1.add("西瓜草莓");
        Map<String,String>map1 = new HashMap<>();
        map1.put("可乐汽水","感觉还需要一杯汽水呢....");
        map1.put("我是个憨憨","真好");
        map1.put("心灵鸡汤导师陈涵","为你点赞，人生不就是这样吗？努力了才会有收获");
        Map<String,String>map2 = new HashMap<>();
        map2.put("迪迦奥特曼","竟然是个男的！！！");
        map2.put("我是个憨憨","真好，比心");
        map2.put("心灵鸡汤导师陈涵","我要转职做歌手去了！");
        Map<String,String>map3 = new HashMap<>();
        map3.put("赛罗奥特曼","竟然是个男的！！！");
        map3.put("萝莉控萝莉","siki");
        map3.put("没皮的皮皮虾","我顶不住了");
        Map<String,String>map4 = new HashMap<>();
        map4.put("我爱吃书","啦啦啦啦啦啦");
        map4.put("杯水车薪","siki");
        map4.put("琦玉老师","我想用拳头交流");
        Map<String,String>map5 = new HashMap<>();
        map5.put("外星人说外星话","*……￥T*&^&@^#@");
        map5.put("工口老师","嘿嘿嘿~~~~~");
        map5.put("没皮的皮皮虾","我顶不住了");
        Map<String,String>map6 = new HashMap<>();
        map6.put("菠萝爱西瓜","我该说的啥呢");
        map6.put("石渣","想着想着就睡着了");
        map6.put("灯光里面黑暗","我感动的要笑了");
        Map<String,String>map7 = new HashMap<>();
        map7.put("未命名","竟然是个男的！！！");
        map7.put("喜欢书也喜欢树","可以可以，很秀");
        map7.put("黄金一客","加油！！！");
        Map<String,String>map8 = new HashMap<>();
        map8.put("菠萝爱西瓜","我该说的啥呢");
        Map<String,String>map9 = new HashMap<>();
        map9.put("菠萝爱西瓜","我该说的啥呢");
        Map<String,String>map10 = new HashMap<>();
        map10.put("菠萝爱西瓜","我该说的啥呢");
        Map<String,String>map11 = new HashMap<>();
        map11.put("菠萝爱西瓜","我该说的啥呢");
        Map<String,String>map12 = new HashMap<>();
        map12.put("菠萝爱西瓜","我该说的啥呢");


        String content1_1 = "首先，我们需要明确这几点才能继续展开来说： 界面原型是什么？什么时候输出？有什么用途？" +
                "界面原型是需求的一种呈现方式，是当下沟通需求的主要方式；可视化是它的一大优势，好处在于" +
                "将文字需求转化成图形界面，直观地让各参与方（市场 /运营、程序猿、 UI设计师、测试工程师、客" +
                "服等）看到需求最终的实现效果，将各自对于需求的理解和预期统一标准化，在此基础上大家来讨" +
                "论需求，更有针对性，验证彼此对需求的理解是否一致，从而达到明确需求的目的；";
        String content1_2 = "提供顾客的有效信息，通常都是用手机号、邮箱、第三方社交账号，辅以其他信息来注册；由" +
                "于B2B产品会对顾客资质有要求，并且有角色细分，所以在注册时可能会要求顾客同时提供有效的" +
                "资质证明，平台审核通过后才算注册成功；在审核的时候可能会需要电话核实，而手机和邮箱选一" +
                "个就可以了，第三方社交账号，如 QQ 、微信、微博， B用户的社交圈中好友可能很多都不是做这" +
                "行的，即使导入社交圈可能用处也不大，所以只提供手机注册就可以了；";
        String content1_3 = "这就相当于在超市储物柜存东西一样，在存东西的那一刻服务员知道这是你的东西，但是如果不给" +
                "你一个标识牌，过 10分钟你回来取东西的时候，服务员怎么知道哪个是你的东西呢，那这个时候你" +
                "拿出标识牌，服务员就知道了";
        String content1_4 = "如果不注册就可以直接下单，就意味着产品中不会存储顾客的信息，那么顾客下单后怎么查看自己" +
                "的订单呢？产品怎么知道这个单是哪个顾客的呢？";
        String content1_5 = "这几张图片挺有意思的，所以我就发出来了。界面原型是在软件开发项目中需求分析阶段输出的，在开始画界面原型之前要做好产品规划，明确" +
                "项目的需求范围，即做哪些需求；而具体每个需求点达到什么程度，怎样做达到这个程度，有什么" +
                "样的规则，即有哪些功能点，业务逻辑是什么，有哪些约束条件；";
        String content1_6 = "表单由手机号、密码、身份、资质信息组成，由前台传送至后台，在后台的 “会员审核 ”页面出现，" +
                "由后台拥有审核权限的用户来审核；";
        String content1_7 = "交互就是产品与使用者之间的交流。通过什么方式呈现、呈现什么内容、在哪个步骤呈现，让使用者在操作过程中感觉轻松愉快，符合预期，避免等待、疑惑和担忧；以下有几个通用的原则可供参考：" +
                "更少的操作步骤" +
                "注册过程分三步走，第一步只是单纯地账户信息，第二步和第三步属于附加的注册补充资料填写，" +
                "很多事实已经验证了一个规律：操作每多一步，顾客流失率就放大一次，所以可以缩短这个注册流" +
                "程吗？";
        String content1_8 = "刚看完《我和我的祖国》电影，深有感慨啊，这每一个熟悉的情节，让我们回想起祖国每一个人激动人心的瞬间，我爱祖国，祖国母亲生日快乐！！！！！！";
        String content1_9 = "In the fictional television series Game of Thrones, based on the series of epic fantasy novels A Song of Ice " +
                "and Fire[1], three dragons are raised by Daenerys Targaryen, the “Mother of Dragons.” When hatched, the dragons are small, rough" +
                "ly 10 kg, and after a year grow to roughly 30-40 kg. They continue to grow throughout their life depending on the conditions and amo" +
                "unt of food available to them. For the purposes of this problem, consider these three fictional dragons are living today. Assume that" +
                " the basic biology of dragons described above is accurate. You will need tomake some additional assumptions about dragons that might i" +
                "nclude, for example,thatdragons are able to fly great distances, breath fire, and resist tremendous trauma. As you address the problem" +
                " requirements, it should be clear how your assumptions are related to the physical constraints of the functions, size, diet, changes, " +
                "or other characteristics associated with the animals. ";

        String content2 = "泠鸢yousa，粉丝爱称冷鸟，女，生日5月19日，身高148cm，四川宜宾人，长期居于成都，哔哩哔哩知名歌手UP主，不常在三次元露面，泠鸢yousa声音软萌多才多艺，填词、作曲、演唱、配音、绘画、后期全能。";
        String content3 = "视频（Video）泛指将一系列静态影像以电信号的方式加以捕捉、纪录、处理、储存、传送与" +
                "重现的各种技术。连续的图像变化每秒超过24帧（frame）画面以上时，根据视觉暂留原理，人眼无法" +
                "辨别单幅的静态画面；看上去是平滑连续的视觉效果，这样连续的画面叫做视频。视频技术最早是为了电视系统而发展，" +
                "但现在已经发展为各种不同的格式以利消费者将视频记录下来。网络技术的发达也促使视频的纪录片段以串流媒体的形式存在于因特网之上并可被...";
        String content4 = "1.语言的书写符号。它是人类最重要的辅助语言的工具。扩大了语言在时间和空间上的交际功用，对人类文明起很大的促进作用。2.文辞：～通顺。 ";
        dongtais = new DongTaiRecycleItemMessage[]{
                new DongTaiRecycleItemMessage(content1_1, "2019/09/01", "可乐汽水", R.drawable.p5, "", "", InitTextState(content1_1), pictures_1,-1,"", likelist1, map1),
                new DongTaiRecycleItemMessage(content1_2, "2019/09/02", "苹果香蕉", R.drawable.p7, "", "", InitTextState(content1_2), pictures_2,-1,"", likelist1, map2),
                new DongTaiRecycleItemMessage(content1_3, "2019/09/03", "平平安安", R.drawable.p10, "", "", InitTextState(content1_3), pictures_3,-1,"", likelist1, map3),
                new DongTaiRecycleItemMessage(content1_4, "2019/09/04", "浮生若梦", R.drawable.p13, "", "", InitTextState(content1_4), pictures_4, -1,"",likelist1, map4),
                new DongTaiRecycleItemMessage(content1_5, "2019/09/05", "似水年华", R.drawable.p15, "", "", InitTextState(content1_5), pictures_5,-1,"", likelist1, map5),
                new DongTaiRecycleItemMessage(content1_6, "2019/09/06", "津津乐道", R.drawable.p16, "", "", InitTextState(content1_6), pictures_6,-1,"", likelist1, map6),
                new DongTaiRecycleItemMessage(content1_7, "2019/09/07", "乐此不彼", R.drawable.p17, "", "", InitTextState(content1_7), pictures_7, -1,"",likelist1, map7),
                new DongTaiRecycleItemMessage(content1_8, "2019/09/08", "张飞&刘备", R.drawable.p45, "", "", InitTextState(content1_8), pictures_8,-1,"", likelist1, map8),
                new DongTaiRecycleItemMessage(content1_9, "2019/09/09", "我是个机器人", R.drawable.myloginlogo, "", "", InitTextState(content1_9), pictures_9,-1,"", likelist1, map1),
                new DongTaiRecycleItemMessage(content2, "2019/09/12", "周深", R.drawable.p10, "", "/storage/self/primary/netease/cloudmusic/Music/周深 - 卷珠帘.mp3", InitTextState(content2), pictures_0, -1,"",likelist1, map9),
                new DongTaiRecycleItemMessage(content2, "2019/09/12", "封茗囧菌", R.drawable.p7, "", "/storage/self/primary/netease/cloudmusic/Music/封茗囧菌 - 白露（节气物语系列）.mp3", InitTextState(content2), pictures_0, -1,"",likelist1, map10),
                new DongTaiRecycleItemMessage(content2, "2019/09/12", "冷鸟饲养员", R.drawable.p10, "", "/storage/self/primary/netease/cloudmusic/Music/锦零 - 夜宴风波【泠鸢·翻唱】（Cover：音阙诗听）.mp3", InitTextState(content2), pictures_0, -1,"",likelist1, map11),
                new DongTaiRecycleItemMessage(content2, "2019/09/12", "锁那", R.drawable.p7, "", "/storage/self/primary/netease/cloudmusic/Music/鎖那 - 可愛くなりたい.mp3", InitTextState(content2), pictures_0, -1,"",likelist1, map12),
                new DongTaiRecycleItemMessage(content3, "2019/09/13", "乃木坂46", R.drawable.p7, "/storage/self/primary/DCIM/ScreenRecorder/Screenrecorder-2022-03-14-09-48-30-617.mp4", "", InitTextState(content3), pictures_0,-1,"", likelist1, map1),
                new DongTaiRecycleItemMessage(content3, "2019/09/19", "我和我的祖国", R.drawable.p13, "/storage/self/primary/DCIM/ScreenRecorder/Screenrecorder-2022-03-14-10-10-29-182.mp4", "", InitTextState(content3), pictures_0, -1,"",likelist1, map12),
                new DongTaiRecycleItemMessage(content3, "2019/10/03", "我和我的祖国", R.drawable.p15, "/storage/self/primary/DCIM/ScreenRecorder/Screenrecorder-2022-03-14-10-13-00-244.mp4", "", InitTextState(content3), pictures_0,-1,"", likelist1, map3),
                new DongTaiRecycleItemMessage(content3, "2019/10/03", "鸡汤哥", R.drawable.p16, "", "", InitTextState(content3), pictures_0,R.drawable.p6,"如何学习更高效", likelist1, map4),
                new DongTaiRecycleItemMessage(content3, "2019/12/03", "鸡汤妹", R.drawable.p10, "", "", InitTextState(content3), pictures_0,R.drawable.p7,"继续跑，别停下", likelist1, map5),
                new DongTaiRecycleItemMessage(content3, "2019/1/03", "程序圆", R.drawable.myloginlogo, "", "", InitTextState(content3), pictures_0,R.drawable.food5,"熬夜是个错误的选择", likelist1, map6),
                new DongTaiRecycleItemMessage(content3, "2019/2/03", "心灵导师陈含", R.drawable.p17, "", "", InitTextState(content3), pictures_0,R.drawable.food2,"相信自己vs相信别人", likelist1, map7),
                new DongTaiRecycleItemMessage(content4, "2019/5/04", "我是个憨憨", R.drawable.p6, "", "", InitTextState(content4), pictures_0,-1,"", likelist1, map8)};
    }
    private void refreshDongTai(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initDongTai();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    private void initDongTai(){
        list_dongtai.clear();
        randlist.clear();
        while(randlist.size()<dongtais.length){
            Random random = new Random();
            int index = random.nextInt(dongtais.length);
            if(!randlist.contains(index)){
                randlist.add(index);
                list_dongtai.add(dongtais[index]);
            }
        }
    }
    private void initMediaPlayer(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.music_play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.music_pause:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.music_stop:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }
    protected void onDestory(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    public void onRequestPermissionResult(int requestCode ,String[] permission, int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else{
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public String InitTextState(String content){
        if(content.length()<113){
            return "";
        } else{
            return "全文";
        }
    }
}