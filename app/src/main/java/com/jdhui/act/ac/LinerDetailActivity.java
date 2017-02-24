package com.jdhui.act.ac;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.LinerDetail2B;
import com.jdhui.bean.mybean.LinerDetail3B;
import com.jdhui.bean.mybean.LinerInfo2B;
import com.jdhui.bean.mybean.LinerInfo3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.widget.FlowLayoutView;
import com.jdhui.widget.PullUpToLoadMore;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * 服务--豪华邮轮游详情页
 */
public class LinerDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private ImageView iv_detail_photo;
    private TextView tv_title_travel_name; //豪华邮轮游详情页title
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
    private LinerDetail2B LinerDetail2B;
    private LinerDetail3B detail;
    private String linerTag;
    private String[] mStringArray;


    private LinerInfo2B LinerInfo2B;
    private ArrayList<ArrayList<LinerInfo3B>> linerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_liner_detail);

        initView();
        initData();
    }

    private void initData() {
        requestDetailData();

        requestLinerInfoData();
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
        ImageLoader.getInstance().displayImage(detail.getInfoPhoto(), iv_detail_photo);

        linerTag = detail.getRouteDestination();
        mStringArray = linerTag.split(",");
        for (int i = 0; i < mStringArray.length; i++) {
            final TextView textView = new TextView(this);
            textView.setText(mStringArray[i]);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            textView.setPadding(5, 5, 5, 5);
            Drawable normal = generateDrawable(Color.rgb(220, 220, 220), 10);
            textView.setBackgroundDrawable(normal);
            framlayout.addView(textView);
        }

        tv_title_travel_name.setText(detail.getRouteName());
        tv_travel_date.setText(detail.getRouteDuration());
        tv_travel_name.setText(detail.getRouteName());
        tv_gatewayPort.setText(detail.getGatewayPort());
        tv_ship_price.setText(detail.getLowerTicketPrice());
        tv_shipName.setText(detail.getShipName());
        tv_liner_starLevel_one.setText(detail.getStarLevel());
        tv_passgerCapacity_one.setText(detail.getPassgerCapacity());
        tv_buildYear_one.setText(detail.getBuildYear());
        tv_tonnage_one.setText(detail.getTonnage());
    }

    public GradientDrawable generateDrawable(int argb, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); //设置为矩形，默认就是矩形
        drawable.setCornerRadius(radius); //设置圆角的半径
        drawable.setColor(argb);
        return drawable;
    }

    /**
     * 请求游轮详情的数据
     */
    private void requestDetailData() {
        HtmlRequest.getLinerDetail(this, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    LinerDetail2B = (LinerDetail2B) params.result;
                    detail = LinerDetail2B.getLuxuryShip();
                    if (detail != null) {
                        setView();
                    }
                }
            }
        });
    }

    /**
     * 请求邮轮信息
     */
    private void requestLinerInfoData() {
        HtmlRequest.getLinerInfo(this, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    LinerInfo2B = (LinerInfo2B) params.result;
                    linerInfo = LinerInfo2B.getVoyageInfo();
                    if (linerInfo != null) {
                        setNextPageView();
                    }
                }
            }
        });
    }

    private void setNextPageView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
