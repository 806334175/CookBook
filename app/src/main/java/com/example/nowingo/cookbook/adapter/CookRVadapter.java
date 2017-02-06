package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.entity.Cook;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/20.
 */
public class CookRVadapter extends RecyclerView.Adapter {
    public static final int TYPE_1=1;
    public static final int TYPE_2=2;
    public static final int TYPE_3=3;
    public static final int TYPE_4=4;
    public static final int TYPE_5=5;

    Context context;
    String img;
    String name;
    String collect;
    int collect_img;

    ArrayList<Cook.ResultBean.DataBean.StepsBean> arrayList;
    String imtro;
    String burden;

    public CookRVadapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Cook.ResultBean.DataBean.StepsBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Cook.ResultBean.DataBean.StepsBean> arrayList) {
        this.arrayList = arrayList;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public int getCollect_img() {
        return collect_img;
    }

    public void setCollect_img(int collect_img) {
        this.collect_img = collect_img;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                View view1 = LayoutInflater.from(context).inflate(R.layout.cook_type_1,parent,false);
                MyViewHolder_1 myViewHolder_1 = new MyViewHolder_1(view1);
                return myViewHolder_1;
            case 2:
                View view2 = LayoutInflater.from(context).inflate(R.layout.collect_and_share,parent,false);
                MyViewHolder_2 myViewHolder_2 = new MyViewHolder_2(view2);
                return myViewHolder_2;
            case 3:
                View view3 = LayoutInflater.from(context).inflate(R.layout.cook_type_3,parent,false);
                MyViewHolder_3 myViewHolder_3 = new MyViewHolder_3(view3);
                return myViewHolder_3;
            case 4:
                View view4 = LayoutInflater.from(context).inflate(R.layout.cook_type_4,parent,false);
                MyViewHolder_4 myViewHolder_4 = new MyViewHolder_4(view4);
                return myViewHolder_4;
            case 5:
                View view5 = LayoutInflater.from(context).inflate(R.layout.cook_type_5,parent,false);
                MyViewHolder_5 myViewHolder_5 = new MyViewHolder_5(view5);
                return myViewHolder_5;
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

    /**
     * 用回调机制写监听
     */
    public interface OnCollectClickLitener{
        void onCollectClick();
    }
    private OnCollectClickLitener onCollectClickLitener;

    public void setOnCollectClickLitener(OnCollectClickLitener onCollectClickLitener){
        this.onCollectClickLitener = onCollectClickLitener;
    }

    /**
     * 用回调机制写监听
     */
    public interface OnShareClickLitener{
        void onShareClick();
    }
    private OnShareClickLitener onShareClickLitener;

    public void setOnShareClickLitener(OnShareClickLitener onShareClickLitener){
        this.onShareClickLitener = onShareClickLitener;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder_1){
                Glide.with(context).load(getImg()).centerCrop().into(((MyViewHolder_1) holder).imageView);
            }else if (holder instanceof MyViewHolder_2){
                ((MyViewHolder_2) holder).ll_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCollectClickLitener.onCollectClick();
                    }
                });
                ((MyViewHolder_2) holder).ll_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShareClickLitener.onShareClick();
                    }
                });
                ((MyViewHolder_2) holder).textView_name.setText(getName());
                ((MyViewHolder_2) holder).textView_collext.setText(getCollect());
                ((MyViewHolder_2) holder).imageView.setImageResource(getCollect_img());
            }else if (holder instanceof MyViewHolder_3){
                ((MyViewHolder_3) holder).textView.setText(getImtro());
            }else if (holder instanceof MyViewHolder_4){
                ((MyViewHolder_4) holder).textView.setText(getBurden());
            }else if (holder instanceof MyViewHolder_5){
                ((MyViewHolder_5) holder).textView_number.setText(position-3+"");
                ((MyViewHolder_5) holder).textView_step.setText(arrayList.get(position-4).getStep());
                Glide.with(context).load(arrayList.get(position-4).getImg()).centerCrop().into(((MyViewHolder_5) holder).imageView);
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
        return arrayList.size()+4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_1;
        }
        if (position==1){
            return TYPE_2;
        }
        if (position==2){
            return TYPE_3;
        }
        if (position==3){
            return TYPE_4;
        }
        return TYPE_5;
    }

    class MyViewHolder_1 extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyViewHolder_1(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cook_type_1_iv);
        }
    }
    class MyViewHolder_2 extends RecyclerView.ViewHolder{
        TextView textView_name,textView_collext;
        ImageView imageView;
        LinearLayout ll_collect,ll_share;
        public MyViewHolder_2(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.collect_and_share_name);
            textView_collext = (TextView) itemView.findViewById(R.id.collect_and_share_collect_tv);
            imageView = (ImageView) itemView.findViewById(R.id.collect_and_share_collect_iv);
            ll_collect = (LinearLayout) itemView.findViewById(R.id.collect_and_share_collect);
            ll_share = (LinearLayout) itemView.findViewById(R.id.collect_and_share_share);
        }
    }
    class MyViewHolder_3 extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder_3(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cook_type_3_tv);
        }
    }
    class MyViewHolder_4 extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder_4(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cook_type_4_tv);
        }
    }
    class MyViewHolder_5 extends RecyclerView.ViewHolder{
        TextView textView_number,textView_step;
        ImageView imageView;
        public MyViewHolder_5(View itemView) {
            super(itemView);
            textView_number = (TextView) itemView.findViewById(R.id.cook_type_5_tv_number);
            textView_step = (TextView) itemView.findViewById(R.id.cook_type_5_tv_step);
            imageView = (ImageView) itemView.findViewById(R.id.cook_type_5_iv);
        }
    }
    //刷新指定的item
    public void reFresh(int position){
        notifyItemChanged(position);
    }
}
