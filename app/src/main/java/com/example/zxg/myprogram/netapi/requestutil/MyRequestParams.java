package com.example.zxg.myprogram.netapi.requestutil;

import com.example.zxg.myprogram.utils.Tools;
import com.lidroid.xutils.http.RequestParams;

import java.util.Map;

/**
 * 网络请求参数转换
 * Created by zxg on 2016/9/30.
 * QQ:1092885570
 */
public class MyRequestParams {

    /**
     * 将map存储的网络参数转换成XUtils的RequestParams参数
     * 用map存储网络参数有利于程序跟XUtils解耦，方便以后替换XUtils网络请求库
     * @param map
     * @return
     */
    public static RequestParams parseMapToParams (Map<String, String> map){
        RequestParams params = new RequestParams();

        params.addBodyParameter("platform", "Android");

        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!Tools.isEmpty(entry.getValue())) {
                    params.addBodyParameter(entry.getKey(), entry.getValue());
                }
            }
        }

        return params;
    }
}
