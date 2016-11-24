package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.BookingHospitalList3B;
import com.jdhui.bean.mybean.Product3B;
import com.jdhui.mould.types.MouldList;

public class HospitalListAdapter extends BaseAdapter {

    private MouldList<BookingHospitalList3B> list;
    private LayoutInflater inflater;

    public HospitalListAdapter(Context context, MouldList<BookingHospitalList3B> list) {
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
            convertView = inflater.inflate(R.layout.ac_hospital_list_item, null);
            holder.tv_hospita_name = (TextView) convertView.findViewById(R.id.tv_hospita_name);
            holder.tv_hospital_level = (TextView) convertView.findViewById(R.id.tv_hospital_level);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_hospita_name.setText(list.get(position).getName());
        holder.tv_hospital_level.setText(list.get(position).getLevel());

        return convertView;
    }

    class Holder {
        TextView tv_hospita_name;
        TextView tv_hospital_level;
    }
}
