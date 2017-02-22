package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.GolfList3B;
import com.jdhui.mould.types.MouldList;
import com.jdhui.widget.FlowLayoutView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Random;

/**
 * 豪华邮轮游 adapter类
 */
public class LinerListAdapter extends BaseAdapter {
    private final Context context;
    private String[] mStringArray;

    private MouldList<GolfList3B> list;
    private LayoutInflater inflater;

    public LinerListAdapter(Context context, MouldList<GolfList3B> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

        mStringArray = new String[]{"澳新.澳洲", "南太平洋.斐济", "北美", "南太平洋.夏威夷"};
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
            holder.tv_travel_date = (TextView) convertView.findViewById(R.id.tv_travel_date);
            holder.framlayout = (FlowLayoutView) convertView.findViewById(R.id.framlayout);
            for (int i = 0; i < mStringArray.length; i++) {
                final TextView textView = new TextView(context);
                textView.setText(mStringArray[i]);
                textView.setTextColor(Color.BLUE);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(16);
                textView.setPadding(8, 8, 8, 8);
                Drawable normal = generateDrawable(Color.rgb(220, 220, 220), 10);
//                Drawable pressed = generateDrawable(randomColor(), 10);
                textView.setBackgroundDrawable(normal);
                holder.framlayout.addView(textView);
            }
            convertView.setTag(holder);
        } else {
            holder = (LinerListAdapter.Holder) convertView.getTag();
        }


        return convertView;
    }
    //点击太效果
    public static StateListDrawable generateSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//设置按下的图片
        drawable.addState(new int[]{}, normal);//设置默认的图片
        return drawable;
    }

    public GradientDrawable generateDrawable(int argb, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        drawable.setCornerRadius(radius);//设置圆角的半径
        drawable.setColor(argb);
        return drawable;
    }

    /**
     * 随机生成漂亮的颜色
     *
     * @return
     */
    public int randomColor() {
        Random random = new Random();
        //如果值太大，会偏白，太小则会偏黑，所以需要对颜色的值进行范围限定
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);//根据rgb混合生成一种新的颜色
    }

    class Holder {
        TextView tv_travel_date;
        FlowLayoutView framlayout;

    }
}
