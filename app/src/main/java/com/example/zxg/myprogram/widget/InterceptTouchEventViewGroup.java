package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.zxg.myprogram.utils.LogUtils;

/**
 * Created by ZHUXIAOGUANG613 on 2016-12-08.
 */
public class InterceptTouchEventViewGroup extends LinearLayout {

    private Context mContext;
    private int startX, startY;

    public int MIN_MOVE_DISTANCE = 10;

    public static final int TYPE_ACTION_MOVE = 1;
    public static final int TYPE_ACTION_UP = 2;

    public InterceptTouchEventViewGroup(Context context) {
        super(context);
        mContext = context;
    }

    public InterceptTouchEventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public InterceptTouchEventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("+++onInterceptTouchEvent1:", "ACTION_DOWN");
                startX = (int)event.getX();
                startY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("+++onInterceptTouchEvent2:", "ACTION_MOVE");
                if ((int) Math.abs(event.getX()-startX) > MIN_MOVE_DISTANCE
                        || (int) Math.abs(event.getY()-startY) > MIN_MOVE_DISTANCE)
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
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("+++onTouchEvent2:","ACTION_MOVE");
                if ((int) Math.abs(event.getX()-startX) > MIN_MOVE_DISTANCE
                        || (int) Math.abs(event.getY()-startY) > MIN_MOVE_DISTANCE){
                    LogUtils.i("+++createPopupWindow:","createPopupWindow");
                    mOnTouchEventListener.handleTouchEvent((int)(event.getX()-startX-MIN_MOVE_DISTANCE),
                            (int)(event.getY()-startY-MIN_MOVE_DISTANCE), TYPE_ACTION_MOVE);
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("+++onTouchEvent3:","ACTION_UP");
                if ((int) Math.abs(event.getX()-startX) > MIN_MOVE_DISTANCE
                        || (int) Math.abs(event.getY()-startY) > MIN_MOVE_DISTANCE) {
                    mOnTouchEventListener.handleTouchEvent((int) (event.getX() - startX - MIN_MOVE_DISTANCE),
                            (int) (event.getY() - startY - MIN_MOVE_DISTANCE), TYPE_ACTION_UP);
                }
                break;
        }
        return true;
    }

    public void setOnTouchEventListener(OnTouchEventListener onTouchEventListener) {
        this.mOnTouchEventListener = onTouchEventListener;
    }

    private OnTouchEventListener mOnTouchEventListener;
    public interface OnTouchEventListener{
        void handleTouchEvent(int x, int y, int type);
    }
}
