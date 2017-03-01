package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo3B;
import com.jdhui.uitls.StringUtil;

import java.util.List;

/**
 * 邮轮详情页第二屏 底部vp的Adapter
 */
public class MyBtmAdapter extends MyPagerAdapter {
    private List<LinerInfo3B> btmList;
    private Context context;

    public MyBtmAdapter(List<LinerInfo3B> btmList, Context context) {
        this.btmList = btmList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return btmList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup viewPager, int position, Object object) {
        viewPager.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup viewPager, final int position) {
        final LinerInfo3B liner = btmList.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_liner_btm, null);

        TextView tv_startPoint = (TextView) view.findViewById(R.id.tv_startPoint); //离港地点
        TextView tv_endPiont = (TextView) view.findViewById(R.id.tv_endPoint); //抵港地点
        TextView tv_startTime = (TextView) view.findViewById(R.id.tv_startTime); //离港时间
        TextView tv_endTime = (TextView) view.findViewById(R.id.tv_endTime); //抵港时间
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);//历时 如：2晚3日

        //四种房对应的价格（内舱房、海景房、阳台房、套房）
        TextView tv_price_1 = (TextView) view.findViewById(R.id.tv_price_1);
        TextView tv_price_2 = (TextView) view.findViewById(R.id.tv_price_2);
        TextView tv_price_3 = (TextView) view.findViewById(R.id.tv_price_3);
        TextView tv_price_4 = (TextView) view.findViewById(R.id.tv_price_4);

        tv_startPoint.setText(StringUtil.getResult(liner.getStartPoint()));
        tv_endPiont.setText(StringUtil.getResult(liner.getEndPiont()));
        tv_startTime.setText(liner.getStartTime());
        tv_endTime.setText(liner.getEndTime());
        tv_time.setText(StringUtil.getResult(liner.getRouteDuration()));
        tv_price_1.setText(liner.getInnerRoom());
        tv_price_2.setText(liner.getSeaviewRoom());
        tv_price_3.setText(liner.getBalconyRoom());
        tv_price_4.setText(liner.getSuiteRoom());
        viewPager.addView(view);
        return view;
    }

}