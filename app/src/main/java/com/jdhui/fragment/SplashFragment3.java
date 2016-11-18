package com.jdhui.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.jdhui.R;
import com.jdhui.act.MainActivity;
import com.jdhui.anim.SceneAnimation;
import com.jdhui.uitls.PreferenceUtil;

public class SplashFragment3 extends Fragment implements OnClickListener {
	private ViewGroup vp;
	private ImageView animationIV;
	private AnimationDrawable animationDrawable;
	private Button btn;
	private SceneAnimation mAnimation;
	private final int[] pFrameRess = { R.drawable.splash_three1,
			R.drawable.splash_three2, R.drawable.splash_three3,
			R.drawable.splash_three4, R.drawable.splash_three5,
			R.drawable.splash_three6, R.drawable.splash_three7,
			R.drawable.splash_three8, R.drawable.splash_three9,
			R.drawable.splash_three10, R.drawable.splash_three11,
			R.drawable.splash_three12, R.drawable.splash_three13 };
	private final int[] pDuration = { 150, 150, 150, 150, 150, 150, 150, 150,
			150, 150, 150, 150, 150 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		vp = (ViewGroup) inflater.inflate(R.layout.fragment_splash3, container,
				false);
		animationIV = (ImageView) vp.findViewById(R.id.splash_threetop);
		btn = (Button) vp.findViewById(R.id.splash_btn);
		btn.setOnClickListener(this);
		mAnimation = new SceneAnimation(animationIV, pFrameRess, pDuration);
		// mHandler = new MyHandler();
		// mthread = new Thread(myRunnable);
		// mthread.start();
		return vp;
	}

	// private MyHandler mHandler;
	// private Thread mthread;
	//
	// class MyHandler extends Handler {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// if (msg.what == 1) {
	// animationIV.setBackgroundResource(R.drawable.splash_three1);
	// } else if (msg.what == 2) {
	// animationIV.setBackgroundResource(R.drawable.splash_three2);
	// } else if (msg.what == 3) {
	// animationIV.setBackgroundResource(R.drawable.splash_three3);
	// } else if (msg.what == 4) {
	// animationIV.setBackgroundResource(R.drawable.splash_three4);
	// } else if (msg.what == 5) {
	// animationIV.setBackgroundResource(R.drawable.splash_three5);
	// } else if (msg.what == 6) {
	// animationIV.setBackgroundResource(R.drawable.splash_three6);
	// } else if (msg.what == 7) {
	// animationIV.setBackgroundResource(R.drawable.splash_three7);
	// } else if (msg.what == 8) {
	// animationIV.setBackgroundResource(R.drawable.splash_three8);
	// } else if (msg.what == 9) {
	// animationIV.setBackgroundResource(R.drawable.splash_three9);
	// } else if (msg.what == 10) {
	// animationIV.setBackgroundResource(R.drawable.splash_three10);
	// } else if (msg.what == 11) {
	// animationIV.setBackgroundResource(R.drawable.splash_three11);
	// } else if (msg.what == 12) {
	// animationIV.setBackgroundResource(R.drawable.splash_three12);
	// }
	// }
	// }
	//
	// Runnable myRunnable = new Runnable() {
	//
	// @Override
	// public void run() {
	// int i = 0;
	// while (i < 12) {
	// Message msg = new Message();
	// i++;
	// msg.what = i;
	// mHandler.sendMessage(msg);
	// try {
	// Thread.sleep(150);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// };

	@Override
	public void onPause() {
		super.onPause();
		mAnimation.stopPlay();
		// animationDrawable.stop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// animationDrawable = null;
		// mHandler.removeCallbacks(myRunnable);
		animationIV = null;
		unbindDrawables(vp);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAnimation.startPaly(false);
		// if (animationDrawable == null) {
		// animationDrawable = (AnimationDrawable) animationIV.getDrawable();
		// }
		// animationDrawable.start();
	}

	@Override
	public void onClick(View v) {
		Intent iMain = new Intent(getActivity(), MainActivity.class);
		startActivity(iMain);
		PreferenceUtil.setFirst(false);
		getActivity().finish();
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

}
