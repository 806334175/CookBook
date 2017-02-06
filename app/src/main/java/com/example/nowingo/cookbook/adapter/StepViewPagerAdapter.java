package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/21.
 */
public class StepViewPagerAdapter extends PagerAdapter {
    ArrayList<View> arrayList;
    Context context;

    public StepViewPagerAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<View> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<View> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    //判断有没有显示错
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //创建以及布局
    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View view=arrayList.get(position);//获得要显示的布局
        parent.addView(view);//把布局加到Viewpager的控件组里面
        return view;//显示要显示的布局
    }


    //销毁方法
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=arrayList.get(position);//获得要被销毁的view
        container.removeView(view);//执行从父控件组中的移除
    }

}
