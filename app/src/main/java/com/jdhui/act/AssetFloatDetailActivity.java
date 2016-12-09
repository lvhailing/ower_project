package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.adapter.AssetFixedDetailAdapter;
import com.jdhui.bean.ResultAssetFixedProductDetailBean;
import com.jdhui.bean.mybean.InterestListBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.view.MyListView;

/**
 * 资产--浮动收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetFloatDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView idImgBack;
    private TextView tvAssetFloatTitle;     //产品标题
    private TextView tvAssetFloatCall;      //年度报告
    private TextView tvAssetFloatName;      //产品名称
    private TextView tvAssetFloatGoumaijine;    //购买金额
    private TextView tvAssetFloatYujishouyi;    //预计收益
    private TextView tvAssetFloatChanpinqixian; //产品期限
    private TextView tvAssetFloatGoumairiqi;    //购买日期
    private TextView tvAssetFloatChengliriqi;   //成立日期
    private TextView tvAssetFloatFuxinjiange;   //付息间隔
    private TextView tvAssetFloatBeizhu;        //备注
    private RelativeLayout ll_asset_float;  //浮收产品名称

    private String tenderId;
    private String productName;
    private ResultAssetFixedProductDetailBean assetFixedBean;
    private MyListView myListView; //加载还款方案的列表


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_float_detail);
        initView();
        initData();
    }

    private void initView() {
        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

        tenderId = getIntent().getStringExtra("tenderId");
        productName = getIntent().getStringExtra("productName");
        assignViews();
    }

    private void initData() {
        requestAssetFixedDetail();
    }

    private void assignViews() {
        idImgBack = (ImageView) findViewById(R.id.id_img_back);
        tvAssetFloatTitle = (TextView) findViewById(R.id.tv_asset_float_title);
        tvAssetFloatCall = (TextView) findViewById(R.id.tv_asset_float_call);
        tvAssetFloatName = (TextView) findViewById(R.id.tv_asset_float_name);
        tvAssetFloatGoumaijine = (TextView) findViewById(R.id.tv_asset_float_goumaijine);
        tvAssetFloatYujishouyi = (TextView) findViewById(R.id.tv_asset_float_yujishouyi);
        tvAssetFloatChanpinqixian = (TextView) findViewById(R.id.tv_asset_float_chanpinqixian);
        tvAssetFloatGoumairiqi = (TextView) findViewById(R.id.tv_asset_float_goumairiqi);
        tvAssetFloatChengliriqi = (TextView) findViewById(R.id.tv_asset_float_chengliriqi);
        tvAssetFloatFuxinjiange = (TextView) findViewById(R.id.tv_asset_float_fuxinjiange);
        tvAssetFloatBeizhu = (TextView) findViewById(R.id.tv_asset_float_beizhu);
        ll_asset_float = (RelativeLayout) findViewById(R.id.ll_asset_float);
        myListView = (MyListView) findViewById(R.id.lv);

        idImgBack.setOnClickListener(this);
        tvAssetFloatCall.setOnClickListener(this);
        ll_asset_float.setOnClickListener(this);
    }

    public void setView() {
        tvAssetFloatTitle.setText(productName);
        tvAssetFloatName.setText(assetFixedBean.getProductName());
        tvAssetFloatGoumaijine.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tvAssetFloatYujishouyi.setText(assetFixedBean.getAnnualRate());
        tvAssetFloatChanpinqixian.setText(assetFixedBean.getTimeLimit());
        tvAssetFloatGoumairiqi.setText(assetFixedBean.getPurchaseDate());
        tvAssetFloatChengliriqi.setText(assetFixedBean.getEstablishmentDate());
        tvAssetFloatFuxinjiange.setText(assetFixedBean.getRepayType());
        tvAssetFloatBeizhu.setText(assetFixedBean.getRemark());

        if (assetFixedBean.getIsAnnualReport().equals("yes")) { // 是否有年度报告	 yes:有;  no:无
            tvAssetFloatCall.setVisibility(View.VISIBLE);
            tvAssetFloatCall.setClickable(true);
        } else {
            tvAssetFloatCall.setVisibility(View.GONE);
            tvAssetFloatCall.setClickable(false);
        }

        MouldList<InterestListBean> interestList = assetFixedBean.getInterestList();
        if (interestList == null || interestList.size() == 0) {
            interestList.add(new InterestListBean("--", "--", "--"));
        }

        //设置还款方案
        AssetFixedDetailAdapter adapter = new AssetFixedDetailAdapter(this, interestList);
        myListView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_back:
                finish();
                break;
            case R.id.tv_asset_float_call: //年度报告 按钮
                Intent i_web = new Intent();
                i_web.setClass(this, WebActivity.class);
                i_web.putExtra("id", assetFixedBean.getProductId());
                i_web.putExtra("type", WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title", "年度报告");
                startActivity(i_web);
                break;
            case R.id.ll_asset_float: //浮收产品名称
                if (assetFixedBean != null) {
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(AssetFloatDetailActivity.this, FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId", assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type", "floating");
                    this.startActivity(i_fixedProductDetail);
                    break;
                }

                break;
        }
    }

    private void requestAssetFixedDetail() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HtmlRequest.getAssetProductDetail(this, userId, tenderId, "floating", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    assetFixedBean = (ResultAssetFixedProductDetailBean) params.result;
                    if (assetFixedBean != null) {
                        setView();
                    }
                }
            }
        });
    }


}
