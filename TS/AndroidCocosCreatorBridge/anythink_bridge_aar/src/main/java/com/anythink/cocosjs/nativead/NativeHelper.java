package com.anythink.cocosjs.nativead;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.anythink.cocosjs.callback.AdSourceCallbackListener;
import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.Const;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdStatusInfo;
import com.anythink.core.api.AdError;
import com.anythink.nativead.api.ATNative;
import com.anythink.nativead.api.ATNativeAdView;
import com.anythink.nativead.api.ATNativeDislikeListener;
import com.anythink.nativead.api.ATNativeEventListener;
import com.anythink.nativead.api.ATNativeNetworkListener;
import com.anythink.nativead.api.ATNativePrepareExInfo;
import com.anythink.nativead.api.ATNativePrepareInfo;
import com.anythink.nativead.api.NativeAd;
import com.cocos.lib.CocosJavascriptJavaBridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NativeHelper extends BaseHelper {

    private final String TAG = getClass().getSimpleName();
    Activity mActivity;
    String mPlacementId;

    ATNative mATNative;
    ATNativeAdView mATNativeAdView;

    NativeAd mNativeAd;
    ViewInfo pViewInfo;

    static List<ViewInfo> currViewInfo = new ArrayList<>();

//    ImageView mDislikeView;

    ATNativePrepareInfo mNativePrepareInfo;

    public NativeHelper() {
        MsgTools.printMsg(TAG + ": " + this);
        mActivity = JSPluginUtil.getActivity();
        mPlacementId = "";
    }

    @Override
    public void setAdListener(final String callbackNameJson) {
        super.setAdListener(callbackNameJson);
    }

    private void initNative(String placementId) {
        mPlacementId = placementId;
        MsgTools.printMsg("initNative: " + placementId);

        mATNative = new ATNative(mActivity, placementId, new ATNativeNetworkListener() {
            @Override
            public void onNativeAdLoaded() {
                MsgTools.printMsg("onNativeAdLoaded: " + mPlacementId);

                if (hasCallbackName(Const.NativeCallback.LoadedCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.LoadedCallbackKey)
                                    + "('" + mPlacementId + "');");
                        }
                    });
                }
            }

            @Override
            public void onNativeAdLoadFail(final AdError adError) {
                MsgTools.printMsg("onNativeAdLoadFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

                if (hasCallbackName(Const.NativeCallback.LoadFailCallbackKey)) {
                    JSPluginUtil.runOnGLThread(new Runnable() {
                        @Override
                        public void run() {
                            CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.LoadFailCallbackKey)
                                    + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "');");
                        }
                    });
                }
            }
        });

        mATNative.setAdSourceStatusListener(new AdSourceCallbackListener(this, mPlacementId));

        mATNativeAdView = new ATNativeAdView(mActivity);
    }

    public void loadNative(final String placementId, final String settings) {
        MsgTools.printMsg("loadNative: " + placementId + ", settings: " + settings);

        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mATNative == null || !TextUtils.equals(mPlacementId, placementId)) {
                    initNative(placementId);
                }

                if (!TextUtils.isEmpty(settings)) {//设置LocalExtra
                    try {
                        JSONObject jsonObject = new JSONObject(settings);
                        int width = 0;
                        int height = 0;
                        Map<String, Object> localExtra = new HashMap<>();
                        if (jsonObject.has(Const.WIDTH)) {
                            width = jsonObject.optInt(Const.WIDTH);
                            localExtra.put("key_width", width);
                            localExtra.put("tt_image_width", width);
                            localExtra.put("mintegral_auto_render_native_width", width);
                        }
                        if (jsonObject.has(Const.HEIGHT)) {
                            height = jsonObject.optInt(Const.HEIGHT);
                            localExtra.put("key_height", height);
                            localExtra.put("tt_image_height", height);
                            localExtra.put("mintegral_auto_render_native_height", height);
                        }

                        MsgTools.printMsg("native setLocalExtra >>>  width: " + width + ", height: " + height);
                        fillMapFromJsonObject(localExtra, jsonObject);
                        mATNative.setLocalExtra(localExtra);

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }

                mATNative.makeAdRequest();
            }
        });
    }


    private ViewInfo parseViewInfo(String showConfig) {
        pViewInfo = new ViewInfo();

        if (TextUtils.isEmpty(showConfig)) {
            Log.e(TAG, "showConfig is null ,user defult");
            pViewInfo = ViewInfo.createDefualtView(mActivity);
        }

        try {
            JSONObject _jsonObject = new JSONObject(showConfig);

            if (_jsonObject.has(Const.parent)) {
                String tempjson = _jsonObject.getString(Const.parent);
                MsgTools.printMsg("parent----> " + tempjson);
                pViewInfo.rootView = pViewInfo.parseINFO(tempjson, "parent", 0, 0);
            }

            if (_jsonObject.has(Const.icon)) {
                String tempjson = _jsonObject.getString(Const.icon);
                MsgTools.printMsg("appIcon----> " + tempjson);
                pViewInfo.IconView = pViewInfo.parseINFO(tempjson, "appIcon", 0, 0);
            }

            if (_jsonObject.has(Const.mainImage)) {
                String tempjson = _jsonObject.getString(Const.mainImage);
                MsgTools.printMsg("mainImage----> " + tempjson);
                pViewInfo.imgMainView = pViewInfo.parseINFO(tempjson, "mainImage", 0, 0);

            }

            if (_jsonObject.has(Const.title)) {
                String tempjson = _jsonObject.getString(Const.title);
                MsgTools.printMsg("title----> " + tempjson);
                pViewInfo.titleView = pViewInfo.parseINFO(tempjson, "title", 0, 0);
            }

            if (_jsonObject.has(Const.desc)) {
                String tempjson = _jsonObject.getString(Const.desc);
                MsgTools.printMsg("desc----> " + tempjson);
                pViewInfo.descView = pViewInfo.parseINFO(tempjson, "desc", 0, 0);
            }

            if (_jsonObject.has(Const.adLogo)) {
                String tempjson = _jsonObject.getString(Const.adLogo);
                MsgTools.printMsg("adLogo----> " + tempjson);
                pViewInfo.adLogoView = pViewInfo.parseINFO(tempjson, "adLogo", 0, 0);
            }

            if (_jsonObject.has(Const.cta)) {
                String tempjson = _jsonObject.getString(Const.cta);
                MsgTools.printMsg("cta----> " + tempjson);
                pViewInfo.ctaView = pViewInfo.parseINFO(tempjson, "cta", 0, 0);
            }

            if (_jsonObject.has(Const.dislike)) {
                String tempjson = _jsonObject.getString(Const.dislike);
                MsgTools.printMsg("dislike----> " + tempjson);
                pViewInfo.dislikeView = pViewInfo.parseINFO(tempjson, "dislike", 0, 0);
            }
            //V6.2.37+
            if (_jsonObject.has(Const.elements)) {
                String tempjson = _jsonObject.getString(Const.elements);
                MsgTools.printMsg("elements----> " + tempjson);
                pViewInfo.elementsView = pViewInfo.parseINFO(tempjson, "elements", 0, 0);
            } else {
                ViewInfo.INFO defaultViewInfoForElements = pViewInfo.getDefaultViewInfoForElements(pViewInfo.rootView);
                MsgTools.printMsg("elements----> default config: " + defaultViewInfoForElements);
                pViewInfo.elementsView = defaultViewInfoForElements;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pViewInfo;
    }


    public void show(final String showConfig, final String scenario) {
        MsgTools.printMsg("native show: " + mPlacementId + ", config: " + showConfig + ", scenario: " + scenario);

        if (mATNative == null) {
            MsgTools.printMsg("native show error: mATNative = null");
            return;
        }

        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NativeAd nativeAd;
                if (!TextUtils.isEmpty(scenario)) {
                    nativeAd = mATNative.getNativeAd(scenario);
                } else {
                    nativeAd = mATNative.getNativeAd();
                }

                if (nativeAd != null) {
                    MsgTools.printMsg("nativeAd:" + nativeAd.toString());
                    pViewInfo = parseViewInfo(showConfig);
                    currViewInfo.add(pViewInfo);
                    mNativeAd = nativeAd;
                    nativeAd.setNativeEventListener(new ATNativeEventListener() {
                        @Override
                        public void onAdImpressed(ATNativeAdView view, final ATAdInfo adInfo) {
                            MsgTools.printMsg("onAdImpressed: " + mPlacementId);

                            if (hasCallbackName(Const.NativeCallback.ShowCallbackKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.ShowCallbackKey)
                                                + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onAdClicked(ATNativeAdView view, final ATAdInfo adInfo) {
                            MsgTools.printMsg("onAdClicked: " + mPlacementId);

                            if (hasCallbackName(Const.NativeCallback.ClickCallbackKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.ClickCallbackKey)
                                                + "('" + mPlacementId + "','" + adInfo.toString() + "');");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onAdVideoStart(ATNativeAdView view) {
                            MsgTools.printMsg("onAdVideoStart: " + mPlacementId);

                            if (hasCallbackName(Const.NativeCallback.VideoStartKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.VideoStartKey)
                                                + "('" + mPlacementId + "');");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onAdVideoEnd(ATNativeAdView view) {
                            MsgTools.printMsg("onAdVideoEnd: " + mPlacementId);

                            if (hasCallbackName(Const.NativeCallback.VideoEndKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.VideoEndKey)
                                                + "('" + mPlacementId + "');");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onAdVideoProgress(ATNativeAdView view, final int progress) {
                            if (hasCallbackName(Const.NativeCallback.VideoProgressKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.VideoProgressKey)
                                                + "('" + mPlacementId + "','" + progress + "');");
                                    }
                                });
                            }
                        }
                    });

                    nativeAd.setDislikeCallbackListener(new ATNativeDislikeListener() {
                        @Override
                        public void onAdCloseButtonClick(ATNativeAdView atNativeAdView, final ATAdInfo atAdInfo) {
                            MsgTools.printMsg("onAdCloseButtonClick: " + mPlacementId);

                            if (hasCallbackName(Const.NativeCallback.CloseCallbackKey)) {
                                JSPluginUtil.runOnGLThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.CloseCallbackKey)
                                                + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                                    }
                                });
                            }
                        }
                    });

                    mNativePrepareInfo = null;

                    try {
                        if (nativeAd.isNativeExpress()) {
                            nativeAd.renderAdContainer(mATNativeAdView, null);
                        } else {
                            SelfRenderViewUtil selfRenderViewUtil = new SelfRenderViewUtil(mActivity, pViewInfo, mNativeAd.getAdInfo().getNetworkFirmId());
                            mNativePrepareInfo = new ATNativePrepareExInfo();

                            View selfRenderView = selfRenderViewUtil.bindSelfRenderView(mNativeAd.getAdMaterial(), mNativePrepareInfo, pViewInfo);

                            mNativeAd.renderAdContainer(mATNativeAdView, selfRenderView);
                        }
                    } catch (Throwable e) {
                        Log.e(TAG, "Native render failed: " + e.getMessage());
                    }

                    mNativeAd.prepare(mATNativeAdView, mNativePrepareInfo);


//                    ATUnityRender atUnityRender = new ATUnityRender(mActivity, pViewInfo);
//                    try {
//                        if (pViewInfo.dislikeView != null) {
//                            initDislikeView(pViewInfo.dislikeView);
//
//                            atUnityRender.setDislikeView(mDislikeView);
//                        }
//
//                        nativeAd.renderAdView(mATNativeAdView, atUnityRender);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    //add dislike button
//                    if (pViewInfo.dislikeView != null && mDislikeView != null) {
//                        if (mDislikeView.getParent() != null) {
//                            ((ViewGroup) mDislikeView.getParent()).removeView(mDislikeView);
//                        }
//
//                        mATNativeAdView.addView(mDislikeView);
//                    }
//
//                    if (pViewInfo.adLogoView != null) {
//                        FrameLayout.LayoutParams adLogoLayoutParams = new FrameLayout.LayoutParams(pViewInfo.adLogoView.mWidth, pViewInfo.adLogoView.mHeight);
//                        adLogoLayoutParams.leftMargin = pViewInfo.adLogoView.mX;
//                        adLogoLayoutParams.topMargin = pViewInfo.adLogoView.mY;
//                        nativeAd.prepare(mATNativeAdView, atUnityRender.getClickViews(), adLogoLayoutParams);
//                    } else {
//                        nativeAd.prepare(mATNativeAdView, atUnityRender.getClickViews(), null);
//                    }

                    ViewInfo.addNativeAdView2Activity(mActivity, pViewInfo, mATNativeAdView);
                } else {
                    if (hasCallbackName(Const.NativeCallback.LoadFailCallbackKey)) {
                        JSPluginUtil.runOnGLThread(new Runnable() {
                            @Override
                            public void run() {
                                CocosJavascriptJavaBridge.evalString(getCallbackName(Const.NativeCallback.LoadFailCallbackKey)
                                        + "('" + mPlacementId + "','" + "showNative error, nativeAd = null" + "');");
                            }
                        });
                    }

                }
            }
        });
    }

