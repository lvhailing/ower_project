package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.HouseDetailAdapter;
import com.jdhui.bean.mybean.OverseaProjectDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * 海外项目详情
 */
public class OverseaHouseDetailActivity extends BaseActivity implements View.OnClickListener {
    private ScrollView scrollView;
    private ImageView iv_back;
    private boolean isShowHouse = false; //刚进来此页面时，项目居室内容默认是不显示的
    private boolean isShowFacilities = false; //刚进来此页面时，配套设施内容默认是不显示的
    private boolean isShowLocation = false; //刚进来此页面时，地理位置内容默认是不显示的
    private ImageView iv_oversea_detail; // 顶部图片
    private TextView tv_pro_house_name;
    private TextView tv_pro_house_price;
    private TextView tv_pro_house_area;
    private TextView tv_pro_position;
    private TextView tv_pro_type;
    private TextView tv_pro_count;
    private TextView tv_pro_decoration_standard;
    private TextView tv_pro_house_desc;


    private ViewPager vp;
    private int currentPos;
    private TextView tv_vp_page;
    private HouseDetailAdapter mAdapter; // 轮播图Adapter
    private ImageView iv_project_click, iv_support_facilities_click, iv_geographic_location_click;
    private LinearLayout ll_pro_house_photos, ll_support_facilities, ll_geographic_location; // 项目居室，配套设施，地理位置等布局
    private TextView tv_project_des, tv_support_facilities_desc, tv_geographic_location_desc; // 项目居室，配套设施，地理位置等的描述
    private String pid;
    private OverseaProjectDetail2B overseaProjectDetail;
    private ArrayList<String> houseTypeImgList;

