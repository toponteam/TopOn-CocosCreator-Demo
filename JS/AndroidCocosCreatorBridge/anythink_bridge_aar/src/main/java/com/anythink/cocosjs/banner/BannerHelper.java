package com.anythink.cocosjs.banner;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BannerHelper extends BaseHelper {

    private final String TAG = getClass().getSimpleName();
    Activity mActivity;
    String mPlacementId;
    boolean isReady;

    ATBannerView mBannerView;

    public BannerHelper() {
        MsgTools.pirntMsg(TAG + ": " + this);
        mActivity = JSPluginUtil.getActivity();
        mPlacementId = "";
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }

    public void initBanner(String placementId) {
        mPlacementId = placementId;
        MsgTools.pirntMsg("initBanner: " + placementId);

        mBannerView = new ATBannerView(mActivity);
        mBannerView.setPlacementId(mPlacementId);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                MsgTools.pirntMsg("onBannerLoaded: " + mPlacementId);

                if (hasCallbackName(Const.BannerCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.LoadedCallbackKey)
                                    + "('" + mPlacementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerFailed(final AdError adError) {
                MsgTools.pirntMsg("onBannerFailed: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.BannerCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.LoadFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerClicked: " + mPlacementId);

                if (hasCallbackName(Const.BannerCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.ClickCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerShow(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerShow: " + mPlacementId);

                if (hasCallbackName(Const.BannerCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.ShowCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerClose(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerClose: " + mPlacementId);

                if (hasCallbackName(Const.BannerCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.CloseCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerAutoRefreshed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerAutoRefreshed: " + mPlacementId);

                if (hasCallbackName(Const.BannerCallback.RefreshCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.RefreshCallbackKey)
                                    + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerAutoRefreshFail(final AdError adError) {
                MsgTools.pirntMsg("onBannerAutoRefreshFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.BannerCallback.RefreshFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.RefreshFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });
    }

    public void loadBanner(final String placementId, final String settings) {
        MsgTools.pirntMsg("loadBanner: " + placementId + ", settings - " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mBannerView == null) {
                    initBanner(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {//针对 穿山甲第一次banner尺寸不对
                    try {
                        JSONObject jsonObject = new JSONObject(settings);
                        int width = 0;
                        int height = 0;
                        if (jsonObject.has(Const.WIDTH)) {
                            width = jsonObject.optInt(Const.WIDTH);
                        }
                        if (jsonObject.has(Const.HEIGHT)) {
                            height = jsonObject.optInt(Const.HEIGHT);
                        }

                        if (mBannerView != null) {
                            MsgTools.pirntMsg("loadBanner, width: " + width + ", height: " + height);
                            if (mBannerView.getLayoutParams() == null) {
                                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
                                mBannerView.setLayoutParams(lp);
                            } else {
                                mBannerView.getLayoutParams().width = width;
                                mBannerView.getLayoutParams().height = height;
                            }
                        }

                        Map<String, Object> localExtra = new HashMap<>();
                        fillMapFromJsonObject(localExtra, jsonObject);

                        mBannerView.setLocalExtra(localExtra);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                mBannerView.loadAd();
            }
        });
    }

    public void showBannerWithRect(String rectJson, final String scenario) {
        MsgTools.pirntMsg("showBannerWithRect: " + mPlacementId + ", rect >>>" + rectJson + ", scenario: " + scenario);

        if (!TextUtils.isEmpty(rectJson)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(rectJson);

                int x = 0, y = 0, width = 0, height = 0;
                if (jsonObject.has(Const.X)) {
                    x = jsonObject.optInt(Const.X);
                }
                if (jsonObject.has(Const.Y)) {
                    y = jsonObject.optInt(Const.Y);
                }
                if (jsonObject.has(Const.WIDTH)) {
                    width = jsonObject.optInt(Const.WIDTH);
                }
                if (jsonObject.has(Const.HEIGHT)) {
                    height = jsonObject.optInt(Const.HEIGHT);
                }

                final int finalWidth = width;
                final int finalHeight = height;
                final int finalX = x;
                final int finalY = y;
                JSPluginUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBannerView != null) {
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(finalWidth, finalHeight);
                            layoutParams.leftMargin = finalX;
                            layoutParams.topMargin = finalY;
                            if (mBannerView.getParent() != null) {
                                ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                            }

                            if (!TextUtils.isEmpty(scenario)) {
                                mBannerView.setScenario(scenario);
                            }

                            mActivity.addContentView(mBannerView, layoutParams);
                        } else {
                            MsgTools.pirntMsg("showBannerWithRect error  ..you must call loadBanner first, placementId >>>  " + mPlacementId);
                        }
                    }
                });

            } catch (Exception e) {
                MsgTools.pirntMsg("showBannerWithRect error: " + e.getMessage());
            }
        } else {
            MsgTools.pirntMsg("showBannerWithRect error without rect, placementId: " + mPlacementId);
        }

    }


    public void showBannerWithPosition(final String position, final String scenario) {
        MsgTools.pirntMsg("showBannerWithPostion: " + mPlacementId + ", position: " + position + ", scenario: " + scenario);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    int width = 0;
                    int height = 0;
                    if (mBannerView.getLayoutParams() != null) {
                        width = mBannerView.getLayoutParams().width;
                        height = mBannerView.getLayoutParams().height;
                    }
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                    if (Const.BANNER_POSITION_TOP.equals(position)) {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                    } else {
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                    }
                    if (mBannerView.getParent() != null) {
                        ((ViewGroup) mBannerView.getParent()).removeView(mBannerView);
                    }

                    if (!TextUtils.isEmpty(scenario)) {
                        mBannerView.setScenario(scenario);
                    }

                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("showBannerWithPostion error  ..you must call loadBanner first, placementId: " + mPlacementId);
                }

            }
        });
    }

    public void reshowBanner() {
        MsgTools.pirntMsg("reshowBanner: " + mPlacementId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.pirntMsg("reshowBanner error  ..you must call loadBanner first, placementId: " + mPlacementId);
                }
            }
        });
    }

    public void hideBanner() {
        MsgTools.pirntMsg("hideBanner: " + mPlacementId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.pirntMsg("hideBanner error  ..you must call loadBanner first, placementId: " + mPlacementId);
                }

            }
        });
    }

    public void removeBanner() {
        MsgTools.pirntMsg("removeBanner: " + mPlacementId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.pirntMsg("removeBanner2 placementId: " + mPlacementId);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.pirntMsg("removeBanner3 >>> no banner need to be removed, placementId: " + mPlacementId);
                }
            }
        });
    }

    public boolean isAdReady() {
        if (mBannerView != null) {
            ATAdStatusInfo atAdStatusInfo = mBannerView.checkAdStatus();
            if (atAdStatusInfo != null) {
                boolean isReady = atAdStatusInfo.isReady();
                MsgTools.pirntMsg("banner isAdReady: " + mPlacementId + "：" + isReady);
            }
        }
        return isReady;
    }

    public String checkAdStatus() {
        MsgTools.pirntMsg("banner checkAdStatus: " + mPlacementId);

        if (mBannerView != null) {
            ATAdStatusInfo atAdStatusInfo = mBannerView.checkAdStatus();
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
