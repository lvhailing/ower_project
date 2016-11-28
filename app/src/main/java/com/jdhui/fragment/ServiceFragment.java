package com.jdhui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jdhui.R;
import com.jdhui.act.AssetFixedActivity;
import com.jdhui.act.AssetFloatActivity;
import com.jdhui.act.AssetInsuranceActivity;
import com.jdhui.act.MessageActivity;
import com.jdhui.act.ac.GeneticTestingListActivity;
import com.jdhui.act.ac.SubBookingHospitalActivity;
import com.jdhui.bean.ResultAccountIndexBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;

/**
 * 底部导航---服务
 */
public class ServiceFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout rl_hospital; //绿通就医
    private RelativeLayout rl_genetic; //基因检测
    private RelativeLayout rl_golf; //高尔夫球场地
    private RelativeLayout rl_plane; //公务机包机

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_service, null);
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

        rl_hospital.setOnClickListener(this);
        rl_genetic.setOnClickListener(this);
        rl_golf.setOnClickListener(this);
        rl_plane.setOnClickListener(this);
    }

    private void initData() {
        requestData();
    }

    private void requestData() {
        /*String userId = null;
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
        });*/
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

                break;
            case R.id.rl_plane: //公务机包机

                break;

        }
    }

}
