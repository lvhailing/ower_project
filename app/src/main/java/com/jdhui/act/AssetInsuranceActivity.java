package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.adapter.AssetFixedAdapter;
import com.jdhui.bean.ResultAccountProductTendersBean;
import com.jdhui.bean.ResultAccountProductTendersItemBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.uitls.ViewUtils;

/**
 * 资产页保险收益列表
 * Created by hasee on 2016/8/10.
 */
public class AssetInsuranceActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_asset_insurance_number; //该类产品购买金额
    private PullToRefreshListView listView; //保险收益列表
    private AssetFixedAdapter mAdapter;
    private int currentPage = 1;
    private MouldList<ResultAccountProductTendersItemBean> totalList = new MouldList<>();
//    private MouldList<ResultAccountProductTendersItemBean> fixedListBean;
//    private ResultAccountProductTendersBean productBean;
//    private int assetFixedPage = 1;
//    private int cacheassetFixedPage = assetFixedPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_insurance);
        initView();
        initData();
    }

    private void initView() {
//        fixedListBean = new MouldList<ResultAccountProductTendersItemBean>();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        tv_asset_insurance_number = (TextView) findViewById(R.id.tv_asset_insurance_name);

        //PullToRefreshListView  上滑加载更多及下拉刷新
        ViewUtils.slideAndDropDown(listView);

        iv_back.setOnClickListener(this);
    }

    public void initData() {
        mAdapter = new AssetFixedAdapter(this, totalList);
        listView.setAdapter(mAdapter);

        requestUserTendersInfo();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上拉加载下一页
                    currentPage++;
                }
                requestUserTendersInfo();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //item 点击监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_fixed = new Intent(AssetInsuranceActivity.this, AssetInsuranceDetailActivity.class);
                i_fixed.putExtra("tenderId", totalList.get(i - 1).getTenderId());
                i_fixed.putExtra("productName", totalList.get(i - 1).getProductName());
                startActivity(i_fixed);
            }
        });
    }

    private void requestUserTendersInfo() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.accountProductTenders(this, currentPage + "", userId, "insurance", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result == null) {
                    Toast.makeText(AssetInsuranceActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();

                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }

                ResultAccountProductTendersBean data = (ResultAccountProductTendersBean) params.result;
                if (data != null && !TextUtils.isEmpty("tenderTotalAmount")) {
                    tv_asset_insurance_number.setText(StringUtil.formatNum(data.getTenderTotalAmount()) + "元");
                }
                MouldList<ResultAccountProductTendersItemBean> everyList = data.getList();

                if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                    Toast.makeText(AssetInsuranceActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                }

                if (currentPage == 1) {
                    //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                    totalList.clear();
                }
                totalList.addAll(everyList);

                //刷新数据
                if (mAdapter == null) {
                    mAdapter = new AssetFixedAdapter(AssetInsuranceActivity.this, totalList);
                    listView.setAdapter(mAdapter);

                } else {
                    mAdapter.notifyDataSetChanged();
                }

                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                    }
                }, 1000);
            }
        });
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
