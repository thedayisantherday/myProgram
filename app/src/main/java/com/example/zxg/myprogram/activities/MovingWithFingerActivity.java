package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.widget.InterceptTouchEventViewGroup;

import static android.content.ContentValues.TAG;

public class MovingWithFingerActivity extends Activity {

    private Context mContext;
    private LinearLayout ll_moving;
    private InterceptTouchEventViewGroup layout_viewgroup;
    private View mView;

    private TranslateAnimation mTranslateAnimation;

    private InterceptTouchEventViewGroup.OnTouchEventListener mOnTouchEventListener = new InterceptTouchEventViewGroup.OnTouchEventListener() {
        @Override
        public void handleTouchEvent(int x, int y, int type) {
            switch (type) {
                case InterceptTouchEventViewGroup.TYPE_ACTION_MOVE:
                    if (mView == null) {
                        mView = new View(mContext);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        ll_moving.addView(mView, params);
                        ll_moving.bringToFront();
                    }

                    LogUtils.i(TAG, "orginX:"+layout_viewgroup.getX()+"orginY"+layout_viewgroup.getY());
                    ll_moving.setX(x+layout_viewgroup.getX());
                    ll_moving.setY(y+layout_viewgroup.getY());
                    break;
                case InterceptTouchEventViewGroup.TYPE_ACTION_UP:
                    if (Math.abs(x) > layout_viewgroup.getWidth()/3 || Math.abs(y) > layout_viewgroup.getHeight()/3) {
                        startMovingAnim(2*x, 2*y);
                    } else {
                        startMovingAnim(-x, -y);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_with_finger);

        mContext = this;

        initView();
    }

    private void initView(){
        ll_moving = (LinearLayout) findViewById(R.id.ll_moving);
        layout_viewgroup = (InterceptTouchEventViewGroup) findViewById(R.id.layout_viewgroup);
        layout_viewgroup.setOnTouchEventListener(mOnTouchEventListener);
    }

    /**
     * 动画
     * @param endX
     * @param endY
     */
    private void startMovingAnim(float endX, float endY) {
        //位移动画
        mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);
        mTranslateAnimation.setDuration(200);
        mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_moving.setX(layout_viewgroup.getX());
                ll_moving.setY(layout_viewgroup.getY());
                ll_moving.clearAnimation();
                mTranslateAnimation.cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_moving.startAnimation(mTranslateAnimation);

        //属性动画
        /*PropertyValuesHolder x1 = PropertyValuesHolder.ofFloat("X", ll_moving.getX(), orginX);
        PropertyValuesHolder y1 = PropertyValuesHolder.ofFloat("Y", ll_moving.getY(), orginY);
        ObjectAnimator oani=ObjectAnimator.ofPropertyValuesHolder(ll_moving,x1,y1);
        oani.setDuration(2000);
        oani.setInterpolator(new AccelerateDecelerateInterpolator());
        oani.start();*/

    }
}
