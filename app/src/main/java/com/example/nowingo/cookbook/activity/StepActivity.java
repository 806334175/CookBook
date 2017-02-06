package com.example.nowingo.cookbook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.adapter.StepViewPagerAdapter;
import com.example.nowingo.cookbook.base.BaseActivity;
import com.example.nowingo.cookbook.entity.Cook;
import com.example.nowingo.cookbook.manager.TopManager;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/21.
 */
public class StepActivity extends BaseActivity {
    ArrayList<View> arrayList;
    TextView textView_number,textView_step;
    ImageView imageView;


    View view_top;
    ViewPager viewPager;
    StepViewPagerAdapter stepViewPagerAdapter;
    ArrayList<Cook.ResultBean.DataBean.StepsBean> stepsBeanArrayList;

    int number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_step);
    }

    @Override
    public void inidata() {
        stepViewPagerAdapter = new StepViewPagerAdapter(this);
        arrayList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        stepsBeanArrayList = (ArrayList<Cook.ResultBean.DataBean.StepsBean>) bundle.getSerializable("data");
        number = bundle.getInt("number");
        for (int i = 0,n = stepsBeanArrayList.size(); i <n ; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.step_vp_item,null);
            textView_number = (TextView) view.findViewById(R.id.step_vp_item_number);
            textView_step = (TextView) view.findViewById(R.id.step_vp_item_step);
            imageView = (ImageView) view.findViewById(R.id.step_vp_item_iv);
            textView_number.setText((i+1)+"/"+n);
            textView_step.setText(stepsBeanArrayList.get(i).getStep());
            Glide.with(this).load(stepsBeanArrayList.get(i).getImg()).centerCrop().into(imageView);
            arrayList.add(view);
        }
        stepViewPagerAdapter.setArrayList(arrayList);
    }

    @Override
    public void iniview() {
        view_top = findViewById(R.id.activity_step_top);
        viewPager = (ViewPager) findViewById(R.id.activity_step_vp);
    }

    @Override
    public void setview() {
        TopManager.setTop(view_top,this,"制作步骤");
        viewPager.setAdapter(stepViewPagerAdapter);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setCurrentItem(number);
    }
}
