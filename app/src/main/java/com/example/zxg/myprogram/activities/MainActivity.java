package com.example.zxg.myprogram.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import cn.sharesdk.OnekeyShareHelper;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    private DrawerLayout dl_drawer;
    private LinearLayout ll_share;
    private Button btn_net, btn_scale, btn_viewpager, btn_test, btn_custom_dialog, btn_popup_dialog,
            btn_moving_with_finger, btn_tuple, btn_android_h5, btn_vertical_viewpager, btn_webview, btn_doubleSurfaceViewFlicker,
            btn_touch_event, btn_movable_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_main);

        mContext = this;
        String str;

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

        btn_tuple = (Button) findViewById(R.id.btn_tuple);
        btn_tuple.setOnClickListener(this);

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

        btn_movable_view = (Button) findViewById(R.id.btn_movable_view);
        btn_movable_view.setOnClickListener(this);
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
                /*AnimUtils.doScaleUpAnim((MainActivity)mContext, com.example.zxg.myprogram.test1.MainActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);*/
                test1();
                test2();
                test3();
                break;
            case R.id.btn_custom_dialog:
                CustomDialog dialog = new CustomDialog(mContext, "test1", "do test1", "取消", "确定");
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
            case R.id.btn_tuple:
                ThreeTuple<String, Integer, Boolean> threeTuple = TupleUtil.tuple("test1", 1, true);
                Toast.makeText(mContext, "ThreeTuple.first = " + threeTuple.first + ", ThreeTuple.second = " + threeTuple.second + ", ThreeTuple.three = " + threeTuple.three, Toast.LENGTH_LONG).show();
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
            case R.id.btn_movable_view:
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        MovableViewActivity.class, R.anim.in_from_left, R.anim.out_to_right);
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

    public static void test1() {
        Map<String, Object> params = new HashMap<>();
        params.put("from", 1);
        params.put("tagId", "tagId");
        params.put("isPost", true);

        for(String key : params.keySet()) {
            LogUtils.i("test1", key + ": " + params.get(key));
        }



        String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        LogUtils.i("String test1", "" + (a == c));
        LogUtils.i("String test2", "" + (a == e));
        LogUtils.i("String test3", "" + (("String" + 2) == a));
        LogUtils.i("String test4", "" + ("hello2" == a));
        LogUtils.i("String test5", "" + ((1+2) == 3));
    }

    public void test2() {
        //临界资源
        Service service = new Service();

        //创建并启动线程A
        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();

        //创建并启动线程B
        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();
    }

    //资源类
    class Service {
        public void print(String stringParam) {
            try {
                synchronized (stringParam) {
                    System.out.println(stringParam.hashCode());
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //线程A
    class ThreadA extends Thread {
        private Service service;

        public ThreadA(Service service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.print("AA");
        }
    }

    //线程B
    class ThreadB extends Thread {
        private Service service;

        public ThreadB(Service service) {
            super();
            this.service = service;
        }

        @Override
        public void run() {
            service.print("AA");
        }
    }

    private void test3() {
        TodoList todoList = new TodoList();
        todoList.remove();
    }

    class TodoList extends PriorityQueue<TodoItem> {

    }

    class TodoItem implements Comparable<TodoItem> {
        @Override
        public int compareTo(@NonNull TodoItem another) {
            return 0;
        }
    }
}
