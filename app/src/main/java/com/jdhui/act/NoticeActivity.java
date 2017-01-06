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
import com.jdhui.adapter.NoticeAdapter;
import com.jdhui.bean.ResultNoticeContentBean;
import com.jdhui.bean.ResultNoticeListContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 君德公告
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private NoticeAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<ResultNoticeContentBean> totalList = new MouldList<>();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_notice);
        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        mBtnBack.setOnClickListener(this);
    }

    private void initData() {
        mAdapter = new NoticeAdapter(NoticeActivity.this, totalList);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent i_web = new Intent(NoticeActivity.this, WebActivity.class);
                i_web.putExtra("type", WebActivity.WEBTYPE_NOTICE_DETAILS);
                i_web.putExtra("id", totalList.get(position - 1).getBulletinId());
                i_web.putExtra("title", "详情");
                startActivity(i_web);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestData() {
        HtmlRequest.getNoticeList(NoticeActivity.this, currentPage, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                NoticeActivity.this.stopLoading();
                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                listView.onRefreshComplete();

                if (params.result == null) {
                    Toast.makeText(NoticeActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }

                ResultNoticeListContentBean data = (ResultNoticeListContentBean) params.result;
                MouldList<ResultNoticeContentBean> everyList = data.getList();

                if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
                    Toast.makeText(NoticeActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (currentPage == 1) {
                    //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                    totalList.clear();
                }
                totalList.addAll(everyList);

                //刷新数据
                if (mAdapter == null) {
                    mAdapter = new NoticeAdapter(NoticeActivity.this, totalList);
                    listView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                listView.onRefreshComplete();
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
