package com.jdhui.act.ac;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.jdhui.uitls.IdCardCheckUtils;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

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
    private String hospitalId; //医院id
    private String hospitalName;//医院名称
    private EditText et_departments;   //科室
    private EditText et_doctor; //预约医生
    private EditText et_illness; //主诉病情
    private EditText et_name;//预约人
    private EditText et_security_num;//社保号
    private EditText et_phone;
    private EditText et_idno;
    private String formatTime1;//用户选择的预约时间
    private String formatTime2;//用户选择的备选时间1
    private String formatTime3;//用户选择的备选时间2
    private long currentTime = System.currentTimeMillis();

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
        et_doctor = (EditText) findViewById(R.id.et_doctor);
        et_illness = (EditText) findViewById(R.id.et_illness);
        et_name = (EditText) findViewById(R.id.et_name);
        et_security_num = (EditText) findViewById(R.id.et_security_num);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_idno = (EditText) findViewById(R.id.et_idno);

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
            hospitalId = data.getStringExtra("id");
            hospitalName = data.getStringExtra("name");
            tv_hospital.setText(hospitalName);
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
        String doctor = et_doctor.getText().toString();
        String illness = et_illness.getText().toString();
        String name = et_name.getText().toString();
        String securityNum = et_security_num.getText().toString();
        String phone = et_phone.getText().toString();
        String idNo = et_idno.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入预约人", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(securityNum)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入预约人社保号码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idNo == null || TextUtils.isEmpty(idNo)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入预约人身份证号", Toast.LENGTH_SHORT).show();
            return;
        } else if (!IdCardCheckUtils.isIdCard(idNo.toUpperCase())) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入预约人电话", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isMobileNO(phone)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(formatTime1)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请选择预约时间", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(hospitalId)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请选择预约医院", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(departments)) {
            Toast.makeText(SubBookingHospitalActivity.this, "请输入预约科室", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.submitBookingHospital(this, userId, hospitalId, departments, doctor, formatTime1, formatTime2, formatTime3, illness, name, securityNum, phone, idNo, new BaseRequester.OnRequestListener() {
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
                if (tv_time1 != null && isTimeValue(selectedTime)) {
                    //选择的是正确的时间
                    String time = selectedTime.replace("年", "-").replace("月", "-").replace("日", "");
                    if (type == 1) {
                        //点击的是预约时间
                        formatTime1 = time;
                        if(formatTime1.length()>16){
                            tv_time1.setText(formatTime1.substring(0,16));
                        }

                    } else if (type == 2) {
                        //点击的是上面的备选时间
                        formatTime2 = time;
                        if(formatTime2.length()>16){
                            tv_time2.setText(formatTime2.substring(0,16));
                        }

                    } else if (type == 3) {
                        //点击的是下面的备选时间
                        formatTime3 = time;
                        if(formatTime3.length()>16){
                            tv_time3.setText(formatTime3.substring(0,16));
                        }

                    }
                }
                ad.dismiss();
                ad = null;
            }
        });
    }

    private boolean isTimeValue(String selectedTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        try {
            Date selectDate = simpleDateFormat.parse(selectedTime);

            /*//获取今天的0时 时间戳
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);*/


            if (currentTime > selectDate.getTime()) {
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
