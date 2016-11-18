package com.jdhui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jdhui.R;

public class ChartView extends View {
	public int XPoint = 100; // 原点的X坐标
	public int YPoint = 400; // 原点的Y坐标
	public int XScale = 55; // X的刻度长度
	public int YScale = 40; // Y的刻度长度
	public int XLength = 380; // X轴的长度
	public int YLength = 240; // Y轴的长度
	public String[] XLabel; // X的刻度
	public String[] YLabel; // Y的刻度
	public String[] Data; // 数据
	public String Title; // 显示的标题
	private int textSize = 20;

	private Paint paint, paint1, paintCircle, paintCircle2;

	public ChartView(Context context) {
		this(context, null);
	}

	public ChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setAntiAlias(true);// 去锯齿
		paint.setColor(getResources().getColor(R.color.gray_d));// 颜色
		paint.setTextSize(textSize); // 设置轴文字大小

		paint1 = new Paint();// 折线的画笔
		paint1.setStyle(Paint.Style.STROKE);
		paint1.setAntiAlias(true);// 去锯齿
		paint1.setStrokeWidth(5);
		paint1.setColor(getResources().getColor(R.color.orange));

		paintCircle = new Paint();// 圆
		paintCircle.setAntiAlias(true);
		paintCircle.setStyle(Paint.Style.STROKE);
		paintCircle.setStrokeWidth(2);
		paintCircle.setColor(getResources().getColor(R.color.orange));

		paintCircle2 = new Paint();
		paintCircle2.setAntiAlias(true);
		paintCircle2.setStrokeWidth(2);
		paintCircle2.setColor(getResources().getColor(R.color.white));
	}

	public void SetInfo(String[] XLabels, String[] YLabels, String[] AllData,
			String strTitle) {
		XLabel = XLabels;
		YLabel = YLabels;
		Data = AllData;
		Title = strTitle;
	}

	public void setXYStart(int x, int y) {
		XPoint = x;
		YPoint = y;
	}

	public void setTextSize(int size) {
		textSize = size;
	}

	public void setXYLength(int x, int y) {
		XLength = x;
		YLength = y;
	}

	public void setScaleLength() {
		if (XLabel.length > 0 && YLabel.length > 0) {
			XScale = XLength / XLabel.length;
			YScale = YLength / YLabel.length;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);// 重写onDraw方法

		canvas.drawColor(Color.WHITE);// 设置背景颜色

		// 设置Y轴
		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // 轴线
		for (int i = 0; i * YScale < YLength; i++) {
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
					* YScale, paint); // 刻度
			try {
				canvas.drawText(YLabel[i], XPoint - 50,
						YPoint - i * YScale + 5, paint); // 文字
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
				+ 6, paint); // 箭头
		canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
				+ 6, paint);
		// 设置X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // 轴线
		for (int i = 0; i * XScale < XLength; i++) {
			canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,
					YPoint - 5, paint); // 刻度
			try {
				canvas.drawText(XLabel[i], XPoint + i * XScale - 10,
						YPoint + 20, paint); // 文字
				// 数据值
				if (i > 0 && YCoord(Data[i - 1]) != -999
						&& YCoord(Data[i]) != -999) { // 保证有效数据
					canvas.drawLine(XPoint + (i - 1) * XScale,
							YCoord(Data[i - 1]), XPoint + i * XScale,
							YCoord(Data[i]), paint1);
					canvas.drawCircle(XPoint + (i - 1) * XScale,
							YCoord(Data[i - 1]), 10, paintCircle2);
					canvas.drawCircle(XPoint + (i - 1) * XScale,
							YCoord(Data[i - 1]), 12, paintCircle);
					canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 10,
							paintCircle2);
					canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), 12,
							paintCircle);
				}
			} catch (Exception e) {
			}
		}
		// 在这里需要再加一个点和一个文本

		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint + 3, paint);
		canvas.drawLine(XPoint + XLength, YPoint, XPoint + XLength - 6,
				YPoint - 3, paint); // 箭头
		paint.setTextSize(16);
		// canvas.drawText(Title, 150, 50, paint);
	}

	private int YCoord(String y0) // 计算绘制时的Y坐标，无数据时返回-999
	{
		int y;
		try {
			y = Integer.parseInt(y0);
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			return YPoint - y * YScale / 5 - 2 * YScale;
		} catch (Exception e) {
		}
		return y;
	}
}
