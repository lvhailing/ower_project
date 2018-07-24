package com.jdhui.act.ac;

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
import com.jdhui.adapter.GolfListAdapter;
import com.jdhui.bean.mybean.GolfList2B;
import com.jdhui.bean.mybean.GolfList3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ViewUtils;

/**
 * 服务--高尔夫球场地列表
 */
public class GolfListActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private GolfListAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<GolfList3B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_golf_list);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        //PullToRefreshListView  上滑加载更多及下拉刷新
        ViewUtils.slideAndDropDown(listView);

        mBtnBack.setOnClickListener(this);
    }

    private void initData() {
        mAdapter = new GolfListAdapter(GolfListActivity.this, totalList);
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
                Intent intent = new Intent(GolfListActivity.this, GolfDetailActivity.class);
                intent.putExtra("id", totalList.get(position - 1).getId());
                startActivity(intent);
            }
        });

    }

    private void requestListData() {  //请求高尔夫球场列表的数据
        try {
            HtmlRequest.getGolfList(GolfListActivity.this, currentPage + "", new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    if (params.result == null) {
                        Toast.makeText(GolfListActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        listView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listView.onRefreshComplete();
                            }
                        }, 1000);
                        return;
                    }

                    GolfList2B data = (GolfList2B) params.result;
                    MouldList<GolfList3B> everyList = data.getList();
                    if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                        Toast.makeText(GolfListActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                    }

                    if (currentPage == 1) {
                        //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                        totalList.clear();
                    }
                    totalList.addAll(everyList);

                    //刷新数据
                    mAdapter.notifyDataSetChanged();

                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    }, 1000);
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
