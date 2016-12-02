package com.jdhui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;
import com.jdhui.bean.mybean.GeneticTestingDetail2B;

/**
 * 产品  产品预约弹框
 */
public class BookingDialog {
    private String name;
    private Dialog dialog;
    private Activity activity;
    private String money;
    private String remarks;
    private EditText et_money;
    private EditText et_remarks;

    public interface MyCallback {
        void onMyclick(Dialog ad, String money, String remarks);
    }

    public BookingDialog(Activity activity,String name) {
        this.activity = activity;
        this.name = name;
    }

    public Dialog subBookingDialog(final MyCallback callback) {
        LinearLayout productBookingLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_product_booking, null);
        TextView tv_insurance_name = (TextView) productBookingLayout.findViewById(R.id.tv_insurance_name);
         et_money = (EditText) productBookingLayout.findViewById(R.id.et_money);
         et_remarks = (EditText) productBookingLayout.findViewById(R.id.et_remarks);
        Button btn_submit = (Button) productBookingLayout.findViewById(R.id.btn_submit);
        tv_insurance_name.setText(name);


        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                money = et_money.getText().toString();
                remarks = et_remarks.getText().toString();
                callback.onMyclick(dialog, money, remarks);
            }
        });

        dialog = new Dialog(activity, R.style.date_picker_style);
        dialog.setContentView(productBookingLayout);
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
