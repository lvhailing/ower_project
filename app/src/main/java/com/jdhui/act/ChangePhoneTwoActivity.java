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
 * 修改手机号
 */
public class ChangePhoneTwoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText mEditInput;
    private ImageView mBtnDelete;
    private Button mBtnNext;
    private String phone = null;
    private TextView mTvPhone;
    private Button btnGetSMS;
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
        phone = getIntent().getStringExtra("phone");
        initView();
        initData();
    }

    private void initView() {

        iv_back = (ImageView) findViewById(R.id.iv_back);
        mTvPhone = (TextView) findViewById(R.id.id_change_phone_tv_number);
        mTvPhone.setText("手机号:" + phone);
        btnGetSMS = (Button) findViewById(R.id.changephone_getSMS);
        mEditInput = (EditText) findViewById(R.id.id_inut_verify_code);
        mBtnDelete = (ImageView) findViewById(R.id.id_btn_delete);
        mBtnNext = (Button) findViewById(R.id.id_btn_next);
        iv_back.setOnClickListener(this);
        btnGetSMS.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnNext.setClickable(false);
        mEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    mBtnDelete.setVisibility(View.GONE);
                    mBtnNext.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    mBtnNext.setClickable(false);
                } else {
                    mBtnDelete.setVisibility(View.VISIBLE);
                    mBtnNext.setBackgroundResource(R.drawable.shape_button_red);
                    mBtnNext.setClickable(true);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.id_btn_delete:
                mEditInput.setText("");
                break;
            case R.id.changephone_getSMS:
                requestSMS();
                break;
            case R.id.id_btn_next:
                String validateCode = mEditInput.getText().toString();
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
                if (b != null) {
                    if (Boolean.parseBoolean(b.getResult())) {
                        Toast.makeText(ChangePhoneTwoActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                        smsflag = true;
                        startThread();
                    } else {
                        smsflag = false;
                        Toast.makeText(ChangePhoneTwoActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangePhoneTwoActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initData() {
        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.signup_time);
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
            btnGetSMS.setText(getResources().getString(R.string.findpd_getphoneauth));
            btnGetSMS.setClickable(true);
            btnGetSMS.setTextColor(getResources().getColor(R.color.txt_white));
            btnGetSMS.setBackgroundResource(R.drawable.shape_button_red);
        } else {
            if (time <= 59) {
                btnGetSMS.setClickable(false);
                btnGetSMS.setBackgroundResource(R.drawable.shape_button_gray_verify);
                btnGetSMS.setTextColor(getResources().getColor(R.color.txt_gray));
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
                        intent.putExtra("userInfoId",PreferenceUtil.getUserInfoId());
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
