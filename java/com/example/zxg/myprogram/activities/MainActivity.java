package com.example.zxg.myprogram.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
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
import com.example.zxg.myprogram.utils.NumberUtils;
import com.example.zxg.myprogram.utils.SysUtils;
import com.example.zxg.myprogram.utils.TimeUtils;
import com.example.zxg.myprogram.utils.Tools;
import com.example.zxg.myprogram.utils.XUtilsTools;

import cn.sharesdk.OnekeyShareHelper;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends BaseActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    private DrawerLayout dl_drawer;
    private LinearLayout ll_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_main);

        mContext = this;

        final Button btn_net = (Button) findViewById(R.id.btn_net);
        btn_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                /*LogUtils.i(TAG,"mockserver isAppRunning: "+SysUtils.isAppRunning(mContext,"com.youtitle.kuaidian"));

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
                loginApi.getTest();*/
            }
        });

        Button btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.doCustomAnim((MainActivity)mContext,
                        ScaleActivity.class, R.anim.in_from_left, R.anim.out_to_right);
            }
        });

        Button btn_viewpager = (Button) findViewById(R.id.btn_viewpager);
        btn_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.doScaleUpAnim((MainActivity)mContext, ViewpagerActivity.class,
                        v, v.getWidth(), v.getHeight(), 0, 0);
            }
        });

        dl_drawer = (DrawerLayout) findViewById(R.id.dl_drawer);
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_drawer.closeDrawers();
                ShareSDK.initSDK(mContext);
                OnekeyShareHelper.showShare(mContext);
            }
        });
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setVisibility(View.VISIBLE);
        viewHolder.iv_left.setImageResource(R.drawable.head_right);
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_drawer.openDrawer(Gravity.LEFT);
                LogUtils.i(TAG,"===S");
            }
        });
        viewHolder.tv_title.setVisibility(View.VISIBLE);
        viewHolder.tv_title.setText("MainActivity");
    }
}
