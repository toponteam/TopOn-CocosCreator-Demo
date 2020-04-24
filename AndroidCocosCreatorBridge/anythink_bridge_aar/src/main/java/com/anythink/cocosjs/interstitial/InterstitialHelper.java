package com.anythink.cocosjs.interstitial;

import android.app.Activity;

import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

public class InterstitialHelper extends BaseHelper {

    private static final String TAG = InterstitialHelper.class.getSimpleName();

    ATInterstitial mInterstitialAd;
    String mUnitId;
    Activity mActivity;

    boolean isReady = false;

    public InterstitialHelper() {
        MsgTools.pirntMsg(TAG + " >>> " + this);
        mActivity = JSPluginUtil.getActivity();
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }


    private void initInterstitial(final String unitid) {
        MsgTools.pirntMsg("initInterstitial  >>> " + mUnitId);

        mInterstitialAd = new ATInterstitial(mActivity, unitid);
        mUnitId = unitid;

        mInterstitialAd.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {
                MsgTools.pirntMsg("onInterstitialAdLoaded .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadedCallbackKey)
                                    + "('" + mUnitId + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdLoadFail(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdLoadFail >> " + mUnitId + ", " + adError.printStackTrace());

                if (hasCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.LoadFailCallbackKey)
                                    + "('" + mUnitId + "','" + adError.printStackTrace() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClicked(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClicked .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ClickCallbackKey)
                                    + "('" + mUnitId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdShow(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdShow .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.ShowCallbackKey)
                                    + "('" + mUnitId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdClose(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdClose .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.CloseCallbackKey)
                                    + "('" + mUnitId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoStart(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoStart .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayStartCallbackKey)
                                    + "('" + mUnitId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoEnd(final ATAdInfo atAdInfo) {
                MsgTools.pirntMsg("onInterstitialAdVideoEnd .." + mUnitId);

                if (hasCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayEndCallbackKey)
                                    + "('" + mUnitId + "','" + atAdInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onInterstitialAdVideoError(final AdError adError) {
                MsgTools.pirntMsg("onInterstitialAdVideoError .." + mUnitId + ", " + adError.printStackTrace());

                if (hasCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.InterstitialCallback.PlayFailCallbackKey)
                                    + "('" + mUnitId + "','" + adError.printStackTrace() + "');");
                        }
                    });
                }
            }
        });
    }


    public void loadInterstitial(final String unitId) {
        MsgTools.pirntMsg("loadInterstitial >>> " + unitId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mInterstitialAd == null) {
                    initInterstitial(unitId);
                }

                mInterstitialAd.load();
            }
        });
    }

    public void showInterstitial(final String scenario) {
        MsgTools.pirntMsg("showInterstitial >>> " + mUnitId + ", scenario >>> " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    isReady = false;
                    mInterstitialAd.show(scenario);
                } else {
                    MsgTools.pirntMsg("showInterstitial error  ..you must call loadRewardVideo first, unitId" + mUnitId);
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
        MsgTools.pirntMsg("interstitial isAdReady >>> " + mUnitId);

        try {
            if (mInterstitialAd != null) {
                boolean isAdReady = mInterstitialAd.isAdReady();
                MsgTools.pirntMsg("interstitial isAdReady >>> " + mUnitId + ", " + isAdReady);
                return isAdReady;
            } else {
                MsgTools.pirntMsg("interstitial isAdReady error  ..you must call loadInterstitial first " + mUnitId);
            }
            MsgTools.pirntMsg("interstitial isAdReady >end>> " + mUnitId);
        } catch (Throwable e) {
            MsgTools.pirntMsg("interstitial isAdReady >Throwable>> " + e.getMessage());
            return isReady;
        }
        return isReady;
    }
}
