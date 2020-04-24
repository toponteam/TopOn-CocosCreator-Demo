package com.anythink.cocosjs.utils;

import android.app.Activity;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

import java.sql.ParameterMetaData;

public class JSPluginUtil {

    public static final String TAG = "ATJSBridge";

    public static Activity getActivity() {
        return (Activity) Cocos2dxActivity.getContext();
    }

    public static void runOnUiThread(final Runnable runnable) {
        try {
            getActivity().runOnUiThread(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runOnGLThread(final Runnable runnable) {
        try {
            ((Cocos2dxActivity) getActivity()).runOnGLThread(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
