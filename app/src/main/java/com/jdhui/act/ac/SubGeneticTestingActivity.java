package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 服务--提交基因检测预约
 */
public class SubGeneticTestingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private EditText et_name; //预约人
    private TextView tv_select_sex; //选择预约人性别
    private EditText et_age;
    private TextView tv_test_set;
    private EditText et_phone;
    private TextView et_address;
    private Button btn_submit;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_sub_genetic_testing);

        initView();
        initData();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");   //提交时 会用到 上个界面传来
        String name = getIntent().getStringExtra("name");
        tv_test_set.setText(name);  //先设置上套餐名字  是从上个界面传来的
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_select_sex = (TextView) findViewById(R.id.tv_select_sex);
        et_age = (EditText) findViewById(R.id.et_age);
        tv_test_set = (TextView) findViewById(R.id.tv_test_set);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address = (TextView) findViewById(R.id.et_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
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
           /* case R.id.rl_hospital: //预约医院
                Intent intent = new Intent(this, BookingHospitalListActivity.class);
                startActivityForResult(intent, 0);
                break;*/
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            id = data.getStringExtra("id");
            tv_hospital.setText(id);
        }
    }*/

    private void submit() {
        //hospitalId:16102616045315630527   北京协和医院
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Todo:no commit!
        /*HtmlRequest.subGeneticTesting(this, userId, id,  "", "男", "30", "", "", "", "", "", "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubmitBookingHospital2B bookingHospital2B = (SubmitBookingHospital2B) params.result;
                    if (bookingHospital2B != null) {
                        if (Boolean.parseBoolean(bookingHospital2B.getFlag())) {
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
        });*/
    }


}
