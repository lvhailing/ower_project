package com.jdhui.uitls;

import java.util.Stack;

import android.app.Activity;

/**
 * activity stack manage
 * 
 * @author sxn
 * 
 */
public class ActivityStack {
//	private static final String LOGTAG = LogUtil
//			.makeLogTag(ActivityStack.class);
	private static Stack<Activity> actStack = new Stack<Activity>();
	private static ActivityStack instance;

	private ActivityStack() {
	}

	public static synchronized ActivityStack getActivityManage() {
		if (instance == null) {
			instance = new ActivityStack();
		}
		return instance;
	}

	public void removeActivity() {
		Activity activity = actStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	public void removeActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			actStack.remove(activity);
			activity = null;
		}
	}

	public Activity currentActivity() {
		if (actStack != null && actStack.size() > 0) {
			Activity activity = actStack.lastElement();
			return activity;
		}
		return null;
	}

	public boolean containMain(String filter) {
		if (actStack != null && actStack.size() > 0) {
			for (int i = 0; i < actStack.size(); i++) {
				Activity current = actStack.get(i);
				if (current.getClass().getName().equals(filter)) {
//					Log.d(LOGTAG, "current:" + current.getClass().getName());
					return true;
				}
			}
		}
		return false;
	}

	public void pushActivity(Activity activity) {
		int num = actStack.indexOf(activity);
		if (num != -1) {
			actStack.remove(num);
			actStack.push(activity);
		}
	}

	/**
	 * 判断stack中是否存在filter
	 * 
	 * @param filter
	 * @return
	 */
	public boolean containActivity(String filter) {
		while (true) {
			Activity activity = currentActivity();
			if (activity.getClass().getName().equals(filter)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 根据filter取出stack中activity
	 * 
	 * @param filter
	 * @return
	 */
	public Activity getFilterActivity(String filter) {
		if (actStack != null && actStack.size() > 0) {
			for (int i = 0; i < actStack.size(); i++) {
				Activity current = actStack.get(i);
				if (current.getClass().getName().equals(filter)) {
//					Log.d(LOGTAG, "current:" + current.getClass().getName());
					return current;
				} 
			}
		}
		return null;

	}

	public void addActivity(Activity activity) {
		actStack.add(activity);
	}

	/**
	 * 移除stack 中除filter之外的activity
	 * 
	 * @param cls
	 * @param filter
	 */
	public void removeAllActivityExceptOne( String filter) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().getName().equals(filter)) {
				break;
			}
			removeActivity(activity);
		}
	}
	/**
	 * 移除stack 中除A 与 B 之外的activity
	 * @param filterA A activity
	 * @param filterB
	 */
	public void removeAllActivityExceptTwo( String filterA,String filterB) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().getName().equals(filterA)) {
				break;
			}
			if (activity.getClass().getName().equals(filterB)) {
				break;
			}
			removeActivity(activity);
		}
	}

	/**
	 * 移除指定的Activity
	 * 
	 * @param cls
	 * @param filter
	 */
	public void removeFilterActivity(String filter) {

		if (actStack != null && actStack.size() > 0) {
			for (int i = 0; i < actStack.size(); i++) {
				Activity current = actStack.get(i);
				if (current.getClass().getName().equals(filter)) {
//					Log.d(LOGTAG, "current:" + current.getClass().getName());
					removeActivity(current);
					break;
				}
			}
		}
	}
	

	public void removeAllActivity() {
		while (true) {
			if (actStack.isEmpty()) {
				return;
			}
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			removeActivity(activity);
		}
	}

	public void removeBottomActivity() throws Exception {
		if (actStack.size() > 0) {
			actStack.remove(0).finish();
		}
	}

	public int getActivityStackNum() {
		return actStack.size();
	}
}
