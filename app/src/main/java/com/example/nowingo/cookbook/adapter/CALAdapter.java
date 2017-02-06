package com.example.nowingo.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.cookbook.R;
import com.example.nowingo.cookbook.entity.Cook;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/22.
 */
public class CALAdapter extends RecyclerView.Adapter<CALAdapter.MyViewHolder> {
    Context context;
    ArrayList<Cook.ResultBean.DataBean> arrayList;

    public CALAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Cook.ResultBean.DataBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Cook.ResultBean.DataBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_rv_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getAlbums().get(0)).centerCrop().into(holder.imageView);
        holder.textView_title.setText(arrayList.get(position).getTitle());
        holder.textView_tags.setText(arrayList.get(position).getTags());
        holder.textView_ingredients.setText(arrayList.get(position).getIngredients());
        holder.textView_burden.setText(arrayList.get(position).getBurden());
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
        return arrayList.size();
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
}
