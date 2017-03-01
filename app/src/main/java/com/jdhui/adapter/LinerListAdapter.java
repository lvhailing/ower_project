package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerList3B;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.StringUtil;
import com.jdhui.uitls.ViewUtils;
import com.jdhui.widget.FlowLayoutLimitLine;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 豪华邮轮游 adapter类
 */
public class LinerListAdapter extends BaseAdapter {
    private final Context context;
    private String[] mStringArray;

    private MouldList<LinerList3B> list;
    private LayoutInflater inflater;
    private String linerTag;

    public LinerListAdapter(Context context, MouldList<LinerList3B> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

//        mStringArray = new String[]{"澳新·澳洲", "南太平洋·斐济", "北美", "南太平洋·夏威夷"};

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.ac_liner_list_item, null);
            holder.iv_liner_item = (ImageView) convertView.findViewById(R.id.iv_liner_item);
            holder.tv_travel_date = (TextView) convertView.findViewById(R.id.tv_travel_date);
            holder.tv_travel_name = (TextView) convertView.findViewById(R.id.tv_travel_name);
            holder.tv_liner_price = (TextView) convertView.findViewById(R.id.tv_liner_price);
            holder.tv_liner_name = (TextView) convertView.findViewById(R.id.tv_liner_name);
//            holder.framlayout = (FlowLayoutView) convertView.findViewById(R.id.framlayout);
            holder.remark = (FlowLayoutLimitLine) convertView.findViewById(R.id.fl_remark);
            convertView.setTag(holder);

        } else {
            holder = (LinerListAdapter.Holder) convertView.getTag();
        }

        //缓存图片到本地
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.bg_normal).showImageOnFail(R.drawable.bg_normal).resetViewBeforeLoading(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();
        //加载item背景图片
        ImageLoader.getInstance().displayImage(list.get(position).getListPhoto(), holder.iv_liner_item,options);

        String travelDate = list.get(position).getRouteDuration();
        String travelName = list.get(position).getRouteName();
        String shipName = list.get(position).getShipName();

        holder.tv_travel_date.setText(StringUtil.getResult(travelDate));
        holder.tv_travel_name.setText(StringUtil.getResult(travelName));
        holder.tv_liner_price.setText("￥" + list.get(position).getLowerTicketPrice() + "/人起");
        holder.tv_liner_name.setText(StringUtil.getResult(shipName));
        linerTag = list.get(position).getRouteDestination();

        mStringArray = linerTag.split(",");
        if (mStringArray.length > 0) {
            holder.remark.setVisibility(View.VISIBLE);
            setRemarks(holder);
        } else {
            holder.remark.setVisibility(View.GONE);
        }
//        for (int i = 0; i < mStringArray.length; i++) {
//            final TextView textView = new TextView(context);
//            textView.setText(mStringArray[i]);
//            textView.setTextColor(Color.BLACK);
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextSize(12);
//            textView.setPadding(5, 5, 5, 5);
//            Drawable normal = generateDrawable(Color.rgb(220, 220, 220), 10);
//            textView.setBackgroundDrawable(normal);
//            holder.framlayout.addView(textView);
//        }
        return convertView;
    }

    private void setRemarks(Holder holder) {
        holder.remark.removeAllViews();
        for (int i = 0; i < mStringArray.length; i++) {
            TextView textView = new TextView(context);
            textView.setText(mStringArray[i]);
            textView.setTag(false);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.bg_flag);
            textView.setIncludeFontPadding(false);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setGravity(Gravity.CENTER);

//            Drawable normal = generateDrawable(Color.rgb(220, 220, 220), 10);
//            textView.setBackgroundDrawable(normal);
//            textView.setBackgroundResource(R.drawable.rectangle_gray_4dp);

            int padding5dp = ViewUtils.dip2px(context, 5);
            textView.setPadding(padding5dp, 0, padding5dp, 0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewUtils.dip2px(context, 24));
            lp.setMargins(0, 0, ViewUtils.dip2px(context, 6), ViewUtils.dip2px(context, 6));
            textView.setLayoutParams(lp);
            holder.remark.addView(textView);
        }
    }

    public GradientDrawable generateDrawable(int argb, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        drawable.setCornerRadius(radius);//设置圆角的半径
        drawable.setColor(argb);
        return drawable;
    }


    class Holder {
        ImageView iv_liner_item;
        TextView tv_travel_date;
        TextView tv_travel_name;
        TextView tv_liner_price;
        TextView tv_liner_name;
        //        FlowLayoutView framlayout;
        FlowLayoutLimitLine remark;

    }
}
