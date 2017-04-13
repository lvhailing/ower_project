package com.jdhui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo3B;
import com.jdhui.bean.mybean.LinerInfo4B;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮轮详情页第二屏 底部vp的Adapter
 */
public class MyBtmAdapter extends PagerAdapter {
    private List<LinerInfo3B> btmList;
    private Context context;
    private HouseListAdapter mAdapter;
    private ArrayList<LinerInfo4B> totalList;

    public MyBtmAdapter(List<LinerInfo3B> btmList, Context context) {
        this.btmList = btmList;
        this.context = context;

        mAdapter = new HouseListAdapter();
    }

    @Override
    public int getCount() {
        return btmList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup viewPager, int position, Object object) {
        viewPager.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup viewPager, final int position) {
        final LinerInfo3B liner = btmList.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_liner_btm, null);

        TextView tv_startPoint = (TextView) view.findViewById(R.id.tv_startPoint); //离港地点
        TextView tv_endPiont = (TextView) view.findViewById(R.id.tv_endPoint); //抵港地点
        TextView tv_startTime = (TextView) view.findViewById(R.id.tv_startTime); //离港时间
        TextView tv_endTime = (TextView) view.findViewById(R.id.tv_endTime); //抵港时间
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);//历时 如：2晚3日
        TextView tv_single_ticket = (TextView) view.findViewById(R.id.tv_single_ticket);//“单船票”或“一价全含”
        ListView house_list = (MyListView) view.findViewById(R.id.lv);//“单船票”或“一价全含”

//        //四种房对应的价格（内舱房、海景房、阳台房、套房）
//        TextView tv_price_1 = (TextView) view.findViewById(R.id.tv_price_1);
//        TextView tv_price_2 = (TextView) view.findViewById(R.id.tv_price_2);
//        TextView tv_price_3 = (TextView) view.findViewById(R.id.tv_price_3);
//        TextView tv_price_4 = (TextView) view.findViewById(R.id.tv_price_4);

        tv_startPoint.setText(StringUtil.getResult(liner.getStartPoint()));
        tv_endPiont.setText(StringUtil.getResult(liner.getEndPiont()));
        tv_startTime.setText(liner.getStartTime());
        tv_endTime.setText(liner.getEndTime());
        tv_time.setText(StringUtil.getResult(liner.getRouteDuration()));
        String ticketType = liner.getTicketsType();
        if (ticketType.equals("seasonTicket")) {
            tv_single_ticket.setText("一价全含");
        } else if (ticketType.equals("oneTicket")) {
            tv_single_ticket.setText("单船票");
        }

        totalList = liner.getCabinTypePrice();
        if (totalList != null && totalList.size() > 0) {
                house_list.setAdapter(mAdapter);
        }

//        tv_price_1.setText(liner.getInnerRoom());
//        tv_price_2.setText(liner.getSeaviewRoom());
//        tv_price_3.setText(liner.getBalconyRoom());
//        tv_price_4.setText(liner.getSuiteRoom());
        viewPager.addView(view);
        return view;
    }

    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局

    private class HouseListAdapter extends BaseAdapter {
        public HouseListAdapter() {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return totalList == null ? 0 : totalList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = mInflater.inflate(R.layout.house_list_item, null);
                holder = new ViewHolder();
                holder.tv_house_type = (TextView) view.findViewById(R.id.tv_house_type);
                holder.tv_house_price = (TextView) view.findViewById(R.id.tv_house_price);
                view.setTag(holder);//绑定ViewHolder对象
            } else {
                holder = (ViewHolder) view.getTag();//取出ViewHolder对象
            }
            holder.tv_house_type.setText(totalList.get(position).getCabinType());
            holder.tv_house_price.setText(totalList.get(position).getCabinPrice());


            return view;
        }

    }

    /*存放控件*/
    public class ViewHolder {
        public TextView tv_house_type; //房子的类型
        public TextView tv_house_price; //房子的价格
    }
}
