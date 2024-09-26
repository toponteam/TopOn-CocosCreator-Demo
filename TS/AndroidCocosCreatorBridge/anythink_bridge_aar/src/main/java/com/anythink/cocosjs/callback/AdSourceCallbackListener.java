package com.anythink.cocosjs.callback;

import com.anythink.cocosjs.utils.BaseHelper;
import com.anythink.cocosjs.utils.CommonUtil;
import com.anythink.cocosjs.utils.JSPluginUtil;
import com.anythink.cocosjs.utils.MsgTools;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdSourceStatusListener;
import com.anythink.core.api.AdError;
import com.cocos.lib.CocosJavascriptJavaBridge;

public class AdSourceCallbackListener implements ATAdSourceStatusListener {

    BaseHelper mHelper;
    String mPlacementId;

    public AdSourceCallbackListener(BaseHelper helper, String placementId) {
        this.mHelper = helper;
        this.mPlacementId = placementId;
    }

    @Override
    public void onAdSourceBiddingAttempt(final ATAdInfo atAdInfo) {
        MsgTools.printMsg("onAdSourceBiddingAttempt: " + mPlacementId);

        if (mHelper.hasCallbackName(mHelper.getBiddingAttemptMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getBiddingAttemptMethodName())
                            + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }

    @Override
    public void onAdSourceBiddingFilled(final ATAdInfo atAdInfo) {
        MsgTools.printMsg("onAdSourceBiddingFilled: " + mPlacementId);

        if (mHelper.hasCallbackName(mHelper.getBiddingFilledMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getBiddingFilledMethodName())
                            + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }

    @Override
    public void onAdSourceBiddingFail(final ATAdInfo atAdInfo, final AdError adError) {
        MsgTools.printMsg("onAdSourceBiddingFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

        if (mHelper.hasCallbackName(mHelper.getBiddingFailMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getBiddingFailMethodName())
                            + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }

    @Override
    public void onAdSourceAttempt(final ATAdInfo atAdInfo) {
        MsgTools.printMsg("onAdSourceAttemp: " + mPlacementId);

        if (mHelper.hasCallbackName(mHelper.getAttempMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getAttempMethodName())
                            + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }

    @Override
    public void onAdSourceLoadFilled(final ATAdInfo atAdInfo) {
        MsgTools.printMsg("onAdSourceLoadFilled: " + mPlacementId);

        if (mHelper.hasCallbackName(mHelper.getLoadFilledMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getLoadFilledMethodName())
                            + "('" + mPlacementId + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }

    @Override
    public void onAdSourceLoadFail(final ATAdInfo atAdInfo, final AdError adError) {
        MsgTools.printMsg("onAdSourceLoadFail: " + mPlacementId + ", " + adError.getFullErrorInfo());

        if (mHelper.hasCallbackName(mHelper.getLoadFailMethodName())) {
            JSPluginUtil.runOnGLThread(new Runnable() {
                @Override
                public void run() {
                    CocosJavascriptJavaBridge.evalString(mHelper.getCallbackName(mHelper.getLoadFailMethodName())
                            + "('" + mPlacementId + "','" + CommonUtil.getErrorMsg(adError) + "','" + atAdInfo.toString() + "');");
                }
            });
        }
    }
}
