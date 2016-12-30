package com.jdhui.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jdhui.act.LoginActivity;
import com.jdhui.bean.ResultLoginOffContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.uitls.PreferenceUtil;


public class UserLoadout {
    private Context context;

    public UserLoadout(Context context) {
        this.context = context;
    }

    public void requestData() {
        HtmlRequest.loginoff(context, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                ResultLoginOffContentBean b = (ResultLoginOffContentBean) params.result;
                // if (b != null) {
                // if (Boolean.parseBoolean(b.getFlag())) {
//				PreferenceUtil.setAutoLoginAccount("");
                PreferenceUtil.setAutoLoginPwd("");
                PreferenceUtil.setLogin(false);
//				PreferenceUtil.setFirstLogin(true);
                PreferenceUtil.setPhone("");
                PreferenceUtil.setUserId("");
                PreferenceUtil.setUserNickName("");
                PreferenceUtil.setCookie("");
                PreferenceUtil.setIsShowAsset(true);
//                PreferenceUtil.setGestureChose(true);
                if (PreferenceUtil.isFirstLogin()){
                    if (PreferenceUtil.isGestureChose()) {
                        PreferenceUtil.setGestureChose(true);
                    } else {
                        PreferenceUtil.setGestureChose(false);

                    }
                }


                // i.putExtra("result", "exit");
                // setResult(9, i);
                Toast.makeText(context, "退出成功", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setClass(context, LoginActivity.class);
                i.putExtra("tomain", "6");
                context.startActivity(i);
                // } else {
                // Toast.makeText(context, b.getMsg(), Toast.LENGTH_LONG)
                // .show();
                // }
                // }
            }
        });
    }
}
