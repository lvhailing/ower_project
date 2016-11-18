package com.jdhui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.jdhui.uitls.FileUtil;
import com.jdhui.uitls.SystemInfo;

public class CrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static final boolean DEBUG = true;
	private Context context;
	private static CrashHandler crashHandler = new CrashHandler();
	private UncaughtExceptionHandler defaultExceptionHandler;
	private static final int CRASH_FILE_SIZE_LIMIT = 2 * 1024 * 1024;
	private static final String CRASH_FILE_DIR_PATH = FileUtil.EXTERNAL_STORAGE_PATH_BASE
			+ "log/";
	public static final String CRASH_FILE_PATH = CRASH_FILE_DIR_PATH
			+ "crashes.txt";

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return crashHandler;
	}

	/**
	 * 初始化方法
	 * 
	 * @param context
	 *            上下文对象
	 */
	public void init(Context context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 异常处理方法
	 * 
	 * @Params Thread对象
	 * @param Throwable对象
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(thread, ex) && defaultExceptionHandler != null) {
			defaultExceptionHandler.uncaughtException(thread, ex);
			// 启动service
			// Intent intent = new Intent(context, MessageService.class);
			// intent.setAction(MessageService.ACTION_LOGIN);
			// context.startService(intent);
		}
	}

	// 程序异常处理方法
	private boolean handleException(Thread thread, Throwable ex) {
		try {
			return saveCrashInfoToFile(ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		defaultExceptionHandler.uncaughtException(thread, ex);
		return true;
	}

	/**
	 * Save the error info to local file.
	 */
	@SuppressLint("NewApi")
	private boolean saveCrashInfoToFile(Throwable ex) {
		if (false == Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (DEBUG) {
				Log.d(TAG,
						"failed to save crash info because external sdcard is not mounted");
			}
			return false;
		}

		File logDir = new File(CRASH_FILE_DIR_PATH);
		logDir.mkdirs();

		if (logDir.exists() && false == logDir.isDirectory()) {
			if (DEBUG) {
				Log.d(TAG,
						"failed to save crash info because of failing create directory:"
								+ logDir.getAbsolutePath());
			}
			return false;
		}
		Properties deviceInfo = new Properties();
		deviceInfo.put("deviceid", SystemInfo.sDeviceId + "");
		deviceInfo.put("networktype", String.valueOf(SystemInfo.sNetworkType));
		deviceInfo.put("buildnumber", SystemInfo.mBuildEnvNumber + "");
		deviceInfo.put("verName", SystemInfo.sVersionName + "");
		deviceInfo.put("verCode", String.valueOf(SystemInfo.sVersionCode));
		deviceInfo.put("channel", SystemInfo.sChannel);
		for (Field field : Build.class.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				deviceInfo.put(field.getName(), null2Empty(field.get(null)));
			} catch (Exception e) {
			}
		}

		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		deviceInfo.put("stackTrace", info.toString());

		try {
			File file = new File(CRASH_FILE_PATH);
			// truncate the file if it is larger than 2M
			FileOutputStream fos = new FileOutputStream(file, file.exists()
					&& file.length() < CRASH_FILE_SIZE_LIMIT);
			deviceInfo.store(new OutputStreamWriter(fos, "UTF-8"), "");

			// Add crash separator
			fos.write("\n\n********** Crash Separator ******\n\n".getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			if (DEBUG) {
				Log.e(TAG, "Error occured while writing crash file...", e);
			}
		}

		return false;
	}

	private String null2Empty(Object str) {
		return (str == null) ? "" : str + "";
	}
}
