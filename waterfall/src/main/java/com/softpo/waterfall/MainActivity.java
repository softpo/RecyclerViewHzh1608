package com.softpo.waterfall;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.softpo.waterfall.widget.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    //图片名称
    private List<String> iconsName = new ArrayList<>();
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
        //1、设置布局管理器
        mRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        //3、装饰一下RecyclerView的条目
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent,
                               RecyclerView.State state) {
                super.onDraw(c, parent, state);

                Paint paint = new Paint();

                paint.setColor(Color.RED);

                paint.setStrokeWidth(2);

                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    //子控件
                    View recyclerItem = parent.getChildAt(i);

                    //描边，画线
                    //左边的边
                    c.drawLine(recyclerItem.getLeft(),recyclerItem.getTop(),
                            recyclerItem.getLeft(),recyclerItem.getBottom(),paint);
                    //右边的边
                    c.drawLine(recyclerItem.getRight(),recyclerItem.getTop(),
                            recyclerItem.getRight(),recyclerItem.getBottom(),paint
                            );

                    //上边
                    c.drawLine(recyclerItem.getLeft(),recyclerItem.getTop(),
                            recyclerItem.getRight(),recyclerItem.getTop(),paint);
                    //下边
                    c.drawLine(recyclerItem.getLeft(),recyclerItem.getBottom(),
                            recyclerItem.getRight(),recyclerItem.getBottom(),paint
                            );
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5,5,5,5);
            }
        });

        //2、设置适配器
        mAdapter = new RVAdapter(this,iconsName);
        mRecyclerView.setAdapter(mAdapter);

        //4、设置条目长按事件
        mAdapter.setOnItemLongClickListener(new RVAdapter.MyViewHolder.OnLongClickListener() {
            @Override
            public void onClick(View view) {
                //获取了recyclerItem
                final int position = mRecyclerView.getChildAdapterPosition(view);

                //
                int flag = (int) (Math.random() * 2);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                switch (flag){
                    case 0://删除该条目
                        builder.setTitle("删除");
                        builder.setMessage("您是否要删除该条目:"+iconsName.get(position));
                        builder.setNegativeButton("取消",null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iconsName.remove(position);

                                mAdapter.notifyItemRemoved(position);
                            }
                        });
                        break;
                    case 1://添加新的数据
                        builder.setTitle("插入");
                        builder.setMessage("您是否要在当前位置:"+position+"插入数据");
                        builder.setNegativeButton("取消",null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = (int) (Math.random()*30);
                                String name = iconsName.get(index);
                                iconsName.add(position,name);

                                mAdapter.notifyItemInserted(position);
                            }
                        });
                        break;

                }
                builder.create().show();

            }
        });

        //5、设置定制版动画
        CustomItemAnimator customItemAnimator = new CustomItemAnimator();

        //设置动画执行时间
        customItemAnimator.setAddDuration(1000);
        customItemAnimator.setRemoveDuration(1000);

        mRecyclerView.setItemAnimator(customItemAnimator);

    }

    private void initData() {
        String[] names = this.getResources().getStringArray(R.array.iconsName);

        for(String name:names){
           iconsName.add(name);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
