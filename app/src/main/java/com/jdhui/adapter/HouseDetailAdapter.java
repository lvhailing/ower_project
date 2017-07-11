package com.jdhui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.jdhui.uitls.ImageLoaderManager.options;

// 房源详情页轮播图Adapter
public class HouseDetailAdapter extends PagerAdapter {
    private final Context context;
    private List<String> urls = null;
    private ImageViewListener mImageViewListener;



    public HouseDetailAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView iv = new ImageView(context);
        String uri = urls.get(position);
        ImageLoader.getInstance().displayImage(uri, iv, options);

        if (mImageViewListener != null) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageViewListener.onImageClick(position);
                }
            });
        }


        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(iv);
        return iv;
    }

    /**
     * @param listener 点击事件的回调
     */

    public void setOnImageListener(ImageViewListener listener) {
        this.mImageViewListener = listener;
    }

    /**
     * 轮播图点击事件的回调接口
     */
    public interface ImageViewListener {

        void onImageClick(int postion);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
