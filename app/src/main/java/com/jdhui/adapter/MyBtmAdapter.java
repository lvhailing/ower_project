package com.jdhui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jdhui.adapter.btmvp.MyBtmAdapterFactory;
import com.jdhui.bean.mybean.LinerInfo3B;

import java.util.List;

/**
 * 邮轮详情页第二屏 底部vp的Adapter
 */
public class MyBtmAdapter extends PagerAdapter {
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
        View view = new MyBtmAdapterFactory().getView(context, liner);
        Log.i("aaa", "aaa");
//        view.requestLayout();
        Log.i("aaa", "bbb");
        viewPager.addView(view);
        Log.i("aaa", "ccc");
        return view;
    }

}