    private RelativeLayout rl_pro_house, rl_pro_facilities, rl_pro_geographic_location; // 项目居室，配套设施，地理位置布局
    private Button btn_oversea_detail_submit; // 立即预约

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_oversea_project_detail);

        initView();
        initData();
    }

    private void initView() {
        pid = getIntent().getStringExtra("pid");

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_oversea_detail = (ImageView) findViewById(R.id.iv_oversea_detail);
        iv_project_click = (ImageView) findViewById(R.id.iv_project_click);
        iv_support_facilities_click = (ImageView) findViewById(R.id.iv_support_facilities_click);
        iv_geographic_location_click = (ImageView) findViewById(R.id.iv_geographic_location_click);

        rl_pro_house = (RelativeLayout) findViewById(R.id.rl_pro_house);
        rl_pro_facilities = (RelativeLayout) findViewById(R.id.rl_pro_facilities);
        rl_pro_geographic_location = (RelativeLayout) findViewById(R.id.rl_pro_geographic_location);


        tv_pro_house_name = (TextView) findViewById(R.id.tv_pro_house_name);
        tv_pro_house_price = (TextView) findViewById(R.id.tv_pro_house_price);
        tv_pro_house_area = (TextView) findViewById(R.id.tv_pro_house_area);
        tv_pro_position = (TextView) findViewById(R.id.tv_pro_position);
        tv_pro_type = (TextView) findViewById(R.id.tv_pro_type);
        tv_pro_count = (TextView) findViewById(R.id.tv_pro_count);
        tv_pro_decoration_standard = (TextView) findViewById(R.id.tv_pro_decoration_standard);
        tv_pro_house_desc = (TextView) findViewById(R.id.tv_pro_house_desc);
        tv_project_des = (TextView) findViewById(R.id.tv_project_des);
        tv_support_facilities_desc = (TextView) findViewById(R.id.tv_support_facilities_desc);
        tv_geographic_location_desc = (TextView) findViewById(R.id.tv_geographic_location_desc);

        ll_pro_house_photos = (LinearLayout) findViewById(R.id.ll_pro_house_photos);
        ll_support_facilities = (LinearLayout) findViewById(R.id.ll_support_facilities);
        ll_geographic_location = (LinearLayout) findViewById(R.id.ll_geographic_location);
        btn_oversea_detail_submit = (Button) findViewById(R.id.btn_oversea_detail_submit);

        vp = (ViewPager) findViewById(R.id.vp);
        tv_vp_page = (TextView) findViewById(R.id.tv_vp_page);

        iv_back.setOnClickListener(this);
        rl_pro_house.setOnClickListener(this);
        rl_pro_facilities.setOnClickListener(this);
        rl_pro_geographic_location.setOnClickListener(this);
        btn_oversea_detail_submit.setOnClickListener(this);
    }

    private void initData() {
        requestDetailData();
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            currentPos = position;
            updateNum();
        }
    }

    // 图片切换时更新数字
    private void updateNum() {
        tv_vp_page.setText(currentPos + 1 + "/" + houseTypeImgList.size());
    }


    private void setView() {
        //加载顶部图片
        ImageLoader.getInstance().displayImage(overseaProjectDetail.getProjectImg(), iv_oversea_detail);

        tv_pro_house_name.setText(overseaProjectDetail.getName());
        tv_pro_house_price.setText(overseaProjectDetail.getPrice() + "万元起");
        tv_pro_house_area.setText("面积" + overseaProjectDetail.getArea());
        tv_pro_position.setText(overseaProjectDetail.getLocation());
        tv_pro_type.setText(overseaProjectDetail.getCategory());
        tv_pro_count.setText(overseaProjectDetail.getTotal() + "套");
        tv_pro_decoration_standard.setText(overseaProjectDetail.getDecorateStandard());
        tv_pro_house_desc.setText(overseaProjectDetail.getProjectDesc());


        houseTypeImgList = overseaProjectDetail.getHouseTypeImg();
        mAdapter = new HouseDetailAdapter(this, houseTypeImgList);
        vp.setAdapter(mAdapter);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
        vp.setCurrentItem(currentPos);

        updateNum();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        scrollView.smoothScrollTo(0, 0);
//        requestDetailData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: // 返回
                finish();
                break;
            case R.id.rl_pro_house:   // 项目居室
                if (!isShowHouse) {
                    ll_pro_house_photos.setVisibility(View.VISIBLE);
                    tv_project_des.setText(overseaProjectDetail.getHouseType());
                    iv_project_click.setBackgroundResource(R.mipmap.icon_oversea_up);
                    isShowHouse = true;
                } else {
                    isShowHouse = false;
                    ll_pro_house_photos.setVisibility(View.GONE);
                    iv_project_click.setBackgroundResource(R.mipmap.icon_oversea_down);
                }
                break;
            case R.id.rl_pro_facilities:  // 配套设施
                if (!isShowFacilities) { // 布局处于打开状态
                    ll_support_facilities.setVisibility(View.VISIBLE);
                    tv_support_facilities_desc.setText(overseaProjectDetail.getSupportFacility());
                    iv_support_facilities_click.setBackgroundResource(R.mipmap.icon_oversea_up);
                    isShowFacilities = true;
                } else { // 关闭状态
                    isShowFacilities = false;
                    ll_support_facilities.setVisibility(View.GONE);
                    iv_support_facilities_click.setBackgroundResource(R.mipmap.icon_oversea_down);
                }
                break;
            case R.id.rl_pro_geographic_location:  // 地理位置
                if (!isShowLocation) {
                    ll_geographic_location.setVisibility(View.VISIBLE);
                    tv_geographic_location_desc.setText(overseaProjectDetail.getGeographyLocation());
                    iv_geographic_location_click.setBackgroundResource(R.mipmap.icon_oversea_up);
                    isShowLocation = true;
                } else {
                    isShowLocation = false;
                    ll_geographic_location.setVisibility(View.GONE);
                    iv_geographic_location_click.setBackgroundResource(R.mipmap.icon_oversea_down);
                }
                break;
            case R.id.btn_oversea_detail_submit: // 立即预约
                Intent intent = new Intent(OverseaHouseDetailActivity.this, SubmitOverseaHouseActivity.class);
                intent.putExtra("houseId", pid);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取海外项目详情页的数据
     */
    private void requestDetailData() {

        HtmlRequest.getOverseaDetailData(this, pid, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    overseaProjectDetail = (OverseaProjectDetail2B) params.result;
                    if (overseaProjectDetail != null) {
                        setView();
                    }
                }
            }
        });
    }
}
