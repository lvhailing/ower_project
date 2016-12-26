package com.jdhui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.StringUtil;

public class AssetFixedDetailAdapter extends BaseAdapter {
    private MouldList<InterestListBean> list;
    private Context context;
    private LayoutInflater inflater;

    public AssetFixedDetailAdapter(Context context, MouldList<InterestListBean> list) {
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
            convertView = inflater.inflate(R.layout.activity_asset_fixed_detail_item, null);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_interest = (TextView) convertView.findViewById(R.id.tv_interest);
            holder.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        String date = list.get(position).getRepayDate();
        if (date != null && date.length() >= 10) {
            holder.tv_date.setText(date.substring(0, 10).replace("/", "-"));
        }

        String interestAmount = list.get(position).getInterestAmount();
        String amount = list.get(position).getAmount();
        if (!TextUtils.isEmpty(interestAmount) && !interestAmount.equals("--")) {
            interestAmount = StringUtil.formatNum(interestAmount); //利息金额
        }
        holder.tv_interest.setText(interestAmount); //利息金额

        if (!TextUtils.isEmpty(interestAmount) && !interestAmount.equals("--")) {
            amount = StringUtil.formatNum(amount); //本息金额
        }
        holder.tv_total.setText(amount); //本息金额

        return convertView;
    }

    class Holder {
        TextView tv_date;
        TextView tv_interest;
        TextView tv_total;
    }
}
