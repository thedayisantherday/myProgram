package com.example.zxg.myprogram.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zxg on 2018/10/26.
 */

public class SharedPreferencesUtil {

    private static final String PREFERENCES_NAME = "preference_moHeBeerBar";

    private static volatile SharedPreferences mSharedPreferences;

    private SharedPreferencesUtil() {}

    public static SharedPreferences getSharedPreferences(Context context) {
        if (null == mSharedPreferences) {
            synchronized (SharedPreferencesUtil.class) {
                if (null == mSharedPreferences) {
                    mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
                }
            }
        }
        return mSharedPreferences;
    }

    /**
     * apply()和commit()方法的区别：
     * 1、commit返回boolean表明修改是否提交成功，而apply没有返回值
     * 2、commit是同步的提交到硬件磁盘，因此，在多个并发的提交commit时，会等待正在处理的commit保存到磁盘后再操作，从而降低了效率。
     *    apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, apply只是原子的提交到内容，后调用apply的函数的将会直接覆盖前面的内存数据，从一定程度上提高了效率。
     *
     *
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static void putLong(Context context, String key, long value) {
        getSharedPreferences(context).edit().putLong(key, value).apply();
    }

    public static void putFloat(Context context, String key, float value) {
        getSharedPreferences(context).edit().putFloat(key, value).apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static void putString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }

    public static void remove(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).apply();
    }
}
