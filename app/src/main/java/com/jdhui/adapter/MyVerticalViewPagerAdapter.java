package com.jdhui.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyVerticalViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;

	public MyVerticalViewPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	public MyVerticalViewPagerAdapter(FragmentManager fm,
			List<Fragment> oneListFragments) {
		super(fm);
		this.fragments = oneListFragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		
		return super.getItemPosition(object);
	}

}
