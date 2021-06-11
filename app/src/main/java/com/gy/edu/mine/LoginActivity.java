package com.gy.edu.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gy.edu.MainActivity;
import com.gy.edu.R;
import com.gy.edu.base.BaseActivity;
import com.gy.edu.util.AnimUtil;
import com.gy.edu.util.LogUtil;
import com.gy.edu.util.SharedPrefUtil;

/**
 * 作者：gy on 16/12/21
 * 说明：
 */
public class LoginActivity extends BaseActivity {

    private EditText etName,etPwd;
    private Button btnLogin;
    private LinearLayout llRegister;
    private TextView tvRegister;

    @Override
    protected void initView() {
        setContentViewRes(R.layout.activity_login);
        hidenTop();
        etName = (EditText) findViewById(R.id.activity_login_ed_name);
        etPwd = (EditText) findViewById(R.id.activity_login_ed_pwd);
        btnLogin = (Button) findViewById(R.id.activity_login_bt_login);
        llRegister = (LinearLayout) findViewById(R.id.activity_login_ll_register);
        tvRegister = (TextView) findViewById(R.id.activity_login_tv_register);

    }

    @Override
    protected void initData() {
        tvRegister.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    @Override
    protected void setListener() {
        super.setListener();
        btnLogin.setOnClickListener(this);
        llRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()){
            case R.id.activity_login_bt_login:
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    showToast("请输入您的账号");
                } else if(TextUtils.isEmpty(pwd)){
                    showToast("请输入密码");
                } else {
                    SharedPrefUtil.put("login",true);
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    AnimUtil.pushLeftInAndOut(this);
                    finish();
                }
                break;
            case R.id.activity_login_ll_register:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                AnimUtil.pushLeftInAndOut(this);
                break;
        }
    }
}
