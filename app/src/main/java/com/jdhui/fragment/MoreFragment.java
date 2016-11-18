package com.jdhui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.JdehuiApplication;
import com.jdhui.R;
import com.jdhui.act.AccountActivity;
import com.jdhui.act.FeedBackActivity;
import com.jdhui.act.ac.NewsActivity;
import com.jdhui.act.NoticeActivity;
import com.jdhui.act.ac.ProductOrderActivity;
import com.jdhui.act.WebActivity;
import com.jdhui.bean.ResultCheckVersionContentBean;
import com.jdhui.bean.ResultMoreInfoContentBean;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.service.AppUpgradeService;
import com.jdhui.uitls.DESUtil;
import com.jdhui.uitls.PreferenceUtil;
import com.jdhui.uitls.StringUtil;
import com.jdhui.uitls.SystemInfo;
import com.jdhui.view.CallServiceDialog;
import com.jdhui.view.CheckVersionDialog;
import com.jdhui.view.CircularImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 底部导航---更多
 */
public class MoreFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RelativeLayout mLayoutAccount, mLayoutNotice, mLayoutContactUs, mLayoutAboutUs,
            mLayoutServiceClauses, mLayoutAgreement, mLayoutCheckVersion, mLayoutFeedBack, mLayoutCheckVersionNumber;
    private TextView mTvName, mTvPhone;
    private CircularImage mImgPhoto;
    private TextView mTvVersionName;
    private ResultMoreInfoContentBean bean;
    private Context context;
    private RelativeLayout mRlProductOrder;  //产品预约；
    private RelativeLayout mRlServiceOrder;  //服务预约；
    private RelativeLayout mRlnews;  //君德快讯；


    /**
     * 图片保存SD卡位置
     */
    private final static String IMG_PATH = Environment.getExternalStorageDirectory() + "/jundehui/imgs/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        context = getActivity();
        mLayoutAccount = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_account);
        mRlProductOrder = (RelativeLayout) view.findViewById(R.id.rl_product_order);
        mRlServiceOrder = (RelativeLayout) view.findViewById(R.id.rl_service_order);
        mRlnews = (RelativeLayout) view.findViewById(R.id.rl_news);

        mLayoutNotice = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_notice);
        mLayoutContactUs = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_contact_us);
        mLayoutAboutUs = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_about_us);
        mLayoutServiceClauses = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_service_clauses);
        mLayoutAgreement = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_agreement);
        mLayoutCheckVersion = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_check_version);
        mLayoutFeedBack = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_feed_back);
        mLayoutCheckVersionNumber = (RelativeLayout) view.findViewById(R.id.id_tab_more_layout_check_version_number);

        mImgPhoto = (CircularImage) view.findViewById(R.id.id_tab_more_img_user);
        mTvName = (TextView) view.findViewById(R.id.id_tab_more_tv_name);
        mTvPhone = (TextView) view.findViewById(R.id.id_tab_more_tv_phone);
        mTvVersionName = (TextView) view.findViewById(R.id.id_tab_more_tv_version_name);
        mTvVersionName.setText(SystemInfo.sVersionName);

        mLayoutAccount.setOnClickListener(this);
        mRlProductOrder.setOnClickListener(this);
        mRlServiceOrder.setOnClickListener(this);
        mRlnews.setOnClickListener(this);
        mLayoutAccount.setClickable(false);
        mLayoutNotice.setOnClickListener(this);
        mLayoutContactUs.setOnClickListener(this);
        mLayoutAboutUs.setOnClickListener(this);
        mLayoutServiceClauses.setOnClickListener(this);
        mLayoutAgreement.setOnClickListener(this);
        mLayoutCheckVersion.setOnClickListener(this);
        mLayoutFeedBack.setOnClickListener(this);
        mLayoutCheckVersionNumber.setOnClickListener(this);

    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_more_layout_account:
                Intent i_account = new Intent(context, AccountActivity.class);
                i_account.putExtra("userInfoId", bean.getUserInfoId());
                startActivity(i_account);
                break;
            case R.id.rl_product_order:  //产品预约
                Intent intent1 = new Intent(context, ProductOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_service_order:  //服务预约；
                Intent intent2 = new Intent(context, NoticeActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_news:  //君德快讯；
                Intent intent3 = new Intent(context, NewsActivity.class);
                startActivity(intent3);
                break;
            case R.id.id_tab_more_layout_notice:  //君德公告；
                Intent i_notice = new Intent(context, NoticeActivity.class);
                startActivity(i_notice);
                break;
            case R.id.id_tab_more_layout_contact_us:
                CallServiceDialog dialog = new CallServiceDialog(getActivity(),
                        new CallServiceDialog.OnCallServiceChanged() {

                            @Override
                            public void onConfim() {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + getString(R.string.tellphone_num));
                                intent.setData(data);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancel() {
                            }
                        }, "客服热线: \n " + getString(R.string.tellphone_num_format));
                dialog.show();
                break;
            case R.id.id_tab_more_layout_about_us:
                Intent i_about_us = new Intent(context, WebActivity.class);
                i_about_us.putExtra("type", WebActivity.WEBTYPE_ABOUT_US);
                i_about_us.putExtra("title", "关于我们");
                startActivity(i_about_us);
                break;
            case R.id.id_tab_more_layout_service_clauses:
                Intent i_service_clauses = new Intent(context, WebActivity.class);
                i_service_clauses.putExtra("type", WebActivity.WEBTYPE_SERVERS);
                i_service_clauses.putExtra("title", "服务条款");
                startActivity(i_service_clauses);
                break;
            case R.id.id_tab_more_layout_agreement:
                Intent i_agreement = new Intent(context, WebActivity.class);
                i_agreement.putExtra("type", WebActivity.WEBTYPE_AGREEMENTS);
                i_agreement.putExtra("title", "隐私协议");
                startActivity(i_agreement);
                break;
            case R.id.id_tab_more_layout_check_version:
                requestData();
                break;
            case R.id.id_tab_more_layout_feed_back:
                Intent i_feed_back = new Intent(context, FeedBackActivity.class);
                startActivity(i_feed_back);
                break;
            case R.id.id_tab_more_layout_check_version_number:
                Intent i_version_number = new Intent(context, WebActivity.class);
                i_version_number.putExtra("type", WebActivity.WEBTYPE_VERSION_NUMBER);
                i_version_number.putExtra("title", "版本号");
                startActivity(i_version_number);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestMoreInfoData();
    }

    /**
     * 请求数据
     */
    private void requestMoreInfoData() {
        String userId = null;
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlRequest.getMoreInfo(context, userId, new BaseRequester.OnRequestListener() {

            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    bean = (ResultMoreInfoContentBean) params.result;
                    setData(bean);
                    mLayoutAccount.setClickable(true);
                } else {
                    Toast.makeText(context, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 获取网落图片资源
     *
     * @return
     */
    class ImageViewService extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap data = getImageBitmap(params[0]);
            return data;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (result != null) {
                mImgPhoto.setImageBitmap(result);
                saveBitmap(result);
            } else {
                mImgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));
            }
        }

    }

    private Uri saveBitmap(Bitmap bm) {
        File tmpDir = new File(IMG_PATH);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File img = new File(IMG_PATH + "Test.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Bitmap getImageBitmap(String url) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void setData(ResultMoreInfoContentBean bean) {
        if (bean != null) {
            mTvName.setText(bean.getUserName());
            mTvPhone.setText(StringUtil.replaceSubString(bean.getMobile()));

            String url = bean.getPictureServerURL();
            if (!TextUtils.isEmpty(url)) {
                new ImageViewService().execute(url);
                // Toast.makeText(activity,"成功", 0).show();
            } else {
                mImgPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
            }
        } else {
            mImgPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.user_icon));
        }
    }

    private static final String TYPE = "android";
    protected static final String tag = "MainActivity";
    private File destDir = null;
    private File destFile = null;

    /**
     * 检查版本更新
     */
    private void requestData() {
        HtmlRequest.checkVersion(context, TYPE,
                new BaseRequester.OnRequestListener() {

                    @Override

                    public void onRequestFinished(BaseParams params) {
                        if (params.result != null) {
                            final ResultCheckVersionContentBean b = (ResultCheckVersionContentBean) params.result;
                            if (b != null) {
                                //后台版本为已停运、未上线，不做处理
                                if (!TextUtils.isEmpty(b.getVersion())) {
                                    if (!b.getVersion().equals(SystemInfo.sVersionName)) {

                                        CheckVersionDialog dialog = new CheckVersionDialog(getActivity(),
                                                new CheckVersionDialog.OnCheckVersion() {

                                                    @Override
                                                    public void onConfim() {
                                                        Intent updateIntent = new Intent(
                                                                context,
                                                                AppUpgradeService.class);
                                                        updateIntent
                                                                .putExtra(
                                                                        "titleId",
                                                                        R.string.app_chinesename);
                                                        updateIntent
                                                                .putExtra(
                                                                        "downloadUrl",
                                                                        // "http://114.113.238.90:40080/upload/app/vjinke.apk");
//																ApplicationConsts.EC_HOST
//																		+ b.getUrl()
                                                                        b.getUrl());
                                                        getActivity().startService(updateIntent);
                                                    }

                                                    @Override
                                                    public void onCancel() {

                                                    }
                                                }, "发现新版本,是否更新");
                                        dialog.show();
                                    } else {
                                        Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
                                        if (Environment
                                                .getExternalStorageState()
                                                .equals(Environment.MEDIA_MOUNTED)) {
                                            if (destDir == null) {
                                                destDir = new File(
                                                        Environment
                                                                .getExternalStorageDirectory()
                                                                .getPath()
                                                                + JdehuiApplication.mDownloadPath);
                                            }
                                            if (destDir.exists() || destDir.mkdirs()) {
                                                destFile = new File(
                                                        destDir.getPath()
                                                                + "/"
                                                                + URLEncoder
                                                                .encode("http://114.113.238.90:40080/upload/app/vjinke.apk"));
                                                if (destFile.exists()
                                                        && destFile.isFile()
                                                        && checkApkFile(destFile
                                                        .getPath())) {
                                                    destFile.delete();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public boolean checkApkFile(String apkFilePath) {
        boolean result = false;
        try {
            PackageManager pManager = getActivity().getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath,
                    PackageManager.GET_ACTIVITIES);
            if (pInfo == null) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
