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
import com.jdhui.adapter.MessageAdapter;
import com.jdhui.bean.ResultMessageListBean;
import com.jdhui.bean.ResultMessageListContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 资产--消息
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    public final static int MESSAGE_RESUEST_CODE = 4003;  //已读消息请求码
    private PullToRefreshListView listView;
    private MessageAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<ResultMessageListBean> totalList = new MouldList<>();
    private int index = -1;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_message);
        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.id_img_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);

        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");

        mBtnBack.setOnClickListener(this);
    }

    private void initData() {
        mAdapter = new MessageAdapter(this, totalList);
        listView.setAdapter(mAdapter);

        requestMessageList();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上拉加载下一页
                    currentPage++;
                }
                requestMessageList();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //item 点击监听
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent i_web = new Intent();
                i_web.setClass(MessageActivity.this, WebActivity.class);
                i_web.putExtra("id", totalList.get(position - 1).getMessageId());
                i_web.putExtra("type", WebActivity.WEBTYPE_MESSAGE_DETAILS);
                index = position - 1;
                i_web.putExtra("title", "消息详情");
                startActivityForResult(i_web, MESSAGE_RESUEST_CODE);
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

    public void requestMessageList() {
        String userid = null;
        try {
            userid = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.getMessageList(this, userid, currentPage + "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result == null) {
                    Toast.makeText(MessageActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    listView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.onRefreshComplete();
                        }
                    }, 1000);
                    return;
                }

                MouldList<ResultMessageListBean> everyList = (MouldList<ResultMessageListBean>) params.result;
                if (everyList.size() == 0 && currentPage != 1) {
                    Toast.makeText(MessageActivity.this, "已经到最后一页", Toast.LENGTH_SHORT).show();
                }

                if (currentPage == 1) {
                    //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                    totalList.clear();
                }
                totalList.addAll(everyList);

                //刷新数据
                if (mAdapter == null) {
                    mAdapter = new MessageAdapter(MessageActivity.this, totalList);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_RESUEST_CODE) {
            totalList.get(index).setStatus("read");
            mAdapter.notifyDataSetChanged();
        }
    }
}
