package com.example.zxg.myprogram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class DPIUtil {
    private static float mDensity = 160.0F;
    private static Display defaultDisplay;
    private static Point outSize = null;

    public DPIUtil() {
    }

    public static void setDensity(float density) {
        mDensity = density;
    }

    public static float getDensity() {
        return mDensity;
    }

    public static Display getDefaultDisplay(Context context) {
        if (null == defaultDisplay) {
            WindowManager wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            defaultDisplay = wm.getDefaultDisplay();
        }

        return defaultDisplay;
    }

    public static int percentWidth(Context context, float percent) {
        return (int)((float)getWidth(context) * percent);
    }

    public static int percentHeight(Context context, float percent) {
        return (int)((float)getHeight(context) * percent);
    }

    public static int customDip2px(float dipValue) {
        return (int)(dipValue * mDensity + 0.5F);
    }

    public static int customPx2dip(float pxValue) {
        return (int)(pxValue / mDensity + 0.5F);
    }

    public static int getWidth(Context context) {
        if (outSize == null) {
            synchronized(DPIUtil.class) {
                if (outSize == null) {
                    getPxSize(context);
                }
            }
        }

        return outSize.x;
    }

    public static int getHeight(Context context) {
        Display display = getDefaultDisplay(context);
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    public static int px2sp(Context context, float pxValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scaledDensity + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)((spValue - 0.5F) * scaledDensity);
    }

    public static int dip2px(Context context, float dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * density + 0.5F);
    }

    public static int getWidthByDesignValue(Context context, int nDesignValue, int nDesignScreenWidth) {
        return (int)((float)(getWidth(context) * nDesignValue) / (float)nDesignScreenWidth + 0.5F);
    }

    public static int getWidthByDesignValue720(Context context, int nDesignValue) {
        return getWidthByDesignValue(context, nDesignValue, 720);
    }

    public static int getWidthByDesignValue750(Context context, int nDesignValue) {
        return getWidthByDesignValue(context, nDesignValue, 750);
    }

    public static void getPxSize(Context context) {
        Display display = getDefaultDisplay(context);
        outSize = new Point();
        display.getSize(outSize);
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean hasNavigationBar(Context context) {
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String)m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }
}