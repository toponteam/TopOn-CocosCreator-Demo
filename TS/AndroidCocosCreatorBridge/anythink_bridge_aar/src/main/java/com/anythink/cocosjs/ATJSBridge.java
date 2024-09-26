package com.anythink.cocosjs;

import static com.anythink.core.api.ATAdConst.ATDevFrameworkType.COCOS_CREATOR;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.NetTrafficeCallback;
import com.anythink.debug.api.ATDebuggerUITest;
import com.cocos.lib.CocosJavascriptJavaBridge;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ATJSBridge {

    private static Activity mActivity = JSPluginUtil.getActivity();

    public static void initSDK(String appId, String appkey) {
        MsgTools.printMsg("initSDK:" + appId + ":" + appkey);
        ATSDK.setSystemDevFragmentType(COCOS_CREATOR);
        ATSDK.init(mActivity.getApplicationContext(), appId, appkey);
    }

    public static void initCustomMap(String customMap) {
        MsgTools.printMsg("initCustomMap:" + customMap);
        if (!TextUtils.isEmpty(customMap)) {
            HashMap<String, Object> map = new HashMap<>();
            try {
                JSONObject jsonObject = new JSONObject(customMap);
                Iterator<String> keys = jsonObject.keys();

                String key;
                while (keys.hasNext()) {
                    key = keys.next();
                    map.put(key, jsonObject.opt(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ATSDK.initCustomMap(map);
        }
    }

    public static void setPlacementCustomMap(String placementId, String customMap) {
        MsgTools.printMsg("setPlacementCustomMap:" + placementId + ":" + customMap);
        if (!TextUtils.isEmpty(customMap)) {
            HashMap<String, Object> map = new HashMap<>();
            try {
                JSONObject jsonObject = new JSONObject(customMap);
                Iterator<String> keys = jsonObject.keys();

                String key;
                while (keys.hasNext()) {
                    key = keys.next();
                    map.put(key, jsonObject.opt(key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ATSDK.initPlacementCustomMap(placementId, map);
        }
    }

    public static void setGDPRLevel(int level) {
        MsgTools.printMsg("setGDPRLevel:" + level);
        ATSDK.setGDPRUploadDataLevel(mActivity.getApplicationContext(), level);
    }

    public static int getGDPRLevel() {
        int gdprDataLevel = ATSDK.getGDPRDataLevel(mActivity.getApplicationContext());
        MsgTools.printMsg("getGDPRLevel:" + gdprDataLevel);
        return gdprDataLevel;
    }

    public static void getUserLocation(final String callbackName) {
        ATSDK.checkIsEuTraffic(mActivity.getApplicationContext(), new NetTrafficeCallback() {
            @Override
            public void onResultCallback(boolean b) {
                MsgTools.printMsg("onResultCallback:" + b);
                final int result = b ? 1 : 2;
                MsgTools.printMsg("Call JS:" + callbackName + "(" + result + ");");
                if (!TextUtils.isEmpty(callbackName)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(callbackName + "(" + result + ");");
                        }
                    });
                }
            }

            @Override
            public void onErrorCallback(String s) {
                MsgTools.printMsg("onErrorCallback:" + s);
                if (!TextUtils.isEmpty(callbackName)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(callbackName + "(" + 0 + ");");
                        }
                    });
                }
            }
        });
        MsgTools.printMsg("getUserLocation");
    }

    public static void showGDPRAuth() {
        MsgTools.printMsg("showGDPRAuth:");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ATSDK.showGdprAuth(mActivity.getApplicationContext());
            }
        });
    }

    public static void setLogDebug(boolean isDebug) {
        MsgTools.setLogDebug(isDebug);
        MsgTools.printMsg("setLogDebug:" + isDebug);
        ATSDK.setNetworkLogDebug(isDebug);
    }

    public static void deniedUploadDeviceInfo(String arrayString) {
        MsgTools.printMsg("deniedUploadDeviceInfo " + arrayString);
        if (!TextUtils.isEmpty(arrayString)) {
            String[] split = arrayString.split(",");
            ATSDK.deniedUploadDeviceInfo(split);
        }
    }

    public static void showDebuggerUI(String debugKey) {
        try {
            MsgTools.printMsg("showDebuggerUI " + debugKey);
            if (!TextUtils.isEmpty(debugKey)) {
                ATDebuggerUITest.showDebuggerUI(mActivity, debugKey);
            } else {
                ATDebuggerUITest.showDebuggerUI(mActivity);
            }
        }  catch (Exception e) {
            MsgTools.printMsg("DebuggerUI not found. " + e.getMessage());
        }
    }

}
