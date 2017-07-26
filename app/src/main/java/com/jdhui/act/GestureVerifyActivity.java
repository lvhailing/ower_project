package com.jdhui.act;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.bean.ResultUserLoginContentBean;
import com.jdhui.net.UserLoadout;
import com.jdhui.net.UserLogin;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.CheckVersionDialog;
import com.jdhui.widget.GestureContentView;
import com.jdhui.widget.GestureDrawline;

import java.util.Observable;
import java.util.Observer;

/**
 * 手势绘制/校验界面
 */
public class GestureVerifyActivity extends BaseActivity implements android.view.View.OnClickListener, Observer {
    private TextView txtTitle;
    private TextView txtMessage;
    private ImageView iv_back;
    private TextView mTextTip;
    private FrameLayout mGestureContainer;
    private GestureContentView mGestureContentView;
    private TextView mTextForget;
    String s = null;
    private int current_num = 1;
    private static final int MAX_NUM = 5;
    public static final String TOMAIN = "6";

    private String from = null;
    private String titleName;
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_gesture_verify);

        from = getIntent().getExtras().getString("from");
        titleName = getIntent().getStringExtra("title");
        message = getIntent().getStringExtra("message");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserLogin.getInstance().addObserver(this);
        setUpViews();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        txtTitle = (TextView) findViewById(R.id.id_txt_title_verify);
        txtMessage = (TextView) findViewById(R.id.text_title_message);
        mTextTip = (TextView) findViewById(R.id.text_title_message);
        mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
        mTextForget = (TextView) findViewById(R.id.text_forget_gesture);

        txtTitle.setText(titleName);
        mTextTip.setText(message);

        iv_back.setOnClickListener(this);

		/*if(netHint_2!=null){
            netHint_2.setVisibility(View.GONE);
			llContent.setVisibility(View.VISIBLE);
		}
		netFail_2 = false;*/
    }

    private void setUpViews() {
        try {
            s = DESUtil.decrypt(PreferenceUtil.getGesturePwd());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (from.equals(ApplicationConsts.ACTIVITY_GESVERIFY)) {
            mTextForget.setVisibility(View.GONE);
        } else if (from.equals(ApplicationConsts.ACTIVITY_ACCOUNT)) {
            mTextForget.setText("忘记手势密码");
            mTextForget.setOnClickListener(this);
        } else if (from.equals(ApplicationConsts.ACTIVITY_SPLASH)) {
            iv_back.setVisibility(View.GONE);
            mTextForget.setText("忘记手势密码");
            mTextForget.setOnClickListener(this);
        } else if (from.equals(ApplicationConsts.ACTIVITY_GESEDIT)) {
            mTextForget.setText("忘记手势密码");
            mTextForget.setOnClickListener(this);
        } else if (from.equals(ApplicationConsts.ACTIVITY_CHANGE_GESTURE)) {
            mTextForget.setText("忘记手势密码");
            mTextForget.setOnClickListener(this);
        }
        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true, s, new GestureDrawline.GestureCallBack() {

            @Override
            public void onGestureCodeInput(String inputCode) {

            }

            @Override
            public void checkedSuccess() {
                mGestureContentView.clearDrawlineState(0L);
                try {
                    if (from.equals(ApplicationConsts.ACTIVITY_SPLASH)) {
                        GestureVerifyActivity.this.finish();
//								UserLogin.getInstance().userlogining(
//										getApplicationContext(),
//										DESUtil.decrypt(PreferenceUtil
//												.getAutoLoginAccount()),
//										DESUtil.decrypt(PreferenceUtil
//												.getAutoLoginPwd()), "");
                        Intent i = new Intent(GestureVerifyActivity.this, MainActivity.class);
                        startActivity(i);
                    } else if (from.equals(ApplicationConsts.ACTIVITY_ACCOUNT)) {
                        Intent i = new Intent(GestureVerifyActivity.this, AccountActivity.class);
                        setResult(2000, i);
                        finish();
                    } else if (from.equals(ApplicationConsts.ACTIVITY_GESEDIT)) {
                        Toast.makeText(GestureVerifyActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (from.equals(ApplicationConsts.ACTIVITY_GESVERIFY)) {
                        UserLogin.getInstance().userlogining(getApplicationContext(), DESUtil.decrypt(PreferenceUtil.getAutoLoginAccount()), DESUtil.decrypt(PreferenceUtil.getAutoLoginPwd()), "");
                        Intent i = new Intent(GestureVerifyActivity.this, MainActivity.class);
                        Toast.makeText(GestureVerifyActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    } else if (from.equals(ApplicationConsts.ACTIVITY_CHANGE_GESTURE)) {
                        GestureVerifyActivity.this.finish();
                        Intent i = new Intent(GestureVerifyActivity.this, GestureEditActivity.class);
                        //判断从账户设置手势密码，点击跳过，再次登录时不会关闭手势密码
                        i.putExtra("skip", "skip_from_account");
                        i.putExtra("title", R.string.title_gestureset);
                        i.putExtra("comeflag", 4);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void checkedFail() {
                if (current_num >= MAX_NUM) {
                    AlertDialog.Builder builder = new Builder(GestureVerifyActivity.this);
                    builder.setMessage("您输入的手势密码错误次数已达到最大次数，请使用登录密码进行登录");
                    builder.setTitle("密码错误");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            from = ApplicationConsts.ACTIVITY_GESVERIFY;
                            Intent intent = new Intent();
                            intent.setClass(GestureVerifyActivity.this, LoginActivity.class);
//											startActivityForResult(ilogin, 99);

                            intent.putExtra("tomain", TOMAIN);
                            PreferenceUtil.setFirstLogin(true);
                            PreferenceUtil.setGesturePwd("");
                            PreferenceUtil.setLogin(false);
                            startActivity(intent);
                            finish();

                        }
                    });
                    builder.create().show();
                } else {
                    mGestureContentView.clearDrawlineState(1300L);
                    mTextTip.setVisibility(View.VISIBLE);
                    String str1, str2, str3;
                    str1 = "密码错误,您还可以尝试";
                    str2 = str1 + (MAX_NUM - current_num);
                    str3 = str2 + "次";

                    mTextTip.setText(StringUtil.setTextStyle(GestureVerifyActivity.this, str1, str2, str3, R.color.txt_red, R.color.txt_red, R.color.txt_red, 16, 16, 16, 0, 0, 0));
                    // 左右移动动画
                    Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                    mTextTip.startAnimation(shakeAnimation);
                    current_num++;
                }
            }
        });
        android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.WRAP_CONTENT, android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mGestureContentView.setLayoutParams(params);
        // 设置手势解锁显示到哪个布局里面
        mGestureContentView.setParentView(mGestureContainer);
    }


    private String getProtectedMobile(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNumber.subSequence(0, 3));
        builder.append("****");
        builder.append(phoneNumber.subSequence(7, 11));
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_forget_gesture:
                CheckVersionDialog dialog = new CheckVersionDialog(GestureVerifyActivity.this, new CheckVersionDialog.OnCheckVersion() {

                    @Override
                    public void onConfim() {
                        UserLoadout out = new UserLoadout(GestureVerifyActivity.this);
                        out.requestData();

                        //确保登录后能进入到重新设置手势密码页面
                        PreferenceUtil.setFirstLogin(true);
                        PreferenceUtil.setGesturePwd("");
                        PreferenceUtil.setLogin(false);
                    }

                    @Override
                    public void onCancel() {

                    }
                }, "忘记手势密码，需要重新登录并设置手势密码");
                dialog.show();
                break;
            case R.id.iv_back:
                this.finish();
                break;
            default:
                break;
        }
    }

	/*public void dialog() {
        AlertDialog.Builder builder = new Builder(GestureVerifyActivity.this);
		builder.setMessage("忘记手势密码，需要重新登录并设置手势密码");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(from.equals(ApplicationConsts.ACTIVITY_SPLASH)||from.equals(ApplicationConsts.ACTIVITY_GESEDIT)){
					Intent intent=new Intent(GestureVerifyActivity.this,LoginActivity.class);
					intent.putExtra("tomain", TOMAIN);
					PreferenceUtil.setFirstLogin(true);
					PreferenceUtil.setGesturePwd("");
					PreferenceUtil.setLogin(false);
					startActivity(intent);
					finish();
				}else{

				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent();
                i.setClass(GestureVerifyActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        } else if (requestCode == 88) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ResultUserLoginContentBean bean;

    @Override
    public void update(Observable observable, Object data) {
        bean = (ResultUserLoginContentBean) data;
        if (bean != null) {
            if (Boolean.parseBoolean(bean.getFlag())) {
                if (from.equals(ApplicationConsts.ACTIVITY_SPLASH)) {
                    finish();
                }
            }
        }
    }

}
