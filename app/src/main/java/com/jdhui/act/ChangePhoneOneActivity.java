package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

/**
 * 修改手机号
 */
public class ChangePhoneOneActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_back;
    private EditText mEditInput;
    private ImageView mBtnDelete;
    private Button mBtnNext;
    private TextView mTvInfo;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_change_phone_one);
        initView();
    }
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mEditInput= (EditText) findViewById(R.id.id_inut_pw);
        mTvInfo= (TextView) findViewById(R.id.id_change_phone_one_info);
        mBtnDelete= (ImageView) findViewById(R.id.id_btn_delete);
        mBtnNext= (Button) findViewById(R.id.id_btn_next);
        try {
            phone= DESUtil.decrypt(PreferenceUtil.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (phone!=null){
        String phoneNum=StringUtil.replaceSubString(phone);
        mTvInfo.setText("当前手机号："+ phoneNum);
        }
        iv_back.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnNext.setClickable(false);
        mEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    mBtnDelete.setVisibility(View.GONE);
                    mBtnNext.setBackgroundResource(R.drawable.shape_button_gray_gray);
                    mBtnNext.setClickable(false);
                }else {
                    mBtnDelete.setVisibility(View.VISIBLE);
                    mBtnNext.setBackgroundResource(R.drawable.shape_button_red);
                    mBtnNext.setClickable(true);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.id_btn_delete:
                mEditInput.setText("");
                break;
            case R.id.id_btn_next:
                String phone=mEditInput.getText().toString();
                if (StringUtil.isMobileNO(phone)) {
                    Intent i_next=new Intent(this,ChangePhoneTwoActivity.class);
                    i_next.putExtra("phone",phone);
                    startActivity(i_next);
                } else {
                    Toast.makeText(ChangePhoneOneActivity.this, "请输入正确的手机号",
                            Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }
}
