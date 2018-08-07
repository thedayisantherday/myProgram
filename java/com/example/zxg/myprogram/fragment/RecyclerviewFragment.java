package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.AnimCompatActivity;
import com.example.zxg.myprogram.adapter.RecyclerViewAdapter;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.LogUtils;

/**
 * Created by zxg on 2016/11/7.
 * QQ:1092885570
 */

public class RecyclerviewFragment extends BaseFragment{

    private RecyclerView rv_recyclerview;
    private RecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        rv_recyclerview = (RecyclerView) mActivity.findViewById(R.id.rv_recyclerview);
        //使RecyclerView保持固定的大小，用于自身的优化
        rv_recyclerview.setHasFixedSize(true);
        rv_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RecyclerViewAdapter();
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //RecyclerView item点击事件
                LogUtils.i(TAG, "RecyclerView item clicked!");
                AnimUtils.doSceneTransitionAnim(mActivity,
                        AnimCompatActivity.class, view.findViewById(R.id.tv_code), "transition_tag");
            }
        });
        rv_recyclerview.setAdapter(mAdapter);
        rv_recyclerview.setItemAnimator(new DefaultItemAnimator());
    }
}
