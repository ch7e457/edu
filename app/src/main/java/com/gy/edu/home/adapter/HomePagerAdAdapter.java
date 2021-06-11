package com.gy.edu.home.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.gy.edu.R;
import com.gy.edu.util.ListViewItemListener;

/** ��ҳviewpager adapter*/
public class HomePagerAdAdapter extends PagerAdapter {

	private Context context;
	private ArrayList<String> list;
	private List<ImageView> mViewList;
	private int flag = 1;
	private ListViewItemListener listener;

	public HomePagerAdAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}
	
	public void setFlag(int flag){
		this.flag = flag;
	}
	
	public void setListener(ListViewItemListener listener){
		this.listener = listener;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		/*
		 * if (list.size() > 0) { if(null != mViewList.get(position))
		 * container.removeView(mViewList.get(position)); }
		 */
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View view = View.inflate(context, R.layout.activity_main_ad_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.ad_image);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.doPassActionListener(null, 0,position, null);
				}
			}
		});

		container.addView(view, 0);
		return view;
	}

}
