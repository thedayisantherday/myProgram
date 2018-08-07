package com.example.zxg.myprogram.widget.scalescrollview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.example.zxg.myprogram.R;

/**
 * Created by zxg on 2016/10/19.
 * QQ:1092885570
 */
public abstract class BaseScaleView extends View {

    public static String TAG = BaseScaleView.class.getSimpleName();

    public boolean isEdit = false;

    public static int initValue = 1100;
    public int mMaxNum; //最大刻度数
    public int mMinNum; //最小刻度数
    protected int mDefaultNum; //初始刻度值

    public int mScaleValue; //一个刻度的代表值的大小
    protected int mSystemScale; //进制，隔几个刻度一个长线
    protected float mCountScale; //滑动的总刻度数

    protected int mDeviceWidth; //设备屏幕的宽度
    protected int mRectWidth; //刻度尺总宽度
    protected int mRectHeight; //刻度尺总高度
    protected int mScaleWidth; //两个刻度线之间的像素
    protected int mScaleHeight; //短刻度线的高度（像素）
    protected int mScaleMaxHeight; //最长刻度线的高度（像素）

    protected int mDeviation; //屏幕偏差，自适应各种屏幕
    protected float mInitDistance; //初始值与初始刻度的距离
//    protected float mTempScale; //临时刻度，用于判断滑动的方向
    protected int mMidScale; //中间刻度

    protected Scroller mScroller;
    protected int mScrollFinalX;
    protected ScrollerListener mScrollerListener;

    public interface ScrollerListener{
        void onScroll(float scale);
    }

    public void setScrollerListener(ScrollerListener scrollerListener) {
        this.mScrollerListener = scrollerListener;
    }


    public BaseScaleView(Context context) {
        super(context);
        initData(null);
    }

    public BaseScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(attrs);
    }

    public BaseScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseScaleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(attrs);
    }

    //数据初始化
    protected void initData(AttributeSet attrs){
        //获取自定义属性值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZXGStyleable);
        mMaxNum = typedArray.getInteger(R.styleable.ZXGStyleable_max_num, 50);
        mMinNum = typedArray.getInteger(R.styleable.ZXGStyleable_min_num, 0);
        mDefaultNum = typedArray.getInteger(R.styleable.ZXGStyleable_default_value, (mMaxNum-mMinNum)/2);

        mScaleValue = typedArray.getInteger(R.styleable.ZXGStyleable_scale_value, 1);
        mSystemScale = typedArray.getInteger(R.styleable.ZXGStyleable_system_scale, 10);
        mScaleWidth = (int)typedArray.getDimension(R.styleable.ZXGStyleable_scale_width, 30);
        mScaleHeight = (int)typedArray.getDimension(R.styleable.ZXGStyleable_scale_height, 20);

        typedArray.recycle();

        mScroller = new Scroller(getContext());
        initVar();
    }

    protected abstract void initVar();
    //画横线
    protected abstract void onDrawLine(Canvas canvas, Paint paint);
    //画刻度线
    protected abstract void onDrawScale(Canvas canvas, Paint paint);
    //画指针
    protected abstract void onDrawPointer(Canvas canvas, Paint paint);

    @Override
    protected void onDraw(Canvas canvas) {
        //TODO
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true); // 抗锯齿
        paint.setDither(true); // 图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setStyle(Paint.Style.FILL);  // 画笔风格为空心
        paint.setTextAlign(Paint.Align.CENTER); // 文字居中

        onDrawScale(canvas, paint);
        onDrawLine(canvas, paint);
        onDrawPointer(canvas, paint);
    }

    public void smoothScrollBy(int dx, int dy){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        Log.i(TAG, "-----fx"+mScroller.getFinalX()+"-----fy"+mScroller.getFinalY());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scorller是否执行完毕，true表示滑动未结束
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate(); // 通过重绘来不断调用computeScroll，从而实现滑动效果
        }
    }


}
