package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.AssetFixedDetailAdapter;
import com.jdhui.adapter.PlaneDetailAdapter;
import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.bean.mybean.PlaneMarchListBean;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.view.MyListView;

/**
 * 更多--服务：公务机包机预约详情
 */
public class ServicePlaneDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private TextView mTvOrderStatus; //预约状态
    private TextView mTvPhone; //联系电话
    private MyListView myListView; //加载行程的列表
    private String id;//服务Id
    private String serviceItems;//服务类型
    private ServiceDetail2B detail2B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_service_plane_detail);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        mTvOrderService = (TextView) findViewById(R.id.tv_order_service);
        mTvOrderStatus = (TextView) findViewById(R.id.tv_order_status);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        myListView = (MyListView) findViewById(R.id.lv);

        mBtnBack.setOnClickListener(this);
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        serviceItems = getIntent().getStringExtra("serviceItems");

        requestDetailData();
    }


    private void requestDetailData() {
        HtmlRequest.getServiceDetail(this, serviceItems, id, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    detail2B = (ServiceDetail2B) params.result;
                    if (detail2B != null) {
                        setView();
                    }
                }
            }
        });
    }

    private void setView() {
        // 公务机包机：airplaneBooking
        if (detail2B.getAirplaneBooking() != null) {
            //公务机包机
            mTvOrderService.setText("公务机包机");
            mTvOrderStatus.setText(detail2B.getAirplaneBooking().getBookingStatus());  //预约状态
            mTvPhone.setText(detail2B.getAirplaneBooking().getClientPhone()); //联系电话

            MouldList<PlaneMarchListBean> marchList = detail2B.getAirplaneBooking().getAirplaneMarch();
            if (marchList == null || marchList.size() == 0) {
                Toast.makeText(this, "您当前暂无行程",Toast.LENGTH_LONG).show();
            }

            //设置行程列表
            PlaneDetailAdapter adapter = new PlaneDetailAdapter(this, marchList);
            myListView.setAdapter(adapter);
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
