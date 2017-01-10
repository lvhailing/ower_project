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
import com.jdhui.act.WebActivity;
import com.jdhui.adapter.NewsAdapter;
import com.jdhui.bean.ResultNewsContentBean;
import com.jdhui.bean.ResultNewsListContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 更多---君德快讯
 */
public class NewsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBtnBack;
    private PullToRefreshListView listView;
    private NewsAdapter mAdapter;
    private MouldList<ResultNewsContentBean> totalList = new MouldList<>();
    private int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_news);
        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        mBtnBack.setOnClickListener(this);
    }

    private void initData() {
        mAdapter = new NewsAdapter(NewsActivity.this, totalList);
        listView.setAdapter(mAdapter);

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
                requestData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //item 点击监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i_web = new Intent(NewsActivity.this, WebActivity.class);
                i_web.putExtra("type", WebActivity.WEBTYPE_NEWS_DETAILS);
                i_web.putExtra("id", totalList.get(position - 1).getNewsId());
                i_web.putExtra("title", "详情");
                startActivity(i_web);
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

    @Override
    public void onResume() {
        super.onResume();
//        currentPage = 1;
//        requestData();
    }

    private void requestData() {
        HtmlRequest.getNewsList(NewsActivity.this, currentPage, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                NewsActivity.this.stopLoading();
                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                listView.onRefreshComplete();

                if (params.result == null) {
                    Toast.makeText(NewsActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }

                ResultNewsListContentBean data = (ResultNewsListContentBean) params.result;
                MouldList<ResultNewsContentBean> everyList = data.getList();

                if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                    Toast.makeText(NewsActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentPage == 1) {
                    //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                    totalList.clear();
                }
                totalList.addAll(everyList);

                //刷新数据
                if (mAdapter == null) {
                    mAdapter = new NewsAdapter(NewsActivity.this, totalList);
                    listView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                    listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                    listView.onRefreshComplete();
                }
            }
        });
    }

}
