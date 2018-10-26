package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.utils.DPIUtil;

public class MovableView extends LinearLayout {

    public static boolean isTouchedMovableView = false; // MovableView是否被touch

    private Context mContext;
    // 活动区域
    private int startX, startY, offsetX, offsetY, mStatusBarHeight;
    public int MIN_MOVE_DISTANCE = 5; // 滑动的最小阈值，只有大于该值才会认为时滑动
    private int usableHeightPrevious;
    private Rect mParentRect, mActiveRegionRect, mMarginRect = new Rect();
    private ActiveRegionListener mActiveRegionListener;

    public MovableView(Context context) {
        this(context, null);
    }

    public MovableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        isTouchedMovableView = false;
        mStatusBarHeight = getStatusBarHeight();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActiveRegionRect = getActiveRegionRect();
                initRootViewLayoutListener();
            }
        }, 500);
    }


    public void initRootViewLayoutListener() {
        final View viewObserving = getRootView().findViewById(android.R.id.content);
        if (viewObserving != null) {
            //给View添加全局的布局监听器
            viewObserving.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int usableHeightNow = computeUsableHeight(viewObserving);
                    int offset = usableHeightPrevious - usableHeightNow;
                    if (usableHeightPrevious != 0 && offset != 0) {
                        mActiveRegionRect = getActiveRegionRect();

                        int maxTop = mActiveRegionRect.bottom - getHeight();
                        if (maxTop - getY() < Math.abs(offset)) {
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
                            if (offset > 0) {
                                marginLayoutParams.topMargin = maxTop - mParentRect.top - offset;
                            } else {
                                marginLayoutParams.topMargin = maxTop - mParentRect.top;
                            }
                            marginLayoutParams.bottomMargin = mMarginRect.bottom;
                            setLayoutParams(marginLayoutParams);
                        } else if (getY() - mActiveRegionRect.top < Math.abs(offset)) {
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
                            marginLayoutParams.topMargin = mMarginRect.top;
                            if (offset > 0) {
                                marginLayoutParams.bottomMargin = mParentRect.bottom - (mActiveRegionRect.top + getHeight()) - offset;
                            } else {
                                marginLayoutParams.bottomMargin = mParentRect.bottom - (mActiveRegionRect.top + getHeight());
                            }
                            setLayoutParams(marginLayoutParams);
                        }
                    }
                    usableHeightPrevious = usableHeightNow;
                }
            });
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i("++onInterceptTouchEvent", "ACTION = " + event.getAction() + ", event.getX() = " + event.getX() + ", event.getY() = " + event.getY() + ", startX = " + startX + ", startY = " + startY);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int)event.getX();
                startY = (int)event.getY();
                setClickable(true);
                break;
            case MotionEvent.ACTION_MOVE: // 当子view处理事件时，才会执行
                if (Math.abs(event.getX()-startX) > MIN_MOVE_DISTANCE || Math.abs(event.getY()-startY) > MIN_MOVE_DISTANCE)
                    return true;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("+++onTouchEvent", "ACTION = " + event.getAction() + ", event.getX() = " + event.getX() + ", event.getY() = " + event.getY() + ", startX = " + startX + ", startY = " + startY);

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                isTouchedMovableView = true;
                if (Math.abs(event.getX()-startX) > MIN_MOVE_DISTANCE || Math.abs(event.getY()-startY) > MIN_MOVE_DISTANCE){
                    offsetX = (int)(event.getX()-startX);
                    offsetY = (int)(event.getY()-startY);

                    int left = Math.min(Math.max(offsetX + getLeft(), mActiveRegionRect.left), mActiveRegionRect.right - getWidth());
                    int top = Math.min(Math.max(offsetY + getTop(), mActiveRegionRect.top), mActiveRegionRect.bottom - getHeight());
                    layout(left, top, left + getWidth(), top + getHeight());

                    // 滑动时屏蔽点击事件
                    setClickable(false);
                    Log.i("+++onTouchEvent move", "offsetX = " + offsetX + ", offsetY = " + offsetY + ", getLeft() = " + getLeft() + ", getTop() = " + getTop() + ", getRight() = " + getRight() + ", getBottom() = " + getBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                offsetY = (int)(event.getY()-startY);
            case MotionEvent.ACTION_CANCEL: // 有可能不执行ACTION_UP动作，而直接从ACTION_MOVE到ACTION_CANCEL
                isTouchedMovableView = false;
                int top = Math.min(Math.max(offsetY + getTop(), mActiveRegionRect.top), mActiveRegionRect.bottom - getHeight());
                layout(mActiveRegionRect.right - getWidth(), top, mActiveRegionRect.right, top + getHeight());

                // 设置LayoutParams，保证MovableView隐藏后在重新显示时在滑动的最后位置，不会回到初始位置
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
                marginLayoutParams.bottomMargin = mParentRect.bottom - getBottom();
                marginLayoutParams.topMargin = top - mParentRect.top;
                setLayoutParams(marginLayoutParams);

                Log.i("+++onTouchEvent up", "DPIUtil.getWidth() = " + DPIUtil.getWidth(mContext) + ", getWidth() = " + getWidth() + ", getLeft() = " + getLeft() + ", getTop() = " + getTop() + ", getRight() = " + getRight() + ", getBottom() = " + getBottom());
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取状态栏的高度
     */
    public int getStatusBarHeight() {
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private int computeUsableHeight(View viewObserved) {
        Rect r = new Rect();
        viewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    public Rect getActiveRegionRect() {
        if (mActiveRegionListener != null && mActiveRegionListener.getActiveRegion() != null) {
            mMarginRect = mActiveRegionListener.getActiveRegion();
        }
        Rect parentRect = getParentRect();
        int activeWidth = parentRect.right - parentRect.left - getWidth();
        int activeHight = parentRect.bottom - parentRect.top - getHeight();
        mMarginRect.left = mMarginRect.left > activeWidth ? activeWidth : mMarginRect.left;
        mMarginRect.top = mMarginRect.top > activeHight ? activeHight : mMarginRect.top;
        int l = parentRect.left + mMarginRect.left;
        int t = parentRect.top + mMarginRect.top;
        mMarginRect.right = parentRect.right - (l + getWidth()) > mMarginRect.right ? mMarginRect.right : parentRect.right - (l + getWidth());
        mMarginRect.bottom = parentRect.bottom - (t + getHeight()) > mMarginRect.bottom ? mMarginRect.bottom : parentRect.bottom - (t + getHeight());
        int r = parentRect.right - mMarginRect.right;
        int b = parentRect.bottom - mMarginRect.bottom;
        return new Rect(l, t, r, b);
    }

    private Rect getParentRect() {
        View parentView = (View) getParent();
        if (parentView == null) {
            mParentRect = new Rect(0, 0, DPIUtil.getWidth(mContext), DPIUtil.getHeight(mContext) - mStatusBarHeight);
        } else {
            mParentRect = new Rect(parentView.getPaddingLeft(), parentView.getPaddingTop(),
                    parentView.getWidth() - parentView.getPaddingRight(), parentView.getHeight() - parentView.getPaddingBottom());
        }

        return mParentRect;
    }

    public void setActiveRegionListener(ActiveRegionListener activeRegionListener) {
        this.mActiveRegionListener = activeRegionListener;
    }

    public interface ActiveRegionListener {
        Rect getActiveRegion();
    }
}
