package com.jdhui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ScrollView;

public class DetailScrollView extends ScrollView {
	private boolean canScroll;

	private GestureDetector mGestureDetector;
	OnTouchListener mGestureListener;

	public DetailScrollView(Context context) {
		super(context);
	}

	public DetailScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mGestureDetector = new GestureDetector(new YScrollDetector());
		// canScroll = true;
	}

	public DetailScrollView(Context context, AttributeSet attrs, int defStyle) {
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

	//
	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	//
	// if (ev.getAction() == MotionEvent.ACTION_UP)
	// canScroll = true;
	// return super.onInterceptTouchEvent(ev)
	// && mGestureDetector.onTouchEvent(ev);
	// }
	//
	// class YScrollDetector extends SimpleOnGestureListener {
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2,
	// float distanceX, float distanceY) {
	// if (canScroll)
	// if (Math.abs(distanceY) >= Math.abs(distanceX))
	// canScroll = true;
	// else
	// canScroll = false;
	// return canScroll;
	// }
	// }
}
