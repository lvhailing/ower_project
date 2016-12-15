package com.jdhui.act.ac;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.MainActivity;
import com.jdhui.bean.mybean.GetGolfInfo2B;
import com.jdhui.bean.mybean.SubmitBookingHospital2B;
import com.jdhui.dialog.DatePickDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 服务--高尔夫球场地预约
 */
public class SubBookingGolfActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_name; //预约人
    private TextView tv_id_num; //身份证号
    private EditText et_together1;
    private EditText et_together2;
    private EditText et_phone; //联系电话
    private TextView tv_booking_time;
    private RelativeLayout rl_booking_time; //预约时间
    private TextView tv_venue_name; //场馆名称
    private TextView tv_tip; //提示语
    private ImageView mBtnBack;

    private LinearLayout ll_together1; //同行人1
    private LinearLayout ll_together2; //同行人2
    private View v_line1;
    private View v_line2;
    private Button btn_submit;
    private String id; //高尔夫球场Id
    private String golfName; //高尔夫球场名称
    private String golfRights; //高尔夫球场名称
    private String userName;//预约人
    private String userIdNo;//身份证号
    private TextView tv_id_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.ac_booking_golf);

        initView();
        initData();
    }

    private void initData() {
        requestData();
        id = getIntent().getStringExtra("id");
        golfRights = getIntent().getStringExtra("golfRights");
        golfName = getIntent().getStringExtra("name");

        tv_venue_name = (TextView) findViewById(R.id.tv_venue_name);
        tv_venue_name.setText(golfName);//设置高尔夫球场名称
        if (golfRights.equals("VIP")) {
            //只有VIP权限时才显示同行人
            ll_together1.setVisibility(View.VISIBLE);
            ll_together2.setVisibility(View.VISIBLE);
            v_line1.setVisibility(View.VISIBLE);
            v_line2.setVisibility(View.VISIBLE);
        } else {
            ll_together1.setVisibility(View.GONE);
            ll_together2.setVisibility(View.GONE);
            v_line1.setVisibility(View.GONE);
            v_line2.setVisibility(View.GONE);
        }
    }

    private void requestData() {
        HtmlRequest.getInfo(this, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    GetGolfInfo2B info2B = (GetGolfInfo2B) params.result;
                    if (info2B != null) {
                        userName = info2B.getUserName();
                        userIdNo = info2B.getUserIdNo();
                        String idType = info2B.getIdType();
                        tv_name.setText(userName);
                        if (idType.equals("idCard")) { //身份证
                            tv_id_name.setText("身份证");
                            tv_id_num.setText(StringUtil.replaceSubStringID(userIdNo));
                        } else if (idType.equals("passport")) { //护照
                            tv_id_name.setText("护照");
                            tv_id_num.setText(userIdNo);
                        } else if (idType.equals("agencyCode")) { //机构代码
                            tv_id_name.setText("机构代码");
                            tv_id_num.setText(userIdNo);
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_id_name = (TextView) findViewById(R.id.tv_id_name);
        tv_id_num = (TextView) findViewById(R.id.tv_id_num);
        et_together1 = (EditText) findViewById(R.id.et_together1);
        et_together2 = (EditText) findViewById(R.id.et_together2);
        et_phone = (EditText) findViewById(R.id.et_phone);
        tv_venue_name = (TextView) findViewById(R.id.tv_venue_name);
        tv_booking_time = (TextView) findViewById(R.id.tv_booking_time);

        ll_together1 = (LinearLayout) findViewById(R.id.ll_together1);
        ll_together2 = (LinearLayout) findViewById(R.id.ll_together2);
        v_line1 = findViewById(R.id.v_line1);
        v_line2 = findViewById(R.id.v_line2);
        mBtnBack = (ImageView) findViewById(R.id.iv_back);
        rl_booking_time = (RelativeLayout) findViewById(R.id.rl_booking_time);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        mBtnBack.setOnClickListener(this);
        rl_booking_time.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
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
            case R.id.rl_booking_time: //预约时间
                showDatePickerDialog();
                break;
        }
    }

    private void submit() {
        String peersOne = et_together1.getText().toString();
        String peersTwo = et_together2.getText().toString();

        String clientPhone = et_phone.getText().toString();
        if (TextUtils.isEmpty(clientPhone)) {
            Toast.makeText(this, "请输入预约人电话", Toast.LENGTH_SHORT).show();
            return;
        }

        String bookingTime = tv_booking_time.getText().toString();
        if (TextUtils.isEmpty(bookingTime) || bookingTime.equals("请选择预约时间")) {
            Toast.makeText(this, "请选择预约时间", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.submitGolfDetail(this, bookingTime, clientPhone, id, peersOne, peersTwo, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    SubmitBookingHospital2B bookingHospital2B = (SubmitBookingHospital2B) params.result;
                    if (bookingHospital2B != null) {
                        if (!Boolean.parseBoolean(bookingHospital2B.getFlag())) {
                            Toast.makeText(SubBookingGolfActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SubBookingGolfActivity.this, MainActivity.class);
                            intent.putExtra("tab", 2);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SubBookingGolfActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SubBookingGolfActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickDialog dialog = new DatePickDialog(this);
        dialog.setDateDialog(new DatePickDialog.MyCallback() {

            public void processTime(Dialog ad, String selectedTime) {
                //如2016年11月30日
                String formatTime = selectedTime.replace("年", "-").replace("月", "-").replace("日", "");
                if (tv_booking_time != null && isTimeValue(formatTime)) {
                    //选择的是正确的时间
                    tv_booking_time.setText(formatTime);
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
                Toast.makeText(SubBookingGolfActivity.this, "时间只能是今天或今天以后", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

}
