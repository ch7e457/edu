package com.handmark.pulltorefresh.library.extras;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author Administrator on 2016/4/1.
 */
public class CustomViewPager extends ViewPager {
    private boolean enableScroll = false;
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isEnableScroll() {
        return enableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isEnableScroll()){
            super.scrollTo(x, y);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isEnableScroll()){
            return super.onInterceptHoverEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnableScroll()){
            return super.onInterceptHoverEvent(ev);
        } else {
            return false;
        }
    }
}
