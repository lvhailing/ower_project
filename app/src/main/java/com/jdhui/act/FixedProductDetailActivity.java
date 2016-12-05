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
import com.jdhui.bean.mybean.BookingInsurance2B;
import com.jdhui.bean.mybean.BookingProduct2B;
import com.jdhui.dialog.BookingDialog;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.ViewUtils;

import static com.jdhui.R.id.btn_order;

/**
 * 浮动产品详情，固定产品详情
 * Created by hasee on 2016/8/11.
 */
public class FixedProductDetailActivity extends BaseActivity implements View.OnClickListener {

    private String productType = null; //optimum:固收，floating:浮动收益
    private String productId = null;

    private LinearLayout ll_fixed_product_detail_rengoujine; //认购金额
    private LinearLayout ll_fixed_product_detail_yujishouyi; //预计收益

    private TextView tv_fixed_product_detail_touzifanwei;
    private TextView tv_fixed_product_detail_fengkongcuoshi;
    private TextView tv_fixed_product_detail_touziliangdian;
    private ImageView id_img_back;

    private TextView tv_fixed_product_detail_more_info;

    private ResultFixedProductDetailBean fixedDetailBean;
    private String annualRateType = null; // 预计收益类型(direct:直接显示;region:区间显示)

    // 直接显示产品净值和预期收益
    private LinearLayout ll_fixed_product_detail_chanpinjingzhi;
    private LinearLayout ll_fixed_product_detail_yuqishouyi;

    // 区间显示预期收益
    private LinearLayout ll_fixed_product_detail_profit;

