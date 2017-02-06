package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.activity.SecondActivity;
import com.example.nowingo.cookbook.entity.Menus;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/23.
 */
public class MenuGridViewAdapter extends BaseAdapter {
    ArrayList<Menus.ResultBean.ListBean> arrayList;
    Context context;

    public MenuGridViewAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Menus.ResultBean.ListBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Menus.ResultBean.ListBean> arrayList) {
        this.arrayList = arrayList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_gridview_item,null);
            vh.textView = (TextView) convertView.findViewById(R.id.menu_gridview_item_tv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textView.setText(arrayList.get(position).getName());
        vh.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SecondActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",arrayList.get(position).getName());
                bundle.putString("id",arrayList.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
