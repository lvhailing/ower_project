package com.jdhui.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
// 版本号 页面
public class VersionNumberActivity extends BaseActivity implements OnClickListener{
	private EditText et_advice_input;
	private Button btn_setting_advice;
	private ImageView iv_back;
	private TextView txt_version_number;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_version_number);
		initView();

	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		txt_version_number= (TextView) findViewById( R.id.id_txt_version_number);
		txt_version_number.setText("版本号"+getIntent().getExtras().getString("numeber"));

		iv_back.setOnClickListener(this);
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
			case R.id.iv_back:
				finish();
				break;
		}
	}
}
