package com.gy.edu.mine;

import android.graphics.Paint;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gy.edu.R;
import com.gy.edu.base.BaseActivity;

/**
 * 作者：gy on 16/12/21
 * 说明：
 */
public class RegisterActivity extends BaseActivity {

    private TextView tvTitlePhone,tvTitleEmail,tvCode,tvAgreement;
    private EditText etAcount,etName,etPwd,etCode;
    private ImageView imgCb;
    private Button btnRegister;
    private int check = 0,agreement = 0;

    @Override
    protected void initView() {
        setContentViewRes(R.layout.activity_register);
        hidenTop();
        tvTitlePhone = (TextView) findViewById(R.id.register_tv_title_phone);
        tvTitleEmail = (TextView) findViewById(R.id.register_tv_title_email);
        tvCode = (TextView) findViewById(R.id.register_tv_code);
        tvAgreement = (TextView) findViewById(R.id.register_tv_xieyi);

        etAcount = (EditText) findViewById(R.id.register_et_acount);
        etName = (EditText) findViewById(R.id.register_et_name);
        etPwd = (EditText) findViewById(R.id.register_et_pwd);
        etCode = (EditText) findViewById(R.id.register_et_code);

        imgCb = (ImageView) findViewById(R.id.register_img_cb);

        btnRegister = (Button) findViewById(R.id.register_bt_register);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        tvTitlePhone.setOnClickListener(this);
        tvTitleEmail.setOnClickListener(this);
        imgCb.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected void fillData() {
        super.fillData();
        tvAgreement.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.register_tv_title_phone:
                check = 0;
                etAcount.setInputType(InputType.TYPE_CLASS_PHONE);
                etAcount.setBackgroundResource(R.drawable.register_input_phone);
                etAcount.setHint("请输入您的手机号码");
                etCode.setHint("短信验证码");
                tvCode.setText("获取短信验证码");
                tvTitlePhone.setBackgroundResource(R.mipmap.register_left_tap);
                tvTitleEmail.setBackgroundResource(R.mipmap.register_right_no_tap);
                tvTitlePhone.setTextColor(getResources().getColor(R.color.title_bg));
                tvTitleEmail.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.register_tv_title_email:
                check = 1;
                etAcount.setInputType(InputType.TYPE_CLASS_TEXT);
                etAcount.setBackgroundResource(R.drawable.register_input_email);
                etAcount.setHint("请输入您常用的邮箱地址");
                etCode.setHint("邮箱验证码");
                tvCode.setText("获取邮箱验证码");
                tvTitlePhone.setBackgroundResource(R.mipmap.register_left_no_tap);
                tvTitleEmail.setBackgroundResource(R.mipmap.register_right_tap);
                tvTitlePhone.setTextColor(getResources().getColor(R.color.white));
                tvTitleEmail.setTextColor(getResources().getColor(R.color.title_bg));
                break;
            case R.id.register_img_cb:
                agreement++;
                if(agreement % 2 == 1){
                    imgCb.setImageResource(R.mipmap.register_check_button_press);
                } else {
                    imgCb.setImageResource(R.mipmap.register_check_button_default);
                }
                break;
            case R.id.register_bt_register:
                String acount = etAcount.getText().toString().trim();
                String name = etName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                if(TextUtils.isEmpty(acount)){
                    if(check == 0){
                        showToast("请输入您的手机号码");
                    } else {
                        showToast("请输入您常用的邮箱地址");
                    }
                } else if(TextUtils.isEmpty(name)){
                    showToast("请输入您的姓名");
                } else if(TextUtils.isEmpty(pwd)){
                    showToast("请输入密码");
                } else if(TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                } else {
                    showToast("注册成功");
                    finish();
                }
                break;
        }
    }
}
