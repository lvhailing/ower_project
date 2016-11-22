package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 更多--服务预约详情
 */
public class ServiceOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private TextView mTvOrderName; //预约人
    private TextView mTvSex; //性别
    private TextView mTvAge; //年龄
    private TextView mTvGeneticTestName; //基因 检测套餐
    private TextView mTvGeneticTestAddress; //基因 通讯地址
    private TextView mTvSocialSecurityNum; //社保号码
    private TextView mTvIdNum; //身份证号
    private TextView mTvPhone; //联系电话
    private TextView mTvSpareTime1; //备选时间1
    private TextView mTvSpareTime2; //备选时间2
    private TextView mTvOrderHospital; //预约医院
    private TextView mTvOrderDepartment; //预约科室
    private TextView mTvOrderDoctor; //预约医生

    private TextView mTvDescIllness; //主诉病情
    private TextView mTvOrderTime; //预约时间
    private String id; //服务id
    private String serviceItems; //服务类型
    private ServiceDetail2B detail2B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_order_detail);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        mTvOrderService = (TextView) findViewById(R.id.tv_order_service);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvGeneticTestName = (TextView) findViewById(R.id.tv_genetic_test_name);
        mTvGeneticTestAddress = (TextView) findViewById(R.id.tv_genetic_test_address);
        mTvSocialSecurityNum = (TextView) findViewById(R.id.tv_social_security_num);
        mTvIdNum = (TextView) findViewById(R.id.tv_id_num);
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

    private void initData() {
        id = getIntent().getStringExtra("id");
        serviceItems = getIntent().getStringExtra("serviceItems");

        requestDetailData();
    }


    private void requestDetailData() {
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
    }

    private void setView() {
        //绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking

        if (detail2B.getServiceItems().equals("hospitalBooking")) {
            //是绿通就医
            mTvOrderService.setText("绿通就医");
            mTvOrderName.setText(detail2B.getBookingClient());  //预约人
            mTvSocialSecurityNum.setText(detail2B.getSecurityNum()); //社保号码
            mTvIdNum.setText(detail2B.getClientIdNo()); //身份证号
            mTvPhone.setText(detail2B.getClientPhone()); //联系电话
            mTvSpareTime1.setText(detail2B.getBakTimeOne()); //备选时间1
            mTvSpareTime2.setText(detail2B.getBakTimeTwo()); //备选时间2
            mTvOrderHospital.setText(detail2B.getName()); //预约医院
            mTvOrderDepartment.setText(detail2B.getDepartments()); //预约科室
            mTvOrderDoctor.setText(detail2B.getDoctor()); //预约医生
            mTvDescIllness.setText(detail2B.getIllnessCondition()); //主诉病情
            mTvOrderTime.setText(detail2B.getBookingTime()); //预约时间

            mTvSex.setVisibility(View.GONE);
            mTvAge.setVisibility(View.GONE);
            mTvGeneticTestName.setVisibility(View.GONE); //基因 检测套餐
            mTvGeneticTestAddress.setVisibility(View.GONE); //基因 通讯地址
        } else if (detail2B.getServiceItems().equals("geneticBooking")) {
            //是基因检测
            mTvSex.setVisibility(View.VISIBLE);
            mTvAge.setVisibility(View.VISIBLE);
            mTvGeneticTestName.setVisibility(View.VISIBLE);
            mTvGeneticTestAddress.setVisibility(View.VISIBLE);

            mTvSocialSecurityNum.setVisibility(View.GONE);
            mTvIdNum.setVisibility(View.GONE);
            mTvSpareTime1.setVisibility(View.GONE);
            mTvSpareTime2.setVisibility(View.GONE);
            mTvOrderHospital.setVisibility(View.GONE);
            mTvOrderDepartment.setVisibility(View.GONE);
            mTvOrderDoctor.setVisibility(View.GONE);
            mTvDescIllness.setVisibility(View.GONE);

            mTvOrderService.setText("基因检测");
            mTvOrderName.setText(detail2B.getBookingClient());  //预约人
            mTvSex.setText(detail2B.getUserSex());  //预约人性别
            mTvAge.setText(detail2B.getUserAge());  //预约人年龄
            mTvGeneticTestName.setText(detail2B.getName());  //基因 检测套餐
            mTvPhone.setText(detail2B.getClientPhone()); //联系电话
            mTvGeneticTestAddress.setText(detail2B.getUserAddress());  //基因 通讯地址
            mTvOrderTime.setText(detail2B.getBookingTime()); //预约时间

        } else {
            //是高尔夫
            mTvOrderService.setText("高尔夫球场地");
            mTvOrderName.setText(detail2B.getUserInfoName());  //预约人
            mTvOrderTime.setText(detail2B.getBookingTime()); //预约时间

            mTvSocialSecurityNum.setVisibility(View.GONE);
            mTvIdNum.setVisibility(View.GONE);
            mTvSpareTime1.setVisibility(View.GONE);
            mTvSpareTime2.setVisibility(View.GONE);
            mTvOrderHospital.setVisibility(View.GONE);
            mTvOrderDepartment.setVisibility(View.GONE);
            mTvOrderDoctor.setVisibility(View.GONE);
            mTvDescIllness.setVisibility(View.GONE);
            mTvSex.setVisibility(View.GONE);
            mTvAge.setVisibility(View.GONE);
            mTvPhone.setVisibility(View.GONE);
            mTvGeneticTestName.setVisibility(View.GONE); //基因 检测套餐
            mTvGeneticTestAddress.setVisibility(View.GONE); //基因 通讯地址
        }
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
