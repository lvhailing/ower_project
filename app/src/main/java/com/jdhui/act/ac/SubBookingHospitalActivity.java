package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.FeedBackActivity;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.bean.mybean.SubmitBookingHospital2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 服务--提交预约医院
 */
public class SubBookingHospitalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_booking_hospital);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void initData() {
//        id = getIntent().getStringExtra("id");
//        serviceItems = getIntent().getStringExtra("serviceItems");

//        requestDetailData();
    }


   /* private void requestDetailData() {
        HtmlRequest.getServiceDetail(this, serviceItems, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    detail2B = (ServiceDetail2B) params.result;
                    if (detail2B != null) {
                        setView();
                    }
                }
            }
        });
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                requestData();
                break;
        }
    }

    private void requestData() {
        //hospitalId:16102616045315630527   北京协和医院
        HtmlRequest.submitBookingHospital(this, "", "16102616045315630527", "", "", "", "", "", "", "", "", "", "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubmitBookingHospital2B bookingHospital2B = (SubmitBookingHospital2B) params.result;
                    if (bookingHospital2B != null) {
                        if (Boolean.parseBoolean(bookingHospital2B.getFlag())) {
                            Toast.makeText(SubBookingHospitalActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(SubBookingHospitalActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubBookingHospitalActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
