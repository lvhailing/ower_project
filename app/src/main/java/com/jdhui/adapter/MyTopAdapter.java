package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdhui.R;

import java.util.ArrayList;
import java.util.List;

public class MyTopAdapter extends PagerAdapter {
    private List<String> topList;
    private List<View> viewList = new ArrayList<>();
    private Context context;

    public MyTopAdapter(List<String> topList, Context context) {
        this.topList = topList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return topList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_liner_top, null);
        String money = topList.get(position);
        if (!TextUtils.isEmpty(money)) {
            TextView tv_year = (TextView) view.findViewById(R.id.tv_year);
            TextView tv_month = (TextView) view.findViewById(R.id.tv_month);
            TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
            tv_year.setText("2017年");
            tv_month.setText("第4月(第2期)");
            tv_money.setText(money);
        }
        viewList.add(view);
        container.addView(view);
        return view;
    }

    private int oldPosition = -1;

    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (oldPosition == -1) {
            //默认进来让第0个view聚焦 改变状态
            View oldView = viewList.get(0);
            TextView tv_money_old = (TextView) oldView.findViewById(R.id.tv_money);
            tv_money_old.setTextColor(Color.parseColor("#ff0000"));
        } else if (oldPosition != position) {
            //当前聚焦的view 改变状态
            View currentView = (View) object;
            TextView tv_money_current = (TextView) currentView.findViewById(R.id.tv_money);
            tv_money_current.setTextColor(Color.parseColor("#ff0000"));

            //上一次聚焦的view 恢复默认状态
            View oldView = viewList.get(oldPosition);
            TextView tv_money_old = (TextView) oldView.findViewById(R.id.tv_money);
            tv_money_old.setTextColor(Color.parseColor("#000000"));
        }
        oldPosition = position;
    }

}