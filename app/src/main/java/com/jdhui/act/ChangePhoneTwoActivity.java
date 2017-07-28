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
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.bean.ResultSentSMSContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 修改手机号二
 */
public class ChangePhoneTwoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageView iv_delete;
    private Button btn_submit;
    private String phone = null;
    private TextView tv_change_phone_number;
    private EditText et_identifying_code; // 输入验证码
    private Button btn_identifying_code; // 获取验证码
    private boolean smsflag = false;
    private MyHandler mHandler;
    private int time = 60;
    private boolean flag = true;
    private int button = 0;
    private String btnString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_change_phone_two);

        initView();
        initData();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_change_phone_number = (TextView) findViewById(R.id.tv_change_phone_number);
        btn_identifying_code = (Button) findViewById(R.id.btn_identifying_code);
        et_identifying_code = (EditText) findViewById(R.id.et_identifying_code);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        iv_back.setOnClickListener(this);
        btn_identifying_code.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_submit.setClickable(false);
        et_identifying_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    iv_delete.setVisibility(View.GONE);
                    btn_submit.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    btn_submit.setClickable(false);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                    btn_submit.setBackgroundResource(R.drawable.shape_button_red);
                    btn_submit.setClickable(true);
                }

            }
        });
    }

    private void initData() {
        phone = getIntent().getStringExtra("phone");
        tv_change_phone_number.setText("手机号:" + phone);

        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.signup_time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_delete: // 删除输入的验证码
                et_identifying_code.setText("");
                break;
            case R.id.btn_identifying_code: // 获取验证码
                requestSMS();
                break;
            case R.id.btn_submit: // 提交
                String validateCode = et_identifying_code.getText().toString();
                if (!TextUtils.isEmpty(validateCode)) {
                    requestData(phone, validateCode);
                } else {
                    Toast.makeText(ChangePhoneTwoActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }

    // 请求短信验证码
    private void requestSMS() {
        //  String mathRandom=String.valueOf(Math.random());
        HtmlRequest.sentSMS(ChangePhoneTwoActivity.this, phone, ApplicationConsts.MOBILEEDIT, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                ResultSentSMSContentBean b = (ResultSentSMSContentBean) params.result;
                if (b == null) {
                    Toast.makeText(ChangePhoneTwoActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Boolean.parseBoolean(b.getResult())) {
                    Toast.makeText(ChangePhoneTwoActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                    smsflag = true;
                    startThread();
                } else {
                    smsflag = false;
                    Toast.makeText(ChangePhoneTwoActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
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
            btn_identifying_code.setText(getResources().getString(R.string.findpd_getphoneauth));
            btn_identifying_code.setClickable(true);
            btn_identifying_code.setTextColor(getResources().getColor(R.color.txt_white));
            btn_identifying_code.setBackgroundResource(R.drawable.shape_button_red);
        } else {
            if (time <= 59) {
                btn_identifying_code.setClickable(false);
                btn_identifying_code.setBackgroundResource(R.drawable.shape_button_gray_verify);
                btn_identifying_code.setTextColor(getResources().getColor(R.color.txt_gray));
                btn_identifying_code.setText(time + btnString);
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

    /**
     * 点提交按钮时调接口
     * @param mobile
     * @param validateCode
     */
    private void requestData(String mobile, String validateCode) {
        HtmlRequest.savePhone(ChangePhoneTwoActivity.this, mobile, validateCode, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                ResultCodeContentBean b = (ResultCodeContentBean) params.result;
                if (b != null) {
                    if (Boolean.parseBoolean(b.getFlag())) {
                        Toast.makeText(ChangePhoneTwoActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();
                        try {
                            PreferenceUtil.setPhone(DESUtil.encrypt(phone));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(ChangePhoneTwoActivity.this, AccountActivity.class);
                        intent.putExtra("userInfoId", PreferenceUtil.getUserInfoId());
                        startActivity(intent);

                        //栈里只留MainActivity
                        ActivityStack stack = ActivityStack.getActivityManage();
                        stack.removeAllActivityExceptOne("com.jdhui.act.MainActivity");
                    } else {
                        Toast.makeText(ChangePhoneTwoActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePhoneTwoActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
