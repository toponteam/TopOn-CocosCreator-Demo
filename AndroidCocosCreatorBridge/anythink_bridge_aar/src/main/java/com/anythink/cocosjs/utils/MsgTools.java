package com.anythink.cocosjs.utils;

import android.util.Log;

import com.anythink.cocosjs.utils.JSPluginUtil;

public class MsgTools {
    private static final String TAG = JSPluginUtil.TAG;
    static boolean isDebug = true;

    public static void pirntMsg(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void setLogDebug(boolean debug) {
        isDebug = debug;
    }

}
