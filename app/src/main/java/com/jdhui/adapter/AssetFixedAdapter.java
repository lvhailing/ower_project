package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultAccountProductTendersItemBean;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.StringUtil;

/**
 * Created by hasee on 2016/8/10.
 */
public class AssetFixedAdapter extends BaseAdapter {

    private Context context;
    private MouldList<ResultAccountProductTendersItemBean> fixedListBeen;
    private LayoutInflater inflater;
    public AssetFixedAdapter(Context context,MouldList<ResultAccountProductTendersItemBean> fixedListBeen) {
        this.context = context;
        this.fixedListBeen = fixedListBeen;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fixedListBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return fixedListBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder= null;
        if(view==null){
            holder = new Holder();
            view = inflater.inflate(R.layout.activity_asset_fixed_item,null);
            holder.tv_asset_fixed_name = (TextView) view.findViewById(R.id.tv_fixed_product_name);
            holder.tv_asset_fixed_count = (TextView) view.findViewById(R.id.tv_asset_fixed_count);
            holder.tv_asset_fixed_scale = (TextView) view.findViewById(R.id.tv_asset_fixed_scale);
            view.setTag(holder);
        }else{
            holder = (Holder) view.getTag();
        }
        holder.tv_asset_fixed_name.setText(fixedListBeen.get(i).getProductName());
        holder.tv_asset_fixed_count.setText("购买金额："+ StringUtil.formatNum(fixedListBeen.get(i).getTransAmount()));
        holder.tv_asset_fixed_scale.setText("占比："+fixedListBeen.get(i).getTenderAmountRate()+"%");

        return view;
    }
    class Holder{
        TextView tv_asset_fixed_name;
        TextView tv_asset_fixed_count;
        TextView tv_asset_fixed_scale;
    }

}
