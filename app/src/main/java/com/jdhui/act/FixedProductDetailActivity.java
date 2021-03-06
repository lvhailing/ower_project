package com.jdhui.act;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.R;
import com.jdhui.bean.ResultFixedProductDetailBean;
import com.jdhui.bean.mybean.BookingProduct2B;
import com.jdhui.dialog.BookingDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.ViewUtils;

/**
 * 产品--固收和浮收的产品详情（产品信息）
 * Created by hasee on 2016/8/11.
 */
public class FixedProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private LinearLayout ll_fixed_subscription_amount; //认购金额
    private LinearLayout ll_fixed_performance_benchmark_year; //预计收益
    // 直接显示产品净值和预期收益
    private LinearLayout ll_fixed_net_product;
    private LinearLayout ll_fixed_performance_benchmark;
    private LinearLayout ll_fixed_product_detail_profit;  // 区间显示预期收益

    private TextView tv_fixed_investment_scope; //投资范围
    private TextView tv_fixed_risk_control_measures; // 投资措施
    private TextView tv_fixed_investment_highlights; // 投资亮点
    private TextView tv_fixed_product_detail_name; //产品名称
    private TextView tv_fixed_product_scale;  //产品规模
    private TextView tv_fixed_product_detail_touzimenkan;  //投资门槛
    private TextView tv_fixed_product_deadline;  //产品期限
    private TextView tv_fixed_net_product;  //产品净值
    private TextView tv_fixed_performance_benchmark;  //预期收益
    private TextView tv_fixed_interest_interval;  //付息间隔
    private TextView tv_fixed_register_date;  //成立日期
    private TextView tv_risk_type;//风险类型 ( conservative:保守型;  steady:稳健型; growth:成长型;  aggressive:进取型;)
    private TextView tv_fixed_management_fee;  //管理费
    private TextView tv_fixed_trust_fee;  //托管费
    private TextView tv_fixed_administrator;  //管理人
    private TextView tv_fixed_custodian;  //托管人
    private Button btn_order; //立即预约
    private String productName;
    private TextView tv_fixed_product_detail_more_info;

    private ResultFixedProductDetailBean fixedDetailBean;
    private String annualRateType = null; // 预计收益类型(direct:直接显示;region:区间显示)
    private String productType = null; //optimum:固收，floating:浮动收益
    private String productId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fixed_product_detail);
        initView();
        initData();
    }

    private void initData() {
        requestFixedDetail();
    }

    public void initView() {
        productId = getIntent().getStringExtra("productId");
        productType = getIntent().getStringExtra("type");
        fixedDetailBean = new ResultFixedProductDetailBean();

        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_fixed_subscription_amount = (LinearLayout) findViewById(R.id.ll_fixed_subscription_amount);
        ll_fixed_performance_benchmark_year = (LinearLayout) findViewById(R.id.ll_fixed_performance_benchmark_year);
        ll_fixed_net_product = (LinearLayout) findViewById(R.id.ll_fixed_net_product);
        ll_fixed_performance_benchmark = (LinearLayout) findViewById(R.id.ll_fixed_performance_benchmark);
        ll_fixed_product_detail_profit = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_profit);

        tv_fixed_investment_scope = (TextView) findViewById(R.id.tv_fixed_investment_scope);
        tv_fixed_risk_control_measures = (TextView) findViewById(R.id.tv_fixed_risk_control_measures);
        tv_fixed_investment_highlights = (TextView) findViewById(R.id.tv_fixed_investment_highlights);
        tv_fixed_product_detail_more_info = (TextView) findViewById(R.id.tv_fixed_product_detail_more_info);
        tv_fixed_product_detail_name = (TextView) findViewById(R.id.tv_fixed_product_detail_name);
        tv_fixed_product_scale = (TextView) findViewById(R.id.tv_fixed_product_scale);
        tv_fixed_product_detail_touzimenkan = (TextView) findViewById(R.id.tv_fixed_product_investment_threshold);
        tv_fixed_product_deadline = (TextView) findViewById(R.id.tv_fixed_product_deadline);
        tv_fixed_net_product = (TextView) findViewById(R.id.tv_fixed_net_product);
        tv_fixed_performance_benchmark = (TextView) findViewById(R.id.tv_fixed_performance_benchmark);
        tv_fixed_interest_interval = (TextView) findViewById(R.id.tv_fixed_interest_interval);
        tv_fixed_register_date = (TextView) findViewById(R.id.tv_fixed_register_date);
        tv_fixed_management_fee = (TextView) findViewById(R.id.tv_fixed_management_fee);
        tv_fixed_trust_fee = (TextView) findViewById(R.id.tv_fixed_trust_fee);
        tv_fixed_administrator = (TextView) findViewById(R.id.tv_fixed_administrator);
        tv_fixed_custodian = (TextView) findViewById(R.id.tv_fixed_custodian);
        tv_risk_type = (TextView) findViewById(R.id.tv_risk_type);
        btn_order = (Button) findViewById(R.id.btn_order);


        iv_back.setOnClickListener(this);
        tv_fixed_investment_scope.setOnClickListener(this);
        tv_fixed_risk_control_measures.setOnClickListener(this);
        tv_fixed_investment_highlights.setOnClickListener(this);
        btn_order.setOnClickListener(this);
    }

    public void setView() {
        annualRateType = fixedDetailBean.getAnnualRateType();
        if (!TextUtils.isEmpty(annualRateType)) {
            if (annualRateType.equals("direct")) { // 业绩比较基准直接显示
                ll_fixed_performance_benchmark.setVisibility(View.VISIBLE);
                ll_fixed_product_detail_profit.setVisibility(View.GONE);
                tv_fixed_performance_benchmark.setText(fixedDetailBean.getAnnualRateDirect());
            } else if (annualRateType.equals("region")) { // 业绩比较基准区间显示
                ll_fixed_performance_benchmark.setVisibility(View.GONE);
                ll_fixed_product_detail_profit.setVisibility(View.VISIBLE);

                for (int i = 0; i < fixedDetailBean.getAnnualRateRegion().size(); i++) {
                    addView(fixedDetailBean.getAnnualRateRegion().get(i).getAmount(), fixedDetailBean.getAnnualRateRegion().get(i).getRate());
                }
            } else {
                ll_fixed_performance_benchmark.setVisibility(View.VISIBLE);
                ll_fixed_product_detail_profit.setVisibility(View.GONE);
            }
        } else {
            ll_fixed_performance_benchmark.setVisibility(View.VISIBLE);
            ll_fixed_product_detail_profit.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(fixedDetailBean.getUnitNet())) {  //判断产品净值是否可见
            ll_fixed_net_product.setVisibility(View.VISIBLE);
            tv_fixed_net_product.setText(fixedDetailBean.getUnitNet());
        } else {
            ll_fixed_net_product.setVisibility(View.GONE);
        }
        String flag = fixedDetailBean.getEstablishedFlag();
        if (Boolean.parseBoolean(flag)) {  //判断立即预约按钮是否可见 false:未成立；true:成立（只有未成立时显示立即预约按钮）
            btn_order.setVisibility(View.GONE);
        } else {
            btn_order.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(fixedDetailBean.getEstablishmentDate())) { //成立日期
            tv_fixed_register_date.setText(fixedDetailBean.getEstablishmentDate());
        } else {
            tv_fixed_register_date.setText("无");
        }

        String riskType = fixedDetailBean.getRiskType();
        if (riskType.equals("conservative")) {
            tv_risk_type.setText("保守型");
        } else if (riskType.equals("steady")) {
            tv_risk_type.setText("稳健型");
        } else if (riskType.equals("growth")) {
            tv_risk_type.setText("成长型");
        } else if (riskType.equals("aggressive")) {
            tv_risk_type.setText("进取型");
        }

        productName = fixedDetailBean.getProductName();
        tv_fixed_product_detail_name.setText(productName);
        tv_fixed_product_scale.setText(fixedDetailBean.getAmount());

        tv_fixed_product_detail_touzimenkan.setText(fixedDetailBean.getTenderCondition());
        tv_fixed_product_deadline.setText(fixedDetailBean.getTimeLimit());

        String repayType = fixedDetailBean.getRepayType();
        if (repayType.equals("monthPayment")) {
            tv_fixed_interest_interval.setText("按月付息，到期还本");
        } else if (repayType.equals("quarterPayment")) {
            tv_fixed_interest_interval.setText("按季付息，到期还本");
        } else if (repayType.equals("halfYearPayment")) {
            tv_fixed_interest_interval.setText("按半年付息，到期还本");
        } else if (repayType.equals("yearPayment")) {
            tv_fixed_interest_interval.setText("按年付息，到期还本");
        } else if (repayType.equals("oneTimePayment")) {
            tv_fixed_interest_interval.setText("一次性还本息");
        }

        tv_fixed_management_fee.setText(fixedDetailBean.getAdministrativeFee());
        tv_fixed_trust_fee.setText(fixedDetailBean.getTrusteeFee());
        tv_fixed_administrator.setText(fixedDetailBean.getAdministrator());
        tv_fixed_custodian.setText(fixedDetailBean.getTrustee());

        tv_fixed_investment_scope.setTextColor(getResources().getColor(R.color.txt_black_light));
        tv_fixed_risk_control_measures.setTextColor(getResources().getColor(R.color.txt_gray_light_l));
        tv_fixed_investment_highlights.setTextColor(getResources().getColor(R.color.txt_gray_light_l));

        tv_fixed_investment_scope.setBackgroundResource(R.color.white);
        tv_fixed_risk_control_measures.setBackgroundResource(R.color.bg_gray);
        tv_fixed_investment_highlights.setBackgroundResource(R.color.bg_gray);

        tv_fixed_product_detail_more_info.setText(fixedDetailBean.getInvestCoverage());


    }

    private void addView(String subscribe, String profit) {
        ImageView i_line = new ImageView(this);
        i_line.setBackgroundResource(R.color.bg_gray);

        LinearLayout.LayoutParams params_line = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewUtils.dip2px(this, 1.0f));
        i_line.setLayoutParams(params_line);

        TextView t_rengou = new TextView(this);
        t_rengou.setText(subscribe);
        t_rengou.setTextColor(getResources().getColor(R.color.bg_black));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(0, ViewUtils.dip2px(this, 10.0f), 0, ViewUtils.dip2px(this, 10.0f));
        t_rengou.setLayoutParams(params);
        ll_fixed_subscription_amount.addView(i_line);
        ll_fixed_subscription_amount.addView(t_rengou);

        TextView t_yujishouyi = new TextView(this);
        t_yujishouyi.setText(profit + "%");
        t_yujishouyi.setTextColor(getResources().getColor(R.color.bg_black));
        t_yujishouyi.setLayoutParams(params);

        ImageView i_line_r = new ImageView(this);
        i_line_r.setBackgroundResource(R.color.bg_gray);
        i_line_r.setLayoutParams(params_line);

        ll_fixed_performance_benchmark_year.addView(i_line_r);
        ll_fixed_performance_benchmark_year.addView(t_yujishouyi);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fixed_investment_scope: //投资范围
                tv_fixed_investment_scope.setTextColor(getResources().getColor(R.color.txt_black_light));
                tv_fixed_risk_control_measures.setTextColor(getResources().getColor(R.color.txt_gray_light_l));
                tv_fixed_investment_highlights.setTextColor(getResources().getColor(R.color.txt_gray_light_l));

                tv_fixed_investment_scope.setBackgroundResource(R.color.white);
                tv_fixed_risk_control_measures.setBackgroundResource(R.color.bg_gray);
                tv_fixed_investment_highlights.setBackgroundResource(R.color.bg_gray);

