package com.example.nowingo.cookbook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.CookRVadapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.db.CollectDbExpress;
import com.example.nowingo.cookbook.db.HistoryDbExpress;
import com.example.nowingo.cookbook.entity.Collect;
import com.example.nowingo.cookbook.entity.Cook;
import com.example.nowingo.cookbook.manager.TopManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by NowINGo on 2017/1/20.
 */
public class CookActivity extends BaseActivity {
    View view;

    View view_in;
    TextView textView_name;
    ImageView imageView;
    TextView textView_collext;
    LinearLayout linearLayout_collext,linearLayout_share;

    RecyclerView recyclerView;
    CookRVadapter cookRVadapter;
    ArrayList<Cook.ResultBean.DataBean.StepsBean> arrayList;
    LinearLayoutManager linearLayoutManager;
    LinearLayout ll;
    Cook.ResultBean.DataBean data;

    CollectDbExpress collectDbExpress;
    HistoryDbExpress historyDbExpress;
    String msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_cook);
    }

    @Override
    public void inidata() {
        collectDbExpress = new CollectDbExpress(this);
        historyDbExpress = new HistoryDbExpress(this);
        Bundle bundle = getIntent().getExtras();
        data = (Cook.ResultBean.DataBean) bundle.getSerializable("data");
        msg = new Gson().toJson(data);
        cookRVadapter = new CookRVadapter(this);
        if (historyDbExpress.ishave(msg)){
            historyDbExpress.delete(msg);
        }
        Collect collect = new Collect(msg);
        historyDbExpress.add(collect);

    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_cook_top);

        view_in = findViewById(R.id.activity_cook_include);
        textView_name = (TextView) view_in.findViewById(R.id.collect_and_share_name);
        imageView = (ImageView) view_in.findViewById(R.id.collect_and_share_collect_iv);
        textView_collext = (TextView) view_in.findViewById(R.id.collect_and_share_collect_tv);
        linearLayout_collext = (LinearLayout) view_in.findViewById(R.id.collect_and_share_collect);
        linearLayout_share = (LinearLayout) view_in.findViewById(R.id.collect_and_share_share);

        recyclerView = (RecyclerView) findViewById(R.id.activity_cook_rv);
        ll = (LinearLayout) findViewById(R.id.activity_cook_ll);
    }

    @Override
    public void setview() {
        TopManager.setTop(view,this,data.getTitle());
        textView_name.setText(data.getTitle());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cookRVadapter);
        cookRVadapter.setImg(data.getAlbums().get(0));
        cookRVadapter.setName(data.getTitle());
        cookRVadapter.setImtro(data.getImtro());
        cookRVadapter.setBurden(data.getBurden());
        arrayList = new ArrayList<>();
        arrayList = (ArrayList<Cook.ResultBean.DataBean.StepsBean>) data.getSteps();
        cookRVadapter.setArrayList(arrayList);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findFirstVisibleItemPosition() > 0){
                    ll.setVisibility(View.VISIBLE);
                }else {
                    ll.setVisibility(View.GONE);
                }
            }
        });

        cookRVadapter.setOnItemClickLitener(new CookRVadapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (postion>3){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data",arrayList);
                    bundle.putInt("number",postion-4);
                    startActivity(StepActivity.class,bundle);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (collectDbExpress.findone(msg)){
            cookRVadapter.setCollect_img(R.drawable.cang_red);
            cookRVadapter.setCollect("已收藏");
            cookRVadapter.reFresh(1);
            imageView.setImageResource(R.drawable.cang_red);
            textView_collext.setText("已收藏");
        }else {
            cookRVadapter.setCollect_img(R.drawable.cang);
            cookRVadapter.setCollect("收藏");
            cookRVadapter.reFresh(1);
            imageView.setImageResource(R.drawable.cang);
            textView_collext.setText("收藏");
        }

        linearLayout_collext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectDbExpress.findone(msg)){
                    collectDbExpress.delete(msg);
                    imageView.setImageResource(R.drawable.cang);
                    textView_collext.setText("收藏");

                    cookRVadapter.setCollect_img(R.drawable.cang);
                    cookRVadapter.setCollect("收藏");
                    cookRVadapter.reFresh(1);
                }else {
                    Collect collect = new Collect(msg);
                    collectDbExpress.add(collect);
                    imageView.setImageResource(R.drawable.cang_red);
                    textView_collext.setText("已收藏");

                    cookRVadapter.setCollect_img(R.drawable.cang_red);
                    cookRVadapter.setCollect("已收藏");
                    cookRVadapter.reFresh(1);
                }
            }
        });

        cookRVadapter.setOnCollectClickLitener(new CookRVadapter.OnCollectClickLitener() {
            @Override
            public void onCollectClick() {
                if (collectDbExpress.findone(msg)){
                    collectDbExpress.delete(msg);
                    imageView.setImageResource(R.drawable.cang);
                    textView_collext.setText("收藏");

                    cookRVadapter.setCollect_img(R.drawable.cang);
                    cookRVadapter.setCollect("收藏");
                    cookRVadapter.reFresh(1);
                }else {
                    Collect collect = new Collect(msg);
                    collectDbExpress.add(collect);
                    imageView.setImageResource(R.drawable.cang_red);
                    textView_collext.setText("已收藏");

                    cookRVadapter.setCollect_img(R.drawable.cang_red);
                    cookRVadapter.setCollect("已收藏");
                    cookRVadapter.reFresh(1);
                }
            }
        });




        linearLayout_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        cookRVadapter.setOnShareClickLitener(new CookRVadapter.OnShareClickLitener() {
            @Override
            public void onShareClick() {
                showShare();
            }
        });
    }

    /**
     *
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(data.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来品尝！");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(data.getAlbums().get(0));
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("非常好吃");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("NewsDay");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.baidu.com");

// 启动分享GUI
        oks.show(this);
    }

    /**
     *
     */

}
