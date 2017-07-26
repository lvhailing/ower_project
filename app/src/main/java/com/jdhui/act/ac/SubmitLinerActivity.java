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
import com.jdhui.bean.mybean.GetGolfInfo2B;
import com.jdhui.bean.mybean.SubBookingShip2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

/**
 * 服务--提交豪华邮轮预约
 */
public class SubmitLinerActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private EditText et_name; //预约人
    private EditText et_phone;
    private Button btn_submit; //提交预约 按钮
    private String shipId;//邮轮 id
//    private String userName;
//    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_sub_booking_ship);

        initView();
        initData();
    }

    private void initData() {
        shipId = getIntent().getStringExtra("shipId");

        requestCustomerData(); //请求客户信息

    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void requestCustomerData() {
        HtmlRequest.getInfo(this, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    GetGolfInfo2B info2B = (GetGolfInfo2B) params.result;
                    if (info2B != null) {
                        String userName = info2B.getUserName();
                        String mobile = info2B.getUserMobile();
                        et_name.setText(userName);
                        et_phone.setText(mobile);
                    }
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
            case R.id.btn_submit:
                submit();
                break;
        }
    }


    private void submit() {
        String userName = et_name.getText().toString();
        String mobile = et_phone.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(SubmitLinerActivity.this, "请输入预约人", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!StringUtil.isMobileNO(mobile)) {
            Toast.makeText(SubmitLinerActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.subBookingShip(this, mobile, shipId, userName, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubBookingShip2B bookingShip = (SubBookingShip2B) params.result;
                    if (bookingShip != null) {
                        if (Boolean.parseBoolean(bookingShip.getFlag())) {
                            Toast.makeText(SubmitLinerActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SubmitLinerActivity.this, MainActivity.class);
                            intent.putExtra("tab", 2);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SubmitLinerActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubmitLinerActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
