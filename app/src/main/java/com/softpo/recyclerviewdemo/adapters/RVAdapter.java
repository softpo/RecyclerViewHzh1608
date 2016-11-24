package com.softpo.recyclerviewdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softpo.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by softpo on 2016/11/24.
 */

public class RVAdapter extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {
    private List<String> data ;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public RVAdapter(List<String> data, Context context) {
        this.data = data;
        mContext = context;
    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView =
                LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);

        MyViewHolderOne holder = new MyViewHolderOne(itemView);

        return holder;
    }

    //bind绑定，绑定ViewHolder，ViewHolder中有子控件，进行赋值
    //参数一holder，参数二position
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolderOne holderOne = (MyViewHolderOne) holder;
        holderOne.showInfo.setText(data.get(position));

        //设置Tag,没有进行刷新，
        holderOne.showInfo.setTag(position);

        holderOne.showInfo.setOnClickListener(this);

        holderOne.showInfo.setOnLongClickListener(this);


    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        switch (v.getId()){
            case R.id.showInfo:
                //接口回调，将position传给了MainActivity
                mOnItemClickListener.itemClick(v);
//                Toast.makeText(mContext, "点击的内容是： "+data.get(position), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    //长按的监听
    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public static class MyViewHolderOne extends RecyclerView.ViewHolder{
        public TextView showInfo;
        //构造方法，View，itemView：RecyclerView 条目的布局的视图
        public MyViewHolderOne(View itemView) {
            super(itemView);
            //findViewById放在super(itemView)下面
            showInfo = (TextView) itemView.findViewById(R.id.showInfo);
        }
    }

    public interface OnItemClickListener{
        void itemClick(View view);
    }
}
