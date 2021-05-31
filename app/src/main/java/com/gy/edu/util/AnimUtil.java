package com.gy.edu.util;

import android.app.Activity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import com.gy.edu.R;


/**
 * Created by 高岳 on 2016/7/5.
 * Describe:activity切换动画
 */
public class AnimUtil {
    public static int in, out;

    public static void pushLeftInAndOut(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    public static void pushdownInAndOut(Activity activity) {
        activity.overridePendingTransition(R.anim.push_down_in,
                R.anim.push_down_out);
    }

    public static void pushRightInAndOut(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    public static void setInAndOut(int in, int out) {
        AnimUtil.in = in;
        AnimUtil.out = out;
    }

    public static void clear() {
        AnimUtil.in = 0;
        AnimUtil.out = 0;
    }

    public static LayoutAnimationController getLayoutAnimationController() {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 50.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        return controller;
    }
}
