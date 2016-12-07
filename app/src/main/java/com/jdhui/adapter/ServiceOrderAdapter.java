package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.Service3B;
import com.jdhui.mould.types.MouldList;

public class ServiceOrderAdapter extends BaseAdapter {
    private Context context;
    private MouldList<Service3B> list;
    private LayoutInflater inflater;

    public ServiceOrderAdapter(Context context, MouldList<Service3B> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return getItem(arg0);
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
            convertView = inflater.inflate(R.layout.ac_service_order_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //serviceItems: 绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking  公务机包机：airplaneBooking
        String str = list.get(position).getServiceItems();
        String name;
        if (str.equals("hospitalBooking")) {
            name = "绿通就医";
        } else if (str.equals("geneticBooking")) {
            name = "基因检测";
        } else if (str.equals("golfBooking")) {
            name = "高尔夫球场";
        } else {
            name = "公务机包机";
        }
        String status = list.get(position).getBookingStatus();
        if (status.equals("confirm")) {
            holder.tv_status.setText("已确认");
        } else if (status.equals("unconfirm")) {
            holder.tv_status.setText("待确认");
        } else if (status.equals("finish")) {
            holder.tv_status.setText("已完成");
        } else if (status.equals("unfinish")) {
            holder.tv_status.setText("未完成");
        } else if (status.equals("refuse")) {
            holder.tv_status.setText("已驳回");
        } else if (status.equals("cancel")) {
            holder.tv_status.setText("取消");
        }
        holder.tv_name.setText(name);
        holder.tv_time.setText(list.get(position).getCreateTime());


        //不同状态时，给字体设置不同的颜色
        if (status.equals("unconfirm") || status.equals("refuse")) { //待确认或已驳回
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.red4));
        } else if (status.equals("confirm")) {//已确认
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.txt_orange_new));
        } else { //取消、未完成、已完成 状态的字体颜色
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.txt_gray));
        }

        return convertView;
    }

    class Holder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_status;
    }
}
