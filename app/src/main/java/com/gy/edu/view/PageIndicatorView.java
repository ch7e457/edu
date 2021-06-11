package com.gy.edu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.gy.edu.R;

/**
 * 
 * 指示点（页码�?
 * 
 * @author zhenwei
 * 
 */

public class PageIndicatorView extends ImageView {
	public static final int STYLE_BITMAP = 0;
	public static final int STYLE_COLOR = 1;
	private Context context;
	private int num = 3;
	private int spacing = 10;
	private int curPosition;

	private Bitmap selected, normal;
	private int mMaxHeight;//两个点最大的高度
	private int selectedColor, defaultColor;

	private LayoutParams layoutParams;
	private int style = STYLE_BITMAP;

	public PageIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.PageIndicatorView);

		int selectedResourceId = array.getResourceId(
				R.styleable.PageIndicatorView_selectedResource, 0);
		int defaultResourceId = array.getResourceId(
				R.styleable.PageIndicatorView_defaultResource, 0);

		if (selectedResourceId == 0) {
			Log.e("PageIndicatorView",
					"please use PageIndicatorView_selectedResource set the bitmap");
			selectedColor = Color.GRAY;
			selectedResourceId = R.mipmap.dot_pink;
		}

		selected = BitmapFactory.decodeResource(getResources(),
				selectedResourceId);

		if (defaultResourceId == 0) {
			defaultColor = Color.WHITE;
			Log.e("PageIndicatorView",
					"please use PageIndicatorView_defaultResource set the bitmap");
			defaultResourceId = R.mipmap.dot_white;
		}

		normal = BitmapFactory
				.decodeResource(getResources(), defaultResourceId);
		
		mMaxHeight = Math.max(normal.getHeight(), selected.getHeight());
	}

	public void setStyle(int style) {
		this.style = style;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		switch (style) {
		case STYLE_BITMAP:
			for (int i = 0; i < num; i++) {
				if (i == curPosition) {
					canvas.drawBitmap(selected, i
							* (normal.getWidth() + spacing), 0, null);
				} else {
					canvas.drawBitmap(normal,
							i * (normal.getWidth() + spacing), 0, null);
				}
			}
			break;
		case STYLE_COLOR:
			/* 设置笔刷 */
			Paint paint = new Paint();

			paint.setAntiAlias(true);// 抗锯�?

			RectF oval = new RectF(0, 0, 1, normal.getHeight());
			for (int i = 0; i < num; i++) {
				if (i == curPosition) {
					oval.left = i * (normal.getWidth() + spacing);
					oval.right = oval.left + normal.getWidth();
					// canvas.drawBitmap(selected, i
					// * (normal.getWidth() + spacing), 0, null);
					paint.setColor(0xffffffff);
//					canvas.drawOval(oval, paint);
					canvas.drawRect(oval, paint);
				} else {
					oval.left = i * (normal.getWidth() + spacing);
					oval.right = oval.left + normal.getWidth();
					// canvas.drawBitmap(normal,
					// i * (normal.getWidth() + spacing), 0, null);
					paint.setColor(0xaa666666);
//					canvas.drawOval(oval, paint);
					canvas.drawRect(oval, paint);
				}
			}
			break;
		default:
			break;
		}

		super.onDraw(canvas);
	}

	/**
	 * 
	 * @param num
	 *            总数�?
	 * @param curPosition
	 *            当前的位�?
	 */
	public void setPropertise(int num, int curPosition) {
		this.num = num;
		this.curPosition = curPosition;
		// num*(selected.getWidth() + spacing);
		layoutParams = (LayoutParams) this.getLayoutParams();
		layoutParams.width = num * (normal.getWidth() + spacing);
		layoutParams.height = mMaxHeight;
		this.setLayoutParams(layoutParams);
		invalidate();
	}

	/**
	 * 
	 * @param curPosition
	 *            当前的位�?
	 */
	public void setCurrentPosition(int curPosition) {
		this.curPosition = curPosition;
		invalidate();
	}

	/**
	 * 
	 * @param num
	 *            总的数量
	 */
	public void setPropertise(int num) {
		this.num = num;
		layoutParams = (LayoutParams) this.getLayoutParams();
		layoutParams.width = num * (normal.getWidth() + spacing);
		layoutParams.height = mMaxHeight;
		this.setLayoutParams(layoutParams);
		invalidate();
	}
}
