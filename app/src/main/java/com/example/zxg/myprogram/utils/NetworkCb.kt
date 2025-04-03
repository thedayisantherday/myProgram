package com.example.zxg.myprogram.utils

import android.content.Context
import android.net.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.Toast

/**
 * @Author: zhuxiaoguang
 * @Email: zhuxiaoguang@shizhuang-inc.com
 * @Date: 2023/11/19 22:09
 * @Description:
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkCb(val context: Context): ConnectivityManager.NetworkCallback() {

    //网络连接成功
    override fun onAvailable(network: Network) {
        Log.d("NetUtils", "网络连接成功")
        Toast.makeText(context, "NetUtils, 网络连接成功", Toast.LENGTH_SHORT).show()
        super.onAvailable(network)
    }

    //网络已断开连接
    override fun onLost(network: Network) {
        Log.d("NetUtils", "网络已断开连接")
        Toast.makeText(context, "NetUtils, 网络已断开连接", Toast.LENGTH_SHORT).show()
        super.onLost(network)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        Log.d("NetUtils", "网络正在断开连接")
        Toast.makeText(context, "NetUtils, 网络正在断开连接", Toast.LENGTH_SHORT).show()
        super.onLosing(network, maxMsToLive)
    }

    //无网络
    override fun onUnavailable() {
        Log.d("NetUtils", "网络连接超时或者网络连接不可达")
        Toast.makeText(context, "NetUtils, 网络连接超时或者网络连接不可达", Toast.LENGTH_SHORT).show()
        super.onUnavailable()
    }

    //当网络状态修改（网络依然可用）时调用
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d("NetUtils", "net status change! 网络连接改变")
        Toast.makeText(context, "NetUtils, net status change! 网络连接改变", Toast.LENGTH_SHORT).show()
        // 表明此网络连接成功验证
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // 使用WI-FI
                Log.d("NetUtils", "当前在使用WiFi上网")
                Toast.makeText(context, "NetUtils, 当前在使用WiFi上网", Toast.LENGTH_SHORT).show()
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // 使用数据网络
                Log.d("NetUtils", "当前在使用数据网络上网")
                Toast.makeText(context, "NetUtils, 当前在使用数据网络上网", Toast.LENGTH_SHORT).show()
            } else{
                Log.d("NetUtils", "当前在使用其他网络")
                Toast.makeText(context, "NetUtils, 当前在使用其他网络", Toast.LENGTH_SHORT).show()
                // 未知网络，包括蓝牙、VPN等
            }
        }
    }

    //当访问的网络被阻塞或者解除阻塞时调用onCapabilitiesChanged
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
    }

    //当网络连接属性发生变化时调用
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
    }
}