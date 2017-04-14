package com.jdhui.adapter.btmvp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo4B;

import java.util.ArrayList;

/**
 * 邮轮详情页第二屏 底部vp里嵌套的myListView 的CabinTypeListAdapter类
 */

public class CabinTypeListAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<LinerInfo4B> totalList;

    public CabinTypeListAdapter(Context context, ArrayList<LinerInfo4B> totalList) {
        this.context = context;
        this.totalList = totalList;
    }

    @Override
    public int getCount() {
        return totalList == null ? 0 : totalList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.house_list_item, null);
            holder = new ViewHolder();
            holder.tv_house_type = (TextView) view.findViewById(R.id.tv_house_type);
            holder.tv_house_price = (TextView) view.findViewById(R.id.tv_house_price);
            view.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) view.getTag();//取出ViewHolder对象
        }
        holder.tv_house_type.setText(totalList.get(position).getCabinType());
        holder.tv_house_price.setText(totalList.get(position).getCabinPrice());

        return view;
    }

    /*存放控件*/
    private class ViewHolder {
        TextView tv_house_type; //房子的类型
        TextView tv_house_price; //房子的价格
    }
}
