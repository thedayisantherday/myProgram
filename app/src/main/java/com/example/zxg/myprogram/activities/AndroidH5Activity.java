package com.example.zxg.myprogram.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.utils.LogUtils;

import org.json.JSONObject;

import java.io.Serializable;

public class AndroidH5Activity extends BaseActivity {

    private static String TAG = AndroidH5Activity.class.getSimpleName();

    private WebView mWebView;
    private MyJavascriptInterface myJavascriptInterface;

    private int num_js = 0;
    private int num_android = 0;

    private class TestJs implements Serializable {
        public int num_1 = 1;
        public int num_2 = 2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_android_h5);

        initView();

        settingProperty();
        mWebView.loadUrl("file:///android_asset/html/h5Native.html");
    }

    private void initView(){
        mWebView = (WebView) findViewById(R.id.wv_test);
        Button button = (Button) findViewById(R.id.btn_test);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("test", "1");
            jsonObject.put("test_1", new TestJs());
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:callbackFromNative('the data is from android! "+ jsonObject+"')");
            }
        });
    }

    private void settingProperty(){
        myJavascriptInterface = new MyJavascriptInterface();
        WebSettings webSettings = mWebView.getSettings();
        //支持js
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(false);
        //设置缓存方式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //开启DOM storage API功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);

        String cacheDirPath = getCacheDir().getAbsolutePath()+ "/webViewCache";
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //开启Application H5 Caches 功能
        webSettings.setAppCacheEnabled(true);
        //设置Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);

        /**
         * 对系统API在19以上的版本作了兼容。因为4.4以上系统在onPageFinished时再恢复图片加载时,
         * 如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载
         */
        if (Build.VERSION.SDK_INT >= 19){
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.addJavascriptInterface(myJavascriptInterface, "android");
    }

    @Override
    public void initHead(ViewHolder viewHolder) {
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidH5Activity.this.finish();
            }
        });
    }


    public class MyJavascriptInterface{
        @JavascriptInterface
        public String h5NativtCallback(String json){
            LogUtils.i(TAG, "h5NativeCallback");
            return "js 调用 android 的回调 "+ json + (num_android++);
        }
    }

    /**
     * 处理webview的各种通知与事件
     * @author EX-SUNWEIMIN001
     */
    public class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //TODO  重写此方法让webview的跳转在一个界面完成
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * 重写webview的浏览器客户端
     * 主要功能是辅助webview处理javascript对话框，网页的icon和title，以及加载进度条等
     */
    public class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //TODO 接收html的title标签，可根据html的title改变而改变
        }
    }
}
