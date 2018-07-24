package com.jdhui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.BonusListBean;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.StringUtil;

public class AssetInsuranceDetailAdapter extends BaseAdapter {
    private MouldList<BonusListBean> list;
    private Context context;
    private LayoutInflater inflater;

    public AssetInsuranceDetailAdapter(Context context, MouldList<BonusListBean> list) {
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
            convertView = inflater.inflate(R.layout.item_asset_insurance_detail, null);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_dividend_amount = (TextView) convertView.findViewById(R.id.tv_dividend_amount);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        String date = list.get(position).getRepayDate();
        if (date != null && date.length() >= 10) {
            holder.tv_date.setText(date.substring(0, 10).replace("/", "-"));
        }

        String bonusAmount = list.get(position).getBonusAmount();
        if (!TextUtils.isEmpty(bonusAmount) && !bonusAmount.equals("--")) {
            bonusAmount = StringUtil.formatNum(bonusAmount);
        }
        holder.tv_dividend_amount.setText(bonusAmount);


        return convertView;
    }

    class Holder {
        TextView tv_date;
        TextView tv_dividend_amount;
    }
}
