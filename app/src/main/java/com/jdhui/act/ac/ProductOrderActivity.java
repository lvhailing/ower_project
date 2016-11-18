package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_product_order);

        initView();
        initData();
    }

    private void initData() {
        requestData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上划加载下一页
                    currentPage++;
                    requestData();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
               /* Intent i_web = new Intent(ProductOrderActivity.this, WebActivity.class);
                i_web.putExtra("type", WebActivity.WEBTYPE_NOTICE_DETAILS);
                i_web.putExtra("id", totalList.get(position - 1).getBulletinId());
                i_web.putExtra("title", "详情");
                startActivity(i_web);*/
            }
        });
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        mBtnBack.setOnClickListener(this);
    }

    private void requestData() {
        try {
            HtmlRequest.getProductOrderList(ProductOrderActivity.this, DESUtil.decrypt(PreferenceUtil.getUserId()), "", "", currentPage + "",
                    new BaseRequester.OnRequestListener() {
                        @Override
                        public void onRequestFinished(BaseParams params) {

                            ProductOrderActivity.this.stopLoading();
                            if (params.result == null) {
                                listView.onRefreshComplete();
                                Toast.makeText(ProductOrderActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                                return;
                            }

                            Product2B data = (Product2B) params.result;
                            MouldList<Product3B> everyList = data.getList();
                            if (everyList == null || everyList.size() == 0) {
                                Toast.makeText(ProductOrderActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                            listView.onRefreshComplete();

                            if (currentPage == 1) {
                                //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                                totalList.clear();
                            }
                            totalList.addAll(everyList);

                            //刷新数据
                            if(mAdapter==null) {
                                //第一次创建adpter
                                mAdapter = new ProductOrderAdapter(ProductOrderActivity.this, totalList);
                                listView.setAdapter(mAdapter);
                            }else {
                                //以后直接刷新
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
