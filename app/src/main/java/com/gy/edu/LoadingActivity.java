package com.gy.edu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gy.edu.base.BaseActivity;
import com.gy.edu.mine.LoginActivity;
import com.gy.edu.util.AnimUtil;
import com.gy.edu.util.SharedPrefUtil;

/**
 * 作者：gy on 16/12/21
 * 说明：
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        handler.sendEmptyMessageDelayed(0,3000);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean login = SharedPrefUtil.get("login",false);
            Intent intent;
            if(login){
                intent = new Intent(LoadingActivity.this,MainActivity.class);
            } else {
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            AnimUtil.pushLeftInAndOut(LoadingActivity.this);
            finish();
        }
    };
}
