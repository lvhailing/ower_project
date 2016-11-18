package com.jdhui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultInsuranceProductItemBean;
import com.jdhui.mould.types.MouldList;

public class InsuranceAdapter extends BaseAdapter {

	private MouldList<ResultInsuranceProductItemBean> list;
	private Context context;
	private LayoutInflater inflater;
	public InsuranceAdapter(Context context,
							MouldList<ResultInsuranceProductItemBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
//		Log.e(tag, "list=="+list.size());
	}

	@Override
	public int getCount() {
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}

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
			convertView = inflater.inflate(R.layout.activity_hot_product_item, null);
			holder.tv_hot_product_content_title = (TextView) convertView.findViewById(R.id.tv_hot_product_content_title);
			holder.tv_hot_product_content_first_one = (TextView) convertView.findViewById(R.id.tv_hot_product_content_first_one);
			holder.tv_hot_product_content_first_two = (TextView) convertView.findViewById(R.id.tv_hot_product_content_first_two);
			holder.tv_hot_product_content_Second_one = (TextView) convertView.findViewById(R.id.tv_hot_product_content_Second_one);
			holder.tv_hot_product_content_Second_two = (TextView) convertView.findViewById(R.id.tv_hot_product_content_Second_two);
			holder.tv_hot_product_content_Third_one = (TextView) convertView.findViewById(R.id.tv_hot_product_content_Third_one);
			holder.tv_hot_product_content_Third_two = (TextView) convertView.findViewById(R.id.tv_hot_product_content_Third_two);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.tv_hot_product_content_title.setText(list.get(position).getProductName());
		holder.tv_hot_product_content_first_one.setText(list.get(position).getCompanyName());
		holder.tv_hot_product_content_first_two.setText("保险公司");
		holder.tv_hot_product_content_Second_one.setText(list.get(position).getTimeLimit());
		holder.tv_hot_product_content_Second_two.setText("保险期间");

		if(list.get(position).getProtectionType().equals("heavyDiseaseClass")){
			holder.tv_hot_product_content_Third_one.setText("重疾类");
		}else if(list.get(position).getProtectionType().equals("highMedicalClass")){
			holder.tv_hot_product_content_Third_one.setText("高端医疗类");
		}else if(list.get(position).getProtectionType().equals("termInsurance")){
			holder.tv_hot_product_content_Third_one.setText("定期险");
		}else if(list.get(position).getProtectionType().equals("retirementInsurance")){
			holder.tv_hot_product_content_Third_one.setText("养老险");
		}else if(list.get(position).getProtectionType().equals("pensionInsurance")){
			holder.tv_hot_product_content_Third_one.setText("年金险");
		}else if(list.get(position).getProtectionType().equals("participatingInsurance")){
			holder.tv_hot_product_content_Third_one.setText("分红险");
		}else if(list.get(position).getProtectionType().equals("shortTermCardOneClass")){
			holder.tv_hot_product_content_Third_one.setText("短期卡单类");
		}else{
			holder.tv_hot_product_content_Third_one.setText("——");
		}
//		holder.tv_hot_product_content_Third_one.setText(list.get(position).getProtectionType());
		holder.tv_hot_product_content_Third_two.setText("保障类型");

		return convertView;
	}
	class Holder{
		TextView tv_hot_product_content_title;
		TextView tv_hot_product_content_first_one;
		TextView tv_hot_product_content_first_two;
		TextView tv_hot_product_content_Second_one;
		TextView tv_hot_product_content_Second_two;
		TextView tv_hot_product_content_Third_one;
		TextView tv_hot_product_content_Third_two;

	}
}
