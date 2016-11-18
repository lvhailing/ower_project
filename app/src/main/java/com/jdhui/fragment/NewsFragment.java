package com.jdhui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.act.WebActivity;
import com.jdhui.adapter.NewsAdapter;
import com.jdhui.bean.ResultNewsContentBean;
import com.jdhui.bean.ResultNewsListContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;

/**
 * 底部导航---快讯
 */
public class NewsFragment extends Fragment {
    private View view;
    private PullToRefreshListView listView;
    private MouldList<ResultNewsContentBean> list;
    private NewsAdapter adapter;
    private int pro_page = 1;
    private int cachePage_pro = pro_page;
    private ResultNewsListContentBean data;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ac_news,null);
        initView(view);
        initData();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
           pro_page = 1;
            requestData(1);
        }else{

        }

    }
    private void initView(View view) {
        context = getActivity();
        listView = (PullToRefreshListView) view.findViewById(R.id.listview);
    }
    private void initData() {
        list = new MouldList<ResultNewsContentBean>();
        requestData(1);
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
        adapter = new NewsAdapter(context, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i_web=new Intent(getActivity(),WebActivity.class);
                i_web.putExtra("type", WebActivity.WEBTYPE_NEWS_DETAILS);
                i_web.putExtra("id", list.get(position - 1).getNewsId());
                i_web.putExtra("title", "详情");
                startActivity(i_web);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(1);
    }
    private void requestData(int page) {
        cachePage_pro = pro_page;
        data = new ResultNewsListContentBean();
        HtmlRequest.getNewsList(context, pro_page,
                new BaseRequester.OnRequestListener() {
                    @Override
                    public void onRequestFinished(BaseParams params) {

                        if (params.result != null) {
                            data = (ResultNewsListContentBean) params.result;
                            if (data.getList() != null) {
                                if (data.getList().size() == 0
                                        && pro_page != 1) {
                                    Toast.makeText(context,
                                            "已经到最后一页", Toast.LENGTH_SHORT)
                                            .show();
                                    pro_page = cachePage_pro - 1;
                                    listView.getRefreshableView()
                                            .smoothScrollToPositionFromTop(0,
                                                    80, 100);
                                    listView.onRefreshComplete();
                                } else {
                                    list.clear();
                                    list.addAll(data.getList());
                                    adapter.notifyDataSetChanged();
                                    listView.getRefreshableView()
                                            .smoothScrollToPositionFromTop(0,
                                                    80, 100);
                                    listView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.onRefreshComplete();
                                        }
                                    }, 1000);
                                }

                            }
                        } else {
                            listView.onRefreshComplete();
                            Toast.makeText(context,
                                    "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
