package com.jdhui.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultUserLoginContentBean;
import com.jdhui.net.UserLogin;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.view.CallServiceDialog;
import com.jdhui.view.CallServiceDialog.OnCallServiceChanged;

import java.util.Observable;
import java.util.Observer;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener, Observer {
    private EditText loginUerName; // 用户名称
    private EditText loginPassword; // 登录密码
    private Button btnLogin; // 登录
    private Button btnLoginForgetPassword; // 忘记密码
    private TextView customerPhone; // 客服电话
    private Resources mResource;
    private int requestCode = 5;
    private ResultUserLoginContentBean bean;
    private String tomain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initListener();
        initView();
    }

    private void initListener() {
        //注册观察者
        UserLogin.getInstance().addObserver(this);
        //注册广播
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("mybroadcast");
//        registerReceiver(broadcastReceiver, filter);
    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.removeAllActivity();
        addMe();

        PreferenceUtil.setCookie("");
        baseSetContentView(R.layout.activity_login);
        tomain = getIntent().getStringExtra("tomain");
        if (tomain.equals("23")) {
            Toast.makeText(this, "账户信息已过期，请重新登录", Toast.LENGTH_LONG).show();
            tomain = "6";
        }
        loginUerName = (EditText) findViewById(R.id.et_login_username);
        loginPassword = (EditText) findViewById(R.id.et_login_password);

        customerPhone = (TextView) findViewById(R.id.tv_login_customer_phone);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLoginForgetPassword = (Button) findViewById(R.id.btn_login_forget_password);

        String userName = "";
        try {
            userName = DESUtil.decrypt(PreferenceUtil.getAutoLoginAccount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(userName)) {
            loginUerName.setText(userName);
            loginPassword.setFocusable(true);
            loginPassword.requestFocus();
            loginPassword.setFocusableInTouchMode(true);
        }

        customerPhone.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginForgetPassword.setOnClickListener(this);
        btnLogin.setClickable(false);

        loginUerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    if (!TextUtils.isEmpty(loginPassword.getText().toString())) {
                        btnLogin.setClickable(true);
                        btnLogin.setBackgroundResource(R.drawable.shape_button_red);
                    } else {
                        btnLogin.setClickable(false);
                        btnLogin.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                } else {
                    btnLogin.setClickable(false);
                    btnLogin.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    if (!TextUtils.isEmpty(loginUerName.getText().toString())) {
                        btnLogin.setClickable(true);
                        btnLogin.setBackgroundResource(R.drawable.shape_button_red);
                    } else {
                        btnLogin.setClickable(false);
                        btnLogin.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                } else {
                    btnLogin.setClickable(false);
                    btnLogin.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    //获取焦点改变背景
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean isOK = data.getExtras().getBoolean("isSignupOK");
                    if (isOK) {
                        String name = data.getExtras().getString("username");
                        loginUerName.setText(name);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UserLogin.getInstance().addObserver(this);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("mybroadcast");
//        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
        UserLogin.getInstance().deleteObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_customer_phone: // 客服热线
                CallServiceDialog dialog = new CallServiceDialog(this, new OnCallServiceChanged() {
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
                }, "客服热线: \n " + getString(R.string.tellphone_num_format));
                dialog.show();
                break;
            case R.id.btn_login: // 登录
                String userName = loginUerName.getText().toString();
                String password = loginPassword.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginActivity.this, "请输入用户密码", Toast.LENGTH_SHORT).show();
                    } else {
                        judgeUser(userName, password);
                    }
                }
                break;
            case R.id.btn_login_forget_password: // 忘记密码
                Intent intent = new Intent(this, FindPassWordActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void judgeUser(String user, String pass) {
        UserLogin.getInstance().userlogining(LoginActivity.this, user, pass, "");
    }

//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            finish();
//        }
//    };

    @Override
    public void update(Observable observable, Object data) {
        bean = (ResultUserLoginContentBean) data;
        if (bean == null || !Boolean.parseBoolean(bean.getFlag())) {
//            Toast.makeText(this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置已登录标记
        PreferenceUtil.setLogin(true);

        //先判断是否答题
        if (!PreferenceUtil.getIsAnswer()) {
            //如果未答题，去答题
            Intent i_survey = new Intent(LoginActivity.this, WebSurveyActivity.class);
            i_survey.putExtra("type", WebSurveyActivity.WEBTYPE_SURVEY);
            i_survey.putExtra("title", "问卷调查");
            i_survey.putExtra("btnInfo", "开始答题");
            startActivity(i_survey);
            finish();
            return;
        }

        //是否做过合格投资者判定
        if (!PreferenceUtil.getIsInvestor()) {
            //如果未判定
            if (PreferenceUtil.getTotalAmount()) {  //判断账户资产是否大于300万，是
                Intent i_commitment = new Intent(this, WebSurveyActivity.class);
                i_commitment.putExtra("type", WebSurveyActivity.WEBTYPE_INVESTOR_COMMITMENT);
                i_commitment.putExtra("title", "投资者承诺函");
                i_commitment.putExtra("btnInfo", "我同意该承诺");
                startActivity(i_commitment);
            } else {    //判断账户资产是否大于300万， 否
                Intent i_judge = new Intent(this, WebInvestorJudgeActivity.class);
                i_judge.putExtra("type", WebInvestorJudgeActivity.WEBTYPE_INVESTOR_JUDGE);
                i_judge.putExtra("title", "投资者判定");
                i_judge.putExtra("btnInfo", "400-80-88888");
                startActivity(i_judge);
            }
            finish();
            return;
        }

        //非第一次登录 比如退出登录来的
        if (!PreferenceUtil.isFirstLogin()) {
            // 如果开启并且手势密码不为空，则去验证手势密码
            if (PreferenceUtil.isGestureOpen()) {
                if (!TextUtils.isEmpty(PreferenceUtil.getGesturePwd())) {
                    Intent intent = new Intent(LoginActivity.this, GestureVerifyActivity.class);
                    intent.putExtra("from", ApplicationConsts.ACTIVITY_SPLASH);
                    intent.putExtra("title", "手势密码登录");
                    intent.putExtra("message", "请画出手势密码解锁");
                    startActivity(intent);
                    finish();
                } else {
                    //手势密码为空，没设置过 去设置界面
                    Intent i = new Intent(LoginActivity.this, GestureEditActivity.class);
                    i.putExtra("back_from_change_gesture", "back_from_change_gesture");
                    i.putExtra("comeflag", 1);
                    if (tomain != null) {
                        i.putExtra("tomain", tomain);
                    }
                    i.putExtra("title", R.string.title_gestureset);
                    startActivity(i);
                    finish();
                }
            } else { // 手势密码为空，则不用验证，直接去主页
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return;
        }

        // 是第一次登录
        //1 设置标记，非第一次登录
        PreferenceUtil.setFirstLogin(false);
        //2 再判断是否开启了手势密码
        if (!PreferenceUtil.isGestureOpen()) {
            // 如果没开启，则直接去主界面 不用验证
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //3 再判断是否设置过手势密码
        if (TextUtils.isEmpty(PreferenceUtil.getGesturePwd())) {
            //手势密码为空，没设置过 去设置界面
            Intent i = new Intent(LoginActivity.this, GestureEditActivity.class);
            i.putExtra("back_from_change_gesture", "back_from_change_gesture");
            i.putExtra("comeflag", 1);
            if (tomain != null) {
                i.putExtra("tomain", tomain);
            }
            i.putExtra("title", R.string.title_gestureset);
            startActivity(i);
            finish();
            return;
        }
        //设置过，去验证界面
        Intent intent = new Intent(LoginActivity.this, GestureVerifyActivity.class);
        intent.putExtra("from", ApplicationConsts.ACTIVITY_SPLASH);
        intent.putExtra("title", "手势密码登录");
        intent.putExtra("message", "请画出手势密码解锁");
        startActivity(intent);
        finish();
    }

    //为了防止父类把自己入栈
    @Override
    public void addMe() {

    }

}
