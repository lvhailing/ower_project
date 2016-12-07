package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.bean.mybean.PlaneMarchListBean;
import com.jdhui.mould.types.MouldList;

public class PlaneDetailAdapter extends BaseAdapter {
    private MouldList<PlaneMarchListBean> list;
    private Context context;
    private LayoutInflater inflater;

    public PlaneDetailAdapter(Context context, MouldList<PlaneMarchListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            convertView = inflater.inflate(R.layout.ac_plane_detail_item, null);
            holder.tv_numbers = (TextView) convertView.findViewById(R.id.tv_numbers);
            holder.tv_strat_city = (TextView) convertView.findViewById(R.id.tv_strat_city);
            holder.tv_end_city = (TextView) convertView.findViewById(R.id.tv_end_city);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv_numbers.setText(list.get(position).getNumbers());
        holder.tv_strat_city.setText(list.get(position).getStartCity());
        holder.tv_end_city.setText(list.get(position).getEndCity());
        holder.tv_time.setText(list.get(position).getMatchTime());

        return convertView;
    }

    class Holder {
        TextView tv_numbers;
        TextView tv_strat_city;
        TextView tv_end_city;
        TextView tv_time;
    }
}
