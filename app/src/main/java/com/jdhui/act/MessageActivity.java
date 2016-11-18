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
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 消息
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener{

    public final static int MESSAGE_RESUEST_CODE = 4003;        //已读消息请求码
    private View view;
    private PullToRefreshListView listView;
    private MessageAdapter mAdapter;
    private ImageView mBtnBack;
    private int messagePage = 1;
    private int cacheassetMessagePage = 1;
    private MouldList<ResultMessageListBean> messgeList;
    private MouldList<ResultMessageListBean> list;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_message);
        initView();
    }
    private void initView() {
        messgeList = new MouldList<ResultMessageListBean>();
        list = new MouldList<ResultMessageListBean>();

        mBtnBack= (ImageView) findViewById(R.id.id_img_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestHotProductData();

    }
    /**
     * 模拟请求固守产品网络数据
     */
    private void requestHotProductData() {

        requestMessageList();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    if (messagePage >= 2) {
                        messagePage--;
                        requestMessageList();
                    } else {
                        messagePage = 1;
                        requestMessageList();
                    }

                } else {
                    messagePage++;
                    requestMessageList();
                }

            }
        });
        mAdapter =new MessageAdapter(this,list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Intent i_web = new Intent();
                i_web.setClass(MessageActivity.this,WebActivity.class);
                i_web.putExtra("id",messgeList.get(position-1).getMessageId());
                i_web.putExtra("type",WebActivity.WEBTYPE_MESSAGE_DETAILS);
                index = position-1;
                i_web.putExtra("title","消息详情");
                startActivityForResult(i_web,MESSAGE_RESUEST_CODE);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_img_back:
                finish();
                break;
        }
    }

    public void requestMessageList(){

        String userid = null;
        try {
            userid = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheassetMessagePage = messagePage;
        HtmlRequest.getMessageList(this, userid,messagePage+"",new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                    if(params.result!=null){
                        messgeList = (MouldList<ResultMessageListBean>) params.result;

                        if (messgeList.size() == 0 && messagePage!=1) {
                            Toast.makeText(MessageActivity.this, "已经到最后一页",
                                    Toast.LENGTH_SHORT).show();
                            messagePage = cacheassetMessagePage - 1;
                            listView.getRefreshableView()
                                    .smoothScrollToPositionFromTop(0, 80,
                                            100);
                            listView.onRefreshComplete();
                        } else {
                            list.clear();
                            list.addAll(messgeList);
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
                    }else{
                        listView.onRefreshComplete();
                        Toast.makeText(MessageActivity.this, "加载失败，请确认网络通畅",
                                Toast.LENGTH_LONG).show();
                    }




            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MESSAGE_RESUEST_CODE){
            messgeList.get(index).setStatus("read");
            mAdapter.notifyDataSetChanged();
        }

    }
}
