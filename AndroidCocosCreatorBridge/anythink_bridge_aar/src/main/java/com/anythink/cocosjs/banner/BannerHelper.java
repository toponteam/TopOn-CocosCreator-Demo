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
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;

import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerHelper extends BaseHelper {

    private final String TAG = getClass().getSimpleName();
    Activity mActivity;
    String mUnitId;
    boolean isReady;

    ATBannerView mBannerView;

    public BannerHelper() {
        MsgTools.pirntMsg(TAG + " >>> " + this);
        mActivity = JSPluginUtil.getActivity();
        mUnitId = "";
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }

    public void initBanner(String unitId) {
        MsgTools.pirntMsg("initBanner >>> " + unitId);

        mUnitId = unitId;

        mBannerView = new ATBannerView(mActivity);
        mBannerView.setUnitId(mUnitId);
        mBannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {
                MsgTools.pirntMsg("onBannerLoaded .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.LoadedCallbackKey)
                                    + "('" + mUnitId + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerFailed(final AdError adError) {
                MsgTools.pirntMsg("onBannerFailed >> " + mUnitId + ", " + adError.printStackTrace());

                if (hasCallbackName(Const.BannerCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.LoadFailCallbackKey)
                                    + "('" + mUnitId + "','" + adError.printStackTrace() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerClicked(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerClicked .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.ClickCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.ClickCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerShow(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerShow .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.ShowCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.ShowCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerClose(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerClose .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.CloseCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.CloseCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerAutoRefreshed(final ATAdInfo adInfo) {
                MsgTools.pirntMsg("onBannerAutoRefreshed .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.RefreshCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = true;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.RefreshCallbackKey)
                                    + "('" + mUnitId + "','" + adInfo.toString() + "');");
                        }
                    });
                }
            }

            @Override
            public void onBannerAutoRefreshFail(final AdError adError) {
                MsgTools.pirntMsg("onBannerAutoRefreshFail .." + mUnitId);

                if (hasCallbackName(Const.BannerCallback.RefreshFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            isReady = false;
                            Cocos2dxJavascriptJavaBridge.evalString(getCallbackName(Const.BannerCallback.RefreshFailCallbackKey)
                                    + "('" + mUnitId + "','" + adError.printStackTrace() + "');");
                        }
                    });
                }
            }
        });
    }

    public void loadBanner(final String unitId, final String settings) {
        MsgTools.pirntMsg("loadBanner >>> " + unitId + ", settings - " + settings);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mBannerView == null) {
                    initBanner(unitId);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                mBannerView.loadAd();
            }
        });
    }

    public void showBannerWithRect(String rectJson) {
        MsgTools.pirntMsg("showBannerWithRect >>> " + mUnitId + ", rect >>>" + rectJson);

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
                            mActivity.addContentView(mBannerView, layoutParams);
                        } else {
                            MsgTools.pirntMsg("showBannerWithRect error  ..you must call loadBanner first, unitId >>>  " + mUnitId);
                        }
                    }
                });

            } catch (Exception e) {
                MsgTools.pirntMsg("showBannerWithRect error  .. " + e.getMessage());
            }
        } else {
            MsgTools.pirntMsg("showBannerWithRect error without rect, unitId >>> " + mUnitId);
        }

    }


    public void showBannerWithPostion(final String position) {
        MsgTools.pirntMsg("showBannerWithPostion >>> " + mUnitId + ", position >>> " + position);
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
                    mActivity.addContentView(mBannerView, layoutParams);
                } else {
                    MsgTools.pirntMsg("showBannerWithPostion error  ..you must call loadBanner first, unitId >>> " + mUnitId);
                }

            }
        });
    }

    public void reshowBanner() {
        MsgTools.pirntMsg("reshowBanner >>> " + mUnitId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.VISIBLE);
                } else {
                    MsgTools.pirntMsg("reshowBanner error  ..you must call loadBanner first, unitId >>> " + mUnitId);
                }
            }
        });
    }

    public void hideBanner() {
        MsgTools.pirntMsg("hideBanner >>> " + mUnitId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null) {
                    mBannerView.setVisibility(View.GONE);
                } else {
                    MsgTools.pirntMsg("hideBanner error  ..you must call loadBanner first, unitId >>> " + mUnitId);
                }

            }
        });
    }

    public void removeBanner() {
        MsgTools.pirntMsg("removeBanner >>> " + mUnitId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mBannerView != null && mBannerView.getParent() != null) {
                    MsgTools.pirntMsg("removeBanner2 unitId >>> " + mUnitId);
                    ViewParent viewParent = mBannerView.getParent();
                    ((ViewGroup) viewParent).removeView(mBannerView);
                } else {
                    MsgTools.pirntMsg("removeBanner3 >>> no banner need to be removed, unitId >>> " + mUnitId);
                }
            }
        });
    }

    public boolean isAdReady() {
        MsgTools.pirntMsg("banner isAdReady >>> " + mUnitId + ", " + isAdReady());
        return isReady;
    }

}
