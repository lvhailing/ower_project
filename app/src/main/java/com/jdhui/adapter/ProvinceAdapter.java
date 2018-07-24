package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.db.model.Province;

import java.util.List;

public class ProvinceAdapter extends BaseAdapter {

    private List<Province> list;
    private LayoutInflater inflater;
    private int selectPos = -1;  //点中了哪项 哪项要背景变白

    public ProvinceAdapter(Context context, List<Province> list) {
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
            convertView = inflater.inflate(R.layout.activity_hospital_list_province_city_item, null);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (selectPos == position) {
            holder.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            holder.rl.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.tv_name.setText(list.get(position).getName());

        return convertView;
    }

    class Holder {
        TextView tv_name;
        RelativeLayout rl;
    }

    public void changeBg(int selectPos) {
        this.selectPos = selectPos;
        notifyDataSetChanged();
    }
}
