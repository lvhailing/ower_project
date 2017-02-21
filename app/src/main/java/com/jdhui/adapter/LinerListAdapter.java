package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.GolfList3B;
import com.jdhui.mould.types.MouldList;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 *  豪华邮轮游 adapter类
 */
public class LinerListAdapter extends BaseAdapter {
    private MouldList<GolfList3B> list;
    private LayoutInflater inflater;

    public LinerListAdapter(Context context, MouldList<GolfList3B> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
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
            convertView.setTag(holder);
        } else {
            holder = (LinerListAdapter.Holder) convertView.getTag();
        }


        return convertView;
    }

    class Holder {
        TextView tv_travel_date;

    }
}
