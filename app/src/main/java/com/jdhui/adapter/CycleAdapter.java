package com.jdhui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jdhui.R;
import com.jdhui.bean.ResultCycleIndexBean;
import com.jdhui.mould.types.MouldList;
import com.jdhui.view.FixedSpeedScroller;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图 自定义的ViewPager
 */
public class CycleAdapter extends ViewPager {
    private static final String tag = " CycleAdapter ";
    private List<ImageView> imageViews;//存放imgView的集合
    private Context context;
    private List<View> dotList = new ArrayList<View>();
    private int lastPosition = 0;
    private ImageCycleViewListener mImageCycleViewListener;
    private LinearLayout mLinearLayout;
    private FixedSpeedScroller mScroller = null;
    private DisplayImageOptions options;
    private MyPagerAdapter mpAdapter;
    private boolean isCycle = false;
    private MouldList<ResultCycleIndexBean> images; //用于保存后台返回的图片的集合
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private int currentItem = 0;
    private int imgaeNum = 0;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    setCurrentItem(getCurrentItem() + 1);
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    currentItem = msg.arg1;
                    break;

                default:
                    break;
            }

            // setCurrentItem(currentItem+1);
        }

        ;
    };
    private ImageView imageView;

    /**
     * @param context 构造函数
     */
    public CycleAdapter(Context context, MouldList<ResultCycleIndexBean> images, DisplayImageOptions options) {
        super(context);
        this.context = context;
        this.images = images;
        this.options = options;

        controlViewPagerSpeed();
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int index;
                if (dotList.size() != 0) {
                    index = position % dotList.size();
                    dotList.get(index).setBackgroundResource(R.drawable.dot_focus);
                    dotList.get(lastPosition).setBackgroundResource(R.drawable.dot_normal);
                    lastPosition = index;
                } else {
                    index = position;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case 0:
                        if (isCycle) {
                            handler.sendEmptyMessageDelayed(0, 2000);
                        } else {
                            handler.removeMessages(0);
                        }
                        break;
                    case 1:
                        handler.removeMessages(0);
                        break;
                    case 2:
                        handler.removeMessages(0);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * @param isCycle true 循环 false 静止
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void getNetImage() {
        int ids[] = {R.drawable.banner_one, R.drawable.banner_two, R.drawable.banner_three, R.drawable.banner_four};

        imageViews = new ArrayList<ImageView>();
        /*
         * for(int i=0;i<ids.length;i++){ imageView = new ImageView(context);
		 * imageView.setBackgroundResource(ids[i]);
		 * 
		 * imageViews.add(imageView); }
		 */
        if (images.size() == 0) {
            imgaeNum = ids.length;
        } else {
            imgaeNum = images.size();
        }

        if (images.size() < 3) {
            for (int k = 0; k < 4; k++) {
                for (int i = 0; i < images.size(); i++) {
                    imageView = new ImageView(context);
                    // imageView.setBackground(new
                    // BitmapDrawable(images.get(i).getBitmap()));
                    imageViews.add(imageView);
                }
            }
        }

        if (imageViews.size() == 0) {
            for (int i = 0; i < ids.length; i++) {
                imageView = new ImageView(context);
                imageView.setBackgroundResource(ids[i]);
                imageViews.add(imageView);
            }
        }
    }

    /**
     * @param listener 点击事件的回调
     */

    public void setOnImageListener(ImageCycleViewListener listener) {
        this.mImageCycleViewListener = listener;
        initDot();
    }

    /**
     * @param mLinearLayout 设置轮播图的载体为线性布局
     */
    public void setNetAndLinearLayoutMethod(LinearLayout mLinearLayout) {
        this.mLinearLayout = mLinearLayout;
        getNetImage();
    }

    /**
     * 开启轮播图的方法
     */
    public void startRoll() {
        mpAdapter = new MyPagerAdapter();
        this.setAdapter(mpAdapter);
        if (imageViews.size() != 0) {
            currentItem = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
//			currentItem = 502;
        } else {
            currentItem = Integer.MAX_VALUE / 2;
//			currentItem = 250;
        }
        this.setCurrentItem(currentItem);
        if (dotList.size() > 0) {
            dotList.get(0).setBackgroundResource(R.drawable.dot_focus);
        }
        if (isCycle) {
            handler.sendEmptyMessageDelayed(0, 3000);
        }

    }

    /**
     * viewpager的水平滑动时间
     */
    private void controlViewPagerSpeed() {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller"); // 反射
            // mScroller为v4包下面的一个类
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            mScroller.setmDuration(500); // 500ms 数值越大时间越长
            mField.set(CycleAdapter.this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制轮播图的点
     */
    private void initDot() {

        dotList.clear();
        for (int i = 0; i < imgaeNum; i++) {

//			Toast.makeText(context,"imgaeNum=="+imgaeNum,Toast.LENGTH_SHORT).show();

            View view = new View(context);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.dot_focus);
            } else {
                view.setBackgroundResource(R.drawable.dot_normal);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMargins(8, 0, 8, 0);
            mLinearLayout.addView(view, layoutParams);
            dotList.add(view);
        }

    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (mpAdapter != null) mpAdapter.notifyDataSetChanged();
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

//		public Object instantiateItem(View container, int position) {
//
//			ImageView imageView = new ImageView(container.getContext());
//			imageView.setBackgroundResource(imgIdArray[position
//					% mImageViews.length]);
//			((ViewPager) container).addView(imageView, 0);
//			return imageView;// mImageViews[position % mImageViews.length];
//
//		}

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView iv;
            if (position < 0) {
                position = imageViews.size() + position;
            }
            final int postItem = position;
            if (images.size() > 0) {
                iv = imageViews.get(position % imageViews.size());

                // Log.e(tag,
                // "instantiateItem="+images.get(position).getPicture());
                // Log.e(tag, "instantiateItem="+imageViews.get(position));

                imageLoader.displayImage(images.get(position % images.size()).getPicture(), iv, options, new SimpleImageLoadingListener() {

                    public void onLoadingStarted(String imageUri, View view) {
                        // Message msg = new Message();
                        // msg.what = 1;
                        // myHandler.sendMessage(msg);
                    }

                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        String message = null;

                        switch (failReason.getType()) {
                            case IO_ERROR:
                                message = "Input/Output error";
                                break;
                            case DECODING_ERROR:
                                message = "Image can't be decoded";
                                break;
                            case NETWORK_DENIED:
                                message = "Downloads are denied";
                                break;
                            case OUT_OF_MEMORY:
                                message = "Out Of Memory error";
                                break;
                            case UNKNOWN:
                                message = "Unknown error";
                                break;
                        }
                        // Toast.makeText(context, message,
                        // Toast.LENGTH_SHORT)
                        // .show();

                    }

                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // Log.e(tag, "imageUri=="+imageUri);
                    }
                });

            } else {
                if (imageViews.size() > 0) {
                    iv = imageViews.get(position % imageViews.size());
                } else {
                    iv = imageViews.get(position);
                }
            }
            if (mImageCycleViewListener != null) {
                iv.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mImageCycleViewListener.onImageClick(postItem % imageViews.size(), iv);
                    }
                });
            }
            ViewGroup parent = (ViewGroup) iv.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            if (imageViews.size() > 0) {
                if (position < 0) {
                    container.removeView((View) imageViews.get(0));
                } else {
                    container.removeView((View) imageViews.get((position) % imageViews.size()));
                }
            } else {
                container.removeView((View) imageViews.get(position));
            }
        }
    }

    /**
     * 轮播图点击事件的回调接口
     */
    public interface ImageCycleViewListener {

        void onImageClick(int postion, View imageView);
    }

}
