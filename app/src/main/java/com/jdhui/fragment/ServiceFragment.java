package com.jdhui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jdhui.R;
import com.jdhui.act.ac.LinerListActivity;
import com.jdhui.act.ac.OverseaHouseListActivity;
import com.jdhui.act.ac.OverseasMedicalBookingActivity;
import com.jdhui.act.ac.WebAirPlanBookingActivity;
import com.jdhui.act.ac.GeneticTestingListActivity;
import com.jdhui.act.ac.GolfListActivity;
import com.jdhui.act.ac.SubmitHospitalActivity;
import com.jdhui.bean.mybean.ServicePicture2B;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.mould.types.MouldList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 底部导航---服务
 */
public class ServiceFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout rl_liner; //豪华邮轮游
    private RelativeLayout rl_oversea_asset; // 海外资产配置
    private RelativeLayout rl_hospital; // 绿通就医
    private RelativeLayout rl_genetic; // 基因检测
    private RelativeLayout rl_golf; // 高尔夫球场地
    private RelativeLayout rl_plane; // 公务机包机
    private RelativeLayout rl_overseas_medical; // 海外医疗

    private ImageView iv_liner; // 豪华邮轮游背景图
    private ImageView iv_asset; // 海外资产配置
    private ImageView iv_hospital; // 绿通就医背景图
    private ImageView iv_genetic; // 基因检测背景图
    private ImageView iv_golf; // 高尔夫球场地背景图
    private ImageView iv_plane; // 公务机包机背景图
    private ImageView iv_overseas_medical; // 海外医疗背景图

    private MouldList<ServicePicture2B> data;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, null);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        }
    }

    private void initView(View view) {
        rl_liner = (RelativeLayout) view.findViewById(R.id.rl_liner);
        rl_oversea_asset = (RelativeLayout) view.findViewById(R.id.rl_oversea_asset);
        rl_hospital = (RelativeLayout) view.findViewById(R.id.rl_hospital);
        rl_genetic = (RelativeLayout) view.findViewById(R.id.rl_genetic);
        rl_golf = (RelativeLayout) view.findViewById(R.id.rl_golf);
        rl_plane = (RelativeLayout) view.findViewById(R.id.rl_plane);
        rl_overseas_medical = (RelativeLayout) view.findViewById(R.id.rl_overseas_medical);

        iv_liner = (ImageView) view.findViewById(R.id.iv_liner);
        iv_asset = (ImageView) view.findViewById(R.id.iv_asset);
        iv_hospital = (ImageView) view.findViewById(R.id.iv_hospital);
        iv_genetic = (ImageView) view.findViewById(R.id.iv_genetic);
        iv_golf = (ImageView) view.findViewById(R.id.iv_golf);
        iv_plane = (ImageView) view.findViewById(R.id.iv_plane);
        iv_overseas_medical = (ImageView) view.findViewById(R.id.iv_overseas_medical);

        //缓存图片到本地
        options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.mipmap.bg_show_img_empty).showImageOnFail(R.mipmap.bg_show_img_empty).resetViewBeforeLoading(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true).displayer(new FadeInBitmapDisplayer(300)).build();

        rl_liner.setOnClickListener(this);
        rl_oversea_asset.setOnClickListener(this);
        rl_hospital.setOnClickListener(this);
        rl_genetic.setOnClickListener(this);
        rl_golf.setOnClickListener(this);
        rl_plane.setOnClickListener(this);
        rl_overseas_medical.setOnClickListener(this);
    }

    private void initData() {
        requestServicePicturesData();
    }

    /**
     * 获取每个服务模块的背景图片
     */
    private void requestServicePicturesData() {
        HtmlRequest.getServicePicture(getActivity(), "123", new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params != null) {
                    data = (MouldList<ServicePicture2B>) params.result;
                    if (data != null) {
                        setView();
                    }
                }
            }
        });
    }

    private void setView() {
        for (ServicePicture2B item : data) {
            if (item.getType().equals("shipBooking")) { // 豪华邮轮
                imageLoader.displayImage(item.getPicture(), iv_liner, options, null);
            } else if (item.getType().equals("houseBooking")) { // 海外资产配置
                imageLoader.displayImage(item.getPicture(), iv_asset, options, null);
            } else if (item.getType().equals("hospitalBooking")) { // 绿通就医
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_hospital);
                imageLoader.displayImage(item.getPicture(), iv_hospital, options, null);
            } else if (item.getType().equals("geneticBooking")) { // 基因检测
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_genetic);
                imageLoader.displayImage(item.getPicture(), iv_genetic, options, null);
            } else if (item.getType().equals("golfBooking")) { // 高尔夫
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_golf);
                imageLoader.displayImage(item.getPicture(), iv_golf, options, null);
            } else if (item.getType().equals("airplaneBooking")) { // 公务机
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_plane);
                imageLoader.displayImage(item.getPicture(), iv_plane, options, null);
            } else if (item.getType().equals("overseasBooking")) { // 海外医疗
                imageLoader.displayImage(item.getPicture(), iv_overseas_medical, options, null);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_liner: //豪华邮轮游预约
                intent = new Intent(getActivity(), LinerListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_oversea_asset: // 海外资产配置
                intent = new Intent(getActivity(), OverseaHouseListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_hospital: //绿通就医
                intent = new Intent(getActivity(), SubmitHospitalActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_genetic: //基因检测
                intent = new Intent(getActivity(), GeneticTestingListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_golf:  //高尔夫球场地
                intent = new Intent(getActivity(), GolfListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_plane: //公务机包机预约
                intent = new Intent(getActivity(), WebAirPlanBookingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_overseas_medical: // 海外医疗预约
                intent = new Intent(getActivity(), OverseasMedicalBookingActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
