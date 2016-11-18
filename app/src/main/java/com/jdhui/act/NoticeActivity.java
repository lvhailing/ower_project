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
import com.jdhui.uitls.ActivityStack;

/**
 * 君德公告
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {
    private View view;
    private PullToRefreshListView listView;
    private NoticeAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<ResultNoticeContentBean> list;
    private int pro_page = 1;
    private int cachePage_pro = pro_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_notice);
        initView();
    }

    private void initData() {
        list = new MouldList<ResultNoticeContentBean>();
        requestData(1);
        mAdapter = new NoticeAdapter(NoticeActivity.this, list);
        listView.setAdapter(mAdapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (pro_page >= 2) {
                        requestData(pro_page--);
                    } else {
                        requestData(1);
                    }
                } else {
                    requestData(pro_page++);
                }

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                Intent i_web = new Intent(NoticeActivity.this, WebActivity.class);
                i_web.putExtra("type", WebActivity.WEBTYPE_NOTICE_DETAILS);
                i_web.putExtra("id", list.get(position - 1).getBulletinId());
                i_web.putExtra("title", "详情");
                startActivity(i_web);
            }
        });
    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    private void requestData(int page) {
        cachePage_pro = pro_page;
        HtmlRequest.getNoticeList(NoticeActivity.this, pro_page,
                new BaseRequester.OnRequestListener() {
                    @Override
                    public void onRequestFinished(BaseParams params) {
                        if (params.result != null) {
                            ResultNoticeListContentBean data = (ResultNoticeListContentBean) params.result;
                            if (data.getList().size() == 0 && pro_page != 1) {
                                Toast.makeText(NoticeActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                                pro_page = cachePage_pro - 1;
                                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                                listView.onRefreshComplete();
                            } else {
                                list.clear();
                                list.addAll(data.getList());
                                mAdapter.notifyDataSetChanged();
                                listView.getRefreshableView().smoothScrollToPositionFromTop(0, 80, 100);
                                listView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.onRefreshComplete();
                                    }
                                }, 1000);
                            }
                        } else {
                            listView.onRefreshComplete();
                            Toast.makeText(NoticeActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        }
                        NoticeActivity.this.stopLoading();

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
