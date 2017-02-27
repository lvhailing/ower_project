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
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 服务--高尔夫球场地详情
 */
public class GolfDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private ImageView iv_detail_photo;
    private GolfDetail2B golf2B;
    private GolfDetail3B detail;
    private String id; //高尔夫球场Id
    private TextView tv_field_name; //高尔夫球场名称
    private TextView tv_address;
    private TextView tv_detail;
    private Button btn_submit;

    private TextView tv_up_price_flag;
    private TextView tv_up_preferential_price;
    private TextView tv_up_original_price;

    private TextView tv_down_price_flag;
    private TextView tv_down_preferential_price;
    private TextView tv_down_original_price;

    private Number preferenialPrice;//平日优惠价
    private Number preferenialPriceHoliday;//假日优惠价
    private Number guestPrice;//平日嘉宾价
    private Number guestPriceHoliday;//假日嘉宾价
    private Number vipPrice;//平日会员价
    private Number vipPriceHoliday;//假日会员价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_golf_detail);

        initView();
        initData();
    }

    private void initData() {
        requestDetailData();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");

        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        iv_detail_photo = (ImageView) findViewById(R.id.iv_detail_photo);
        tv_field_name = (TextView) findViewById(R.id.tv_field_name);

        tv_up_price_flag = (TextView) findViewById(R.id.tv_up_price_flag);
        tv_up_preferential_price = (TextView) findViewById(R.id.tv_up_preferential_price);
        tv_up_original_price = (TextView) findViewById(R.id.tv_up_original_price);
        tv_down_price_flag = (TextView) findViewById(R.id.tv_down_price_flag);
        tv_down_preferential_price = (TextView) findViewById(R.id.tv_down_preferential_price);
        tv_down_original_price = (TextView) findViewById(R.id.tv_down_original_price);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void setView() {
        //加载图片
        ImageLoader.getInstance().displayImage(detail.getGolfPhoto(), iv_detail_photo);

        String type;
        String price;
        //高尔夫权限  not：优惠价  A1：嘉宾价  A2：会员价  VIP：会员价
        if (detail.getGolfRights().equals("not")) {
            type = "优惠价";
            preferenialPrice = detail.getPreferenialPrice();
            preferenialPriceHoliday = detail.getPreferenialPriceHoliday();

            tv_up_price_flag.setText("平日优惠价");
            tv_up_preferential_price.setText("￥:" + preferenialPrice);
            tv_up_original_price.setText("￥" + detail.getOriginalPrice()); //平日原价

            tv_down_price_flag.setText("假日优惠价");
            tv_down_preferential_price.setText("￥:" + preferenialPriceHoliday);
            tv_down_original_price.setText("￥" + detail.getOriginalPriceHoliday());

        } else if (detail.getGolfRights().equals("A1")) {
            type = "嘉宾价";
            guestPrice = detail.getGuestPrice();
            guestPriceHoliday = detail.getGuestPriceHoliday();

            tv_up_price_flag.setText("平日嘉宾价");
            tv_up_preferential_price.setText("￥:" + guestPrice);
            tv_up_original_price.setText("￥" + detail.getOriginalPrice()); //平日原价

            tv_down_price_flag.setText("假日嘉宾价");
            tv_down_preferential_price.setText("￥:" + guestPriceHoliday);
            tv_down_original_price.setText("￥" + detail.getOriginalPriceHoliday());


        } else {
            type = "会员价";
            vipPrice = detail.getVipPrice();
            vipPriceHoliday = detail.getVipPriceHoliday();

            tv_up_price_flag.setText("平日会员价");
            tv_up_preferential_price.setText("￥:" + vipPrice);
            tv_up_original_price.setText("￥" + detail.getOriginalPrice()); //平日原价

            tv_down_price_flag.setText("假日会员价");
            tv_down_preferential_price.setText("￥:" + vipPriceHoliday);
            tv_down_original_price.setText("￥" + detail.getOriginalPriceHoliday());
        }

        tv_up_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰
        tv_down_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);// 设置中划线并加清晰


        tv_field_name.setText(detail.getGolfName());
        tv_address.setText(detail.getGolfAddress());
        tv_detail.setText(detail.getGolfBrief());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:   //立即预约
                Intent intent = new Intent(GolfDetailActivity.this, SubBookingGolfActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", detail.getGolfName());
                intent.putExtra("golfRights", detail.getGolfRights());//高尔夫权限  not：优惠价  A1：嘉宾价  A2和VIP（都显示）：会员价
                startActivity(intent);
                break;
        }
    }

    private void requestDetailData() {  //请求高尔夫球场详情的数据
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
