package com.anythink.cocosjs.utils;

import android.util.Log;

public class MsgTools {
    private static final String TAG = JSPluginUtil.TAG;
    public static boolean isDebug = true;

    public static void printMsg(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void printMsg(String logPre, String msg) {
        if (isDebug) {
            Log.e(TAG, logPre + msg);
        }
    }

    public static void setLogDebug(boolean debug) {
        isDebug = debug;
    }

}
