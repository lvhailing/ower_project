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
import com.jdhui.uitls.ActivityStack;

/**
 * 浮动收益列表
 */
public class FloatActivity extends BaseActivity implements View.OnClickListener{
    private View view;
    private PullToRefreshListView listView;
    private FloatAdapter mAdapter;
    private ImageView mBtnBack;
    private ResultFixedProductListBean fixedProductBean;

    private int fixedPage = 1;
    private int cachePage = fixedPage;
    private MouldList<ResultFixedProductListItemBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_float);
        initView();
        initData();

    }
    public void initData(){

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (fixedPage >= 2) {
                        fixedPage--;
                        requestFloatingProductList();
                    } else {
                        fixedPage = 1;
                        requestFloatingProductList();
                    }

                } else {
                    fixedPage++;
                    requestFloatingProductList();
                }

            }
        });
        requestFloatingProductList();

        mAdapter =new FloatAdapter(this,list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Intent i_fixedProductDetail = new Intent();
                i_fixedProductDetail.setClass(FloatActivity.this,FixedProductDetailActivity.class);
                i_fixedProductDetail.putExtra("productId",list.get(position-1).getProductId());
                i_fixedProductDetail.putExtra("type","floating");
                FloatActivity.this.startActivity(i_fixedProductDetail);
            }
        });

    }

    private void initView() {

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        list = new MouldList<ResultFixedProductListItemBean>();

        mBtnBack= (ImageView) findViewById(R.id.id_img_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_img_back:
                finish();
                break;
        }
    }

    private void requestFloatingProductList(){
        cachePage = fixedPage;
        HtmlRequest.getProductList(this,"floating",fixedPage, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {

                if (params.result != null) {
                    fixedProductBean = (ResultFixedProductListBean) params.result;

                    if(fixedProductBean.getList()!=null){
                        if (fixedProductBean.getList().size() == 0 &&fixedPage != 1 ) {
                            Toast.makeText(FloatActivity.this, "已经到最后一页",
                                    Toast.LENGTH_SHORT).show();
                            fixedPage = cachePage - 1;
                            listView.getRefreshableView()
                                    .smoothScrollToPositionFromTop(0, 80,
                                            100);
                            listView.onRefreshComplete();
                        } else {
                            list.clear();
                            list.addAll(fixedProductBean.getList());
                            mAdapter.notifyDataSetChanged();
                            listView.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listView.onRefreshComplete();
                                }
                            }, 1000);
                            listView.getRefreshableView()
                                    .smoothScrollToPositionFromTop(0, 80,
                                            100);
                        }
                    }

                } else {
                    listView.onRefreshComplete();
                    Toast.makeText(FloatActivity.this, "加载失败，请确认网络通畅",
                            Toast.LENGTH_LONG).show();
                }
                FloatActivity.this.stopLoading();
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
