package com.jdhui.act.ac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.jdhui.adapter.ServiceOrderAdapter;
import com.jdhui.bean.mybean.Service2B;
import com.jdhui.bean.mybean.Service3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 更多--服务预约列表
 */
public class ServiceOrderActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private ServiceOrderAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<Service3B> totalList = new MouldList<>();
    private View v_hidden; //隐藏的类型或状态布局背景
    private LinearLayout ll_hidden; //隐藏的类型布局
    private RelativeLayout rl_type; //全部 按钮
    private TextView tv_0, tv_1, tv_2, tv_3, tv_4;  //类型的下面的text  tv_0：全部   tv_1：绿通就医  tv_2：基因检测  tv_3：高尔夫球场地   tv_4：公务机包机

    private int currentPage = 1;    //当前页
    private String type;    //当前服务类型
    private boolean isOpened = false;   //动画是否开启
    private TextView mTvType;//服务类型  全部
    private ImageView iv_select; // "全部"后面的 小三角

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_order);

        initView();
        initData();
    }

    private void initData() {
        type = "";  //首次默认"" ，代表全部类型
        requestData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上划加载下一页
                    currentPage++;
                    Log.i("BBB", "当前页是：" + currentPage + "---" + totalList.size());
                }
                requestData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent;
                String type = totalList.get(position - 1).getServiceItems();
                if (type.equals("airplaneBooking")) { //公务机包机预约详情
                    intent = new Intent(ServiceOrderActivity.this, ServicePlaneDetailActivity.class);
                } else {  //绿通就医，基因检测，高尔夫球场等预约详情
                    intent = new Intent(ServiceOrderActivity.this, ServiceOrderDetailActivity.class);
                }
                intent.putExtra("serviceItems", type);
                intent.putExtra("id", totalList.get(position - 1).getId());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        iv_select = (ImageView) findViewById(R.id.iv_select);
        mTvType = (TextView) findViewById(R.id.tv_type);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        v_hidden = findViewById(R.id.v_hidden);
        ll_hidden = (LinearLayout) findViewById(R.id.ll_hidden);
        rl_type = (RelativeLayout) findViewById(R.id.rl_type);
        tv_0 = (TextView) findViewById(R.id.tv_0);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);

        mBtnBack.setOnClickListener(this);
        v_hidden.setOnClickListener(this);
        rl_type.setOnClickListener(this);

        tv_0.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        requestData();
    }

    private void requestData() {
        try {
            HtmlRequest.getServiceOrderList(ServiceOrderActivity.this, type, currentPage + "", new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    ServiceOrderActivity.this.stopLoading();
//                    listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
//                    listView.onRefreshComplete();

                    if (params.result == null) {
                        Toast.makeText(ServiceOrderActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Service2B data = (Service2B) params.result;
                    MouldList<Service3B> everyList = data.getList();
                    if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                        Toast.makeText(ServiceOrderActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                    }


                    if (currentPage == 1) {
                        //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                        totalList.clear();
                    }
                    totalList.addAll(everyList);

                    //刷新数据
                    if (mAdapter == null) {
                        //第一次创建adpter
                        mAdapter = new ServiceOrderAdapter(ServiceOrderActivity.this, totalList);
                        listView.setAdapter(mAdapter);
                    } else {
                        //以后直接刷新
                        mAdapter.notifyDataSetChanged();
                    }

                    listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                    listView.onRefreshComplete();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.v_hidden:  //隐藏布局 关闭动画
                closeShopping();
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                break;
            case R.id.rl_type:  //全部
//                currentPage = 1;
                if (isOpened) {
                    //全部是开启状态 则需关闭动画
                    iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                    closeShopping();
                } else {
                    //否则开启动画
                    iv_select.setBackgroundResource(R.drawable.triangle_up_fill);
                    openShopping();
                }
                break;
            case R.id.tv_0:  //全部
                currentPage = 1;
                type = "";
                mTvType.setText("全部");
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                closeShopping();
                totalList.clear();
                requestData();
                break;
            case R.id.tv_1:  //绿通就医
                currentPage = 1;
                type = "hospitalBooking";
                mTvType.setText("绿通就医");
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                closeShopping();
                totalList.clear();
                requestData();
                break;
            case R.id.tv_2:  //基因检测
                currentPage = 1;
                type = "geneticBooking";
                mTvType.setText("基因检测");
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                closeShopping();
                totalList.clear();
                requestData();
                break;
            case R.id.tv_3:  //高尔夫球场
                currentPage = 1;
                type = "golfBooking";
                mTvType.setText("高尔夫球场地");
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                closeShopping();
                totalList.clear();
                requestData();
                break;
            case R.id.tv_4:  //公务机包机
                currentPage = 1;
                type = "airplaneBooking";
                mTvType.setText("公务机包机");
                iv_select.setBackgroundResource(R.drawable.triangle_down_fill);
                closeShopping();
                totalList.clear();
                requestData();
                break;
        }
    }
}
