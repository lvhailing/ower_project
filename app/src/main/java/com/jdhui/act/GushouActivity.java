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
import com.jdhui.adapter.GuShouAdapter;
import com.jdhui.bean.ResultFixedProductListBean;
import com.jdhui.bean.ResultFixedProductListItemBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ActivityStack;

/**
 * 产品--固定收益列表
 */
public class GushouActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private PullToRefreshListView listView;
    private GuShouAdapter mAdapter;
    private ResultFixedProductListBean fixedBean;
    private int fixedPage = 1;
    private int cachePage = fixedPage;
    private MouldList<ResultFixedProductListItemBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_gushou);
        initView();
        initData();

    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        fixedBean = new ResultFixedProductListBean();
        list = new MouldList<ResultFixedProductListItemBean>();

        mBtnBack = (ImageView) findViewById(R.id.id_img_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        mBtnBack.setOnClickListener(this);
    }

    public void initData() {
        requestFixedProductList();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (fixedPage >= 2) {
                        fixedPage--;
                        requestFixedProductList();

                    } else {
                        fixedPage = 1;
                        requestFixedProductList();
                    }

                } else {
                    fixedPage++;
                    requestFixedProductList();
                }

            }
        });

        mAdapter = new GuShouAdapter(this, list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent i_fixedProductDetail = new Intent();
                i_fixedProductDetail.setClass(GushouActivity.this, FixedProductDetailActivity.class);
                i_fixedProductDetail.putExtra("productId", list.get(position - 1).getProductId());
                i_fixedProductDetail.putExtra("type", "optimum");
                GushouActivity.this.startActivity(i_fixedProductDetail);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
        }
    }

    private void requestFixedProductList() {
        cachePage = fixedPage;
        HtmlRequest.getProductList(this, "optimum", fixedPage, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    fixedBean = (ResultFixedProductListBean) params.result;
                    if (fixedBean.getList() != null) {
                        if (fixedBean.getList().size() == 0 && fixedPage != 1) {
                            Toast.makeText(GushouActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                            fixedPage = cachePage - 1;
                            listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                            listView.onRefreshComplete();
                        } else {
                            list.clear();
                            list.addAll(fixedBean.getList());
                            mAdapter.notifyDataSetChanged();
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
                    Toast.makeText(GushouActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
                GushouActivity.this.stopLoading();
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                    }
                }, 1000);

            }
        });


    }


}
