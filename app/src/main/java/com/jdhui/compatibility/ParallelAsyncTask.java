package com.jdhui.compatibility;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;


public abstract class ParallelAsyncTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> {
//    private final static boolean DEBUG = SystemInfo.DEBUG;
    @SuppressLint("NewApi")
    public final ParallelAsyncTask<Params, Progress, Result> parallelExecute(Params... params) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
                // single,has no pool executor, not supported
                return null;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // single by default, by has pool executor
                return (ParallelAsyncTask<Params, Progress, Result>) super.executeOnExecutor(
                        THREAD_POOL_EXECUTOR, params);
            } else {
                // parallel by default
                return (ParallelAsyncTask<Params, Progress, Result>) super.execute(params);
            }
        } catch (Throwable e) {
//			if (DEBUG) {
//				Log.d("ParallelAsyncTask",
//						"ParallelAsyncTask executing rejected, will not crash.");
//			}
            e.printStackTrace();
            return this;
        }
    }

}
