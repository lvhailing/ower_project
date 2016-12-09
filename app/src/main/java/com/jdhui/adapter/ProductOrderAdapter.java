package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.Product3B;
import com.jdhui.mould.types.MouldList;

public class ProductOrderAdapter extends BaseAdapter {

    private MouldList<Product3B> list;
    private LayoutInflater inflater;
    private String status;

    public ProductOrderAdapter(Context context, MouldList<Product3B> list) {
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
            convertView = inflater.inflate(R.layout.ac_product_order_item, null);
            holder.tv_pro_name = (TextView) convertView.findViewById(R.id.tv_pro_name);
            holder.tv_pro_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_pro_name.setText(list.get(position).getProductName());
        status = list.get(position).getStatus();
        if (status.equals("submit")) {
            holder.tv_pro_status.setText("待确认");
        } else if (status.equals("confirm")) {
            holder.tv_pro_status.setText("已确认");
        } else if (status.equals("cancel")) {
            holder.tv_pro_status.setText("无效预约");
        }

        holder.tv_order_time.setText(list.get(position).getBookingTime());

        return convertView;
    }

    class Holder {
        TextView tv_pro_name;
        TextView tv_pro_status;
        TextView tv_order_time;
    }
}
