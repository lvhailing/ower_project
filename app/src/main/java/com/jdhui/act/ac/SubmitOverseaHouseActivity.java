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
import com.jdhui.bean.mybean.SubOverseaProject2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

/**
 * 服务--提交海外房产项目预约
 */
public class SubmitOverseaHouseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText et_name; // 预约人
    private EditText et_phone; // 预约人电话
    private EditText et_financial_planner; // 专属理财师
    private Button btn_submit;
    private String houseId; // 房产项目Id(即项目编号)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_project_booking);

        initView();
        initData();
    }

    private void initData() {
        houseId = getIntent().getStringExtra("houseId");

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_financial_planner = (EditText) findViewById(R.id.et_financial_planner);
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
            case R.id.btn_submit: // 提交预约
                submit();
                break;
        }
    }

    // 海外项目提交预约
    private void submit() {
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String financialPlanner = et_financial_planner.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SubmitOverseaHouseActivity.this, "请输入预约人", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SubmitOverseaHouseActivity.this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isMobileNO(phone)) {
            Toast.makeText(SubmitOverseaHouseActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(financialPlanner)) {
            Toast.makeText(SubmitOverseaHouseActivity.this, "请输入专属理财师", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.subOverseaProject(this, name, phone, houseId, financialPlanner, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params == null) {
                    Toast.makeText(SubmitOverseaHouseActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;

                }
                SubOverseaProject2B OverseaProjectData = (SubOverseaProject2B) params.result;
                if (OverseaProjectData != null) {
                    if (Boolean.parseBoolean(OverseaProjectData.getFlag())) {
                        Toast.makeText(SubmitOverseaHouseActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SubmitOverseaHouseActivity.this, MainActivity.class);
                        intent.putExtra("tab", 2);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubmitOverseaHouseActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
