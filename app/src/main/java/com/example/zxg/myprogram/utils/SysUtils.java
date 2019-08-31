package com.example.zxg.myprogram.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 系统相关工具类
 * Created by zxg on 2016/10/5.
 * QQ:1092885570
 */
public class SysUtils {

    public static final String TAG = SysUtils.class.getSimpleName();

    /**
     * 判断当前进程是否在运行
     * @return
     */
    public static boolean isAppRunning(Context context) {
        if (context == null){
            return false;
        }
        return isAppRunning(context, context.getPackageName());
    }
    /**
     * 判断指定包名的进程是否在运行
     * @param packageName
     * @return
     */
    public static boolean isAppRunning(Context context, String packageName) {
        if (context == null || Tools.isEmpty(packageName)){
            return false;
        }

        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
            for (RunningAppProcessInfo rapi : processInfos){
                if (rapi.processName.equals(packageName)){
                    return true;
                }
            }
//            List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(100);
//            for (ActivityManager.RunningTaskInfo rapi : taskInfos){
//                if (rapi.topActivity.getPackageName().equals(packageName)){
//                    return true;
//                }
//                activityManager.killBackgroundProcesses(packageName);
//            }
        }catch (Exception e){
            LogUtils.e(TAG, "判断" + packageName + "是否允许：" + e);
        }
        return false;
    }

    /**
     * 判断当前activity是否在前台运行
     * @return
     */
    public static boolean isActivityForeground(Context context) {
        if (context == null){
            return false;
        }
        return isActivityForeground(context, context.getClass().getName());
    }

    /**
     * 判断指定activity是否在前台运行
     * @param className 全限定类名
     * @return
     */
    public static boolean isActivityForeground(Context context, String className) {
        if (context == null || Tools.isEmpty(className)){
            return false;
        }

        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
            for (ActivityManager.RunningTaskInfo rti : runningTaskInfos){
                if (rti.topActivity.getClassName().equals(className)){
                    return true;
                }
            }
        }catch (Exception e){
            LogUtils.e(TAG, "判断" + className + "是否允许：" + e);
        }
        return false;
    }
}
