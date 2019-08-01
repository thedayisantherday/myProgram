package com.example.zxg.myprogram.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.adapter.RecyclerViewAdapter;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.view.BaseFloorEntity;
import com.example.zxg.myprogram.view.LoopLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class LoopRecyclerviewActivity extends BaseActivity {

    private RecyclerView rv_loop;
    private RecyclerViewAdapter mAdapter;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_recyclerview);

        rv_loop = findViewById(R.id.rv_loop);

        LoopLayoutManager loopLayoutManager = new LoopLayoutManager();
        loopLayoutManager.setOrientation(LoopLayoutManager.VERTICAL);
        rv_loop.setLayoutManager(loopLayoutManager);
        List<BaseFloorEntity> mCommonModels = new ArrayList<BaseFloorEntity>();
        RecyclerViewAdapter.TestFloorData model = new RecyclerViewAdapter.TestFloorData(RecyclerViewAdapter.TestFloorData.FLOOR_TYPE_1, "1", "the first item", "{[Hello, this is the content of the first item]}");
        mCommonModels.add(model);
        model = new RecyclerViewAdapter.TestFloorData(RecyclerViewAdapter.TestFloorData.FLOOR_TYPE_2, "2", "the second item", "{[Hello, this is the content of the second item]}");
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

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                rv_loop.smoothScrollBy(rv_loop.getScrollX(), rv_loop.getScrollY() + 1);
            }
        };
        mHandler.postDelayed(mRunnable, 100);

        rv_loop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mHandler.postDelayed(mRunnable, 20);
            }
        });
    }
}
