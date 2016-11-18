package com.jdhui.uitls;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;

public class FileUtil {
	
	public static final String ASSETS_FILE = "future";
	public static final String EXTERNAL_STORAGE_PATH_BASE = Environment
			.getExternalStorageDirectory().getPath() + "/FUTURE/";
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
	
	public static String getFromAssets(Context c ,String fileName) {
		String Result = "";
		try {
			InputStreamReader inputReader = new InputStreamReader(
					c.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}
	
}
