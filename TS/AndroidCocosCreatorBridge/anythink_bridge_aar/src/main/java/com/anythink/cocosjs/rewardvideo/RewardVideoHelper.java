package com.anythink.cocosjs.rewardvideo;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.anythink.cocosjs.callback.AdSourceCallbackListener;
import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoExListener;
import com.cocos.lib.CocosJavascriptJavaBridge;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RewardVideoHelper extends BaseHelper {

    private static final String TAG = RewardVideoHelper.class.getSimpleName();

    ATRewardVideoAd mRewardVideoAd;
    String mPlacementId;
    Activity mActivity;

    boolean isReady = false;

    public RewardVideoHelper() {
        MsgTools.printMsg(TAG + ": " + this);
        mActivity = JSPluginUtil.getActivity();
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }

    private void initVideo(final String placementId) {
        mPlacementId = placementId;
        MsgTools.printMsg("initVideo placementId: " + mPlacementId);

        mRewardVideoAd = new ATRewardVideoAd(mActivity, placementId);

        mRewardVideoAd.setAdListener(new ATRewardVideoExListener() {

            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.printMsg("onRewardedVideoAdLoaded: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadedCallbackKey)
                                    + "('" + mPlacementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdFailed(final AdError pAdError) {
                MsgTools.printMsg("onRewardedVideoAdFailed: " + mPlacementId + ", " + pAdError.getFullErrorInfo());

                if (hasCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(pAdError) + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayStart: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayStartCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayEnd: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayEndCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError adError, final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayFailed: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }


            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdClosed: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.CloseCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo adInfo) {
                MsgTools.printMsg("onRewardedVideoAdPlayClicked: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.ClickCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onReward(final ATAdInfo adInfo) {
                MsgTools.printMsg("onReward: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.RewardCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.RewardCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardFailed(ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardFailed: " + mPlacementId);
            }

            @Override
            public void onDeeplinkCallback(final ATAdInfo atAdInfo, final boolean b) {
            }

            @Override
            public void onDownloadConfirm(Context context, ATAdInfo atAdInfo, ATNetworkConfirmInfo atNetworkConfirmInfo) {
            }

            @Override
            public void onRewardedVideoAdAgainPlayStart(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayStart: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.AgainPlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.AgainPlayStartCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayEnd(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayEnd: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.AgainPlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.AgainPlayEndCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayFailed(final AdError adError, final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayFailed: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.RewardVideoCallback.AgainPlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.AgainPlayFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdAgainPlayClicked(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onRewardedVideoAdAgainPlayClicked: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.AgainClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.AgainClickCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onAgainReward(final ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAgainReward: " + mPlacementId);

                if (hasCallbackName(Const.RewardVideoCallback.AgainRewardCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.AgainRewardCallbackKey)
                                    + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onAgainRewardFailed(ATAdInfo atAdInfo) {
                MsgTools.printMsg("onAgainRewardFailed: " + mPlacementId);
            }
        });

        mRewardVideoAd.setAdSourceStatusListener(new AdSourceCallbackListener(this, mPlacementId));
    }


    public void loadRewardedVideo(final String placementId, final String settings) {
        MsgTools.printMsg("loadRewardedVideo: " + placementId + ", settings: " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mRewardVideoAd == null) {
                    initVideo(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {
                    Map<String, Object> localExtra = new HashMap<>();

                    String userId = "";
                    String userData = "";
                    try {
                        JSONObject jsonObject = new JSONObject(settings);

                        fillMapFromJsonObject(localExtra, jsonObject);

                        if (jsonObject.has(Const.USER_ID)) {
                            userId = jsonObject.optString(Const.USER_ID);
                        }
                        if (jsonObject.has(Const.USER_DATA)) {
                            userData = jsonObject.optString(Const.USER_DATA);
                        }

                        localExtra.put("user_id", userId);
                        localExtra.put("user_custom_data", userData);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mRewardVideoAd.setLocalExtra(localExtra);
                }

                mRewardVideoAd.load();
            }
        });
    }

    public void showVideo(final String scenario) {
        MsgTools.printMsg("showVideo: " + mPlacementId + ", scenario: " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardVideoAd != null) {
                    isReady = false;
                    mRewardVideoAd.show(mActivity, scenario);
                } else {
                    MsgTools.printMsg("showVideo error, you must call loadRewardVideo first " + mPlacementId);
                    if (hasCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)) {
                        JSPluginUtil.runOnGLThread(new Runnable() {
                            @Override
                            public void run() {
                                CocosJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)
                                        + "('" + mPlacementId + "','" + "you must call loadRewardVideo first" + "');");
                            }
                        });
                    }
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.printMsg("video isAdReady: " + mPlacementId);

        try {
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.printMsg("video isAdReady: " + mPlacementId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.printMsg("video isAdReady error, you must call loadRewardedVideo first " + mPlacementId);
            }
            MsgTools.printMsg("video isAdReady, end: " + mPlacementId);
        } catch (Throwable e) {
            MsgTools.printMsg("video isAdReady, Throwable: " + e.getMessage());
            return isReady;
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.printMsg("video checkAdStatus: " + mPlacementId);

        if (mRewardVideoAd != null) {
            ATAdStatusInfo atAdStatusInfo = mRewardVideoAd.checkAdStatus();
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
        return Const.RewardVideoCallback.AdSourceBiddingAttempt;
    }

    @Override
    public String getBiddingFilledMethodName() {
        return Const.RewardVideoCallback.AdSourceBiddingFilled;
    }

    @Override
    public String getBiddingFailMethodName() {
        return Const.RewardVideoCallback.AdSourceBiddingFail;
    }

    @Override
    public String getAttempMethodName() {
        return Const.RewardVideoCallback.AdSourceAttemp;
    }

    @Override
    public String getLoadFilledMethodName() {
        return Const.RewardVideoCallback.AdSourceLoadFilled;
    }

    @Override
    public String getLoadFailMethodName() {
        return Const.RewardVideoCallback.AdSourceLoadFail;
    }
}
