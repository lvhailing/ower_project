package com.jdhui.act;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.jdhui.ApplicationConsts;
import com.jdhui.R;
import com.jdhui.adapter.SplashAdapter;
import com.jdhui.fragment.SplashFragment1;
import com.jdhui.fragment.SplashFragment2;
import com.jdhui.fragment.SplashFragment3;
import com.jdhui.push.Utils;
import com.jdhui.uitls.PreferenceUtil;

public class SplashActivity extends FragmentActivity {

    private Thread mThread;
    private MyHandler mHandler;
    private MyRunnable mRunnable;

    private static String TAG = "splashactivity";

    private ArrayList<Fragment> fgList;
    private ImageView[] indicator_imgs = new ImageView[3];// 存放引到图片数组

    SplashFragment1 fg1 = new SplashFragment1();
    SplashFragment2 fg2 = new SplashFragment2();
    SplashFragment3 fg3 = new SplashFragment3();
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView(PreferenceUtil.isFirst());
        Utils.logStringCache = Utils.getLogText(getApplicationContext());
//		if (PreferenceUtil.isPushEnable()) {
//			PushManager.startWork(getApplicationContext(),
//					PushConstants.LOGIN_TYPE_API_KEY,
//					Utils.getMetaValue(SplashActivity.this, "api_key"));
//		}
        /*Resources resource = this.getResources();
        String pkgName = this.getPackageName();
		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1,
		// cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				getApplicationContext(), resource.getIdentifier(
						"notification_custom_builder", "layout", pkgName),
				resource.getIdentifier("notification_icon", "id", pkgName),
				resource.getIdentifier("notification_title", "id", pkgName),
				resource.getIdentifier("notification_text", "id", pkgName));
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		cBuilder.setLayoutDrawable(resource.getIdentifier(
				"simple_notification_icon", "drawable", pkgName));
		PushManager.setNotificationBuilder(this, 1, cBuilder);*/

    }

    private void initView(boolean is) {
        ImageView iv = (ImageView) findViewById(R.id.splash_img);
        mViewPager = (ViewPager) findViewById(R.id.splashviewpager);

        ImageView imgView;
        View v = findViewById(R.id.splash_indicator);// 线性水平布局，负责动态调整导航图标
        if (false) {
            iv.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            fgList = new ArrayList<Fragment>();
            for (int i = 0; i < 3; i++) {
                imgView = new ImageView(this);
                LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10, 10);
                params_linear.setMargins(7, 10, 7, 10);
                imgView.setLayoutParams(params_linear);
                indicator_imgs[i] = imgView;
                if (i == 0) { // 初始化第一个为选中状态
                    indicator_imgs[i].setBackgroundResource(R.drawable.indicator_focused);
                } else {
                    indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
                }
                ((ViewGroup) v).addView(indicator_imgs[i]);
            }

            fgList.add(fg1);
            fgList.add(fg2);
            fgList.add(fg3);
            SplashAdapter mAdapter = new SplashAdapter(getSupportFragmentManager(), fgList);
            mAdapter.setData(fgList);
            mViewPager.setAdapter(mAdapter);
            // 绑定动作监听器：如翻页的动画
            mViewPager.setOnPageChangeListener(new MyListener());
        } else {
            iv.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            iv.setBackgroundResource(R.mipmap.splash);
            mHandler = new MyHandler();
            mRunnable = new MyRunnable();
            mThread = new Thread(mRunnable);
            mThread.start();
        }
    }

    @Override
    protected void onDestroy() {
        fg1 = null;
        fg2 = null;
        fg3 = null;
        if (fgList != null) {
            fgList.clear();
            fgList = null;
        }
        PreferenceUtil.setFirst(false);
        unbindDrawables(mViewPager);
        System.gc();
        super.onDestroy();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    /**
     * 动作监听器，可异步加载图片
     */
    private class MyListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                // new MyAdapter(null).notifyDataSetChanged();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // 改变所有导航的背景图片为：未选中
            for (int i = 0; i < indicator_imgs.length; i++) {
                indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
            }
            // 改变当前背景图片为：选中
            indicator_imgs[position].setBackgroundResource(R.drawable.indicator_focused);
//			if(position==2){
//				btn.setVisibility(View.VISIBLE);
//				btn.setClickable(true);
//			}else {
//				btn.setVisibility(View.GONE);
//				btn.setClickable(false);
//			}
        }
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN:
                    if (PreferenceUtil.isGestureOpen()) {
                        Intent i = new Intent(SplashActivity.this, GestureVerifyActivity.class);
                        i.putExtra("from", ApplicationConsts.ACTIVITY_SPLASH);
                        i.putExtra("title", "手势密码登录");
                        i.putExtra("message", "请画出手势密码解锁");
                        startActivity(i);
                    } else {
                        Intent iMain = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(iMain);
                    }
                    finish();
                    break;
                case NOLOGIN:
                    Intent iLogin = new Intent(SplashActivity.this, LoginActivity.class);
                /*Intent iLogin= new Intent(SplashActivity.this,
                        MainActivity.class);*/
                    iLogin.putExtra("tomain", "6");
                    startActivity(iLogin);
                    finish();
                    break;
                case NOPWD:
                    //判断是否答题  false:未答题 true:已答题
                   /* Toast.makeText(SplashActivity.this, PreferenceUtil.getIsAnswer()+"",
                            Toast.LENGTH_LONG).show();*/

                    if (PreferenceUtil.getIsAnswer()) {
                        //是否做过合格投资者判定  false:未做合格投资者判定 true:做过合格投资者判定
                        if (PreferenceUtil.getIsInvestor()) {
                            if (PreferenceUtil.isGestureOpen()) {
                                Toast.makeText(SplashActivity.this, "您还没有设置手势密码，请先设置手势密码", Toast.LENGTH_LONG).show();
                                Intent i_nopwd = new Intent();
                                i_nopwd.putExtra("comeflag", 0);
                                //控制登录后没有设置手势密码，点击设置手势密码的返回，跳到主页
                                i_nopwd.putExtra("back_from_splah", "back_from_splah");
                                i_nopwd.putExtra("title", R.string.title_gestureset);
                                i_nopwd.setClass(SplashActivity.this, GestureEditActivity.class);
                                startActivity(i_nopwd);
                            } else {
                                Intent i_Main = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(i_Main);
                            }
                        } else {
                            //判断账户资产是否大于300万  false:判定为非合格投资者 true:判定为合格投资者
                            if (PreferenceUtil.getTotalAmount()) {
                                Intent i_commitment = new Intent(SplashActivity.this, WebSurveyActivity.class);
                                i_commitment.putExtra("type", WebSurveyActivity.WEBTYPE_INVESTOR_COMMITMENT);
                                i_commitment.putExtra("title", "投资者承诺函");
                                i_commitment.putExtra("btnInfo", "我同意该承诺");
                                startActivity(i_commitment);
                            } else {
                                Intent i_judge = new Intent(SplashActivity.this, WebInvestorJudgeActivity.class);
                                i_judge.putExtra("type", WebInvestorJudgeActivity.WEBTYPE_INVESTOR_JUDGE);
                                i_judge.putExtra("title", "投资者判定");
                                i_judge.putExtra("btnInfo", "400-80-88888");
                                startActivity(i_judge);
                            }

                        }
                    } else {
                        Intent i_survey = new Intent(SplashActivity.this, WebSurveyActivity.class);
                        i_survey.putExtra("type", WebSurveyActivity.WEBTYPE_SURVEY);
                        i_survey.putExtra("title", "问卷调查");
                        i_survey.putExtra("btnInfo", "开始答题");
                        startActivity(i_survey);
                    }
                    finish();
                    break;
                default:
                    break;
            }
        }

    }

    private static final int LOGIN = 0;
    private static final int NOLOGIN = 1;
    private static final int NOPWD = 2;

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Message msg = new Message();
                if (PreferenceUtil.isLogin()) {
                    if (TextUtils.isEmpty(PreferenceUtil.getGesturePwd())) {
                        msg.what = NOPWD; // 如果未设置
                    } else {
                        msg.what = LOGIN; // 已设置手势密码
                    }
                } else {
                    msg.what = NOLOGIN; // 未登录
                }
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";

    /**
     * 判断应用是否初次加载，读取SharedPreferences中的guide_activity字段
     *
     * @param context
     * @param className
     * @return
     */
    private boolean isFirstEnter(Context context, String className) {
        if (context == null || className == null || "".equalsIgnoreCase(className)) {
            return false;
        }
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE).getString(KEY_GUIDE_ACTIVITY, "");// 取得所有类名 如 com.my.MainActivity
        if (mResultStr.equalsIgnoreCase("false")) {
            return false;
        } else {
            return true;
        }
    }


}
