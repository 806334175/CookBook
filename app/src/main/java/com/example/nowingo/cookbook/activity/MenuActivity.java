package com.example.nowingo.cookbook.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.MenuRVAdapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.entity.Menus;
import com.example.nowingo.cookbook.manager.TopManager;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/23.
 */
public class MenuActivity extends BaseActivity {
    View view;
    ArrayList<Menus.ResultBean> arrayList;
    RecyclerView recyclerView;
    MenuRVAdapter menuRVAdapter;
    TabLayout tabLayout;
    LinearLayoutManager llm;
    boolean move;
    private int mIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_menu);
    }

    @Override
    public void inidata() {
        llm = new LinearLayoutManager(this);
        Menus menus = (Menus) getIntent().getExtras().getSerializable("menu");
        arrayList = (ArrayList<Menus.ResultBean>) menus.getResult();
        menuRVAdapter = new MenuRVAdapter(this);
    }

    @Override
    public void iniview() {

        view = findViewById(R.id.activity_menu_top);
        tabLayout = (TabLayout) findViewById(R.id.activity_menu_tab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_menu_rv);
    }

    @Override
    public void setview() {
        TopManager.setTop(view,this,"全部菜谱");
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cf1919"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#cf1919"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i <arrayList.size() ; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(arrayList.get(i).getName()));
        }
        menuRVAdapter.setArrayList(arrayList);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(menuRVAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                moveToPosition(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动（最后的100米！）
                if (move ){
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - llm.findFirstVisibleItemPosition();
                    if ( 0 <= n && n < recyclerView.getChildCount()){
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerView.getChildAt(n).getTop();
                        //最后的移动
                        recyclerView.scrollBy(0, top);
                    }
                }

                tabLayout.getTabAt(llm.findFirstVisibleItemPosition()).select();
            }
        });
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = llm.findFirstVisibleItemPosition();
        int lastItem = llm.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }
}
