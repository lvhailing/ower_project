package com.jdhui.act.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jdhui.R;
import com.jdhui.act.BaseActivity;
import com.jdhui.adapter.PhotographyProductionAdapter;
import com.jdhui.widget.ZoomOutPageTransformer;

import java.util.ArrayList;

/**
 * Created by junde on 2017/10/16.
 */
public class OrderPhotographyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ViewPager vp;
    private LinearLayout vp_point;
    private ArrayList<ImageView> imageList = null;
    private int lastPosition; //记录上次的位置
    private int dpHeng;  //ViewPager底部小圆点之间的间距
    private Button btn_submit; // 立即预约

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_order_photograph);

        initView();
        initPointGroup(7);
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        vp = (ViewPager) findViewById(R.id.vp);
        vp_point = (LinearLayout) findViewById(R.id.vp_point);

        iv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        dpHeng = dp2px(getApplicationContext(), 5);

        int[] imgResIDs = {R.drawable.production1, R.drawable.production2, R.drawable.production3, R.drawable.production4,
                R.drawable.production5, R.drawable.production6, R.drawable.production7};
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imgResIDs.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imgResIDs[i]);
            imageList.add(image);
        }

        vp.setAdapter(new PhotographyProductionAdapter(this, imageList));
        vp.setOffscreenPageLimit(5);
        vp.setOnPageChangeListener(new MyOnPageChangeListener());
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    private void initPointGroup(int size) {
        vp_point.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = dpHeng;
            params.rightMargin = dpHeng;
            params.bottomMargin = 0;
            iv.setLayoutParams(params);
            if (i == 0) {
                iv.setBackgroundResource(R.drawable.vp_bg_red);
            } else {
                iv.setBackgroundResource(R.drawable.vp_bg_dark_gray);
            }
            vp_point.addView(iv);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            //改变点位置和颜色
            vp_point.getChildAt(position).setBackgroundResource(R.drawable.vp_bg_red);
            vp_point.getChildAt(lastPosition).setBackgroundResource(R.drawable.vp_bg_dark_gray);
            lastPosition = position;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                Intent intent = new Intent(this, SubmitPhotographyActivity.class);
//                intent.putExtra("shipId", id);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
