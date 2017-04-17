package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.adapter.FloatAdapter;
import com.jdhui.bean.ResultFixedProductListBean;
import com.jdhui.bean.ResultFixedProductListItemBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ViewUtils;

/**
 * 产品--浮动收益列表
 */
public class FloatActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private PullToRefreshListView listView;
    private FloatAdapter mAdapter;
    private MouldList<ResultFixedProductListItemBean> totalList = new MouldList<>();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_float);
        initView();
        initData();

    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        //PullToRefreshListView  上滑加载更多及下拉刷新
        ViewUtils.slideAndDropDown(listView);

        iv_back.setOnClickListener(this);
    }

    public void initData() {
        mAdapter = new FloatAdapter(this, totalList);
        listView.setAdapter(mAdapter);

        requestFloatingProductList();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上拉加载下一页
                    currentPage++;
                }
                requestFloatingProductList();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(FloatActivity.this, FixedProductDetailActivity.class);
                intent.putExtra("productId", totalList.get(position - 1).getProductId());
                intent.putExtra("type", "floating");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void requestFloatingProductList() {
        HtmlRequest.getProductList(this, "floating", currentPage, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result == null) {
                    Toast.makeText(FloatActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();

                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }

                ResultFixedProductListBean data = (ResultFixedProductListBean) params.result;
                MouldList<ResultFixedProductListItemBean> everyList = data.getList();

                if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                    Toast.makeText(FloatActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                }

                if (currentPage == 1) {
                    //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                    totalList.clear();
                }
                totalList.addAll(everyList);

                //刷新数据
                if (mAdapter == null) {
                    mAdapter = new FloatAdapter(FloatActivity.this, totalList);
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
