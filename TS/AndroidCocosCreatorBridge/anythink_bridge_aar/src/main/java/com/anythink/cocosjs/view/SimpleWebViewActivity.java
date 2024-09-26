package com.anythink.cocosjs.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.anythink.cocosjs.utils.MsgTools;

public class SimpleWebViewActivity extends Activity {

    public static final String EXTRA_URL = "extra_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String url = intent.getStringExtra(EXTRA_URL);

        if (TextUtils.isEmpty(url)) {
            MsgTools.printMsg("url is empty, do nothing");

            finish();
            return;
        }

        try {
            MsgTools.printMsg("open web view, url: " + url);

            WebView webView = new SimpleWebView(this);
            webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            setContentView(webView);


            webView.loadUrl(url);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MsgTools.printMsg("SimpleWebViewActivity onDestroy");
    }
}
