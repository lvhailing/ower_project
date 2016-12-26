package com.jdhui.act.ac;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.PlaneDetailAdapter;
import com.jdhui.bean.mybean.BookingInsurance2B;
import com.jdhui.bean.mybean.PlaneMarchListBean;
import com.jdhui.bean.mybean.ServiceDetail2B;
import com.jdhui.bean.mybean.SubGeneticTesting2B;
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
    private String status;
    private RelativeLayout rl_tips;//顶部提示语布局
    private TextView tv_title;//顶部提示语
    private Button btn_cancel;

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
        tv_title = (TextView) findViewById(R.id.tv_title);
        myListView = (MyListView) findViewById(R.id.lv);
        rl_tips = (RelativeLayout) findViewById(R.id.rl_tips);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        mBtnBack.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
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
            status = detail2B.getAirplaneBooking().getBookingStatus();
            if (status.equals("submit")) {
                mTvOrderStatus.setText("待确认");

                rl_tips.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
            } else if (status.equals("confirm")) {
                mTvOrderStatus.setText("已确认");
            } else if (status.equals("finish")) {
                mTvOrderStatus.setText("已完成");
            } else if (status.equals("unfinish")) {
                mTvOrderStatus.setText("未完成");
                rl_tips.setVisibility(View.VISIBLE);
                tv_title.setText(detail2B.getAirplaneBooking().getBookingRemark());
            } else if (status.equals("refuse")) {
                mTvOrderStatus.setText("已驳回");

                rl_tips.setVisibility(View.VISIBLE);
                tv_title.setText(detail2B.getAirplaneBooking().getBookingRemark());
            } else if (status.equals("cancel")) {
                mTvOrderStatus.setText("已取消");
            }
            mTvPhone.setText(detail2B.getAirplaneBooking().getClientPhone()); //联系电话

            MouldList<PlaneMarchListBean> marchList = detail2B.getAirplaneBooking().getAirplaneMarch();
            if (marchList == null || marchList.size() == 0) {
                Toast.makeText(this, "您当前暂无行程", Toast.LENGTH_LONG).show();
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
            case R.id.btn_cancel:
                abolish();
                break;
        }
    }

    private void abolish() {
        //让按钮不可点
        btn_cancel.setEnabled(false);
        btn_cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_button_gray_gray));

        HtmlRequest.cancelBooking(this, id, "airplaneBooking", "", "", "", "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params == null) {
                    cancelFailure();
                    return;
                }

                BookingInsurance2B bookingInsurance2B = (BookingInsurance2B) params.result;
                if (bookingInsurance2B == null || Boolean.parseBoolean(bookingInsurance2B.getMessage())) {
                    cancelFailure();
                    return;
                }
                Toast.makeText(ServicePlaneDetailActivity.this, "取消成功", Toast.LENGTH_LONG).show();
                finish();
            }

            private void cancelFailure() {
                Toast.makeText(ServicePlaneDetailActivity.this, "取消预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                //让按钮再次可点
                btn_cancel.setEnabled(true);
                btn_cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_button_red));
            }
        });
    }

}
