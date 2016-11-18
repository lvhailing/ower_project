package com.jdhui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class DetailWebView extends WebView {

	public static final int CURRENT_PAGE = 1;
	public static final int GO_UP = 4;

	public static int TOP = 0;
	int num = 0;
	public static int FLAG = CURRENT_PAGE;

	// ScrollInterface web;

	public DetailWebView(Context context) {
		super(context);
		this.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
	}

	public DetailWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DetailWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean canScrollVertically(int direction) {
		final int offset = computeVerticalScrollOffset();
		final int range = computeVerticalScrollRange()
				- computeVerticalScrollExtent();
		if (range == 0)
			return false;
		if (direction < 0) {
			return offset > 0;
		} else {
			return offset < range - 1;
		}
	}

	float x_tmp1, y_tmp1, x_tmp2, y_tmp2;

	// @SuppressLint("ClickableViewAccessibility")
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// requestDisallowInterceptTouchEvent(true);
	// if (FLAG == GO_UP) {
	// return false;
	// }
	// // 获取当前坐标
	// float x = event.getX();
	// float y = event.getY();
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// x_tmp1 = x;
	// y_tmp1 = y;
	// break;
	// case MotionEvent.ACTION_UP:
	// x_tmp2 = x;
	// y_tmp2 = y;
	// if (y_tmp2 - y_tmp1 > 50) {
	// if (TOP == 0) {
	// num++;
	// if (num == 1) {
	// FLAG = GO_UP;
	// num = 0;
	// return false;
	// }
	// // if (FLAG == CURRENT_PAGE) {
	// // return super.onTouchEvent(event);
	// // } else if (FLAG == GO_UP) {
	// // return false;
	// // }
	// }
	// }
	// break;
	// case MotionEvent.ACTION_MOVE:
	//
	// break;
	// default:
	// break;
	// }
	// return super.onTouchEvent(event);
	//
	// }
	//
	// @Override
	// protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	// super.onScrollChanged(l, t, oldl, oldt);
	// TOP = t;
	// web.onSChanged(l, t, oldl, oldt);
	// }
	//
	// public void setOnCustomScroolChangeListener(ScrollInterface t) {
	// this.web = t;
	// }
	//
	// /**
	// * 定义滑动接口
	// *
	// * @param t
	// */
	// public interface ScrollInterface {
	// public void onSChanged(int l, int t, int oldl, int oldt);
	// }

}
