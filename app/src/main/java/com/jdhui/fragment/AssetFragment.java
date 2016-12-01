package com.jdhui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jdhui.R;
import com.jdhui.act.AssetFixedActivity;
import com.jdhui.act.AssetFloatActivity;
import com.jdhui.act.AssetInsuranceActivity;
import com.jdhui.act.MessageActivity;
import com.jdhui.bean.ResultAccountIndexBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

import java.util.ArrayList;

/**
 * 底部导航---资产
 */
public class AssetFragment extends Fragment implements View.OnClickListener {

    public final static int ASSET_REQUEST_CODE = 2001;
    private View view;
    private ImageView iv_asset_chosse_my_num;
    private TextView tv_asset_my_num;
    private boolean isshow = true;
    private PieChart mChart;
    private RelativeLayout rl_fragment_asset_fixed, rl_fragment_asset_float, rl_fragment_asset_insurance; //固收、浮收、保险等对应的布局
    private Context context;
    private TextView tv_fragment_asset_passed;              //是否合格投资者
    private TextView tv_fragment_asset_steady;              //是否稳健型
    private ImageView iv_fragment_asset_message;                    //消息

    private TextView tv_fragment_asset_fixed_product_totalnum;          //固收类购买金额
    private TextView tv_fragment_asset_floating_product_totalnum;       //浮动类购买金额
    private TextView tv_fragment_asset_insurance_product_totalnum;      //保险类购买金额
    private ResultAccountIndexBean accountBean;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asset, null);
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        context = getActivity();
        mActivity = getActivity();
        accountBean = new ResultAccountIndexBean();
        iv_asset_chosse_my_num = (ImageView) view.findViewById(R.id.iv_asset_chosse_my_num);
        tv_asset_my_num = (TextView) view.findViewById(R.id.tv_asset_my_num);
        rl_fragment_asset_fixed = (RelativeLayout) view.findViewById(R.id.rl_fragment_asset_fixed);
        rl_fragment_asset_float = (RelativeLayout) view.findViewById(R.id.rl_fragment_asset_float);
        rl_fragment_asset_insurance = (RelativeLayout) view.findViewById(R.id.rl_fragment_asset_insurance);
        tv_fragment_asset_passed = (TextView) view.findViewById(R.id.tv_fragment_asset_passed);
        tv_fragment_asset_steady = (TextView) view.findViewById(R.id.tv_fragment_asset_steady);
        tv_fragment_asset_fixed_product_totalnum = (TextView) view.findViewById(R.id.tv_fragment_asset_fixed_product_totalnum);
        tv_fragment_asset_floating_product_totalnum = (TextView) view.findViewById(R.id.tv_fragment_asset_floating_product_totalnum);
        tv_fragment_asset_insurance_product_totalnum = (TextView) view.findViewById(R.id.tv_fragment_asset_insurance_product_totalnum);
        iv_fragment_asset_message = (ImageView) view.findViewById(R.id.iv_fragment_asset_message);


        if (isshow) {
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_open);
        } else {
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_hide);
        }

        iv_asset_chosse_my_num.setOnClickListener(this);
        rl_fragment_asset_fixed.setOnClickListener(this);
        rl_fragment_asset_float.setOnClickListener(this);
        rl_fragment_asset_insurance.setOnClickListener(this);
        iv_fragment_asset_message.setOnClickListener(this);
        mChart = (PieChart) view.findViewById(R.id.chart1);
    }

    private void initAsset(boolean flag) {           //flag 是否有数据  true有数据   false没数据
        mChart.setMinOffset(0f);
        mChart.setUsePercentValues(true);          //是否用百分比显示
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 15, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);



        if (!flag) {
            mChart.setHoleRadius(0f);
            mChart.setTransparentCircleRadius(0f);
            mChart.setCenterText("暂无投资");
            mChart.setCenterTextColor(Color.WHITE);
            mChart.setDrawHoleEnabled(false);
            mChart.setCenterTextSize(18.0f);
            mChart.setRotationAngle(0);         //角度
        } else {
            mChart.setHoleRadius(45f);
            mChart.setTransparentCircleRadius(45f);
            mChart.setCenterText("资产分布");
            mChart.setCenterTextSize(14.0f);
            mChart.setRotationAngle(90);         //角度
            mChart.setDrawHoleEnabled(true);
        }


        mChart.setDrawCenterText(true);


        mChart.setRotationEnabled(false);           // 可转动
        mChart.setHighlightPerTapEnabled(false);
        setData(flag);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            initData();
            if (context != null) {
                requestUserInfo();
//                Toast.makeText(context,"onresume",Toast.LENGTH_SHORT).show();
            }
        } else {
            if (context != null) {

//                Toast.makeText(context,"onPase",Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void setData(boolean flag) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        if (!flag) {
            entries.add(new PieEntry(100));
        } else {
            if (Integer.parseInt(accountBean.getOptimumAmountRate()) != 0) {
                entries.add(new PieEntry(Integer.parseInt(accountBean.getOptimumAmountRate()), ""));
            }
            if (Integer.parseInt(accountBean.getFloatingAmountRate()) != 0) {
                entries.add(new PieEntry(Integer.parseInt(accountBean.getFloatingAmountRate()), ""));
            }
            if (Integer.parseInt(accountBean.getInsuranceAmountRate()) != 0) {
                entries.add(new PieEntry(Integer.parseInt(accountBean.getInsuranceAmountRate()), ""));
            }

        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);              //设置饼图之间间隙大小
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(18f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] ints = new int[]{};
        if (!flag) {
            ints = new int[]{R.color.asset_null};
        } else {

            if (Integer.parseInt(accountBean.getOptimumAmountRate()) == 0) {
                if (Integer.parseInt(accountBean.getFloatingAmountRate()) == 0) {
                    ints = new int[]{R.color.asset_insurance};
                } else {
                    ints = new int[]{R.color.asset_float_incomea};
                }
            } else {
                if (Integer.parseInt(accountBean.getFloatingAmountRate()) == 0) {
                    if (Integer.parseInt(accountBean.getInsuranceAmountRate()) == 0) {
                        ints = new int[]{R.color.asset_fixed_income};
                    } else {
                        ints = new int[]{R.color.asset_fixed_income, R.color.asset_insurance};
                    }

                } else {
                    if (Integer.parseInt(accountBean.getInsuranceAmountRate()) == 0) {
                        ints = new int[]{R.color.asset_fixed_income, R.color.asset_float_incomea};
                    } else {
                        ints = new int[]{R.color.asset_fixed_income, R.color.asset_float_incomea, R.color.asset_insurance};
                    }
                }
            }

        }

        colors = (ArrayList<Integer>) ColorTemplate.createColors(context.getResources(), ints);

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);        //起点距离中心位置
        dataSet.setValueLinePart1Length(0.4f);              //
        dataSet.setValueLinePart2Length(0.9f);

        dataSet.setValueLineColor(R.color.txt_gray_light_l);
