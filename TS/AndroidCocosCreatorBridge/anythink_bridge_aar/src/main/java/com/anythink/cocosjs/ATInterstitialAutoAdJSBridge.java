package com.anythink.cocosjs;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.interstitial.InterstitialAutoADHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitialAutoAd;
import com.anythink.interstitial.api.ATInterstitialAutoEventListener;
import com.anythink.interstitial.api.ATInterstitialAutoLoadListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.cocos.lib.CocosJavascriptJavaBridge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATInterstitialAutoAdJSBridge {

    private final static String LOG_PRE = "interstitial[AutoAD] ";
    private static String listenerJson;
    private static Activity mActivity;
    private static InterstitialAutoADHelper mHelper;
    private static List<String> mAddedPlacementIds = new ArrayList<>();

    public static void setAdListener(String listener) {
        MsgTools.printMsg(LOG_PRE, "setAdListener >>> " + listener);
        listenerJson = listener;
        init();
    }

    private static void init() {
        mActivity = JSPluginUtil.getActivity();
        if (mHelper == null) {
            mHelper = new InterstitialAutoADHelper();
        }
        mHelper.setAdListener(listenerJson);
        ATInterstitialAutoAd.init(mActivity.getApplicationContext(), null, new ATInterstitialAutoLoadListener() {
            @Override
            public void onInterstitialAutoLoaded(final String placementId) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAutoLoaded: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.LoadedCallbackKey)
                                    + "('" + placementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAutoLoadFail(final String placementId, final AdError adError) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAutoLoadFail: " + placementId + ", " + adError.getFullErrorInfo());

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.LoadFailCallbackKey)
                                    + "('" + placementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });

    }

    public static void addPlacementIds(String placementIds) {
        if (TextUtils.isEmpty(placementIds)) {
            MsgTools.printMsg(LOG_PRE, "addPlacementIds warn: not set placementIds");
            return;
        }
        try {
            MsgTools.printMsg(LOG_PRE, "addPlacementIds: " + placementIds);
            JSONArray jsonArray = new JSONArray(placementIds);
            String[] placementIdArr = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                placementIdArr[i] = jsonArray.getString(i);
            }
            mAddedPlacementIds.addAll(Arrays.asList(placementIdArr));
            ATInterstitialAutoAd.addPlacementId(placementIdArr);
        } catch (Exception e) {
            e.printStackTrace();
            MsgTools.printMsg(LOG_PRE, "addPlacementIds error,please check your placementIds:\"" + placementIds + "\"");
        }
    }

    public static void removePlacementId(String placementIds) {
        if (TextUtils.isEmpty(placementIds)) {
            MsgTools.printMsg(LOG_PRE, "removePlacementId warn: not set placementId");
            return;
        }

        try {
            MsgTools.printMsg(LOG_PRE, "removePlacementId: " + placementIds);
            JSONArray jsonArray = new JSONArray(placementIds);
            String[] placementIdArr = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                placementIdArr[i] = jsonArray.getString(i);
            }
            mAddedPlacementIds.removeAll(Arrays.asList(placementIdArr));
            ATRewardVideoAutoAd.removePlacementId(placementIdArr);
        } catch (Exception e) {
            e.printStackTrace();
            MsgTools.printMsg(LOG_PRE, "addPlacementIds error,please check your placementIds:\"" + placementIds + "\"");
        }
    }

    public static void show(String placementId) {
        show(placementId, "");
    }

    public static void show(final String placementId, String scenario) {
//        check setAdListen
        if (TextUtils.isEmpty(listenerJson) || mHelper == null) {
            MsgTools.printMsg(LOG_PRE, "show fail:please setListener before show");
            return;
        }

        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "show please addPlacementIds first: " + placementId);
        }

        if (!ATInterstitialAutoAd.isAdReady(placementId)) {
            MsgTools.printMsg(LOG_PRE, "show fail:this placementId(" + placementId + ") is not ready to show");
            return;
        }

        MsgTools.printMsg(LOG_PRE, "show placementId:" + placementId + ", scenario:" + scenario);

        ATInterstitialAutoAd.show(mActivity, placementId, scenario, new ATInterstitialAutoEventListener() {
            @Override
            public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdClicked: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.ClickCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdShow: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.ShowCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdClose: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.CloseCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdVideoStart: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.PlayStartCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdVideoEnd: " + placementId);

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.PlayEndCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.printMsg(LOG_PRE, "onInterstitialAdVideoError: " + placementId + ", " + adError.getFullErrorInfo());

                if (mHelper.hasCallbackName(Const.InterstitialAutoAdCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.InterstitialAutoAdCallback.PlayFailCallbackKey)
                                    + "('" + placementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });
    }

    public static void setAdExtraData(final String placementId, final String settings) {
        MsgTools.printMsg(LOG_PRE, "setExtraData start: " + placementId + ", settings: " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(placementId) && !TextUtils.isEmpty(settings)) {
                    try {
                        if (mHelper == null) {
                            mHelper = new InterstitialAutoADHelper();
                        }
                        JSONObject jsonObject = new JSONObject(settings);
                        Map<String, Object> localExtra = new HashMap<>();
                        if (jsonObject.has(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                            if ((boolean) jsonObject.get(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                                localExtra.put("is_use_rewarded_video_as_interstitial", true);
                            }
                        }
                        mHelper.fillMapFromJsonObjectExposed(localExtra, jsonObject);
                        ATInterstitialAutoAd.setLocalExtra(placementId, localExtra);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static boolean isAdReady(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "isAdReady please addPlacementIds first: " + placementId);
        }
        boolean isReady = ATInterstitialAutoAd.isAdReady(placementId);
        MsgTools.printMsg(LOG_PRE, "isAdReady: " + placementId + ", " + isReady);

        return isReady;
    }

    public static String checkAdStatus(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "checkAdStatus please addPlacementIds first: " + placementId);
        }
        ATAdStatusInfo atAdStatusInfo = ATInterstitialAutoAd.checkAdStatus(placementId);
        boolean loading = atAdStatusInfo.isLoading();
        boolean ready = atAdStatusInfo.isReady();
        ATAdInfo atTopAdInfo = atAdStatusInfo.getATTopAdInfo();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isLoading", loading);
            jsonObject.put("isReady", ready);
            jsonObject.put("adInfo", atTopAdInfo);

            return jsonObject.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void entryAdScenario(String placementId, String scenario) {
        MsgTools.printMsg(LOG_PRE, "entryAdScenario... " + placementId + "," + scenario);
        ATInterstitialAutoAd.entryAdScenario(placementId, scenario);
    }
}
