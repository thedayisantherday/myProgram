package com.example.zxg.myprogram.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.adapter.BaseFragmentAdapter;
import com.example.zxg.myprogram.fragment.RecyclerviewFragment;
import com.example.zxg.myprogram.widget.bannerview.BannerView;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerActivity extends BaseActivity {

    private TabLayout tl_tabs;
    private ViewPager vp_fragment;
    private List<Fragment> mFragments;
    String[] mTabTitles = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_viewpager);
        initView();
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewpagerActivity.this.finish();
            }
        });
        viewHolder.tv_title.setVisibility(View.VISIBLE);
        viewHolder.tv_title.setText("ViewpagerActivity");
    }

    private void initView(){

        tl_tabs = (TabLayout) findViewById(R.id.tl_tabs);
        tl_tabs.setBackgroundColor(Color.rgb(255, 90, 0));
        vp_fragment = (ViewPager) findViewById(R.id.vp_fragment);

        BannerView banner_view = (BannerView) findViewById(R.id.banner_view);
        int[] img_src = {R.drawable.test1, R.drawable.test2, R.drawable.test3};

        banner_view.setView(img_src/*,new int[]{R.drawable.ic_indicator_off, R.drawable.ic_indicator_on}*/);
//        banner_view.startAutoPlay(1000);

        initFragments();
    }

    private void initFragments(){
        // 第一步：初始化fragments和tabTitles
        mTabTitles = new String[]{ "Recycler View", "搞笑", "逗比", "明星名人", "男神", "女神", "音乐",
                "舞蹈", "旅行", "美食", "美妆时尚", "涨姿势", "宝宝", "萌宠乐园", "二次元"};
        mFragments = new ArrayList<Fragment>();
        RecyclerviewFragment fragment = new RecyclerviewFragment();
        mFragments.add(fragment);
        for (int i = 1; i < mTabTitles.length; i++) {
            Fragment fm = new Fragment();
            mFragments.add(fm);

        }

        // 第二步：为ViewPager设置适配器
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(
                getSupportFragmentManager(), mFragments, mTabTitles);
        vp_fragment.setAdapter(adapter);
        vp_fragment.setOffscreenPageLimit(1);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tl_tabs.setupWithViewPager(vp_fragment);
    }
}


