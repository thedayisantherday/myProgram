package com.example.zxg.myprogram.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.utils.Tools;
import com.example.zxg.myprogram.widget.bannerview.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardViewBannerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_banner);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        int[] img_src = {R.drawable.test1, R.drawable.test2, R.drawable.test3};
        List<View> views = new ArrayList<>();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < img_src.length; i++) {
            FrameLayout cardView = new FrameLayout(this);
            cardView.setLayoutParams(lp);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(Tools.readBitmap(this, img_src[i]));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("TAG", "bannerview onclick listener");
                }
            });
            cardView.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            views.add(cardView);
        }
        mAdapter = new ViewPagerAdapter(views);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    mViewPager.setPadding(200, 0, 20, 0);
                } else {
                    mViewPager.setPadding(20, 0, 200, 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}