package com.gy.edu;

import android.app.Application;
import android.content.SharedPreferences;

import com.gy.edu.util.SharedPrefUtil;

/**
 * 作者：gy on 16/12/22
 * 说明：
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefUtil.init(getApplicationContext());
    }
}
