package com.jdhui.act.ac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.ServiceOrderAdapter;
import com.jdhui.bean.mybean.Service2B;
import com.jdhui.bean.mybean.Service3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 更多--服务预约详情
 */
public class ServiceOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private TextView mTvOrderName; //预约人
    private TextView mTvSex; //性别
    private TextView mTvAge; //年龄
    private TextView mTvSocialSecurityNum; //社保号码
    private TextView mTvIDNum; //身份证号
    private TextView mTvPhone; //联系电话
    private TextView mTvSpareTime1; //备选时间1
    private TextView mTvSpareTime2; //备选时间2
    private TextView mTvOrderHospital; //预约医院
    private TextView mTvOrderDepartment; //预约科室
    private TextView mTvOrderDoctor; //预约医生
    private TextView mTvDescIllness; //主诉病情
    private TextView mTvOrderTime; //预约时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_order_detail);

        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        mTvOrderService = (TextView) findViewById(R.id.tv_order_service);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvSocialSecurityNum = (TextView) findViewById(R.id.tv_social_security_num);
        mTvIDNum = (TextView) findViewById(R.id.tv_ID_num);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvSpareTime1 = (TextView) findViewById(R.id.tv_spare_time1);
        mTvSpareTime2 = (TextView) findViewById(R.id.tv_spare_time2);
        mTvOrderHospital = (TextView) findViewById(R.id.tv_order_hospital);
        mTvOrderDepartment = (TextView) findViewById(R.id.tv_order_department);
        mTvOrderDoctor = (TextView) findViewById(R.id.tv_order_doctor);
        mTvDescIllness = (TextView) findViewById(R.id.tv_desc_illness);
        mTvOrderTime = (TextView) findViewById(R.id.tv_order_time);

        mBtnBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
