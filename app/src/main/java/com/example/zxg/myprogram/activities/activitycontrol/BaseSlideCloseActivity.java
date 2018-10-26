package com.example.zxg.myprogram.activities.activitycontrol;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by zxg on 2018/8/25.
 */

public class BaseSlideCloseActivity extends Activity implements SlidingPaneLayout.PanelSlideListener {
    private ViewPager viewPager;
    private boolean canSlide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSlideBackClose();
        super.onCreate(savedInstanceState);
    }

    private void initSlideBackClose() {
        SlidingPaneLayout slidingPaneLayout = new CustomSlidingPanelLayout(this);
        try {
            Field overhangSize = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            overhangSize.setAccessible(true);
            overhangSize.set(slidingPaneLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setSliderFadeColor(getResources()
                .getColor(android.R.color.transparent));
        View leftView = new View(this);
        leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slidingPaneLayout.addView(leftView, 0);
        ViewGroup decorView = (ViewGroup) ((ViewGroup)getWindow().getDecorView()).getChildAt(0);
        ViewGroup decorChild = (ViewGroup) decorView.getChildAt(1);
        decorChild.setBackgroundColor(getResources()
                .getColor(android.R.color.white));
        decorView.removeView(decorChild);
        decorView.addView(slidingPaneLayout, 1);
        slidingPaneLayout.addView(decorChild, 1);
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    private class CustomSlidingPanelLayout extends SlidingPaneLayout {
        private float mInitialMotionX;
        private float mInitialMotionY;

        public CustomSlidingPanelLayout(Context context) {
            this(context, null);
        }

        public CustomSlidingPanelLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public CustomSlidingPanelLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            if (!canSlide) {
                return false;
            }
            if (viewPager != null && viewPager.getCurrentItem() != 0) {
                return false;
            }
            switch (MotionEventCompat.getActionMasked(ev)) {
                case MotionEvent.ACTION_DOWN: {
                    mInitialMotionX = ev.getX();
                    mInitialMotionY = ev.getY();
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    final float x = ev.getX();
                    final float y = ev.getY();
                    if (x - mInitialMotionX < 0 || Math.abs(y - mInitialMotionY) > 20) {
                        return false;
                    }
                    if (!isOpen() && canScroll(this, false,
                            Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {
                        MotionEvent cancelEvent = MotionEvent.obtain(ev);
                        cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                        return super.onInterceptTouchEvent(cancelEvent);
                    }
                }
            }
            boolean onInterceptTouchEvent = false;
            try {
                onInterceptTouchEvent = super.onInterceptTouchEvent(ev);
            } catch (Exception e) {
            }
            return onInterceptTouchEvent;
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
    }

    @Override
    public void onPanelOpened(View panel) {
        finish();
    }

    @Override
    public void onPanelClosed(View panel) {
    }
}
