package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultFindPWDbyPhoneContent;
import com.jdhui.bean.ResultSentSMSContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

/**
 * 密码找回
 */
public class FindPassWordActivity extends BaseActivity implements View.OnClickListener{
    private Button btnOk, btnGetSMS;
    private EditText edtPhone, edtNew, edtNewAgain, edtAuth;
    private boolean smsflag = false;
    private MyHandler mHandler;
    private int time = 60;
    private boolean flag = true;
    private int button = 0;
    private String btnString;
    private ImageView mImgBack;
    private EditText findpd_newnickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_find_password);
        initView();
        initData();
    }
    private void initView() {
        mImgBack= (ImageView) findViewById(R.id.id_img_back);
        btnGetSMS = (Button) findViewById(R.id.findpd_getSMS);
        btnOk = (Button) findViewById(R.id.findpd_ok);
        edtPhone = (EditText) findViewById(R.id.findpd_phone);
        edtNew = (EditText) findViewById(R.id.findpd_newpwd);
        edtNewAgain = (EditText) findViewById(R.id.findpd_newpwd2);
        edtAuth = (EditText) findViewById(R.id.findpd_authcode);
        findpd_newnickname = (EditText) findViewById(R.id.findpd_newnickname);
        mImgBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnGetSMS.setOnClickListener(this);
        btnOk.setClickable(false);
        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);

        edtNew.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pwd = edtNew.getText().toString();
                    if (pwd.equals("")) {
                        Toast.makeText(FindPassWordActivity.this,
                                getResources().getString(R.string.findpd_null),
                                Toast.LENGTH_LONG).show();
                    } else if (pwd.length() < 6) {
                        Toast.makeText(
                                FindPassWordActivity.this,
                                getResources().getString(R.string.findpd_short),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(edtAuth.getText())){
                    if(!TextUtils.isEmpty(findpd_newnickname.getText())){
                        if(!TextUtils.isEmpty(edtNew.getText())){
                            if(!TextUtils.isEmpty(edtNewAgain.getText())){

                                if(!TextUtils.isEmpty(charSequence)){
                                    btnOk.setClickable(true);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_red);
                                }else{
                                    btnOk.setClickable(false);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                                }
                            }else{
                                btnOk.setClickable(false);
                                btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                            }
                        }else{
                            btnOk.setClickable(false);
                            btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                        }
                    }else{
                        btnOk.setClickable(false);
                        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                }else{
                    btnOk.setClickable(false);
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtAuth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    if(!TextUtils.isEmpty(findpd_newnickname.getText())){
                        if(!TextUtils.isEmpty(edtNew.getText())){
                            if(!TextUtils.isEmpty(edtNewAgain.getText())){
                                if(!TextUtils.isEmpty(edtPhone.getText())){
                                    btnOk.setClickable(true);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_red);
                                }else{
                                    btnOk.setClickable(false);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                                }
                            }else{
                                btnOk.setClickable(false);
                                btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                            }
                        }else{
                            btnOk.setClickable(false);
                            btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                        }
                    }else{
                        btnOk.setClickable(false);
                        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                }else{
                    btnOk.setClickable(false);
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        findpd_newnickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(edtAuth.getText())){
                    if(!TextUtils.isEmpty(charSequence)){
                        if(!TextUtils.isEmpty(edtNew.getText())){
                            if(!TextUtils.isEmpty(edtNewAgain.getText())){
                                if(!TextUtils.isEmpty(edtPhone.getText())){
                                    btnOk.setClickable(true);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_red);
                                }else{
                                    btnOk.setClickable(false);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                                }
                            }else{
                                btnOk.setClickable(false);
                                btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                            }
                        }else{
                            btnOk.setClickable(false);
                            btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                        }
                    }else{
                        btnOk.setClickable(false);
                        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                }else{
                    btnOk.setClickable(false);
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(edtAuth.getText())){
                    if(!TextUtils.isEmpty(findpd_newnickname.getText())){
                        if(!TextUtils.isEmpty(charSequence)){
                            if(!TextUtils.isEmpty(edtNewAgain.getText())){
                                if(!TextUtils.isEmpty(edtPhone.getText())){
                                    btnOk.setClickable(true);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_red);
                                }else{
                                    btnOk.setClickable(false);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                                }
                            }else{
                                btnOk.setClickable(false);
                                btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                            }
                        }else{
                            btnOk.setClickable(false);
                            btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                        }
                    }else{
                        btnOk.setClickable(false);
                        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                }else{
                    btnOk.setClickable(false);
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtNewAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(edtAuth.getText())){
                    if(!TextUtils.isEmpty(findpd_newnickname.getText())){
                        if(!TextUtils.isEmpty(edtNew.getText())){
                            if(!TextUtils.isEmpty(charSequence)){
                                if(!TextUtils.isEmpty(edtPhone.getText())){
                                    btnOk.setClickable(true);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_red);
                                }else{
                                    btnOk.setClickable(false);
                                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                                }
                            }else{
                                btnOk.setClickable(false);
                                btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                            }
                        }else{
                            btnOk.setClickable(false);
                            btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                        }
                    }else{
                        btnOk.setClickable(false);
                        btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                    }
                }else{
                    btnOk.setClickable(false);
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_d);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findpd_phone:
                break;
            case R.id.findpd_getSMS:
                if (!TextUtils.isEmpty(edtPhone.getText())) {
                    if (StringUtil.isMobileNO(edtPhone.getText().toString())) {
                        requestSMS();
                    } else {
                        Toast.makeText(FindPassWordActivity.this, "请输入正确的手机号",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FindPassWordActivity.this, "请输入手机号",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.findpd_ok:
                String newpwd = edtNew.getText().toString();
                String newpwdAgain = edtNewAgain.getText().toString();
                String phone = edtPhone.getText().toString();
                String authCode = edtAuth.getText().toString();

                if (TextUtils.isEmpty(newpwd)) {
                    Toast.makeText(FindPassWordActivity.this, "请输入新密码",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (!newpwd.equals(edtNewAgain.getText().toString())) {
                        Toast.makeText(FindPassWordActivity.this,
                                "两次密码输入不一致",
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (StringUtil.hasBlank(newpwd)) {
                            Toast.makeText(
                                    FindPassWordActivity.this,
                                    getResources().getString(
                                            R.string.findpd_hasspace),
                                    Toast.LENGTH_LONG).show();
                        } else {
                           if (newpwd.length() < 8 || newpwd.length() > 16||StringUtil.hasSpecialWordOne(newpwd)) {
                                Toast.makeText(FindPassWordActivity.this,
                                        "登录密码为8~16位字母数字组合", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                if (!TextUtils.isEmpty(authCode)) {
                                    if (TextUtils.isEmpty(phone)) {
                                        Toast.makeText(FindPassWordActivity.this, "手机号不能为空",
                                                Toast.LENGTH_SHORT).show();
                                        edtPhone.requestFocusFromTouch();

                                    } else {
                                        if (StringUtil.isMobileNO(edtPhone.getText().toString())) {
                                            if(TextUtils.isEmpty(findpd_newnickname.getText().toString())){
                                                Toast.makeText(FindPassWordActivity.this, "用户名不能为空",
                                                        Toast.LENGTH_SHORT).show();
                                            }else{
                                                requestData(phone,newpwdAgain,authCode,findpd_newnickname.getText().toString());
                                            }

                                        }else{
                                            Toast.makeText(FindPassWordActivity.this, "请输入正确的手机号码",
                                                    Toast.LENGTH_SHORT).show();
                                            edtPhone.requestFocusFromTouch();
                                        }
                                    }
                                } else {
                                    Toast.makeText(FindPassWordActivity.this,
                                            "您输入的短信验证码不正确", Toast.LENGTH_SHORT)
                                            .show();
                                }

                            }
                        }
                    }
                }
                break;
            case R.id.id_img_back:
                finish();
                break;
            default:
                break;
        }
    }
    private void initData() {
        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.signup_time);
    }

    // 请求找回密码
    private void requestData(String mobile, String newPassword, String validateCode,String userName) {

//        try {
//            userName = DESUtil.decrypt(PreferenceUtil.getAutoLoginAccount());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        HtmlRequest.findPWDbyPhone(FindPassWordActivity.this, mobile, newPassword,validateCode,userName,
                new BaseRequester.OnRequestListener() {

                    @Override
                    public void onRequestFinished(BaseParams params) {
                        if (params.result != null) {
                            ResultFindPWDbyPhoneContent data = (ResultFindPWDbyPhoneContent) params.result;
                            if (Boolean.parseBoolean(data.getFlag())) {
                                Toast.makeText(
                                        FindPassWordActivity.this,
                                        getResources().getString(
                                                R.string.findpd_success),
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent();
                                i.putExtra("result", "ok");
                                setResult(RESULT_OK, i);
                                finish();
                            } else {
                                Toast.makeText(FindPassWordActivity.this,
                                        data.getMsg(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(FindPassWordActivity.this,
                                    "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    // 请求短信验证码
    private void requestSMS() {
        final String userMobile = edtPhone.getText().toString();
   //     String mathRandom=String.valueOf(Math.random());
        HtmlRequest.sentSMS(FindPassWordActivity.this, userMobile,
                ApplicationConsts.LOGINRET, new BaseRequester.OnRequestListener() {

                    @Override
                    public void onRequestFinished(BaseParams params) {
                        ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
                        if (b != null) {
                            if (Boolean.parseBoolean(b.getFlag())) {
                                Toast.makeText(FindPassWordActivity.this,
                                        "短信发送成功", Toast.LENGTH_LONG).show();
                                smsflag = true;

                                startThread();
                            } else {
                                smsflag = false;
                                Toast.makeText(FindPassWordActivity.this,
                                        b.getMessage(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(FindPassWordActivity.this,
                                    "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void startThread() {
        if (smsflag) {
            Thread t = new Thread(myRunnable);
            flag = true;
            button = R.id.findpd_getSMS;
            t.start();
        }
    }

    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setButtonStyle(msg.arg1);
        }

    }

    private void setButtonStyle(int time) {
        if (time == 0) {
            btnGetSMS.setText(getResources().getString(
                    R.string.findpd_getphoneauth));
            btnGetSMS.setClickable(true);
            btnGetSMS.setTextColor(getResources().getColor(R.color.txt_white));
            btnGetSMS.setBackgroundResource(R.drawable.shape_button_red);
        } else {
            if (time <= 59) {
                btnGetSMS.setClickable(false);
                btnGetSMS.setBackgroundResource(R.drawable.shape_button_gray_verify);
                btnGetSMS.setTextColor(getResources()
                        .getColor(R.color.txt_gray));
                btnGetSMS.setText(time + btnString);
            }
        }
    }

    Runnable myRunnable = new Runnable() {

        @Override
        public void run() {
            while (flag) {
                Message msg = new Message();
                time -= 1;
                msg.arg1 = time;
                if (time == 0) {
                    flag = false;
                    mHandler.sendMessage(msg);
                    time = 60;
                    mHandler.removeCallbacks(myRunnable);
                } else {
                    mHandler.sendMessage(msg);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

}
