package com.example.nowingo.cookbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class SecondViewPagerAdapter extends FragmentStatePagerAdapter {
    //fragment数据源
    ArrayList<Fragment> arrayList;
    //title数据源
    ArrayList<String> stringArrayList;

    public SecondViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ArrayList<Fragment> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Fragment> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        fragment = (Fragment) super.instantiateItem(container,position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //设置title
        return stringArrayList.get(position);
    }

}
