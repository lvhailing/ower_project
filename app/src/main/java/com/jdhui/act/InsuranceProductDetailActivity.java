package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultInsuranceProductDetailBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 *
 * 保险产品详情
 * Created by hasee on 2016/8/11.
 */
public class InsuranceProductDetailActivity extends BaseActivity implements View.OnClickListener{



    private ImageView id_img_back;
    private ImageView iv_insurance_product_title_pic;

    private TextView tv_insurance_product_detail_name;

    private RelativeLayout rl_insurance_product_detail_des;
    private TextView tv_insurance_product_detail_type;              //保险类型
    private TextView tv_insurance_product_detail_des;               //保险简介
    private TextView tv_insurance_product_detail_baoxiangongsi;               //保险公司
    private TextView tv_insurance_product_detail_toubaofangshi;               //投保方式
    private TextView tv_insurance_product_detail_toubaofanwei;               //投保范围
    private TextView tv_insurance_product_detail_baoxianqijian;               //保险期间
    private TextView tv_insurance_product_detail_jiaofeifangshi;               //交费方式
    private TextView tv_insurance_product_detail_fengxiantixing;               //风险提醒

    private LinearLayout ll_insurance_product_detail_toubaofangshi;
    private LinearLayout ll_insurance_product_detail_fengxiantixing;
    private String productId = null;
    private ResultInsuranceProductDetailBean insuranceDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_insurance_product_detail);
        initView();

    }

    public void initView(){

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        productId = getIntent().getStringExtra("productId");
        id_img_back = (ImageView) findViewById(R.id.id_img_back);
        iv_insurance_product_title_pic = (ImageView) findViewById(R.id.iv_insurance_product_title_pic);

        rl_insurance_product_detail_des = (RelativeLayout) findViewById(R.id.rl_insurance_product_detail_des);

        tv_insurance_product_detail_name = (TextView) findViewById(R.id.tv_insurance_product_detail_name);
        tv_insurance_product_detail_type = (TextView) findViewById(R.id.tv_insurance_product_detail_type);
        tv_insurance_product_detail_des = (TextView) findViewById(R.id.tv_insurance_product_detail_des);
        tv_insurance_product_detail_baoxiangongsi = (TextView) findViewById(R.id.tv_insurance_product_detail_baoxiangongsi);
        tv_insurance_product_detail_toubaofangshi = (TextView) findViewById(R.id.tv_insurance_product_detail_toubaofangshi);
        tv_insurance_product_detail_toubaofanwei = (TextView) findViewById(R.id.tv_insurance_product_detail_toubaofanwei);
        tv_insurance_product_detail_baoxianqijian = (TextView) findViewById(R.id.tv_insurance_product_detail_baoxianqijian);
        tv_insurance_product_detail_jiaofeifangshi = (TextView) findViewById(R.id.tv_insurance_product_detail_jiaofeifangshi);
        tv_insurance_product_detail_fengxiantixing = (TextView) findViewById(R.id.tv_insurance_product_detail_fengxiantixing);
        ll_insurance_product_detail_toubaofangshi = (LinearLayout) findViewById(R.id.ll_insurance_product_detail_toubaofangshi);
        ll_insurance_product_detail_fengxiantixing = (LinearLayout) findViewById(R.id.ll_insurance_product_detail_fengxiantixing);
//        ll_insurance_product_detail_toubaofangshi.setVisibility(View.GONE);
//        ll_insurance_product_detail_fengxiantixing.setVisibility(View.GONE);

        rl_insurance_product_detail_des.setOnClickListener(this);
        id_img_back.setOnClickListener(this);


        requestInsuranceDetail();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_img_back:
                finish();

                break;
            case R.id.rl_insurance_product_detail_des:      //图文详情
                Intent i_web = new Intent();
                i_web.setClass(this,WebActivity.class);
                i_web.putExtra("id",productId);
                i_web.putExtra("type",WebActivity.WEBTYPE_INSURANCE_DETAIL_DES);
                i_web.putExtra("title","图文详情");
                startActivity(i_web);
                break;

        }
    }

    private void requestInsuranceDetail(){

        HtmlRequest.getInsuranceProductDetail(this, productId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){
                    insuranceDetailBean = (ResultInsuranceProductDetailBean)params.result;
                    if(insuranceDetailBean!=null){
                        setView();
                    }


                }

            }
        });

    }
    public void setView(){

        tv_insurance_product_detail_name.setText(insuranceDetailBean.getProductName());

        if("accidentInsurance".equals(insuranceDetailBean.getType())){
            tv_insurance_product_detail_type.setText("保险类型："+"意外险");
        }else if("lifeInsurance".equals(insuranceDetailBean.getType())){
            tv_insurance_product_detail_type.setText("保险类型："+"人寿险");
        }else if("propertyInsurance".equals(insuranceDetailBean.getType())){
            tv_insurance_product_detail_type.setText("保险类型："+"财产险");
        }else if("travelInsurance".equals(insuranceDetailBean.getType())){
            tv_insurance_product_detail_type.setText("保险类型："+"旅游险");
        }else if("healthInsurance".equals(insuranceDetailBean.getType())){
            tv_insurance_product_detail_type.setText("保险类型："+"健康险");
        }


        tv_insurance_product_detail_des.setText(insuranceDetailBean.getRecommendations());
        tv_insurance_product_detail_baoxiangongsi.setText(insuranceDetailBean.getCompanyName());
        if(TextUtils.isEmpty(insuranceDetailBean.getGuaranteeType())){
            ll_insurance_product_detail_toubaofangshi.setVisibility(View.GONE);
        }else{
            ll_insurance_product_detail_toubaofangshi.setVisibility(View.VISIBLE);
            tv_insurance_product_detail_toubaofangshi.setText(insuranceDetailBean.getGuaranteeType());
        }


        tv_insurance_product_detail_toubaofanwei.setText(insuranceDetailBean.getInsuranceCoverage());
        tv_insurance_product_detail_baoxianqijian.setText(insuranceDetailBean.getTimeLimit());
        tv_insurance_product_detail_jiaofeifangshi.setText(insuranceDetailBean.getPayType());
        if(TextUtils.isEmpty(insuranceDetailBean.getRiskTips())){
            ll_insurance_product_detail_fengxiantixing.setVisibility(View.GONE);
        }else{
            ll_insurance_product_detail_fengxiantixing.setVisibility(View.VISIBLE);
            tv_insurance_product_detail_fengxiantixing.setText(insuranceDetailBean.getRiskTips());
        }


        ImageLoader.getInstance().displayImage(insuranceDetailBean.getAdvertisePictue(),iv_insurance_product_title_pic);
    }

}
