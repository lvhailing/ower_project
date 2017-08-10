package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.adapter.AssetInsuranceDetailAdapter;
import com.jdhui.bean.ResultAssetInsuranceProductDetailBean;
import com.jdhui.bean.mybean.BonusListBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.MyListView;


/**
 * 资产页保险收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetInsuranceDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_asset_insurance_name; // 保险的产品名称
    private TextView tv_asset_insurance_annual_report; // 年度报告
    private TextView tv_product_name; // 产品名称
    private TextView tv_insurance_product_type; // 产品类型
    private TextView tv_insurance_date; // 投报日期
    private TextView tv_insurance_period; // 保险期间
    private TextView tv_insurance_payment_period; // 缴费期间
    private TextView tv_insurance_sum_insured; // 保额
    private TextView tv_insurance_premium; // 保费
    private TextView tv_insurance_effective_date; // 生效日期
    private TextView tv_insurance_renewal_date; // 续费日期
    private TextView tv_insurance_applicant; // 投保人
    private TextView tv_insurance_insured_person; // 被保人
    private TextView tv_insurance_beneficiary; // 受益人
    private TextView tv_insurance_remarks; // 备注

    private String tenderId;
    private String productName;
    private ResultAssetInsuranceProductDetailBean assetFixedBean;
    private RelativeLayout ll_asset_insurance; //保险产品产品名称
    private MyListView myListView; //加载分红列表
    private ScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_insurance_detail);
        initView();
        initData();
    }

    public void initData() {
        requestAssetInsuranceDetail();
    }

    private void initView() {
        tenderId = getIntent().getStringExtra("tenderId");
        productName = getIntent().getStringExtra("productName");

        assetFixedBean = new ResultAssetInsuranceProductDetailBean();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_asset_insurance_name = (TextView) findViewById(R.id.tv_asset_insurance_name);
        tv_asset_insurance_annual_report = (TextView) findViewById(R.id.tv_asset_insurance_annual_report);
        tv_product_name = (TextView) findViewById(R.id.tv_product_name);
        tv_insurance_product_type = (TextView) findViewById(R.id.tv_insurance_product_type);
        tv_insurance_date = (TextView) findViewById(R.id.tv_insurance_date);
        tv_insurance_period = (TextView) findViewById(R.id.tv_insurance_period);
        tv_insurance_payment_period = (TextView) findViewById(R.id.tv_insurance_payment_period);
        tv_insurance_sum_insured = (TextView) findViewById(R.id.tv_insurance_sum_insured);
        tv_insurance_premium = (TextView) findViewById(R.id.tv_insurance_premium);
        tv_insurance_effective_date = (TextView) findViewById(R.id.tv_insurance_effective_date);
        tv_insurance_renewal_date = (TextView) findViewById(R.id.tv_insurance_renewal_date);
        tv_insurance_applicant = (TextView) findViewById(R.id.tv_insurance_applicant);
        tv_insurance_insured_person = (TextView) findViewById(R.id.tv_insurance_insured_person);
        tv_insurance_beneficiary = (TextView) findViewById(R.id.tv_insurance_beneficiary);
        tv_insurance_remarks = (TextView) findViewById(R.id.tv_insurance_remarks);
        ll_asset_insurance = (RelativeLayout) findViewById(R.id.ll_asset_insurance);
        myListView = (MyListView) findViewById(R.id.lv);
        scrollview = (ScrollView) findViewById(R.id.scrollview);

        iv_back.setOnClickListener(this);
        tv_asset_insurance_annual_report.setOnClickListener(this);
        ll_asset_insurance.setOnClickListener(this);
    }

    public void setView() {
        tv_asset_insurance_name.setText(productName);
        tv_product_name.setText(assetFixedBean.getProductName());

        //保险类型（healthInsurance:健康险;accidentInsurance:意外险;lifeInsurance:人寿险;propertyInsurance:财产险;travelInsurance:旅游险）

        String insuranceType = assetFixedBean.getType();
        if ("accidentInsurance".equals(insuranceType)) {
            tv_insurance_product_type.setText("意外险");
        } else if ("lifeInsurance".equals(insuranceType)) {
            tv_insurance_product_type.setText("人寿险");
        } else if ("propertyInsurance".equals(insuranceType)) {
            tv_insurance_product_type.setText("财产险");
        } else if ("travelInsurance".equals(insuranceType)) {
            tv_insurance_product_type.setText("旅游险");
        } else if ("healthInsurance".equals(insuranceType)) {
            tv_insurance_product_type.setText("健康险");
        }
//        tv_insurance_product_type.setText(assetFixedBean.getType());

        tv_insurance_date.setText(assetFixedBean.getInsuranceDate());
        tv_insurance_period.setText(assetFixedBean.getTimeLimit());
        tv_insurance_payment_period.setText(assetFixedBean.getPayLimit());
        tv_insurance_sum_insured.setText(assetFixedBean.getCoverageAmount());
        tv_insurance_premium.setText(StringUtil.formatNum(assetFixedBean.getPremiumAmount()) + "元");
        tv_insurance_effective_date.setText(assetFixedBean.getEffectiveDate());
        tv_insurance_renewal_date.setText(assetFixedBean.getRenewalDate());
        tv_insurance_applicant.setText(assetFixedBean.getPolicyholder() + "--" + assetFixedBean.getPolicyholderIdNo());
        tv_insurance_insured_person.setText(assetFixedBean.getInsured() + "--" + assetFixedBean.getInsuredIdNo());
        tv_insurance_beneficiary.setText(assetFixedBean.getBeneficiary());
        tv_insurance_remarks.setText(assetFixedBean.getRemark());


        MouldList<BonusListBean> bonusList = assetFixedBean.getBonusList();
        if (bonusList == null || bonusList.size() == 0) {
            bonusList.add(new BonusListBean("--", "--"));
        }

        //设置分红列表
        AssetInsuranceDetailAdapter adapter = new AssetInsuranceDetailAdapter(this, bonusList);
        myListView.setAdapter(adapter);
        scrollview.smoothScrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_asset_insurance_annual_report:      //年度报告
//                Intent i_web = new Intent();
//                i_web.setClass(this,WebActivity.class);
//                i_web.putExtra("id",assetFixedBean.getProductId());
//                i_web.putExtra("type",WebActivity.WEBTYPE_INSURANCE_DETAIL_DES);
//                i_web.putExtra("title","年度报告");
//                startActivity(i_web);

                break;
            case R.id.ll_asset_insurance:
                if (assetFixedBean != null) {
                    Intent i_insuranceProductDetail = new Intent();
                    i_insuranceProductDetail.setClass(AssetInsuranceDetailActivity.this, InsuranceProductDetailActivity.class);
                    i_insuranceProductDetail.putExtra("productId", assetFixedBean.getProductId());
                    this.startActivity(i_insuranceProductDetail);
                }

                break;

        }
    }

    private void requestAssetInsuranceDetail() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HtmlRequest.getInsuranceAssetProductDetail(this, userId, tenderId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    assetFixedBean = (ResultAssetInsuranceProductDetailBean) params.result;
                    if (assetFixedBean != null) {
                        setView();
                    }
                }
            }
        });

    }


}
