package com.example.zxg.myprogram.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/** ViewPager适配器
 * Created by zxg on 2016/11/8.
 * QQ:1092885570
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> vp_views;

    public ViewPagerAdapter(List<View> views) {
        if (views == null){
            vp_views = new ArrayList<View>();
        }else {
            vp_views = views;
        }
    }

    @Override
    public int getCount() {
        return vp_views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(vp_views.get(position));
        return vp_views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(vp_views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
