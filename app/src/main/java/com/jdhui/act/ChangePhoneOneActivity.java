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
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

/**
 * 修改手机号一
 */
public class ChangePhoneOneActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText et_new_phone; // 新手机号
    private ImageView iv_delete;
    private Button btn_next; // 下一步
    private TextView tv_current_phone; // 当前手机号
    private String phone;
    private String newPhone; // 新手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_change_phone_one);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_new_phone = (EditText) findViewById(R.id.et_new_phone);
        tv_current_phone = (TextView) findViewById(R.id.tv_current_phone);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        btn_next = (Button) findViewById(R.id.btn_next);

        try {
            phone = DESUtil.decrypt(PreferenceUtil.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (phone != null) {
            String phoneNum = StringUtil.replaceSubString(phone);
            tv_current_phone.setText("当前手机号：" + phoneNum);
        }

        iv_back.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_next.setClickable(false);
        et_new_phone.addTextChangedListener(new TextWatcher() {
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
                    btn_next.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    btn_next.setClickable(false);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                    btn_next.setBackgroundResource(R.drawable.shape_button_red);
                    btn_next.setClickable(true);
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
                et_new_phone.setText("");
                break;
            case R.id.btn_next: // 下一步
                 newPhone = et_new_phone.getText().toString();
                if (StringUtil.isMobileNO(newPhone)) {
                    requestData(newPhone);
                } else {
                    Toast.makeText(ChangePhoneOneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }

    /**
     * 修改手机号(验证新输入的手机号是否已经注册过)
     * @param mobile
     */
    private void requestData(String mobile) {
        HtmlRequest.checkPhone(ChangePhoneOneActivity.this, mobile, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                ResultCodeContentBean b = (ResultCodeContentBean) params.result;
                if (b == null) {
                    Toast.makeText(ChangePhoneOneActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }
                if (Boolean.parseBoolean(b.getFlag())) {
                    Intent intent = new Intent(ChangePhoneOneActivity.this, ChangePhoneTwoActivity.class);
                    intent.putExtra("phone", newPhone);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePhoneOneActivity.this, b.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
