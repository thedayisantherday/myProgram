package com.example.zxg.myprogram.netapi.requestutil;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.MyApplication;
import com.example.zxg.myprogram.config.Config;
import com.example.zxg.myprogram.model.CommonModel;
import com.example.zxg.myprogram.netapi.Cookie.PersistentCookieStore;
import com.example.zxg.myprogram.utils.JsonUtils;
import com.example.zxg.myprogram.utils.LogUtils;
import com.example.zxg.myprogram.utils.Tools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.entity.BodyParamsEntity;

import org.apache.http.HttpEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by zxg on 2016/9/30.
 * QQ:1092885570
 */
public abstract class RequestUtil extends CallBack{

    private static String TAG = RequestUtil.class.getSimpleName();

    private Context mContext;
    //网络加载弹窗
    private Dialog mDialog;
    //是否显示网络加载弹窗
    public boolean isShowDialog = false;
    private HttpUtils mHttpUtils;
    private PersistentCookieStore persistentCookieStore;

    //永久token
//    public static String everToken = "";

    public RequestUtil(Context context){
        this(context, null, false);
    }

    /**
     * @param context
     * @param prompt 网络加载对话框显示的提示信息
     * @param isShowDialog 是否显示网络加载对话框
     */
    public RequestUtil(Context context, String prompt, boolean isShowDialog){
        super();
        this.mContext = context;
        this.isShowDialog = isShowDialog;

        mHttpUtils = new HttpUtils(1000*30);
        mHttpUtils.configRequestRetryCount(2);
        // 将cookie添加到HttpUtils中
        persistentCookieStore = MyApplication.getPersistentCookieStore();
        if (persistentCookieStore == null) {
            persistentCookieStore = new PersistentCookieStore(MyApplication.getInstance().getApplicationContext());
        }
        if (persistentCookieStore != null) {
            mHttpUtils.configCookieStore(persistentCookieStore);
        }

        if (isShowDialog){
            mDialog = new Dialog(mContext, R.style.dialog_netrequest);
            mDialog.setContentView(R.layout.dialog_netrequest);
            if(!Tools.isEmpty(prompt)){
                TextView textView = (TextView) mDialog.findViewById(R.id.tv_content);
                textView.setText(prompt);
            }
        }
    }

//    public void post(final String url){
//        post(url, null);
//    }
    /**post请求
     *
     * @param url
     * @param map_params map类型请求参数，可以为null
     */
    public void post(final String url, final Map<String, String> map_params){
        RequestParams params = MyRequestParams.parseMapToParams(map_params);
        //调试模式将请求的url和参数打印log
        HttpEntity entity = params.getEntity();
        if(Config.DEBUG) {
            if (entity!=null && entity instanceof BodyParamsEntity) {
                try {
                    StringBuffer buffer = new StringBuffer();
                    byte[] bytes = new byte[1024];
                    InputStream in = entity.getContent();
                    for (int i = 0; (i = in.read(bytes)) != -1 ; ) {
                        buffer.append(new String(bytes), 0, i);
                    }
                    LogUtils.i(TAG, "---URL=" + url + "---request=[" + buffer.toString() + "]");
                } catch (IOException e) {
                  e.printStackTrace();
                }
            }
        }

        mHttpUtils.send(HttpRequest.HttpMethod.POST, url, params,
            new RequestCallBack<String>() {
                @Override
                public void onStart() {
                    //TODO 待开发，显示正在加载Dialog
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    try {
                        String strJson = responseInfo.result;
                        CommonModel model = JsonUtils.parseJsonToJavaBean(strJson, CommonModel.class);
                        //TODO
                        //待开发，请求成功之后解析
                        onSucceed(strJson);

                    }catch (Exception e){
                        e.printStackTrace();
                        onFailed();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    //TODO
                    LogUtils.e(TAG, "网络请求失败："+error.getMessage());
                    onFailed();
                }
            });
    }

    /**
     * 保存Cookie，主要是在用户登录时保存Cookie
     */
    //TODO 待添加，用户登录时，保存cookies
    public void saveCookies(){
        PersistentCookieStore cookieStore = MyApplication.getPersistentCookieStore();
        DefaultHttpClient httpClient = (DefaultHttpClient) mHttpUtils.getHttpClient();
        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        for (Cookie cookie : cookies){
            cookieStore.addCookie(cookie);
        }
    }
}
