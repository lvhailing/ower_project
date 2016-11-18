package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.FixedProductDetailActivity;
import com.jdhui.act.WebActivity;
import com.jdhui.bean.ResultAssetFixedProductDetailBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

/**
 * 产品预约--预约详情
 */
public class ProOrderDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTvTitle;
    private TextView mTvtype;
    private TextView mTvOrderName;
    private TextView mTvPhone;
    private TextView mTvIDNum;
    private TextView mTvOrderStatus;
    private TextView mTvOrderTime;

    private ImageView mIvBack;
    private String tenderId;
    private ResultAssetFixedProductDetailBean assetFixedBean;
    private RelativeLayout ll_asset_fixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_pro_order_detail);
        initView();
        initData();
    }
    private void initData(){
        requestAssetFixedDetail();
    }

    private void initView(){
//        tenderId = getIntent().getStringExtra("tenderId");
//        productName = getIntent().getStringExtra("productName");

        assetFixedBean = new ResultAssetFixedProductDetailBean();

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvtype = (TextView) findViewById(R.id.tv_type);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvIDNum = (TextView) findViewById(R.id.tv_ID_num);
        mTvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        mTvOrderTime = (TextView) findViewById(R.id.tv_order_time);
        ll_asset_fixed = (RelativeLayout) findViewById(R.id.ll_asset_fixed);

        mIvBack.setOnClickListener(this);
        ll_asset_fixed.setOnClickListener(this);
    }

    public void setView(){

/*
        tv_asset_fixed_title.setText(productName);
        tv_asset_fixed_name.setText(assetFixedBean.getProductName());
        tv_asset_fixed_goumaijine.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tv_asset_fixed_yujishouyi.setText(assetFixedBean.getAnnualRate());
        tv_asset_fixed_chanpinqixian.setText(assetFixedBean.getTimeLimit());
        tv_asset_fixed_goumairiqi.setText(assetFixedBean.getPurchaseDate());
        tv_asset_fixed_chengliriqi.setText(assetFixedBean.getEstablishmentDate());
        tv_asset_fixed_fuxinjiange.setText(assetFixedBean.getRepayType());
        tv_asset_fixed_beizhu.setText(assetFixedBean.getRemark());

        if(assetFixedBean.getIsAnnualReport().equals("yes")){           // 是否有年度报告		yes:有;no:无
            tv_asset_fixed_call.setVisibility(View.VISIBLE);
        }else{
            tv_asset_fixed_call.setClickable(false);
            tv_asset_fixed_call.setVisibility(View.GONE);
        }*/
    }


    @Override
    protected void onStop() {
        super.onStop();
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
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_asset_fixed_call:              //年度报告
//                finish();
                Intent i_web = new Intent();
                i_web.setClass(this,WebActivity.class);
                i_web.putExtra("id",assetFixedBean.getProductId());
                i_web.putExtra("type",WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title","年度报告");
                startActivity(i_web);

                break;
            case R.id.ll_asset_fixed:
                if(assetFixedBean!=null){
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(ProOrderDetailActivity.this,FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId",assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type","optimum");
                    this.startActivity(i_fixedProductDetail);
                }

                break;
        }
    }

    private void requestAssetFixedDetail(){
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }


        HtmlRequest.getAssetProductDetail(this, userId,tenderId,"optimum" ,new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){
                    assetFixedBean = (ResultAssetFixedProductDetailBean)params.result;
                    if(assetFixedBean!=null){
                        setView();
                    }

                }

            }
        });

    }

}
