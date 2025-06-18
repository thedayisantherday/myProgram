package com.example.zxg.myprogram.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.adapter.BaseFragmentAdapter;
import com.example.zxg.myprogram.fragment.CustomViewFragment;
import com.example.zxg.myprogram.fragment.FunctionsFragment;
import com.example.zxg.myprogram.fragment.ToolUtilsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.OnekeyShareHelper;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.dl_drawer)
    DrawerLayout dl_drawer;
    private LinearLayout ll_share;
    private TabLayout tl_tabs;
    private ViewPager vp_fragment;

    private List<Fragment> mFragments;
    String[] mTabTitles = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = this;

//        dl_drawer = findViewById(R.id.dl_drawer);
        ll_share = findViewById(R.id.ll_share);
        ll_share.setOnClickListener(this);

        tl_tabs = (TabLayout) findViewById(R.id.tl_tabs);
        tl_tabs.setBackgroundColor(Color.rgb(255, 90, 0));
        vp_fragment = (ViewPager) findViewById(R.id.vp_fragment);

        initFragments();
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
            case R.id.ll_share: //一键分享
                dl_drawer.closeDrawers();
                ShareSDK.initSDK(mContext);
                OnekeyShareHelper.showShare(mContext);
                break;
        }
    }

    private void initFragments(){
        // 第一步：初始化fragments和tabTitles
        mTabTitles = new String[]{ "自定义view", "tools", "功能"};
        mFragments = new ArrayList<Fragment>();
        CustomViewFragment customViewFragment = new CustomViewFragment();
        mFragments.add(customViewFragment);
        ToolUtilsFragment toolUtilsFragment = new ToolUtilsFragment();
        mFragments.add(toolUtilsFragment);
        FunctionsFragment functionsFragment = new FunctionsFragment();
        mFragments.add(functionsFragment);

        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(
                getSupportFragmentManager(), mFragments, mTabTitles);
        vp_fragment.setAdapter(adapter);
        vp_fragment.setOffscreenPageLimit(1);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tl_tabs.setupWithViewPager(vp_fragment);
    }
}
