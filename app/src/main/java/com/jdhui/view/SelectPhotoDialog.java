package com.jdhui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.jdhui.R;

import java.util.ArrayList;

/**
 * 选择头像
 */
public class SelectPhotoDialog extends Dialog implements
		DialogInterface.OnCancelListener, DialogInterface.OnDismissListener{

	private Context mContext;
	private LayoutInflater inflater;
	private LayoutParams lp;
	private int percentageH = 4;
	private int percentageW = 8;
	private TextView txtAblum = null;
	private TextView txtCamera = null;
	private TextView txtCancel = null;

	ArrayList<OnCancelListener> m_arrCancelListeners = new ArrayList<OnCancelListener>();
	ArrayList<OnDismissListener> m_arrDismissListeners = new ArrayList<OnDismissListener>();
	private OnSelectPhotoChanged onChanged = null;

	public SelectPhotoDialog(Context context, OnSelectPhotoChanged onChanged) {
		super(context, R.style.Dialog);
		this.mContext = context;
		this.onChanged = onChanged;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mView = inflater.inflate(R.layout.dialog_select_photo, null);
		setContentView(mView);
		// 设置window属性
		lp = getWindow().getAttributes();
		lp.gravity = Gravity.BOTTOM;
		lp.dimAmount = 0.6f; // 去背景遮盖
		lp.alpha = 1.0f;
		int[] wh = initWithScreenWidthAndHeight(mContext);
		lp.width = wh[0] - wh[0] / percentageW;
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(lp);
		setCanceledOnTouchOutside(false);
		setOnDismissListener(this);
		setOnCancelListener(this);
		initView(mView);

	}

	private void initView(View mView) {
		txtAblum = (TextView) mView
				.findViewById(R.id.dialog_tv_album);
		txtCamera = (TextView) mView
				.findViewById(R.id.dialog_tv_camera);
		txtCancel = (TextView) mView
				.findViewById(R.id.dialog_tv_cancel);
		txtAblum.setOnClickListener(albumListener);
		txtCamera.setOnClickListener(cameraListener);
		txtCancel.setOnClickListener(cancelListener);
	}

	private View.OnClickListener albumListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onChanged.onAlbum();
			onDismiss();
		}

	};
	private View.OnClickListener cameraListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onChanged.onCamera();
			onDismiss();
		}

	};

	private View.OnClickListener cancelListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			onDismiss();
		}
	};

	private void ondismiss() {

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (m_arrDismissListeners != null) {
			for (int x = 0; x < m_arrDismissListeners.size(); x++)
				m_arrDismissListeners.get(x).onDismiss(dialog);
		}
		ondismiss();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (m_arrCancelListeners != null) {
			for (int x = 0; x < m_arrDismissListeners.size(); x++)
				m_arrCancelListeners.get(x).onCancel(dialog);
		}
	}

	public void addListeners(OnCancelListener c, OnDismissListener d) {
		m_arrDismissListeners.add(d);
		m_arrCancelListeners.add(c);
	}

	public void removeListeners(OnCancelListener c, OnDismissListener d) {
		m_arrDismissListeners.remove(d);
		m_arrCancelListeners.remove(c);
	}

	private void onDismiss() {
		if (this.isShowing()) {
			this.dismiss();
		}

	}

	/**
	 * 获取当前window width,height
	 * 
	 * @param context
	 * @return
	 */
	private static int[] initWithScreenWidthAndHeight(Context context) {
		int[] wh = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		wh[0] = dm.widthPixels;
		wh[1] = dm.heightPixels;
		return wh;
	}

	public interface OnSelectPhotoChanged {
		public void onAlbum();
		public void onCamera();
		public void onCancel();

	}

}