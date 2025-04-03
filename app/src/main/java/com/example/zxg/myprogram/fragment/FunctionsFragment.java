package com.example.zxg.myprogram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.AndroidH5Activity;
import com.example.zxg.myprogram.activities.BluetoothControlActivity;
import com.example.zxg.myprogram.activities.BluetoothScanActivity;
import com.example.zxg.myprogram.activities.NetworkActivity;
import com.example.zxg.myprogram.activities.TouchEventActivity;
import com.example.zxg.myprogram.activities.WebviewActivity;
import com.example.zxg.myprogram.utils.AnimUtils;

/**
 * description ：
 * Created by zhuxiaoguang at 18:37 on 2019/8/30
 */
public class FunctionsFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private Button btn_android_h5,
            btn_webview,
            btn_network,
            btn_touch_event,
            btn_bluetooth_control,
            btn_bluetooth_scan;

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

        btn_network = (Button)rootView.findViewById(R.id.btn_network);
        btn_network.setOnClickListener(this);

        btn_touch_event = (Button) rootView.findViewById(R.id.btn_touch_event);
        btn_touch_event.setOnClickListener(this);

        btn_bluetooth_control = (Button) rootView.findViewById(R.id.btn_bluetooth_control);
        btn_bluetooth_control.setOnClickListener(this);

        btn_bluetooth_scan = (Button) rootView.findViewById(R.id.btn_bluetooth_scan);
        btn_bluetooth_scan.setOnClickListener(this);
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
            case R.id.btn_network:
                AnimUtils.doCustomAnim(mActivity,
                        NetworkActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_touch_event:
                AnimUtils.doCustomAnim(mActivity,
                        TouchEventActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_bluetooth_control:
                AnimUtils.doCustomAnim(mActivity,
                        BluetoothControlActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_bluetooth_scan:
                AnimUtils.doCustomAnim(mActivity,
                        BluetoothScanActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
    }
}
