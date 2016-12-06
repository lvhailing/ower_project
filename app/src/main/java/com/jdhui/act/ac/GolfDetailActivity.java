package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.GolfDetail2B;
import com.jdhui.bean.mybean.GolfDetail3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;

/**
 * 服务--高尔夫球场地详情
 */
public class GolfDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private ImageView iv_detail_photo;
    private GolfDetail2B golf2B;
    private String id; //高尔夫球场Id
    private TextView tv_field_name;
    private TextView tv_address;
    private TextView tv_detail;
    private TextView tv_original_price;
    private TextView tv_type_price;
    private TextView tv_field_price;
    private GolfDetail3B detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_golf_detail);

        initView();
        initData();
    }

    private void initData() {
        requestListData();

    }

    private void initView() {
        id = getIntent().getStringExtra("id");

        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        iv_detail_photo = (ImageView) findViewById(R.id.iv_detail_photo);
        tv_field_name = (TextView) findViewById(R.id.tv_field_name);
        tv_field_price = (TextView) findViewById(R.id.tv_field_price);
        tv_type_price = (TextView) findViewById(R.id.tv_type_price);
        tv_original_price = (TextView) findViewById(R.id.tv_original_price);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_detail = (TextView) findViewById(R.id.tv_detail);

        mBtnBack.setOnClickListener(this);
    }

    private void setView() {

        tv_field_name.setText(detail.getGolfName());
        if (detail.getGolfRights().equals("not")) {
            tv_type_price.setText("优惠价");

            tv_field_price.setText("￥" + detail.getPreferenialPrice().toString());
            tv_original_price.setText("￥" + detail.getOriginalPrice().toString());
        } else if (detail.getGolfRights().equals("A1")) {
            tv_type_price.setText("嘉宾价");

            tv_field_price.setText("￥" + detail.getGuestPrice().toString());
            tv_original_price.setText("￥" + detail.getOriginalPrice().toString());
        } else {
            tv_type_price.setText("会员价");

            tv_field_price.setText("￥" + detail.getVipPrice().toString());
            tv_original_price.setText("￥" + detail.getOriginalPrice().toString());
        }

        tv_address.setText(detail.getGolfAddress());
        tv_detail.setText(detail.getGolfBrief());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void requestListData() {  //请求高尔夫球场详情的数据
        HtmlRequest.getGolfDetail(this, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    golf2B = (GolfDetail2B) params.result;
                    detail = golf2B.getGolf();
                    if (detail != null) {
                        setView();
                    }
                }
            }
        });
    }
}
