package com.softpo.recyclerviewdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

import com.softpo.recyclerviewdemo.adapters.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mData;
    private RVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();

        initRecyclerView();

    }

    private void initRecyclerView() {
        //RecyclerView可以展示ListView，GriView，瀑布流
        //1、设置样式setLayoutManager
        //1.1:ListView样式
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

//        1.2 ：GriView样式
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                this,
                3,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //GridLayoutManager，设置列宽
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if(position==0||position==7||position==11){
                    return 2;
                }else if(position%5 ==3) {
                    return 3;
                }
                return 1;
            }
        });
        //适配器
        mAdapter = new RVAdapter(mData,this);
        mRecyclerView.setAdapter(mAdapter);

        //对RecyclerView添加动画,添加默认的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置条目点击事件
        //该方法需要监听接口
        mAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view) {//这里的View就是RVAdapter中点击的TextView，

                // recycle_item
                ViewParent parent = view.getParent();

                View recyclerItem = (View) parent;

                //recyclerItem作为子控件展示到RecyclerView中
                final int position = (int) mRecyclerView
                        .getChildAdapterPosition(recyclerItem);

                //删除该条目
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("提示：");

                builder.setMessage("您是否要删除内容："+mData.get(position)+"?");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        //该条数据，从数据源移出
                        mData.remove(position);

                        //adapter,全局
                        //可以添加删除的动画
//                        mAdapter.notifyDataSetChanged();
                        mAdapter.notifyItemRemoved(position);


//                        mData.add(position,"蚕丛及鱼凫开国何茫然");
//
//                        mAdapter.notifyItemInserted(position);
                    }
                });
                builder.create().show();
            }
        });

//        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
//            @Override
//            public void onViewRecycled(RecyclerView.ViewHolder holder) {
//                CharSequence info = ((RVAdapter.MyViewHolderOne) holder).showInfo.getText();
//                Toast.makeText(MainActivity.this, "点击的内容是："+info, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("蜀道之难难于上青天"+i);
        }
    }
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
