package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.entity.Menus;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/23.
 */
public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.MyMenuViewHolder> {
    ArrayList<Menus.ResultBean> arrayList;
    ArrayList<Menus.ResultBean.ListBean> list;
    MenuGridViewAdapter menuGridViewAdapter;
    Context context;

    public MenuRVAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Menus.ResultBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Menus.ResultBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_rv_item,null);
        MyMenuViewHolder myMenuViewHolder = new MyMenuViewHolder(view);
        return myMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(MyMenuViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position).getName());
        menuGridViewAdapter = new MenuGridViewAdapter(context);
        list = new ArrayList<>();
        list = (ArrayList<Menus.ResultBean.ListBean>) arrayList.get(position).getList();
        menuGridViewAdapter.setArrayList(list);
        holder.gridView.setAdapter(menuGridViewAdapter);
        set(holder.gridView,list.size());
//        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("msg",list.get(position).getName());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyMenuViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        GridView gridView;
        public MyMenuViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.menu_rv_item_tv);
            gridView = (GridView) itemView.findViewById(R.id.menu_rv_item_gv);
        }
    }

    private void set(GridView gridView,int size){
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null){
            return;
        }
        int totalHeight = 0;
        int lineNum = 0;
        if (size%4>0){
            lineNum = (size/4)+1;
        }else {
            lineNum = size/4;
        }

        View item = listAdapter.getView(0,null,gridView);
        item.measure(0,0);
        totalHeight = item.getMeasuredHeight()*lineNum;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        ((ViewGroup.MarginLayoutParams) params).setMargins(10,10,10,10);
        gridView.setLayoutParams(params);
    }
}
