package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultVerifyPassWordContent;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 验证登陆密码
 */
public class VerifyPassWordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText mEditInput;
    private ImageView mBtnDelete;
    private Button mBtnNext;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_verify_password);
        initView();

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mEditInput = (EditText) findViewById(R.id.et_new_phone);
        mBtnDelete = (ImageView) findViewById(R.id.iv_delete);
        mBtnNext = (Button) findViewById(R.id.btn_submit);

        iv_back.setOnClickListener(this);
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
            case R.id.iv_delete:
                mEditInput.setText("");
                break;
            case R.id.btn_submit:
                String password = mEditInput.getText().toString();
                if (!TextUtils.isEmpty(password)) {
                    requestData(password);
                } else {
                    Toast.makeText(VerifyPassWordActivity.this, "登录密码为空", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    private void requestData(String password) {
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HtmlRequest.verifyPassWord(VerifyPassWordActivity.this, userId, password, new BaseRequester.OnRequestListener() {

                @Override
                public void onRequestFinished(BaseParams params) {
                    if (params.result == null) {
                        Toast.makeText(VerifyPassWordActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ResultVerifyPassWordContent data = (ResultVerifyPassWordContent) params.result;
                    if (data.getFlag().equals("true")) {
                        Toast.makeText(VerifyPassWordActivity.this, data.getMsg(), Toast.LENGTH_LONG).show();
                        Intent i_next = new Intent(VerifyPassWordActivity.this, ChangePhoneOneActivity.class);
                        startActivity(i_next);
                    } else {
                        Toast.makeText(VerifyPassWordActivity.this, data.getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
