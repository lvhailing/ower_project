package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.adapter.InsuranceAdapter;
import com.jdhui.adapter.NewsViewPagerAdapter;
import com.jdhui.bean.ResultInsuranceProductBean;
import com.jdhui.bean.ResultInsuranceProductItemBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.view.NewsTitleTextView;

import java.util.ArrayList;

/**
 * 产品--保险列表
 */
public class InsuranceActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    //healthInsurance:健康险; accidentInsurance:意外险; lifeInsurance:人寿险; propertyInsurance:财产险; travelInsurance:旅游险

    private ViewPager mViewPager;
    private ViewGroup mViewGroup;
    private PullToRefreshListView listView;
    private BaseAdapter adapter;
    private NewsViewPagerAdapter mAdapter;
    private String[] mTabItems = new String[]{"全   部", "健康险", "意外险", "人寿险", "财产险", "旅游险", ""};
    private int mPreSelectItem;
    View view1;
    View view2;
    View view3;
    View view4;
    View view5;
    View view6;

    private ImageView mBtnBack;
    private ResultInsuranceProductBean insuranceBean;
    private MouldList<ResultInsuranceProductItemBean> list;
    private int insurancePage = 1;          //当前页码
    private int cachePage = insurancePage;
    private String insuranceType = "";
    private InsuranceAdapter insuranceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_insurance);
        initView();
        addViewPagerView();
        initData();

    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        insuranceBean = new ResultInsuranceProductBean();
        list = new MouldList<ResultInsuranceProductItemBean>();

        mBtnBack = (ImageView) findViewById(R.id.id_img_back);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        mBtnBack.setOnClickListener(this);
    }

    private void addViewPagerView() {
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ViewPager.LayoutParams.WRAP_CONTENT;
        params.height = ViewPager.LayoutParams.WRAP_CONTENT;

        ArrayList<View> newview = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.insurance_listview, null);
        view2 = inflater.inflate(R.layout.insurance_listview, null);
        view3 = inflater.inflate(R.layout.insurance_listview, null);
        view4 = inflater.inflate(R.layout.insurance_listview, null);
        view5 = inflater.inflate(R.layout.insurance_listview, null);
        view6 = inflater.inflate(R.layout.insurance_listview, null);

        newview.add(view1);
        newview.add(view2);
        newview.add(view3);
        newview.add(view4);
        newview.add(view5);
        newview.add(view6);

        for (int i = 0; i < mTabItems.length; i++) {
            String label = mTabItems[i];

            NewsTitleTextView tv = new NewsTitleTextView(this);
            int itemWidth = (int) tv.getPaint().measureText(label);
            tv.setLayoutParams(new LinearLayout.LayoutParams((itemWidth * 2), -1));
            tv.setTextSize(16);
            tv.setText(label);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.color.white);
            tv.setOnClickListener(this);
            if (i == 0) {
                tv.setTextColor(getResources().getColor(R.color.orange));
                tv.setIsHorizontaline(true);
            } else {
                tv.setTextColor(getResources().getColor(R.color.gray_d));
                tv.setIsHorizontaline(false);
            }
            tv.setIsVerticalLine(false);
            mViewGroup.addView(tv);
        }

        mAdapter = new NewsViewPagerAdapter(this, newview);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initData() {

        switch (mPreSelectItem) {
            case 0:
                listView = (PullToRefreshListView) view1.findViewById(R.id.listview_insurance);
                break;
            case 1:
                listView = (PullToRefreshListView) view2.findViewById(R.id.listview_insurance);
                break;
            case 2:
                listView = (PullToRefreshListView) view3.findViewById(R.id.listview_insurance);
                break;
            case 3:
                listView = (PullToRefreshListView) view4.findViewById(R.id.listview_insurance);
                break;
            case 4:
                listView = (PullToRefreshListView) view5.findViewById(R.id.listview_insurance);
                break;
            case 5:
                listView = (PullToRefreshListView) view6.findViewById(R.id.listview_insurance);
                break;

            default:
                break;

        }

        requestInsuranceProductList(insuranceType);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (insurancePage >= 2) {
                        insurancePage--;
                        requestInsuranceProductList(insuranceType);
                    } else {
                        insurancePage = 1;
                        requestInsuranceProductList(insuranceType);
                    }
                } else {
                    insurancePage++;
                    requestInsuranceProductList(insuranceType);
                }
            }
        });
        insuranceAdapter = new InsuranceAdapter(this, list);
        listView.setAdapter(insuranceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //item 点击监听
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent i_insuranceProductDetail = new Intent();
                i_insuranceProductDetail.setClass(InsuranceActivity.this, InsuranceProductDetailActivity.class);
                i_insuranceProductDetail.putExtra("productId", list.get(position - 1).getProductId());
                InsuranceActivity.this.startActivity(i_insuranceProductDetail);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
        }
        // 点击tabbar
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            NewsTitleTextView child = (NewsTitleTextView) mViewGroup.getChildAt(i);
            if (child == v) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int selectPosition) {
        moveTitleLabel(selectPosition);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /*
     * 点击新闻分类的tabbar，使点击的bar居中显示到屏幕中间
     */
    private void moveTitleLabel(int position) {

        // 点击当前按钮所有左边按钮的总宽度
        int visiableWidth = 0;
        // HorizontalScrollView的宽度
        int scrollViewWidth = 0;

        mViewGroup.measure(mViewGroup.getMeasuredWidth(), -1);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mViewGroup.getMeasuredWidth(), -1);
        params.gravity = Gravity.CENTER_VERTICAL;
        mViewGroup.setLayoutParams(params);
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            NewsTitleTextView itemView = (NewsTitleTextView) mViewGroup.getChildAt(i);
            int width = itemView.getMeasuredWidth();
            if (i < position) {
                visiableWidth += width;
            }
            scrollViewWidth += width;

            if (i == mViewGroup.getChildCount() - 1) {
                break;
            }
            if (position != i) {
                itemView.setTextColor(getResources().getColor(R.color.gray_d));
                itemView.setIsHorizontaline(false);
            } else {
                itemView.setTextColor(getResources().getColor(R.color.orange));
                itemView.setIsHorizontaline(true);
            }
        }
        // 当前点击按钮的宽度
        int titleWidth = mViewGroup.getChildAt(position).getMeasuredWidth();
        int nextTitleWidth = 0;
        if (position > 0) {
            // 当前点击按钮相邻右边按钮的宽度
            nextTitleWidth = position == mViewGroup.getChildCount() - 1 ? 0 : mViewGroup.getChildAt(position - 1).getMeasuredWidth();
        }
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int move = visiableWidth - (screenWidth - titleWidth) / 2;
        if (mPreSelectItem < position) {// 向屏幕右边移动
            if ((visiableWidth + titleWidth + nextTitleWidth) >= (screenWidth / 2)) {
                // new Handler().post(new Runnable() {
                //
                // @Override
                // public void run() {
                ((HorizontalScrollView) mViewGroup.getParent()).setScrollX(move);
                // }
                // });

            }
        } else {// 向屏幕左边移动
            if ((scrollViewWidth - visiableWidth) >= (screenWidth / 2)) {
                ((HorizontalScrollView) mViewGroup.getParent()).setScrollX(move);
            }
        }
        mPreSelectItem = position;
        switch (mPreSelectItem) {
            case 0:
                insurancePage = 1;
                insuranceType = ""; //全部
                list.clear();
                initData();
                // requestData(category, 1);
                break;
            case 1:
                insurancePage = 1;
                insuranceType = "healthInsurance"; //健康
                list.clear();
                initData();
                // requestData(category, 1);
                break;
            case 2:
                insurancePage = 1;
                insuranceType = "accidentInsurance"; //意外
                list.clear();
                initData();
                // requestData(category, 1);
                break;
            case 3:
                insurancePage = 1;
                insuranceType = "lifeInsurance"; //人寿
                list.clear();
                initData();
                // requestData(category, 1);
                break;
            case 4:
                insurancePage = 1;
                insuranceType = "propertyInsurance"; //财产
                list.clear();
                initData();
                // requestData(category, 1);
                break;
            case 5:
                insurancePage = 1;
                insuranceType = "travelInsurance"; //旅游
                list.clear();
                initData();
                // requestData(category, 1);
                break;


            default:
                break;
        }
    }

    private void requestInsuranceProductList(String insuranceType) {
        cachePage = insurancePage;
        HtmlRequest.getInsuranceList(this, insuranceType, insurancePage + "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {

                    if (params.result != null) {
                        insuranceBean = (ResultInsuranceProductBean) params.result;

                        if (insuranceBean.getList() != null) {
                            if (insuranceBean.getList().size() == 0 && insurancePage != 1) {
                                Toast.makeText(InsuranceActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                                insurancePage = cachePage - 1;
                                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                                listView.onRefreshComplete();
                            } else {
                                list.clear();
                                list.addAll(insuranceBean.getList());
                                insuranceAdapter.notifyDataSetChanged();
                                listView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.onRefreshComplete();
                                    }
                                }, 1000);
                                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                            }
                        }
                    } else {
                        listView.onRefreshComplete();
                        Toast.makeText(InsuranceActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                    InsuranceActivity.this.stopLoading();
                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    }, 1000);

                }

            }
        });

    }

}
