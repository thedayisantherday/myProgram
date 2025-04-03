package com.example.zxg.myprogram.activities

import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*
import com.example.zxg.myprogram.R
import com.example.zxg.myprogram.utils.LogUtils
import com.example.zxg.myprogram.utils.NetUtils
import java.io.*
import java.net.*

/**
 * @Author: zhuxiaoguang
 * @Email: zhuxiaoguang@shizhuang-inc.com
 * @Date: 2023/11/20 15:52
 * @Description:
 */

class NetworkActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NetworkActivity"
    }

    private var mWebView: WebView? = null
    private var tvTestWifi: TextView? = null
    private var tvTestMobile: TextView? = null
    private var btnSwitch2Wifi: Button? = null
    private var btnSwitch2Mobile: Button? = null
    private var btnLoadUrl: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        initView()
        Log.i("NetworkActivity", "networkActivity")
        settingProperty()
        btnLoadUrl?.setOnClickListener {
//            mWebView?.loadUrl("https://blog.51cto.com/u_16175484/7754671")
//            updateView()

            val currentTime = System.currentTimeMillis()
            val thread = Thread {
                downloadFile("http://editorup.zol.com.cn/upload/200911/4baaea43ece71.zip", "test-${currentTime}.zip")
//                downloadFile("https://pic1.zhimg.com/v2-eec5a661e5f271869feb04810ca68d90_r.jpg?source=2c26e567", "test-${currentTime}.png")
            }
            thread.start()
        }
        btnSwitch2Wifi?.setOnClickListener {
            NetUtils.switchToWIFI(baseContext)
        }
        btnSwitch2Mobile?.setOnClickListener {
            NetUtils.switchToMobile(baseContext)
        }
        updateView()
    }

    private fun initView() {
        mWebView = findViewById(R.id.webview)
        tvTestWifi = findViewById(R.id.tv_test_wifi)
        tvTestMobile = findViewById(R.id.tv_test_cellular)
        btnSwitch2Wifi = findViewById(R.id.btn_switch_to_wifi)
        btnSwitch2Mobile = findViewById(R.id.btn_switch_to_cellular)
        btnLoadUrl = findViewById(R.id.btn_load_url)
    }

    private fun updateView() {
        val isWifi = NetUtils.isWifiConnected(baseContext)
        val isMobile = NetUtils.isMobileConnected(baseContext)
        val netType = NetUtils.getNetConnectType(baseContext)
        val ipAddr = NetUtils.getCurrentIpAddr()
        Toast.makeText(baseContext, "updateView: isWifi = $isWifi, isMobile = $isMobile, netType = ${netType}, ipAddr = ${ipAddr}", Toast.LENGTH_SHORT).show()
        tvTestWifi?.text = if (isWifi) "yes" else "no"
        tvTestMobile?.text = if (isMobile) "yes" else "no"
    }

    private fun settingProperty() {
        val webSettings = mWebView!!.settings
        //支持js
        webSettings.javaScriptEnabled = true
        //支持通过JS打开新窗口
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        //缩放至屏幕的大小
        webSettings.loadWithOverviewMode = true
        //将图片调整到适合webview的大小
        webSettings.useWideViewPort = false
        //设置缓存方式
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        //开启DOM storage API功能
        webSettings.domStorageEnabled = true
        //开启 database storage API 功能
        webSettings.databaseEnabled = true
        val cacheDirPath = cacheDir.absolutePath + "/webViewCache"
        //设置数据库缓存路径
        webSettings.databasePath = cacheDirPath
        //开启Application H5 Caches 功能
        webSettings.setAppCacheEnabled(true)
        //设置Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath)
        /**
         * 对系统API在19以上的版本作了兼容。因为4.4以上系统在onPageFinished时再恢复图片加载时,
         * 如果存在多张图片引用的是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载
         */
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.loadsImagesAutomatically = true
        } else {
            webSettings.loadsImagesAutomatically = false
        }
    }

    private fun downloadFile(urlString: String, savePath: String) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val inputStream: InputStream = connection.inputStream
            val externalDir = getExternalFilesDir(null) // filesDir、getExternalFilesDir(null) 或者 getExternalStoragePublicDirectory(null)
            val file = File(externalDir, savePath)
            val outputStream = FileOutputStream(file)

            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int

            while (inputStream.read(data).also { count = it } != -1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    outputStream.write(data, 0, count)
                } else {
                    outputStream.write(data)
                }
                total += count.toLong()
                LogUtils.i(TAG, "downloadFile: File $savePath is downloading")
            }

            LogUtils.i(TAG, "downloadFile: File $savePath is downloaded")
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}