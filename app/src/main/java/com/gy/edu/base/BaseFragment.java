package com.gy.edu.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gy.edu.R;
import com.gy.edu.view.ErrorView;

import java.util.Calendar;

/**
 * Created by liguojun on 2016/7/1.
 * Description 所有fragment的基类
 *
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected Context mContext;
    private Toast toast;
    public View _rootView;
    protected Dialog loadingDialog = null;
    private FrameLayout _dataLayout;
    private FrameLayout _floatLayout;
    private View _error, _loading;
    protected ImageView _baseBack;
    protected TextView _baseTitle, _baseRight_text, _loadMsg,_baseLeft_text;//标题栏控件
    protected FrameLayout _baseRight;
    protected RelativeLayout _rlTitle;
    protected ErrorView _errorView;//错误提示view
    protected LinearLayout _llError;
    protected ImageView _imgRight,_imgRightTwo;
    public Gson gson = new Gson();
    public boolean isLoading = false;
    public static final int MIN_CLICK_DELAY_TIME = 800;//两次点击间隔
    private long lastClickTime = 0;//点击时间

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(_rootView == null){
            initBaseView();
            setContentViewRes(setView(inflater));
            initView();
            initData();
            setListener();
            fillData();
        }
        return _rootView;
    }

    public View getRootView(){
        return _rootView;
    }

    /**
     * 初始化view
     */
    private void initBaseView() {
        _rootView = View.inflate(mContext, R.layout.activity_base,null);
        _errorView = new ErrorView(mContext);
        _llError = (LinearLayout) _errorView.findViewById(R.id.activity_error_ll_center);
        _rlTitle = (RelativeLayout) _rootView.findViewById(R.id._rl_title);
        _baseBack = (ImageView) _rootView.findViewById(R.id._back);
        _baseRight = (FrameLayout) _rootView.findViewById(R.id._right);
        _imgRight = (ImageView) _rootView.findViewById(R.id.icon_right);
        _imgRightTwo = (ImageView) _rootView.findViewById(R.id.icon_right_two);
        _baseTitle = (TextView) _rootView.findViewById(R.id._title);
        _baseRight_text = (TextView) _rootView.findViewById(R.id._baseRight_text);
        _baseLeft_text = (TextView) _rootView.findViewById(R.id._baseLeft_text);
        _baseBack.setOnClickListener(this);
        _baseRight.setOnClickListener(this);
        _llError.setOnClickListener(this);

        _dataLayout = (FrameLayout) _rootView.findViewById(R.id._data_layout);
        _floatLayout = (FrameLayout) _rootView.findViewById(R.id._float_layout);
        _error = _rootView.findViewById(R.id._error_layout);
        _loading = View.inflate(mContext, R.layout.view_loading_layout, null);
        _loadMsg = (TextView) _loading.findViewById(R.id.view_loading_tv_msg);
        _floatLayout.addView(_errorView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    protected abstract View setView(LayoutInflater inflater);

    protected abstract void initView();
    protected abstract void initData();

    /**
     * 设置一些view的监听
     */
    protected void setListener() {}

    /**
     * 填充页面数据
     */
    protected void fillData() {}

    /**
     * 隐藏标题栏
     */
    protected void hidenTop() {
        if (_rlTitle != null) {
            _rlTitle.setVisibility(View.GONE);
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
     * toast string消息
     */
    protected void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
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
            toast = Toast.makeText(mContext, resId,
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * 显示loading框
     */
    public void showLoadDialog() {
        closeLoadDialog();
        showLoadDialog("");
    }

    public void showLoadDialog(int flag) {
        showLoadDialog("", flag);
    }

    public void showLoadDialog(String msg) {
        showLoadDialog(msg, 0);
    }

    public void showLoadDialog(String msg, int flag) {
        if (TextUtils.isEmpty(msg)) {
            msg = "加载中...";
        }
        _loadMsg.setText(msg);
        if (flag == 0) {
            _error.setVisibility(View.GONE);
        } else if (flag == 1) {
            _error.setVisibility(View.VISIBLE);
        }
        if(loadingDialog  == null){
//            if(flag == 0){
                loadingDialog = new Dialog(mContext, R.style.dialog_loading);
//            } else if(flag == 1){
//                loadingDialog = new Dialog(mContext,R.style.dialog_loading_default);
//            }
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.setContentView(_loading, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
    }

    /**
     * 加载dialog是否显示
     * @return
     */
    public boolean isShowDialog(){
        if(loadingDialog  == null){
            return loadingDialog.isShowing();
        }
        return false;
    }

    /**
     * 关闭loading框
     */
    public void closeLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            _error.setVisibility(View.GONE);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
