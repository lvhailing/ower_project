package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.bean.mybean.ServiceDetail3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 更多--服务预约详情
 */
public class ServiceOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private TextView mTvOrderStatus; //预约状态
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
    private TextView mTvSubTime; //提交时间
    private TextView mTvVenueName;

    private String id; //服务id
    private String serviceItems; //服务类型
    private ServiceDetail2B detail2B;

    private LinearLayout mLlOrderName; //预约人
    private LinearLayout mLlSecurityNum; //社保号
    private LinearLayout mLlIdNum; //身份证号
    private LinearLayout mLlPhone; //联系电话
    private LinearLayout mLlOrderTime; //预约时间
    private LinearLayout mLlSpareTime1; //备选时间1
    private LinearLayout mLlSpareTime2; //备选时间1
    private LinearLayout mLlOrderHospital; //预约医院
    private LinearLayout mLlOrderDepartment; //预约科室
    private LinearLayout mLlOrderDoctor; //预约医生
    private LinearLayout mLlDescIllness; //主诉病情

    private LinearLayout mLlSex; //性别
    private LinearLayout mLlAge; //年龄
    private LinearLayout mLlGeneticTest; //检测套餐
    private LinearLayout mLlAddress; //通讯地址

    private LinearLayout mLlVenueName; //场馆名称
    private LinearLayout mLlOrderStatus; //预约状态
    private LinearLayout mLlSubTime; //提交时间
    private LinearLayout mLlTogether1; //同行人1
    private LinearLayout mLlTogether2; //同行人2

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
        mTvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
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
        mTvSubTime = (TextView) findViewById(R.id.tv_sub_time);
        mTvVenueName = (TextView) findViewById(R.id.tv_venue_name);

        mLlOrderName = (LinearLayout) findViewById(R.id.ll_order_name);
        mLlSecurityNum = (LinearLayout) findViewById(R.id.ll_security_num);
        mLlIdNum = (LinearLayout) findViewById(R.id.ll_id_num);
        mLlSpareTime1 = (LinearLayout) findViewById(R.id.ll_spare_time1);
        mLlSpareTime2 = (LinearLayout) findViewById(R.id.ll_spare_time2);
        mLlOrderHospital = (LinearLayout) findViewById(R.id.ll_order_hospital);
        mLlOrderDepartment = (LinearLayout) findViewById(R.id.ll_order_department);
        mLlOrderDoctor = (LinearLayout) findViewById(R.id.ll_order_doctor);
        mLlDescIllness = (LinearLayout) findViewById(R.id.ll_desc_illness);
        mLlPhone = (LinearLayout) findViewById(R.id.ll_phone);
        mLlSex = (LinearLayout) findViewById(R.id.ll_sex);
        mLlAge = (LinearLayout) findViewById(R.id.ll_age);
        mLlGeneticTest = (LinearLayout) findViewById(R.id.ll_genetic_test);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mLlOrderStatus = (LinearLayout) findViewById(R.id.ll_order_status);
        mLlVenueName = (LinearLayout) findViewById(R.id.ll_venue_name);
        mLlSubTime = (LinearLayout) findViewById(R.id.ll_sub_time);
        mLlTogether1 = (LinearLayout) findViewById(R.id.ll_together1);
        mLlTogether2 = (LinearLayout) findViewById(R.id.ll_together2);
        mLlOrderTime = (LinearLayout) findViewById(R.id.ll_order_time);

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

        if (detail2B.getHospitalBooking() != null) {
            //是绿通就医
            mTvOrderService.setText("绿通就医");

            mLlOrderName.setVisibility(View.VISIBLE);
            mLlSecurityNum.setVisibility(View.VISIBLE);
            mLlIdNum.setVisibility(View.VISIBLE);
            mLlPhone.setVisibility(View.VISIBLE);
            mLlOrderTime.setVisibility(View.VISIBLE);
            mLlSpareTime1.setVisibility(View.VISIBLE);
            mLlSpareTime2.setVisibility(View.VISIBLE);
            mLlOrderHospital.setVisibility(View.VISIBLE);
            mLlOrderDepartment.setVisibility(View.VISIBLE);
            mLlOrderDoctor.setVisibility(View.VISIBLE);
            mLlDescIllness.setVisibility(View.VISIBLE);
            mLlSubTime.setVisibility(View.VISIBLE);

            mTvOrderStatus.setText("新增待写");  //预约状态
            mTvOrderName.setText(detail2B.getHospitalBooking().getBookingClient());  //预约人
            mTvSocialSecurityNum.setText(detail2B.getHospitalBooking().getSecurityNum()); //社保号码
            mTvIdNum.setText(detail2B.getHospitalBooking().getClientIdNo()); //身份证号
            mTvPhone.setText(detail2B.getHospitalBooking().getClientPhone()); //联系电话
            mTvOrderTime.setText(detail2B.getHospitalBooking().getBookingTime()); //预约时间
            mTvSpareTime1.setText(detail2B.getHospitalBooking().getBakTimeOne()); //备选时间1
            mTvSpareTime2.setText(detail2B.getHospitalBooking().getBakTimeTwo()); //备选时间2
            mTvOrderHospital.setText(detail2B.getHospitalBooking().getName()); //预约医院
            mTvOrderDepartment.setText(detail2B.getHospitalBooking().getDepartments()); //预约科室
            mTvOrderDoctor.setText(detail2B.getHospitalBooking().getDoctor()); //预约医生
            mTvDescIllness.setText(detail2B.getHospitalBooking().getIllnessCondition()); //主诉病情
            mTvSubTime.setText("新增待写"); //提交时间

        } else if (detail2B.getGeneticBooking() != null) {
            //是基因检测
            mTvOrderService.setText("基因检测");

            mLlOrderName.setVisibility(View.VISIBLE);
            mLlSex.setVisibility(View.VISIBLE);
            mLlAge.setVisibility(View.VISIBLE);
            mLlGeneticTest.setVisibility(View.VISIBLE);
            mLlPhone.setVisibility(View.VISIBLE);
            mLlAddress.setVisibility(View.VISIBLE);
            mLlSubTime.setVisibility(View.VISIBLE);

            mTvOrderStatus.setText("新增待写");  //预约状态
            mTvOrderName.setText(detail2B.getGeneticBooking().getBookingClient());  //预约人
            mTvSex.setText(detail2B.getGeneticBooking().getUserSex());  //预约人性别
            mTvAge.setText(detail2B.getGeneticBooking().getUserAge());  //预约人年龄
            mTvGeneticTestName.setText(detail2B.getGeneticBooking().getName());  //基因 检测套餐
            mTvPhone.setText(detail2B.getGeneticBooking().getClientPhone()); //联系电话
            mTvGeneticTestAddress.setText(detail2B.getGeneticBooking().getUserAddress());  //基因 通讯地址
            mTvSubTime.setText("新增待写"); //提交时间

        } else if (detail2B.getGolfBooking() != null) {
            //是高尔夫
            mTvOrderService.setText("高尔夫球场地");

            mLlOrderName.setVisibility(View.VISIBLE);
            mLlIdNum.setVisibility(View.VISIBLE);
            mLlPhone.setVisibility(View.VISIBLE);
            mLlVenueName.setVisibility(View.VISIBLE);
            mLlOrderTime.setVisibility(View.VISIBLE);
            mLlSubTime.setVisibility(View.VISIBLE);

            mTvOrderName.setText(detail2B.getGolfBooking().getUserInfoName());  //预约人
            mTvOrderTime.setText(detail2B.getGolfBooking().getBookingTime()); //预约时间

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
