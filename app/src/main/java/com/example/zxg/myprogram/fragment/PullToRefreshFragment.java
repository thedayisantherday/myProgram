package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.widget.pushToRefresh.PullToRefreshLayout;

/**
 * Created by zxg on 16/11/29.
 */
public class PullToRefreshFragment extends BaseFragment {

    private static String TAG = PullToRefreshFragment.class.getSimpleName();

    private PullToRefreshLayout mPullToRefreshLayout;
    private WebView wv_pulltorefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pulltorefresh, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        mPullToRefreshLayout = (PullToRefreshLayout) mActivity.findViewById(R.id.pulltorefresh);
        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                LogUtils.i(TAG, "mPullToRefreshLayout is refresh");
                mPullToRefreshLayout.refreshFinish(1);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                LogUtils.i(TAG, "mPullToRefreshLayout is loadmore");
                mPullToRefreshLayout.refreshFinish(0);
            }
        });

        wv_pulltorefresh = (WebView) mActivity.findViewById(R.id.wv_pulltorefresh);

    }
}
