package com.gy.edu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

public class RoundViewPager extends ViewPager {

	private Path mPath;
	private int radio = 0;// 20

	public RoundViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RoundViewPager(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPath = new Path();
		radio = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				10, getResources().getDisplayMetrics());
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		int scrollX = getScrollX();
		int scrollY = getScrollY();
		mPath.reset();
		mPath.addRoundRect(new RectF(getScrollX(), 0,
				getScrollX() + getWidth(), getHeight()), radio, radio,
				Direction.CW);
		// canvas.clipPath(mPath);

		super.dispatchDraw(canvas);
	}
	
	 float curX = 0f;  
     float downX = 0f;  
     OnSingleTouchListener onSingleTouchListener; 

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		curX = ev.getX();
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			downX = curX;
		}
		int curIndex = getCurrentItem();
		if (curIndex == 0) {
			if (downX <= curX) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
		} else if (curIndex == getAdapter().getCount() - 1) {
			if (downX >= curX) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
		} else {
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		return super.onTouchEvent(ev);
	}

	public void onSingleTouch() {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch();
		}
	}

	public interface OnSingleTouchListener {
		public void onSingleTouch();
	}

	public void setOnSingleTouchListner(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}
}
