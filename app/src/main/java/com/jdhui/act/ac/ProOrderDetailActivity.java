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
import com.jdhui.act.InsuranceProductDetailActivity;
import com.jdhui.bean.mybean.ProductDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 更多--产品预约的 预约详情
 */
public class ProOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlProName;
    private TextView mTvTitle;
    private TextView mTvProName;//产品名称
    private TextView mTvtype;//产品类型
    private TextView mTvOrderName;//预约人
    private TextView mTvPhone;//手机号码
    private TextView mTvIdNum;//证件号码
    private TextView mTvOrderStatus;//预约状态
    private TextView mTvOrderTime;//预约时间
    private ImageView mIvBack;
    private String id;
    private String category;
    private String status;
    private ProductDetail2B proDetail2B;

    private String proName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_pro_order_detail);
        initView();
        initData();
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        proName = getIntent().getStringExtra("productName");
        category = getIntent().getStringExtra("category");
        status = getIntent().getStringExtra("status");
        requestProOrderDetailData();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mRlProName = (RelativeLayout) findViewById(R.id.rl_pro_name);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvProName = (TextView) findViewById(R.id.tv_pro_name);
        mTvtype = (TextView) findViewById(R.id.tv_type);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvIdNum = (TextView) findViewById(R.id.tv_id_num);
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
                if (category.equals("insurance")) { //保险类型
                    Intent intent = new Intent(this, InsuranceProductDetailActivity.class);
                    intent.putExtra("productId", proDetail2B.getProductId());
                    startActivity(intent);
                } else { //非保险（固收和浮收）
                    Intent intent = new Intent(this, FixedProductDetailActivity.class);
                    intent.putExtra("productId", proDetail2B.getProductId());
                    intent.putExtra("type", category);
                    startActivity(intent);
                }
                break;
        }
    }

    private void requestProOrderDetailData() {
        HtmlRequest.getProOrderDetail(this, id, category, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    proDetail2B = (ProductDetail2B) params.result;
                    if (proDetail2B != null) {
                        setView();
                    }
                }
            }
        });
    }

    private void setView() {
        mTvTitle.setText(proName);
        mTvProName.setText(proDetail2B.getProductName());
        if (category.equals("insurance")) {
            mTvtype.setText("保险类");
        } else if (category.equals("optimun")) {
            mTvtype.setText("固定收益类");
        } else {
            mTvtype.setText("浮动收益类");
        }
        mTvOrderName.setText(proDetail2B.getUserInfoName());
        mTvPhone.setText(proDetail2B.getMobile());
        mTvIdNum.setText(proDetail2B.getIdNo());
        if (status.equals("submit")) {
            mTvOrderStatus.setText("待确认");
        } else if (status.equals("confirm")) {
            mTvOrderStatus.setText("已确认");
        } else if (status.equals("cancel")) {
            mTvOrderStatus.setText("无效预约");
        }
        mTvOrderTime.setText(proDetail2B.getBookingTime());
    }

}
