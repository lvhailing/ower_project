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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.net.UserLoadout;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

public class WebSurveyActivity extends Activity implements View.OnClickListener {

    private WebView webview;
    private String type = null;
    private String url = null;
    public static final String WEBTYPE_SURVEY = "survey"; // 问卷调查
    public static final String WEBTYPE_ASSESSMENT = "assessment"; // 问卷评估
    public static final String WEBTYPE_INVESTOR_COMMITMENT = "investor_commitment"; // 投资者承诺函

    public String title;
    public String btnInfo;
    private TextView tv_web_title;
    private ImageView iv_back;
    private Button btn_bottom;
    private String userId;
    private ActivityStack stack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_survey);
        type = getIntent().getStringExtra("type");
        url = getIntent().getStringExtra("url");
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

        webview = (WebView) findViewById(R.id.webview);
        tv_web_title = (TextView) findViewById(R.id.tv_web_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_bottom = (Button) findViewById(R.id.btn_web_bottom);

        iv_back.setOnClickListener(this);
        btn_bottom.setOnClickListener(this);

        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if (type.equals(WEBTYPE_SURVEY)) { // 问卷调查
            url = ApplicationConsts.URL_SURVEY + userId;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
            btn_bottom.setText(getIntent().getExtras().getString("btnInfo"));

        } else if (type.equals(WEBTYPE_ASSESSMENT)) { // 问卷评估
            url = ApplicationConsts.URL_ASSESSMENT + userId;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
            btn_bottom.setText(getIntent().getExtras().getString("btnInfo"));
            iv_back.setVisibility(View.GONE);

        } else if (type.equals(WEBTYPE_INVESTOR_COMMITMENT)) { //投资者承诺函
            url = ApplicationConsts.URL_INVESTOR_COMMITMENT + userId;
            tv_web_title.setText(getIntent().getExtras().getString("title"));
            btn_bottom.setText(getIntent().getExtras().getString("btnInfo"));

        }

        HtmlRequest.synCookies(this, url);

        webview.loadUrl(url);

    }

    public class MyJavaScriptinterface {
        @JavascriptInterface
        public void result() {
            /*if (type.equals(WEBTYPE_WITHDRAW)) {
                setResult(RESULT_OK);
			} */
            WebSurveyActivity.this.finish();
        }

        @JavascriptInterface
        public void login() {
            if (type.equals(WEBTYPE_SURVEY)) {
                Intent i_login = new Intent();
                i_login.setClass(WebSurveyActivity.this, LoginActivity.class);
                startActivity(i_login);
            }
            WebSurveyActivity.this.finish();
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
            if (type.equals(WEBTYPE_ASSESSMENT)) {
                return false;
            } else if (type.equals(WEBTYPE_SURVEY)) {
                UserLoadout out = new UserLoadout(WebSurveyActivity.this);
                out.requestData();
                finish();
                return true;
            } else {
                this.finish();
                return true;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (type.equals(WEBTYPE_SURVEY)) {
                    UserLoadout out = new UserLoadout(WebSurveyActivity.this);
                    out.requestData();
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.btn_web_bottom: // 开始答题
                if (type.equals(WEBTYPE_SURVEY)) { // 如果是“问卷调查”页，点过“开始答题”按钮后跳转到“填写问卷”页
                    Intent i_survey = new Intent(this, WebWrittenActivity.class);
                    i_survey.putExtra("type", WebWrittenActivity.WEBTYPE_WRITTEN);
                    i_survey.putExtra("title", "填写问卷");
                    startActivity(i_survey);
                } else if (type.equals(WEBTYPE_ASSESSMENT)) { // 如果是“问卷评估”，再判断账户资产是否大于300万
                    if (PreferenceUtil.getTotalAmount()) { // 账户资产大于300万，跳转“投资者承诺函”页
                        Intent i_commitment = new Intent(this, WebSurveyActivity.class);
                        i_commitment.putExtra("type", WebSurveyActivity.WEBTYPE_INVESTOR_COMMITMENT);
                        i_commitment.putExtra("title", "投资者承诺函");
                        i_commitment.putExtra("btnInfo", "我同意该承诺");
                        startActivity(i_commitment);
                    } else { // 账户资产小于300万，跳转“投资者判定”页
                        Intent i_judge = new Intent(this, WebInvestorJudgeActivity.class);
                        i_judge.putExtra("type", WebInvestorJudgeActivity.WEBTYPE_INVESTOR_JUDGE);
                        i_judge.putExtra("title", "投资者判定");
                        i_judge.putExtra("btnInfo", "400-80-88888");
                        startActivity(i_judge);
                    }
                } else if (type.equals(WEBTYPE_INVESTOR_COMMITMENT)) { // 投资者承诺函
                    try {
                        userId = DESUtil.decrypt(PreferenceUtil.getUserId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    requestData(userId, "acceptable");
                }

                break;
        }
    }

    /**
     * 我同意该承诺、跳过 接口
     *
     * @param userId
     * @param qualifiedInvestor
     */
    private void requestData(String userId, String qualifiedInvestor) {
        HtmlRequest.investorJudgeSave(WebSurveyActivity.this, userId, qualifiedInvestor, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                ResultCodeContentBean b = (ResultCodeContentBean) params.result;
                if (b == null) {
                    Toast.makeText(WebSurveyActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Boolean.parseBoolean(b.getFlag())) {
                    PreferenceUtil.setIsInvestor(true);
                    Toast.makeText(WebSurveyActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();

                    Intent i_nopwd = new Intent();
                    i_nopwd.putExtra("comeflag", 0);
                    i_nopwd.putExtra("title", R.string.title_gestureset);
                    i_nopwd.setClass(WebSurveyActivity.this, GestureEditActivity.class);
                    startActivity(i_nopwd);
                    stack.removeAllActivity();
                } else {
                    Toast.makeText(WebSurveyActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
