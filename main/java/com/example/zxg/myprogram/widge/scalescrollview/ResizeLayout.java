package com.example.zxg.myprogram.widget.scalescrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zxg on 2016/4/25.
 *
 * 定义布局类，继承RelativeLayout类
 * 用于监听布局大小改变事件
 *
 */
public class ResizeLayout extends RelativeLayout {
    private OnResizeListener mListener;

    public interface OnResizeListener {
        void OnResize(int w, int h, int oldw, int oldh);
    }

    /**
     * 定义一个监听器，用于监听布局大小的改变
     */
    public void setOnResizeListener(OnResizeListener listener) {
        mListener = listener;
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param width 当前宽度
     * @param height 当前高度
     * @param oldWidth 原始宽度
     * @param oldHeight 原始高度
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (mListener != null) {
            mListener.OnResize(width, height, oldWidth, oldHeight);
        }
    }
}
