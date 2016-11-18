package com.jdhui.view;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jdhui.R;

/**
 * @FileName: ItemWindow.java
 * @Author
 * @Date 2014-3-31
 */
public class StickItemWindow implements OnClickListener {
	private static final String TAG = "ItemWindow";
	private View mAnchor = null, rootView = null;

	private PopupWindow mPopWindow = null;
	private int location[] = new int[2];
	private boolean below = false;
	private onItemSelectListener mListner = null;
	private View mContentView = null;
	private int margin = 0;

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public boolean isBelow() {
		return below;
	}

	public void setBelow(boolean isBelow) {
		this.below = isBelow;
	}

	public interface onItemSelectListener {
		public void onItemSelect(int index);
	}

	public void setOnItemSelectListner(onItemSelectListener listner) {
		this.mListner = listner;
		final ViewGroup vp = (ViewGroup) mContentView;
		for (int i = 0; i < vp.getChildCount(); i++) {
			vp.getChildAt(i).setOnClickListener(this);
		}
	}

	public StickItemWindow(View contentView, View anchor) {
		this.mAnchor = anchor;
		this.rootView = contentView.getRootView();
		anchor.getLocationInWindow(location);

		mPopWindow = new PopupWindow(contentView, anchor.getWidth(),
				anchor.getHeight(), true);
	}

	/**
	 * 设置popupwindow显示在anchor下面
	 */
	public void showAsDropDown(View anchor) {
		mPopWindow.showAsDropDown(anchor);

	}

	/**
	 * 设置popupwindow显示在anchor下面有偏移
	 */
	public void showAsDropDownOff(View anchor, int xoff) {
		mPopWindow.showAsDropDown(anchor, xoff, 0);

	}

	public View getAnchor() {
		return mAnchor;
	}

	public View getView() {
		return mContentView;
	}

	public void setAnchor(View anchor) {
		this.mAnchor = anchor;
		anchor.getLocationInWindow(location);
		mPopWindow.setHeight(anchor.getHeight());
		mPopWindow.setWidth(anchor.getWidth());
	}

	public void setContentView(View v) {
		mPopWindow.setBackgroundDrawable(new ColorDrawable());
		mPopWindow.setContentView(v);
		mContentView = v;
	}

	public void setHeight(int height) {
		mPopWindow.setHeight(height);
	}

	public void setWidth(int width) {
		mPopWindow.setWidth(width);
	}

	public void dismiss() {
		if (mPopWindow.isShowing())
			mPopWindow.dismiss();
	}

	public void show() {
		if (!mPopWindow.isShowing()) {
			int x = location[0] + mAnchor.getWidth() - mPopWindow.getWidth()
					- marginH;
			int y = 0;
			if (below) {
				y = location[1] + mAnchor.getHeight() + margin;
			} else {
				y = location[1] - mPopWindow.getHeight() - margin;
			}
			// mPopWindow.showAsDropDown(mAnchor);
			// mPopWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, x
			// - mAnchor.getWidth(), y);
			mPopWindow.setAnimationStyle(R.style.TitleBar_Menu_New);
			mPopWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, x,
					y);
		}
	}

	/**
	 * 显示Title处的下拉菜单
	 */
	public void showTitleMenu() {
		if (!mPopWindow.isShowing()) {
			int x = location[0] + mAnchor.getWidth() - mPopWindow.getWidth()
					- marginH;
			int y = 0;
			if (below) {
				y = location[1] + mAnchor.getHeight() + margin;
			} else {
				y = location[1] - mPopWindow.getHeight() - margin;
			}
			// mPopWindow.showAsDropDown(mAnchor);
			// mPopWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, x
			// - mAnchor.getWidth(), y);
			mPopWindow.setAnimationStyle(R.style.TitleBar_Menu_New);
			mPopWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, x,
					y);
		}
	}

	@Override
	public void onClick(View v) {
		if (mListner != null) {
			final ViewGroup vp = (ViewGroup) mContentView;
			for (int i = 0; i < vp.getChildCount(); i++) {
				if (v == vp.getChildAt(i)) {
					mListner.onItemSelect(i);
					break;
				}
			}
		}
	}

	int marginH;

	public void setMarginH(int m) {
		// TODO Auto-generated method stub
		marginH = m;
	}
}