/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.zxg.myprogram.utils;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * 名称：ViewLayoutUtil.java
 * 描述：View工具类.
 */

public class ViewLayoutUtil {
	static class AbAppConfig {

		/**  UI设计的基准宽度. */
		public static int UI_WIDTH = 750;

		/**  UI设计的基准高度. */
		public static int UI_HEIGHT = 1334;

		/**  UI设计的密度. */
		public static int UI_DENSITY = 2;
	}
    
    /**
     * 无效值
     */
    public static final int INVALID = Integer.MIN_VALUE;
    

	/**
	 * 测量这个view
	 * 最后通过getMeasuredWidth()获取宽度和高度.
	 * @param view 要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 * 获得这个View的宽度
	 * 测量这个view，最后通过getMeasuredWidth()获取宽度.
	 * @param view 要测量的view
	 * @return 测量过的view的宽度
	 */
	public static int getViewWidth(View view) {
		measureView(view);
		return view.getMeasuredWidth();
	}
	
	/**
	 * 获得这个View的高度
	 * 测量这个view，最后通过getMeasuredHeight()获取高度.
	 * @param view 要测量的view
	 * @return 测量过的view的高度
	 */
	public static int getViewHeight(View view) {
		measureView(view);
		return view.getMeasuredHeight();
	}

    private static DisplayMetrics getDisplayMetrics(Context context) {
		return context.getResources().getDisplayMetrics();
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param context the context
	 * @return the int
	 */
	public static int scaleValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		//为了兼容尺寸小密度大的情况
		if(mDisplayMetrics.scaledDensity > AbAppConfig.UI_DENSITY){
			//密度
			if(mDisplayMetrics.widthPixels > AbAppConfig.UI_WIDTH){
				value = value*(1.3f - 1.0f/mDisplayMetrics.scaledDensity);
			}else if(mDisplayMetrics.widthPixels < AbAppConfig.UI_WIDTH){
				value = value*(1.0f - 1.0f/mDisplayMetrics.scaledDensity);
			}
		}
		return scale(mDisplayMetrics.widthPixels,
				mDisplayMetrics.heightPixels, value);
	}
	
	/**
	 * 描述：根据屏幕大小缩放文本.
	 *
	 * @param context the context
	 * @return the int
	 */
	public static int scaleTextValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
		//为了兼容尺寸小密度大的情况
		if(mDisplayMetrics.scaledDensity > 2){
			//缩小到密度分之一
			//value = value*(1.1f - 1.0f/mDisplayMetrics.scaledDensity);
		}
		return scale(mDisplayMetrics.widthPixels,
				mDisplayMetrics.heightPixels, value);
	}
	
	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param displayWidth the display width
	 * @param displayHeight the display height
	 * @param pxValue the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		if(pxValue == 0 ){
			return 0;
		}
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / AbAppConfig.UI_WIDTH;
			float scaleHeight = (float) displayHeight / AbAppConfig.UI_HEIGHT;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

    /**
     * 缩放文字大小
     * @param textView button
     * @param size sp值
     * @return
     */
    public static void setSPTextSize(TextView textView,float size) {
    	float scaledSize = scaleTextValue(textView.getContext(),size);
        textView.setTextSize(scaledSize);
    }
    
    /**
     * 缩放文字大小,这样设置的好处是文字的大小不和密度有关，
     * 能够使文字大小在不同的屏幕上显示比例正确
     * @param textView button
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(TextView textView,float sizePixels) {
    	float scaledSize = scaleTextValue(textView.getContext(),sizePixels);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,scaledSize);
    }
    
    /**
     * 缩放文字大小
     * @param context
     * @param textPaint
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(Context context,TextPaint textPaint,float sizePixels) {
    	float scaledSize = scaleTextValue(context,sizePixels);
    	textPaint.setTextSize(scaledSize);
    }
    
    /**
     * 缩放文字大小
     * @param context
     * @param paint
     * @param sizePixels px值
     * @return
     */
    public static void setTextSize(Context context,Paint paint,float sizePixels) {
    	float scaledSize = scaleTextValue(context,sizePixels);
    	paint.setTextSize(scaledSize);
    }
    
   /**
    * 设置View的PX尺寸
    * @param view  如果是代码new出来的View，需要设置一个适合的LayoutParams
    * @param widthPixels
    * @param heightPixels
    */
    public static void setViewSize(View view,int widthPixels, int heightPixels){
        int scaledWidth = scaleValue(view.getContext(), widthPixels);
        int scaledHeight = scaleValue(view.getContext(), heightPixels);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(params == null){
            return;
        }
        if (widthPixels != INVALID){
            params.width = scaledWidth;
        }
        if (heightPixels != INVALID){
            params.height = scaledHeight;
        }
        view.setLayoutParams(params);
    }

	/**
	 * 设置PX padding.
	 *
	 * @param view the view
	 * @param left the left padding in pixels
     * @param top the top padding in pixels
     * @param right the right padding in pixels
     * @param bottom the bottom padding in pixels
	 */
	public static void setPadding(View view, int left,
			int top, int right, int bottom) {
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}
	/**
	 * 设置 PX margin.
	 *
	 * @param view the view
	 * @param left the left margin in pixels
	 * @param top the top margin in pixels
	 * @param right the right margin in pixels
	 * @param bottom the bottom margin in pixels
	 */
	public static void setMargin(View view, int left, int top,
								 int right, int bottom) {
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);

		if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
			ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams) view
					.getLayoutParams();
			if (mMarginLayoutParams != null){
				if (left != INVALID) {
					mMarginLayoutParams.leftMargin = scaledLeft;
				}
				if (right != INVALID) {
					mMarginLayoutParams.rightMargin = scaledRight;
				}
				if (top != INVALID) {
					mMarginLayoutParams.topMargin = scaledTop;
				}
				if (bottom != INVALID) {
					mMarginLayoutParams.bottomMargin = scaledBottom;
				}
				view.setLayoutParams(mMarginLayoutParams);
			}
		}

	}
	/**
	 * 设置 PX margin.
	 *
	 * @param view the view
	 * @param left the left margin in pixels
	 * @param top the top margin in pixels
	 * @param right the right margin in pixels
	 * @param bottom the bottom margin in pixels
	 */
	public static void setMargin2(View view, int left, int top,
								 int right, int bottom) {
		if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
			ViewGroup.MarginLayoutParams mMarginLayoutParams = (ViewGroup.MarginLayoutParams) view
					.getLayoutParams();
			if (mMarginLayoutParams != null){
				if (left != INVALID) {
					mMarginLayoutParams.leftMargin = left;
				}
				if (right != INVALID) {
					mMarginLayoutParams.rightMargin = right;
				}
				if (top != INVALID) {
					mMarginLayoutParams.topMargin = top;
				}
				if (bottom != INVALID) {
					mMarginLayoutParams.bottomMargin = bottom;
				}
				view.setLayoutParams(mMarginLayoutParams);
			}
		}
	}
}
