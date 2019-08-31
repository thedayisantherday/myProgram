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
import com.example.zxg.myprogram.activities.LoopRecyclerviewActivity;
import com.example.zxg.myprogram.activities.MovableViewActivity;
import com.example.zxg.myprogram.activities.MovingWithFingerActivity;
import com.example.zxg.myprogram.activities.ScaleActivity;
import com.example.zxg.myprogram.activities.SurfaceViewActivity;
import com.example.zxg.myprogram.activities.TestActivity;
import com.example.zxg.myprogram.activities.TouchEventActivity;
import com.example.zxg.myprogram.activities.VerticalViewpagerActivity;
import com.example.zxg.myprogram.activities.ViewpagerActivity;
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
import com.example.zxg.myprogram.widget.dialogs.CustomDialog;
import com.example.zxg.myprogram.widget.dialogs.PopupFromBottomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * description ：
 * Created by zhuxiaoguang at 18:37 on 2019/8/30
 */
public class ToolUtilsFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private Button btn_net,
            btn_tuple;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_toolutils, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        btn_net = (Button) rootView.findViewById(R.id.btn_net);
        btn_net.setOnClickListener(this);

        btn_tuple = (Button) rootView.findViewById(R.id.btn_tuple);
        btn_tuple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_net:
                XUtilsTools.displayImage(mActivity, new ImageView(mActivity), "url",null,null);

                /*if (Tools.isFastClick()){
                    LogUtils.i(TAG, "---isFastClick");
                }

                String str = NumberUtils.formatDecimals(2016.100192, 4);
                if (Tools.isEmpty(str)){
                    LogUtils.i(TAG, "str is empty");
                }
                LogUtils.i(TAG, "upperCaseFirstOne:"+Tools.upperCaseFirstOne("my -- test1 -test1"));
                LogUtils.i(TAG, "getCharCount:"+Tools.getCharCount("my -- -test1"));
                LogUtils.i(TAG, "---my test1");*/

//                List list = null;
//                list.contains(2);
//                list.size();
//                list.add(1);
////                list.add(3);
//                if(list.contains(2))
//                    LogUtils.i(TAG, "list(0)的元素："+ list.get(0));
//                else
//                    LogUtils.i(TAG, "list(1)的元素："+ list.get(1));
//                LogUtils.i(TAG,"myprogram isAppRunning: "+SysUtils.isAppRunning(mContext,"com.example.zxg.myprogram"));

                LogUtils.i(TAG,"mockserver isAppRunning: "+ SysUtils.isAppRunning(mActivity,"com.youtitle.kuaidian"));

                LogUtils.i(TAG, "IP地址："+ NetUtils.getCurrentIpAddr());
                LogUtils.i(TAG, "WIFI IP地址："+ NetUtils.getCurrentWifiIPAddr(mActivity));
                LogUtils.i(TAG, "网络类型："+ NetUtils.getNetConnectType(mActivity));

                LogUtils.i(TAG, "欢迎语："+ TimeUtils.getWelcome());
                LoginApi loginApi= new LoginApi(mActivity) {
                    @Override
                    public void onResult(String jsonStr) {
                        LogUtils.i(TAG, "====="+jsonStr);

                    }

                    @Override
                    public void onFail() {

                    }
                };
                loginApi.getTest();
                AnimUtils.doCustomAnim(mActivity,
                        AutoClickActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_tuple:
                ThreeTuple<String, Integer, Boolean> threeTuple = TupleUtil.tuple("test1", 1, true);
                Toast.makeText(mActivity, "ThreeTuple.first = " + threeTuple.first + ", ThreeTuple.second = " + threeTuple.second + ", ThreeTuple.three = " + threeTuple.three, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
