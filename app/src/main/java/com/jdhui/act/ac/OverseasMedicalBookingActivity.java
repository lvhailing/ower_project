package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.WebActivity;

/**
 *  海外医疗
 *  Created by rui on 2017/7/21.
 */
public class OverseasMedicalBookingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RelativeLayout rl_overseas_medical_check; // 海外体检
    private RelativeLayout rl_overseas_medical_hospital; // 海外就医
    private RelativeLayout rl_overseas_medical_consultation; // 国际远程会诊
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_overseas_medical);

        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rl_overseas_medical_check = (RelativeLayout) findViewById(R.id.rl_overseas_medical_check);
        rl_overseas_medical_hospital = (RelativeLayout) findViewById(R.id.rl_overseas_medical_hospital);
        rl_overseas_medical_consultation = (RelativeLayout) findViewById(R.id.rl_overseas_medical_consultation);

        iv_back.setOnClickListener(this);
        rl_overseas_medical_check.setOnClickListener(this);
        rl_overseas_medical_hospital.setOnClickListener(this);
        rl_overseas_medical_consultation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_overseas_medical_check: // 海外体检
                intent = new Intent(OverseasMedicalBookingActivity.this, WebActivity.class);
                intent.putExtra("type", WebActivity.WEBTYPE_HEALTH_CHECK);
                intent.putExtra("title", "海外体检");
                startActivity(intent);
                break;
            case R.id.rl_overseas_medical_hospital: // 海外就医
                intent = new Intent(OverseasMedicalBookingActivity.this, WebActivity.class);
                intent.putExtra("type", WebActivity.WEBTYPE_OVERSEA_HOSPITAL);
                intent.putExtra("title", "海外就医");
                startActivity(intent);
                break;
            case R.id.rl_overseas_medical_consultation: // 国际远程会诊
                intent = new Intent(OverseasMedicalBookingActivity.this, WebActivity.class);
                intent.putExtra("type", WebActivity.WEBTYPE_INTERNATIONAL_CONSULTATION);
                intent.putExtra("title", "海外远程会议");
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
