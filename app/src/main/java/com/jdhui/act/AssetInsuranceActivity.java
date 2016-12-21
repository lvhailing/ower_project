package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * 资产页保险收益列表
 * Created by hasee on 2016/8/10.
 */
public class AssetInsuranceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView id_img_back;
    private PullToRefreshListView listview_asset_insurance; //保险收益列表
    private TextView tv_asset_insurance_number;
    private MouldList<ResultAccountProductTendersItemBean> fixedListBean;

    private ResultAccountProductTendersBean productBean;
    private int assetFixedPage = 1;
    private int cacheassetFixedPage = assetFixedPage;
    private AssetFixedAdapter assetFixedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_insurance);
        initView();

    }

    private void initView() {
        fixedListBean = new MouldList<ResultAccountProductTendersItemBean>();

        id_img_back = (ImageView) findViewById(R.id.id_img_back);
        listview_asset_insurance = (PullToRefreshListView) findViewById(R.id.listview_asset_insurance);
        tv_asset_insurance_number = (TextView) findViewById(R.id.tv_asset_insurance_number);
        id_img_back.setOnClickListener(this);
//        text();

        requestUserTendersInfo();

        listview_asset_insurance.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (assetFixedPage >= 2) {
                        assetFixedPage--;
                        requestUserTendersInfo();
                    } else {
                        assetFixedPage = 1;
                        requestUserTendersInfo();
                    }
                } else {
                    assetFixedPage++;
                    requestUserTendersInfo();
                }
            }
        });

        assetFixedAdapter = new AssetFixedAdapter(this, fixedListBean);
        listview_asset_insurance.setAdapter(assetFixedAdapter);

        listview_asset_insurance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_fixed = new Intent(AssetInsuranceActivity.this, AssetInsuranceDetailActivity.class);
                i_fixed.putExtra("tenderId", fixedListBean.get(i - 1).getTenderId());
                i_fixed.putExtra("productName", fixedListBean.get(i - 1).getProductName());
                AssetInsuranceActivity.this.startActivity(i_fixed);
            }
        });

    }

    public void initData() {
        tv_asset_insurance_number.setText(StringUtil.formatNum(productBean.getTenderTotalAmount()) + "元");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
        }
    }

//    private void text(){
//
//        for(int i=0;i<20;i++){
//            AssetFixedListBean bean = new AssetFixedListBean();
//            bean.setName("信达新兴资产保险"+i+"号");
//            bean.setNum("111"+i);
//            bean.setScale(i+"0%");
//            fixedListBean.add(bean);
//        }
//
//    }

    private void requestUserTendersInfo() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cacheassetFixedPage = assetFixedPage;
        HtmlRequest.accountProductTenders(this, assetFixedPage + "", userId, "insurance", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    productBean = (ResultAccountProductTendersBean) params.result;
                    if (productBean.getList() != null) {
                        if (productBean.getList().size() == 0 && assetFixedPage != 1) {
                            Toast.makeText(AssetInsuranceActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                            assetFixedPage = cacheassetFixedPage - 1;
                            listview_asset_insurance.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                            listview_asset_insurance.onRefreshComplete();
                        } else {
                            fixedListBean.clear();
                            fixedListBean.addAll(productBean.getList());
                            assetFixedAdapter.notifyDataSetChanged();
                            listview_asset_insurance.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listview_asset_insurance.onRefreshComplete();
                                }
                            }, 1000);
                            listview_asset_insurance.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                        }
                        initData();
                    }
                } else {
                    listview_asset_insurance.onRefreshComplete();
                    Toast.makeText(AssetInsuranceActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
