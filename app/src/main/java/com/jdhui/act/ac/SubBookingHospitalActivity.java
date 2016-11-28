package com.jdhui.act.ac;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.jdhui.uitls.DESUtil;
import com.jdhui.dialog.DatePickDialog;
import com.jdhui.uitls.PreferenceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 服务--提交预约医院
 */
public class SubBookingHospitalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private TextView mTvOrderService; //预约服务
    private Button btn_submit;
    private RelativeLayout rl_time1; //预约时间
    private TextView tv_time1;
    private RelativeLayout rl_time2; //备选时间
    private TextView tv_time2;
    private RelativeLayout rl_time3; //备选时间
    private TextView tv_time3;
    private RelativeLayout rl_hospital; //预约医院
    private TextView tv_hospital;
    private String id; //医院id
    private EditText et_departments;   //科室

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_booking_hospital);

        initView();
    }

    private void initView() {
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rl_time1 = (RelativeLayout) findViewById(R.id.rl_time1);
        rl_time2 = (RelativeLayout) findViewById(R.id.rl_time2);
        rl_time3 = (RelativeLayout) findViewById(R.id.rl_time3);
        rl_hospital = (RelativeLayout) findViewById(R.id.rl_hospital);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        tv_hospital = (TextView) findViewById(R.id.tv_hospital);
        et_departments = (EditText) findViewById(R.id.et_departments);

        mBtnBack.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rl_time1.setOnClickListener(this);
        rl_time2.setOnClickListener(this);
        rl_time3.setOnClickListener(this);
        rl_hospital.setOnClickListener(this);
    }

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
            case R.id.rl_time2: //备选时间1
                showDatePickerDialog(2);
                break;
            case R.id.rl_time3: //备选时间2
                showDatePickerDialog(3);
                break;
            case R.id.rl_hospital: //预约医院
                Intent intent = new Intent(this, BookingHospitalListActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            id = data.getStringExtra("id");
            tv_hospital.setText(id);
        }
    }

    private void submit() {
        //hospitalId:16102616045315630527   北京协和医院
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String departments = et_departments.getText().toString();
        HtmlRequest.submitBookingHospital(this, userId, id, departments, "", "", "", "", "", "", "", "", "", new BaseRequester.OnRequestListener() {
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
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setDateDialog(new DatePickDialog.MyCallback() {

            public void processTime(Dialog ad, String selectedTime) {
                //如2016年11月30日
//                String formatTime = selectedTime.replace("年", "-").replace("月", "-").replace("日", "");
                if (tv_time1 != null && isTimeValue(selectedTime)) {
                    //选择的是正确的时间
                    if (type == 1) {
                        //点击的是预约时间
                        tv_time1.setText(selectedTime);
                    } else if (type == 2) {
                        //点击的是上面的备选时间
                        tv_time2.setText(selectedTime);
                    } else if (type == 3) {
                        //点击的是下面的备选时间
                        tv_time3.setText(selectedTime);
                    }
                }
                ad.dismiss();
                ad = null;
            }
        });
    }

    private boolean isTimeValue(String selectedTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        try {
            Date selectDate = simpleDateFormat.parse(selectedTime);

            //获取今天的0时 时间戳
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);

            if (cal.getTimeInMillis() > selectDate.getTime()) {
                //选择的时间必须是从今天开始包含今天
                Toast.makeText(SubBookingHospitalActivity.this, "时间只能是今天或今天以后", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

}
