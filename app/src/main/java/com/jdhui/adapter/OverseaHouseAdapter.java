package com.jdhui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.OverseaProjectList3B;
import com.jdhui.mould.types.MouldList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import static com.jdhui.uitls.ImageLoaderManager.options;

// 海外项目 Adapter
public class OverseaHouseAdapter extends BaseAdapter {
    private MouldList<OverseaProjectList3B> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public OverseaHouseAdapter(Context context, MouldList<OverseaProjectList3B> list) {
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

        //缓存图片到本地
        DisplayImageOptions options = getDisplayImageOptions();

        //加载图片
        ImageLoader.getInstance().displayImage(list.get(position).getPath(), holder.iv_oversea_house, options);

        holder.tv_oversea_name.setText(list.get(position).getName());
        holder.tv_oversea_price.setText(list.get(position).getPrice() + "万元起");
        holder.tv_oversea_area.setText("面积" + list.get(position).getArea());
        return convertView;
    }

    private DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.bg_normal)
                    .showImageOnFail(R.drawable.bg_normal)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    class Holder {
        ImageView iv_oversea_house;
        TextView tv_oversea_name;
        TextView tv_oversea_price;
        TextView tv_oversea_area;
    }
}
