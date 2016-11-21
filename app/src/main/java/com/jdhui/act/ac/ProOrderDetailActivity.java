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
import com.jdhui.bean.ResultAssetInsuranceProductDetailBean;
import com.jdhui.bean.mybean.ProductDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 产品预约--预约详情
 */
public class ProOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlProName;
    private TextView mTvTitle;
    private TextView mTvtype;
    private TextView mTvOrderName;
    private TextView mTvPhone;
    private TextView mTvIDNum;
    private TextView mTvOrderStatus;

    private TextView mTvOrderTime;
    private ImageView mIvBack;
    private String userInfoId;
    private String category;
    private String status;
    private ProductDetail2B ProDetail2B;
    private TextView mTvProName;
    private String proName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_pro_order_detail);
        initView();
        initData();
    }

    private void initData() {
        requestProOrderDetailData();
    }

    private void initView() {
        userInfoId = getIntent().getStringExtra("userInfoId");
        proName = getIntent().getStringExtra("ProductName");
        category = getIntent().getStringExtra("category");
        status = getIntent().getStringExtra("status");


        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mRlProName = (RelativeLayout) findViewById(R.id.rl_pro_name);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvProName = (TextView) findViewById(R.id.tv_pro_name);
        mTvtype = (TextView) findViewById(R.id.tv_type);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvIDNum = (TextView) findViewById(R.id.tv_ID_num);
        mTvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        mTvOrderTime = (TextView) findViewById(R.id.tv_order_time);

        mIvBack.setOnClickListener(this);
        mRlProName.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_pro_name:

                break;
        }
    }

    private void requestProOrderDetailData() {
       /* String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        HtmlRequest.getProOrderDetail(this, userInfoId,category ,new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){
                    ProDetail2B = (ProductDetail2B)params.result;
                    if(ProDetail2B!=null){
                        setView();
                    }


                }

            }
        });
    }

    private void setView() {
        mTvTitle.setText(proName);
        mTvProName.setText(ProDetail2B.getProductName());
        mTvtype.setText(category);
        mTvOrderName.setText(ProDetail2B.getUserInfoName());
        mTvPhone.setText(ProDetail2B.getMobile());
        mTvIDNum.setText(ProDetail2B.getIdNo());
        mTvOrderStatus.setText(ProDetail2B.getStatus());
        mTvOrderTime.setText(ProDetail2B.getBookingTime());
    }

}
