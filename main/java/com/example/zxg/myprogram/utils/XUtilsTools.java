package com.example.zxg.myprogram.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.BitmapGlobalConfig;
import com.lidroid.xutils.util.OtherUtils;

/**
 * Created by zxg on 2016/11/4.
 * QQ:1092885570
 */

public class XUtilsTools {

    private static String IMAGE_CACHE_PATH = null;

    /**
     * xutils加载网络图片
     * @param context
     * @param imageView 图片展示在该imageView
     * @param url 网络请求url
     * @param loadingImg 显示正在加载的image资源id，为null则不显示正在加载的image
     * @param errImg 显示加载错误的image资源id，为null则不显示加载错误的image
     */
    public static void displayImage(Context context, ImageView imageView,
                                    String url, Integer loadingImg, Integer errImg){
        if (Tools.isEmpty(url)){
            return;
        }

        //TODO 具体项目直接换成项目名称，不用这么复杂
        //得到应用程序报名
        String[] names = context.getPackageName().split("\\.");
        IMAGE_CACHE_PATH = names[names.length-1] + "_img_cache";
        LogUtils.i("==IMAGE_CACHE_PATH", IMAGE_CACHE_PATH);

        String cachePath = OtherUtils.getDiskCacheDir(context, IMAGE_CACHE_PATH);
        BitmapUtils bitmapUtils = new BitmapUtils(context, cachePath);
        if (loadingImg != null){
            bitmapUtils.configDefaultLoadingImage(errImg);
        }
        if (errImg != null){
            bitmapUtils.configDefaultLoadFailedImage(errImg);
        }
        bitmapUtils.display(imageView, url);
    }

    /**
     *
     * @param context
     * @param imageView imageView 图片展示在该imageView
     * @param url 网络请求url
     * @param errImg 加载失败时，显示默认的image资源id
     */
    public static void displayCheckImage(Context context, ImageView imageView, String url, int errImg) {
        //TODO 具体项目直接换成项目名称，不用这么复杂
        //得到应用程序报名
        /*String[] names = context.getPackageName().split("\\.");
        IMAGE_CACHE_PATH = names[names.length-1] + "_img_cache";
        LogUtils.i("==IMAGE_CACHE_PATH", IMAGE_CACHE_PATH);

        String cachePath = OtherUtils.getDiskCacheDir(context, IMAGE_CACHE_PATH);
        BitmapGlobalConfig globalConfig = BitmapGlobalConfig.getInstance(context, cachePath);
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        displayConfig.setLoadFailedDrawable(context.getResources().getDrawable(errImg));
        globalConfig.setDownloader(new Cookie);

        BitmapUtils bitmapUtils = new BitmapUtils(context, cachePath);
        if (NetUtils.isNetworkAvailable(context)) {
            bitmapUtils.display(imageView, url, displayConfig);
        }else {
            imageView.setImageResource(errImg);
        }*/
    }
}
