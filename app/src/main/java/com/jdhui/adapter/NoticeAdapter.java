package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultNoticeContentBean;
import com.jdhui.mould.types.MouldList;

public class NoticeAdapter extends BaseAdapter {

    private MouldList<ResultNoticeContentBean> list;
    private Context context;
    private LayoutInflater inflater;
    private String status; // 公告状态 （yes:已阅读；no:未阅读）

    public NoticeAdapter(Context context, MouldList<ResultNoticeContentBean> list) {
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
            convertView = inflater.inflate(R.layout.activity_notice_item, null);
            holder.iv_circle_red = (ImageView) convertView.findViewById(R.id.iv_circle_red);
            holder.tv_notice_item_title = (TextView) convertView.findViewById(R.id.tv_notice_item_title);
            holder.tv_notice_item_time = (TextView) convertView.findViewById(R.id.id_notice_item_tv_time);
            holder.tv_notice_item_content = (TextView) convertView.findViewById(R.id.id_notice_item_tv_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        status = list.get(position).getReadState();
        if (status.equals("no")) {
            holder.iv_circle_red.setVisibility(View.VISIBLE);
        }else if(status.equals("yes")) {
            holder.iv_circle_red.setVisibility(View.GONE);
        }
        holder.tv_notice_item_title.setText(list.get(position).getTitle());
        holder.tv_notice_item_time.setText(list.get(position).getSendTime());
        holder.tv_notice_item_content.setText(list.get(position).getDescription());

        return convertView;
    }

    class Holder {
        ImageView iv_circle_red; // 公告是否阅读
        TextView tv_notice_item_title; // 公告标题
        TextView tv_notice_item_time; // 公告时间
        TextView tv_notice_item_content; // 公告内容
    }
}
