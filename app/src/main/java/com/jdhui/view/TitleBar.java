package com.jdhui.view;

import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.jdhui.R;
import com.jdhui.uitls.ImageLoaderManager;
import com.jdhui.uitls.SystemInfo;
import com.jdhui.uitls.ViewUtils;
import com.jdhui.widget.BadgeView;

public class TitleBar extends RelativeLayout implements OnClickListener {

	public LinearLayout actions;
	private LinearLayout actions_back;
	private ListView menuList;
	private LayoutInflater inflater;
	private StickItemWindow menuWindow, titleMenuWindow;
	private TextView title;// left;
	private ImageView left;
	private TextView more;
	private ImageView redpoint;
	private ImageView leftImg;
	private TextView tv_groupcount;
	private TextView top_title_menu;// TitleBar的下拉菜单
	private ImageView iv_top_title_menu;// 下拉菜单的图标
	private RelativeLayout rl_top_title_menu;
	private TextView tv_center;
	private String url = null;
	private String logoName = null;
	// private View back;
	private DisplayImageOptions options;

	private Context mContext;

	public TitleBar(Context context) {
		super(context);
		init(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout view = (RelativeLayout) inflater.inflate(
				R.layout.titlebar, this);
		actions = (LinearLayout) view.findViewById(R.id.titlebar_btcontainer);// action
		actions_back = (LinearLayout) view.findViewById(R.id.title_back);// 大块返回，包括Title文字
		tv_groupcount = (TextView) view.findViewById(R.id.top_groupcount);// 返回中的Title右侧文字
		more = (TextView) view.findViewById(R.id.top_title_rightbtn);// 右侧
		// 更多菜单
		redpoint = (ImageView) view.findViewById(R.id.top_title_redpoint);//
		// 右侧提示的红点
		// back = view.findViewById(R.id.title_back);

		// left = (TextView) view.findViewById(R.id.top_title_leftbtn);
		left = (ImageView) view.findViewById(R.id.top_title_leftbtn);// 返回中的logo图标
		title = (TextView) view.findViewById(R.id.top_title);// 返回中的Title左侧文字
		leftImg = (ImageView) view.findViewById(R.id.top_title_leftview);// 返回中最左侧的回去图标
		top_title_menu = (TextView) view.findViewById(R.id.top_title_menu);// 有下拉菜单按钮的Title
		rl_top_title_menu = (RelativeLayout) findViewById(R.id.rl_top_title_menu);
		iv_top_title_menu = (ImageView) findViewById(R.id.iv_top_title_menu);
		tv_center = (TextView) findViewById(R.id.title_center);// 中间文字

		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.move_rightlittle);
		animation.setFillAfter(true);
		leftImg.startAnimation(animation);// 为左侧回去图标设置动来动去的动画
		// setAnimationLeft(R.anim.left_title_first);
		actions_back.setBackgroundResource(R.drawable.title_selector);// 设置大块返回点击给用户反应
																		// --变身灰色

		actions_back.setOnClickListener(this);
		 left.setOnClickListener(this);
		// title.setOnClickListener(this);
		// leftImg.setOnClickListener(this);
		more.setOnClickListener(this);
		
		options = ImageLoaderManager.initDisplayImageOptions(
				R.drawable.icons, R.drawable.icons,
				R.drawable.icons);
	}

	private ImageView red;

	public LinearLayout getActions() {
		return actions;
	}
	private ImageView action1,action2,action3;
	public void changBg(int actionId) {
		ImageView action = (ImageView) findViewById(actionId);

//		ImageView action1 = (ImageView) findViewById(1);
//		ImageView action2 = (ImageView) findViewById(2);
//		ImageView action3 = (ImageView) findViewById(3);

		resutlImage_1(1);

		resutlImage_2(2);

		resutlImage_3(3);

		if (actionId == 1) {
			action.setImageResource(R.drawable.home_2);
			action2.setImageResource(R.drawable.uesr_1);
			action3.setImageResource(R.drawable.more_1);
		} else if (actionId == 2) {
			action.setImageResource(R.drawable.uesr_2);
			action1.setImageResource(R.drawable.home_1);
			action3.setImageResource(R.drawable.more_1);
		} else {
			action2.setImageResource(R.drawable.uesr_1);
			action1.setImageResource(R.drawable.home_1);
			action.setImageResource(R.drawable.more_2);
		}
	}
	public void resutlImage_1(int result){
		action1 = (ImageView) findViewById(result);

	}
	public void resutlImage_2(int result){
		action2 = (ImageView) findViewById(result);
	}
	public void resutlImage_3(int result){
		action3 = (ImageView) findViewById(result);
	}

