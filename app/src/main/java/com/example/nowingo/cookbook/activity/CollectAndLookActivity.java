package com.example.nowingo.cookbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.CALAdapter;
import com.example.nowingo.cookbook.adapter.SecondRVAdapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.db.CollectDbExpress;
import com.example.nowingo.cookbook.db.HistoryDbExpress;
import com.example.nowingo.cookbook.entity.Collect;
import com.example.nowingo.cookbook.entity.Cook;
import com.example.nowingo.cookbook.manager.TopManager;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/22.
 */
public class CollectAndLookActivity extends BaseActivity {
    ArrayList<Cook.ResultBean.DataBean> arrayList;
    ArrayList<Collect> collectArrayList;
    CALAdapter calAdapter;
    RecyclerView recyclerView;
    View view;
    HistoryDbExpress historyDbExpress;
    CollectDbExpress collectDbExpress;

    RelativeLayout relativeLayout;
    String title;
    int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_collect_and_look);
    }

    @Override
    public void inidata() {
        historyDbExpress = new HistoryDbExpress(this);
        collectDbExpress = new CollectDbExpress(this);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        i = bundle.getInt("int");
        calAdapter = new CALAdapter(this);
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_collect_and_look_top);
        recyclerView = (RecyclerView) findViewById(R.id.activity_collect_and_look_rv);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_collect_and_look_rl);
    }

    @Override
    public void setview() {
        TopManager.setTop(view,this,title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(calAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (i){
            case 0:
                arrayList = new ArrayList<>();
                collectArrayList = new ArrayList<>();
                collectArrayList = collectDbExpress.findall();
                if (collectArrayList.size()==0){
                    relativeLayout.setVisibility(View.VISIBLE);
                }else {
                    relativeLayout.setVisibility(View.GONE);
                }
                for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                    Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                    arrayList.add(data);
                }
                calAdapter.setArrayList(arrayList);
                calAdapter.notifyDataSetChanged();

                calAdapter.setOnItemClickLitener(new CALAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(CollectAndLookActivity.this, CookActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("data",arrayList.get(postion));
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                arrayList = new ArrayList<>();
                collectArrayList = new ArrayList<>();
                collectArrayList = historyDbExpress.findall();
                if (collectArrayList.size()==0){
                    relativeLayout.setVisibility(View.VISIBLE);
                }else {
                    relativeLayout.setVisibility(View.GONE);
                }
                for (int i = collectArrayList.size()-1,n =0 ; n<=i ; i--) {
                    Cook.ResultBean.DataBean data = new Gson().fromJson(collectArrayList.get(i).getMsg(), Cook.ResultBean.DataBean.class);
                    arrayList.add(data);
                }
                calAdapter.setArrayList(arrayList);
                calAdapter.notifyDataSetChanged();

                calAdapter.setOnItemClickLitener(new CALAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        Intent intent = new Intent(CollectAndLookActivity.this, CookActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("data",arrayList.get(postion));
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                break;
        }
    }
}
