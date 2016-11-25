package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.db.model.City;

import java.util.List;

public class CityAdapter extends BaseAdapter {
    private List<City> list;
    private LayoutInflater inflater;

    public CityAdapter(Context context, List<City> list) {
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
        CityAdapter.Holder holder = null;
        if (convertView == null) {
            holder = new CityAdapter.Holder();
            convertView = inflater.inflate(R.layout.ac_hospital_list_province_city_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (CityAdapter.Holder) convertView.getTag();
        }

        holder.tv_name.setText(list.get(position).getName());

        return convertView;
    }

    class Holder {
        TextView tv_name;
    }

    public void setList(List<City> list) {
        this.list = list;
    }

}
