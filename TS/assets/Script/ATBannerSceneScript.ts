import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATBannerSDK} from "./AnyThinkAds/ATBannerTSSDK"

const { ccclass, property } = _decorator;

 
@ccclass('ATBannerSceneScript')
export class ATBannerSceneScript extends Component {


    placementID() {
        if (cc.sys.os === cc.sys.OS.IOS) {           
            return "b5bacaccb61c29";
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return "b5baca4f74c3d8";
        } return "b5baca53984692";
        
    }

    start () {
        ATBannerSDK.setAdListener(this);
        // [3]
    }

    loadAD(){
        ATJSSDK.printLog("AnyThinkBannerDemo::loadAd(" + this.placementID() + ")");
		var setting = {};
        setting[ATBannerSDK.kATBannerAdLoadingExtraBannerAdSizeStruct] = ATBannerSDK.createLoadAdSize(cc.view.getFrameSize().width, 150);
        setting[ATBannerSDK.kATBannerAdAdaptiveWidth] = cc.view.getFrameSize().width;
		// setting[ATBannerJSSDK.kATBannerAdAdaptiveOrientation] = ATBannerJSSDK.kATBannerAdAdaptiveOrientationCurrent;
		setting[ATBannerSDK.kATBannerAdAdaptiveOrientation] = ATBannerSDK.kATBannerAdAdaptiveOrientationPortrait;
		// setting[ATBannerJSSDK.kATBannerAdAdaptiveOrientation] = ATBannerJSSDK.kATBannerAdAdaptiveOrientationLandscape;
        ATBannerSDK.loadBanner(this.placementID(), setting);
    }

    showAd(){
      // ATBannerJSSDK.showAdInRectangle(this.placementID(), ATBannerJSSDK.createShowAdRect(0, 150, cc.view.getFrameSize().width, 150));
      ATJSSDK.printLog("AnyThinkBannerDemo::showAd(" + this.placementID() + ")");
      ATBannerSDK.showAdInPosition(this.placementID(), ATBannerSDK.kATBannerAdShowingPositionBottom);

      // ATBannerJSSDK.showAdInRectangleAndScenario(this.placementID(), ATBannerJSSDK.createShowAdRect(0, 150, cc.view.getFrameSize().width, 150), "f600e6039e152c");
      // ATBannerJSSDK.showAdInPositionAndScenario(this.placementID(), ATBannerJSSDK.kATBannerAdShowingPositionBottom, "f600e6039e152c");
    }

    removeAd() {
        printLog("removeAd", this.placementID(), "", "")
        ATBannerSDK.removeAd(this.placementID());
    }

    reShowAd() {
        printLog("reShowAd", this.placementID(), "", "")
        ATBannerSDK.reShowAd(this.placementID());
    }

    hideAd() {
        printLog("reShowAd", this.placementID(), "", "")
        ATBannerSDK.hideAd(this.placementID());
    }

    isReady(){
        var hasReady = ATBannerSDK.hasAdReady(this.placementID());
        ATJSSDK.printLog("AnyThinkBannerDemo::checkReady() " + (hasReady ? "Ready" : "NO"));

        var adStatusInfo = ATBannerSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkBannerDemo::checkAdStatus()   " + adStatusInfo);
    }

    back(){
        cc.director.loadScene("MainScene");
    }

    onBannerAdLoaded(placementId) {
        printLog("onBannerAdLoaded", placementId, "", "")
    }

    onBannerAdLoadFail(placementId, errorInfo) {
        printLog("onBannerAdLoadFail", placementId, "", errorInfo)
    }

    onBannerAdShow(placementId, callbackInfo) {
        printLog("onBannerAdShow", placementId, callbackInfo, "")
    }

    onBannerAdClick(placementId, callbackInfo) {
        printLog("onBannerAdClick", placementId, callbackInfo, "")
    }

    onBannerAdAutoRefresh(placementId, callbackInfo) {
        printLog("onBannerAdAutoRefresh", placementId, callbackInfo, "")
    }

    onBannerAdAutoRefreshFail(placementId, errorInfo) {
        printLog("onBannerAdAutoRefreshFail", placementId, "", errorInfo)
    }

    onBannerAdCloseButtonTapped(placementId, callbackInfo) {
        printLog("onBannerAdCloseButtonTapped", placementId, callbackInfo, "")
    }
    //added v5.8.10
    onAdSourceBiddingAttempt(placementId, callbackInfo) {
        printLog("onAdSourceBiddingAttempt", placementId, callbackInfo, "")
    }

    onAdSourceBiddingFilled(placementId, callbackInfo) {
        printLog("onAdSourceBiddingFilled", placementId, callbackInfo, "")
    }

    onAdSourceBiddingFail(placementId, errorInfo, callbackInfo)  {
        printLog("onAdSourceBiddingFail", placementId, callbackInfo, errorInfo)
    }

    onAdSourceAttemp(placementId, callbackInfo) {
        printLog("onAdSourceAttemp", placementId, callbackInfo, "")
    }

    onAdSourceLoadFilled(placementId, callbackInfo) {
        printLog("onAdSourceLoadFilled", placementId, callbackInfo, "")
    }

    onAdSourceLoadFail(placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceLoadFail", placementId, callbackInfo, errorInfo)
    }
}

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkBannerDemo", methodName, placementId, callbackInfo, errorInfo)
}


