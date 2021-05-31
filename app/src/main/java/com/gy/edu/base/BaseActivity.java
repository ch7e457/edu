package com.gy.edu.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gy.edu.R;
import com.gy.edu.util.AnimUtil;
import com.gy.edu.view.ErrorView;

import java.util.Calendar;

/**
 * Created by liguojun on 2016/7/1.
 * Description activity基类 所有activity继承
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, DialogInterface.OnCancelListener {
    protected Context mContext;
    private static Handler handler;
    private FrameLayout _dataLayout;
    private FrameLayout _floatLayout;
    private View _error, _loading;
    protected View _baseBack;
    protected TextView _baseTitle, _baseRight_text, _loadMsg;//标题栏控件
    protected FrameLayout _baseRight;
    protected RelativeLayout _rlTitle;
    protected ErrorView _errorView;//错误提示view
    protected LinearLayout _llError;
    protected ImageView _imgRight, _imgRightTwo, _imgHead, _imgCloud;
    private Toast toast;
    protected ProgressDialog progressDialog = null;
    protected Dialog loadingDialog = null;
    private boolean isFinish = false;//当前界面是否关闭
    public Gson gson = new Gson();
    public boolean isLoading = false;
    public static final int MIN_CLICK_DELAY_TIME = 800;//两次点击间隔
    private long lastClickTime = 0;//点击时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mContext = this;
        isFinish = false;
        setContentView(R.layout.activity_base);
        initBaseView();
        initView();
        initData();
        setListener();
        fillData();
    }

    /**
     * 初始化view
     */
    private void initBaseView() {
        _errorView = new ErrorView(this);
        _llError = (LinearLayout) _errorView.findViewById(R.id.activity_error_ll_center);
        _rlTitle = (RelativeLayout) findViewById(R.id._rl_title);
        _baseBack = findViewById(R.id._back);
        _baseRight = (FrameLayout) findViewById(R.id._right);
        _imgRight = (ImageView) findViewById(R.id.icon_right);
        _imgRightTwo = (ImageView) findViewById(R.id.icon_right_two);
        _baseTitle = (TextView) findViewById(R.id._title);
        _baseRight_text = (TextView) findViewById(R.id._baseRight_text);
        _baseBack.setOnClickListener(this);
        _baseRight.setOnClickListener(this);
        _llError.setOnClickListener(this);

        _dataLayout = (FrameLayout) findViewById(R.id._data_layout);
        _floatLayout = (FrameLayout) findViewById(R.id._float_layout);
        _error = findViewById(R.id._error_layout);
        _loading = View.inflate(this, R.layout.view_loading_layout, null);
        _loadMsg = (TextView) _loading.findViewById(R.id.view_loading_tv_msg);
        _floatLayout.addView(_errorView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 抽象方法子类必须实现
     */
    protected abstract void initView();

    /**
     * 做一些初始化操作
     */
    protected abstract void initData();

    /**
     * 设置一些view的监听
     */
    protected void setListener() {
    }

    /**
     * 填充页面数据
     */
    protected void fillData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 填充页面中间部分内容
     *
     * @param layoutRes
     */
    protected void setContentViewRes(int layoutRes) {
        if (_dataLayout != null) {
            _dataLayout.removeAllViews();
            View curContent = View.inflate(this, layoutRes, null);
            _dataLayout.addView(curContent, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 填充页面中间部分内容
     *
     * @param contentView
     */
    protected void setContentViewRes(View contentView) {
        if (_dataLayout != null) {
            _dataLayout.removeAllViews();
            _dataLayout.addView(contentView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 隐藏标题栏
     */
    protected void hidenTop() {
        if (_rlTitle != null) {
            _rlTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     */
    protected void setTitleMsg(String title) {
        if (_baseTitle != null) {
            _baseTitle.setText(title);
        }
    }

    /**
     * 显示加载失败
     */
    protected void showErrorLayout() {
        if (_dataLayout != null && _floatLayout != null) {
            _dataLayout.setVisibility(View.INVISIBLE);
            _floatLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏加载失败  显示正常数据
     */
    protected void hidenErrorLayout() {
        if (_dataLayout != null && _floatLayout != null) {
            _dataLayout.setVisibility(View.VISIBLE);
            _floatLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示loading框
     */
    public void showLoadDialog() {
        showLoadDialog("");
    }

    /**
     * 显示loading框
     *
     * @param flag 默认0：正常网络请求加载    1：显示空白背景，一般用于首次进入界面请求
     */
    public void showLoadDialog(int flag) {
        showLoadDialog("", flag);
    }

    public void showLoadDialog(String msg) {
        showLoadDialog(msg, 0);
    }

    public void showLoadDialog(String msg, int flag) {
        if (loadingDialog != null && loadingDialog.isShowing())
            return;
        if (TextUtils.isEmpty(msg)) {
            msg = "加载中...";
        }

        _loadMsg.setText(msg);
        if (flag == 0) {
            _error.setVisibility(View.GONE);
        } else if (flag == 1) {
            _error.setVisibility(View.VISIBLE);
        }
        if (loadingDialog == null) {
            loadingDialog = new Dialog(this, R.style.dialog_loading);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.setContentView(_loading, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();

    }

    /**
     * 关闭loading框
     */
    public void closeLoadDialog() {
        if (!isFinish && loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            _error.setVisibility(View.GONE);
        }

    }

    /**
     * toast string消息
     */
    protected void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * toast string消息
     */
    protected void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), resId,
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;

        } else {
            return;
        }
        if (v.getId() == R.id._back) {
            finish();
            AnimUtil.pushRightInAndOut(this);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        //dialog取消监听
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

    protected Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimUtil.pushRightInAndOut(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
