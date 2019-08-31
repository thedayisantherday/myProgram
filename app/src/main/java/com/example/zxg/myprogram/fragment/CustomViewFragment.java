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
public class CustomViewFragment extends BaseFragment implements View.OnClickListener{

    private Button btn_net,
            btn_scale,
            btn_viewpager,
            btn_test,
            btn_custom_dialog,
            btn_popup_dialog,
            btn_moving_with_finger,
            btn_tuple,
            btn_android_h5,
            btn_vertical_viewpager,
            btn_webview,
            btn_doubleSurfaceViewFlicker,
            btn_touch_event,
            btn_movable_view,
            btn_loop_recyclerview;

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

    private void initView() {

        btn_net = (Button) mActivity.findViewById(R.id.btn_net);
        btn_net.setOnClickListener(this);

        btn_scale = (Button) mActivity.findViewById(R.id.btn_scale);
        btn_scale.setOnClickListener(this);

        btn_viewpager = (Button) mActivity.findViewById(R.id.btn_viewpager);
        btn_viewpager.setOnClickListener(this);

        btn_test = (Button) mActivity.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_custom_dialog = (Button) mActivity.findViewById(R.id.btn_custom_dialog);
        btn_custom_dialog.setOnClickListener(this);

        btn_popup_dialog = (Button) mActivity.findViewById(R.id.btn_popup_dialog);
        btn_popup_dialog.setOnClickListener(this);

        btn_moving_with_finger = (Button) mActivity.findViewById(R.id.btn_moving_with_finger);
        btn_moving_with_finger.setOnClickListener(this);

        btn_tuple = (Button) mActivity.findViewById(R.id.btn_tuple);
        btn_tuple.setOnClickListener(this);

        btn_android_h5 = (Button)mActivity.findViewById(R.id.btn_android_h5);
        btn_android_h5.setOnClickListener(this);

        btn_vertical_viewpager = (Button)mActivity.findViewById(R.id.btn_vertical_viewpager);
        btn_vertical_viewpager.setOnClickListener(this);

        btn_webview = (Button)mActivity.findViewById(R.id.btn_webview);
        btn_webview.setOnClickListener(this);

        btn_doubleSurfaceViewFlicker = (Button)mActivity.findViewById(R.id.btn_doubleSurfaceViewFlicker);
        btn_doubleSurfaceViewFlicker.setOnClickListener(this);

        btn_touch_event = (Button) mActivity.findViewById(R.id.btn_touch_event);
        btn_touch_event.setOnClickListener(this);

        btn_movable_view = (Button) mActivity.findViewById(R.id.btn_movable_view);
        btn_movable_view.setOnClickListener(this);

        btn_loop_recyclerview = (Button) mActivity.findViewById(R.id.btn_loop_recyclerview);
        btn_loop_recyclerview.setOnClickListener(this);
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
            case R.id.btn_scale:
                AnimUtils.doCustomAnim(mActivity,
                        ScaleActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_viewpager:
                AnimUtils.doScaleUpAnim(mActivity, ViewpagerActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_test:
                /*AnimUtils.doScaleUpAnim((MainActivity)mContext, com.example.zxg.myprogram.test1.MainActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);*/
                AnimUtils.doCustomAnim(mActivity,
                        TestActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                /*AnimUtils.doScaleUpAnim((MainActivity)mContext, TestActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);*/
                break;
            case R.id.btn_custom_dialog:
                CustomDialog dialog = new CustomDialog(mActivity, "test1", "do test1", "取消", "确定");
                dialog.showDialog();
                dialog.setClickCallBack(new CustomDialog.ClickCallBack() {
                    @Override
                    public void onNegative() {
                        LogUtils.i(TAG, "Negative button is clicked!");
                    }

                    @Override
                    public void onPositive() {
                        LogUtils.i(TAG, "Positive button is clicked!");
                    }
                });
                break;
            case R.id.btn_popup_dialog:
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 2; i++) {

                    list.add("sure");
                    list.add("cancel");
                }
                final PopupFromBottomDialog popupFromBottomDialog = new PopupFromBottomDialog(mActivity, list);
                popupFromBottomDialog.showDialog();
                popupFromBottomDialog.setmOnItemListener(new PopupFromBottomDialog.OnItemListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case 0:
                            case 1:
                                popupFromBottomDialog.dismiss();
                                break;

                        }
                    }
                });
                break;
            case R.id.btn_android_h5:
                AnimUtils.doScaleUpAnim(mActivity, AndroidH5Activity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_moving_with_finger:
                AnimUtils.doCustomAnim(mActivity,
                        MovingWithFingerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_tuple:
                ThreeTuple<String, Integer, Boolean> threeTuple = TupleUtil.tuple("test1", 1, true);
                Toast.makeText(mActivity, "ThreeTuple.first = " + threeTuple.first + ", ThreeTuple.second = " + threeTuple.second + ", ThreeTuple.three = " + threeTuple.three, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_vertical_viewpager:
                AnimUtils.doCustomAnim(mActivity,
                        VerticalViewpagerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_webview:
                AnimUtils.doCustomAnim(mActivity,
                        WebviewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_doubleSurfaceViewFlicker:
                AnimUtils.doCustomAnim(mActivity,
                        SurfaceViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_touch_event:
                AnimUtils.doCustomAnim(mActivity,
                        TouchEventActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_movable_view:
                AnimUtils.doCustomAnim(mActivity,
                        MovableViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_loop_recyclerview:
                AnimUtils.doCustomAnim(mActivity,
                        LoopRecyclerviewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
    }
}
