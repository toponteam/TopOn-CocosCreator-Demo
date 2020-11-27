package com.anythink.cocosjs;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATSDK;
import com.anythink.core.api.NetTrafficeCallback;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ATJSBridge {

    private static Activity mActivity = JSPluginUtil.getActivity();

    public static void initSDK(String appId, String appkey) {
        MsgTools.pirntMsg("initSDK:" + appId + ":" + appkey);
        ATSDK.init(mActivity.getApplicationContext(), appId, appkey);
    }

    public static void initCustomMap(String customMap) {
        MsgTools.pirntMsg("initCustomMap:" + customMap);
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
        MsgTools.pirntMsg("setPlacementCustomMap:" + placementId + ":" + customMap);
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
        MsgTools.pirntMsg("setGDPRLevel:" + level);
        ATSDK.setGDPRUploadDataLevel(mActivity.getApplicationContext(), level);
    }

    public static int getGDPRLevel() {
        int gdprDataLevel = ATSDK.getGDPRDataLevel(mActivity.getApplicationContext());
        MsgTools.pirntMsg("getGDPRLevel:" + gdprDataLevel);
        return gdprDataLevel;
    }

    public static void getUserLocation(final String callbackName) {
        ATSDK.checkIsEuTraffic(mActivity.getApplicationContext(), new NetTrafficeCallback() {
            @Override
            public void onResultCallback(boolean b) {
                MsgTools.pirntMsg("onResultCallback:" + b);
                final int result = b ? 1 : 2;
                MsgTools.pirntMsg("Call JS:" + callbackName + "(" + result + ");");
                if (!TextUtils.isEmpty(callbackName)) {
                    ((Cocos2dxActivity) mActivity).runOnGLThread(new Runnable() {
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(callbackName + "(" + result + ");");
                        }
                    });
                }
            }

            @Override
            public void onErrorCallback(String s) {
                MsgTools.pirntMsg("onErrorCallback:" + s);
                if (!TextUtils.isEmpty(callbackName)) {
                    ((Cocos2dxActivity) mActivity).runOnGLThread(new Runnable() {
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(callbackName + "(" + 0 + ");");
                        }
                    });
                }
            }
        });
        MsgTools.pirntMsg("getUserLocation");
    }

    public static void showGDPRAuth() {
        MsgTools.pirntMsg("showGDPRAuth:");
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ATSDK.showGdprAuth(mActivity.getApplicationContext());
            }
        });
    }

    public static void setLogDebug(boolean isDebug) {
        MsgTools.setLogDebug(isDebug);
        MsgTools.pirntMsg("setLogDebug:" + isDebug);
        ATSDK.setNetworkLogDebug(isDebug);
    }

    public static void deniedUploadDeviceInfo(String arrayString) {
        MsgTools.pirntMsg("deniedUploadDeviceInfo " + arrayString);
        if (!TextUtils.isEmpty(arrayString)) {
            String[] split = arrayString.split(",");
            ATSDK.deniedUploadDeviceInfo(split);
        }
    }

}
