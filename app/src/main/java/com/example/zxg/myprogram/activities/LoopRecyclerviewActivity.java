package com.example.zxg.myprogram.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.adapter.RecyclerViewAdapter;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.DPIUtil;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.view.BaseFloorEntity;
import com.example.zxg.myprogram.view.LoopLayoutManager;
import com.example.zxg.myprogram.widget.CustomProgressBar;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class LoopRecyclerviewActivity extends BaseActivity {

    private RecyclerView rv_loop;
    private RecyclerViewAdapter mAdapter;
    private CustomProgressBar view_custom_progressbar;

    private boolean isDragging = false;
    private int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_recyclerview);

        rv_loop = findViewById(R.id.rv_loop);
        view_custom_progressbar = findViewById(R.id.view_custom_progressbar);
        view_custom_progressbar.setMaskImg(R.drawable.custom_progressbar_mask);
        view_custom_progressbar.setText("12345fgH");
        view_custom_progressbar.setTextSize(DPIUtil.dip2px(this, 12));
        view_custom_progressbar.setProgress(1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view_custom_progressbar.setProgress(5);
                view_custom_progressbar.postInvalidate();
            }
        },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view_custom_progressbar.setProgress(50);
                view_custom_progressbar.postInvalidate();
            }
        },2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view_custom_progressbar.setProgress(98);
                view_custom_progressbar.postInvalidate();
            }
        },3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view_custom_progressbar.setProgress(100);
                view_custom_progressbar.postInvalidate();
            }
        },4000);

        LoopLayoutManager loopLayoutManager = new LoopLayoutManager(LoopRecyclerviewActivity.this);
//        loopLayoutManager.setOrientation(LoopLayoutManager.VERTICAL);
        loopLayoutManager.setMillisecondsPerPx(2.0f);
        rv_loop.setLayoutManager(loopLayoutManager);
        List<BaseFloorEntity> mCommonModels = new ArrayList<BaseFloorEntity>();
        RecyclerViewAdapter.TestFloorData model = new RecyclerViewAdapter.TestFloorData(RecyclerViewAdapter.TestFloorData.FLOOR_TYPE_1, "1", "the first item", "{[Hello, this is the content of the first item]}");
        mCommonModels.add(model);
        model = new RecyclerViewAdapter.TestFloorData(RecyclerViewAdapter.TestFloorData.FLOOR_TYPE_2, "2", "the second item", "{[Hello, this is the content of the second item]}");
        mCommonModels.add(model);
        model = new RecyclerViewAdapter.TestFloorData(RecyclerViewAdapter.TestFloorData.FLOOR_TYPE_1, "1", "the first item", "{[Hello, this is the content of the first item]}");
        mCommonModels.add(model);
        mAdapter = new RecyclerViewAdapter(mCommonModels, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RecyclerView item点击事件
                LogUtils.i(TAG, "RecyclerView item clicked!");
                AnimUtils.doSceneTransitionAnim(LoopRecyclerviewActivity.this,
                        AnimCompatActivity.class, view.findViewById(R.id.tv_code), "transition_tag");
            }
        });
        rv_loop.setAdapter(mAdapter);

        rv_loop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtils.i("onScrollStateChanged", "newState--------------" + newState);
                /*if (newState == SCROLL_STATE_IDLE) {
                    rv_loop.smoothScrollBy(rv_loop.getScrollX() + 1, rv_loop.getScrollY());
                }*/
                if (newState == SCROLL_STATE_IDLE) {
                    if (isDragging) {
                        isDragging = false;
                        rv_loop.smoothScrollBy(rv_loop.getScrollX() + 10, rv_loop.getScrollY());
                    } else {
                        position = (++position) % mAdapter.getItemCount();
                        LogUtils.i("onScrollStateChanged", "position : " + position);
                        rv_loop.smoothScrollToPosition(position);
                    }
                } else if (newState == SCROLL_STATE_DRAGGING) {
                    isDragging = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                rv_loop.smoothScrollBy(rv_loop.getScrollX() + 1, rv_loop.getScrollY());

                /*position++;
                LogUtils.i("onScrolled", "position : " + position);
                rv_loop.smoothScrollToPosition(position);*/
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rv_loop.smoothScrollToPosition(position);
            }
        }, 100);
    }
}
