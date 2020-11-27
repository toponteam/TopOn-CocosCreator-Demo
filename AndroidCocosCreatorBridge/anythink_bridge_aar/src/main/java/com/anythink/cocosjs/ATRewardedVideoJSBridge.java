package com.anythink.cocosjs;

import com.anythink.cocosjs.rewardvideo.RewardVideoHelper;
import com.anythink.cocosjs.utils.MsgTools;

import java.util.HashMap;

public class ATRewardedVideoJSBridge {

    private static final HashMap<String, RewardVideoHelper> sHelperMap = new HashMap<>();

    private static String listenerJson;

    public static void setAdListener(String listener) {
        MsgTools.pirntMsg("video setAdListener >>> " + listener);
        listenerJson = listener;
    }


    public static void load(String placementId, String settings) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.setAdListener(listenerJson);
            helper.loadRewardedVideo(placementId, settings);
        }
    }

    public static void show(String placementId) {
        show(placementId, "");
    }

    public static void show(String placementId, String scenario) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showVideo(scenario);
        }
    }

    public static boolean isAdReady(String placementId) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.isAdReady();
        }
        return false;
    }

    public static String checkAdStatus(String placementId) {
        RewardVideoHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.checkAdStatus();
        }
        return "";
    }

    private static RewardVideoHelper getHelper(String placementId) {
        RewardVideoHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new RewardVideoHelper();
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
