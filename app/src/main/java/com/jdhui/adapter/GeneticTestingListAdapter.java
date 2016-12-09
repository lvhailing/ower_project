package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.GeneticTestingList3B;
import com.jdhui.mould.types.MouldList;

/**
 * 基因检测列表 Adapter
 */
public class GeneticTestingListAdapter extends BaseAdapter {

    private MouldList<GeneticTestingList3B> list;
    private LayoutInflater inflater;

    public GeneticTestingListAdapter(Context context, MouldList<GeneticTestingList3B> list) {
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
            convertView = inflater.inflate(R.layout.ac_genetic_list_item, null);
            holder.tv_genetic_name = (TextView) convertView.findViewById(R.id.tv_genetic_name);
            convertView.setTag(holder);
        } else {
            holder = (GeneticTestingListAdapter.Holder) convertView.getTag();
        }
        holder.tv_genetic_name.setText(list.get(position).getName());

        return convertView;
    }

    class Holder {
        TextView tv_genetic_name;
    }
}
