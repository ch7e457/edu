package com.gy.edu.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gy.edu.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/12/14.
 */
public class DialogUtil {

    public static ProgressDialog progressDialog;
    private static int year;
    private static int month;
    private static int day;

    /**
     * 圆角对话框
     */
    public static void showSignDiolag(final Context context, final String message) {

//        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        if(context == null)
            return;
        Activity activity = (Activity) context;

        View view = View.inflate(context, R.layout.diolag_sign, null);
        final MyDialog alertDialog = new MyDialog(context, 0, 0, view, R.style.dialog_success);
        alertDialog.setCancelable(true);
        if(activity.isFinishing()){
            return;
        } else {
            alertDialog.show();
        }
        alertDialog.getWindow().setContentView(view);
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.getWindow().setWindowAnimations(R.style.dialog_style);
        TextView tv_message_diolag = (TextView) view.findViewById(R.id.tv_message_diolag);
        tv_message_diolag.setText(message);

        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        }, 1000);

    }

    /**
     * 弹出确认对话框
     */
    public static void showTextDiolag(final Context context, final String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        View view = View.inflate(context, R.layout.diolag_item_text, null);

        alertDialog.setCancelable(true);
        alertDialog.show();
        alertDialog.getWindow().setContentView(view);
        alertDialog.setCanceledOnTouchOutside(true);

        /**
         * 屏幕宽度
         */
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();

        /**
         * 设置大小
         */
        WindowManager.LayoutParams params =
                alertDialog.getWindow().getAttributes();
        params.width = (width / 5) * 4;
        alertDialog.getWindow().setAttributes(params);

        TextView tv_message_diolag = (TextView) view.findViewById(R.id.tv_message_diolag);
        tv_message_diolag.setText("" + message + "");

        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                if (alertDialog != null)
                    alertDialog.dismiss();
            }
        }, 3000);

    }

    public static void diolagShow(Context context) {
        // 显示Progress对话框
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        progressDialog.setMessage("正在加载中...");
        progressDialog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDialog.show();

    }

    public static void diolagDismiss() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    /**
     * 显示提示内容和确认按钮
     */
    public static void showDialog(Context context, String message, View.OnClickListener listener) {
        showDialog(context, "", message, "", "", 1, listener);
    }

    /**
     * 显示提示内容和确认按钮
     */
    public static void showDialog(Context context, String message) {
        showDialog(context, "", message, "", "", 1, null);
    }

    /**
     * 显示提示内容和确认按钮,并且传入按钮文字
     */
    public static void showDialog(Context context, String message, String sure, View.OnClickListener listener) {
        showDialog(context, "", message, sure, "", 2, listener);
    }

    /**
     * 显示提示内容和确认按钮,并且传入按钮文字
     */
    public static void showDialog(Context context, String message, String sure) {
        showDialog(context, "", message, sure, "", 2, null);
    }

    /**
     * 显示提示内容和确认按钮,并且传入按钮文字，包含标题，标识为4
     */
    public static void showDialog(Context context, String title, String message, String sure, int flag) {
        showDialog(context, title, message, sure, "", flag, null);
    }

    /**
     * 显示提示内容和确认取消按钮
     */
    public static void showDialog(Context context, String message, String sure, String cancel, View.OnClickListener listener) {
        showDialog(context, "", message, sure, cancel, 3, listener);
    }

    /**
     * 显示提示内容和确认取消按钮
     */
    public static void showDialog(Context context, String message, String sure, String cancel) {
        showDialog(context, "", message, sure, cancel, 3, null);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param sure
     * @param cancel
     * @param flag     1:只显示内容和确定  2：只显示提示内容和确认按钮,并且传入按钮文字  3:显示内容、确认、取消
     * @param listener
     */
    public static void showDialog(Context context, String title, String message, String sure,
                                  String cancel, int flag, final View.OnClickListener listener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        View view = View.inflate(context, R.layout.dialog_layout, null);

        alertDialog.setCancelable(true);
        alertDialog.show();
        alertDialog.getWindow().setContentView(view);
        alertDialog.setCanceledOnTouchOutside(false);

        /**
         * 屏幕宽度
         */
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();

        /**
         * 设置大小
         */
        WindowManager.LayoutParams params =
                alertDialog.getWindow().getAttributes();
        params.width = (width / 5) * 4;
        alertDialog.getWindow().setAttributes(params);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message_diolag);
        TextView tvSure = (TextView) view.findViewById(R.id.dialog_sure);
        TextView tvCancel = (TextView) view.findViewById(R.id.dialog_cancel_tv);
        LinearLayout llCancel = (LinearLayout) view.findViewById(R.id.dialog_cancel);

        switch (flag) {
            case 0:
                if (!TextUtils.isEmpty(sure)) {
                    tvSure.setText(sure);
                }
                if (!TextUtils.isEmpty(cancel)) {
                    tvCancel.setText(cancel);
                }
                break;
            case 1:
                tvTitle.setVisibility(View.GONE);
                llCancel.setVisibility(View.GONE);
                break;
            case 2:
                tvTitle.setVisibility(View.GONE);
                llCancel.setVisibility(View.GONE);
                tvSure.setText(sure);
                break;
            case 3:
                tvTitle.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(sure)) {
                    tvSure.setText(sure);
                }
                if (!TextUtils.isEmpty(cancel)) {
                    tvCancel.setText(cancel);
                }
                break;
            case 4:
                llCancel.setVisibility(View.GONE);
                tvSure.setText(sure);
                break;
        }

        tvTitle.setText(title);
        tvMessage.setText(message);

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (listener != null) {
                    listener.onClick(view);
                }
            }
        });
    }
}
