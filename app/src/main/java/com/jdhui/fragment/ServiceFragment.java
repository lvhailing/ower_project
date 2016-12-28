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
import com.jdhui.act.ac.WebAirPlanBookingActivity;
import com.jdhui.act.ac.GeneticTestingListActivity;
import com.jdhui.act.ac.GolfListActivity;
import com.jdhui.act.ac.SubBookingHospitalActivity;
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
    private RelativeLayout rl_hospital; //绿通就医
    private RelativeLayout rl_genetic; //基因检测
    private RelativeLayout rl_golf; //高尔夫球场地
    private RelativeLayout rl_plane; //公务机包机
    private ImageView iv_hospital;
    private ImageView iv_genetic;
    private ImageView iv_golf;
    private ImageView iv_plane;
    private MouldList<ServicePicture2B> data;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

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
        rl_hospital = (RelativeLayout) view.findViewById(R.id.rl_hospital);
        rl_genetic = (RelativeLayout) view.findViewById(R.id.rl_genetic);
        rl_golf = (RelativeLayout) view.findViewById(R.id.rl_golf);
        rl_plane = (RelativeLayout) view.findViewById(R.id.rl_plane);

        iv_hospital = (ImageView) view.findViewById(R.id.iv_hospital);
        iv_genetic = (ImageView) view.findViewById(R.id.iv_genetic);
        iv_golf = (ImageView) view.findViewById(R.id.iv_golf);
        iv_plane = (ImageView) view.findViewById(R.id.iv_plane);

        //缓存图片到本地
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.banner_three)
                .showImageOnFail(R.drawable.banner_three)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        rl_hospital.setOnClickListener(this);
        rl_genetic.setOnClickListener(this);
        rl_golf.setOnClickListener(this);
        rl_plane.setOnClickListener(this);
    }

    private void initData() {
        requestData();
    }

    private void requestData() {
       /* String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
            if (item.getType().equals("hospitalBooking")) {
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_hospital);
                imageLoader.displayImage(item.getPicture(), iv_hospital, options,null);

            } else if (item.getType().equals("geneticBooking")) {
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_genetic);
                imageLoader.displayImage(item.getPicture(), iv_genetic, options,null);
            } else if (item.getType().equals("golfBooking")) {
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_golf);
                imageLoader.displayImage(item.getPicture(), iv_golf, options,null);
            } else if (item.getType().equals("airplaneBooking")) {
//                ImageLoader.getInstance().displayImage(item.getPicture(), iv_plane);
                imageLoader.displayImage(item.getPicture(), iv_plane, options,null);
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
            case R.id.rl_hospital: //绿通就医
                Intent intent1 = new Intent(getActivity(), SubBookingHospitalActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_genetic: //基因检测
                Intent intent2 = new Intent(getActivity(), GeneticTestingListActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_golf:  //高尔夫球场地
                Intent intent3 = new Intent(getActivity(), GolfListActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_plane: //公务机包机预约
                Intent intent4 = new Intent(getActivity(), WebAirPlanBookingActivity.class);
                startActivity(intent4);
                break;

        }
    }

}
