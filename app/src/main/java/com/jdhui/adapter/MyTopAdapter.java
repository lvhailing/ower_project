package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo3B;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮轮详情页第二屏 顶部vp的Adapter
 */
public class MyTopAdapter extends MyPagerAdapter {
    private List<LinerInfo3B> topList;
    private List<View> viewList = new ArrayList<>();
    private Context context;
    private LinerInfo3B item;

    public MyTopAdapter(List<LinerInfo3B> topList, Context context) {
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
        if (topList.size() != 0 && topList != null) {
            item = topList.get(position);
        }
        TextView tv_year = (TextView) view.findViewById(R.id.tv_year); //游行时间
        TextView tv_month = (TextView) view.findViewById(R.id.tv_month); //航期数
        TextView tv_money = (TextView) view.findViewById(R.id.tv_money); //最低票价

        String shipCount = item.getShipCount();
        String money = item.getLowerTicketPrice();
        String date = item.getShipTime();
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);


        tv_year.setText(year + "年");
        tv_month.setText(month + "月" + "[" + shipCount + "航期]");
        tv_money.setText("￥"+money+"/人起");

        viewList.add(view);
        container.addView(view);
        return view;
    }

    private int oldPosition = -1;

    @Override
    public void setPrimaryItem(View container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (viewList.size() <= 0) {
            return;
        }

        if (oldPosition == -1) {
            //默认进来让第0个view聚焦 改变状态
            View oldView = viewList.get(0);
            TextView tv_year_old = (TextView) oldView.findViewById(R.id.tv_year);
            TextView tv_month_old = (TextView) oldView.findViewById(R.id.tv_month);
            TextView tv_money_old = (TextView) oldView.findViewById(R.id.tv_money);

            tv_year_old.setTextColor(Color.parseColor("#333333"));
            tv_month_old.setTextColor(Color.parseColor("#333333"));
            tv_money_old.setTextColor(Color.parseColor("#333333"));
        } else if (oldPosition != position) {
            //当前聚焦的view 改变状态
            View currentView = (View) object;
            TextView tv_year_old = (TextView) currentView.findViewById(R.id.tv_year);
            TextView tv_month_old = (TextView) currentView.findViewById(R.id.tv_month);
            TextView tv_money_current = (TextView) currentView.findViewById(R.id.tv_money);

            tv_year_old.setTextColor(Color.parseColor("#333333"));
            tv_month_old.setTextColor(Color.parseColor("#333333"));
            tv_money_current.setTextColor(Color.parseColor("#333333"));

            //上一次聚焦的view 恢复默认状态
            View oldView = viewList.get(oldPosition);
            TextView tv_year_old1 = (TextView) oldView.findViewById(R.id.tv_year);
            TextView tv_month_old1 = (TextView) oldView.findViewById(R.id.tv_month);
            TextView tv_money_old = (TextView) oldView.findViewById(R.id.tv_money);

            tv_year_old1.setTextColor(Color.parseColor("#959595"));
            tv_month_old1.setTextColor(Color.parseColor("#959595"));
            tv_money_old.setTextColor(Color.parseColor("#959595"));
        }
        oldPosition = position;
    }

}