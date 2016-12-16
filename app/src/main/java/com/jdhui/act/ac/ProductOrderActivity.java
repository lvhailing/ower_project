package com.jdhui.act.ac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.ProductOrderAdapter;
import com.jdhui.bean.mybean.Product2B;
import com.jdhui.bean.mybean.Product3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 更多--产品预约
 */
public class ProductOrderActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private ProductOrderAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<Product3B> totalList = new MouldList<>();
    private View v_hidden; //隐藏的类型或状态布局背景
    private LinearLayout ll_hidden; //隐藏的类型或状态布局
    private RelativeLayout rl_category; //类型按钮
    private RelativeLayout rl_status; //状态按钮
    private TextView tv_1, tv_2, tv_3, tv_4;  //状态或类型的下面的text

    private int currentPage = 1;    //当前页
    private int currentType;  //当前点的是上拉、下拉刷新  还是组合选择  1、上拉、下拉刷新  2、组合选择
    private int currentFlag;  //当前选择哪个按钮  1、类型按钮  2、状态按钮
    private String userInfoId; //用户id
    private String category;    //当前产品类型
    private String status;    //当前预约状态   （submit:待确认;confirm:已确认;cancel:无效预约）
    private boolean isOpened = false;   //动画是否开启
    private TextView mTvType; //全部类型
    private TextView mTvStatus; //全部状态
    private ImageView iv_select_left;//类型后面的 小三角 图标
    private ImageView iv_select_right;//状态后面的 小三角 图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_product_order);

        initView();
        initData();
    }

    private void initData() {
        category = "";  //首次默认"" ，代表全部类型
        status = "";  //首次默认"" ，代表全部状态
        requestData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上划加载下一页
                    currentPage++;
                }
                currentType = 1;
                requestData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(ProductOrderActivity.this, ProOrderDetailActivity.class);
                intent.putExtra("id", totalList.get(position - 1).getId());
                intent.putExtra("productName", totalList.get(position - 1).getProductName());
                intent.putExtra("category", totalList.get(position - 1).getCategory());
                intent.putExtra("status", totalList.get(position - 1).getStatus());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        iv_select_left = (ImageView) findViewById(R.id.iv_select_left);
        iv_select_right = (ImageView) findViewById(R.id.iv_select_right);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        v_hidden = findViewById(R.id.v_hidden);
        ll_hidden = (LinearLayout) findViewById(R.id.ll_hidden);
        rl_category = (RelativeLayout) findViewById(R.id.rl_category);
        rl_status = (RelativeLayout) findViewById(R.id.rl_status);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);

        mBtnBack.setOnClickListener(this);
        v_hidden.setOnClickListener(this);
        rl_category.setOnClickListener(this);
        rl_status.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
    }

    private void requestData() {
        try {
            userInfoId = DESUtil.decrypt(PreferenceUtil.getUserId());
            HtmlRequest.getProductOrderList(ProductOrderActivity.this, userInfoId, category, status, currentPage + "", new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {

                    ProductOrderActivity.this.stopLoading();
                    if (params.result == null) {
                        listView.onRefreshComplete();
                        Toast.makeText(ProductOrderActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        return;
                    }

                    listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                    listView.onRefreshComplete();

                    Product2B data = (Product2B) params.result;
                    MouldList<Product3B> everyList = data.getList();
                    if (everyList == null || everyList.size() == 0) {
                        Toast.makeText(ProductOrderActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
                        if (mAdapter != null && currentType == 2) {
                            totalList.clear();
                            mAdapter.notifyDataSetChanged();    //刷新界面
                        }
                        return;
                    }
                    if (currentPage == 1) {
                        //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                        totalList.clear();
                    }
                    totalList.addAll(everyList);

                    //刷新数据
                    if (mAdapter == null) {
                        //第一次创建adpter
                        mAdapter = new ProductOrderAdapter(ProductOrderActivity.this, totalList);
                        listView.setAdapter(mAdapter);
                    } else {
                        //以后直接刷新
                        mAdapter.notifyDataSetChanged();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开启动画
    private void openShopping() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(ll_hidden, "translationY", -ll_hidden.getMeasuredHeight(), 0f);
        oa.setDuration(200);
        oa.start();
        v_hidden.setVisibility(View.VISIBLE);
        ll_hidden.setVisibility(View.VISIBLE);
        freshUI();
        isOpened = true;
    }

    //关闭动画
    private void closeShopping() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(ll_hidden, "translationY", 0f, -ll_hidden.getMeasuredHeight());
        oa.setDuration(200);
        oa.start();
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v_hidden.setVisibility(View.GONE);
                ll_hidden.setVisibility(View.GONE);
            }
        });
        isOpened = false;
    }

    //点按钮后 需要刷新UI
    private void freshUI() {
        if (currentFlag == 1) {
            //点了类型按钮
            tv_1.setText("全部类型");
            tv_2.setText("固定收益");
            tv_3.setText("浮动收益");
            tv_4.setText("保险");
        } else {
            //点了状态按钮
            tv_1.setText("全部状态");
            tv_2.setText("已确认");
            tv_3.setText("待确认");
            tv_4.setText("无效预约");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.v_hidden:  //隐藏布局 关闭动画
                closeShopping();
                break;
            case R.id.rl_category:  //类型
                if (isOpened && currentFlag == 2) {
                    //类型展开着，只刷新UI即可
                    currentFlag = 1;
                    freshUI();
                    iv_select_right.setBackgroundResource(R.drawable.triangle_up_fill);
                } else if (isOpened) {
                    //类型是开启状态 则需关闭动画
                    currentFlag = 1;
                    iv_select_left.setBackgroundResource(R.drawable.triangle_down_fill);
                    closeShopping();
                } else {
                    //否则开启动画
                    iv_select_left.setBackgroundResource(R.drawable.triangle_up_fill);
                    currentFlag = 1;
                    openShopping();
                }
                break;
            case R.id.rl_status:  //状态
                if (isOpened && currentFlag == 1) {
                    //状态展开着，只刷新UI即可
                    currentFlag = 2;
                    freshUI();
                    iv_select_right.setBackgroundResource(R.drawable.triangle_up_fill);
                } else if (isOpened) {
                    //状态是开启状态 则需关闭动画
                    currentFlag = 2;
                    iv_select_right.setBackgroundResource(R.drawable.triangle_down_fill);
                    closeShopping();
                } else {
                    //否则开启动画
                    currentFlag = 2;
                    iv_select_right.setBackgroundResource(R.drawable.triangle_up_fill);
                    openShopping();
                }
                break;
            case R.id.tv_1:  //全部类型、或全部状态
                if (currentFlag == 1) {
                    category = "";
                    mTvType.setText("全部类型");
                    iv_select_left.setBackgroundResource(R.drawable.triangle_down_fill);
                } else {
                    status = "";
                    mTvStatus.setText("全部状态");
                    iv_select_right.setBackgroundResource(R.drawable.triangle_down_fill);
                }
                currentType = 2;
                closeShopping();
                requestData();
                break;
            case R.id.tv_2:  //固定收益、或已确认
                if (currentFlag == 1) {
                    category = "optimum";
                    mTvType.setText("固定收益");
                    iv_select_left.setBackgroundResource(R.drawable.triangle_down_fill);
                } else {
                    status = "confirm";
                    mTvStatus.setText("已确认");
                    iv_select_right.setBackgroundResource(R.drawable.triangle_down_fill);
                }
                currentType = 2;
                closeShopping();
                requestData();
                break;
            case R.id.tv_3:  //浮动收益、或待确认
                if (currentFlag == 1) {
                    category = "floating";
                    mTvType.setText("浮动收益");
                    iv_select_left.setBackgroundResource(R.drawable.triangle_down_fill);
                } else {
                    status = "submit";
                    mTvStatus.setText("待确认");
                    iv_select_right.setBackgroundResource(R.drawable.triangle_down_fill);
                }
                currentType = 2;
                closeShopping();
                requestData();
                break;
            case R.id.tv_4:  //保险、或无效预约
                if (currentFlag == 1) {
                    category = "insurance";
                    mTvType.setText("保险");
                    iv_select_left.setBackgroundResource(R.drawable.triangle_down_fill);
                } else {
                    status = "cancel";
                    mTvStatus.setText("无效预约");
                    iv_select_right.setBackgroundResource(R.drawable.triangle_down_fill);
                }
                currentType = 2;
                closeShopping();
                requestData();
                break;
        }
    }
}
