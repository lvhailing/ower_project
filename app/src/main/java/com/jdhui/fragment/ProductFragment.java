package com.jdhui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.act.FixedProductDetailActivity;
import com.jdhui.act.FloatActivity;
import com.jdhui.act.GushouActivity;
import com.jdhui.act.InsuranceActivity;
import com.jdhui.act.InsuranceProductDetailActivity;
import com.jdhui.act.WebActivity;
import com.jdhui.adapter.CycleAdapter;
import com.jdhui.adapter.ProductHotProductAdapter;
import com.jdhui.bean.ImageBean;
import com.jdhui.bean.ResultCycleIndexBean;
import com.jdhui.bean.ResultProductIndexBean;
import com.jdhui.bean.ResultProductIndexHotItemBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.jdhui.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * 底部导航---首页
 */
public class ProductFragment extends Fragment implements View.OnClickListener ,CycleAdapter.ImageCycleViewListener {
    private View mView;
    private LinearLayout mViewPager;
    private LinearLayout mLinearLayout;
    private ArrayList<ImageBean> images;
    private ImageBean imgbean = null;
    private DisplayImageOptions options;
    private CycleAdapter cycleAdapter;
    private MyListView myListView;
    private ProductHotProductAdapter mHotProductAdapter;
//    private BadgeViewOne mBadgeView;
//    private LinearLayout layout_badgeV;
    private TextView mTvGuShou,mTvFloat,mTvInsurance;
    private ScrollView scrollView;
    private Context context;
    private ResultProductIndexBean productIndexBean;
    private MouldList<ResultCycleIndexBean> productcycleBean;
    private MouldList<ResultProductIndexHotItemBean> list;
    private TextView tv_fragment_product_notice;
    private LinearLayout ll_fragment_product_notice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product, container,
                    false);
            try {
                initView(mView);
                initData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if(mView.getParent()!=null){
                ((ViewGroup) mView.getParent()).removeView(mView);
            }


        }

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestProductIndex();
        scrollView.smoothScrollTo(0, 0);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            requestProductIndex();
            scrollView.smoothScrollTo(0, 0);
        }else{

        }

    }

    private void initView(View mView) {
        context = getActivity();
        productIndexBean = new ResultProductIndexBean();
        productcycleBean = new MouldList<ResultCycleIndexBean>();
        list = new MouldList<ResultProductIndexHotItemBean>();

        mViewPager = (LinearLayout) mView.findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) mView.findViewById(R.id.down_dots_ll);
        myListView = (MyListView) mView.findViewById(R.id.lv_home_hot_product);
