package com.jdhui.act.ac;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.act.MainActivity;
import com.jdhui.bean.mybean.GetGolfInfo2B;
import com.jdhui.bean.mybean.SubBookingShip2B;
import com.jdhui.bean.mybean.SubPhotography2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.StringUtil;

/**
 * 服务--提交摄影预约
 */
public class SubmitPhotographyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText et_name; // 预约人
    private EditText et_phone; // 联系电话
    private EditText et_financial_planner; // 专属理财师
    private Button btn_submit; //提交预约 按钮
//    private String userName;
//    private String mobile;
//    private String financialPlanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_sub_booking_photography);

        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_financial_planner = (EditText) findViewById(R.id.et_financial_planner);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        iv_back.setOnClickListener(this);
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
        }
    }

    /**
     * 提交预约 调的接口
     */
    private void submit() {
        String userName = et_name.getText().toString();
        String mobile = et_phone.getText().toString();
        String financialPlanner = et_financial_planner.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(SubmitPhotographyActivity.this, "请输入预约人", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(SubmitPhotographyActivity.this, "请输入联系电话", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isMobileNO(mobile)) {
            Toast.makeText(SubmitPhotographyActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(financialPlanner)) {
            Toast.makeText(SubmitPhotographyActivity.this, "请输入专属理财师", Toast.LENGTH_SHORT).show();
            return;
        }

        HtmlRequest.submitPhotography(this, userName, mobile, financialPlanner, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params == null) {
                    Toast.makeText(SubmitPhotographyActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }
                SubPhotography2B photographyData = (SubPhotography2B) params.result;
                if (photographyData != null) {
                    if (Boolean.parseBoolean(photographyData.getFlag())) {
                        Toast.makeText(SubmitPhotographyActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SubmitPhotographyActivity.this, MainActivity.class);
                        intent.putExtra("tab", 2);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubmitPhotographyActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

}