//    private void initDislikeView(ViewInfo.INFO dislikeInfo) {
//        if (mDislikeView == null) {
//            mDislikeView = new ImageView(mActivity);
//            mDislikeView.setImageResource(CommonUtil.getResId(mActivity, "btn_close", "drawable"));
//        }
//
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dislikeInfo.mWidth, dislikeInfo.mHeight);
//        layoutParams.leftMargin = dislikeInfo.mX;
//        layoutParams.topMargin = dislikeInfo.mY;
//
//        if (!TextUtils.isEmpty(dislikeInfo.bgcolor)) {
//            mDislikeView.setBackgroundColor(Color.parseColor(dislikeInfo.bgcolor));
//        }
//
//        mDislikeView.setLayoutParams(layoutParams);
//    }


    public boolean isAdReady() {
        if (mATNative == null) {
            MsgTools.printMsg("isAdReady error  ..you must call initNative first " + mPlacementId);
            return false;
        }

        ATAdStatusInfo atAdStatusInfo = mATNative.checkAdStatus();
        if (atAdStatusInfo != null) {
            boolean isReady = atAdStatusInfo.isReady();
            MsgTools.printMsg("native isAdReady: " + mPlacementId + ", " + isReady);
            return isReady;
        }

        return false;
    }

    public void clean() {
        if (mNativeAd != null) {
            mNativeAd.destory();
        }
    }

    public void remove() {
        MsgTools.printMsg("native remove: " + mPlacementId);
        JSPluginUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (mATNativeAdView != null) {
                        ViewGroup _viewGroup = (ViewGroup) mATNativeAdView.getParent();
                        if (_viewGroup != null) {
                            _viewGroup.removeView(mATNativeAdView);

                        }
                    }

                } catch (Exception e) {
                    if (Const.DEBUG) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void onPause() {
        if (mNativeAd != null) {
            mNativeAd.onPause();
        }
    }

    public void onResume() {
        if (mNativeAd != null) {
            mNativeAd.onResume();
        }
    }

    public String checkAdStatus() {
        MsgTools.printMsg("native checkAdStatus: " + mPlacementId);

        if (mATNative != null) {
            ATAdStatusInfo atAdStatusInfo = mATNative.checkAdStatus();
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
        return Const.NativeCallback.AdSourceBiddingAttempt;
    }

    @Override
    public String getBiddingFilledMethodName() {
        return Const.NativeCallback.AdSourceBiddingFilled;
    }

    @Override
    public String getBiddingFailMethodName() {
        return Const.NativeCallback.AdSourceBiddingFail;
    }

    @Override
    public String getAttempMethodName() {
        return Const.NativeCallback.AdSourceAttemp;
    }

    @Override
    public String getLoadFilledMethodName() {
        return Const.NativeCallback.AdSourceLoadFilled;
    }

    @Override
    public String getLoadFailMethodName() {
        return Const.NativeCallback.AdSourceLoadFail;
    }
}
