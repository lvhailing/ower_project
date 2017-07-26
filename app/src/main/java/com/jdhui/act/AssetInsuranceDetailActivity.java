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
    private TextView tvAssetInsuranceNumber;
    private TextView tvAssetInsuranceCall;
    private TextView tvAssetInsuranceName;
    private TextView tvAssetInsuranceChanpinleixing;
    private TextView tvAssetInsuranceToubaoriqi;
    private TextView tvAssetInsuranceBaoxianqijian;
    private TextView tvAssetInsuranceJiaofeiqijian;
    private TextView tvAssetInsuranceBaoe;
    private TextView tvAssetInsuranceBaofei;
    private TextView tvAssetInsuranceShengxiaoriqi;
    private TextView tvAssetInsuranceJiaofeiriqi;
    private TextView tvAssetInsuranceToubaoren;
    private TextView tvAssetInsuranceBeibaoren;
    private TextView tvAssetInsuranceShouyiren;
    private TextView tvAssetInsuranceBeizhu;

    private String tenderId;
    private String productName;
    private ResultAssetInsuranceProductDetailBean assetFixedBean;
    private RelativeLayout ll_asset_insurance; //保险产品产品名称
    private MyListView myListView; //加载分红列表
    private ScrollView sv_asset_insurance_detail;

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
        assignViews();

    }

    private void assignViews() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tvAssetInsuranceNumber = (TextView) findViewById(R.id.tv_asset_insurance_number);
        tvAssetInsuranceCall = (TextView) findViewById(R.id.tv_asset_insurance_call);
        tvAssetInsuranceName = (TextView) findViewById(R.id.tv_asset_insurance_name);
        tvAssetInsuranceChanpinleixing = (TextView) findViewById(R.id.tv_asset_insurance_chanpinleixing);
        tvAssetInsuranceToubaoriqi = (TextView) findViewById(R.id.tv_asset_insurance_toubaoriqi);
        tvAssetInsuranceBaoxianqijian = (TextView) findViewById(R.id.tv_asset_insurance_baoxianqijian);
        tvAssetInsuranceJiaofeiqijian = (TextView) findViewById(R.id.tv_asset_insurance_jiaofeiqijian);
        tvAssetInsuranceBaoe = (TextView) findViewById(R.id.tv_asset_insurance_baoe);
        tvAssetInsuranceBaofei = (TextView) findViewById(R.id.tv_asset_insurance_baofei);
        tvAssetInsuranceShengxiaoriqi = (TextView) findViewById(R.id.tv_asset_insurance_shengxiaoriqi);
        tvAssetInsuranceJiaofeiriqi = (TextView) findViewById(R.id.tv_asset_insurance_jiaofeiriqi);
        tvAssetInsuranceToubaoren = (TextView) findViewById(R.id.tv_asset_insurance_toubaoren);
        tvAssetInsuranceBeibaoren = (TextView) findViewById(R.id.tv_asset_insurance_beibaoren);
        tvAssetInsuranceShouyiren = (TextView) findViewById(R.id.tv_asset_insurance_shouyiren);
        tvAssetInsuranceBeizhu = (TextView) findViewById(R.id.tv_asset_insurance_beizhu);
        ll_asset_insurance = (RelativeLayout) findViewById(R.id.ll_asset_insurance);
        myListView = (MyListView) findViewById(R.id.lv);
        sv_asset_insurance_detail = (ScrollView) findViewById(R.id.sv_asset_insurance_detail);

        iv_back.setOnClickListener(this);
        tvAssetInsuranceCall.setOnClickListener(this);
        ll_asset_insurance.setOnClickListener(this);

    }

    public void setView() {
        tvAssetInsuranceNumber.setText(productName);
        tvAssetInsuranceName.setText(assetFixedBean.getProductName());

        //保险类型（healthInsurance:健康险;accidentInsurance:意外险;lifeInsurance:人寿险;propertyInsurance:财产险;travelInsurance:旅游险）

        if ("accidentInsurance".equals(assetFixedBean.getType())) {
            tvAssetInsuranceChanpinleixing.setText("意外险");
        } else if ("lifeInsurance".equals(assetFixedBean.getType())) {
            tvAssetInsuranceChanpinleixing.setText("人寿险");
        } else if ("propertyInsurance".equals(assetFixedBean.getType())) {
            tvAssetInsuranceChanpinleixing.setText("财产险");
        } else if ("travelInsurance".equals(assetFixedBean.getType())) {
            tvAssetInsuranceChanpinleixing.setText("旅游险");
        } else if ("healthInsurance".equals(assetFixedBean.getType())) {
            tvAssetInsuranceChanpinleixing.setText("健康险");
        }
//        tvAssetInsuranceChanpinleixing.setText(assetFixedBean.getType());

        tvAssetInsuranceToubaoriqi.setText(assetFixedBean.getInsuranceDate());
        tvAssetInsuranceBaoxianqijian.setText(assetFixedBean.getTimeLimit());
        tvAssetInsuranceJiaofeiqijian.setText(assetFixedBean.getPayLimit());
        tvAssetInsuranceBaoe.setText(assetFixedBean.getCoverageAmount());
        tvAssetInsuranceBaofei.setText(StringUtil.formatNum(assetFixedBean.getPremiumAmount()) + "元");
        tvAssetInsuranceShengxiaoriqi.setText(assetFixedBean.getEffectiveDate());
        tvAssetInsuranceJiaofeiriqi.setText(assetFixedBean.getRenewalDate());
        tvAssetInsuranceToubaoren.setText(assetFixedBean.getPolicyholder() + "--" + assetFixedBean.getPolicyholderIdNo());
        tvAssetInsuranceBeibaoren.setText(assetFixedBean.getInsured() + "--" + assetFixedBean.getInsuredIdNo());
        tvAssetInsuranceShouyiren.setText(assetFixedBean.getBeneficiary());
        tvAssetInsuranceBeizhu.setText(assetFixedBean.getRemark());


        MouldList<BonusListBean> bonusList = assetFixedBean.getBonusList();
        if (bonusList == null || bonusList.size() == 0) {
            bonusList.add(new BonusListBean("--", "--"));
        }

        //设置分红列表
        AssetInsuranceDetailAdapter adapter = new AssetInsuranceDetailAdapter(this, bonusList);
        myListView.setAdapter(adapter);
        sv_asset_insurance_detail.smoothScrollTo(0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_asset_insurance_call:      //年度报告
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