	public TitleBar setCenterText(String text) {
		tv_center.setText(text);
		return TitleBar.this;
	}

	/**
	 * 在标题栏中添加一个可点击的button
	 * 
	 * @param act
	 * @return
	 */
	@SuppressLint("NewApi")
	public TitleBar addAction(Action act) {
		if (actions.getChildCount() == 3) {
			throw new RuntimeException("action container is full,limit is 2");
		}
		FrameLayout f = new FrameLayout(mContext);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ImageView child = new ImageView(mContext);
		if (act.id > 0) {
			child.setId(act.id);
		}
		child.setImageResource(act.background);
		child.setScaleType(ScaleType.CENTER_INSIDE);
		child.setBackgroundResource(R.drawable.title_selector);
		child.setOnClickListener(this);

		int w = ViewUtils.dip2px(mContext, 55);
		int h = ViewUtils.dip2px(mContext, 55);

		if (act.text != 0) {
			TextView t = new TextView(mContext);
			FrameLayout.LayoutParams pT = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT);
			String s = getResources().getString(act.text);
			if (s.length() >= 3) {
				t.setTextSize(14);
				// Paint paint = new Paint();
				// float strwid = paint.measureText(s);
				// System.out.println("strwid = " + strwid);
				int display[] = SystemInfo.getScreenDispaly(mContext);
				w = display[0] / 5;
			} else {
				t.setTextSize(20);
			}
			t.setText(act.text);
			t.setTextColor(mContext.getResources().getColor(R.color.white));

			t.setGravity(Gravity.CENTER_VERTICAL);
			t.setSingleLine();
			t.setOnClickListener(this);

			f.addView(t);
			child.setVisibility(View.VISIBLE);
		}
		f.setLayoutParams(params);
		f.addView(child);
		// red = new ImageView(mContext);
		// red.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// red.setImageResource(R.drawable.redpoint);
		// red.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		// FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
		// Gravity.RIGHT | Gravity.TOP);
		// p.rightMargin = ViewUtils.dip2px(mContext, 5);
		// p.topMargin = ViewUtils.dip2px(mContext, 10);
		// red.setLayoutParams(p);
		// f.addView(red);
		// red.setVisibility(View.GONE);
		LayoutParams actionParams = new LayoutParams(w, h);
		actions.addView(f, actionParams);
		// actions.addView(child, new LayoutParams(w, w));
		return TitleBar.this;

