package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.utils.DPIUtil;

/**
 * Created by zxg on 2019/07/15.
 *
 * 图文混排组件
 * 1、暂不支持限制行数；
 * 2、行末为英文单词时，会被隔断，可以考虑在隔断出加"-"连接符
 */
public class ImageTextView extends View {

    private Context mContext;
    private Bitmap mImage;
    private float mImgX, mImgY;
    private int mImgMarginTop, mImgMarginLeft, mImgMarginBottom, mImgMarginRight;
    private String mText;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 24, mTextSpace = 6;
    private Typeface mTextTypeface;
    private float mTempTextX, mTempTextY;
    private int mBackgroundColor = Color.WHITE;

    private int mDrawStart;
    private boolean isDrawn = false;

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData(attrs);
    }

    private void initData(@Nullable AttributeSet attrs) {
        setWillNotDraw(false);
        // TODO 关注Bitmap内存泄漏问题

        try {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ImageTextViewAttr);
            mText = typedArray.getString(R.styleable.ImageTextViewAttr_text);
            mTextColor = typedArray.getColor(R.styleable.ImageTextViewAttr_textColor, Color.BLACK);
            mTextSize = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_textSizeDP, 12));
            mTextSpace = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_textSpaceDP, 6));
            BitmapDrawable imgSrc = (BitmapDrawable)typedArray.getDrawable(R.styleable.ImageTextViewAttr_imgSrc);
            mImage = imgSrc.getBitmap();
            mImgMarginTop = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_imgMarginTopDp, 0));
            mImgMarginLeft = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_imgMarginLeftDp, 0));
            mImgMarginBottom = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_imgMarginBottomDp, 0));
            mImgMarginRight = DPIUtil.dip2px(mContext, typedArray.getInt(R.styleable.ImageTextViewAttr_imgMarginRightDp, 0));
            mBackgroundColor = typedArray.getColor(R.styleable.ImageTextViewAttr_backgroundColor, Color.WHITE);
            setBackgroundColor(mBackgroundColor);

            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        mText = text;
        if (!TextUtils.isEmpty(mText)) {
            requestLayout();
        }
        isDrawn = false;
    }

    public void setTextSize(float textSize) {
        if (textSize > 0) {
            mTextSize = textSize;
        }
    }

    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
    }

    public void setTextSpace(float textSpace) {
        if (textSpace >= 0) {
            mTextSpace = textSpace;
        }
    }

    public void setTextTypeface(Typeface textTypeface) {
        mTextTypeface = textTypeface;
    }

    public void setImage(int imgRes) {
        try {
            setImage(BitmapFactory.decodeResource(getResources(), imgRes), 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(Bitmap image) {
        setImage(image, 0, 0);
    }

    public void setImage(Bitmap image, int imgX, int imgY) {
        mImage = image;
        mImgX = imgX;
        mImgY = imgY;
        if (mImage != null) {
            requestLayout();
        }
        isDrawn = false;
    }

    public void setImageMargin(int marginTop, int marginLeft, int marginBottom, int marginRight) {
        mImgMarginTop = marginTop;
        mImgMarginLeft = marginLeft;
        mImgMarginBottom = marginBottom;
        mImgMarginRight = marginRight;
    }

    public int getImageWidth() {
        if (mImage == null) {
            return 0;
        } else {
            return mImage.getWidth() + mImgMarginLeft + mImgMarginRight;
        }
    }

    public int getImageHeight() {
        if (mImage == null) {
            return 0;
        } else {
            return mImage.getHeight() + mImgMarginTop + mImgMarginBottom;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            drawImage(canvas);
            drawText(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawImage(Canvas canvas) {
        if (mImage != null) {
            Paint paint = new Paint();
            canvas.drawBitmap(mImage, mImgX + mImgMarginLeft, mImgY + mImgMarginTop, paint);
        }
    }

    private void drawText(Canvas canvas) {
        if (TextUtils.isEmpty(mText)) return;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mTextColor);
//        paint.setTextAlign(Paint.Align.CENTER);
        if (mTextTypeface != null) {
            paint.setTypeface(mTextTypeface);
        }
        paint.setTextSize(mTextSize);

        mDrawStart = 0;
        mTempTextX = 0;
        mTempTextY = paint.getTextSize();

        // 绘制图片上面的文字
        int topLines = (int) Math.floor(mImgY/(mTextSize + mTextSpace));
        for (int i = 0; i < topLines; i++) {
            int numAll = paint.breakText(mText.substring(mDrawStart), true, getWidth(), null);
            if (mText.length() <= numAll+ mDrawStart) {
                drawEndText(canvas, paint, (int) Math.ceil(mTempTextY +getImageHeight()));
                return;
            } else {
                drawNotEndText(canvas, paint, numAll, getWidth());
            }
            mTempTextY += mTextSize + mTextSpace;
        }

        // 绘制和图片同行的文字
        int sideLines = (int) Math.ceil((getImageHeight() + mImgY - topLines * (mTextSize + mTextSpace))/(mTextSize + mTextSpace));
        if (sideLines > 0) {
            // 如果只有左右两边有文字，则让文字居中展示
            if (topLines <= 0) {
                int tempDrawStart = mDrawStart;
                for (int line = 1; line < sideLines; line++) {
                    int numLeft = paint.breakText(mText.substring(tempDrawStart), true, mImgX, null);
                    if (tempDrawStart + numLeft >= mText.length() && (sideLines - line) > 1) {
                        mTempTextY += 0.5f * (getImageHeight() - (mTextSize + mTextSpace) * line);
                        break;
                    }
                    tempDrawStart += numLeft;
                    int numRight = paint.breakText(mText.substring(tempDrawStart), true, getWidth() - mImgX - getImageWidth(), null);
                    if (tempDrawStart + numRight >= mText.length() && (sideLines - line) > 0) {
                        mTempTextY += 0.5f * (getImageHeight() - (mTextSize + mTextSpace) * line);
                        break;
                    }
                    tempDrawStart += numRight;
                }
            }

            for (int i = 0; i < sideLines; i++) {
                // 绘制图片左边的文字
                if(mImgX > mTextSize) {
                    int numLeft = paint.breakText(mText.substring(mDrawStart), true, mImgX, null);
                    if (mText.length() <= numLeft+ mDrawStart) {
                        drawEndText(canvas, paint, (int) Math.ceil(Math.max(mTempTextY, topLines * (mTextSize + mTextSpace) + getImageHeight())));
                        return;
                    } else {
                        drawNotEndText(canvas, paint, numLeft, mImgX);
                    }
                }

                // 绘制图片右边的文字
                if (getWidth() - mImgX - getImageWidth() > mTextSize) {
                    mTempTextX = mImgX + getImageWidth();
                    int numRight = paint.breakText(mText.substring(mDrawStart), true, getWidth() - mImgX - getImageWidth(), null);
                    if (mText.length() <= numRight + mDrawStart) {
                        drawEndText(canvas, paint, (int) Math.ceil(Math.max(mTempTextY, topLines * (mTextSize + mTextSpace) + getImageHeight())));
                        return;
                    } else {
                        drawNotEndText(canvas, paint, numRight, getWidth() - mImgX - getImageWidth());
                    }
                    mTempTextX = 0;
                }
                mTempTextY += mTextSize + mTextSpace;
            }
        }

        long startTime = System.currentTimeMillis();
        // 防止死循环
        while(System.currentTimeMillis() - startTime < 5000) {
            int numAll = paint.breakText(mText.substring(mDrawStart), true, getWidth(), null);
            if (mText.length() <= numAll+ mDrawStart) {
                drawEndText(canvas, paint, (int) Math.ceil(mTempTextY));
                return;
            } else {
                drawNotEndText(canvas, paint, numAll, getWidth());
            }
            mTempTextY += mTextSize + mTextSpace;
        }
        drawEndText(canvas, paint, (int) Math.ceil(mTempTextY));
    }

    private void drawEndText(Canvas canvas, Paint paint, int height) {
        String subStr = mText.substring(mDrawStart, mText.length());
        canvas.drawText(subStr, mTempTextX, mTempTextY, paint);
        setLayout(height);
    }

    private void drawNotEndText(Canvas canvas, Paint paint, int wordNum, float width) {
        String subStr = mText.substring(mDrawStart, wordNum+mDrawStart);
        float offsetX = (width - paint.measureText(subStr)) / wordNum;
        for (int i = 0; i < wordNum; i++, mDrawStart++) {
            subStr = mText.substring(mDrawStart, mDrawStart+1);
            canvas.drawText(subStr, mTempTextX, mTempTextY, paint);
            mTempTextX += offsetX + paint.measureText(subStr);
        }
        mTempTextX = 0;
    }

    /**
     * 因为自定义view不能设置wrap_content,所以绘制完成之后重新设置一下view的高度
     * @param height
     */
    private void setLayout(int height) {
        if (!isDrawn) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = height + 10;
            setLayoutParams(layoutParams);
            isDrawn = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mImage != null) {
            mImage.recycle();
            mImage = null;
        }
    }
}