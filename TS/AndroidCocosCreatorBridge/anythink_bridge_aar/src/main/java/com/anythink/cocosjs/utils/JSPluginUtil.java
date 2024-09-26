package com.anythink.cocosjs.utils;

import android.app.Activity;

import com.cocos.lib.CocosActivity;
import com.cocos.lib.CocosHelper;
import com.cocos.lib.GlobalObject;


import java.sql.ParameterMetaData;

public class JSPluginUtil {

    public static final String TAG = "ATJSBridge";

    public static Activity getActivity() {

        return  (CocosActivity) GlobalObject.getActivity();
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
            CocosHelper.runOnGameThread(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
