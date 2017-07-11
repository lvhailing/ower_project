package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.OverseaProjectAdapter;
import com.jdhui.bean.mybean.OverseaProjectList2B;
import com.jdhui.bean.mybean.OverseaProjectList3B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ViewUtils;
import com.jdhui.view.TitleBar;

import java.util.HashMap;


/**
 * 服务-- 海外项目列表
 */
public class OverseaProjectListActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private OverseaProjectAdapter mAdapter;
    private MouldList<OverseaProjectList3B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页
    private ViewSwitcher vs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_oversea_project);

//        initTopTitle();
        initView();
        initData();
    }

    private void initTopTitle() {
        TitleBar title = (TitleBar) findViewById(R.id.titlebar);
        title.showLeftImg(true);
        title.setTitle(getResources().getString(R.string.title_null)).setLogo(R.drawable.icons, false).setIndicator(R.drawable.back)
             .setCenterText(getResources().getString(R.string.title_oversea_project)).showMore(false).setOnActionListener(new TitleBar.OnActionListener() {

            @Override
            public void onMenu(int id) {
            }

            @Override
            public void onBack() {
                finish();
            }

            @Override
            public void onAction(int id) {

            }
        });
    }

    private void initView() {
        vs = (ViewSwitcher) findViewById(R.id.vs);
        TextView tv_empty = (TextView) findViewById(R.id.tv_empty);
        ImageView img_empty = (ImageView) findViewById(R.id.img_empty);
        tv_empty.setText("暂无海外项目");
        img_empty.setBackgroundResource(R.mipmap.ic_empty_oversea_project);

        listView = (PullToRefreshListView) findViewById(R.id.listview);
        //PullToRefreshListView  上滑加载更多及下拉刷新
        ViewUtils.slideAndDropDown(listView);
    }

    private void initData() {
        mAdapter = new OverseaProjectAdapter(this, totalList);
        listView.setAdapter(mAdapter);

//        requestListData();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新
                    currentPage = 1;
                } else {
                    //上划加载下一页
                    currentPage++;
                }
//                requestListData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(OverseaProjectListActivity.this, OverseaProjectDetailActivity.class);
                intent.putExtra("pid", totalList.get(position - 1).getPid());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
//        requestListData();
        listView.getRefreshableView().setSelection(0);
    }

    @Override
    public void onClick(View v) {

    }

    // 获取海外项目列表数据
//    private void requestListData() {
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("page", currentPage + "");
//        param.put("userId", userId);
//
//        try {
//            HtmlRequest.getOverseaListData(mContext, param, new BaseRequester.OnRequestListener() {
//                        @Override
//                        public void onRequestFinished(BaseParams params) {
//                            if (params.result == null) {
//                                vs.setDisplayedChild(1);
//                                Toast.makeText(mContext, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
//                                listView.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        listView.onRefreshComplete();
//                                    }
//                                }, 1000);
//                                return;
//                            }
//
//                            OverseaProjectList2B data = (OverseaProjectList2B) params.result;
//                            MouldList<OverseaProjectList3B> everyList = data.getList();
//
//                            if ((everyList == null || everyList.size() == 0) && currentPage != 1) {
//                                Toast.makeText(mContext, "已显示全部", Toast.LENGTH_SHORT).show();
//                            }
//
//                            if (currentPage == 1) {
//                                //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
//                                totalList.clear();
//                            }
//                            totalList.addAll(everyList);
//                            if (totalList.size() == 0) {
//                                vs.setDisplayedChild(1);
//                            } else {
//                                vs.setDisplayedChild(0);
//                            }
//                            //刷新数据
//                            mAdapter.notifyDataSetChanged();
//
//                            listView.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    listView.onRefreshComplete();
//                                }
//                            }, 1000);
//                        }
//
//                    }
//
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
