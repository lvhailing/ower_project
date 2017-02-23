package com.jdhui.act.ac;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.GolfDetail2B;
import com.jdhui.bean.mybean.GolfDetail3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.widget.FlowLayoutView;
import com.jdhui.widget.PullUpToLoadMore;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 服务--豪华邮轮游详情页
 */
public class LinerDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private TextView tv_title_travel_name; //豪华邮轮游详情页title
    private ImageView iv_detail_photo;
    private GolfDetail2B golf2B;
    private GolfDetail3B detail;
    private String id; //游轮id
    private TextView tv_field_name;
    private TextView tv_ship_price;
    private Button btn_submit;
    private PullUpToLoadMore ptlm;
    private TextView tv_travel_date; //历时 如：2日3晚
    private TextView tv_travel_name; //旅行名称
    private FlowLayoutView framlayout; //旅行名称标签
    private TextView tv_gatewayPort; //途径港口
    private TextView tv_shipName; //邮轮名称
    private TextView tv_liner_starLevel_one; //邮轮星级
    private TextView tv_passgerCapacity_one;//载客量
    private TextView tv_buildYear_one;//建造年份
    private TextView tv_tonnage_one;//吨位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_liner_detail);

        initView();
        initData();
    }

    private void initData() {
        requestDetailData();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");

        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        tv_title_travel_name = (TextView) findViewById(R.id.tv_title_travel_name);
        ptlm = (PullUpToLoadMore) findViewById(R.id.ptlm);
        iv_detail_photo = (ImageView) findViewById(R.id.iv_detail_photo);
        tv_ship_price = (TextView) findViewById(R.id.tv_ship_price);
        tv_travel_date = (TextView) findViewById(R.id.tv_travel_date);
        tv_travel_name = (TextView) findViewById(R.id.tv_travel_name);
        framlayout = (FlowLayoutView) findViewById(R.id.framlayout);
        tv_gatewayPort = (TextView) findViewById(R.id.tv_gatewayPort);
        tv_shipName = (TextView) findViewById(R.id.tv_shipName);
        tv_liner_starLevel_one = (TextView) findViewById(R.id.tv_liner_starLevel_one);
        tv_passgerCapacity_one = (TextView) findViewById(R.id.tv_passgerCapacity_one);
        tv_buildYear_one = (TextView) findViewById(R.id.tv_buildYear_one);
        tv_tonnage_one = (TextView) findViewById(R.id.tv_tonnage_one);


        mBtnBack.setOnClickListener(this);
    }

    private void setView() {
        //加载图片
        ImageLoader.getInstance().displayImage(detail.getGolfPhoto(), iv_detail_photo);

        String type;
        String price;
        //高尔夫权限  not：优惠价  A1：嘉宾价  A2：会员价  VIP：会员价
        if (detail.getGolfRights().equals("not")) {
            type = "优惠价";
            price = detail.getPreferenialPrice();
        } else if (detail.getGolfRights().equals("A1")) {
            type = "嘉宾价";
            price = detail.getGuestPrice();
        } else {
            type = "会员价";
            price = detail.getVipPrice();
        }
//        tv_type_price.setText(type);
//        tv_field_price.setText(price + "￥");
//        tv_original_price.setText(detail.getOriginalPrice() + "￥");
//        tv_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
//
//
//        tv_field_name.setText(detail.getGolfName());
//        tv_address.setText(detail.getGolfAddress());
//        tv_detail.setText(detail.getGolfBrief());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:   //立即预约
//                Intent intent = new Intent(LinerDetailActivity.this, SubBookingGolfActivity.class);
//                intent.putExtra("id", id);
//                intent.putExtra("name", detail.getGolfName());
//                intent.putExtra("golfRights", detail.getGolfRights());//高尔夫权限  not：优惠价  A1：嘉宾价  A2和VIP（都显示）：会员价
//                startActivity(intent);
//                break;
        }
    }

    private void requestDetailData() {  //请求游轮详情的数据
        HtmlRequest.getLinerDetail(this, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    golf2B = (GolfDetail2B) params.result;
//                    detail = golf2B.getGolf();
                    if (detail != null) {
//                        setView();
                    }
                }
            }
        });
    }
}
