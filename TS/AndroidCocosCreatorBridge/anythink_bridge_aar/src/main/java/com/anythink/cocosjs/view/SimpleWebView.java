package com.anythink.cocosjs.view;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.reflect.Method;

public class SimpleWebView extends WebView {
    public SimpleWebView(Context context) {
        super(context);

        restrictDeviceContentAccess();

        configSafeWebView(this);

        initializeWebView(this, context);


        setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (TextUtils.isEmpty(url) || "about:blank".equals(url)) {
                    return false;
                }

                view.loadUrl(url);
                return true;
            }

        });

        setWebChromeClient(new WebChromeClient());

    }

    private void restrictDeviceContentAccess() {
        getSettings().setAllowFileAccess(false);
        getSettings().setAllowContentAccess(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getSettings().setAllowFileAccessFromFileURLs(false);
            getSettings().setAllowUniversalAccessFromFileURLs(false);
        }
    }


    public static void configSafeWebView(WebView webView) {
        if (webView == null) {
            return;
        }
        /**
         * Safe WebView
         */
        webView.removeJavascriptInterface("searchBoxjavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        webView.getSettings().setAllowFileAccess(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowFileAccessFromFileURLs(false);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        }
        webView.getSettings().setSavePassword(false);//Close password saving
    }


    public static void initializeWebView(WebView mWebView, Context context) {
        WebSettings webSettings = mWebView.getSettings();

        /*
         * Pinch to zoom is apparently not enabled by default on all devices, so
         * declare zoom support explicitly.
         * https://stackoverflow.com/questions/5125851/enable-disable-zoom-in-android-webview
         */
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setUseWideViewPort(true);

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.requestFocus();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheMaxSize(5242880L);
        webSettings.setAllowFileAccess(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSavePassword(false);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (Build.VERSION.SDK_INT >= 17) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 11) {
            try {
                Method var7 = WebSettings.class.getDeclaredMethod("setDisplayZoomControls", Boolean.TYPE);
                var7.setAccessible(true);
                var7.invoke(webSettings, false);
            } catch (Exception var4) {
            }
        }

        // enable database
        webSettings.setDatabaseEnabled(false);
//        String dbPath = context.getDir("database", Context.MODE_PRIVATE).getPath();
//        webSettings.setDatabasePath(dbPath);

        // enable Geolocation
        webSettings.setGeolocationEnabled(false);
//        webSettings.setGeolocationDatabasePath(dbPath);

//        mWebView.setWebViewClient(new BrowserWebViewClient(iBrowserWebViewClientCallback));

    }


}