//        dataSet.setValueTextColors(colors);
//         dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);




        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        if (!flag) {
            data.setValueTextSize(0f);                     //百分比字体大小
            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        }else{
            data.setValueTextSize(16f);                     //百分比字体大小
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        }
        data.setValueTextColor(R.color.bg_gray_gray);            //百分比字体颜色设置
//        data.setValueTextColor(R.color.asset_fixed_income);            //百分比字体颜色设置

//        data.setValueTypeface(mTfLight);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);

//        Legend legend = mChart.getLegend();     // 设置比例图
//        legend.setEnabled(false);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setEnabled(false);

        mChart.invalidate();
    }

    public void initData() {
        requestUserInfo();

    }

    public void setView() {
        isshow = PreferenceUtil.getIsShowAsset();
        if (isshow) {
            if (accountBean != null) {
                if (TextUtils.isEmpty(accountBean.getTotalAmount())) {
                    tv_asset_my_num.setText("0.00");
                } else {
                    tv_asset_my_num.setText(StringUtil.formatNum(accountBean.getTotalAmount()));
                }
            } else {
                tv_asset_my_num.setText("0.00");
            }
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_open);
        } else {
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_hide);
            tv_asset_my_num.setText("*****");
        }

        if (accountBean != null) {
            if (!TextUtils.isEmpty(accountBean.getTotalAmount())) {
                if (Float.parseFloat(accountBean.getTotalAmount()) == 0) {
                    initAsset(false);
                } else {
                    initAsset(true);
                }
            }else {
                initAsset(false);
            }
        } else {
            initAsset(false);
        }


