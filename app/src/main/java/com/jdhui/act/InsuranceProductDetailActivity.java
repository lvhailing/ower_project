package com.jdhui.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultInsuranceProductDetailBean;
import com.jdhui.bean.mybean.BookingInsurance2B;
import com.jdhui.dialog.BookingDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 资产--保险产品详情（产品信息）
 * Created by hasee on 2016/8/11.
 */
public class InsuranceProductDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageView iv_insurance_product; //顶部展示的图片
    private TextView tv_insurance_product_detail_name; //保险产品名
    private RelativeLayout rl_insurance_product_detail_des; //图文详情
    private TextView tv_insurance_product_detail_type; //保险类型
    private TextView tv_insurance_product_detail_des; //保险简介
    private TextView tv_insurance_company_name; //保险公司
    private TextView tv_insuring_way; //投保方式
    private TextView tv_insurance_coverage;  //投保范围
    private TextView tv_insurance_period; //保险期间
    private TextView tv_insurance_payment_methods; //交费方式
    private TextView tv_insurance_risk_warning; //风险提醒

    private LinearLayout ll_insurance_insuring_way;
    private LinearLayout ll_insurance_risk_warning;
    private String productId = null;
    private ResultInsuranceProductDetailBean insuranceDetailBean;
    private Button btn_order; //立即预约
    private String insuranceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_insurance_product_detail);
        initView();

    }

    public void initView() {
        productId = getIntent().getStringExtra("productId");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_insurance_product = (ImageView) findViewById(R.id.iv_insurance_product);

        rl_insurance_product_detail_des = (RelativeLayout) findViewById(R.id.rl_insurance_product_detail_des);

        tv_insurance_product_detail_name = (TextView) findViewById(R.id.tv_insurance_product_detail_name);
        tv_insurance_product_detail_type = (TextView) findViewById(R.id.tv_insurance_product_detail_type);
        tv_insurance_product_detail_des = (TextView) findViewById(R.id.tv_insurance_product_detail_des);
        tv_insurance_company_name = (TextView) findViewById(R.id.tv_insurance_company_name);
        tv_insuring_way = (TextView) findViewById(R.id.tv_insuring_way);
        tv_insurance_coverage = (TextView) findViewById(R.id.tv_insurance_coverage);
        tv_insurance_period = (TextView) findViewById(R.id.tv_insurance_period);
        tv_insurance_payment_methods = (TextView) findViewById(R.id.tv_insurance_payment_methods);
        tv_insurance_risk_warning = (TextView) findViewById(R.id.tv_insurance_risk_warning);
        ll_insurance_insuring_way = (LinearLayout) findViewById(R.id.ll_insurance_insuring_way);
        ll_insurance_risk_warning = (LinearLayout) findViewById(R.id.ll_insurance_risk_warning);
        btn_order = (Button) findViewById(R.id.btn_order);


//        ll_insurance_insuring_way.setVisibility(View.GONE);
//        ll_insurance_risk_warning.setVisibility(View.GONE);

        rl_insurance_product_detail_des.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_order.setOnClickListener(this);


        requestInsuranceDetail();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_insurance_product_detail_des: // 图文详情
                Intent i_web = new Intent();
                i_web.setClass(this, WebActivity.class);
                i_web.putExtra("id", productId);
                i_web.putExtra("type", WebActivity.WEBTYPE_INSURANCE_DETAIL_DES);
                i_web.putExtra("title", "图文详情");
                startActivity(i_web);
                break;
            case R.id.btn_order: // 立即预约
                showDialog();
                break;

        }
    }

    private void requestInsuranceDetail() {

        HtmlRequest.getInsuranceProductDetail(this, productId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    insuranceDetailBean = (ResultInsuranceProductDetailBean) params.result;
                    if (insuranceDetailBean != null) {
                        setView();
                    }
                }

            }
        });

    }

    public void setView() {
        insuranceName = insuranceDetailBean.getProductName();
        tv_insurance_product_detail_name.setText(insuranceName);

        if ("accidentInsurance".equals(insuranceDetailBean.getType())) {
            tv_insurance_product_detail_type.setText("保险类型：意外险");
        } else if ("lifeInsurance".equals(insuranceDetailBean.getType())) {
            tv_insurance_product_detail_type.setText("保险类型：人寿险");
        } else if ("propertyInsurance".equals(insuranceDetailBean.getType())) {
            tv_insurance_product_detail_type.setText("保险类型：财产险");
        } else if ("travelInsurance".equals(insuranceDetailBean.getType())) {
            tv_insurance_product_detail_type.setText("保险类型：旅游险");
        } else if ("healthInsurance".equals(insuranceDetailBean.getType())) {
            tv_insurance_product_detail_type.setText("保险类型：健康险");
        }


        tv_insurance_product_detail_des.setText(insuranceDetailBean.getRecommendations());
        tv_insurance_company_name.setText(insuranceDetailBean.getCompanyName());
        if (TextUtils.isEmpty(insuranceDetailBean.getGuaranteeType())) {
            ll_insurance_insuring_way.setVisibility(View.GONE);
        } else {
            ll_insurance_insuring_way.setVisibility(View.VISIBLE);
            tv_insuring_way.setText(insuranceDetailBean.getGuaranteeType());
        }

        tv_insurance_coverage.setText(insuranceDetailBean.getInsuranceCoverage());
        tv_insurance_period.setText(insuranceDetailBean.getTimeLimit());
        tv_insurance_payment_methods.setText(insuranceDetailBean.getPayType());
        if (TextUtils.isEmpty(insuranceDetailBean.getRiskTips())) {
            ll_insurance_risk_warning.setVisibility(View.GONE);
        } else {
            ll_insurance_risk_warning.setVisibility(View.VISIBLE);
            tv_insurance_risk_warning.setText(insuranceDetailBean.getRiskTips());
        }
        ImageLoader.getInstance().displayImage(insuranceDetailBean.getAdvertisePictue(), iv_insurance_product);
    }

    //产品预约对话框
    private void showDialog() {
        BookingDialog dialog = new BookingDialog(this, insuranceName, new BookingDialog.MyCallback() {
            @Override
            public void onMyclick(Dialog ad, String money, String remarks) {
                requestData(money, remarks, ad);
                ad = null;
            }
        });
        dialog.subBookingDialog();
    }

    private void requestData(String money, String remarks, final Dialog ad) {
        try {
            String userInfoId = DESUtil.decrypt(PreferenceUtil.getUserId());
            HtmlRequest.subBookingInsurance(this, productId, userInfoId, remarks, money, new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    if (params != null) {
                        BookingInsurance2B insurance2B = (BookingInsurance2B) params.result;
                        if (insurance2B == null) {
                            Toast.makeText(InsuranceProductDetailActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!Boolean.parseBoolean(insurance2B.getMessage())) {
                            Toast.makeText(InsuranceProductDetailActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            ad.dismiss();
                        } else {
                            Toast.makeText(InsuranceProductDetailActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
