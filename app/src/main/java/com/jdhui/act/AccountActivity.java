package com.jdhui.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.net.UserLoadout;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 账户中心
 */
public class AccountActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton mImageButton; //手势密码 开关按钮
    private RelativeLayout mLayoutMyInfo; //查看或修改个人信息 布局
    private RelativeLayout mLayoutModifyPhone; //修改手机号 布局
    private RelativeLayout mLayoutChangeGesture; //修改手势密码 布局
    private RelativeLayout mLayoutChangePW; //修改登录密码 布局
    private ImageView mImgChangeGesTabLines;
    private ImageView mImgBack;
    private Button account_btn_exit;
    private String tomain = null;
    private String userInfoId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_account);
        initView();
        registerBoradcastReceiver();
    }

    private void initView() {
        userInfoId = getIntent().getStringExtra("userInfoId");

        mImgBack = (ImageView) findViewById(R.id.id_img_back);
        mLayoutMyInfo = (RelativeLayout) findViewById(R.id.id_account_layout_modify_info);
        mImageButton = (ImageButton) findViewById(R.id.accountset_recive);
        mLayoutChangeGesture = (RelativeLayout) findViewById(R.id.id_account_layout_gesture_change);
        mImgChangeGesTabLines = (ImageView) findViewById(R.id.id_account_img_gesture_edit_lines);
        mLayoutModifyPhone = (RelativeLayout) findViewById(R.id.id_account_layout_modify_phone);
        mLayoutChangePW = (RelativeLayout) findViewById(R.id.account_layout_change_password);
        account_btn_exit = (Button) findViewById(R.id.account_btn_exit);

        if (PreferenceUtil.isGestureChose()) {
            mImageButton.setImageResource(R.drawable.message2);
            mLayoutChangeGesture.setVisibility(View.VISIBLE);
            mImgChangeGesTabLines.setVisibility(View.VISIBLE);
        } else {
            mImageButton.setImageResource(R.drawable.message1);
            mLayoutChangeGesture.setVisibility(View.GONE);
            mImgChangeGesTabLines.setVisibility(View.GONE);
        }

        mImgBack.setOnClickListener(this);
        mLayoutMyInfo.setOnClickListener(this);
        mImageButton.setOnClickListener(this);
        mLayoutChangeGesture.setOnClickListener(this);
        mLayoutModifyPhone.setOnClickListener(this);
        mLayoutChangePW.setOnClickListener(this);
        account_btn_exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
            case R.id.id_account_layout_modify_info:
                Intent i_myInfo = new Intent(this, MyInfoActivity.class);
                i_myInfo.putExtra("userInfoId", userInfoId);
                startActivity(i_myInfo);
                break;
            case R.id.accountset_recive://帐户中心页面  手势密码的开关按钮
                if (PreferenceUtil.isGestureChose()) {
                    Intent i = new Intent(this, GestureVerifyActivity.class);
                    i.putExtra("from", ApplicationConsts.ACTIVITY_ACCOUNT);
                    i.putExtra("title", "手势密码登录");
                    i.putExtra("message", "请画出手势密码解锁");
                    startActivityForResult(i, 1000);
                } else {
                    Intent i = new Intent(this, GestureEditActivity.class);
                    i.putExtra("comeflag", 4);
                    if (tomain != null) {
                        i.putExtra("tomain", tomain);
                    }
                    i.putExtra("title", R.string.title_gestureset);
                    startActivity(i);

                }
                PreferenceUtil.setFirstLogin(false);
                break;
            case R.id.id_account_layout_gesture_change: //修改手势密码 布局
                Intent i = new Intent(this, GestureVerifyActivity.class);
                i.putExtra("from", ApplicationConsts.ACTIVITY_CHANGE_GESTURE);
                i.putExtra("title", "修改手势密码");
                i.putExtra("message", "请绘制旧的解锁图案");
                startActivity(i);
                break;
            case R.id.id_account_layout_modify_phone: //修改手机号 布局
                Intent i_modify_phone = new Intent(this, VerifyPassWordActivity.class);
                startActivity(i_modify_phone);
                break;
            case R.id.account_layout_change_password: //修改登录密码 布局
                Intent i_changePW = new Intent(this, ChangePasswordActivity.class);
                startActivity(i_changePW);
                break;
            case R.id.account_btn_exit: //退出登录
                UserLoadout out = new UserLoadout(AccountActivity.this);
                out.requestData();
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            if (resultCode == 2000) {
                PreferenceUtil.setGestureChose(false);
                mImageButton.setImageResource(R.drawable.message1);
                mLayoutChangeGesture.setVisibility(View.GONE);
                mImgChangeGesTabLines.setVisibility(View.GONE);
            }
        }
    }

    private ReceiveBroadCast receiveBroadCast; // 广播实例
    String myActionName = "gestureChooseState";

    // 注册广播
    public void registerBoradcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(myActionName); // 只有持有相同的action的接受者才能接收此广播
        this.registerReceiver(receiveBroadCast, filter);
    }

    // 定义一个BroadcastReceiver广播接收类：
    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String actionName = data.getAction();
            if (actionName.equals(myActionName)) {
                PreferenceUtil.setGestureChose(true);
                mImageButton.setImageResource(R.drawable.message2);
                mLayoutChangeGesture.setVisibility(View.VISIBLE);
                mImgChangeGesTabLines.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiveBroadCast);
        super.onDestroy();
    }
}
