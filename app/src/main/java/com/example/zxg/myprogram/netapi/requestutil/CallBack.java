package com.example.zxg.myprogram.netapi.requestutil;

/**
 * 回调
 * Created by zxg on 2016/10/2.
 * QQ:1092885570
 */
public abstract class CallBack {

    public abstract void onSucceed(String strJson);

    public abstract void onFailed();
}
