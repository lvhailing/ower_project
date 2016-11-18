package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultNewsContentBean;
import com.jdhui.mould.types.MouldList;

public class NewsAdapter extends BaseAdapter {

	private MouldList<ResultNewsContentBean> list;
	private Context context;
	private LayoutInflater inflater;
	public NewsAdapter(Context context,
					   MouldList<ResultNewsContentBean> list) {
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
		Holder holder= null;
		if(convertView==null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.activity_news_item, null);
			holder.tv_news_name = (TextView) convertView.findViewById(R.id.id_news_name);
			holder.tv_news_time = (TextView) convertView.findViewById(R.id.id_news_time);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.tv_news_name.setText(list.get(position).getTopic());
		holder.tv_news_time.setText(list.get(position).getPublishTime());
		return convertView;
	}
	class Holder{
		TextView tv_news_name;
		TextView tv_news_time;
}}
