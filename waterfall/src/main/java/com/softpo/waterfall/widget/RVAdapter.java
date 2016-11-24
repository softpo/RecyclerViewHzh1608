package com.softpo.waterfall.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softpo.waterfall.R;

import java.util.List;

/**
 * Created by softpo on 2016/11/24.
 */

public class RVAdapter extends RecyclerView.Adapter implements View.OnLongClickListener {

    private Context mContext;
    private List<String> mData;
    private MyViewHolder.OnLongClickListener mOnItemLongClickListener;

    public RVAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);

        itemView.setOnLongClickListener(this);

        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder) holder;

        holder1.showName.setText(mData.get(position));

        holder1.mImageView.setImageResource(mContext.getResources()
                .getIdentifier(mData.get(position),"mipmap",mContext.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return mData!=null?mData.size():0;
    }

    public void setOnItemLongClickListener(MyViewHolder.OnLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        //将点击的recyclerItem外传
        mOnItemLongClickListener.onClick(v);
        return false;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView showName;
        public MyViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.showImage);
            showName = (TextView) itemView.findViewById(R.id.showName);

        }
        //条目的长按事件
        public interface OnLongClickListener{
            void onClick(View view);
        }
    }
}
