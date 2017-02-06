package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.entity.Menus;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/15.
 */
public class MainGridViewAdapter extends BaseAdapter {
    ArrayList<Menus.ResultBean.ListBean> arrayList;
    ArrayList<Integer> arrayList_iv;
    Context context;

    public MainGridViewAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
        arrayList_iv = new ArrayList<>();
    }

    public ArrayList<Menus.ResultBean.ListBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Menus.ResultBean.ListBean> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Integer> getArrayList_iv() {
        return arrayList_iv;
    }

    public void setArrayList_iv(ArrayList<Integer> arrayList_iv) {
        this.arrayList_iv = arrayList_iv;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.main_gridview_item,null);
            vh.imageView = (ImageView) convertView.findViewById(R.id.main_gridview_item_iv);
            vh.textView = (TextView) convertView.findViewById(R.id.main_gridview_item_tv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.imageView.setImageResource(arrayList_iv.get(position));
        vh.textView.setText(arrayList.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
