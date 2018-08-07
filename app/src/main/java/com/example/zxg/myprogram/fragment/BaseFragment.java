package com.example.zxg.myprogram.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * Created by zxg on 2016/11/7.
 * QQ:1092885570
 */

public class BaseFragment extends Fragment {

    protected static String TAG = BaseFragment.class.getSimpleName();

    public Activity mActivity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
    }
}
