package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultMessageListBean;
import com.jdhui.mould.types.MouldList;

public class MessageAdapter extends BaseAdapter {
    private MouldList<ResultMessageListBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, MouldList<ResultMessageListBean> list) {
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
            convertView = inflater.inflate(R.layout.activity_message_item, null);
            holder.tv_notice_item_title = (TextView) convertView.findViewById(R.id.tv_notice_item_title);
            holder.tv_notice_item_time = (TextView) convertView.findViewById(R.id.id_notice_item_tv_time);
            holder.tv_notice_item_content = (TextView) convertView.findViewById(R.id.id_notice_item_tv_content);
            holder.tv_flag = (TextView) convertView.findViewById(R.id.tv_flag);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_notice_item_title.setText(list.get(position).getTitle());
        holder.tv_notice_item_time.setText(list.get(position).getSendTime());
        holder.tv_notice_item_content.setText(list.get(position).getContent());
        if (list.get(position).getStatus().equals("read")) { //已读
            holder.tv_notice_item_title.setTextColor(context.getResources().getColor(R.color.gray));
            holder.tv_flag.setTextColor(context.getResources().getColor(R.color.gray));
            holder.tv_flag.setBackgroundResource(R.drawable.shape_bg_read_gray);
            holder.tv_flag.setText("已读");
        } else if (list.get(position).getStatus().equals("unread")) { //未读
            holder.tv_notice_item_title.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_flag.setTextColor(context.getResources().getColor(R.color.white));
            holder.tv_flag.setBackgroundResource(R.drawable.shape_bg_unread_red);
            holder.tv_flag.setText("未读");
        } else { //未知状态 默认
            holder.tv_notice_item_title.setTextColor(context.getResources().getColor(R.color.gray));
            holder.tv_flag.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class Holder {
        TextView tv_notice_item_title;
        TextView tv_notice_item_time;
        TextView tv_notice_item_content;
        TextView tv_flag;
    }
}
