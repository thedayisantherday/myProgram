package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.AndroidH5Activity;
import com.example.zxg.myprogram.activities.AutoClickActivity;
import com.example.zxg.myprogram.activities.BluetoothActivity;
import com.example.zxg.myprogram.activities.TouchEventActivity;
import com.example.zxg.myprogram.activities.WebviewActivity;
import com.example.zxg.myprogram.common.ThreeTuple;
import com.example.zxg.myprogram.common.TupleUtil;
import com.example.zxg.myprogram.netapi.api.LoginApi;
import com.example.zxg.myprogram.utils.AnimUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.utils.NetUtils;
import com.example.zxg.myprogram.utils.SysUtils;
import com.example.zxg.myprogram.utils.TimeUtils;
import com.example.zxg.myprogram.utils.XUtilsTools;

/**
 * description ：
 * Created by zhuxiaoguang at 18:37 on 2019/8/30
 */
public class FunctionsFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private Button btn_android_h5,
            btn_webview,
            btn_touch_event,
            btn_bluetooth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_functions, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        btn_android_h5 = (Button)rootView.findViewById(R.id.btn_android_h5);
        btn_android_h5.setOnClickListener(this);

        btn_webview = (Button)rootView.findViewById(R.id.btn_webview);
        btn_webview.setOnClickListener(this);

        btn_touch_event = (Button) rootView.findViewById(R.id.btn_touch_event);
        btn_touch_event.setOnClickListener(this);

        btn_bluetooth = (Button) rootView.findViewById(R.id.btn_bluetooth);
        btn_bluetooth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_android_h5:
                AnimUtils.doScaleUpAnim(mActivity, AndroidH5Activity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_webview:
                AnimUtils.doCustomAnim(mActivity,
                        WebviewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_touch_event:
                AnimUtils.doCustomAnim(mActivity,
                        TouchEventActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_bluetooth:
                AnimUtils.doCustomAnim(mActivity,
                        BluetoothActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
    }
}
