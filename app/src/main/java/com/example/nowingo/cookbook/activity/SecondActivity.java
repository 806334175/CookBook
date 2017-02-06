package com.example.nowingo.cookbook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.SecondViewPagerAdapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.fragment.FragmentSecond;
import com.example.nowingo.cookbook.manager.TopManager;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class SecondActivity extends BaseActivity {
    View top;
    TabLayout tabLayout;
    ViewPager viewPager;
    //fragment数据源
    ArrayList<Fragment> arrayList;
    //title数据源
    ArrayList<String> stringArrayList;
    SecondViewPagerAdapter secondViewPagerAdapter;
    FragmentManager fragmentManager;
    String title;
    String id;
    String find;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_second);
    }

    @Override
    public void inidata() {
        title = getIntent().getExtras().getString("title");
        id = getIntent().getExtras().getString("id");
        find = getIntent().getExtras().getString("find");
        fragmentManager = getSupportFragmentManager();
        secondViewPagerAdapter = new SecondViewPagerAdapter(fragmentManager);
        stringArrayList = new ArrayList<>();
        stringArrayList.add("全部菜谱");
        stringArrayList.add("最近浏览");
        stringArrayList.add("我的收藏");
        arrayList = new ArrayList<>();
        for (int i = 0; i <stringArrayList.size() ; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("title",stringArrayList.get(i));
            bundle.putString("id",id);
            bundle.putString("find",find);
            FragmentSecond fs = new FragmentSecond();
            fs.setArguments(bundle);
            arrayList.add(fs);
        }
    }

    @Override
    public void iniview() {
        top = findViewById(R.id.activity_second_top);
        tabLayout = (TabLayout) findViewById(R.id.activity_second_tablayout);
        viewPager = (ViewPager) findViewById(R.id.activity_second_viewpager);
    }

    @Override
    public void setview() {
        TopManager.setTop(top,this,title);
        secondViewPagerAdapter.setStringArrayList(stringArrayList);
        secondViewPagerAdapter.setArrayList(arrayList);
        //设置模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(secondViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
