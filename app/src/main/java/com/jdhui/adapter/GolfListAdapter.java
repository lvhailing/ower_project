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

public class GolfListAdapter extends BaseAdapter {

    private MouldList<GolfList3B> list;
    private LayoutInflater inflater;

    public GolfListAdapter(Context context, MouldList<GolfList3B> list) {
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
            convertView = inflater.inflate(R.layout.activity_golf_list_item, null);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            holder.tv_golf_name = (TextView) convertView.findViewById(R.id.tv_golf_name);
            convertView.setTag(holder);
        } else {
            holder = (GolfListAdapter.Holder) convertView.getTag();
        }

        //加载图片
        ImageLoader.getInstance().displayImage(list.get(position).getListPhoto(), holder.iv_photo);
        holder.tv_golf_name.setText(list.get(position).getGolfName());

        return convertView;
    }

    class Holder {
        ImageView iv_photo;
        TextView tv_golf_name;

    }
}
