package com.jdhui;

import java.util.HashSet;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.jdhui.uitls.APNManager;
import com.jdhui.uitls.NetworkUtils;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.SystemInfo;

public class JdehuiApplication extends Application {
	private static JdehuiApplication instance;
	public static String mAppId;
	public static String mDownloadPath;

	public static JdehuiApplication getInstance() {
		return instance;
	}
	private static final String TAG = "Init";
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		NetworkUtils.setContext(this);
		// CrashHandler.getInstance().init(this);
		PreferenceUtil.initialize(this);
		SystemInfo.initialize(this);
		initNetReceiver();
		initImageLoader();
		APNManager.getInstance().checkNetworkType(this);

		mAppId = getString(R.string.app_id);
		mDownloadPath = "/" + mAppId + "/download";

		initCloudChannel(this);
	}

	/****
	 * 初始化imageloader
	 */
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new WeakMemoryCache())
				.writeDebugLogs() // Remove for release app
				.build();
		
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public interface NetListener {
		public void onNetWorkChange(String netType);
	}

	private BroadcastReceiver mReceiver;
	public String netType;
	HashSet<NetListener> mListeners = new HashSet<NetListener>();
	IntentFilter mFilter;

	/**
	 * 加入网络监听
	 * 
	 * @param l
	 * @return
	 */
	public boolean addNetListener(NetListener l) {
		boolean rst = false;
		if (l != null && mListeners != null) {
			rst = mListeners.add(l);
		}
		return rst;
	}

	/**
	 * 移除网络监听
	 * 
	 * @param l
	 * @return
	 */
	public boolean removeNetListener(NetListener l) {
		return mListeners.remove(l);
	}

	/**
	 * 初始化网络监听器
	 */
	private void initNetReceiver() {
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info = manager.getActiveNetworkInfo();
					if (info != null && info.isConnected()) {
						netType = info.getTypeName();
					} else {
						netType = "";
					}
					for (NetListener lis : mListeners) {
						if (lis != null) {
							lis.onNetWorkChange(netType);
						}
					}
				}
			}
		};
		mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	}

	/**
	 * 注册网络监听器
	 */
	public void registReceiver() {
		try {
			registerReceiver(mReceiver, mFilter);
		} catch (Exception e) {
		}
	}

	/**
	 * 注销网络监听器
	 */
	public void unRegisterNetListener() {
		if (mListeners != null) {
			mListeners.clear();
		}
		try {
			unregisterReceiver(mReceiver);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 初始化云推送通道
	 * @param applicationContext
	 */
	private void initCloudChannel(Context applicationContext) {
		PushServiceFactory.init(applicationContext);
		CloudPushService pushService = PushServiceFactory.getCloudPushService();
		pushService.register(applicationContext, new CommonCallback() {
			@Override
			public void onSuccess(String response) {

				Log.d(TAG, "init cloudchannel success===getDeviceId====="+PushServiceFactory.getCloudPushService().getDeviceId());
			}
			@Override
			public void onFailed(String errorCode, String errorMessage) {
				Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
			}
		});
	}

}
