package com.jdhui.act.ac;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 预约公务机包机页面
 */
public class WebAirPlanBookingActivity extends Activity implements View.OnClickListener {
    private WebView mWebview;
    private String url = null;
    public String title;
    private TextView tv_web_title;
    private ImageView id_img_back;
    private String userId;
    private ActivityStack stack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_written);

        initView();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initView() {
        stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mWebview = (WebView) findViewById(R.id.webview);
        tv_web_title = (TextView) findViewById(R.id.tv_web_title);
        id_img_back = (ImageView) findViewById(R.id.iv_back);
        id_img_back.setOnClickListener(this);

        mWebview.getSettings().setSupportZoom(true);
        mWebview.getSettings().setBuiltInZoomControls(true);    // 设置出现缩放工具
        mWebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.addJavascriptInterface(new MyJavaScriptinterface(), "click");

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        url = ApplicationConsts.URL_AIR_PLANE_BOOKING + userId;
        tv_web_title.setText("公务机包机");

        HtmlRequest.synCookies(this, url);

        mWebview.loadUrl(url);

    }

    public class MyJavaScriptinterface {
        @JavascriptInterface
        public void written() {
            setResult(RESULT_OK);
            WebAirPlanBookingActivity.this.finish();
            Toast.makeText(WebAirPlanBookingActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
