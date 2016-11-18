package com.jdhui.act;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.jdhui.R;
import com.jdhui.JdehuiApplication;
import com.jdhui.JdehuiApplication.NetListener;
import com.jdhui.wheel.widget.CustomProgressDialog;

public class BaseActivity extends AbsBaseActivity implements NetListener {

//	private TitleBar title;
	// private MyProgressDialog progressDialog;
	private CustomProgressDialog dialog;
	public static ArrayList<String> arrayList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base);
		JdehuiApplication apl = (JdehuiApplication) getApplicationContext();
		apl.registReceiver();
	}

	public void baseSetContentView(int layoutResId) {
		LinearLayout llContent = (LinearLayout) findViewById(R.id.content);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// progressDialog = new MyProgressDialog(BaseActivity.this);
		// progressDialog.setMessage(getResources().getString(
		// R.string.order_load_toast));
		// progressDialog.setCanceledOnTouchOutside(false);
		dialog = new CustomProgressDialog(this, "", R.anim.frame_loading);
		View v = inflater.inflate(layoutResId, null);
		llContent.addView(v);
	}

	public void startLoading() {
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}
	}

	public void stopLoading() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		JdehuiApplication apl = (JdehuiApplication) getApplication();
		apl.addNetListener(this);
		onNetWorkChange(apl.netType);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JdehuiApplication apl = (JdehuiApplication) getApplication();
		apl.removeNetListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JdehuiApplication apl = (JdehuiApplication) getApplicationContext();
		apl.unRegisterNetListener();
	}

	@Override
	public void onNetWorkChange(String netType) {
		View netHint = findViewById(R.id.netfail_hint);
		netHint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		if (netHint != null) {
			boolean netFail = TextUtils.isEmpty(netType);
			netHint.setVisibility(netFail ? View.VISIBLE : View.GONE);
//			if(netFail){		//没网
//				
//			}else {				//有网
////				UserLoadout out = new UserLoadout(BaseActivity.this);
////				out.requestData();
////				UserLogin.getInstance().addObserver(BaseActivity.this);
//			}
		}
	}
}
