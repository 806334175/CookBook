package com.example.nowingo.cookbook.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.activity.CookActivity;
import com.example.nowingo.cookbook.adapter.SecondRVAdapter;
import com.example.nowingo.cookbook.db.CollectDbExpress;
import com.example.nowingo.cookbook.db.HistoryDbExpress;
import com.example.nowingo.cookbook.entity.Collect;
import com.example.nowingo.cookbook.entity.Cook;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class FragmentSecond extends Fragment {
    RecyclerView recyclerView;
    ImageView image_loading;
    RelativeLayout rl;
    TextView text;


    ArrayList<Cook.ResultBean.DataBean> arrayList;

    ArrayList<Collect> collectArrayList;

    ArrayList<Cook.ResultBean.DataBean> nowarrayList;
    SecondRVAdapter secondRVAdapter;
    LinearLayoutManager linearLayoutManager;
    int pn;
    int rn;

    Cook cook;
    HistoryDbExpress historyDbExpress;
    CollectDbExpress collectDbExpress;

    //判断是否上滑
    boolean isScroll;

    String title;
    String id;
    String find;

    AnimationDrawable animationDrawable;

    boolean isfresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collectDbExpress = new CollectDbExpress(getContext());
        historyDbExpress = new HistoryDbExpress(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_second_rv);
        image_loading = (ImageView) view.findViewById(R.id.fragment_second_iv_loading);
        rl = (RelativeLayout) view.findViewById(R.id.fragment_second_rl);
        text = (TextView) view.findViewById(R.id.fragment_second_tv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        secondRVAdapter = new SecondRVAdapter(getContext());
        recyclerView.setAdapter(secondRVAdapter);
        pn = 0;
        rn = 20;
        final Bundle bundle = getArguments();
        title = bundle.getString("title");
        id = bundle.getString("id");
        find = bundle.getString("find");
        switch (title){
            case "全部菜谱":
                animationDrawable = new AnimationDrawable();
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.loading_0),100);
                animationDrawable.addFrame(getResources().getDrawable(R.drawable.loading_1),100);
                animationDrawable.setOneShot(false);
                image_loading.setBackground(animationDrawable);
                animationDrawable.setOneShot(false);
                if (find == null) {
                    postAsynHttp(id, pn + "", rn + "");
                }else {
                    postAsynHttp2(id, pn + "", rn + "");
                }
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isfresh){

                        }else {
                            if (find == null) {
                                postAsynHttp(id, pn + "", rn + "");
                            }else {
                                postAsynHttp2(id, pn + "", rn + "");
                            }
                        }
                    }
                });
                break;
            case "最近浏览":
                secondRVAdapter.setFootState(3);
                arrayList = new ArrayList<>();
                collectArrayList = new ArrayList<>();
                collectArrayList = historyDbExpress.findall();
                if (collectArrayList.size()==0){
                    rl.setVisibility(View.VISIBLE);
                    text.setText("没有数据");
                }else {
                    rl.setVisibility(View.GONE);
                }
                for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                    Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                    arrayList.add(data);
                }
                secondRVAdapter.setArrayList(arrayList);
                secondRVAdapter.notifyDataSetChanged();

                secondRVAdapter.setOnItemClickLitener(new SecondRVAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(getContext(), CookActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("data",arrayList.get(postion));
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                break;
            case "我的收藏":
                secondRVAdapter.setFootState(3);
                arrayList = new ArrayList<>();
                collectArrayList = new ArrayList<>();
                collectArrayList = collectDbExpress.findall();
                if (collectArrayList.size()==0){
                    rl.setVisibility(View.VISIBLE);
                    text.setText("没有数据");
                }else {
                    rl.setVisibility(View.GONE);
                }
                for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                    Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                    arrayList.add(data);
                }
                secondRVAdapter.setArrayList(arrayList);
                secondRVAdapter.notifyDataSetChanged();

                secondRVAdapter.setOnItemClickLitener(new SecondRVAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(getContext(), CookActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("data",arrayList.get(postion));
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (title.equals("我的收藏")) {
            secondRVAdapter.setFootState(3);
            arrayList = new ArrayList<>();
            collectArrayList = new ArrayList<>();
            collectArrayList = collectDbExpress.findall();
            if (collectArrayList.size()==0){
                rl.setVisibility(View.VISIBLE);
                text.setText("没有数据");
            }else {
                rl.setVisibility(View.GONE);
            }
            for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                arrayList.add(data);
            }
            secondRVAdapter.setArrayList(arrayList);
            secondRVAdapter.notifyDataSetChanged();

            secondRVAdapter.setOnItemClickLitener(new SecondRVAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Intent intent = new Intent(getContext(), CookActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("data",arrayList.get(postion));
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            });
        }else if (title.equals("最近浏览")){
            secondRVAdapter.setFootState(3);
            arrayList = new ArrayList<>();
            collectArrayList = new ArrayList<>();
            collectArrayList = historyDbExpress.findall();
            if (collectArrayList.size()==0){
                rl.setVisibility(View.VISIBLE);
                text.setText("没有数据");
            }else {
                rl.setVisibility(View.GONE);
            }
            for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                arrayList.add(data);
            }
            secondRVAdapter.setArrayList(arrayList);
            secondRVAdapter.notifyDataSetChanged();

            secondRVAdapter.setOnItemClickLitener(new SecondRVAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Intent intent = new Intent(getContext(), CookActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("data",arrayList.get(postion));
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            });
        }
    }

    private void postAsynHttp(String id, String pn, String rn) {
        isfresh = true;
        animationDrawable.start();
        OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("cid",id)
                .add("pn",pn)
                .add("rn",rn)
                .add("key","9942e5cbcecfa4e513c30af8113a9a60")
                .build();
        Request request = new Request.Builder()
                .url("http://apis.juhe.cn/cook/index")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String str = response.body().string();
                Message message = new Message();
                message.what = 0;
                message.obj = str;
                handler.sendMessage(message);
            }
        });
    }

    private void postAsynHttp2(String id, String pn, String rn) {
        isfresh = true;
        animationDrawable.start();
        OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("menu",id)
                .add("pn",pn)
                .add("rn",rn)
                .add("key","9942e5cbcecfa4e513c30af8113a9a60")
                .build();
        Request request = new Request.Builder()
                .url("http://apis.juhe.cn/cook/query.php")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String str = response.body().string();
                Message message = new Message();
                message.what = 0;
                message.obj = str;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String str = (String) msg.obj;
                    try {
                        JSONObject jo=new JSONObject(str);
                        if (jo.getString("resultcode").equals("200")){
                            cook = new Gson().fromJson(str, Cook.class);
                            arrayList = new ArrayList<>();
                            arrayList = (ArrayList<Cook.ResultBean.DataBean>) cook.getResult().getData();
                            nowarrayList = secondRVAdapter.getArrayList();
                            for (int i = 0; i <arrayList.size() ; i++) {
                                nowarrayList.add(arrayList.get(i));
                            }
                            secondRVAdapter.setFootState(SecondRVAdapter.DATA_FINISH);
                            secondRVAdapter.setArrayList(nowarrayList);
                            secondRVAdapter.notifyDataSetChanged();
                            animationDrawable.stop();
                            rl.setVisibility(View.GONE);
                            isfresh = false;
                        }else {
                            Toast .makeText(getContext(),""+jo.getString("reason"),Toast.LENGTH_SHORT).show();
                            animationDrawable.stop();
                            image_loading.setImageResource(R.drawable.loaded_cry);
                            isfresh = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    secondRVAdapter.setOnItemClickLitener(new SecondRVAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int postion) {
                            Intent intent = new Intent(getContext(), CookActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("data",cook.getResult().getData().get(postion));
                            intent.putExtras(bundle1);
                            startActivity(intent);
                        }
                    });

                    //recyclerview设置滑动监听
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            //判断是否到了底部

                            if (linearLayoutManager.findLastVisibleItemPosition() == secondRVAdapter.getArrayList().size()) {

                                //2.代表惯性移动距离
                                //1.代表手指移动
                                //0.代表停止
                                switch (newState) {
                                    case 0:
                                        if (isScroll) {
                                            secondRVAdapter.setFootState(SecondRVAdapter.DATA_LOADING);
                                            secondRVAdapter.reFresh(nowarrayList.size());
                                            pn = pn + 20;
                                            rn = rn + 20;
                                            postAsynHttp(id,pn+"",rn+"");
                                        }
                                        break;
                                    case 1:
                                        isScroll = false;
                                        break;
                                    case 2:
                                        isScroll = true;
                                        break;
                                }
                            }
                        }
                    });
                    break;
                case 1:
                    animationDrawable.stop();
                    image_loading.setImageResource(R.drawable.loaded_cry);
                    isfresh = false;
                    break;
            }
        }
    };
}