//        layout_badgeV= (LinearLayout) mView.findViewById(R.id.id_badgeView);
        scrollView= (ScrollView) mView.findViewById(R.id.fragment_home_scrollview);
        mTvGuShou = (TextView) mView.findViewById(R.id.id_tab_home_tv_gushou);
        mTvFloat = (TextView) mView.findViewById(R.id.id_tab_home_tv_float);
        mTvInsurance = (TextView) mView.findViewById(R.id.id_tab_home_tv_insurance);
        tv_fragment_product_notice = (TextView) mView.findViewById(R.id.tv_fragment_product_notice);
        ll_fragment_product_notice = (LinearLayout) mView.findViewById(R.id.ll_fragment_product_notice);

        mTvGuShou.setOnClickListener(this);
        mTvFloat.setOnClickListener(this);
        mTvInsurance.setOnClickListener(this);
        tv_fragment_product_notice.setOnClickListener(this);
        ll_fragment_product_notice.setOnClickListener(this);
    }

    private void initData() {

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.banner_one)
                .showImageOnFail(R.drawable.banner_one)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        requestCycleIndex();

    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(Context context,ListView listView,int dividerHeight) {
        if(listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight()+dividerHeight);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * listAdapter.getCount())+5;

        listView.setLayoutParams(params);
    }

    /**
     * 模拟请求轮播图网络数据
     */
    private void requestData() {
        cycleAdapter = new CycleAdapter(context,productcycleBean,options);
        cycleAdapter.setNetAndLinearLayoutMethod(mLinearLayout);
        cycleAdapter.setOnImageListener(new CycleAdapter.ImageCycleViewListener() {
            @Override
            public void onImageClick(int postion, View imageView) {
                if(productcycleBean!=null&&productcycleBean.size()!=0){
                    if(!TextUtils.isEmpty(productcycleBean.get(postion%productcycleBean.size()).getUrl())){
                        Intent i_web = new Intent(context, WebActivity.class);
                        i_web.putExtra("type", WebActivity.WEBTYPE_BANNER);
                        i_web.putExtra("url",productcycleBean.get(postion%productcycleBean.size()).getUrl());
                        i_web.putExtra("title",productcycleBean.get(postion%productcycleBean.size()).getDescription());
                        getActivity().startActivity(i_web);

                    }

                }

            }
        });
        cycleAdapter.setCycle(true);
        cycleAdapter.startRoll();
        mViewPager.addView(cycleAdapter);
    }
    /**
     * 模拟请求热销产品网络数据
     */
    private void initHotProductData() {
        if(!TextUtils.isEmpty(productIndexBean.getBulletin().getTopic())){
            tv_fragment_product_notice.setText(productIndexBean.getBulletin().getTopic());
        }

        mHotProductAdapter =new ProductHotProductAdapter(context,list);
        myListView.setAdapter(mHotProductAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if(list.get(position).getCategory().equals("optimum")){
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(context,FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId",list.get(position).getId());
                    i_fixedProductDetail.putExtra("type","optimum");
                    context.startActivity(i_fixedProductDetail);
                }else if(list.get(position).getCategory().equals("floating")){
                    Intent i_fixedProductDetail = new Intent();
                    i_fixedProductDetail.setClass(context,FixedProductDetailActivity.class);
                    i_fixedProductDetail.putExtra("productId",list.get(position).getId());
                    i_fixedProductDetail.putExtra("type","floating");
                    context.startActivity(i_fixedProductDetail);
                }else if(list.get(position).getCategory().equals("insurance")){
                    Intent i_insuranceProductDetail = new Intent();
                    i_insuranceProductDetail.setClass(context,InsuranceProductDetailActivity.class);
                    i_insuranceProductDetail.putExtra("productId",list.get(position).getId());
                    context.startActivity(i_insuranceProductDetail);
                }
            }
        });
        setListViewHeightBasedOnChildren(getActivity(),myListView,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tab_home_tv_gushou:
                Intent i_gushou=new Intent(context,GushouActivity.class);
                startActivity(i_gushou);
                break;
            case R.id.id_tab_home_tv_float:
                Intent i_float=new Intent(context,FloatActivity.class);
                startActivity(i_float);
                break;
            case R.id.id_tab_home_tv_insurance:
                Intent i_insurance=new Intent(context,InsuranceActivity.class);
                startActivity(i_insurance);
                break;

            case R.id.ll_fragment_product_notice:
                if(productIndexBean!=null){
                    Intent i_web=new Intent(context,WebActivity.class);
                    i_web.putExtra("type", WebActivity.WEBTYPE_NOTICE_DETAILS);
                    i_web.putExtra("id",productIndexBean.getBulletin().getId());
                    i_web.putExtra("title","详情");
                    startActivity(i_web);
                }

                break;
            case R.id.tv_fragment_product_notice:
                if(productIndexBean!=null){
                    Intent i_web=new Intent(context,WebActivity.class);
                    i_web.putExtra("type", WebActivity.WEBTYPE_NOTICE_DETAILS);
                    i_web.putExtra("id",productIndexBean.getBulletin().getId());
                    i_web.putExtra("title","详情");
                    startActivity(i_web);
                }

                break;
        }
    }

    private void requestProductIndex(){

        HtmlRequest.getProductIndex(context,new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){
                    productIndexBean = (ResultProductIndexBean) params.result;
                    if(productIndexBean!=null){
                        if(list!=null){
                            list.clear();
                        }
                        list.addAll(productIndexBean.getList());
                        initHotProductData();
                    }

//                    setView();


                }

            }
        });
    }

    private void requestCycleIndex(){

        HtmlRequest.getCycleIndex(context,new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if(params!=null){

//                    list.addAll(productIndexBean.getList());
//                    setView();
                    if(params.result!=null){
                        productcycleBean = (MouldList<ResultCycleIndexBean>) params.result;

                    }
//                    requestData();
                }
                requestData();
            }
        });
    }


    @Override
    public void onImageClick(int postion, View imageView) {

    }
}
