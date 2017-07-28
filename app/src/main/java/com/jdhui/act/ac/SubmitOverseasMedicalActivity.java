package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.MainActivity;
import com.jdhui.bean.mybean.SubOverseasMedical2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

/**
 * 服务--提交海外医疗预约
 */
public class SubmitOverseasMedicalActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText et_name; //预约人
    private EditText et_phone; // 联系电话
    private EditText et_financial; // 专属理财师
    private Button btn_submit;
    private String overseasType;// 海外医疗预约类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_submit_overseas_medical);

        initView();
        initData();
    }

    private void initData() {
        overseasType = getIntent().getStringExtra("overseasType");
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_financial = (EditText) findViewById(R.id.et_financial);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        iv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }


    private void submit() {
        String client = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String financial = et_financial.getText().toString();

        if (TextUtils.isEmpty(client)) {
            Toast.makeText(SubmitOverseasMedicalActivity.this, "请输入预约人", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SubmitOverseasMedicalActivity.this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        }else if (!StringUtil.isMobileNO(phone)) {
            Toast.makeText(SubmitOverseasMedicalActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(financial)) {
            Toast.makeText(SubmitOverseasMedicalActivity.this, "请输入专属理财师", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.submitOverseasMedical(this, client, phone, overseasType, financial,  new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubOverseasMedical2B overseasMedical = (SubOverseasMedical2B) params.result;
                    if (overseasMedical != null) {
                        if (Boolean.parseBoolean(overseasMedical.getFlag())) {
                            Toast.makeText(SubmitOverseasMedicalActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SubmitOverseasMedicalActivity.this, MainActivity.class);
                            intent.putExtra("tab", 2);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SubmitOverseasMedicalActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubmitOverseasMedicalActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
