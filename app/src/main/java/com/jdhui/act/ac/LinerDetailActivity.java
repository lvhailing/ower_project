package com.jdhui.act.ac;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.MyBtmAdapter;
import com.jdhui.adapter.MyTopAdapter;
import com.jdhui.bean.mybean.LinerDetail2B;
import com.jdhui.bean.mybean.LinerDetail3B;
import com.jdhui.bean.mybean.LinerInfo2B;
import com.jdhui.bean.mybean.LinerInfo3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ViewUtils;
import com.jdhui.widget.FlowLayoutLimitLine;
import com.jdhui.widget.PullUpToLoadMore;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务--豪华邮轮游详情页
 */
public class LinerDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private ImageView iv_detail_photo;
    private TextView tv_title_travel_name; //豪华邮轮游详情页title
    private String id; //游轮id
    private TextView tv_ship_price;
    private Button btn_submit; // 立即预约按钮
    private PullUpToLoadMore ptlm;
    private TextView tv_travel_date; //历时 如：2日3晚
    private TextView tv_travel_name; //旅行名称
    private FlowLayoutLimitLine remark; //旅行名称标签
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

    private LinearLayout vp_container_top;
    private LinearLayout vp_container_point;
    private ViewPager vpTop;
    private ViewPager vpBtm;

    private MyTopAdapter vpTopAdapter;
    private MyBtmAdapter vpBtmAdapter;

    private List<LinerInfo3B> topList = new ArrayList<>();
    private List<LinerInfo3B> btmList = new ArrayList<>();

    private int screenWidth = 0;
    private int dpHeng; //底部小圆点之间的间距
    private int lastPosition = 0;   //记录上次的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_liner_detail);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        tv_title_travel_name = (TextView) findViewById(R.id.tv_title_travel_name);
        ptlm = (PullUpToLoadMore) findViewById(R.id.ptlm);
        iv_detail_photo = (ImageView) findViewById(R.id.iv_detail_photo);
        tv_ship_price = (TextView) findViewById(R.id.tv_ship_price);
        tv_travel_date = (TextView) findViewById(R.id.tv_travel_date);
        tv_travel_name = (TextView) findViewById(R.id.tv_travel_name);
        remark = (FlowLayoutLimitLine) findViewById(R.id.fl_remark);
        tv_gatewayPort = (TextView) findViewById(R.id.tv_gatewayPort);
        tv_shipName = (TextView) findViewById(R.id.tv_shipName);
        tv_liner_starLevel_one = (TextView) findViewById(R.id.tv_liner_starLevel_one);
        tv_passgerCapacity_one = (TextView) findViewById(R.id.tv_passgerCapacity_one);
        tv_buildYear_one = (TextView) findViewById(R.id.tv_buildYear_one);
        tv_tonnage_one = (TextView) findViewById(R.id.tv_tonnage_one);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        vpTop = (ViewPager) this.findViewById(R.id.vp_top);
        vp_container_top = (LinearLayout) this.findViewById(R.id.vp_container_top);

        vpBtm = (ViewPager) findViewById(R.id.vp_btm);
        vp_container_point = (LinearLayout) findViewById(R.id.vp_container_point);

        // 将父节点Layout事件分发给viewpager，否则只能滑动中间的一个view对象
        vp_container_top.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vpTop.dispatchTouchEvent(event);
            }
        });

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void initData() {
        id = getIntent().getStringExtra("id");

        dpHeng = ViewUtils.dip2px(getApplicationContext(), 4);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        vpTop.setLayoutParams(params);

        vpTopAdapter = new MyTopAdapter(topList, this);
        vpTop.setAdapter(vpTopAdapter);
        vpTop.setOffscreenPageLimit(3); // viewpager缓存页数
        vpTop.setOnPageChangeListener(new MyTopChangeListener());

        vpBtmAdapter = new MyBtmAdapter(btmList, this);
        vpBtm.setAdapter(vpBtmAdapter);
        vpBtm.setOnPageChangeListener(new MyBtmChangeListener());

        requestDetailData(); //请求游轮详情的数据

        requestLinerInfoData(); //请求邮轮信息
    }

    private class MyTopChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            btmList.clear();
            btmList.addAll(linerInfo.get(position));
            vpBtmAdapter.notifyDataSetChanged();
            //top vp切换后 保证btm的vp也回到第一个
            vpBtm.setCurrentItem(0);

            //初始化btm vp的小圆点
            initPointGroup(btmList.size());
        }
    }

    private class MyBtmChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            vp_container_point.getChildAt(position).setBackgroundResource(R.drawable.vp_bg_orange);
            vp_container_point.getChildAt(lastPosition).setBackgroundResource(R.drawable.vp_bg_gray);
            lastPosition = position;
        }
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
                        setPrePageView();
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

    /**
     * 详情页第一屏数据展示
     */
    private void setPrePageView() {
        //加载图片
        ImageLoader.getInstance().displayImage(detail.getInfoPhoto(), iv_detail_photo);

        linerTag = detail.getRouteDestination();
        mStringArray = linerTag.split(",");
        if (mStringArray.length > 0) {
            remark.setVisibility(View.VISIBLE);
            setFlowRemarks();
        } else {
            remark.setVisibility(View.GONE);
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

    private void setFlowRemarks() {
        remark.removeAllViews();
        for (int i = 0; i < mStringArray.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(mStringArray[i]);
            textView.setTag(false);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.bg_flag);
            textView.setIncludeFontPadding(false);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setGravity(Gravity.CENTER);

            int padding5dp = ViewUtils.dip2px(this, 5);
            textView.setPadding(padding5dp, 0, padding5dp, 0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewUtils.dip2px(this, 24));
            lp.setMargins(0, 0, ViewUtils.dip2px(this, 6), ViewUtils.dip2px(this, 6));
            textView.setLayoutParams(lp);
            remark.addView(textView);
        }
    }

    /**
     * 请求邮轮信息接口调成功后，界面展示数据
     */
    private void setNextPageView() {
        //准备top vp数据
        for (ArrayList<LinerInfo3B> list : linerInfo) {
            if (list != null && list.size() > 0) {
                topList.add(list.get(0));
            }
        }
        //刷新top vp
        vpTopAdapter.notifyDataSetChanged();

        //准备btm vp数据
        btmList.addAll(linerInfo.get(0));
        //刷新btm vp
        vpBtmAdapter.notifyDataSetChanged();

        //初始化btm vp的小圆点
        initPointGroup(btmList.size());
    }

    private void initPointGroup(int size) {
        vp_container_point.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = dpHeng;
            params.rightMargin = dpHeng;
            params.bottomMargin = 0;
            point.setLayoutParams(params);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.vp_bg_orange);
            } else {
                point.setBackgroundResource(R.drawable.vp_bg_gray);
            }
            vp_container_point.addView(point);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit: //立即预约 按钮

                Intent intent = new Intent(this, SubBookingShipActivity.class);
                intent.putExtra("shipId", id);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (vpBtm != null) {
            vpBtm.removeAllViews();
            vpBtm = null;
        }
        if (vpTop != null) {
            vpTop.removeAllViews();
            vpTop = null;
        }
        super.onDestroy();
    }

}
