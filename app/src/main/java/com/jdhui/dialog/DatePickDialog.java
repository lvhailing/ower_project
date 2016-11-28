package com.jdhui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhui.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickDialog implements OnDateChangedListener {
    private DatePicker datePicker;
    private TextView cancel;
    private TextView sure;
    private Dialog dialog;
    private String dateTime;
    private Activity activity;

    public interface MyCallback {
        void processTime(Dialog ad, String selectedTime);
    }

    public DatePickDialog(Activity activity) {
        this.activity = activity;
    }

    public Dialog setDateDialog(final MyCallback callback) {
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.date_picker_dialog, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.date_picker);
        cancel = (TextView) dateTimeLayout.findViewById(R.id.tv_cancel);
        sure = (TextView) dateTimeLayout.findViewById(R.id.tv_sure);

        //初始化日期
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

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

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                callback.processTime(dialog, dateTime);
            }
        });
        dialog.show();

        onDateChanged(datePicker, 0, 0, 0);

        return dialog;
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        dateTime = sdf.format(calendar.getTime());
    }

}
