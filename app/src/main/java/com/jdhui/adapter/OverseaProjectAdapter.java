package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.OverseaProjectList3B;
import com.jdhui.mould.types.MouldList;
import com.nostra13.universalimageloader.core.ImageLoader;
import static com.jdhui.uitls.ImageLoaderManager.options;

// 海外项目 Adapter
public class OverseaProjectAdapter extends BaseAdapter {
    private MouldList<OverseaProjectList3B> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public OverseaProjectAdapter(Context context, MouldList<OverseaProjectList3B> list) {
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
            convertView = inflater.inflate(R.layout.item_oversea_project, null);
            holder.iv_oversea_house = (ImageView) convertView.findViewById(R.id.iv_oversea_house);
            holder.tv_oversea_name = (TextView) convertView.findViewById(R.id.tv_oversea_name);
            holder.tv_oversea_name = (TextView) convertView.findViewById(R.id.tv_oversea_name);
            holder.tv_oversea_price = (TextView) convertView.findViewById(R.id.tv_oversea_price);
            holder.tv_oversea_area = (TextView) convertView.findViewById(R.id.tv_oversea_area);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //加载图片
        ImageLoader.getInstance().displayImage(list.get(position).getPath(), holder.iv_oversea_house, options);

        holder.tv_oversea_name.setText(list.get(position).getName());
        holder.tv_oversea_price.setText(list.get(position).getPrice() + "万元起");
        holder.tv_oversea_area.setText("面积" + list.get(position).getArea());
        return convertView;
    }

    class Holder {
        ImageView iv_oversea_house;
        TextView tv_oversea_name;
        TextView tv_oversea_price;
        TextView tv_oversea_area;
    }
}
