package com.anythink.cocosjs;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.rewardvideo.RewardVideoAutoADHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoEventListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoLoadListener;
import com.cocos.lib.CocosJavascriptJavaBridge;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATRewardedVideoAutoAdJSBridge {

    private final static String LOG_PRE = "video[AutoAD] ";
    private static String listenerJson;
    private static Activity mActivity;
    private static RewardVideoAutoADHelper mHelper;
    private static List<String> mAddedPlacementIds = new ArrayList<>();

    public static void setAdListener(String listener) {
        MsgTools.printMsg(LOG_PRE, "setAdListener >>> " + listener);
        listenerJson = listener;
        init();
    }

    private static void init() {
        mActivity = JSPluginUtil.getActivity();
        if (mHelper == null) {
            mHelper = new RewardVideoAutoADHelper();
        }
        mHelper.setAdListener(listenerJson);
        ATRewardVideoAutoAd.init(mActivity.getApplicationContext(), null, new ATRewardVideoAutoLoadListener() {
            @Override
            public void onRewardVideoAutoLoaded(final String placementId) {
                MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoaded: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.LoadedCallbackKey)
                                    + "('" + placementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardVideoAutoLoadFail(final String placementId, final AdError adError) {
                MsgTools.printMsg(LOG_PRE, "onRewardVideoAutoLoadFail: " + placementId + ", " + adError.getFullErrorInfo());

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {

                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.LoadFailCallbackKey)
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
            ATRewardVideoAutoAd.addPlacementId(placementIdArr);
        } catch (Exception e) {
            e.printStackTrace();
            MsgTools.printMsg(LOG_PRE, "addPlacementIds error,please check your placementIds:\"" + placementIds + "\"");
        }
    }

    public static void removePlacementId(String placementIds) {
        if (TextUtils.isEmpty(placementIds)) {
            MsgTools.printMsg(LOG_PRE, "removePlacementId warn: not set placementIds");
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

        if (!ATRewardVideoAutoAd.isAdReady(placementId)) {
            MsgTools.printMsg(LOG_PRE, "show fail:this placementId(" + placementId + ") is not ready to show");
            return;
        }

        MsgTools.printMsg(LOG_PRE, "show placementId:" + placementId + ", scenario:" + scenario);

        ATRewardVideoAutoAd.show(mActivity, placementId, scenario, new ATRewardVideoAutoEventListener() {
            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdPlayStart: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.PlayStartCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdPlayEnd: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.PlayEndCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError adError, final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdPlayFailed: " + placementId + ", " + adError.getFullErrorInfo());

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.PlayFailCallbackKey)
                                    + "('" + placementId + "','" + CommonUtil.getErrorMsg(adError) + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdClosed: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.CloseCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdPlayClicked: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.ClickCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onReward(final ATAdInfo atAdInfo) {
                MsgTools.printMsg(LOG_PRE, "onReward: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.RewardCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.RewardCallbackKey)
                                    + "('" + placementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayStart(final ATAdInfo adInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdAgainPlayStart: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayStartCallbackKey)
                                    + "('" + placementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayEnd(final ATAdInfo adInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdAgainPlayEnd: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayEndCallbackKey)
                                    + "('" + placementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayFailed(final AdError errorCode, final ATAdInfo adInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdAgainPlayFailed: " + placementId + ", " + errorCode.getFullErrorInfo());

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.AgainPlayFailCallbackKey)
                                    + "('" + placementId + "','" + CommonUtil.getErrorMsg(errorCode) + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayClicked(final ATAdInfo adInfo) {
                MsgTools.printMsg(LOG_PRE, "onRewardedVideoAdAgainPlayClicked: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.AgainClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.AgainClickCallbackKey)
                                    + "('" + placementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onAgainReward(final ATAdInfo adInfo) {
                MsgTools.printMsg(LOG_PRE, "onAgainReward: " + placementId);

                if (mHelper.hasCallbackName(Const.RewardVideoAutoAdCallback.AgainRewardCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(Const.RewardVideoAutoAdCallback.AgainRewardCallbackKey)
                                    + "('" + placementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }
        });
    }

    public static void setAdExtraData(String placementId, String settings) {
        MsgTools.printMsg(LOG_PRE, "setExtraData start: " + placementId + "," + settings);
        if (mHelper == null) {
            mHelper = new RewardVideoAutoADHelper();
        }
        if (!TextUtils.isEmpty(settings)) {
            Map<String, Object> localExtra = new HashMap<>();

            String userId = "";
            String userData = "";
            try {
                JSONObject jsonObject = new JSONObject(settings);

                mHelper.fillMapFromJsonObjectExposed(localExtra, jsonObject);

                if (jsonObject.has(Const.USER_ID)) {
                    userId = jsonObject.optString(Const.USER_ID);
                }
                if (jsonObject.has(Const.USER_DATA)) {
                    userData = jsonObject.optString(Const.USER_DATA);
                }

                localExtra.put("user_id", userId);
                localExtra.put("user_custom_data", userData);
                ATRewardVideoAutoAd.setLocalExtra(placementId, localExtra);
                MsgTools.printMsg(LOG_PRE, "setExtraData success: " + placementId + "," + settings);
            } catch (Exception e) {
                e.printStackTrace();
                MsgTools.printMsg(LOG_PRE, "setExtraData failed: " + placementId + "," + settings);
            }

        }

    }

    public static boolean isAdReady(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "isAdReady please addPlacementIds first: " + placementId);
        }
        boolean isReady = ATRewardVideoAutoAd.isAdReady(placementId);
        MsgTools.printMsg(LOG_PRE, "isAdReady: " + placementId + ", " + isReady);

        return isReady;
    }

    public static String checkAdStatus(String placementId) {
        if (!mAddedPlacementIds.contains(placementId)) {
            MsgTools.printMsg(LOG_PRE, "checkAdStatus please addPlacementIds first: " + placementId);
        }
        ATAdStatusInfo atAdStatusInfo = ATRewardVideoAutoAd.checkAdStatus(placementId);
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
        ATRewardVideoAutoAd.entryAdScenario(placementId, scenario);
    }
}
