package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.entity.Cook;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/19.
 */
public class SecondRVAdapter extends RecyclerView.Adapter {
    public static final int TYPE_ITEM=1;
    public static final int TYPE_BOTTOM=2;

    public static final int DATA_LOADING=0;//数据加载中
    public static final int DATA_FINISH=1;//数据加载完成
    public static final int DATA_NOMORE=2;//没有更多数据
    //尾布局状态
    int FootState;

    Context context;
    ArrayList<Cook.ResultBean.DataBean> arrayList;

    public SecondRVAdapter(Context context) {
        this.context = context;
        //初始化尾布局状态
        FootState = DATA_FINISH;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Cook.ResultBean.DataBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Cook.ResultBean.DataBean> arrayList) {
        this.arrayList = arrayList;
    }

    public int getFootState() {
        return FootState;
    }

    public void setFootState(int footState) {
        FootState = footState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_ITEM:
                View view = LayoutInflater.from(context).inflate(R.layout.second_rv_item,null);
                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;
            case TYPE_BOTTOM:
                View view1 = LayoutInflater.from(context).inflate(R.layout.second_rv_foot,null);
                MyViewHolderFoot myFootViewHolder = new MyViewHolderFoot(view1);
                return myFootViewHolder;
        }
        return null;
    }

    /**
     * 用回调机制写监听
     */
    public interface OnItemClickLitener{
        void onItemClick(View view,int postion);
    }
    private OnItemClickLitener onItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.onItemClickLitener = onItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (arrayList.size()==0){}else {
            if (holder instanceof MyViewHolder) {
                Glide.with(context).load(arrayList.get(position).getAlbums().get(0)).centerCrop().into(((MyViewHolder) holder).imageView);
                ((MyViewHolder) holder).textView_title.setText(arrayList.get(position).getTitle());
                ((MyViewHolder) holder).textView_tags.setText(arrayList.get(position).getTags());
                ((MyViewHolder) holder).textView_ingredients.setText(arrayList.get(position).getIngredients());
                ((MyViewHolder) holder).textView_burden.setText(arrayList.get(position).getBurden());
            } else if (holder instanceof MyViewHolderFoot) {
                //当只有尾部时，不加载尾部
                if (position == 0){
                    Log.i("msg","当只有尾部时，不加载尾部");
                    ((MyViewHolderFoot) holder).textView.setVisibility(View.GONE);
                    ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                }else {
                    switch (getFootState()) {
                        //加载完成的尾部状态
                        case DATA_FINISH:
                            ((MyViewHolderFoot) holder).textView.setVisibility(View.VISIBLE);
                            ((MyViewHolderFoot) holder).textView.setText("加载更多");
                            ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                            break;
                        //加载中的尾部状态
                        case DATA_LOADING:
                            ((MyViewHolderFoot) holder).textView.setVisibility(View.GONE);
                            ((MyViewHolderFoot) holder).progressBar.setVisibility(View.VISIBLE);
                            break;
                        //没有更多的尾部状态
                        case DATA_NOMORE:
                            ((MyViewHolderFoot) holder).textView.setVisibility(View.VISIBLE);
                            ((MyViewHolderFoot) holder).textView.setText("没有更多");
                            ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                            break;
                        case 3:
                            ((MyViewHolderFoot) holder).textView.setVisibility(View.GONE);
                            ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }

        if (onItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.size()==0){
            return TYPE_BOTTOM;
        }else {
            if (position == arrayList.size()) {
                return TYPE_BOTTOM;
            }
            return TYPE_ITEM;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView_title,textView_tags,textView_ingredients,textView_burden;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.second_rv_item_iv);
            textView_title = (TextView) itemView.findViewById(R.id.second_rv_item_tv_title);
            textView_tags = (TextView) itemView.findViewById(R.id.second_rv_item_tv_tags);
            textView_ingredients = (TextView) itemView.findViewById(R.id.second_rv_item_tv_ingredients);
            textView_burden = (TextView) itemView.findViewById(R.id.second_rv_item_tv_burden);
        }
    }

    class MyViewHolderFoot extends RecyclerView.ViewHolder{
        TextView textView;
        ProgressBar progressBar;
        public MyViewHolderFoot(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.second_rv_foot_tv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.second_rv_foot_pro);
        }
    }

    //刷新指定的item
    public void reFresh(int position){
        notifyItemChanged(position);
    }
}
