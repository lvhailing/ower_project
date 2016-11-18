package com.jdhui.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import com.jdhui.R;

public class SystemInfo {
	// 请不要手动修改DEBUG值
	public final static boolean DEBUG = true;
	public static final String TAG = "SystemInfo";
	public static final String mBuildEnvNumber = "000";
	/**
	 * 自定义设备id iPhone设备号为I1 iPad设备号为I2 Android手机设备号为A1 Android Pad设备号为A2
	 */
	public static final String sF2CDeviceId = "A1";
	public static int sVersionCode = 0;
	public static String sVersionName;
	public static int sNetworkType = -1;
	public static String sImei = null;
	// 设备id用imei和mac地址合成
	public static String sDeviceId = null;
	public static final String BOOT_CHANNEL_ID = "boot_channel_id";
	public final static String DEFAULT_IMEI = "f2c_imei";
	public final static String DEFAULT_MAC = "f2c_mac";
	public final static String DEFAULT_CHANNEL_ID = "C010000";
	public static int sWidth;
	public static int sHeight;
	public static int sCardImgHeight;
	public static String sChannel;// 渠道号

	// /** IM是否登出*/
	// public static boolean isIMLogout = false;

	public static void initialize(Context context) {
		if (context != null) {
			PackageManager pm = context.getPackageManager();// context为当前Activity上下文
			PackageInfo pi;
			try {
				pi = pm.getPackageInfo(context.getPackageName(), 0);
				sVersionName = pi.versionName;
				sVersionCode = pi.versionCode;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		try {
			getDeviceInfo(context);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		getDisplayWH(context);
	}

	private static void getDeviceInfo(Context context) {
		String key = "devid";
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"f2cdevice", Context.MODE_PRIVATE);
		String devid = sharedPreferences.getString(key, null);
		if (devid == null) {
			sDeviceId = StringUtil.MD5Encode(getImei(context)
					+ getLocalMacAddress(context));
			Editor editor = sharedPreferences.edit();
			editor.putString(key, sDeviceId);
			editor.commit();
		} else {
			sDeviceId = devid;
		}
		// sChannel = AppTool.getCurrentSDK() == AppTool.BAIDU ?
		// redBAIDUChannelId(context) : redUMENGChannelId(context);
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		sNetworkType = tm.getNetworkType();
		if (DEBUG) {
			Log.w("SystemInfo", "deviceid=" + sDeviceId + " sNetworkType="
					+ sNetworkType + " sChannel=" + sChannel);
		}
		// channel info
		// String s = null;//sharedPreferences.getString(BOOT_CHANNEL_ID, null);
		// if (s == null) {
		// InputStream is = null;
		// try {
		// is = context.getAssets().open("cid.dat");
		// BufferedReader reader = new BufferedReader(new InputStreamReader(is,
		// "utf-8"));
		// s = reader.readLine().trim();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// is.close();
		// } catch (Exception e) {
		// }
		// }
		// if (TextUtils.isEmpty(s) == true) {
		// sChannel = DEFAULT_CHANNEL_ID;
		// } else {
		// sChannel = s;
		// }
		// Editor editor = sharedPreferences.edit();
		// editor.putString(BOOT_CHANNEL_ID, sChannel);
		// editor.commit();
		// } else {
		// sChannel = s;
		// }

	}

	public static String redUMENGChannelId(Context paramContext) {

		String localObject1 = "Unknown";
		try {
			PackageManager localPackageManager = paramContext
					.getPackageManager();
			ApplicationInfo localApplicationInfo = localPackageManager
					.getApplicationInfo(paramContext.getPackageName(),
							PackageManager.GET_META_DATA);

			if (localApplicationInfo != null
					&& (localApplicationInfo.metaData != null)) {
				Object localObject2 = localApplicationInfo.metaData
						.get("UMENG_CHANNEL");
				if (localObject2 != null) {
					String str = localObject2.toString();
					if (str != null) {
						localObject1 = str;
					} else {
						Log.e(TAG,
								"Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
					}
				}
			}
		} catch (Exception localException) {
			Log.e(TAG,
					"Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");

		}
		return localObject1;

	}

	public static String redBAIDUChannelId(Context paramContext) {

		String localObject1 = "Unknown";
		try {
			PackageManager localPackageManager = paramContext
					.getPackageManager();
			ApplicationInfo localApplicationInfo = localPackageManager
					.getApplicationInfo(paramContext.getPackageName(),
							PackageManager.GET_META_DATA);

			if (localApplicationInfo != null
					&& (localApplicationInfo.metaData != null)) {
				Object localObject2 = localApplicationInfo.metaData
						.get("BaiduMobAd_CHANNEL");
				if (localObject2 != null) {
					String str = localObject2.toString();
					if (str != null) {
						localObject1 = str;
					} else {
						Log.e(TAG,
								"Could not read BaiduMobAd_CHANNEL meta-data from AndroidManifest.xml.");
					}
				}
			}
		} catch (Exception localException) {
			Log.e(TAG,
					"Could not read BaiduMobAd_CHANNEL meta-data from AndroidManifest.xml.");

		}
		return localObject1;

	}

	public static String getImei(Context context) {
		String imei = null;
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		} catch (Exception ex) {

		}
		if (imei == null) {
			return DEFAULT_IMEI;
		}
		return imei;
	}

	public static String getLocalMacAddress(Context context) {
		try {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			return info.getMacAddress();
		} catch (Exception ex) {
			return DEFAULT_MAC;
		}
	}

	public static void getDisplayWH(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		sWidth = wm.getDefaultDisplay().getWidth();// 屏幕宽度

		sHeight = wm.getDefaultDisplay().getHeight();// 屏幕高度
		float f = context.getResources().getDimension(R.dimen.home_card_left);
		sCardImgHeight = (int) ((sWidth - f * 2) / 2.17);
		if (DEBUG) {
			Log.w("SystemInfo", "sWidth=" + sWidth + " sHeight=" + sHeight
					+ ":sCardImgHeight=" + sCardImgHeight);
		}
	}

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}

}
