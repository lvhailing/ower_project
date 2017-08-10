package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.adapter.AssetFixedDetailAdapter;
import com.jdhui.bean.ResultAssetFixedProductDetailBean;
import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.MyListView;

/**
 * 资产--固定收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetFixedDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_fixed_annual_report; //年度报告
    private TextView tv_asset_fixed_title; //产品标题
    private TextView tv_fixed_product_name; //产品名称
    private TextView tv_fixed_purchase_amount; //购买金额
    private TextView tv_fixed_performance_benchmark; //预计收益
    private TextView tv_fixed_product_deadline;  //产品期限
    private TextView tv_fixed_purchase_date; //购买日期
    private TextView tv_fixed_register_date; //成立日期
    private TextView tv_fixed_interest_interval; //付息间隔
    private TextView tv_fixed_remarks; //备注

    private ImageView iv_back;
    private String tenderId;
    private String productName;
    private ResultAssetFixedProductDetailBean assetFixedBean;
    private RelativeLayout ll_asset_fixed; //固收产品名称
    private MyListView myListView; //加载还款方案的列表
    private LinearLayout ll_asset_fixed_unitnet; //产品净值 布局（默认不可见）
    private TextView tv_asset_fixed_unitnet;//产品净值
    private ScrollView sv_asset_fixed_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_fixed_detail);

        initView();
        initData();
    }

    private void initData() {
        requestAssetFixedDetail();
    }

    private void initView() {
        tenderId = getIntent().getStringExtra("tenderId");
        productName = getIntent().getStringExtra("productName");

        assetFixedBean = new ResultAssetFixedProductDetailBean();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_fixed_annual_report = (TextView) findViewById(R.id.tv_fixed_annual_report);
        tv_asset_fixed_title = (TextView) findViewById(R.id.tv_asset_fixed_title);
        tv_fixed_product_name = (TextView) findViewById(R.id.tv_fixed_product_name);
        tv_fixed_purchase_amount = (TextView) findViewById(R.id.tv_fixed_purchase_amount);
        tv_fixed_performance_benchmark = (TextView) findViewById(R.id.tv_fixed_performance_benchmark);
        tv_fixed_product_deadline = (TextView) findViewById(R.id.tv_fixed_product_deadline);
        tv_fixed_purchase_date = (TextView) findViewById(R.id.tv_fixed_purchase_date);
        tv_fixed_register_date = (TextView) findViewById(R.id.tv_fixed_register_date);
        tv_fixed_interest_interval = (TextView) findViewById(R.id.tv_fixed_interest_interval);
        tv_fixed_remarks = (TextView) findViewById(R.id.tv_fixed_remarks);
        tv_asset_fixed_unitnet = (TextView) findViewById(R.id.tv_asset_fixed_unitnet);
        ll_asset_fixed_unitnet = (LinearLayout) findViewById(R.id.ll_asset_fixed_unitnet);
        ll_asset_fixed = (RelativeLayout) findViewById(R.id.ll_asset_fixed);
        myListView = (MyListView) findViewById(R.id.lv);
        sv_asset_fixed_detail = (ScrollView) findViewById(R.id.sv_asset_fixed_detail);

        iv_back.setOnClickListener(this);
        tv_fixed_annual_report.setOnClickListener(this);
        ll_asset_fixed.setOnClickListener(this);
    }

    public void setView() {
        tv_asset_fixed_title.setText(productName);
        tv_fixed_product_name.setText(assetFixedBean.getProductName());
        tv_fixed_purchase_amount.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tv_fixed_performance_benchmark.setText(assetFixedBean.getAnnualRate());
        tv_fixed_product_deadline.setText(assetFixedBean.getTimeLimit());
        tv_fixed_purchase_date.setText(assetFixedBean.getPurchaseDate());
        tv_fixed_register_date.setText(assetFixedBean.getEstablishmentDate());

        String repayType = assetFixedBean.getRepayType();
        if (repayType.equals("monthPayment")) {
            tv_fixed_interest_interval.setText("按月付息，到期还本");
        } else if (repayType.equals("quarterPayment")) {
            tv_fixed_interest_interval.setText("按季付息，到期还本");
        } else if (repayType.equals("halfYearPayment")) {
            tv_fixed_interest_interval.setText("按半年付息，到期还本");
        } else if (repayType.equals("yearPayment")) {
            tv_fixed_interest_interval.setText("按年付息，到期还本");
        } else if (repayType.equals("oneTimePayment")) {
            tv_fixed_interest_interval.setText("一次性还本息");
        }

        //产品净值的判断 （后台返回“无”时不显示）
        String unitNet = assetFixedBean.getUnitNet();
        if (unitNet.equals("无")) {
            ll_asset_fixed_unitnet.setVisibility(View.GONE);
        } else {
//            ll_asset_fixed_unitnet.setVisibility(View.VISIBLE);
            tv_asset_fixed_unitnet.setText(unitNet);
        }

        String remark = assetFixedBean.getRemark();
        if (remark.equals("无")) {
            tv_fixed_remarks.setText("暂无备注");
        } else {
            tv_fixed_remarks.setText(remark);
        }

        if (assetFixedBean.getIsAnnualReport().equals("yes")) { // 是否有年度报告	 yes:有;  no:无
            tv_fixed_annual_report.setVisibility(View.VISIBLE);
        } else {
            tv_fixed_annual_report.setClickable(false);
            tv_fixed_annual_report.setVisibility(View.GONE);
        }

        MouldList<InterestListBean> interestList = assetFixedBean.getInterestList();
        if (interestList == null || interestList.size() == 0) {
            interestList.add(new InterestListBean("--", "--", "--"));
        }

//        MouldList<InterestListBean> interestList = new MouldList<>();
//        interestList.add(new InterestListBean("2016/11/11 19:00","1000","1000"));
//        interestList.add(new InterestListBean("2016/11/11","1000","1000"));
//        interestList.add(new InterestListBean("2016/11/11","1000","1000"));
//        interestList.add(new InterestListBean("2016/11/11","1000","1000"));
//        interestList.add(new InterestListBean("2016/11/11","1000","1000"));

        //设置还款方案
        AssetFixedDetailAdapter adapter = new AssetFixedDetailAdapter(this, interestList);
        myListView.setAdapter(adapter);
        sv_asset_fixed_detail.smoothScrollTo(0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fixed_annual_report://年度报告
                Intent i_web = new Intent();
                i_web.setClass(this, WebActivity.class);
                i_web.putExtra("id", assetFixedBean.getProductId());
                i_web.putExtra("type", WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title", "年度报告");
                startActivity(i_web);

                break;
            case R.id.ll_asset_fixed: // 固收产品名称
                if (assetFixedBean != null) {
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(AssetFixedDetailActivity.this, FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId", assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type", "optimum");
                    this.startActivity(i_fixedProductDetail);
                    break;
                }

                break;
        }
    }

    /**
     * 获取非保险投资产品详情数据
     */
    private void requestAssetFixedDetail() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HtmlRequest.getAssetProductDetail(this, userId, tenderId, "optimum", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    assetFixedBean = (ResultAssetFixedProductDetailBean) params.result;
                    if (assetFixedBean != null) {
                        setView();
                    }
                }
            }
        });
    }

}
