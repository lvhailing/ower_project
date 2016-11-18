package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultNoticeContentBean;
import com.jdhui.mould.types.MouldList;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {

    private ArrayList<ResultNoticeContentBean> list;
    private Context context;
    private LayoutInflater inflater;

    public NoticeAdapter(Context context, ArrayList<ResultNoticeContentBean> list) {
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
            convertView = inflater.inflate(R.layout.activity_notice_item, null);
            holder.tv_notice_item_title = (TextView) convertView.findViewById(R.id.id_notice_item_tv_title);
            holder.tv_notice_item_time = (TextView) convertView.findViewById(R.id.id_notice_item_tv_time);
            holder.tv_notice_item_content = (TextView) convertView.findViewById(R.id.id_notice_item_tv_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_notice_item_title.setText(list.get(position).getTitle());
        holder.tv_notice_item_time.setText(list.get(position).getSendTime());
        holder.tv_notice_item_content.setText(list.get(position).getDescription());
        /*if (list.get(position).isRead()){
			holder.tv_notice_item_title.setTextColor(context.getResources().getColor(R.color.gray));
			holder.tv_notice_item_time.setTextColor(context.getResources().getColor(R.color.gray));
			holder.tv_notice_item_content.setTextColor(context.getResources().getColor(R.color.gray));
		}else{
			holder.tv_notice_item_title.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_notice_item_time.setTextColor(context.getResources().getColor(R.color.black));
			holder.tv_notice_item_content.setTextColor(context.getResources().getColor(R.color.black));
		}*/

        return convertView;
    }

    class Holder {
        TextView tv_notice_item_title;
        TextView tv_notice_item_time;
        TextView tv_notice_item_content;

    }
}
