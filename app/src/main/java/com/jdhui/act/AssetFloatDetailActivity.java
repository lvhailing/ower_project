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
 * 资产--浮动收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetFloatDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_asset_float_title; // 产品标题
    private TextView tv_asset_float_call; // 年度报告
    private TextView tv_asset_float_name; // 产品名称
    private TextView tv_float_purchase_amount; // 购买金额
    private TextView tv_float_performance_benchmark; // 预计收益
    private TextView tv_float_product_deadline; // 产品期限
    private TextView tv_float_purchase_date; // 购买日期
    private TextView tv_float_register_date; // 成立日期
    private TextView tv_asset_float_interest_interval; // 付息间隔
    private TextView tv_float_remarks; // 备注
    private RelativeLayout ll_asset_float;  // 浮收产品名称

    private String tenderId;
    private String productName;
    private ResultAssetFixedProductDetailBean assetFixedBean;
    private MyListView myListView; //加载还款方案的列表
    private LinearLayout ll_asset_float_unitnet; //产品净值  布局
    private TextView tv_asset_float_unitnet; //产品净值
    private ScrollView sv_asset_float_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_float_detail);
        initView();
        initData();
    }

    private void initView() {
        tenderId = getIntent().getStringExtra("tenderId");
        productName = getIntent().getStringExtra("productName");

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_asset_float_title = (TextView) findViewById(R.id.tv_asset_float_title);
        tv_asset_float_call = (TextView) findViewById(R.id.tv_asset_float_call);
        tv_asset_float_name = (TextView) findViewById(R.id.tv_asset_float_name);
        tv_float_purchase_amount = (TextView) findViewById(R.id.tv_float_purchase_amount);
        tv_float_performance_benchmark = (TextView) findViewById(R.id.tv_float_performance_benchmark);
        tv_float_product_deadline = (TextView) findViewById(R.id.tv_float_product_deadline);
        tv_float_purchase_date = (TextView) findViewById(R.id.tv_float_purchase_date);
        tv_float_register_date = (TextView) findViewById(R.id.tv_float_register_date);
        tv_asset_float_interest_interval = (TextView) findViewById(R.id.tv_asset_float_interest_interval);
        tv_float_remarks = (TextView) findViewById(R.id.tv_float_remarks);
        tv_asset_float_unitnet = (TextView) findViewById(R.id.tv_asset_float_unitnet);
        ll_asset_float = (RelativeLayout) findViewById(R.id.ll_asset_float);
        ll_asset_float_unitnet = (LinearLayout) findViewById(R.id.ll_asset_float_unitnet);
        myListView = (MyListView) findViewById(R.id.lv);
        sv_asset_float_detail = (ScrollView) findViewById(R.id.scrollview);

        iv_back.setOnClickListener(this);
        tv_asset_float_call.setOnClickListener(this);
        ll_asset_float.setOnClickListener(this);
    }

    private void initData() {
        requestAssetFixedDetail();
    }

    public void setView() {
        tv_asset_float_title.setText(productName);
        tv_asset_float_name.setText(assetFixedBean.getProductName());
        tv_float_purchase_amount.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tv_float_performance_benchmark.setText(assetFixedBean.getAnnualRate());
        tv_float_product_deadline.setText(assetFixedBean.getTimeLimit());
        tv_float_purchase_date.setText(assetFixedBean.getPurchaseDate());
        tv_float_register_date.setText(assetFixedBean.getEstablishmentDate());

        String remark = assetFixedBean.getRemark();
        if (remark.equals("无")) {
            tv_float_remarks.setText("暂无备注");
        } else {
            tv_float_remarks.setText(remark);
        }

        String repayType = assetFixedBean.getRepayType();
        if (repayType.equals("monthPayment")) {
            tv_asset_float_interest_interval.setText("按月付息，到期还本");
        } else if (repayType.equals("quarterPayment")) {
            tv_asset_float_interest_interval.setText("按季付息，到期还本");
        } else if (repayType.equals("halfYearPayment")) {
            tv_asset_float_interest_interval.setText("按半年付息，到期还本");
        } else if (repayType.equals("yearPayment")) {
            tv_asset_float_interest_interval.setText("按年付息，到期还本");
        } else if (repayType.equals("oneTimePayment")) {
            tv_asset_float_interest_interval.setText("一次性还本息");
        }

        //产品净值的判断 （后台返回“无”时不显示）
        String unitNet = assetFixedBean.getUnitNet();
        if (unitNet.equals("无")) {
            ll_asset_float_unitnet.setVisibility(View.GONE);
        } else {
            tv_asset_float_unitnet.setText(unitNet);
        }

        if (assetFixedBean.getIsAnnualReport().equals("yes")) { // 是否有年度报告	 yes:有;  no:无
            tv_asset_float_call.setVisibility(View.VISIBLE);
            tv_asset_float_call.setClickable(true);
        } else {
            tv_asset_float_call.setVisibility(View.GONE);
            tv_asset_float_call.setClickable(false);
        }

        MouldList<InterestListBean> interestList = assetFixedBean.getInterestList();
        if (interestList == null || interestList.size() == 0) {
            interestList.add(new InterestListBean("--", "--", "--"));
        }

        //设置还款方案
        AssetFixedDetailAdapter adapter = new AssetFixedDetailAdapter(this, interestList);
        myListView.setAdapter(adapter);
        sv_asset_float_detail.smoothScrollTo(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_asset_float_call: //年度报告 按钮
                Intent i_web = new Intent();
                i_web.setClass(this, WebActivity.class);
                i_web.putExtra("id", assetFixedBean.getProductId());
                i_web.putExtra("type", WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title", "年度报告");
                startActivity(i_web);
                break;
            case R.id.ll_asset_float: //浮收产品名称
                if (assetFixedBean != null) {
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(AssetFloatDetailActivity.this, FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId", assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type", "floating");
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

        HtmlRequest.getAssetProductDetail(this, userId, tenderId, "floating", new BaseRequester.OnRequestListener() {
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
