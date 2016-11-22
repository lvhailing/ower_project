package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.ProductDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 产品预约--预约详情
 */
public class ProOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlProName;
    private TextView mTvTitle;
    private TextView mTvtype;
    private TextView mTvOrderName;
    private TextView mTvPhone;
    private TextView mTvIdNum;
    private TextView mTvOrderStatus;

    private TextView mTvOrderTime;
    private ImageView mIvBack;
    private String id;
    private String category;
    private String status;
    private ProductDetail2B proDetail2B;
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
        mTvtype.setText(category);
        mTvOrderName.setText(proDetail2B.getUserInfoName());
        mTvPhone.setText(proDetail2B.getMobile());
        mTvIdNum.setText(proDetail2B.getIdNo());
        mTvOrderStatus.setText(proDetail2B.getStatus());
        mTvOrderTime.setText(proDetail2B.getBookingTime());
    }

}
