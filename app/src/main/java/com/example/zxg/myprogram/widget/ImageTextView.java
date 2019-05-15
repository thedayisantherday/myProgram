package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxg.myprogram.R;

/**
 * Created by zxg on 2019/01/23.
 *
 * 图文混排组件，仅支持图片在文字的四个脚
 * 若要支持在图片行中间时，需要增加位移处理，待后续处理
 */
public class ImageTextView extends LinearLayout{

    private static final int TEXTVIEW_NUM = 4;

    private Context mContext;
    private ImageView iv_img;
    private TextView[] textViews = new TextView[TEXTVIEW_NUM];

    private Drawable imgSrc;
    private int[] tv_lines = new int[TEXTVIEW_NUM];
    private int[] tv_visibility = new int[TEXTVIEW_NUM];
    private int textColor;
    private int textSize;
    private float lineSpacingAdd, lineSpacingMult;
    private String text, overflowText;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

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
        initView();
    }

    private void initData(@Nullable AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ImageTextViewAttr);
        imgSrc = typedArray.getDrawable(R.styleable.ImageTextViewAttr_imgSrc);
        tv_lines[0] = typedArray.getInt(R.styleable.ImageTextViewAttr_topTVLines, 0);
        tv_lines[1] = typedArray.getInt(R.styleable.ImageTextViewAttr_leftTVLines, 0);
        tv_lines[2] = typedArray.getInt(R.styleable.ImageTextViewAttr_rightTVLines, 0);
        tv_lines[3] = typedArray.getInt(R.styleable.ImageTextViewAttr_bottomTVLines, 0);
        tv_visibility[0] = typedArray.getInt(R.styleable.ImageTextViewAttr_topTVVisibility, 0);
        tv_visibility[1] = typedArray.getInt(R.styleable.ImageTextViewAttr_leftTVVisibility, 0);
        tv_visibility[2] = typedArray.getInt(R.styleable.ImageTextViewAttr_rightTVVisibility, 0);
        tv_visibility[3] = typedArray.getInt(R.styleable.ImageTextViewAttr_bottomTVVisibility, 0);
        textColor = typedArray.getColor(R.styleable.ImageTextViewAttr_textColor, Color.BLACK);
        text = typedArray.getString(R.styleable.ImageTextViewAttr_text);
        overflowText = text;

        typedArray.recycle();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_image_textview,null);
        addView(view,new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        iv_img = (ImageView) findViewById(R.id.iv_img);
        textViews[0] = (TextView) findViewById(R.id.tv_top);
        textViews[1] = (TextView) findViewById(R.id.tv_left);
        textViews[2] = (TextView) findViewById(R.id.tv_right);
        textViews[3] = (TextView) findViewById(R.id.tv_bottom);

        iv_img.setImageDrawable(imgSrc);
        for (int i = 0; i < TEXTVIEW_NUM; i++) {
            textViews[i].setVisibility(tv_visibility[i]);
            textViews[i].setTextColor(textColor);
            if (textSize > 0) {
                textViews[i].setTextSize(textSize);
            }
        }
        setTVText(0);
    }

    /**
     * 逐个计算每个textview能显示的字符串长度
     * @param index
     */
    private void setTVText(final int index) {
        if (index < TEXTVIEW_NUM) {
            if (null == textViews[index] || textViews[index].getVisibility() != VISIBLE || tv_lines[index] <= 0) {
                if (null != textViews[index]) {
                    textViews[index].setVisibility(GONE);
                }
                setTVText(index+1);
                return;
            }

            textViews[index].setMaxLines(tv_lines[index]);
            textViews[index].setText(overflowText);
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    getOverflowText(textViews[index], tv_lines[index]);
                    setTVText(index+1);
                }
            };
            mHandler.postDelayed(mRunnable, 5);
        }
    }

    /**
     * 为每个textview分配显示的字符串
     * 分割字符串时，需要做延时处理；否则textView.getLayout()为null
     * @param textView
     * @param maxLines
     */
    private void getOverflowText(TextView textView, int maxLines) {
        if (null == textView || maxLines <= 0) {
            return;
        }

        Layout layout = textView.getLayout();
        int lines = textView.getLineCount();
        if (null == layout || lines <= 0) {
            return;
        }

        int end = layout.getLineEnd(Math.min(lines, maxLines) - 1);
        overflowText = overflowText.substring(end);
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        for (int i = 0; i < TEXTVIEW_NUM; i++) {
            textViews[i].setTextColor(textColor);
        }
    }

    public void setTextSize(int pixel) {
        this.textSize = pixel;
        for (int i = 0; i < TEXTVIEW_NUM; i++) {
            textViews[i].setTextSize(pixel);
        }
    }

    public void setTextviewLines(int top, int left, int right, int bottom) {
        mHandler.removeCallbacks(mRunnable);
        tv_lines = new int[]{top, left, right, bottom};
        overflowText = text;
        setTVText(0);
    }

    public void setTextViewLineSpacing(float add, float mult) {
        if (lineSpacingAdd != add || lineSpacingMult != mult) {
            lineSpacingAdd = add;
            lineSpacingMult = mult;

            for (int i = 0; i < TEXTVIEW_NUM; i++) {
                textViews[i].setLineSpacing(add, mult);
            }
        }
    }
}
