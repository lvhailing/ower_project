package com.jdhui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.GeneticTestingDetail2B;

/**
 * 基因检测  性别选择弹框
 */
public class SexDialog {
    private Dialog dialog;
    private Activity activity;
    private String selectedSex;

    public interface MyCallback {
        void onSelected(Dialog ad, String selectedSex);
    }

    public SexDialog(Activity activity) {
        this.activity = activity;
    }

    public Dialog setDialog(final MyCallback callback) {
        LinearLayout sexLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_sex, null);
        TextView tv_man = (TextView) sexLayout.findViewById(R.id.tv_man);
        TextView tv_woman = (TextView) sexLayout.findViewById(R.id.tv_woman);
        TextView tv_cancel = (TextView) sexLayout.findViewById(R.id.tv_cancel);


        tv_man.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onSelected(dialog,"男");
            }
        });
        tv_woman.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onSelected(dialog,"女");
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = new Dialog(activity, R.style.date_picker_style);
        dialog.setContentView(sexLayout);
        dialog.setCanceledOnTouchOutside(true);

        //设置dialog全屏宽
        Window window = dialog.getWindow();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        window.setAttributes(lp);
        //设置弹出动画及弹出位置
        window.setWindowAnimations(R.style.date_picker_anim);
        window.setGravity(Gravity.BOTTOM);

        dialog.show();

        return dialog;
    }

}
