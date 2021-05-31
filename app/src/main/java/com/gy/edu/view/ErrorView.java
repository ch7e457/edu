package com.gy.edu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gy.edu.R;


/**
 * Created by 高岳 on 2016/7/4.
 * Describe:错误界面
 */
public class ErrorView extends LinearLayout {

    private Context context;
    private LinearLayout llCenter;
    private ImageView img;//错误图片
    private TextView tvMsg;//错误提示信息
    private LayoutInflater inflater;

    public ErrorView(Context context) {
        this(context,null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.activity_net_error_layout,this);
        llCenter = (LinearLayout) findViewById(R.id.activity_error_ll_center);
        img = (ImageView) findViewById(R.id.activity_error_img);
        tvMsg = (TextView) findViewById(R.id.activity_error_tv_msg);
    }

    /** 设置提示信息*/
    public void setErrorMsg(String msg){
        tvMsg.setText(msg);
    }

    /** 设置提示信息*/
    public void setErrorMsg(int msgId){
        tvMsg.setText(context.getResources().getString(msgId));
    }

    /** 设置提示图片*/
    public void setErrorImg(int resId){
        img.setImageResource(resId);
    }

}
