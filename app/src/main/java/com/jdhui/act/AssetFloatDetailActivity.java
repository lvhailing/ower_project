package com.jdhui.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.ResultAssetFixedProductDetailBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.ActivityStack;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;

/**
 * 资产页浮动收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetFloatDetailActivity extends BaseActivity implements View.OnClickListener{


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
    private RelativeLayout ll_asset_float;        //备注

    private String tenderId;
    private String productName;
    private ResultAssetFixedProductDetailBean assetFixedBean;


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

        idImgBack.setOnClickListener(this);
        tvAssetFloatCall.setOnClickListener(this);
        ll_asset_float.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_float_detail);
        initView();
        initData();
    }
    private void initData(){
        requestAssetFixedDetail();
    }

    private void initView(){

        ActivityStack stack = ActivityStack.getActivityManage();
        stack.addActivity(this);
        tenderId = getIntent().getStringExtra("tenderId");
        productName = getIntent().getStringExtra("productName");
        assignViews();

    }

    public void setView(){
        tvAssetFloatTitle.setText(productName);
        tvAssetFloatName.setText(assetFixedBean.getProductName());
        tvAssetFloatGoumaijine.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tvAssetFloatYujishouyi.setText(assetFixedBean.getAnnualRate());
        tvAssetFloatChanpinqixian.setText(assetFixedBean.getTimeLimit());
        tvAssetFloatGoumairiqi.setText(assetFixedBean.getPurchaseDate());
        tvAssetFloatChengliriqi.setText(assetFixedBean.getEstablishmentDate());
        tvAssetFloatFuxinjiange.setText(assetFixedBean.getRepayType());
        tvAssetFloatBeizhu.setText(assetFixedBean.getRemark());

        if(assetFixedBean.getIsAnnualReport().equals("yes")){
            tvAssetFloatCall.setVisibility(View.VISIBLE);
            tvAssetFloatCall.setClickable(true);
        }else{
            tvAssetFloatCall.setVisibility(View.GONE);
            tvAssetFloatCall.setClickable(false);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_img_back:
                finish();
                break;
            case R.id.tv_asset_float_call:
                Intent i_web = new Intent();
                i_web.setClass(this,WebActivity.class);
                i_web.putExtra("id",assetFixedBean.getProductId());
                i_web.putExtra("type",WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title","年度报告");
                startActivity(i_web);
                break;
            case R.id.ll_asset_float:
                if(assetFixedBean!=null){
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(AssetFloatDetailActivity.this,FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId",assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type","floating");
                    this.startActivity(i_fixedProductDetail);
                }

                break;
        }
    }
    private void requestAssetFixedDetail(){
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }


        HtmlRequest.getAssetProductDetail(this, userId,tenderId,"floating" ,new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){
                    assetFixedBean = (ResultAssetFixedProductDetailBean)params.result;
                    if(assetFixedBean!=null){
                        setView();
                    }


                }

            }
        });

    }


}
