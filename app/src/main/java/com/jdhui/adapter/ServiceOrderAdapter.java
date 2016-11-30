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

    private MouldList<Service3B> list;
    private LayoutInflater inflater;

    public ServiceOrderAdapter(Context context, MouldList<Service3B> list) {
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

        //erviceItems: 绿通就医：hospitalBooking、基因检测：geneticBooking、高尔夫球场：golfBooking
        String str = list.get(position).getServiceItems();
        String name;
        if (str.equals("hospitalBooking")) {
            name = "绿通就医";
        } else if (str.equals("geneticBooking")) {
            name = "基因检测";
        } else {
            name = "高尔夫球场";
        }
        holder.tv_name.setText(name);
        holder.tv_time.setText(list.get(position).getBookingTime());
//        holder.tv_status.setText(list.get(position));

        return convertView;
    }

    class Holder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_status;
    }
}
