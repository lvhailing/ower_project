package com.jdhui.act;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhui.JdehuiApplication;
import com.jdhui.R;
import com.jdhui.bean.ResultCheckVersionContentBean;
import com.jdhui.bean.mybean.ResultRedDot2B;
import com.jdhui.fragment.AssetFragment;
import com.jdhui.fragment.MoreFragment;
import com.jdhui.fragment.ProductFragment;
import com.jdhui.fragment.ServiceFragment;
import com.jdhui.mould.BaseParams;
import com.jdhui.mould.BaseRequester;
import com.jdhui.mould.BaseRequester.OnRequestListener;
import com.jdhui.mould.HtmlRequest;
import com.jdhui.service.AppUpgradeService;
import com.jdhui.uitls.SystemInfo;
import com.jdhui.view.CheckVersionDialog;
import com.jdhui.view.MessageDialog;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;

    private LinearLayout mLlTabAsset;    //资产
    private LinearLayout mLlTabProduct;    //产品
    private LinearLayout mLlTabService;    //服务
    private LinearLayout mLlTabMore; //更多

    private ImageView mIvProduct;
    private ImageView mIvService;
    private ImageView mIvAsset;
    private ImageView mIvMore;

    private TextView mTvProduct;
    private TextView mTvService;
    private TextView mTvAsset;
    private TextView mTvMore;

    private List<Fragment> mFragments;
    private AssetFragment tab_asset; //资产
    private ProductFragment tab_product; //产品
    private ServiceFragment tab_service; //服务
    private MoreFragment tab_more; //更多
    private ImageView mIvCircleRed;  // 小红点 （有未读新公告时显示）
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int tab = intent.getIntExtra("tab", 0);
            setSelect(tab);
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_main_viewpager);
        mLlTabAsset = (LinearLayout) findViewById(R.id.ll_tab_asset);
        mLlTabProduct = (LinearLayout) findViewById(R.id.ll_tab_product);
        mLlTabService = (LinearLayout) findViewById(R.id.ll_tab_service);
        mLlTabMore = (LinearLayout) findViewById(R.id.ll_tab_more);

        mIvAsset = (ImageView) findViewById(R.id.iv_asset);
        mIvProduct = (ImageView) findViewById(R.id.iv_product);
        mIvService = (ImageView) findViewById(R.id.iv_service);
        mIvMore = (ImageView) findViewById(R.id.iv_more);
        mIvCircleRed = (ImageView) findViewById(R.id.iv_circle_red);

        mTvAsset = (TextView) findViewById(R.id.tv_asset);
        mTvProduct = (TextView) findViewById(R.id.tv_product);
        mTvService = (TextView) findViewById(R.id.tv_service);
        mTvMore = (TextView) findViewById(R.id.tv_more);

        mIvAsset.setImageResource(R.mipmap.icon_tab_asset_pressed);


        mFragments = new ArrayList<Fragment>();
        tab_asset = new AssetFragment();
        tab_product = new ProductFragment();
        tab_service = new ServiceFragment();
        tab_more = new MoreFragment();

        mFragments.add(tab_asset);
        mFragments.add(tab_product);
        mFragments.add(tab_service);
        mFragments.add(tab_more);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }

    private void initEvent() {
        mLlTabAsset.setOnClickListener(this);
        mLlTabProduct.setOnClickListener(this);
        mLlTabService.setOnClickListener(this);
        mLlTabMore.setOnClickListener(this);
    }

    private void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
        requestBulletinUnreadCount();

        if (result > 0 && i == 3) {
            tab_more.synchroData(3);
        }
    }

    private void initData() {
        requestData();
        requestBulletinUnreadCount();
    }

    private void setTab(int currentItem) {
        resetImgs();
        switch (currentItem) {
            case 0:
                mTvAsset.setTextColor(Color.parseColor("#8a0002"));
                mIvAsset.setImageResource(R.mipmap.icon_tab_asset_pressed);
                mIvProduct.setImageResource(R.mipmap.img_product_icon_normal);
                mIvService.setImageResource(R.mipmap.img_news_icon_normal);
                mIvMore.setImageResource(R.mipmap.img_more_icon_normal);
                break;
            case 1:
                mTvProduct.setTextColor(Color.parseColor("#8a0002"));
                mIvAsset.setImageResource(R.mipmap.img_asset_icon_normal);
                mIvProduct.setImageResource(R.mipmap.icon_tab_product_pressed);
                mIvService.setImageResource(R.mipmap.img_news_icon_normal);
                mIvMore.setImageResource(R.mipmap.img_more_icon_normal);
                break;
            case 2:
                mTvService.setTextColor(Color.parseColor("#8a0002"));
                mIvAsset.setImageResource(R.mipmap.img_asset_icon_normal);
                mIvProduct.setImageResource(R.mipmap.img_product_icon_normal);
                mIvService.setImageResource(R.mipmap.icon_tab_service_pressed);
                mIvMore.setImageResource(R.mipmap.img_more_icon_normal);
                break;
            case 3:
                mTvMore.setTextColor(Color.parseColor("#8a0002"));
                mIvAsset.setImageResource(R.mipmap.img_asset_icon_normal);
                mIvProduct.setImageResource(R.mipmap.img_product_icon_normal);
                mIvService.setImageResource(R.mipmap.img_news_icon_normal);
                mIvMore.setImageResource(R.mipmap.icon_tab_more_pressde);
                break;

        }
    }

    private void resetImgs() {
        mTvAsset.setTextColor(Color.parseColor("#999999"));
        mTvProduct.setTextColor(Color.parseColor("#999999"));
        mTvService.setTextColor(Color.parseColor("#999999"));
        mTvMore.setTextColor(Color.parseColor("#999999"));
    }

    @Override
    public void onResume() {
        super.onResume();
        requestBulletinUnreadCount();
//        Log.i("hhh", "Main--onresume：调用接口了！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static final String TYPE = "android";
    protected static final String tag = "MainActivity";
    private File destDir = null;
    private File destFile = null;

    /**
     * 检查版本更新
     */
    private void requestData() {
        HtmlRequest.checkVersion(MainActivity.this, TYPE, new OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params.result != null) {
                    final ResultCheckVersionContentBean b = (ResultCheckVersionContentBean) params.result;
                    if (b != null) {
                        //后台版本为已停运、未上线，不做处理
                        if (!TextUtils.isEmpty(b.getVersion())) {
                            if (!b.getVersion().equals(SystemInfo.sVersionName)) {
                                CheckVersionDialog dialog = new CheckVersionDialog(MainActivity.this, new CheckVersionDialog.OnCheckVersion() {
                                    @Override
                                    public void onConfim() {
                                        Intent updateIntent = new Intent(MainActivity.this, AppUpgradeService.class);
                                        updateIntent.putExtra("titleId", R.string.app_chinesename);
                                        updateIntent.putExtra("downloadUrl",
                                                // "http://114.113.238.90:40080/upload/app/vjinke.apk");
                                                //	ApplicationConsts.EC_HOST + b.getUrl()
                                                b.getUrl());
                                        MainActivity.this.startService(updateIntent);
                                    }

                                    @Override
                                    public void onCancel() {
                                    }
                                }, "发现新版本,是否更新");
                                dialog.show();
                            } else {
//										Toast.makeText(MainActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                    if (destDir == null) {
                                        destDir = new File(Environment.getExternalStorageDirectory().getPath() + JdehuiApplication.mDownloadPath);
                                    }
                                    if (destDir.exists() || destDir.mkdirs()) {
                                        destFile = new File(destDir.getPath() + "/" + URLEncoder.encode("http://114.113.238.90:40080/upload/app/vjinke.apk"));
                                        if (destFile.exists() && destFile.isFile() && checkApkFile(destFile.getPath())) {
                                            destFile.delete();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (isAppInstalled("com.jundehui")) {
                        MessageDialog dialog = new MessageDialog(MainActivity.this, new MessageDialog.OnCheckVersion() {
                            @Override
                            public void onConfim() {
                            }

                            @Override
                            public void onCancel() {
                            }
                        }, "由于版本更新问题，请您手动卸载之前旧版本，祝您生活愉快");
                        dialog.show();
                    } else {
//								Toast.makeText(MainActivity.this,"未安装",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 更多模块是否显示小红点（有未读公告时则显示）
     */
    private void requestBulletinUnreadCount() {
        HtmlRequest.getBulletinUnreadCount(MainActivity.this, new BaseRequester.OnRequestListener() {
            @Override
            public void onRequestFinished(BaseParams params) {
                if (params == null) {
                    Toast.makeText(MainActivity.this, "加载失败，请确认网络通畅", Toast.LENGTH_LONG).show();
                    return;
                }
                ResultRedDot2B bulletinUnreadCount = (ResultRedDot2B) params.result;
                String unreadCount = bulletinUnreadCount.getNum();
                result = Integer.parseInt(unreadCount);
                if (bulletinUnreadCount != null && !TextUtils.isEmpty(unreadCount) && result > 0) {
                    mIvCircleRed.setVisibility(View.VISIBLE);
                    tab_more.synchroData(3);
                } else {
                    mIvCircleRed.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    public boolean checkApkFile(String apkFilePath) {
        boolean result = false;
        try {
            PackageManager pManager = getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_asset:  // 资产
                setSelect(0);
                break;
            case R.id.ll_tab_product:  // 产品
                setSelect(1);
                break;
            case R.id.ll_tab_service:  // 服务
                setSelect(2);
                break;
            case R.id.ll_tab_more:  // 更多
                setSelect(3);
                break;

        }
    }

    public void freshBulletinUnreadCount(int num) {
        if (num == 1) {
            mIvCircleRed.setVisibility(View.VISIBLE);
        } else {
            mIvCircleRed.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AssetFragment.ASSET_REQUEST_CODE) {
//			initData();
            tab_asset.initData();
        } /*else  {
            tab_more.initData();
            requestBulletinUnreadCount();
        }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
        }
    }
}
