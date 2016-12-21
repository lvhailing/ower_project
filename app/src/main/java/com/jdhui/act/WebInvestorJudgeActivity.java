package com.jdhui.act;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.view.CallServiceDialog;

public class WebInvestorJudgeActivity extends Activity implements View.OnClickListener{

	private WebView mWebview;
	private String type = null;
	private String url = null;
	private TextView tv_skip;
	private LinearLayout btn_bottom;
	public static final String WEBTYPE_INVESTOR_JUDGE = "investor_judge"; //投资者判定


	public String title;
	private TextView tv_web_title;
	private ImageView id_img_back;
	private String userId;
	private ActivityStack stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_investor_judge);
		type = getIntent().getStringExtra("type");
		url=getIntent().getStringExtra("url");
		initView();
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	private void initView() {
		stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		try {
			userId= DESUtil.decrypt(PreferenceUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mWebview = (WebView) findViewById(R.id.webview_web);
		tv_web_title = (TextView) findViewById(R.id.tv_web_title);
		id_img_back = (ImageView) findViewById(R.id.id_img_back);
		tv_skip = (TextView) findViewById(R.id.tv_web_skip);
		btn_bottom = (LinearLayout) findViewById(R.id.btn_web_bottom);
		id_img_back.setOnClickListener(this);
		btn_bottom.setOnClickListener(this);
		tv_skip.setOnClickListener(this);

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


		if (type.equals(WEBTYPE_INVESTOR_JUDGE)) {
			url = ApplicationConsts.URL_INVESTOR_JUDGE
					+ userId;
			tv_web_title.setText(getIntent().getExtras().getString("title"));

		}

		HtmlRequest.synCookies(this, url);

		mWebview.loadUrl(url);

	}
	public class MyJavaScriptinterface {
		@JavascriptInterface
		public void result() {
			/*if (type.equals(WEBTYPE_WITHDRAW)) {
				setResult(RESULT_OK);
			} */
			WebInvestorJudgeActivity.this.finish();
		}
		@JavascriptInterface
		public void login(){
			if(type.equals(WEBTYPE_INVESTOR_JUDGE)){
				Intent i_login = new Intent();
				i_login.setClass(WebInvestorJudgeActivity.this, LoginActivity.class);
				startActivity(i_login);
			}
			WebInvestorJudgeActivity.this.finish();
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
		stack.removeActivity(this);
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
			case R.id.tv_web_skip:
				try {
					userId = DESUtil.decrypt(PreferenceUtil.getUserId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				requestData(userId, "unacceptable");

				break;
			case R.id.btn_web_bottom:
				CallServiceDialog dialog = new CallServiceDialog(this,
						new CallServiceDialog.OnCallServiceChanged() {

							@Override
							public void onConfim() {
								Intent intent = new Intent(Intent.ACTION_DIAL);
								Uri data = Uri.parse("tel:" + getString(R.string.tellphone_num));
								intent.setData(data);
								startActivity(intent);
							}

							@Override
							public void onCancel() {

							}
						}, "客服热线: \n "+getString(R.string.tellphone_num_format));
				dialog.show();
				break;

		}
	}
	private void requestData(String userId,String qualifiedInvestor) {
		HtmlRequest.investorJudgeSave(WebInvestorJudgeActivity.this, userId, qualifiedInvestor, new BaseRequester.OnRequestListener() {

			@Override
			public void onRequestFinished(BaseParams params) {
				ResultCodeContentBean b = (ResultCodeContentBean) params.result;
				if (b != null) {
					if (Boolean.parseBoolean(b.getFlag())) {
						PreferenceUtil.setIsInvestor(true);
						Toast.makeText(WebInvestorJudgeActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();
						Intent i_nopwd = new Intent();
						i_nopwd.putExtra("comeflag", 0);
						i_nopwd.putExtra("title", R.string.title_gestureset);
						i_nopwd.setClass(WebInvestorJudgeActivity.this, GestureEditActivity.class);
						startActivity(i_nopwd);
						stack.removeAllActivity();
					} else {
						Toast.makeText(WebInvestorJudgeActivity.this,
								b.getMsg(), Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(WebInvestorJudgeActivity.this,
							"加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
