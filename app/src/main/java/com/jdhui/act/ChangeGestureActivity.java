package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

/**
 *
 */
public class ChangeGestureActivity extends BaseActivity implements
		OnClickListener {

	private TextView t_phone;
	private Button btnOk;
	private EditText edtPWD;
	private String from = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		baseSetContentView(R.layout.activity_changegesture);
		from = getIntent().getExtras().getString("from");
		initView();
		initData();
	}
	private void initView() {
		t_phone = (TextView) findViewById(R.id.changegesture_phonenum);
		btnOk = (Button) findViewById(R.id.changegesture_next);
		edtPWD = (EditText) findViewById(R.id.changegesture_pwd);
		btnOk.setOnClickListener(this);
		
		/*if(netHint_2!=null){
			netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;*/
	}

	private void initData() {
		try {
			t_phone.setText(StringUtil.replaceSubString(DESUtil
					.decrypt(PreferenceUtil.getPhone())));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changegesture_next:
			String edt = edtPWD.getText().toString();
			if (!TextUtils.isEmpty(edt)) {
				try {
					if (edt.equals(DESUtil.decrypt(PreferenceUtil
							.getAutoLoginPwd()))) {
						Intent i = new Intent();
						i.setClass(ChangeGestureActivity.this,
								GestureEditActivity.class);
						if (from.equals(ApplicationConsts.ACTIVITY_GESVERIFY)) {
							i.putExtra("comeflag", 3);
							i.putExtra("title", R.string.title_changepwd);
//							setResult(RESULT_OK);
						} else {
							i.putExtra("comeflag", 2);
							i.putExtra("title", R.string.title_changegesture);
							i.putExtra("message", "忘记手势密码");
						}
						startActivity(i);
						ChangeGestureActivity.this.finish();
					} else {
						Toast.makeText(ChangeGestureActivity.this, "密码不正确",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(ChangeGestureActivity.this, "密码不能空",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

}
