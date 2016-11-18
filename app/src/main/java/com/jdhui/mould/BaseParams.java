package com.jdhui.mould;

import java.util.HashMap;

import android.content.Context;

import com.jdhui.mould.BaseRequester.OnRequestListener;
import com.jdhui.mould.types.IMouldType;

public class BaseParams {
    public String id;
    public Context context = null;
    public OnRequestListener requestListener;
    public int from, size;
    public String url;
    private HashMap<String, Object> mMap = new HashMap<String, Object>();
    public IMouldType result;

    public void sendResult() {
        context = null;
        if (requestListener != null) {
            requestListener.onRequestFinished(this);
        }
    }

    public BaseParams put(String key, Object value) {
        if (key != null) {
            mMap.put(key, value);  
        }
        return this;
    }

    public Object get(String key) {
        return mMap.get(key);
    }
    //    public OnRequestListener getRequestListener() {
    //        return mRequestListener;
    //    }
    //
    //    public void setRequestListener(OnRequestListener listener) {
    //        this.mRequestListener = listener;
    //    }
    //
    //    public Context getContext() {
    //        return mContext;
    //    }
    //
    //    public void setContext(Context context) {
    //        this.mContext = context;
    //    }

}