		// TextView child = new TextView(mContext);
		// if (act.text > 0)
		// child.setText(act.text);
		// if (act.id > 0)
		// child.setId(act.id);
		// // child.setBackgroundResource(act.background);
		// Drawable drawable = getResources().getDrawable(act.background);
		// // / 这一步必须要做,否则不会显示.
		// drawable.setBounds(-10, 0, drawable.getMinimumWidth()-10,
		// drawable.getMinimumHeight());
		// child.setCompoundDrawables(drawable, null, null, null);
	}

	/**
	 * 在标题栏中添加一个可点击的button
	 * 
	 * @param act
	 * @return
	 */
	public TitleBar addAction(Action act, boolean b) {
		if (actions.getChildCount() == 3) {
			throw new RuntimeException("action container is full,limit is 2");
		}
		TextView child = new TextView(mContext);
		if (act.text > 0)
			child.setText(act.text);
		if (act.id > 0)
			child.setId(act.id);
		// child.setBackgroundResource(act.background);
		Drawable drawable = mContext.getResources().getDrawable(act.background);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		child.setCompoundDrawables(drawable, null, null, null);
		child.setBackgroundResource(R.drawable.title_selector);
		child.setOnClickListener(this);
		int w = ViewUtils.dip2px(mContext, 56);
		if (b) {
			child.setVisibility(View.VISIBLE);
		} else {
			child.setVisibility(View.GONE);
		}
		actions.addView(child, new LayoutParams(w, w));
		return TitleBar.this;
	}

	/**
	 * 在标题栏中添加一个可点击的button 带文字的
	 * 
	 * @param act
	 * @return
	 */
	public TitleBar addAction(Action act, String text) {
		if (actions.getChildCount() == 3) {
			throw new RuntimeException("action container is full,limit is 2");
		}
		TextView child = new TextView(mContext);
		child.setPadding(0, 0, 10, 0);
		child.setGravity(Gravity.CENTER);
		if (text.length() >= 3) {
			child.setTextSize(14);
			// Paint paint = new Paint();
			// float strwid = paint.measureText(s);
			// System.out.println("strwid = " + strwid);
			// int display[] = SystemInfo.getScreenDispaly(mContext);
			// w = display[0] / 5;
		} else {
			child.setTextSize(20);
		}
		child.setText(text);
		child.setTextColor(mContext.getResources().getColor(R.color.white));
		if (!TextUtils.isEmpty(text)) {
		}
		if (act.id > 0) {
			child.setId(act.id);
		}
		Drawable drawable = getResources().getDrawable(act.background);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		child.setCompoundDrawables(drawable, null, null, null);
		child.setBackgroundResource(R.drawable.title_selector);
		child.setOnClickListener(this);
		// int w = ViewUtils.dip2px(mContext, 56);
		child.setVisibility(View.VISIBLE);
		actions.addView(child, new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		return TitleBar.this;
	}

	/**
	 * 在标题栏中添加一个可点击的button 带数字的
	 * 
	 * @param act
	 * @return
	 */
	public TitleBar addAction(Action act, Context context) {
		if (actions.getChildCount() == 3) {
			throw new RuntimeException("action container is full,limit is 2");
		}
		TextView child = new TextView(mContext);
		child.setPadding(0, 0, 10, 0);
		child.setGravity(Gravity.CENTER);
		if (act.id > 0) {
			child.setId(act.id);
		}
		Drawable drawable = getResources().getDrawable(act.background);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		child.setCompoundDrawables(drawable, null, null, null);
		child.setBackgroundResource(R.drawable.title_selector);
		child.setOnClickListener(this);
		child.setVisibility(View.VISIBLE);
		actions.addView(child, new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		badgeRightbtn = new BadgeView(context, child);
		badgeRightbtn.setText("0");
		return TitleBar.this;
	}

	/**
	 * 刷新一个已有的右边的按钮 (加1或减1) 参考图片选择时的
	 * 
	 * @param id
	 */
	public void refreshRightBtn(boolean isIncrement, int id, Context context) {
		if (badgeRightbtn != null) {
			if (isIncrement) {
				badgeRightbtn.increment(1);
			} else {
				badgeRightbtn.decrement(1);
			}
			if (!badgeRightbtn.isShown()) {
				badgeRightbtn.show();
			} else {
				CharSequence txt = badgeRightbtn.getText();
				int i;
				if (txt != null) {
					try {
						i = Integer.parseInt(txt.toString());
					} catch (NumberFormatException e) {
						i = 0;
					}
				} else {
					i = 0;
				}
				if (i == 0) {
					badgeRightbtn.toggle();
					badgeRightbtn.setText("0");
				}
			}
		}
	}

	/**
	 * 在标题栏中删除一个可点击的button
	 * 
	 * @param actionId 是否显示 Action 具体的哪一个action
	 * @return
	 */
	public void setShowAction(boolean b, int actionId) {

		View view = findViewById(actionId);

		if (view != null) {
			if (b) {
				view.setVisibility(View.VISIBLE);
			} else {
				view.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 为titlebar添加一个菜单项
	 * 
	 * @param mAct
	 * @return
	 */
	public TitleBar addMenu(Action mAct) {
		if (menuWindow == null) {
			menuWindow = new StickItemWindow((View) getParent(), more);
			menuList = new ListView(mContext);
			menuList.setDivider(new ColorDrawable(0xff888888));
			menuList.setDividerHeight(1);
			menuList.setAdapter(mAdapter);
			// menuList.setBackgroundColor(0xffdddddd);
			menuList.setBackgroundColor(0xff666666);
			menuList.setVerticalScrollBarEnabled(false);
			menuList.setSelector(R.color.txt_black);
			menuList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (mListenter != null) {
						mListenter.onMenu(view.getId());
						menuWindow.dismiss();
					}
				}
			});
			menuWindow.setContentView(menuList);
			menuWindow.setBelow(true);
		}
		// from bing ge
		Action old = null;
		for (Action a : menus) {
			if (a.id == mAct.id) {
				old = a;
				break;
			}
		}
		if (old != null) {
			menus.remove(old);
		}
		menus.add(mAct);
		Collections.sort(menus);
		mAdapter.notifyDataSetChanged();
		return TitleBar.this;
	}

	/**
	 * 为title文字点击添加一个菜单项
	 * 
	 * @param mAct
	 * @return
	 */
	public TitleBar addTitleMenu(Action mAct) {
		if (titleMenuWindow == null) {
			titleMenuWindow = new StickItemWindow((View) getParent(), more);
			menuList = new ListView(mContext);
			menuList.setCacheColorHint(getResources().getColor(R.color.transparent));// 去掉ListView的模糊边缘
			menuList.setDivider(new ColorDrawable(0xff888888));
			menuList.setDividerHeight(1);
			menuList.setAdapter(mTitleAdapter);
			// menuList.setBackgroundResource(R.drawable.pullmenu_bg);//设置下拉菜单总背景
			menuList.setBackgroundColor(0xff666666);
			menuList.setVerticalScrollBarEnabled(false);
			// menuList.setSelector(R.color.transparent);//设置点击时背景透明
			menuList.setSelector(R.color.txt_black);
			menuList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (mListenter != null) {
						mListenter.onMenu(view.getId());
						titleMenuWindow.dismiss();
					}
				}

			});
			// menuList.setLayoutParams(new AbsListView.LayoutParams(
			// AbsListView.LayoutParams.MATCH_PARENT,
			// AbsListView.LayoutParams.MATCH_PARENT));
			titleMenuWindow.setContentView(menuList);
			titleMenuWindow.setBelow(true);
		}
		// from bing ge
		Action old = null;
		for (Action a : titleMenus) {
			if (a.id == mAct.id) {
				old = a;
				break;
			}
		}
		if (old != null) {
			titleMenus.remove(old);
		}
		titleMenus.add(mAct);
		Collections.sort(titleMenus);
		mTitleAdapter.notifyDataSetChanged();
		return TitleBar.this;
	}

	public void sert() {
		// titleMenus.get(0).
	}

	/**
	 * 设置最左侧的指示图标资源
	 * 
	 * @param r
	 * @return
	 */
	public TitleBar setIndicator(int r) {
		Animation animation = AnimationUtils.loadAnimation(mContext,
				R.anim.move_rightlittle);
		animation.setFillAfter(true);
		leftImg.startAnimation(animation);
		leftImg.setImageResource(r);
		return TitleBar.this;
	}

	/**
	 * 设置LOGO显示图片及是否显示
	 * 
	 * @param id
	 * @param b
	 * @return
	 */
	public TitleBar setLogo(int id, boolean b) {
		if (b) {
			left.setImageResource(id);
		} else {
			left.setVisibility(View.GONE);
		}
		return TitleBar.this;
	}
	/**
	 * 设置LOGO显示图片及是否显示
	 * 
	 * @param picture
	 * @param b
	 * @return
	 */
	public TitleBar setLogo(Bitmap picture,String url,String logoName, boolean b) {
		this.url = url;
		this.logoName = logoName;
//		Toast.makeText(mContext, "setLogo picture=="+picture+" url=="+url+" logoName=="+logoName, Toast.LENGTH_LONG).show();
		if (b) {
			if(picture==null){
				left.setImageResource(R.drawable.icons);
			}else{
				left.setImageBitmap(picture);
			}
		} else {
			left.setVisibility(View.GONE);
		}
		return TitleBar.this;
	}

	/**
	 * 设置是否显示菜单按钮
	 * 
	 * @param isShow
	 * @return
	 */
	public TitleBar showMore(boolean isShow) {
		more.setVisibility(isShow ? View.VISIBLE : View.GONE);
		if (!isShow) {
			LayoutParams p = ((LayoutParams) actions
					.getLayoutParams());
			p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		return TitleBar.this;
	}

	/**
	 * 设置菜单按钮的资源
	 * 
	 * @param res
	 * @return
	 */
	public TitleBar setMore(int res) {
		// more.setImageResource(res);
		Drawable drawable = getResources().getDrawable(res);
		// / 这一步必须要做,否则不会显示.
		drawable.setBounds(-20, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		more.setCompoundDrawables(drawable, null, null, null);
		more.setBackgroundResource(R.drawable.title_selector);
		return TitleBar.this;
	}

	/**
	 * 设置左图片动画
	 */
	public TitleBar setAnimationLeft(Animation animation, boolean is) {
		if (is) {
			actions_back.startAnimation(animation);
		}
		return TitleBar.this;
	}

	/**
	 * 设置左图片动画
	 */
	public TitleBar setAnimationLeft(Animation animation, Animation animation2) {
		// actions_back.startAnimation(animation);
		leftImg.startAnimation(animation);
		left.startAnimation(animation2);
		return TitleBar.this;
	}

	public void stopAnimationLeft() {
		actions_back.clearAnimation();
		left.clearAnimation();
		leftImg.clearAnimation();
	}

	/**
	 * 设置titlebar群聊人数
	 * 
	 * @param text
	 * @return
	 * 
	 *         public TitleBar setGroupCount(int count) { String text = ""; if
	 *         (count > 0) { text = "(" + count + ")";
	 *         tv_groupcount.setText(text); } else { tv_groupcount.setText("");
	 *         } return TitleBar.this; }
	 */
	/**
	 * 设置标题文本
	 * 
	 * @param res
	 * @return
	 */
	public TitleBar setTitle(int res) {
		title.setText(res);
		return TitleBar.this;
	}

	/**
	 * 设置标题文本
	 * 
	 * @param string
	 * @return
	 */
	public TitleBar setTitle(String string) {
		title.setText(string);
		return TitleBar.this;
	}

	/**
	 * 设置菜单标题文本
	 * 
	 * @param string
	 * @return
	 */
	public TitleBar setMenuTitle(String string) {
		top_title_menu.setText(string);
		return TitleBar.this;
	}

	/**
	 * 设置菜单标题文本
	 * 
	 * @param res
	 * @return
	 */
	public TitleBar setMenuTitle(int res) {
		top_title_menu.setText(res);
		return TitleBar.this;
	}

	/**
	 * 设置标题的响应
	 * 
	 * @param isSet
	 * @return
	 */
	public TitleBar setTitleMenuListenter(boolean isSet) {
		if (isSet) {
			rl_top_title_menu.setOnClickListener(this);
		}
		return TitleBar.this;
	}

	/**
	 * 设置标题的右侧的图标
	 * 
	 * @param drawable
	 * @return
	 */
	public TitleBar setTitleRigterIcon(int drawable) {
		iv_top_title_menu.setBackgroundResource(drawable);
		return TitleBar.this;
	}

	/***
	 * 设置菜单右侧红点
	 * 
	 * @param isShown
	 * @return
	 */
	public void setMenuRedPoint(boolean isShown) {
		if (isShown) {
			redpoint.setVisibility(View.VISIBLE);
		} else {
			redpoint.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置Action红点
	 * 
	 * @param isShown
	 */
	public void setActionRedPoint(boolean isShown) {
		if (red != null) {
			if (isShown) {
				red.setVisibility(View.VISIBLE);
			} else {
				red.setVisibility(View.GONE);
			}
		}
	}

	OnActionListener mListenter;

	public interface OnActionListener {
		public void onAction(int id);

		public void onMenu(int id);

		public void onBack();
	}

	/**
	 * 设置title中各个控件的点击事件监听
	 * 
	 * @param l
	 */
	public void setOnActionListener(OnActionListener l) {
		this.mListenter = l;
	}

	@Override
	public void onClick(View v) {

		if (mListenter != null) {
			switch (v.getId()) {
			case R.id.title_back:
			// case R.id.top_title:
			// case R.id.top_title_leftview:
				mListenter.onBack();
				break;
			// case R.id.top_title_rightbtn: {
			// menuWindow.setAnchor(more);
			// menuWindow.setWidth((int) (more.getWidth() * 2.5));
			// menuWindow.setHeight(menus.size()
			// * ViewUtils.dip2px(mContext, 50));
			// menuWindow.setMarginH(ViewUtils.dip2px(mContext, 4));
			// menuWindow.show();
			// break;
			// }
			case R.id.rl_top_title_menu:
			case R.id.iv_top_title_menu:
			case R.id.top_title_menu: {
				titleMenuWindow.setAnchor(rl_top_title_menu);
				titleMenuWindow.setWidth((int) (left.getWidth() * 4));
				titleMenuWindow.setHeight(titleMenus.size()
						* ViewUtils.dip2px(mContext, 50));
				titleMenuWindow.setMarginH(ViewUtils.dip2px(mContext, 4));
				// titleMenuWindow.showAsDropDownOff(top_title_menu,-left.getWidth());
				titleMenuWindow.showAsDropDown(rl_top_title_menu);
				titleMenuWindow.showTitleMenu();
				onRefreshTitleMenusNum();
				break;
			}
			default: {
				mListenter.onAction(v.getId());
			}
			}
		}
	}

	public void refeshMenu() {
		mAdapter.notifyDataSetChanged();
		// TODO 需要吗
		mTitleAdapter.notifyDataSetChanged();
	}

	MenuAdapter mAdapter = new MenuAdapter();
	private ArrayList<Action> menus = new ArrayList<Action>();
	private BadgeView badgeRightbtn;

	/**
	 * titleBar的更多那儿的下拉菜单
	 * 
	 * @author
	 * 
	 */
	class MenuAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return menus.size();
		}

		@Override
		public Action getItem(int position) {
			// TODO Auto-generated method stub
			return menus.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Action act = getItem(position);
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.titlemenu, null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.title_menu_img);
				holder.text = (TextView) convertView
						.findViewById(R.id.title_menu_text);
				holder.redImg = (ImageView) convertView
						.findViewById(R.id.title_menu_red);
				holder.redImg.setVisibility(GONE);
				holder.img.setScaleType(ScaleType.CENTER_INSIDE);
				holder.text.setGravity(Gravity.CENTER_VERTICAL);
				holder.text.setPadding(ViewUtils.dip2px(mContext, 10), 0, 0, 0);
				holder.text.setTextSize(16);
				holder.text.setTextColor(Color.WHITE);
				holder.text.setBackgroundResource(act.background);
				convertView.setLayoutParams(new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, ViewUtils
								.dip2px(mContext, 50)));
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			// TextView t = new TextView(mContext);
			if (act.isRed) {
				holder.redImg.setVisibility(View.VISIBLE);
			} else {
				holder.redImg.setVisibility(View.GONE);
			}
			if (act.text > 0)
				holder.text.setText(act.text);
			if (act.id > 0)
				convertView.setId(act.id);
			if (act.left_img != 0) {
				holder.img.setImageResource(act.left_img);

			}
			return convertView;
		}

		class Holder {
			ImageView img;
			TextView text;
			ImageView redImg;
		}
	}

	/**
	 * 设置某索引位置的字体颜色为红色
	 * 
	 * @param index
	 */
	public void setPullMenu(int index) {
		if (titleMenus == null || mTitleAdapter == null) {
			return;
		}
		for (int x = 0; x < titleMenus.size(); x++) {
			if (x == index) {

				titleMenus.get(x).setColor(true);
			} else {
				titleMenus.get(x).setColor(false);

			}
		}
		mTitleAdapter.notifyDataSetChanged();

	}

	/**
	 * 设置最左侧图标是否显示
	 * 
	 * @param b
	 */
	public void showLeftImg(boolean b) {
		if (b) {
			leftImg.setVisibility(View.VISIBLE);
		} else {
			leftImg.setVisibility(View.GONE);
		}
	}

	/**
	 * title中的下拉效果
	 * 
	 * @author
	 * 
	 */
	MenuTitleAdapter mTitleAdapter = new MenuTitleAdapter();
	private ArrayList<Action> titleMenus = new ArrayList<Action>();

	class MenuTitleAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titleMenus.size();
		}

		@Override
		public Action getItem(int position) {
			// TODO Auto-generated method stub
			return titleMenus.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Action act = getItem(position);
			Holder holder = null;
			if (convertView == null) {

				holder = new Holder();
				inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.titlemenu_left, null);
				holder.text = (TextView) convertView
						.findViewById(R.id.title_menu_text);
				holder.text.setGravity(Gravity.CENTER);
				// holder.text.setLayoutParams(new
				// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				// RelativeLayout.LayoutParams.MATCH_PARENT));
				// holder.text.setPadding(ViewUtils.dip2px(mContext, 10), 0, 0,
				// 0);
				holder.text.setTextSize(16);
				holder.text.setTextColor(Color.WHITE);
				holder.text.setBackgroundResource(act.background);
				holder.badgeView = (TextView) convertView
						.findViewById(R.id.title_menu_badgeView);
				convertView.setTag(holder);
				convertView.setLayoutParams(new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, ViewUtils
								.dip2px(mContext, 49)));
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			// TextView t = new TextView(mContext);
			if (act.text > 0)
				holder.text.setText(act.text);
			if (act.id > 0) {
				convertView.setId(act.id);
			}
			if (!TextUtils.isEmpty(act.textNum) && !act.textNum.equals("0")
					&& !act.textNum.equals("null")) {
				holder.badgeView.setVisibility(View.VISIBLE);
				holder.badgeView.setText(act.textNum);
			} else {
				holder.badgeView.setVisibility(View.GONE);
				holder.badgeView.setText("");
			}
			if (act.isTextColorRed) {
				holder.text.setTextColor(mContext.getResources().getColor(
						R.color.txt_red));

			} else {
				holder.text.setTextColor(Color.WHITE);

			}
			return convertView;
		}

		class Holder {
			TextView text, badgeView;
		}
	}

	public static class Action implements Comparable<Action> {
		/**
		 * id不能为零， 点击事件时会返回
		 */
		public int id;
		/**
		 * 背景资源
		 */
		public int background;
		/**
		 * 文本资源
		 */
		public int text;
		/**
		 * 文本角标资源
		 */
		public String textNum;
		/**
		 * 左侧图标
		 */
		public int left_img;

		/**
		 * 是否开启红点
		 */
		public boolean isRed;
		public boolean isTextColorRed;

		public void setColor(boolean isRed) {
			isTextColorRed = isRed;
		}

		/**
		 * titlebar上的action
		 * 
		 * @param id
		 * @param t
		 * @param b
		 */
		public Action(int id, int t, int b) {
			this.id = id;
			this.background = b;
			this.text = t;
		}

		/**
		 * 带有红点的action，左图标
		 * 
		 * @param id
		 * @param t
		 * @param b
		 * @param i
		 */
		public Action(int id, int t, int b, int l, boolean i) {
			this.id = id;
			this.background = b;
			this.text = t;
			this.left_img = l;
			this.isRed = i;
		}

		/**
		 * 无左图标，带红点
		 * 
		 * @param id
		 * @param t
		 * @param b
		 * @param i
		 */
		public Action(int id, int t, int b, boolean i) {
			this.id = id;
			this.background = b;
			this.text = t;
			this.isRed = i;
		}

		/**
		 * menu中的action，带左部小图标
		 * 
		 * @param id
		 * @param t
		 * @param b
		 * @param l
		 */
		public Action(int id, int t, int b, int l) {
			this.id = id;
			this.background = b;
			this.text = t;
			this.left_img = l;
		}

		@Override
		public int compareTo(Action another) {
			int oId = another.id;
			return id > oId ? 1 : (id == oId ? 0 : -1);
		}
	}

	public void onRefreshTitleMenusNum() {
		if (titleMenus == null || mTitleAdapter == null) {
			return;
		}
		mTitleAdapter.notifyDataSetChanged();
	}
}