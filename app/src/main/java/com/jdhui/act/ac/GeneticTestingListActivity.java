package com.jdhui.act.ac;

import android.app.Dialog;
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
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.GeneticTestingListAdapter;
import com.jdhui.bean.mybean.GeneticTestingDetail2B;
import com.jdhui.bean.mybean.GeneticTestingList2B;
import com.jdhui.bean.mybean.GeneticTestingList3B;
import com.jdhui.dialog.GeneticTestingDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 服务--基因检测列表
 */
public class GeneticTestingListActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private GeneticTestingListAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<GeneticTestingList3B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页
    private GeneticTestingDetail2B detail2B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_booking_genetictesting);

        initView();
        initData();
    }

    private void initData() {
        mAdapter = new GeneticTestingListAdapter(GeneticTestingListActivity.this, totalList);
        listView.setAdapter(mAdapter);

        requestListData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上划加载下一页
                    currentPage++;
                }
                requestListData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String id = totalList.get(position).getId();
                //带着点击的那一项的id去访问接口
                requestDetailData(id);
            }
        });

    }

    //请求详情
    private void requestDetailData(String id) {
        HtmlRequest.getGeneticTestingDetail(GeneticTestingListActivity.this, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                GeneticTestingListActivity.this.stopLoading();
                if (params.result == null) {
                    Toast.makeText(GeneticTestingListActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }

                detail2B = (GeneticTestingDetail2B) params.result;

                showDialog();
            }
        });
        //数据回来后 弹框
    }

    //展示详情对话框
    private void showDialog() {
        GeneticTestingDialog dialog = new GeneticTestingDialog(this, detail2B);
        dialog.setDateDialog(new GeneticTestingDialog.MyCallback() {
            @Override
            public void onMyclick(Dialog ad) {
                Intent intent = new Intent(GeneticTestingListActivity.this, SubGeneticTestingActivity.class);
                intent.putExtra("id", detail2B.getId());
                intent.putExtra("name", detail2B.getName());    //基因检测套餐名字
                startActivity(intent);

                ad.dismiss();
                ad = null;
            }
        });
    }


    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        mBtnBack.setOnClickListener(this);
    }

    private void requestListData() {
//        Toast.makeText(this, selectCity + ":" + selectProvince, Toast.LENGTH_SHORT).show();
        try {
            HtmlRequest.getGeneticTestingList(GeneticTestingListActivity.this, "1", new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    GeneticTestingListActivity.this.stopLoading();
                    if (params.result == null) {
                        listView.onRefreshComplete();
                        Toast.makeText(GeneticTestingListActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        return;
                    }

                    GeneticTestingList2B data = (GeneticTestingList2B) params.result;
                    MouldList<GeneticTestingList3B> everyList = data.getList();
                    if (everyList == null || everyList.size() == 0) {
                        Toast.makeText(GeneticTestingListActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
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
                    mAdapter.notifyDataSetChanged();
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