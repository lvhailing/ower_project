package com.jdhui.uitls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	// 将时间戳转为字符串
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}

	/**
	 * 将时间戳转换为long时间
	 * 
	 * @param user_time
	 * @return
	 */
	public static String getTimeLong(String user_time, String type) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date d = null;
		try {
			d = sdf.parse(user_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = d.getTime();
		String str = String.valueOf(l);
		re_time = str.substring(0, 10);
		return re_time;
	}

	/**
	 * yyyy-MM-dd hh-mm-ss格式分割为数组
	 * 
	 * @param data
	 * @return
	 */
	public static String[] splitData(String data) {
		String[] temp1 = data.split(" ");
		String str1 = temp1[0];
		String str2 = temp1[1];
		String[] date = str1.split("-");
		String[] time = str2.split(":");
		String[] result = new String[6];
		result[0] = date[0];
		result[1] = date[1];
		result[2] = date[2];
		result[3] = time[0];
		result[4] = time[1];
		result[5] = time[2];
		return result;
	}

	/**
	 * 组合yyyy-MM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String combinationDate(String[] date) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < date.length; i++) {
			if (i < 2) {
				sb.append(date[i]);
				sb.append("-");
			} else if (i == 2) {
				sb.append(date[i]);
				sb.append(" ");
			} else {
				sb.append(date[i]);
				sb.append(":");
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long[] getTwoTimeDif(String time1, String time2) {
		long[] timeDif = new long[] { 0, 0, 0, 0 };
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(time1);
			Date d2 = df.parse(time2);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别

			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
			long second = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
			timeDif[0] = days;
			timeDif[1] = hours;
			timeDif[2] = minutes;
			timeDif[3] = second;
		} catch (Exception e) {
		}
		return timeDif;
	}

	public static String getCurrentTime() {
		long time = System.currentTimeMillis();// long now =
												// android.os.SystemClock.uptimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		String t1 = format.format(d1);
		return t1;
	}
}
