package com.jdhui.act.ac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.CityAdapter;
import com.jdhui.adapter.HospitalListAdapter;
import com.jdhui.adapter.ProvinceAdapter;
import com.jdhui.bean.mybean.BookingHospitalList2B;
import com.jdhui.bean.mybean.BookingHospitalList3B;
import com.jdhui.db.model.City;
import com.jdhui.db.model.Province;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.DownloadUtils;

import java.util.List;

/**
 * 服务--医院列表
 */
public class BookingHospitalListActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshListView listView;
    private HospitalListAdapter mAdapter;
    private ImageView mBtnBack;
    private MouldList<BookingHospitalList3B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页
    private ListView lv_left;   //左侧省份lv
    private ListView lv_right;   //右侧市lv
    private View v_hidden; //隐藏的省市布局背景
    private LinearLayout ll_hidden; //隐藏的省市布局
    private RelativeLayout rl_area; //全部地区按钮
    private TextView tv_area;
    private EditText et_search; //搜索

    private boolean isOpened = false;   //动画是否开启
    private List<Province> provinceList;    //所有省
    private List<City> cityList;    //所有市
    private DownloadUtils downloadUtils;
    private ProvinceAdapter provinceAdapter;    //省份的ad

    private CityAdapter cityAdapter;    //市的ad
    private String selectProvince = "";  //当前选中的省份
    private String selectCity = "";  //当前选中的市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_hospital_list);

        initView();
        initData();
    }

    private void initData() {
        downloadUtils = new DownloadUtils();
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
                Intent intent = new Intent();
                intent.putExtra("id", totalList.get(position).getId());
                setResult(100, intent);
                finish();
            }
        });

        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectProvince = provinceList.get(position).getName();
                tv_area.setText(selectProvince);
                //改变点击的背景色
                provinceAdapter.changeBg(position);
                //刷新市数据
                freshCityDatas(position);
            }
        });

        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectCity = cityList.get(position).getName();
                tv_area.setText(selectCity);
                //关闭动画
                closeShopping();
                //访问接口
                requestData();
            }
        });
    }

    //刷新市数据
    private void freshCityDatas(int position) {
        cityList = downloadUtils.getCityList(this, provinceList.get(position).getPid());
        if (cityList == null || cityList.size() == 0) {
            return;
        }
        if (cityAdapter == null) {
            cityAdapter = new CityAdapter(this, cityList);
            lv_right.setAdapter(cityAdapter);
        } else {
            cityAdapter.setList(cityList);
            cityAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
        v_hidden = findViewById(R.id.v_hidden);
        ll_hidden = (LinearLayout) findViewById(R.id.ll_hidden);
        rl_area = (RelativeLayout) findViewById(R.id.rl_area);
        tv_area = (TextView) findViewById(R.id.tv_area);
        et_search = (EditText)findViewById(R.id.et_search);

        mBtnBack.setOnClickListener(this);
        v_hidden.setOnClickListener(this);
        rl_area.setOnClickListener(this);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //當文本變化時，觸發事件
                String input=s.toString();
                //調接口
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void requestData() {
//        Toast.makeText(this, selectCity + ":" + selectProvince, Toast.LENGTH_SHORT).show();
        try {
            HtmlRequest.getBookingHospitalList(BookingHospitalListActivity.this, selectProvince, selectCity, "", "", new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    BookingHospitalListActivity.this.stopLoading();
                    if (params.result == null) {
                        listView.onRefreshComplete();
                        Toast.makeText(BookingHospitalListActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        return;
                    }

                    BookingHospitalList2B data = (BookingHospitalList2B) params.result;
                    MouldList<BookingHospitalList3B> everyList = data.getList();
                    if (everyList == null || everyList.size() == 0) {
                        Toast.makeText(BookingHospitalListActivity.this, "没有数据了", Toast.LENGTH_SHORT).show();
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
                    if (mAdapter == null) {
                        //第一次创建adpter
                        mAdapter = new HospitalListAdapter(BookingHospitalListActivity.this, totalList);
                        listView.setAdapter(mAdapter);
                    } else {
                        //以后直接刷新
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开启动画
    private void openShopping() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(ll_hidden, "translationY", -ll_hidden.getMeasuredHeight(), 0f);
        oa.setDuration(200);
        oa.start();
        v_hidden.setVisibility(View.VISIBLE);
        ll_hidden.setVisibility(View.VISIBLE);
        isOpened = true;
    }

    //关闭动画
    private void closeShopping() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(ll_hidden, "translationY", 0f, -ll_hidden.getMeasuredHeight());
        oa.setDuration(200);
        oa.start();
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v_hidden.setVisibility(View.GONE);
                ll_hidden.setVisibility(View.GONE);
            }
        });
        isOpened = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.v_hidden:  //隐藏布局 关闭动画
                closeShopping();
                break;
            case R.id.rl_area:  //全部地区
                getProvinceDatas();
                if (isOpened) {
                    //类型是开启状态 则需关闭动画
                    closeShopping();
                } else {
                    //否则开启动画
                    openShopping();
                }
                break;
        }
    }

    //从数据库获取数据 设置到下拉菜单里
    private void getProvinceDatas() {
        if (provinceList == null) {
            //第一次 则从数据库获取所有省
            provinceList = downloadUtils.getProvinceList(this);
            provinceAdapter = new ProvinceAdapter(this, provinceList);
            lv_left.setAdapter(provinceAdapter);
        }
    }


}
