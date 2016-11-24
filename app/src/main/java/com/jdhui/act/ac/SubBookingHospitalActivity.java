package com.jdhui.act.ac;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.bean.mybean.SubmitBookingHospital2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DatePickDialogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务--提交预约医院
 */
public class SubBookingHospitalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private Button btn_submit;
    private TextView tv_time1; //预约时间
    private RelativeLayout rl_time1;    //预约时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_booking_hospital);

        initView();
        initData();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rl_time1 = (RelativeLayout) findViewById(R.id.rl_time1);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rl_time1.setOnClickListener(this);
    }

    private void initData() {
//        id = getIntent().getStringExtra("id");
//        serviceItems = getIntent().getStringExtra("serviceItems");

//        requestDetailData();
    }


   /* private void requestDetailData() {
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
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                submit();
                break;
            case R.id.rl_time1: //预约时间
                showDatePickerDialog(1);
                break;
        }
    }

    private void submit() {
        //hospitalId:16102616045315630527   北京协和医院
        HtmlRequest.submitBookingHospital(this, "", "16102616045315630527", "", "", "", "", "", "", "", "", "", "", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubmitBookingHospital2B bookingHospital2B = (SubmitBookingHospital2B) params.result;
                    if (bookingHospital2B != null) {
                        if (Boolean.parseBoolean(bookingHospital2B.getFlag())) {
                            Toast.makeText(SubBookingHospitalActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(SubBookingHospitalActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubBookingHospitalActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void showDatePickerDialog(final int type) {
        DatePickDialogUtil dialog = new DatePickDialogUtil(this);
        dialog.setDateDialog(new DatePickDialogUtil.MyCallback() {

            public void processTime(Dialog ad, String selectedTime) {
                //如2.16年11月30日
//                String formatTime = selectedTime.replace("年", "-").replace("月", "-").replace("日", "");
                if (tv_time1 != null && isTimeValue(selectedTime)) {
                    //选择的是正确的时间
                    if (type == 1) {
                        //点击的是预约时间
                        tv_time1.setText(selectedTime);
                    } else if (type == 2) {
                        //点击的是上面的备选时间
//                        tv_time2.setText(selectedTime);
                    } else if (type == 3) {
                        //点击的是下面的备选时间
//                        tv_time3.setText(selectedTime);
                    }
                }
                ad.dismiss();
                ad = null;
            }
        });
    }

    private boolean isTimeValue(String selectedTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date date = simpleDateFormat.parse(selectedTime);
            long now = new Date().getTime();
            if (now > date.getTime()) {
                //选择的时间必须是明天或明天以后
                Toast.makeText(SubBookingHospitalActivity.this, "时间不能晚于今天", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

}
