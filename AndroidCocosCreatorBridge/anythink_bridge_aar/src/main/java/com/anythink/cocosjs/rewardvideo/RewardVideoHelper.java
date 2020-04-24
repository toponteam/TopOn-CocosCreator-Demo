package com.anythink.cocosjs.rewardvideo;

import android.app.Activity;
import android.text.TextUtils;

import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONObject;

public class RewardVideoHelper extends BaseHelper {

    private static final String TAG = RewardVideoHelper.class.getSimpleName();

    ATRewardVideoAd mRewardVideoAd;
    String mUnitId;
    Activity mActivity;

    boolean isReady = false;

    public RewardVideoHelper() {
        MsgTools.pirntMsg(TAG + " >>> " + this);
        mActivity = JSPluginUtil.getActivity();
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }


    private void initVideo(final String unitid) {
        MsgTools.pirntMsg("initVideo unitId >>> " + mUnitId);

        mRewardVideoAd = new ATRewardVideoAd(mActivity, unitid);
        mUnitId = unitid;

        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                MsgTools.pirntMsg("onRewardedVideoAdLoaded .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadedCallbackKey)
                                    + "('" + mUnitId + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdFailed(final AdError pAdError) {
                MsgTools.pirntMsg("onRewardedVideoAdFailed >> " + mUnitId + ", " + pAdError.printStackTrace());

                if (hasCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)
                                    + "('" + mUnitId + "','" + pAdError.printStackTrace() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayStart(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayStart .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayStartCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayEnd(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayEnd .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayEndCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayFailed(final AdError pAdError, final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayFailed .." + mUnitId + ", " + pAdError.printStackTrace());

                if (hasCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.PlayFailCallbackKey)
                                    + "('" + mUnitId + "','" + pAdError.printStackTrace() + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }


            @Override
            public void onRewardedVideoAdClosed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdClosed .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.CloseCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onRewardedVideoAdPlayClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onRewardedVideoAdPlayClicked .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.ClickCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onReward(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onReward .." + mUnitId);

                if (hasCallbackName(Const.RewardVideoCallback.RewardCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.RewardCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }
        });
    }


    public void loadRewardedVideo(final String unitId, final String settings) {
        MsgTools.pirntMsg("loadRewardedVideo >>> " + unitId + ", settings >>> " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mRewardVideoAd == null) {
                    initVideo(unitId);
                }

                if (!TextUtils.isEmpty(settings)) {
                    String userId = "";
                    String userData = "";
                    try {
                        JSONObject jsonObject = new JSONObject(settings);

                        if (jsonObject.has(Const.USER_ID)) {
                            userId = jsonObject.optString(Const.USER_ID);
                        }
                        if (jsonObject.has(Const.USER_DATA)) {
                            userData = jsonObject.optString(Const.USER_DATA);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mRewardVideoAd.setUserData(userId, userData);
                }

                mRewardVideoAd.load();
            }
        });
    }

    public void showVideo(final String scenario) {
        MsgTools.pirntMsg("showVideo >>> " + mUnitId + ", scenario >>> " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardVideoAd != null) {
                    isReady = false;
                    mRewardVideoAd.show(scenario);
                } else {
                    MsgTools.pirntMsg("showVideo error  ..you must call loadRewardVideo first " + mUnitId);
                    if (hasCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)) {
                        JSPluginUtil.runOnGLThread(new Runnable() {
                            @Override
                            public void run() {
                                Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.RewardVideoCallback.LoadFailCallbackKey)
                                        + "('" + mUnitId + "','" + "you must call loadRewardVideo first" + "');");
                            }
                        });
                    }
                }
            }
        });

    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("video isAdReady >>> " + mUnitId);

        try {
            if (mRewardVideoAd != null) {
                boolean isAdReady = mRewardVideoAd.isAdReady();
                MsgTools.pirntMsg("video isAdReady >>> " + mUnitId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("video isAdReady error  ..you must call loadRewardedVideo first " + mUnitId);
            }
            MsgTools.pirntMsg("video isAdReady >end>> " + mUnitId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("video isAdReady >Throwable>> " + e.getMessage());
            return isReady;
        }
        return isReady;
    }
}
