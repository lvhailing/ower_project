package com.jdhui.uitls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ViewUtils {

	/** 
     * 
     */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** 
     * 
     */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static void detachParent(View view) {
		if (view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
		}
	}

	public static Bitmap getDrawingCache(View view) {
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache();
		Bitmap emptybitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight(), Config.ARGB_8888);
		try {
			Canvas cv = new Canvas(emptybitmap);
			cv.drawBitmap(bitmap, 0, 0, null);
			cv.save(Canvas.ALL_SAVE_FLAG);
			cv.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(false);
		return emptybitmap;
	}

	public static ViewGroup getRootView(Activity context) {
		return (ViewGroup) ((ViewGroup) context
				.findViewById(android.R.id.content)).getChildAt(0);
	}

	public static void delEditTextChar(EditText editText) {
		KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
//		if (null == editText) {
//			return;
//		}
//		int position = editText.getSelectionStart();// 光标当前位置
//		if (position <= editText.getText().length() && position != 0) {
//			if (!deleteEditTextImgSpan(editText, position)) {
//				editText.getText().delete(position - 1, position);// 删除光标后的一位
//			}
//		}
	}

	public static boolean deleteSpanChar(EditText editText, int position) {
		if (deleteEditTextImgSpan(editText, position)) {
			return true;
		} else if (deleteEditTextForegColorSpan(editText, position)) {
			return true;
		}
		return false;
	}

	private static boolean deleteEditTextImgSpan(EditText editText, int position) {
		Spanned s = editText.getEditableText();
		ImageSpan[] imageSpan = s.getSpans(0, s.length(), ImageSpan.class);
		for (int i = 0; i < imageSpan.length; i++) {
			ImageSpan span = imageSpan[i];
			int start = s.getSpanStart(span);
			int end = s.getSpanEnd(span);
			if (end == position) {
				Editable et = editText.getText();
				et.delete(start, end);
				editText.invalidate();
				return true;
			}
		}
		return false;
	}

	public static boolean deleteEditTextForegColorSpan(EditText editText,
			int position) {
		Spanned s = editText.getEditableText();
		ForegroundColorSpan[] imageSpan = s.getSpans(0, s.length(),
				ForegroundColorSpan.class);
		for (int i = 0; i < imageSpan.length; i++) {
			ForegroundColorSpan span = imageSpan[i];
			int start = s.getSpanStart(span);
			int end = s.getSpanEnd(span);
			if (end == position) {
				Editable et = editText.getText();
				et.delete(start, end);
				editText.invalidate();
				return true;
			}
		}
		return false;
	}

	public static void buildTitle(View rootView) {

	}

	public static void vibrateLongPress(View view) {
		view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
	}

	/**
	 * PullToRefreshListView  上滑加载更多及下拉刷新
	 * @param listView
     */
	public static void slideAndDropDown(PullToRefreshListView listView) {
		// 下拉刷新
		listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
		listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
		listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");
		// 上拉加载更多，分页加载
		listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
		listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
		listView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
	}
}
