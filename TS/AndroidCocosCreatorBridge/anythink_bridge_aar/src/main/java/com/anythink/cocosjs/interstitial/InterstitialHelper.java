package com.anythink.cocosjs.interstitial;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.callback.AdSourceCallbackListener;
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
import com.cocos.lib.CocosJavascriptJavaBridge;


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
        MsgTools.printMsg(TAG + ": " + this);
        mActivity = JSPluginUtil.getActivity();
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }


    private void initInterstitial(final String placementId) {
        mPlacementId = placementId;
        MsgTools.printMsg("initInterstitial: " + mPlacementId);

        mInterstitialAd = new ATInterstitial(mActivity, placementId);

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.printMsg("onInterstitialAdLoaded: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadedCallbackKey)
                                    + "('" + mPlacementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.printMsg("onInterstitialAdLoadFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onInterstitialAdClicked: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ClickCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onInterstitialAdShow: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ShowCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onInterstitialAdClose: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.CloseCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onInterstitialAdVideoStart: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onInterstitialAdVideoEnd: " + mPlacementId);

                if (hasCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.printMsg("onInterstitialAdVideoError: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });

        mInterstitialAd.setAdSourceStatusListener(new AdSourceCallbackListener(this, mPlacementId));
    }

    public void loadInterstitial(final String placementId, final String settings) {
        MsgTools.printMsg("loadInterstitial: " + placementId + ", settings: " + settings);
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
        MsgTools.printMsg("showInterstitial: " + mPlacementId + ", scenario: " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    isReady = false;
                    mInterstitialAd.show(mActivity, scenario);
                } else {
                    MsgTools.printMsg("showInterstitial error, you must call loadInterstitial first, placementId" + mPlacementId);
                    if (hasCallbackName(Const.InterstitialCallback.ShowFailCallbackKey)) {
                        JSPluginUtil.runOnGLThread(new Runnable() {
                            @Override
                            public void run() {
                                CocosJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ShowFailCallbackKey)
                                        + "('" + mPlacementId + "','" + "you must call loadInterstitial first" + "');");
                            }
                        });
                    }
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.printMsg("interstitial isAdReady: " + mPlacementId);

        try {
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.printMsg("interstitial isAdReady: " + mPlacementId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.printMsg("interstitial isAdReady error, you must call loadInterstitial first " + mPlacementId);
            }
            MsgTools.printMsg("interstitial isAdReady, end: " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.printMsg("interstitial isAdReady, Throwable: " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.printMsg("interstitial checkAdStatus: " + mPlacementId);

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

    @Override
    public String getBiddingAttemptMethodName() {
        return Const.InterstitialCallback.AdSourceBiddingAttempt;
    }

    @Override
    public String getBiddingFilledMethodName() {
        return Const.InterstitialCallback.AdSourceBiddingFilled;
    }

    @Override
    public String getBiddingFailMethodName() {
        return Const.InterstitialCallback.AdSourceBiddingFail;
    }

    @Override
    public String getAttempMethodName() {
        return Const.InterstitialCallback.AdSourceAttemp;
    }

    @Override
    public String getLoadFilledMethodName() {
        return Const.InterstitialCallback.AdSourceLoadFilled;
    }

    @Override
    public String getLoadFailMethodName() {
        return Const.InterstitialCallback.AdSourceLoadFail;
    }
}
