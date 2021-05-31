package com.gy.edu.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gy.edu.R;
import com.gy.edu.util.ActivityUtil;
import com.gy.edu.util.DialogUtil;
import com.gy.edu.view.WebViewProgressBar;

/**
 * Created by 高岳 on 2016/9/5.
 * Describe:
 */
public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private WebViewProgressBar progressBar;
    public static final String TITLE = "title";
    public static final String URL = "url";
    private String url = "";
    private String title = "";
    private WebChromeClient wcc;

    @Override
    protected void initView() {
        setContentViewRes(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.activity_webView);
        progressBar = (WebViewProgressBar) findViewById(R.id.activity_webview_progressbar);
    }

    @Override
    protected void initData() {
        url = getIntent().getExtras().getString(URL);
        title = getIntent().getExtras().getString(TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitleMsg(title);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        WebSettings webSetting = webView.getSettings();
        // 支持JavaScript
        webSetting.setJavaScriptEnabled(true);
        // 设置可以访问文件s
        webSetting.setAllowFileAccess(true);
        // 告诉javascript来自动打开的窗口。这适用于JavaScript函数的窗口，open()。
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // 支持缩放
        webSetting.setSupportZoom(true);
        // 是否禁止是网络加载数据
        webSetting.setBlockNetworkLoads(false);
        // 设置是否支持多窗口
        webSetting.setSupportMultipleWindows(true);
        // 是否开启本地DOM存储
        webSetting.setDomStorageEnabled(true);
        // 设置不缓存
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 阻塞加载图片
        webSetting.setBlockNetworkImage(false);
        // 支持启用插件
        webSetting.setPluginState(WebSettings.PluginState.ON);
        // 设置任意比较缩放为真
        webSetting.setUseWideViewPort(true);
        // 设置WebView加载页面的模式
        webSetting.setLoadWithOverviewMode(true);
        // 控制页面显示布局
        // NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
        // NORMAL：正常显示不做任何渲染
        // SINGLE_COLUMN：把所有内容放大webview等宽的一列中
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //禁止用地理定位
        webSetting.setSaveFormData(true);
        // 是否启动地理定位
        webSetting.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSetting.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        String userAgent = webSetting.getUserAgentString();
        webSetting.setUserAgentString(userAgent + " KoudailejuApp");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 大于21
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // webSetting.setTextSize(t)
        wcc = new WebChromeClientRingTone();
        webView.setWebChromeClient(wcc);
        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (TextUtils.isEmpty(url)) return;
                Uri u = Uri.parse(url);
                Intent skip_intent = new Intent(Intent.ACTION_VIEW, u);
                startActivity(skip_intent);
            }

        });
        webView.addJavascriptInterface(new CaptureSource(), "handler");
        webView.setWebViewClient(new WebViewClientRingTone());
        webView.requestFocus();
        // webViewScroolChangeListener();
        webView.setOnCreateContextMenuListener(this);
    }

    @Override
    protected void fillData() {
        super.fillData();
        webView.setFocusable(true);
        if(!ActivityUtil.isNetworkAvailable(mContext)){
            return;
        }else{
            webView.loadUrl(url);
        }

    }

    /**
     * 处理url
     *
     * @param url
     * @return
     */
    private String dealUrl(String url) {
        return url;
    }

    private class WebChromeClientRingTone extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
            WebView.HitTestResult result = view.getHitTestResult();
            String data = result.getExtra();
            view.loadUrl(data);
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        }

    }

    final class CaptureSource {
        public void captureSource(String source) {
        }
    }

    private class WebViewClientRingTone extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url))
                return true;
                return false;
        }

        @SuppressLint({"JavascriptInterface", "NewApi"})
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onReceivedSslError(WebView view,
                                       android.webkit.SslErrorHandler handler,
                                       android.net.http.SslError error) {
            handler.proceed();
        }

    }
}