//        initAsset(true);
        if (accountBean != null) {
            tv_fragment_asset_fixed_product_totalnum.setText(!TextUtils.isEmpty(accountBean.getOptimumAmount())?StringUtil.formatNum(accountBean.getOptimumAmount()):"0.00");
            tv_fragment_asset_floating_product_totalnum.setText(!TextUtils.isEmpty(accountBean.getFloatingAmount())?StringUtil.formatNum(accountBean.getFloatingAmount()):"0.00");
            tv_fragment_asset_insurance_product_totalnum.setText(!TextUtils.isEmpty(accountBean.getInsuranceAmount())?StringUtil.formatNum(accountBean.getInsuranceAmount()):"0.00");

            if (!TextUtils.isEmpty(accountBean.getUnreadMessageNum())) {

                if (accountBean.getUnreadMessageNum().equals("0")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_0);
                } else if (accountBean.getUnreadMessageNum().equals("1")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_1);
                } else if (accountBean.getUnreadMessageNum().equals("2")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_2);
                } else if (accountBean.getUnreadMessageNum().equals("3")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_3);
                } else if (accountBean.getUnreadMessageNum().equals("4")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_4);
                } else if (accountBean.getUnreadMessageNum().equals("5")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_5);
                } else if (accountBean.getUnreadMessageNum().equals("6")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_6);
                } else if (accountBean.getUnreadMessageNum().equals("7")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_7);
                } else if (accountBean.getUnreadMessageNum().equals("8")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_8);
                } else if (accountBean.getUnreadMessageNum().equals("9")) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_9);
                } else if (Integer.parseInt(accountBean.getUnreadMessageNum()) > 9) {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_10);
                } else {
                    iv_fragment_asset_message.setImageResource(R.mipmap.img_message_0);
                }

            } else {
                iv_fragment_asset_message.setImageResource(R.mipmap.img_message_0);
            }


            // acceptable:合格投资者;unacceptable:不合格投资者
            if(!TextUtils.isEmpty(accountBean.getQualifiedInvestor())){
                if (accountBean.getQualifiedInvestor().equals("acceptable")) {
                    tv_fragment_asset_passed.setVisibility(View.VISIBLE);
                } else {
                    tv_fragment_asset_passed.setVisibility(View.GONE);
                }
            }else{
                tv_fragment_asset_passed.setVisibility(View.GONE);
            }


            // conservative:保守型;steady:稳健型;balance:平衡型;growth:成长型;aggressive:进取型;

            if (!TextUtils.isEmpty(accountBean.getUserType())) {
                if (accountBean.getUserType().equals("conservative")) {
                    tv_fragment_asset_steady.setText("保守型");
                } else if (accountBean.getUserType().equals("steady")) {
                    tv_fragment_asset_steady.setText("稳健型");
                } else if (accountBean.getUserType().equals("balance")) {
                    tv_fragment_asset_steady.setText("平衡型");
                } else if (accountBean.getUserType().equals("growth")) {
                    tv_fragment_asset_steady.setText("成长型");
                } else if (accountBean.getUserType().equals("aggressive")) {
                    tv_fragment_asset_steady.setText("进取型");
                } else {
                    tv_fragment_asset_steady.setVisibility(View.GONE);
                }
            } else {
                tv_fragment_asset_steady.setVisibility(View.GONE);
            }


        } else {
            tv_fragment_asset_fixed_product_totalnum.setText("0.00");
            tv_fragment_asset_floating_product_totalnum.setText("0.00");
            tv_fragment_asset_insurance_product_totalnum.setText("0.00");
        }

    }

    private void onclickAssetData() {
        isshow = PreferenceUtil.getIsShowAsset();
        if (isshow) {
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_hide);
            tv_asset_my_num.setText("*****");
            PreferenceUtil.setIsShowAsset(false);
        } else {
            iv_asset_chosse_my_num.setImageResource(R.mipmap.img_asset_open);
            if (accountBean != null) {
                tv_asset_my_num.setText(StringUtil.formatNum(accountBean.getTotalAmount()));
            } else {
                tv_asset_my_num.setText("0.00");
            }

            PreferenceUtil.setIsShowAsset(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_asset_chosse_my_num: //总资产后的眼睛图标
                onclickAssetData();
                break;
            case R.id.rl_fragment_asset_fixed:  //固定收益类
                Intent i_fixed = new Intent(mActivity, AssetFixedActivity.class);
                mActivity.startActivityForResult(i_fixed, ASSET_REQUEST_CODE);

                break;
            case R.id.rl_fragment_asset_float: //浮动收益类
                Intent i_float = new Intent(mActivity, AssetFloatActivity.class);
                mActivity.startActivityForResult(i_float, ASSET_REQUEST_CODE);

                break;
            case R.id.rl_fragment_asset_insurance: //保险类
                Intent i_insurance = new Intent(mActivity, AssetInsuranceActivity.class);
                mActivity.startActivityForResult(i_insurance, ASSET_REQUEST_CODE);

                break;
            case R.id.iv_fragment_asset_message:  //消息列表
                Intent i_messgae = new Intent(mActivity, MessageActivity.class);
                mActivity.startActivityForResult(i_messgae, ASSET_REQUEST_CODE);
                break;

        }
    }

    private void requestUserInfo() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.accountIndex(context, userId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    accountBean = (ResultAccountIndexBean) params.result;
                    setView();
                }

            }
        });
    }

}
