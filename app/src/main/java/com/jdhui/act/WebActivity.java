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
import com.jdhui.uitls.SystemInfo;

public class WebActivity extends Activity implements View.OnClickListener {
    private WebView mWebview;
    private String type = null;
    private String url = null;
    public static final String WEBTYPE_BANNER = "banner";            //轮播图
    public static final String WEBTYPE_ADVERTIS_2 = "advertises"; // 首页活动
    public static final String WEBTYPE_NOTICE_DETAILS = "notice_details"; // 公告详情
    public static final String WEBTYPE_INSURANCE_DETAIL_DES = "insurance_detail_des"; // 保险详情图文详情
    public static final String WEBTYPE_PRODUCT_CALL = "annual_report"; // 年度报告
    public static final String WEBTYPE_NEWS_DETAILS = "news_details"; // 快讯详情
    public static final String WEBTYPE_MESSAGE_DETAILS = "message_details"; // 消息详情

    public static final String WEBTYPE_ABOUT_US = "about_us"; // 关于我们
    public static final String WEBTYPE_SERVERS = "servers"; // 服务条款
    public static final String WEBTYPE_AGREEMENTS = "agreements"; // 隐私协议
    public static final String WEBTYPE_VERSION_NUMBER = "version_number"; // 版本号

    public String title;
    private TextView tv_web_title;
    private ImageView id_img_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);
        type = getIntent().getStringExtra("type");
        url = getIntent().getStringExtra("url");
        initView();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initView() {

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

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


        if (type.equals(WEBTYPE_INSURANCE_DETAIL_DES)) { // 图文详情
            url = ApplicationConsts.URL_INSURANCE_PRODUCT_DETAIL_DES + getIntent().getExtras().getString("id");
            tv_web_title.setText(getIntent().getExtras().getString("title"));

        } else if (type.equals(WEBTYPE_PRODUCT_CALL)) { // 年度报告
            url = ApplicationConsts.URL_INSURANCE_PRODUCT_DETAIL_REPORT + getIntent().getExtras().getString("id");
            tv_web_title.setText(getIntent().getExtras().getString("title"));

        } else if (type.equals(WEBTYPE_NOTICE_DETAILS)) {// 公告详情
            url = ApplicationConsts.URL_NOTICE_DETAILS + getIntent().getStringExtra("id");
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_NEWS_DETAILS)) {// 快讯详情
            url = ApplicationConsts.URL_NEWS_DETAILS + getIntent().getStringExtra("id");
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_MESSAGE_DETAILS)) { // 消息详情
            url = ApplicationConsts.URL_MESSAGE_DETAILS + getIntent().getStringExtra("id");
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_ABOUT_US)) {// 关于我们
            url = ApplicationConsts.URL_ABOUT_US;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_SERVERS)) {// 服务条款
            url = ApplicationConsts.URL_SERVERS;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_AGREEMENTS)) {// 隐私协议
            url = ApplicationConsts.URL_SERVERS;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_BANNER)) {// 轮播图
//			url = ApplicationConsts.URL_SERVERS;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
        } else if (type.equals(WEBTYPE_VERSION_NUMBER)) {//版本号
            url = ApplicationConsts.URL_VERSION_NUMBER + SystemInfo.sVersionName;
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
            WebActivity.this.finish();
        }

        @JavascriptInterface
        public void login() {
            if (type.equals(WEBTYPE_ADVERTIS_2)) {
                Intent i_login = new Intent();
                i_login.setClass(WebActivity.this, LoginActivity.class);
                startActivity(i_login);
            }
            WebActivity.this.finish();
        }
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
        switch (view.getId()) {
            case R.id.id_img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.removeActivity(this);
    }
}
