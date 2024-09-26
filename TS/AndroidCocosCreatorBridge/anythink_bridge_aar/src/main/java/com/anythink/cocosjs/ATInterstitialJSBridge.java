package com.anythink.cocosjs;

import com.anythink.cocosjs.interstitial.InterstitialHelper;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.interstitial.api.ATInterstitial;

import java.util.HashMap;

public class ATInterstitialJSBridge {

    private static final HashMap<String, InterstitialHelper> sHelperMap = new HashMap<>();

    private static String listenerJson;

    public static void setAdListener(String listener) {
        MsgTools.printMsg("interstitial setAdListener >>> " + listener);
        listenerJson = listener;
    }

    public static void load(String placementId, String settings) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.setAdListener(listenerJson);
            helper.loadInterstitial(placementId, settings);
        }
    }

    public static void show(String placementId) {
        show(placementId, "");
    }

    public static void show(String placementId, String settings) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showInterstitial(settings);
        }
    }

    public static boolean isAdReady(String placementId) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.isAdReady();
        }
        return false;
    }

    public static String checkAdStatus(String placementId) {
        InterstitialHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.checkAdStatus();
        }
        return "";
    }

    public static void entryAdScenario(String placementId, String scenario) {
        MsgTools.printMsg("interstitial entryAdScenario... " + placementId + "," + scenario);
        ATInterstitial.entryAdScenario(placementId, scenario);
    }

    private static InterstitialHelper getHelper(String placementId) {
        InterstitialHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new InterstitialHelper();
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