    private TextView tv_fixed_product_detail_name;          //产品名称
    private TextView tv_fixed_product_detail_chanpinguimo;  //产品规模
    private TextView tv_fixed_product_detail_touzimenkan;  //投资门槛
    private TextView tv_fixed_product_detail_chanpinqixian;  //产品期限
    private TextView tv_fixed_product_detail_chanpinjingzhi;  //产品净值
    private TextView tv_fixed_product_detail_yujishouyi;  //预期收益
    private TextView tv_fixed_product_detail_fuxijiange;  //付息间隔
    private TextView tv_fixed_product_detail_chengliriqi;  //成立日期
    private TextView tv_fixed_product_detail_guanlifei;  //管理费
    private TextView tv_fixed_product_detail_tuoguanfei;  //托管费
    private TextView tv_fixed_product_detail_guanliren;  //管理人
    private TextView tv_fixed_product_detail_tuoguanren;  //托管人
    private Button btn_order; //立即预约
    private String productName;

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
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);
        productId = getIntent().getStringExtra("productId");
        productType = getIntent().getStringExtra("type");
        fixedDetailBean = new ResultFixedProductDetailBean();

        id_img_back = (ImageView) findViewById(R.id.id_img_back);
        ll_fixed_product_detail_rengoujine = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_rengoujine);
        ll_fixed_product_detail_yujishouyi = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_yujishouyi);

        tv_fixed_product_detail_touzifanwei = (TextView) findViewById(R.id.tv_fixed_product_detail_touzifanwei);
        tv_fixed_product_detail_fengkongcuoshi = (TextView) findViewById(R.id.tv_fixed_product_detail_fengkongcuoshi);
        tv_fixed_product_detail_touziliangdian = (TextView) findViewById(R.id.tv_fixed_product_detail_touziliangdian);
        tv_fixed_product_detail_more_info = (TextView) findViewById(R.id.tv_fixed_product_detail_more_info);

        ll_fixed_product_detail_chanpinjingzhi = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_chanpinjingzhi);
        ll_fixed_product_detail_yuqishouyi = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_yuqishouyi);
        ll_fixed_product_detail_profit = (LinearLayout) findViewById(R.id.ll_fixed_product_detail_profit);

        tv_fixed_product_detail_name = (TextView) findViewById(R.id.tv_fixed_product_detail_name);
        tv_fixed_product_detail_chanpinguimo = (TextView) findViewById(R.id.tv_fixed_product_detail_chanpinguimo);
        tv_fixed_product_detail_touzimenkan = (TextView) findViewById(R.id.tv_fixed_product_detail_touzimenkan);
        tv_fixed_product_detail_chanpinqixian = (TextView) findViewById(R.id.tv_fixed_product_detail_chanpinqixian);
        tv_fixed_product_detail_chanpinjingzhi = (TextView) findViewById(R.id.tv_fixed_product_detail_chanpinjingzhi);
        tv_fixed_product_detail_yujishouyi = (TextView) findViewById(R.id.tv_fixed_product_detail_yujishouyi);
        tv_fixed_product_detail_fuxijiange = (TextView) findViewById(R.id.tv_fixed_product_detail_fuxijiange);
        tv_fixed_product_detail_chengliriqi = (TextView) findViewById(R.id.tv_fixed_product_detail_chengliriqi);
        tv_fixed_product_detail_guanlifei = (TextView) findViewById(R.id.tv_fixed_product_detail_guanlifei);
        tv_fixed_product_detail_tuoguanfei = (TextView) findViewById(R.id.tv_fixed_product_detail_tuoguanfei);
        tv_fixed_product_detail_guanliren = (TextView) findViewById(R.id.tv_fixed_product_detail_guanliren);
        tv_fixed_product_detail_tuoguanren = (TextView) findViewById(R.id.tv_fixed_product_detail_tuoguanren);
        btn_order = (Button) findViewById(R.id.btn_order);


        id_img_back.setOnClickListener(this);
        tv_fixed_product_detail_touzifanwei.setOnClickListener(this);
        tv_fixed_product_detail_fengkongcuoshi.setOnClickListener(this);
        tv_fixed_product_detail_touziliangdian.setOnClickListener(this);
        btn_order.setOnClickListener(this);
    }

    public void setView() {
        annualRateType = fixedDetailBean.getAnnualRateType();
        if (!TextUtils.isEmpty(annualRateType)) {
            if (annualRateType.equals("direct")) { // 预期收益直接显示
                ll_fixed_product_detail_yuqishouyi.setVisibility(View.VISIBLE);
                ll_fixed_product_detail_profit.setVisibility(View.GONE);

                tv_fixed_product_detail_yujishouyi.setText(fixedDetailBean.getAnnualRateDirect());
            } else if (annualRateType.equals("region")) { // 预期收益区间显示
                ll_fixed_product_detail_yuqishouyi.setVisibility(View.GONE);

                ll_fixed_product_detail_profit.setVisibility(View.VISIBLE);

                for (int i = 0; i < fixedDetailBean.getAnnualRateRegion().size(); i++) {
                    addView(fixedDetailBean.getAnnualRateRegion().get(i).getAmount(), fixedDetailBean.getAnnualRateRegion().get(i).getRate());
                }

            } else {
                ll_fixed_product_detail_yuqishouyi.setVisibility(View.VISIBLE);
                ll_fixed_product_detail_profit.setVisibility(View.GONE);
            }
        } else {
            ll_fixed_product_detail_yuqishouyi.setVisibility(View.VISIBLE);

            ll_fixed_product_detail_profit.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(fixedDetailBean.getUnitNet())) {  //判断产品净值是否可见
            ll_fixed_product_detail_chanpinjingzhi.setVisibility(View.VISIBLE);
            tv_fixed_product_detail_chanpinjingzhi.setText(fixedDetailBean.getUnitNet());
        } else {
            ll_fixed_product_detail_chanpinjingzhi.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(fixedDetailBean.getEstablishmentDate())) {  //判断立即预约按钮是否可见
            tv_fixed_product_detail_chengliriqi.setText("无");
            btn_order.setVisibility(View.VISIBLE);
        } else {
            tv_fixed_product_detail_chengliriqi.setText(fixedDetailBean.getEstablishmentDate());
            btn_order.setVisibility(View.GONE);
        }
        productName = fixedDetailBean.getProductName();
        tv_fixed_product_detail_name.setText(productName);
        tv_fixed_product_detail_chanpinguimo.setText(fixedDetailBean.getAmount());

        tv_fixed_product_detail_touzimenkan.setText(fixedDetailBean.getTenderCondition());
        tv_fixed_product_detail_chanpinqixian.setText(fixedDetailBean.getTimeLimit());

        tv_fixed_product_detail_fuxijiange.setText(fixedDetailBean.getRepayType());
        tv_fixed_product_detail_guanlifei.setText(fixedDetailBean.getAdministrativeFee());
        tv_fixed_product_detail_tuoguanfei.setText(fixedDetailBean.getTrusteeFee());
        tv_fixed_product_detail_guanliren.setText(fixedDetailBean.getAdministrator());
        tv_fixed_product_detail_tuoguanren.setText(fixedDetailBean.getTrustee());

        tv_fixed_product_detail_touzifanwei.setTextColor(getResources().getColor(R.color.txt_orange_gray));
        tv_fixed_product_detail_fengkongcuoshi.setTextColor(getResources().getColor(R.color.bg_black));
        tv_fixed_product_detail_touziliangdian.setTextColor(getResources().getColor(R.color.bg_black));

        tv_fixed_product_detail_touzifanwei.setBackgroundResource(R.color.white);
        tv_fixed_product_detail_fengkongcuoshi.setBackgroundResource(R.color.bg_gray);
        tv_fixed_product_detail_touziliangdian.setBackgroundResource(R.color.bg_gray);

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
        ll_fixed_product_detail_rengoujine.addView(i_line);
        ll_fixed_product_detail_rengoujine.addView(t_rengou);

        TextView t_yujishouyi = new TextView(this);
        t_yujishouyi.setText(profit + "%");
        t_yujishouyi.setTextColor(getResources().getColor(R.color.bg_black));
        t_yujishouyi.setLayoutParams(params);

        ImageView i_line_r = new ImageView(this);
        i_line_r.setBackgroundResource(R.color.bg_gray);
        i_line_r.setLayoutParams(params_line);

        ll_fixed_product_detail_yujishouyi.addView(i_line_r);
        ll_fixed_product_detail_yujishouyi.addView(t_yujishouyi);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_img_back:
                finish();
                break;
            case R.id.tv_fixed_product_detail_touzifanwei: //投资范围
                tv_fixed_product_detail_touzifanwei.setTextColor(getResources().getColor(R.color.txt_orange_gray));
                tv_fixed_product_detail_fengkongcuoshi.setTextColor(getResources().getColor(R.color.bg_black));
                tv_fixed_product_detail_touziliangdian.setTextColor(getResources().getColor(R.color.bg_black));

                tv_fixed_product_detail_touzifanwei.setBackgroundResource(R.color.white);
                tv_fixed_product_detail_fengkongcuoshi.setBackgroundResource(R.color.bg_gray);
                tv_fixed_product_detail_touziliangdian.setBackgroundResource(R.color.bg_gray);

//                tv_fixed_product_detail_more_info.setText("11投资范围本基金资金用于华物资本投资公司持有的北京秉红嘉盛创业投资有限公司的股权收益权");
                if (fixedDetailBean != null) {
                    tv_fixed_product_detail_more_info.setText(!TextUtils.isEmpty(fixedDetailBean.getInvestCoverage()) ? fixedDetailBean.getInvestCoverage() : "");
                }
                break;
            case R.id.tv_fixed_product_detail_fengkongcuoshi:  //风控措施
                tv_fixed_product_detail_touzifanwei.setTextColor(getResources().getColor(R.color.bg_black));
                tv_fixed_product_detail_fengkongcuoshi.setTextColor(getResources().getColor(R.color.txt_orange_gray));
                tv_fixed_product_detail_touziliangdian.setTextColor(getResources().getColor(R.color.bg_black));

                tv_fixed_product_detail_touzifanwei.setBackgroundResource(R.color.bg_gray);
                tv_fixed_product_detail_fengkongcuoshi.setBackgroundResource(R.color.white);
                tv_fixed_product_detail_touziliangdian.setBackgroundResource(R.color.bg_gray);

//                tv_fixed_product_detail_more_info.setText("222风控措施本基金资金用于华物资本投资公司持有的北京秉红嘉盛创业投资有限公司的股权收益权");
                if (fixedDetailBean != null) {
                    tv_fixed_product_detail_more_info.setText(!TextUtils.isEmpty(fixedDetailBean.getControlMeasures()) ? fixedDetailBean.getControlMeasures() : "");
                }
                break;
            case R.id.tv_fixed_product_detail_touziliangdian: //投资亮点
                tv_fixed_product_detail_touzifanwei.setTextColor(getResources().getColor(R.color.bg_black));
                tv_fixed_product_detail_fengkongcuoshi.setTextColor(getResources().getColor(R.color.bg_black));
                tv_fixed_product_detail_touziliangdian.setTextColor(getResources().getColor(R.color.txt_orange_gray));

                tv_fixed_product_detail_touzifanwei.setBackgroundResource(R.color.bg_gray);
                tv_fixed_product_detail_fengkongcuoshi.setBackgroundResource(R.color.bg_gray);
                tv_fixed_product_detail_touziliangdian.setBackgroundResource(R.color.white);

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
                requestData(money, remarks);
                ad.dismiss();
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
    private void requestData(String money, String remarks) {
        try {
            String userInfoId = DESUtil.decrypt(PreferenceUtil.getUserId());
            HtmlRequest.subBookingProduct(this, productId, userInfoId, remarks, money, productType, new BaseRequester.OnRequestListener() {
                @Override
                public void onRequestFinished(BaseParams params) {
                    if (params != null) {
                        BookingProduct2B product2B = (BookingProduct2B) params.result;
                        if (product2B != null) {
                            if (!Boolean.parseBoolean(product2B.getMessage())) {
                                Toast.makeText(FixedProductDetailActivity.this, "预约成功", Toast.LENGTH_LONG).show();
                                finish();
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
