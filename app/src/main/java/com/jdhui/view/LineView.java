package com.jdhui.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jdhui.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class LineView extends View {

	private int XPoint = 30;
	private int YPoint = 165;
	private int XScale = 40;
	private int YScale = 20;
	private int XLength = 240;
	private int YLength = 160;
	private int MaxDataSize = XLength / XScale;
	private List<Integer> data = new ArrayList<Integer>();

	private String[] YLabel = new String[YLength / YScale];
	private String[] XLabel;

	private Map<Integer, Float> lhmap = new LinkedHashMap<Integer, Float>();
	private Map<String, Float> map = new LinkedHashMap<String, Float>();
	Context context;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x1234) {
				LineView.this.invalidate();
			}
		};
	};

	public void setData(Map<String, Float> map) {
		this.map = map;
		XLabel = new String[map.size()];
		int i = 0;
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			float value = Float.valueOf((String) entry.getValue());
			lhmap.put(i, value);
			String key = (String) entry.getKey();
			XLabel[i] = key.substring(5, key.length());
			i++;
		}
		
		float j = 0;
		for (i = 0; i < YLabel.length; i++, j += 0.5) {
			YLabel[i] = j + "";
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {

					if (data.size() >= MaxDataSize) {
						data.remove(0);
					}
					data.add(i);
					handler.sendEmptyMessage(0x1234);
				}
			}
		}).start();
	}
	
	public LineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		//[{"dayWorth":1.0141,"worthDate":"2015-06-16"},
		//{"dayWorth":1.0116,"worthDate":"2015-06-17"},
		//{"dayWorth":1.0132,"worthDate":"2015-06-18"},
		//{"dayWorth":1.0105,"worthDate":"2015-06-19"},
		//{"dayWorth":1.0105,"worthDate":"2015-06-21"},
		//{"dayWorth":1.0105,"worthDate":"2015-06-30"}]
		
//		map.put("2015-06-16", 1.0141f);
//		map.put("2015-06-17", 1.0116f);
//		map.put("2015-06-18", 1.0132f);
//		map.put("2015-06-19", 1.0105f);
//		map.put("2015-06-21", 1.0105f);
//		map.put("2015-06-30", 1.0105f);
		
//		lhmap.put(0, 3.0141f);
//		lhmap.put(1, 1.0116f);

		
//		float j = 0;
//		for (int i = 0; i < XLabel.length; i++, j += 0.5) {
//			XLabel[i] = j + "";
//		}
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(Color.GRAY);

		// 画Y轴
		canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);
		// Y轴箭头
		canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint - YLength
				+ 6, paint); // 箭头
		canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint - YLength
				+ 6, paint);
		// 添加Y轴刻度和文字
		for (int i = 0; i < YLabel.length; i++) {
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
					* YScale, paint); // 刻度
			canvas.drawText(YLabel[i], XPoint - 25, YPoint - i * YScale, paint); //文字
		}
		// 添加X轴刻度和文字
		for (int i = 0; i < XLabel.length; i ++) {
			canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale,
					YPoint - 5, paint); //刻度
			canvas.drawText(XLabel[i], XPoint + i * XScale, YPoint + 20,
					paint); // 文字
		}
		// 画X轴
		canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint);
		System.out.println("Data.size = " + data.size());

//		if (data.size() > 1) {
//			for (int i = 1; i < data.size(); i++) {
//				canvas.drawLine(XPoint + (i - 1) * XScale,
//						YPoint - data.get(i - 1) * YScale, XPoint + i * XScale,
//						YPoint - data.get(i) * YScale, paint);
//				canvas.drawCircle(XPoint + (i - 1) * XScale,
//						YPoint - data.get(i - 1) * YScale, 5, paint);
//			}
//		}
		paint.setColor(context.getResources().getColor(R.color.orange));
		if(lhmap.size()>1){
			Iterator iter = lhmap.entrySet().iterator();
			int key_pre = 0;
			float value_pre = 0;
			if(iter.hasNext()){
				Entry entry_pre = (Entry) iter.next();
				key_pre = (Integer) entry_pre.getKey();
				value_pre = (Float) entry_pre.getValue();
			}
			while(iter.hasNext()){
				Entry entry = (Entry) iter.next();
				int key = (Integer) entry.getKey();
				float value = (Float) entry.getValue();
				float r = (value-value_pre)/(key-key_pre);
				System.out.println("key == "+r);
				float b = (float) Math.sqrt(5*5/(r*r+1));		//x轴
				float a = b*r;						//y轴
				if(value-value_pre>0){
					System.out.println(">>>>>");
					canvas.drawLine(XPoint + key_pre*XScale+b, YPoint-value_pre*YScale*2-a, XPoint + key*XScale-b, YPoint-value*YScale*2+a, paint);
				}else if(value-value_pre<0){
					System.out.println("<<<<<<<<");
					canvas.drawLine(XPoint + key_pre*XScale+b, YPoint-value_pre*YScale*2-a, XPoint + key*XScale-b, YPoint-value*YScale*2+a, paint);
				}else{
					System.out.println("=======");
					canvas.drawLine(XPoint + key_pre*XScale+5, YPoint-value_pre*YScale*2, XPoint + key*XScale-5, YPoint-value*YScale*2, paint);
				}
				canvas.drawCircle(XPoint + key*XScale, YPoint-value*YScale*2, 5, paint);
				key_pre = key;
				value_pre = value;
			}
		}
	}

}
