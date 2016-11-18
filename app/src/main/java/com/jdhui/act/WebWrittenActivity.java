package com.jdhui.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

public class WebWrittenActivity extends Activity implements View.OnClickListener{

	private WebView mWebview;
	private String type = null;
	private String url = null;
	public static final String WEBTYPE_WRITTEN = "written"; //填写问卷


	public String title;
	private TextView tv_web_title;
	private ImageView id_img_back;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_written);
		type = getIntent().getStringExtra("type");
		url=getIntent().getStringExtra("url");
		initView();
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	private void initView() {
		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);
		try {
			userId= DESUtil.decrypt(PreferenceUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mWebview = (WebView) findViewById(R.id.webview_web);
		tv_web_title = (TextView) findViewById(R.id.tv_web_title);
		id_img_back = (ImageView) findViewById(R.id.id_img_back);
		id_img_back.setOnClickListener(this);

		mWebview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		mWebview.getSettings().setBuiltInZoomControls(true);
		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		mWebview.getSettings().setUseWideViewPort(true);

		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.addJavascriptInterface(new MyJavaScriptinterface(), "click");


		if (type.equals(WEBTYPE_WRITTEN)) {
			url = ApplicationConsts.URL_WRITTEN
					+ userId;
			tv_web_title.setText(getIntent().getExtras().getString("title"));
		}

		HtmlRequest.synCookies(this, url);

		mWebview.loadUrl(url);

	}
	public class MyJavaScriptinterface {
		@JavascriptInterface
		public void result() {
			if (type.equals(WEBTYPE_WRITTEN)) {
				setResult(RESULT_OK);
			}
			WebWrittenActivity.this.finish();
		}
		@JavascriptInterface
		public void written(){
			if(type.equals(WEBTYPE_WRITTEN)){
				PreferenceUtil.setIsAnswer(true);
				Intent i_written = new Intent(WebWrittenActivity.this,WebSurveyActivity.class);
				i_written.putExtra("type",WebSurveyActivity.WEBTYPE_ASSESSMENT);
				i_written.putExtra("title","问卷评估");
				i_written.putExtra("btnInfo","下一步");
				startActivity(i_written);
			}
			WebWrittenActivity.this.finish();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		} else {

			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.id_img_back:
				finish();
				break;
		}
	}
}