//                tv_fixed_product_detail_more_info.setText("11投资范围本基金资金用于华物资本投资公司持有的北京秉红嘉盛创业投资有限公司的股权收益权");
                if (fixedDetailBean != null) {
                    tv_fixed_product_detail_more_info.setText(!TextUtils.isEmpty(fixedDetailBean.getInvestCoverage()) ? fixedDetailBean.getInvestCoverage() : "");
                }
                break;
            case R.id.tv_fixed_risk_control_measures:  //风控措施
                tv_fixed_investment_scope.setTextColor(getResources().getColor(R.color.txt_gray_light_l));
                tv_fixed_risk_control_measures.setTextColor(getResources().getColor(R.color.txt_black_light));
                tv_fixed_investment_highlights.setTextColor(getResources().getColor(R.color.txt_gray_light_l));

                tv_fixed_investment_scope.setBackgroundResource(R.color.bg_gray);
                tv_fixed_risk_control_measures.setBackgroundResource(R.color.white);
                tv_fixed_investment_highlights.setBackgroundResource(R.color.bg_gray);

//                tv_fixed_product_detail_more_info.setText("222风控措施本基金资金用于华物资本投资公司持有的北京秉红嘉盛创业投资有限公司的股权收益权");
                if (fixedDetailBean != null) {
                    tv_fixed_product_detail_more_info.setText(!TextUtils.isEmpty(fixedDetailBean.getControlMeasures()) ? fixedDetailBean.getControlMeasures() : "");
                }
                break;
            case R.id.tv_fixed_investment_highlights: //投资亮点
                tv_fixed_investment_scope.setTextColor(getResources().getColor(R.color.txt_gray_light_l));
                tv_fixed_risk_control_measures.setTextColor(getResources().getColor(R.color.txt_gray_light_l));
                tv_fixed_investment_highlights.setTextColor(getResources().getColor(R.color.txt_black_light));

                tv_fixed_investment_scope.setBackgroundResource(R.color.bg_gray);
                tv_fixed_risk_control_measures.setBackgroundResource(R.color.bg_gray);
                tv_fixed_investment_highlights.setBackgroundResource(R.color.white);

