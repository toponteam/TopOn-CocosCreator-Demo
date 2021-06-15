package com.anythink.cocosjs;

import com.anythink.cocosjs.nativead.NativeHelper;
import com.anythink.cocosjs.utils.MsgTools;

import java.util.HashMap;

public class ATNativeJSBridge {

    private static final HashMap<String, NativeHelper> sHelperMap = new HashMap<>();

    private static String listenerJson;

    public static void setAdListener(String listener) {
        MsgTools.pirntMsg("native setAdListener >>> " + listener);
        listenerJson = listener;
    }


    public static void load(String placementId, String settings) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.setAdListener(listenerJson);
            helper.loadNative(placementId, settings);
        }
    }

    public static void show(String placementId, String adViewProperty, String scenario) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.show(adViewProperty, scenario);
        }
    }

    public static void remove(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.remove();
        }
    }

    public static void onResume(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.onResume();
        }
    }

    public static void onPause(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.onPause();
        }
    }

    public static void clean(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.clean();
        }
    }

    public static boolean isAdReady(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.isAdReady();
        }
        return false;
    }

    public static String checkAdStatus(String placementId) {
        NativeHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.checkAdStatus();
        }
        return "";
    }

    private static NativeHelper getHelper(String placementId) {
        NativeHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new NativeHelper();
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
