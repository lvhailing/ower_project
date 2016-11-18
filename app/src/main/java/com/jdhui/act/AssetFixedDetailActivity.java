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
 * 资产页固定收益详情
 * Created by hasee on 2016/8/10.
 */
public class AssetFixedDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_asset_fixed_call;           //年度报告
    private TextView tv_asset_fixed_title;          //产品标题
    private TextView tv_asset_fixed_name;           //产品名称
    private TextView tv_asset_fixed_goumaijine;     //购买金额
    private TextView tv_asset_fixed_yujishouyi;     //预计收益
    private TextView tv_asset_fixed_chanpinqixian;  //产品期限
    private TextView tv_asset_fixed_goumairiqi;     //购买日期
    private TextView tv_asset_fixed_chengliriqi;    //成立日期
    private TextView tv_asset_fixed_fuxinjiange;    //付息间隔
    private TextView tv_asset_fixed_beizhu;         //备注

    private ImageView id_img_back;
    private String tenderId;
    private String productName;
    private ResultAssetFixedProductDetailBean assetFixedBean;
    private RelativeLayout ll_asset_fixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_asset_fixed_detail);
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

        assetFixedBean = new ResultAssetFixedProductDetailBean();

        id_img_back = (ImageView) findViewById(R.id.id_img_back);
        tv_asset_fixed_call = (TextView) findViewById(R.id.tv_asset_fixed_call);
        tv_asset_fixed_title = (TextView) findViewById(R.id.tv_asset_fixed_title);
        tv_asset_fixed_name = (TextView) findViewById(R.id.tv_asset_fixed_name);
        tv_asset_fixed_goumaijine = (TextView) findViewById(R.id.tv_asset_fixed_goumaijine);
        tv_asset_fixed_yujishouyi = (TextView) findViewById(R.id.tv_asset_fixed_yujishouyi);
        tv_asset_fixed_chanpinqixian = (TextView) findViewById(R.id.tv_asset_fixed_chanpinqixian);
        tv_asset_fixed_goumairiqi = (TextView) findViewById(R.id.tv_asset_fixed_goumairiqi);
        tv_asset_fixed_chengliriqi = (TextView) findViewById(R.id.tv_asset_fixed_chengliriqi);
        tv_asset_fixed_fuxinjiange = (TextView) findViewById(R.id.tv_asset_fixed_fuxinjiange);
        tv_asset_fixed_beizhu = (TextView) findViewById(R.id.tv_asset_fixed_beizhu);
        ll_asset_fixed = (RelativeLayout) findViewById(R.id.ll_asset_fixed);

        id_img_back.setOnClickListener(this);
        tv_asset_fixed_call.setOnClickListener(this);
        ll_asset_fixed.setOnClickListener(this);
    }

    public void setView(){


        tv_asset_fixed_title.setText(productName);
        tv_asset_fixed_name.setText(assetFixedBean.getProductName());
        tv_asset_fixed_goumaijine.setText(StringUtil.formatNum(assetFixedBean.getTenderAmount()));
        tv_asset_fixed_yujishouyi.setText(assetFixedBean.getAnnualRate());
        tv_asset_fixed_chanpinqixian.setText(assetFixedBean.getTimeLimit());
        tv_asset_fixed_goumairiqi.setText(assetFixedBean.getPurchaseDate());
        tv_asset_fixed_chengliriqi.setText(assetFixedBean.getEstablishmentDate());
        tv_asset_fixed_fuxinjiange.setText(assetFixedBean.getRepayType());
        tv_asset_fixed_beizhu.setText(assetFixedBean.getRemark());

        if(assetFixedBean.getIsAnnualReport().equals("yes")){           // 是否有年度报告		yes:有;no:无
            tv_asset_fixed_call.setVisibility(View.VISIBLE);
        }else{
            tv_asset_fixed_call.setClickable(false);
            tv_asset_fixed_call.setVisibility(View.GONE);
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
            case R.id.tv_asset_fixed_call:              //年度报告
//                finish();
                Intent i_web = new Intent();
                i_web.setClass(this,WebActivity.class);
                i_web.putExtra("id",assetFixedBean.getProductId());
                i_web.putExtra("type",WebActivity.WEBTYPE_PRODUCT_CALL);
                i_web.putExtra("title","年度报告");
                startActivity(i_web);

                break;
            case R.id.ll_asset_fixed:
                if(assetFixedBean!=null){
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(AssetFixedDetailActivity.this,FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId",assetFixedBean.getProductId());
                    i_fixedProductDetail.putExtra("type","optimum");
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


        HtmlRequest.getAssetProductDetail(this, userId,tenderId,"optimum" ,new BaseRequester.OnRequestListener() {
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
