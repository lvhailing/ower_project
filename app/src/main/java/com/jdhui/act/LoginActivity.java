package com.jdhui.act;

import android.app.ActivityManager;
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
import android.view.KeyEvent;
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
    private EditText edtUsername, edtPassword;
    private Button btnLogin, btn_find;
    private TextView txtCall;
    private Resources mResource;
    private int requestCode = 5;
    private ResultUserLoginContentBean bean;
    private String tomain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.removeAllActivity();
        stack.addActivity(this);
        PreferenceUtil.setCookie("");
        baseSetContentView(R.layout.activity_login);
        tomain = getIntent().getStringExtra("tomain");
        if (tomain.equals("23")) {
            Toast.makeText(this, "账户信息已过期，请重新登录", Toast.LENGTH_LONG).show();
            tomain = "6";
        }
        edtUsername = (EditText) findViewById(R.id.id_login_edit_username);
        edtPassword = (EditText) findViewById(R.id.id_login_edit_password);

        txtCall = (TextView) findViewById(R.id.id_login_txt_call);
        btnLogin = (Button) findViewById(R.id.id_login_btn_login);
        btn_find = (Button) findViewById(R.id.id_login_btn_findpw);

        String userName = "";
        try {
            userName = DESUtil.decrypt(PreferenceUtil.getAutoLoginAccount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(userName)) {
            edtUsername.setText(userName);
            edtPassword.setFocusable(true);
            edtPassword.requestFocus();
            edtPassword.setFocusableInTouchMode(true);
        }


        txtCall.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btnLogin.setClickable(false);

        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    if (!TextUtils.isEmpty(edtPassword.getText().toString())) {
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

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence)) {
                    if (!TextUtils.isEmpty(edtUsername.getText().toString())) {
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

    private void initData() {
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
                        edtUsername.setText(name);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserLogin.getInstance().addObserver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("mybroadcast");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        UserLogin.getInstance().deleteObserver(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_login_txt_call: //客服热线
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
            case R.id.id_login_btn_login:
                String user = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(pass)) {
                        Toast.makeText(LoginActivity.this, "请输入用户密码", Toast.LENGTH_SHORT).show();
                    } else {
                        judgeUser(user, pass);
                    }
                }

                break;
            case R.id.id_login_btn_findpw:
                Intent i_findpw = new Intent(this, FindPassWordActivity.class);
                startActivity(i_findpw);
                break;
            default:
                break;
        }
    }

    private void judgeUser(String user, String pass) {
        UserLogin.getInstance().userlogining(LoginActivity.this, user, pass, "");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    public void update(Observable observable, Object data) {
        bean = (ResultUserLoginContentBean) data;
        if (bean == null || !Boolean.parseBoolean(bean.getFlag())) {
            Toast.makeText(this, "用户名或密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置已登录标记
        PreferenceUtil.setLogin(true);

        //非第一次登录 比如退出登录来的
        if (!PreferenceUtil.isFirstLogin()) {
            Intent intent = new Intent(LoginActivity.this, GestureVerifyActivity.class);
            intent.putExtra("from", ApplicationConsts.ACTIVITY_SPLASH);
            intent.putExtra("title", "手势密码登录");
            intent.putExtra("message", "请画出手势密码解锁");
            startActivity(intent);
            finish();
            return;
        }

        //第一次登录
        PreferenceUtil.setFirstLogin(false);    //设置非第一次登录标记
        if (PreferenceUtil.getIsAnswer()) { //判断是否答题  true:已答题
            if (PreferenceUtil.getIsInvestor()) {   //是否做过合格投资者判定
                Intent i = new Intent(LoginActivity.this, GestureEditActivity.class);
                i.putExtra("back_from_change_gesture", "back_from_change_gesture");
                i.putExtra("comeflag", 1);
                if (tomain != null) {
                    i.putExtra("tomain", tomain);
                }
                i.putExtra("title", R.string.title_gestureset);
                startActivity(i);
            } else {    //是否做过合格投资者判定 否
                if (PreferenceUtil.getTotalAmount()) {  //判断账户资产是否大于300万
                    Intent i_commitment = new Intent(this, WebSurveyActivity.class);
                    i_commitment.putExtra("type", WebSurveyActivity.WEBTYPE_INVESTOR_COMMITMENT);
                    i_commitment.putExtra("title", "投资者承诺函");
                    i_commitment.putExtra("btnInfo", "我同意该承诺");
                    startActivity(i_commitment);
                } else {    //判断账户资产是否大于300万 否
                    Intent i_judge = new Intent(this, WebInvestorJudgeActivity.class);
                    i_judge.putExtra("type", WebInvestorJudgeActivity.WEBTYPE_INVESTOR_JUDGE);
                    i_judge.putExtra("title", "投资者判定");
                    i_judge.putExtra("btnInfo", "400-80-88888");
                    startActivity(i_judge);
                }
            }
        } else {//判断是否答题  false:未答题
            Intent i_survey = new Intent(LoginActivity.this, WebSurveyActivity.class);
            i_survey.putExtra("type", WebSurveyActivity.WEBTYPE_SURVEY);
            i_survey.putExtra("title", "问卷调查");
            i_survey.putExtra("btnInfo", "开始答题");
            startActivity(i_survey);
        }
        finish();
    }

   /* private long preTime;   //上一次的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();  //本次按下按钮的时间
            if (currentTime - preTime < 2000) {
                //方案一
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                am.restartPackage("com.jdhui");

                //方案二
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
            } else {
                Toast.makeText(LoginActivity.this, "再按一次，退出程序", Toast.LENGTH_SHORT).show();
                preTime = currentTime;
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
*/
}
