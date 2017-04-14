package com.jdhui.adapter.btmvp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.LinerInfo3B;
import com.jdhui.bean.mybean.LinerInfo4B;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.MyListView;

import java.util.ArrayList;

/**
 * 邮轮详情页第二屏 底部vp的Adapter
 */
public class MyBtmAdapterFactory {

    public View getView(Context context, LinerInfo3B liner) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_liner_btm, null);

        TextView tv_startPoint = (TextView) view.findViewById(R.id.tv_startPoint); //离港地点
        TextView tv_endPiont = (TextView) view.findViewById(R.id.tv_endPoint); //抵港地点
        TextView tv_startTime = (TextView) view.findViewById(R.id.tv_startTime); //离港时间
        TextView tv_endTime = (TextView) view.findViewById(R.id.tv_endTime); //抵港时间
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);//历时 如：2晚3日
        TextView tv_single_ticket = (TextView) view.findViewById(R.id.tv_single_ticket);//“单船票”或“一价全含”
        ListView house_list = (MyListView) view.findViewById(R.id.lv);//“单船票”或“一价全含”

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

        ArrayList<LinerInfo4B> totalList = liner.getCabinTypePrice();
        if (totalList != null && totalList.size() > 0) {

            CabinTypeListAdapter mAdapter = new CabinTypeListAdapter(context, totalList);
            house_list.setAdapter(mAdapter);

            Log.i("aaa", "mAdapter: " + mAdapter);
        }

        return view;
    }
}
