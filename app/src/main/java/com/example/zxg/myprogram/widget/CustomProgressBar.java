package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class CustomProgressBar extends View{

    private int mProgress;
    private Bitmap maskImg;
    private String text;
    private int textSize = 20;
    private @ColorInt int textColor = Color.WHITE;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setDither(true); // 图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        paint.setStyle(Paint.Style.FILL);  // 画笔风格为空心
        paint.setTextAlign(Paint.Align.CENTER); // 文字居中

        // 绘制底色
        paint.setColor(Color.parseColor("#FFB5AE"));
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), getHeight()/2, getHeight()/2, paint);

        // 绘制进度条
        // 设置渐变
        if (mProgress > 0) {
            LinearGradient linearGradient = new LinearGradient(0, 0, mProgress / 100f * getWidth(), getHeight(), Color.parseColor("#FF5E5E"), Color.parseColor("#FF4545"), Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
            // 计算进度条宽度
            float width = mProgress / 100f * getWidth();
            // 进度小于getHeight()时（即京都小于一个圆的宽度），绘制椭圆矩形，否则绘制圆角矩形
            if (width < getHeight()) {
                float offset = width < getHeight() / 2 ? 0.8f : 1;
                float radiusY = (float) Math.sqrt(getHeight() * getHeight() / 4 - (getHeight() / 2 - width / 2) * (getHeight() / 2 - width / 2));
                canvas.drawRoundRect(new RectF(0, getHeight() / 2 - radiusY * offset, width, getHeight() / 2 + radiusY * offset), width / 2, radiusY, paint);
            } else {
                canvas.drawRoundRect(new RectF(0, 0, width, getHeight()), getHeight() / 2, getHeight() / 2, paint);
            }
            // 清除渐变
            paint.setShader(null);
        }

        //绘制条纹
        if (maskImg != null) {
            Rect src = new Rect(0, 0, (int) (mProgress / 100f * maskImg.getWidth()), getHeight());
            Rect dst = new Rect(0, 0, (int) (mProgress / 100f * getWidth()), getHeight());
            canvas.drawBitmap(maskImg, src, dst, paint);
        }

        // 绘制文字
        if (!TextUtils.isEmpty(text)) {
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            canvas.drawText(text, getWidth()/2, getHeight()/2f + (bottom - top)/2 - bottom, paint);
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = Math.min(Math.max(0, progress), 100);
    }

    public void setMaskImg(Bitmap maskImg) {
        this.maskImg = maskImg;
    }

    public void setMaskImg(int imgRes) {
        try {
            setMaskImg(BitmapFactory.decodeResource(getResources(), imgRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextSize(int textSize) {
        if (textSize <= 0) return;
        this.textSize = textSize;
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
    }
}
