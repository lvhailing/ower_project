package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo3B;

import java.util.List;

/**
 *  邮轮详情页第二屏 底部vp的Adapter
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
        TextView tv_price_1 = (TextView) view.findViewById(R.id.tv_price_1);
        tv_price_1.setText(liner.getSeaviewRoom());
        viewPager.addView(view);
        return view;
    }

}