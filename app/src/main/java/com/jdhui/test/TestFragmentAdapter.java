package com.jdhui.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TestFragmentAdapter extends FragmentPagerAdapter {
	public String[] CONTENT;

	public TestFragmentAdapter(FragmentManager fm, String content[]) {
		super(fm);
		CONTENT = content;
	}
	
	public void setContent(String []CONTENT){
		this.CONTENT = CONTENT;
	}
	
	@Override
	public Fragment getItem(int position) {
		return TestFragment.newInstance(CONTENT[position]);
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}
}