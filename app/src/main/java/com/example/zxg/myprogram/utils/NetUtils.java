package com.example.zxg.myprogram.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**网络相关工具类
 * Created by zxg on 2016/10/24.
 * QQ:1092885570
 */

public class NetUtils {

    public final static int CONNECTED_TYPE_MOBILE = 5;
    public final static int CONNECTED_TYPE_4G = 4;
    public final static int CONNECTED_TYPE_3G = 3;
    public final static int CONNECTED_TYPE_2G = 2;
    public final static int CONNECTED_TYPE_WIFI = 1;
    public final static int CONNECTED_TYPE_OTHER = 0;
    public final static int CONNECTED_TYPE_UNCONNECTED = -1;

    public static final String IP_DEFAULT = "0.0.0.0";

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return true if network is available, otherwise false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isAvailable();

    }

    /**
     * 判断已连接的网络是否为wifi
     *
     * @param context
     * @return true if WIFI is connected, otherwise false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiNetworkInfo!=null && wifiNetworkInfo.isConnected();
        } else {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities status = connectivityManager.getNetworkCapabilities(network);
            return status != null && status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
    }

    /**
     * 判断已连接的网络是否为移动网络（2G/3G/4G）
     *
     * @param context
     * @return true if Mobile is connected, otherwise false
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            NetworkInfo mMobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return mMobileNetworkInfo!=null && mMobileNetworkInfo.isConnected();
        } else {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities status = connectivityManager.getNetworkCapabilities(network);
            if (status != null && status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取连接网络的类型名称
     * @param context
     * @return
     */
    public static int getNetConnectType(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //wifi网络
        if (isWifiConnected(context)){
            return NetUtils.CONNECTED_TYPE_WIFI;
        } else if (isMobileConnected(context)){
            NetworkInfo allNetwork = connectivityManager.getActiveNetworkInfo();
            NetworkInfo mobileNetworkInfo =
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            String strTypeName = mobileNetworkInfo.getSubtypeName();
            switch (allNetwork.getSubtype()){
                //4G网络
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetUtils.CONNECTED_TYPE_4G;
                //3G网络
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetUtils.CONNECTED_TYPE_3G;
                //2G网络
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetUtils.CONNECTED_TYPE_2G;

                default:
                    if (strTypeName.equalsIgnoreCase("TD-SCDMA") ||
                            strTypeName.equalsIgnoreCase("WCDMA") || strTypeName.equalsIgnoreCase("CDMA2000")) {
                        return NetUtils.CONNECTED_TYPE_3G;
                    } else {
                        return NetUtils.CONNECTED_TYPE_MOBILE;
                    }
            }
        } else if (isNetworkAvailable(context)){
            return NetUtils.CONNECTED_TYPE_OTHER;
        } else {
            return NetUtils.CONNECTED_TYPE_UNCONNECTED;
        }
    }

    /**
     * 获取当前设备的IP地址
     * （需要开启权限：<uses-permission android:name="android.permission.INTERNET" />)
     *
     * @return current IP address of the device
     */
    public static String getCurrentIpAddr(){
        try {
            Enumeration<NetworkInterface> netInterfaceEnum = NetworkInterface.getNetworkInterfaces();
            while (netInterfaceEnum.hasMoreElements()) {
                NetworkInterface networkInterface = netInterfaceEnum.nextElement();
                Enumeration<InetAddress> inetAddrEnum = networkInterface.getInetAddresses();
                while (inetAddrEnum.hasMoreElements()) {
                    InetAddress inetAddr = inetAddrEnum.nextElement();
                    if (!inetAddr.isLoopbackAddress() &&
                            !inetAddr.isLinkLocalAddress() && inetAddr.isSiteLocalAddress()) {
                        return inetAddr.getHostAddress();
                    }
                }
            }
        }catch (SocketException e){
            e.printStackTrace();
        }
        return IP_DEFAULT;
    }

    /**
     * 通过WifiManager获取当前设备的IP地址（注意需要开启以下权限：
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     * <uses-permission android:name="android.permission.WAKE_LOCK" />
     *
     * @param context
     * @return current wifi IP address of the device
     */
    public static String getCurrentWifiIPAddr(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddr = wifiInfo.getIpAddress();
        return intIP2StringIP(ipAddr);
    }

    /**将整数表示的ip转换成ip地址
     *
     * @param intIP 整数形式表示的ip地址
     * @return
     */
    public static String intIP2StringIP(int intIP) {
        StringBuilder strB_IP = new StringBuilder();
        strB_IP.append(intIP & 0xFF).append(".");
        strB_IP.append((intIP >> 8) & 0xFF).append(".");
        strB_IP.append((intIP >> 16) & 0xFF).append(".");
        strB_IP.append((intIP >> 24) & 0xFF);
        return strB_IP.toString();
    }

    public static void switchToWIFI(Context context) {
        Toast.makeText(context, "切换到wifi", Toast.LENGTH_SHORT).show();
        // 获取ConnectivityManager
        ConnectivityManager sConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 创建 network request，网络类型是蜂窝数据
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            // 请求网络
            sConnectivityManager.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {

                //网络连接成功
                @Override
                public void onAvailable(Network network) {
                    Log.d("NetUtils", "网络连接成功");
                    Toast.makeText(context, "NetUtils, 网络连接成功, isWifiConnected = " + isWifiConnected(context), Toast.LENGTH_SHORT).show();
                    super.onAvailable(network);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sConnectivityManager.bindProcessToNetwork(network);
                    }
                }

                //网络已断开连接
                @Override
                public void onLost(Network network) {
                    Log.d("NetUtils", "网络已断开连接");
                    Toast.makeText(context, "NetUtils, 网络已断开连接", Toast.LENGTH_SHORT).show();
                    super.onLost(network);
                }
                @Override
                public void onLosing(Network network, int maxMsToLive) {
                    Log.d("NetUtils", "网络正在断开连接");
                    Toast.makeText(context, "NetUtils, 网络正在断开连接", Toast.LENGTH_SHORT).show();
                    super.onLosing(network, maxMsToLive);
                }

                //无网络
                @Override
                public void onUnavailable() {
                    Log.d("NetUtils", "网络连接超时或者网络连接不可达");
                    Toast.makeText(context, "NetUtils, 网络连接超时或者网络连接不可达", Toast.LENGTH_SHORT).show();
                    super.onUnavailable();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sConnectivityManager.bindProcessToNetwork(null);
                    }
                }

                //当网络状态修改（网络依然可用）时调用
                @Override
                public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    Log.d("NetUtils", "net status change! 网络连接改变");
                    Toast.makeText(context, "NetUtils, net status change! 网络连接改变", Toast.LENGTH_SHORT).show();
                    // 表明此网络连接成功验证
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            // 使用WI-FI
                            Log.d("NetUtils", "当前在使用WiFi上网");
                            Toast.makeText(context, "NetUtils, 当前在使用WiFi上网", Toast.LENGTH_SHORT).show();
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            // 使用数据网络
                            Log.d("NetUtils", "当前在使用数据网络上网");
                            Toast.makeText(context, "NetUtils, 当前在使用数据网络上网", Toast.LENGTH_SHORT).show();
                        } else{
                            Log.d("NetUtils", "当前在使用其他网络");
                            Toast.makeText(context, "NetUtils, 当前在使用其他网络", Toast.LENGTH_SHORT).show();
                            // 未知网络，包括蓝牙、VPN等
                        }
                    }
                }

                //当网络连接属性发生变化时调用
                @Override
                public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties);
                }

                //当访问的网络被阻塞或者解除阻塞时调用onCapabilitiesChanged
                @Override
                public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
                    super.onBlockedStatusChanged(network, blocked);
                }
            });
        }
    }


    public static void switchToMobile(Context context) {
        Toast.makeText(context, "切换到移动网络", Toast.LENGTH_SHORT).show();
        // 获取ConnectivityManager
        ConnectivityManager sConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 创建 network request，网络类型是蜂窝数据
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            // 请求网络
            sConnectivityManager.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {

                //网络连接成功
                @Override
                public void onAvailable(Network network) {
                    Log.d("NetUtils", "网络连接成功");
                    Toast.makeText(context, "NetUtils, 网络连接成功, isMobileConnected = " + isMobileConnected(context), Toast.LENGTH_SHORT).show();
                    super.onAvailable(network);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sConnectivityManager.bindProcessToNetwork(network);
                    }
                }

                //网络已断开连接
                @Override
                public void onLost(Network network) {
                    Log.d("NetUtils", "网络已断开连接");
                    Toast.makeText(context, "NetUtils, 网络已断开连接", Toast.LENGTH_SHORT).show();
                    super.onLost(network);
                }
                @Override
                public void onLosing(Network network, int maxMsToLive) {
                    Log.d("NetUtils", "网络正在断开连接");
                    Toast.makeText(context, "NetUtils, 网络正在断开连接", Toast.LENGTH_SHORT).show();
                    super.onLosing(network, maxMsToLive);
                }

                //无网络
                @Override
                public void onUnavailable() {
                    Log.d("NetUtils", "网络连接超时或者网络连接不可达");
                    Toast.makeText(context, "NetUtils, 网络连接超时或者网络连接不可达", Toast.LENGTH_SHORT).show();
                    super.onUnavailable();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        sConnectivityManager.bindProcessToNetwork(null);
                    }
                }

                //当网络状态修改（网络依然可用）时调用
                @Override
                public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    Log.d("NetUtils", "net status change! 网络连接改变");
                    Toast.makeText(context, "NetUtils, net status change! 网络连接改变", Toast.LENGTH_SHORT).show();
                    // 表明此网络连接成功验证
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            // 使用WI-FI
                            Log.d("NetUtils", "当前在使用WiFi上网");
                            Toast.makeText(context, "NetUtils, 当前在使用WiFi上网", Toast.LENGTH_SHORT).show();
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            // 使用数据网络
                            Log.d("NetUtils", "当前在使用数据网络上网");
                            Toast.makeText(context, "NetUtils, 当前在使用数据网络上网", Toast.LENGTH_SHORT).show();
                        } else{
                            Log.d("NetUtils", "当前在使用其他网络");
                            Toast.makeText(context, "NetUtils, 当前在使用其他网络", Toast.LENGTH_SHORT).show();
                            // 未知网络，包括蓝牙、VPN等
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            sConnectivityManager.bindProcessToNetwork(network);
                        }
                    }
                }

                //当网络连接属性发生变化时调用
                @Override
                public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties);
                }

                //当访问的网络被阻塞或者解除阻塞时调用onCapabilitiesChanged
                @Override
                public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
                    super.onBlockedStatusChanged(network, blocked);
                }
            });
        }
    }
}
