package com.anythink.cocosjs.interstitial;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InterstitialHelper extends BaseHelper {

    private static final String TAG = InterstitialHelper.class.getSimpleName();

    ATInterstitial mInterstitialAd;
    String mPlacementId;
    Activity mActivity;

    boolean isReady = false;

    public InterstitialHelper() {
        MsgTools.pirntMsg(TAG + ": " + this);
        mActivity = JSPluginUtil.getActivity();
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }


    private void initInterstitial(final String placementId) {
        mPlacementId = placementId;
        MsgTools.pirntMsg("initInterstitial: " + mPlacementId);

        mInterstitialAd = new ATInterstitial(mActivity, placementId);

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.pirntMsg("onInterstitialAdLoaded: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadedCallbackKey)
                                    + "('" + mPlacementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdLoadFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClicked: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ClickCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdShow: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ShowCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClose: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.CloseCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoStart: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoEnd: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdVideoError: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });
    }

    public void loadInterstitial(final String placementId, final String settings) {
        MsgTools.pirntMsg("loadInterstitial: " + placementId + ", settings: " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd == null) {
                    initInterstitial(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {
                    try {
                        JSONObject jsonObject = new JSONObject(settings);
                        Map<String, Object> localExtra = new HashMap<>();
                        if (jsonObject.has(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                            if ((boolean) jsonObject.get(Const.Interstital.UseRewardedVideoAsInterstitial)) {
                                localExtra.put("is_use_rewarded_video_as_interstitial", true);
                            }
                        }
                        fillMapFromJsonObject(localExtra, jsonObject);
                        mInterstitialAd.setLocalExtra(localExtra);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                mInterstitialAd.load();
            }
        });
    }

    public void showInterstitial(final String scenario) {
        MsgTools.pirntMsg("showInterstitial: " + mPlacementId + ", scenario: " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    isReady = false;
                    mInterstitialAd.show(mActivity, scenario);
                } else {
                    MsgTools.pirntMsg("showInterstitial error, you must call loadRewardVideo first, placementId" + mPlacementId);
                    if (hasCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)) {
                        JSPluginUtil.runOnGLThread(new Runnable() {
                            @Override
                            public void run() {
                                Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)
                                        + "('" + mPlacementId + "','" + "you must call loadRewardVideo first" + "');");
                            }
                        });
                    }
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("interstitial isAdReady: " + mPlacementId);

        try {
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.pirntMsg("interstitial isAdReady: " + mPlacementId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("interstitial isAdReady error, you must call loadInterstitial first " + mPlacementId);
            }
            MsgTools.pirntMsg("interstitial isAdReady, end: " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("interstitial isAdReady, Throwable: " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.pirntMsg("interstitial checkAdStatus: " + mPlacementId);

        if (mInterstitialAd != null) {
            ATAdStatusInfo atAdStatusInfo = mInterstitialAd.checkAdStatus();
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
        }
        return "";
    }
}
