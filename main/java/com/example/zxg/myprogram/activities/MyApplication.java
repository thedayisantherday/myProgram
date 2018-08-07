package com.example.zxg.myprogram.activities;

import android.app.Application;

import com.example.zxg.myprogram.netapi.Cookie.PersistentCookieStore;
import com.lidroid.xutils.util.PreferencesCookieStore;

/**
 * Created by zxg on 2016/10/8.
 * QQ:1092885570
 */
//TODO 应用在其他项目中时，需要修改名字
public class MyApplication extends Application{

    private static PersistentCookieStore persistentCookieStore = null;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取Cookie
     * @return
     */
    public synchronized static PersistentCookieStore getPersistentCookieStore() {
        if (persistentCookieStore == null){
            persistentCookieStore = new PersistentCookieStore(instance.getApplicationContext());
        }
        return persistentCookieStore;
    }
}
