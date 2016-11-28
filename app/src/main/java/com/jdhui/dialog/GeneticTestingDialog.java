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

public class GeneticTestingDialog {
    private GeneticTestingDetail2B detail2B;
    private Dialog dialog;
    private Activity activity;

    public interface MyCallback {
        void onMyclick(Dialog ad);
    }

    public GeneticTestingDialog(Activity activity, GeneticTestingDetail2B detail2B) {
        this.activity = activity;
        this.detail2B = detail2B;
    }

    public Dialog setDateDialog(final MyCallback callback) {
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_genetic_testing, null);
        TextView tv_name = (TextView) dateTimeLayout.findViewById(R.id.tv_name);
        TextView tv_items = (TextView) dateTimeLayout.findViewById(R.id.tv_items);
        TextView tv_unvip_price = (TextView) dateTimeLayout.findViewById(R.id.tv_unvip_price);
        TextView tv_vip_price = (TextView) dateTimeLayout.findViewById(R.id.tv_vip_price);
        TextView tv_balance_price = (TextView) dateTimeLayout.findViewById(R.id.tv_balance_price);
        TextView tv_submit = (TextView) dateTimeLayout.findViewById(R.id.tv_submit);

        tv_name.setText(detail2B.getName());
        tv_items.setText(detail2B.getItems());
        tv_unvip_price.setText("非会员价：" + detail2B.getUnvipPrice() + "元");
        tv_vip_price.setText("会员价：" + detail2B.getVipPrice() + "元");
        tv_balance_price.setText("结算价：" + detail2B.getBalancePrice() + "元");

        tv_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onMyclick(dialog);
            }
        });

        dialog = new Dialog(activity, R.style.date_picker_style);
        dialog.setContentView(dateTimeLayout);
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
