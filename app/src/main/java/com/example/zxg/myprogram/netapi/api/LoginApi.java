package com.example.zxg.myprogram.netapi.api;

import android.content.Context;

import com.example.zxg.myprogram.config.Config;
import com.example.zxg.myprogram.netapi.requestutil.RequestUtil;
import com.example.zxg.myprogram.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxg on 2016/9/30.
 * QQ:1092885570
 */
public abstract class LoginApi extends BaseApi {

    public static String TAG = LoginApi.class.getSimpleName();

    public LoginApi(Context mContext) {
        super(mContext);
    }

    public void getTest(){
        RequestUtil requestUtil = new RequestUtil(mContext) {
            @Override
            public void onSucceed(String jsonStr) {
                LogUtils.i(TAG, "=====接口调用成功=====");
                onResult(jsonStr);
            }

            @Override
            public void onFailed() {
                LogUtils.i(TAG, "=====接口调用失败=====");
            }
        };
//        Map<String, String> map_params = new HashMap<String, String>();
        requestUtil.post(Config.url.URL_LOGIN, null);
    }
}
