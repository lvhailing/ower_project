package com.jdhui.uitls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

public class NetworkUtils {
    private static ConnectivityManager connectivityManager;
    private static Context sContext;

    public static void setContext(Context context) {
        sContext = context;
    }

    public static boolean isNetworkAvailable() {
        if (sContext != null) {
            return isNetworkAvailable(sContext);
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (connectivityManager == null) {
        	//获取系统的连接服务 
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
     // 获取代表联网状态的NetWorkInfo对象  
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnected();
    }

    public static boolean isWifiEnable(Context context) {
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        switch (wifiMng.getWifiState()) {
        case WifiManager.WIFI_STATE_ENABLED:
        case WifiManager.WIFI_STATE_ENABLING:
            return true;
        }
        return false;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            WifiManager wifi = (WifiManager) context
                            .getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                                android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
