package com.anythink.cocosjs;

import com.anythink.cocosjs.banner.BannerHelper;
import com.anythink.cocosjs.utils.MsgTools;

import java.util.HashMap;

public class ATBannerJSBridge {

    private static final HashMap<String, BannerHelper> sHelperMap = new HashMap<>();

    private static String listenerJson;

    public static void setAdListener(String listener) {
        MsgTools.pirntMsg("banner setAdListener >>> " + listener);
        listenerJson = listener;
    }


    public static void load(String placementId, String settings) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.setAdListener(listenerJson);
            helper.loadBanner(placementId, settings);
        }

    }

    public static void showWithPosition(String placementId, String position, String scenario) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showBannerWithPosition(position, scenario);
        }
    }

    public static void showWithRect(String placementId, String rectJson, String scenario) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.showBannerWithRect(rectJson, scenario);
        }
    }

    public static void hide(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.hideBanner();
        }
    }

    public static void reshow(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.reshowBanner();
        }
    }

    public static void remove(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            helper.removeBanner();
        }
    }

    public static boolean isAdReady(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.isAdReady();
        }
        return false;
    }

    public static String checkAdStatus(String placementId) {
        BannerHelper helper = getHelper(placementId);
        if (helper != null) {
            return helper.checkAdStatus();
        }
        return "";
    }

    private static BannerHelper getHelper(String placementId) {
        BannerHelper helper;

        if (!sHelperMap.containsKey(placementId)) {
            helper = new BannerHelper();
            sHelperMap.put(placementId, helper);
        } else {
            helper = sHelperMap.get(placementId);
        }

        return helper;
    }
}
