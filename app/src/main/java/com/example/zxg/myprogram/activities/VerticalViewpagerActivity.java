package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.widget.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class VerticalViewpagerActivity extends Activity {
    private VerticalViewPager verticalViewPager;
    private final int[] imgSource = { R.drawable.bg_android_v7_1,
            R.drawable.bg_android_v7_2, R.drawable.bg_android_v7_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_viewpager);

        verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);

        List<View> viewList = new ArrayList<View>();
        for (int i = 0; i < imgSource.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.vertical_viewpager, null);
            ImageView img = (ImageView) view.findViewById(R.id.pager);
            img.setImageResource(imgSource[i]);
            viewList.add(view);
        }
        verticalViewPager.setViewList(viewList);
    }
}

