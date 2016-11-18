package com.jdhui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class SplashAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fgLists;

	public SplashAdapter(FragmentManager fm, ArrayList<Fragment> fgLists) {
		super(fm);
	}

	public void setData(ArrayList<Fragment> fgLists) {
		this.fgLists = fgLists;
	}

	@Override
	public Fragment getItem(int position) {
		return fgLists.get(position);
	}

	@Override
	public int getCount() {
		return fgLists.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//System.out.println("position Destory" + position);
		super.destroyItem(container, position, object);
	}

}