//                tv_fixed_product_detail_more_info.setText("333投资亮点本基金资金用于华物资本投资公司持有的北京秉红嘉盛创业投资有限公司的股权收益权");
                if (fixedDetailBean != null) {
                    tv_fixed_product_detail_more_info.setText(!TextUtils.isEmpty(fixedDetailBean.getProductAdvantage()) ? fixedDetailBean.getProductAdvantage() : "");
                }
                break;
            case R.id.btn_order: //立即预约 按钮
                showDialog();
                break;
        }
    }

    //产品预约对话框
    private void showDialog() {
        BookingDialog dialog = new BookingDialog(this, productName, new BookingDialog.MyCallback() {
            @Override
            public void onMyclick(Dialog ad, String money, String remarks) {
                requestData(money, remarks,ad);
                ad = null;
            }
        });
        dialog.subBookingDialog();
    }

    /**
     * 获取非保险产品详情
     */
    private void requestFixedDetail() {
        HtmlRequest.getProductDetail(this, productId, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    fixedDetailBean = (ResultFixedProductDetailBean) params.result;
                    if (fixedDetailBean != null) {
                        setView();
                    }
                }
            }
        });
    }

    /**
     * 非保险预约 接口数据
     *
     * @param money
     * @param remarks
     */
    private void requestData(String money, String remarks,final Dialog ad) {
        try {
            String userInfoId = DESUtil.decrypt(PreferenceUtil.getUserId());
            HtmlRequest.subBookingProduct(this, productId, userInfoId, remarks, money, productType, new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    if (params != null) {
                        BookingProduct2B product2B = (BookingProduct2B) params.result;
                        if (product2B != null) {
                            if (!Boolean.parseBoolean(product2B.getMessage())) {
                                Toast.makeText(FixedProductDetailActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                ad.dismiss();
                            } else {
                                Toast.makeText(FixedProductDetailActivity.this, "预约失败，请您检查提交信息", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(FixedProductDetailActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
