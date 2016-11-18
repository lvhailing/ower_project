package com.jdhui.act;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.net.UserLoadout;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

public class ChangePasswordActivity extends BaseActivity implements
        OnClickListener {

    private EditText edt_old, edt_new, edt_again;
    private Button btnOk;
    private ImageView mBtnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_changepwd);
        initView();
    }

    private void initView() {

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        mBtnBack = (ImageView) findViewById(R.id.id_img_back);
        edt_again = (EditText) findViewById(R.id.changepwd_new2);
        edt_old = (EditText) findViewById(R.id.changepwd_old);
        edt_new = (EditText) findViewById(R.id.changepwd_new);
        btnOk = (Button) findViewById(R.id.changepwd_ok);
        mBtnBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnOk.setClickable(false);
        edt_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    btnOk.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(edt_old.getText().toString()) && !TextUtils.isEmpty(edt_new.getText().toString())) {
                        btnOk.setBackgroundResource(R.drawable.shape_button_orange);
                        btnOk.setClickable(true);
                    }
                }

            }
        });

        edt_old.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    btnOk.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(edt_again.getText().toString()) && !TextUtils.isEmpty(edt_new.getText().toString())) {
                        btnOk.setBackgroundResource(R.drawable.shape_button_orange);
                        btnOk.setClickable(true);
                    }
                }

            }
        });

        edt_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    btnOk.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    btnOk.setClickable(false);
                } else {
                    if (!TextUtils.isEmpty(edt_again.getText().toString()) && !TextUtils.isEmpty(edt_old.getText().toString())) {
                        btnOk.setBackgroundResource(R.drawable.shape_button_orange);
                        btnOk.setClickable(true);
                    }
                }

            }
        });

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
            case R.id.changepwd_ok:
                String pwdnew = edt_new.getText().toString();
                String pwdnew2 = edt_again.getText().toString();
                if (StringUtil.hasBlank(pwdnew2)) {
                    Toast.makeText(ChangePasswordActivity.this, "密码中不能含有空格",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (pwdnew.equals(pwdnew2)) {
                        if (!StringUtil.hasSpecialWordOne(pwdnew2)) {
                            if (pwdnew.length() < 8 || pwdnew.length() > 16) {
                                Toast.makeText(ChangePasswordActivity.this,
                                        "密码长度在8-16个字符之间", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                requestData();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this,
                                    "密码只能是字母和数字组合", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ChangePasswordActivity.this,
                                "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void requestData() {
        try {
            HtmlRequest.changePassword(ChangePasswordActivity.this, DESUtil.decrypt(PreferenceUtil.getUserId()), edt_old.getText().toString(), edt_new.getText().toString(),
                    new BaseRequester.OnRequestListener() {
                        @Override
                        public void onRequestFinished(BaseParams params) {
                            if (params.result != null) {
                                ResultCodeContentBean bean = (ResultCodeContentBean) params.result;
                                if (Boolean.parseBoolean(bean.getFlag())) {
                                    Toast.makeText(ChangePasswordActivity.this,
                                            "密码修改成功,请重新登录", Toast.LENGTH_SHORT)
                                            .show();
                                    UserLoadout out = new UserLoadout(
                                            ChangePasswordActivity.this);
                                    out.requestData();
                                    finish();
                                } else {
                                    Toast.makeText(ChangePasswordActivity.this, bean.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ChangePasswordActivity.this,
                                        "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
