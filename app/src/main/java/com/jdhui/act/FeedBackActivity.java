package com.jdhui.act;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultCodeContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.CallServiceDialog;

public class FeedBackActivity extends BaseActivity implements OnClickListener{
	private EditText et_advice_input;
	private Button btn_setting_advice;
	private ImageView mImgBack;
	private TextView txtCall;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_feed_back);
		initView();
		initData();
		
	}
	
	private void initData() {
		
	}

	private void initView() {

		ActivityStack stack = ActivityStack.getActivityManage();
		stack.addActivity(this);

		mImgBack= (ImageView) findViewById(R.id.id_img_back);
		et_advice_input = (EditText) findViewById(R.id.et_advice_input);
		btn_setting_advice = (Button) findViewById(R.id.btn_setting_advice);
		txtCall= (TextView) findViewById(R.id.id_txt_call);
		String str1="如有疑问，您也可以直接联系:";
		String str2=str1+ getString(R.string.tellphone_num_format);
		String str3=str2+"";
		txtCall.setText(StringUtil.setTextStyle(this, str1, str2, str3,
				R.color.gray_dark, R.color.orange, R.color.gray_dark,
				13, 13, 13, 0, 0, 0));
		txtCall.setOnClickListener(this);
		mImgBack.setOnClickListener(this);
		btn_setting_advice.setOnClickListener(this);
		btn_setting_advice.setClickable(false);
		et_advice_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				if (TextUtils.isEmpty(editable)) {
					btn_setting_advice.setBackgroundResource(R.drawable.shape_button_gray_gray);
					btn_setting_advice.setClickable(false);
				} else {
					btn_setting_advice.setBackgroundResource(R.drawable.shape_button_orange);
					btn_setting_advice.setClickable(true);
				}

			}
		});

		
	}
	
	//意见反馈
	public void requestAdviceData(){
		String content = et_advice_input.getText().toString();
		String userId = null;
		try {
			userId = DESUtil.decrypt(PreferenceUtil.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HtmlRequest.getAdviceData(FeedBackActivity.this, userId, content,
				new BaseRequester.OnRequestListener() {
					@Override
					public void onRequestFinished(BaseParams params) {

						ResultCodeContentBean bean = (ResultCodeContentBean) params.result;
						if (bean != null) {
							if (Boolean.parseBoolean(bean.getFlag())) {
								Toast.makeText(FeedBackActivity.this, "意见反馈成功", Toast.LENGTH_LONG).show();
								finish();
							} else {
								Toast.makeText(FeedBackActivity.this, "意见反馈失败，请您检查提交信息", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(FeedBackActivity.this, "加载失败，请确认网络通畅",
									Toast.LENGTH_LONG).show();
						}

					}
				});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.id_img_back:
				finish();
				break;
			case R.id.btn_setting_advice:
				requestAdviceData();
				break;
			case R.id.id_txt_call:
				CallServiceDialog dialog = new CallServiceDialog(this,
						new CallServiceDialog.OnCallServiceChanged() {

							@Override
							public void onConfim() {
								Intent intent = new Intent(Intent.ACTION_DIAL);
								Uri data = Uri.parse("tel:" + getString(R.string.tellphone_num));
								intent.setData(data);
								startActivity(intent);
							}

							@Override
							public void onCancel() {

							}
						}, "客服热线: \n "+getString(R.string.tellphone_num_format));
				dialog.show();
				break;
		}
	}
}
