package com.example.zxg.myprogram.netapi.api;

import android.content.Context;

/**接口的基类
 * Created by zxg on 2016/10/24.
 * QQ:1092885570
 */

public abstract class BaseApi {
    protected static Context mContext;

    public BaseApi(Context context) {
        this.mContext = context;
    }

    public abstract void onResult(String jsonStr);

    public abstract void onFail();
}
