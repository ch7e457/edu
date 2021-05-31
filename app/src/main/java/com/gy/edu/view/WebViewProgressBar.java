package com.gy.edu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class WebViewProgressBar extends View {

	private Paint paint;
	private int strokeWidth = 5;
	private int width;
	private int height;
	private int drawStartX;
	private int drawEndX;
	private int drawStartY;
	private int drawEndY;
	private float progress = 30f;
	private float totleLength = 100f;
	
	float density;
	
	public WebViewProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		density = dm.density;
		
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(strokeWidth * density);
		paint.setAlpha(100);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = this.getMeasuredWidth();
		height = this.getMeasuredHeight();
		drawStartX = 0;
		drawEndX = width;
		drawStartY = height/2;
		drawEndY = height/2;
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//paint.setColor(Color.GRAY);
		paint.setColor(0x90000000);
		//paint.setColor(0xFFFFE0);
		canvas.drawLine(drawStartX, drawStartY, progress/totleLength*width, drawEndY, paint);
		paint.setColor(0xff888888);
		canvas.drawLine(progress/totleLength*width, drawStartY, drawEndX, drawEndY, paint);
		//paint.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	public void setProgress(int progress){
		if(progress < 0){
			progress = 0;
		}
		this.progress = progress;
		invalidate();
	}
}
