package com.example.zxg.myprogram.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.method.Touch;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
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

import cn.sharesdk.OnekeyShareHelper;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    private DrawerLayout dl_drawer;
    private LinearLayout ll_share;
    private Button btn_net, btn_scale, btn_viewpager, btn_test, btn_custom_dialog, btn_popup_dialog,
            btn_moving_with_finger, btn_android_h5, btn_vertical_viewpager, btn_webview, btn_doubleSurfaceViewFlicker,
            btn_touch_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_main);

        mContext = this;

        btn_net = (Button) findViewById(R.id.btn_net);
        btn_net.setOnClickListener(this);

        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_scale.setOnClickListener(this);

        btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        btn_viewpager.setOnClickListener(this);

        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);

        btn_custom_dialog = (Button) findViewById(R.id.btn_custom_dialog);
        btn_custom_dialog.setOnClickListener(this);

        btn_popup_dialog = (Button) findViewById(R.id.btn_popup_dialog);
        btn_popup_dialog.setOnClickListener(this);

        btn_moving_with_finger = (Button) findViewById(R.id.btn_moving_with_finger);
        btn_moving_with_finger.setOnClickListener(this);

        btn_android_h5 = (Button)findViewById(R.id.btn_android_h5);
        btn_android_h5.setOnClickListener(this);

        dl_drawer = (DrawerLayout) findViewById(R.id.dl_drawer);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_share.setOnClickListener(this);

        btn_vertical_viewpager = (Button)findViewById(R.id.btn_vertical_viewpager);
        btn_vertical_viewpager.setOnClickListener(this);

        btn_webview = (Button)findViewById(R.id.btn_webview);
        btn_webview.setOnClickListener(this);

        btn_doubleSurfaceViewFlicker = (Button)findViewById(R.id.btn_doubleSurfaceViewFlicker);
        btn_doubleSurfaceViewFlicker.setOnClickListener(this);

        btn_touch_event = (Button) findViewById(R.id.btn_touch_event);
        btn_touch_event.setOnClickListener(this);
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setVisibility(View.VISIBLE);
        viewHolder.iv_left.setImageResource(R.drawable.head_right);
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_drawer.openDrawer(Gravity.LEFT);
            }
        });
        viewHolder.tv_title.setVisibility(View.VISIBLE);
        viewHolder.tv_title.setText("MainActivity");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_net:
                XUtilsTools.displayImage(mContext, new ImageView(mContext), "url",null,null);

                /*if (Tools.isFastClick()){
                    LogUtils.i(TAG, "---isFastClick");
                }

                String str = NumberUtils.formatDecimals(2016.100192, 4);
                if (Tools.isEmpty(str)){
                    LogUtils.i(TAG, "str is empty");
                }
                LogUtils.i(TAG, "upperCaseFirstOne:"+Tools.upperCaseFirstOne("my -- test -test"));
                LogUtils.i(TAG, "getCharCount:"+Tools.getCharCount("my -- -test"));
                LogUtils.i(TAG, "---my test");*/

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

                LogUtils.i(TAG,"mockserver isAppRunning: "+ SysUtils.isAppRunning(mContext,"com.youtitle.kuaidian"));

                LogUtils.i(TAG, "IP地址："+ NetUtils.getCurrentIpAddr());
                LogUtils.i(TAG, "WIFI IP地址："+ NetUtils.getCurrentWifiIPAddr(mContext));
                LogUtils.i(TAG, "网络类型："+ NetUtils.getNetConnectType(mContext));

                LogUtils.i(TAG, "欢迎语："+ TimeUtils.getWelcome());
                LoginApi loginApi= new LoginApi(mContext) {
                    @Override
                    public void onResult(String jsonStr) {
                        LogUtils.i(TAG, "====="+jsonStr);

                    }

                    @Override
                    public void onFail() {

                    }
                };
                loginApi.getTest();
                break;
            case R.id.btn_scale:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        ScaleActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_viewpager:
                AnimUtils.doScaleUpAnim((MainActivity)mContext, ViewpagerActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_test:
                AnimUtils.doScaleUpAnim((MainActivity)mContext, com.example.zxg.myprogram.test.MainActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.btn_custom_dialog:
                CustomDialog dialog = new CustomDialog(mContext, "test", "do test", "取消", "确定");
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
                final PopupFromBottomDialog popupFromBottomDialog = new PopupFromBottomDialog(mContext, list);
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
                AnimUtils.doScaleUpAnim((MainActivity)mContext, AndroidH5Activity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
                break;
            case R.id.ll_share: //一键分享
                dl_drawer.closeDrawers();
                ShareSDK.initSDK(mContext);
                OnekeyShareHelper.showShare(mContext);
                break;
            case R.id.btn_moving_with_finger:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        MovingWithFingerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_vertical_viewpager:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        VerticalViewpagerActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_webview:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        WebviewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_doubleSurfaceViewFlicker:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        SurfaceViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
            case R.id.btn_touch_event:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        TouchEventActivity.class, R.anim.in_from_left, R.anim.out_to_right);
                break;
        }
    }

    /**
     *
     * @param str
     * @param start
     */
    private void printStr (String str, int start) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (str.length() == start) {
            System.out.println(str);
        }
        for (int i = start-1; i < str.length(); i++) {
            String temp = str.substring(0, start-1) + str.charAt(i);
            if (start < str.length()) {
                temp = temp + str.replace(String.valueOf(str.charAt(i)), "").substring(start-1);
            }
            printStr(temp, start+1);
        }
    }

    private void printStr1 (String str, int start) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (str.length() == start) {
            System.out.println(str);
        }
        char[] temp = str.toCharArray();
        for (int i = start; i < temp.length; i++) {
            swipt(temp, start, i);
            printStr1(new String(temp), start + 1);
        }
    }

    private void swipt(char[] chars, int start, int index) {
        if (start >= index) {
            return;
        }
        char temp = chars[start];
        chars[start] = chars[index];
        chars[index] = temp;
    }

    /**
     * 找出数组中最大差值
     * @param list
     * @return
     */
    private int getMax (int[] list) {
        if (list.length < 2) {
            return 0;
        }
        int temp = list[0]; // 当前的最小值
        int max = 0; // 当前最大获利
        for (int i = 1; i < list.length; i++) {
            if (list[i] < temp) {
                temp = list[i];
            } else {
                if (list[i] - temp > max) {
                    max = list[i] - temp;
                }
            }
        }
        return max;
    }

    /**
     * 从有序数组中找出两个数，这两个数的和等于target
     * @param list
     * @param target
     * @return
     */
    private int[] getTarget (int[] list, int target) {
        if (list.length <= 1) {
            return new int[]{};
        }
        int start = 0;
        int end = list.length-1;
        while (start < end) {
            if (list[start] + list[end] > target) {
                end--;
            } else if (list[start] + list[end] < target) {
                start++;
            } else {
                return new int[]{start, end};
            }
        }
        return new int[]{};
    }
}
