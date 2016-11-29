package com.jdhui.act.ac;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.SubGeneticTesting2B;
import com.jdhui.dialog.SexDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 服务--提交基因检测预约
 */
public class SubGeneticTestingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private EditText et_name; //预约人
    private RelativeLayout rl_sex; //选择预约人性别
    private TextView tv_select_sex; //选择预约人性别
    private EditText et_age;
    private TextView tv_test_set;
    private EditText et_phone;
    private TextView et_address;
    private Button btn_submit;
    private String geneticId;//基因检测 id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_sub_genetic_testing);

        initView();
        initData();
    }

    private void initData() {
        geneticId = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        tv_test_set.setText(name);  //设置上套餐名字
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_select_sex = (TextView) findViewById(R.id.tv_select_sex);
        et_age = (EditText) findViewById(R.id.et_age);
        tv_test_set = (TextView) findViewById(R.id.tv_test_set);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (TextView) findViewById(R.id.et_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
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
            case R.id.rl_sex: //选择性别
                showDialog();
                break;
        }
    }

    private void showDialog() {
        SexDialog dialog = new SexDialog(this);
        dialog.setDialog(new SexDialog.MyCallback() {
            @Override
            public void onSelected(Dialog ad, String selectedSex) {
                if (tv_select_sex != null) {
                    tv_select_sex.setText(selectedSex);
                }
                ad.dismiss();
                ad = null;
            }
        });
    }

    private void submit() {
        String bookingClient = et_name.getText().toString();
        String userAge = et_age.getText().toString();
        String address = et_address.getText().toString();
        String phone = et_phone.getText().toString();

        HtmlRequest.subGeneticTesting(this, geneticId, "女", userAge, address, bookingClient, phone, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubGeneticTesting2B geneticTesting2B = (SubGeneticTesting2B) params.result;
                    if (geneticTesting2B != null) {
                        if (Boolean.parseBoolean(geneticTesting2B.getFlag())) {
                            Toast.makeText(SubGeneticTestingActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(SubGeneticTestingActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubGeneticTestingActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


}
