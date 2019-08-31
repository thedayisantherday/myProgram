package com.example.zxg.myprogram.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.utils.DPIUtil;

/**
 * Created by zhuxiaoguang on 2017/11/6.
 */

public class MovableLinearLayout extends LinearLayout {

    /**
     * moveListener为null时，不会拦截事件，和普通LinearLayout一样
     */
    private MoveListener moveListener;
    private float startEventX, startEventY;
    /**
     * 最大移动距离
     */
    private int maxMoveLength;
    /**d
     * 获取子view移动方向
     * @return 0:+X方向，1:-X方向（默认），2:+Y方向, 3:-Y方向
     */
    private int moveOrientation = 1;


    public MovableLinearLayout(Context context) {
        super(context);
    }

    public MovableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startEventX = event.getX();
                startEventY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveListener !=null && moveListener.getInterceptCondition(event)) { // 满足自定义拦截条件
                    // 子view移动方向和手指移动方向相同时，拦截事件
                    if ((event.getX()> startEventX && moveOrientation==0 || event.getX()< startEventX && moveOrientation==1)
                            && Math.abs(startEventX -event.getX()) > Math.abs(startEventY -event.getY()) // X轴方向移动的距离大于Y轴方向移动的距离
                            || (event.getY()> startEventY && moveOrientation==2 || event.getY()< startEventY && moveOrientation==3)
                            && Math.abs(startEventX -event.getX()) < Math.abs(startEventY -event.getY())) {
                        // 防止跟父view事件冲突
                        getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if ((event.getX()> startEventX && moveOrientation==0 || event.getX()< startEventX && moveOrientation==1)
                        && Math.abs(startEventX -event.getX()) > Math.abs(startEventY -event.getY())
                        || (event.getY()> startEventY && moveOrientation==2 || event.getY()< startEventY && moveOrientation==3)
                        && Math.abs(startEventX -event.getX()) < Math.abs(startEventY -event.getY())) { // &&的优先级高于||
                    transitionViews(event);
                } else {
                    resetView(0);
                }
                if (moveListener != null) {
                    moveListener.onMoving(event);
                }
                return true;
            case MotionEvent.ACTION_UP:
                if ((event.getX()> startEventX && moveOrientation==0 || event.getX()< startEventX && moveOrientation==1)
                        && Math.abs(startEventX -event.getX()) > Math.abs(startEventY -event.getY())
                        || (event.getY()> startEventY && moveOrientation==2 || event.getY()< startEventY && moveOrientation==3)
                        && Math.abs(startEventX -event.getX()) < Math.abs(startEventY -event.getY())) { // &&的优先级高于||
                    if (moveListener != null) {
                        moveListener.onMoved(event);
                    }
                }
                return true;
            default:
                resetView(200);
        }
        return super.onTouchEvent(event);
    }

    private void transitionViews (MotionEvent event) {

        int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }

        int factor = moveOrientation%2==0 ? 1 : -1; // 子view移动系数，-1表示沿负方向移动，1表示沿正方向移动
        float offset = 0; // 子view移动的距离
        // 如果该ViewGroup为水平布局，则在水平方向移动；否则高度为各个子view的累加
        if (moveOrientation < 2) {
            offset = Math.min(Math.abs(startEventX -event.getX()), maxMoveLength);
            moveHorizontal(childCount, factor * offset, event);
        } else {
            offset = Math.min(Math.abs(startEventY -event.getY()), maxMoveLength);
            moveVertical(childCount, factor * offset, event);
        }
    }

    // 水平方向移动
    private void moveHorizontal (int childCount, float offset, MotionEvent event) {
        LayoutParams layoutParams = null;
        // 逐一移动各个子View
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (MotionEvent.ACTION_MOVE == event.getAction()) {
                if (getOrientation() == LinearLayout.HORIZONTAL && i > 0) { // 相邻子view移动前后的相对位置不变
                    View preView = getChildAt(i-1);
                    int rightMargin = layoutParams == null ? 0 : layoutParams.rightMargin;
                    offset = preView.getX() + preView.getMeasuredWidth() + rightMargin;
                }
                layoutParams = (LayoutParams) childView.getLayoutParams();
                int leftMargin = layoutParams == null ? 0 : layoutParams.leftMargin;
                childView.setX(offset + leftMargin);
            }
        }
    }
    // 垂直方向移动
    private void moveVertical (int childCount, float offset, MotionEvent event) {
        LayoutParams layoutParams = null;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (MotionEvent.ACTION_MOVE == event.getAction()) {
                if (getOrientation() == LinearLayout.VERTICAL && i > 0) {
                    View preView = getChildAt(i - 1);
                    int bottomMargin = layoutParams == null ? 0 : layoutParams.bottomMargin;
                    offset = preView.getY() + preView.getMeasuredHeight() + bottomMargin;
                }
                layoutParams = (LayoutParams) childView.getLayoutParams();
                int topMargin = layoutParams == null ? 0 : layoutParams.topMargin;
                childView.setY(offset + topMargin);
            }
        }
    }

    public void resetView (long duration) {
        if (duration > 0) {
            resetWithAnim(duration);
        } else {
            resetNoAnim();
        }
    }

    /**
     * MotionEvent.ACTION_UP时，如果滑动距离没有超过maxMoveLength，执行复位动画
     * @param duration
     */
    private void resetWithAnim(long duration) {
        int childCount = getChildCount();
        if (childCount <= 0 || duration <= 0) return;

        String orientation = moveOrientation < 2 ? "translationX" : "translationY";
        for (int i = 0; i < childCount; i++) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(getChildAt(i), orientation, 0);
            anim.setDuration(duration);
            anim.start();
        }
    }

    private void resetNoAnim() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            float offset = 0;
            if (moveOrientation < 2) {
                if (getOrientation() == LinearLayout.HORIZONTAL && i > 0) { // 相邻子view移动前后的相对位置不变
                    View preView = getChildAt(i-1);
                    int rightMargin = layoutParams == null ? 0 : layoutParams.rightMargin;
                    offset = preView.getX() + preView.getMeasuredWidth() + rightMargin;
                }
                int leftMargin = layoutParams == null ? 0 : layoutParams.leftMargin;
                childView.setX(offset + leftMargin);
            } else {
                if (getOrientation() == LinearLayout.VERTICAL && i > 0) {
                    View preView = getChildAt(i - 1);
                    int bottomMargin = layoutParams == null ? 0 : layoutParams.bottomMargin;
                    offset = preView.getY() + preView.getMeasuredHeight() + bottomMargin;
                }
                int topMargin = layoutParams == null ? 0 : layoutParams.topMargin;
                childView.setY(offset + topMargin);
            }
        }
    }

    public float getStartEventX() {
        return startEventX;
    }

    public float getStartEventY() {
        return startEventY;
    }

    public void addView(final View view) {
        super.addView(view);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                setMaxMoveLength(view.getWidth());
            }
        });
    }

    public void setMaxMoveLength(int maxMoveLength) {
        this.maxMoveLength = Math.abs(maxMoveLength);
    }

    public int getMaxMoveLength() {
        return maxMoveLength;
    }

    public void setMoveOrientation(int moveOrientation) {
        if (moveOrientation >= 0 && moveOrientation <=3) {
            this.moveOrientation = moveOrientation;
        }
    }

    public void setMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    public static abstract class MoveListener {
        /**
         * 自定义事件拦截条件
         * @param event
         * @return 默认为true
         */
        public boolean getInterceptCondition(MotionEvent event){
            return false;
        }

        public void onMoving(MotionEvent event){}

        public void onMoved(MotionEvent event){}
    }
}
