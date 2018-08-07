package com.example.zxg.myprogram.widget.scalescrollview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zxg on 2016/10/19.
 * QQ:1092885570
 */
public class HorizontalScaleView extends BaseScaleView {
    //代码中直接new的时候调用
    public HorizontalScaleView(Context context) {
        super(context);
    }

    //在xml布局文件引用的时候调用
    public HorizontalScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //在xml布局文件中引用，且自定义view中是哟个了自定义属性时调用
    public HorizontalScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HorizontalScaleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initVar() {
        mRectHeight = mScaleHeight * 9;
        mScaleMaxHeight = mScaleHeight * 6;
//        // 设置layoutParams，MarginLayoutParams是viewgrop支持margin，即子布局能使用margin属性
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(mRectWidth, mRectHeight);
//        //这里无效，需要在自定义view初始化完成之后调用setLayoutParams()方法才有效
//        this.setLayoutParams(lp);

        mMinNum = mMinNum>0 ? mMinNum : 0;
        /**
         * 当前额度大于一百万时， mMax = mMin;
         * 当前额度大于九十万小于一百万时， 刻度尺显示的最大值为一百万
         */
        if(initValue+mMinNum*mScaleValue >= 1000000){
            mMaxNum = mMinNum;
        }else if(initValue+mMinNum*mScaleValue > 900000
                && initValue+mMinNum*mScaleValue < 1000000){
            mMaxNum = (1000000 - initValue/mScaleValue*mScaleValue - mMinNum*mScaleValue)/mScaleValue + mMinNum;
        }
        mRectWidth = (mMaxNum - mMinNum) * mScaleWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHight = View.MeasureSpec.makeMeasureSpec(mRectHeight, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mHight);

        //获取组件宽度
        mDeviceWidth = getMeasuredWidth();
        System.out.println("空间宽度=====>" + mDeviceWidth);
        //计算屏幕偏差，调整刻度尺，使滑动时刻度尺始终与中间指针重合
        mDeviation = mDeviceWidth % (2 * mScaleWidth);

//        mTempScale = mDeviceWidth / mScaleWidth / 2 + mMinNum;
        mMidScale = mDeviceWidth / mScaleWidth / 2 + mMinNum;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mInitDistance = (initValue % mScaleValue)/(float)mScaleValue;
        int halfRectWidth = mDeviceWidth / mScaleWidth / 2;
        int x = (int)((mInitDistance-halfRectWidth) * mScaleWidth);
        System.out.println("初始值====>"+x+":::::"+mInitDistance);
        smoothScrollBy(x, 0);
    }

    public void doScrollTo(float scale){
        int x = (int)((scale-mCountScale)*mScaleWidth);
        System.out.println("滑动的距离" + x);
        smoothScrollBy(x, 0);
        postInvalidate();
    }

    @Override
    protected void onDrawLine(Canvas canvas, Paint paint) {
        paint.setColor(Color.rgb(221, 221, 221));
        canvas.drawRoundRect(new RectF(-mScaleHeight/2, mRectHeight-mScaleHeight*21/4, mRectWidth+ mScaleWidth/2,  mRectHeight-mScaleHeight*19/4),
                mScaleHeight*1/4, mScaleHeight*1/4, paint);
    }

    @Override
    protected void onDrawScale(Canvas canvas, Paint paint) {
        paint.setTextSize(18);

        for (int i = mMinNum; i <= mMaxNum; i++) {
            if((i+initValue/mScaleValue) % mSystemScale ==0){
                if ((i+initValue/mScaleValue) % (2*mSystemScale) ==0) {
                    canvas.drawLine(i*mScaleWidth + mDeviation/2, mRectHeight - 8*mScaleHeight,
                            i*mScaleWidth + mDeviation/2, mRectHeight - 2*mScaleHeight, paint);
                    //整数数值
                    canvas.drawText(String.valueOf((i+initValue/mScaleValue) * mScaleValue),
                            i*mScaleWidth + mDeviation/2, mRectHeight - mScaleHeight/2, paint);
                }else {
                    canvas.drawLine(i*mScaleWidth + mDeviation/2, mRectHeight - 7*mScaleHeight,
                            i*mScaleWidth + mDeviation/2, mRectHeight - 3*mScaleHeight, paint);
                    //整数数值
                    canvas.drawText(String.valueOf((i+initValue/mScaleValue) * mScaleValue),
                            i*mScaleWidth + mDeviation/2, mRectHeight - mScaleHeight*3/2, paint);
                }
            }else {
                canvas.drawLine(i*mScaleWidth + mDeviation/2, mRectHeight - 6*mScaleHeight,
                        i*mScaleWidth + mDeviation/2, mRectHeight - 4*mScaleHeight, paint);
            }
        }
    }

    @Override
    protected void onDrawPointer(Canvas canvas, Paint paint) {
        //半屏的刻度线数
        int scaleNum = mDeviceWidth/mScaleWidth/2;
        int finalX = mScroller.getFinalX();

        if (mScrollerListener!=null && !isEdit){
            int tempCountScale = (int)Math.rint((double)finalX/mScaleWidth);
            mCountScale = tempCountScale + scaleNum + mMinNum;
            mScrollerListener.onScroll(mCountScale);
        }else if(isEdit) {
            float tempCountScale = (float) (finalX/mScaleWidth);
            mCountScale = tempCountScale + scaleNum + mMinNum;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mScroller!=null && !mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mScrollFinalX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                isEdit = false;

                int scrollX = mScrollFinalX - x;
                if (scrollX<0 && mCountScale<mMinNum+mInitDistance) { //若滑动到最小刻度，则禁止右滑
                    return super.onTouchEvent(event);
                }else if (scrollX>0 && mCountScale>mMaxNum) { //若滑动到最大刻度，则禁止左滑
                    return super.onTouchEvent(event);
                }
                mScrollFinalX = x;
                smoothScrollBy(scrollX, 0);
                postInvalidate();
//                mTempScale = mCountScale;
                return true;
            case MotionEvent.ACTION_UP:
                if (mCountScale < mMinNum+mInitDistance){
                    mCountScale = mMinNum + mInitDistance;
                }else if(mCountScale > mMaxNum){
                    mCountScale = mMaxNum;
                }

                if (0!= initValue%mScaleValue && mCountScale<1+mInitDistance){
                    mCountScale = 2; //要求至少滑动一个刻度
                }
                int finalX = (int)(mCountScale - mMidScale) * mScaleWidth;
                mScroller.setFinalX(finalX);
                postInvalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }
}
