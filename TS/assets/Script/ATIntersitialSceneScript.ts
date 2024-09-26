import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATInterstitialSDK} from "./AnyThinkAds/ATIntersitialTSSDK"

const { ccclass, property } = _decorator;

 
@ccclass('ATIntersitialSceneScript')
export class ATIntersitialSceneScript extends Component {


    placementID() {
        if (cc.sys.os === cc.sys.OS.IOS) {           
            return "b5bacad26a752a";
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return "b5baca53984692";
        }
    }

    start () {
        ATInterstitialSDK.setAdListener(this);
        // [3]
    }

    loadAD(){
        var setting = {};
        // setting[ATInterstitialSDK.userIdKey] = "test_user_id";
        // setting[ATInterstitialSDK.userDataKey] = "test_user_data";
        ATInterstitialSDK.loadInterstitial(this.placementID(), setting);

        printLog("loadAD", this.placementID(), "", "");
    }

    showAd(){
        ATInterstitialSDK.showAdInScenario(this.placementID(), "f5e54970dc84e6");

        printLog("showAd", this.placementID(), "", "");
    }

    isReady(){
        ATJSSDK.printLog("AnyThinkInterisitalDemo::checkReady()   " + (ATInterstitialSDK.hasAdReady(this.placementID()) ? "Ready" : "No"));

        var adStatusInfo = ATInterstitialSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkInterisitalDemo::checkAdStatus()   " + adStatusInfo);
    }

    back(){
        cc.director.loadScene("MainScene");
    }
    
    //Callbacks
    onInterstitialAdLoaded(placementId) {
        printLog("onInterstitialAdLoaded", placementId, "", "");
    }

    onInterstitialAdLoadFail(placementId, errorInfo) {
        printLog("onInterstitialAdLoadFail", placementId, "", errorInfo);
    }

    onInterstitialAdShow(placementId, callbackInfo) {
        printLog("onInterstitialAdShow", placementId, callbackInfo, "");
    }

    onInterstitialAdStartPlayingVideo(placementId, callbackInfo) {
        printLog("onInterstitialAdStartPlayingVideo", placementId, callbackInfo, "");
    }

    onInterstitialAdEndPlayingVideo(placementId, callbackInfo){
        printLog("onInterstitialAdEndPlayingVideo", placementId, callbackInfo, "");
    }

    onInterstitialAdFailedToPlayVideo(placementId, errorInfo) {
        printLog("onInterstitialAdFailedToPlayVideo", placementId, "", errorInfo);
    }

    onInterstitialAdFailedToShow(placementId, errorInfo, callbackInfo) {
        printLog("onInterstitialAdFailedToShow", placementId, callbackInfo, errorInfo);
    }

    onInterstitialAdClose (placementId, callbackInfo) {
        printLog("onInterstitialAdClose", placementId, callbackInfo, "");
    }

    onInterstitialAdClick (placementId, callbackInfo) {
        printLog("onInterstitialAdClick", placementId, callbackInfo, "");
    }
    //added v5.8.10
    onAdSourceBiddingAttempt (placementId, callbackInfo) {
        printLog("onAdSourceBiddingAttempt", placementId, callbackInfo, "");
    }

    onAdSourceBiddingFilled (placementId, callbackInfo) {
        printLog("onAdSourceBiddingFilled", placementId, callbackInfo, "");
    }

    onAdSourceBiddingFail(placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceBiddingFail", placementId, callbackInfo, errorInfo);
    }

    onAdSourceAttemp (placementId, callbackInfo) {
        printLog("onAdSourceAttemp", placementId, callbackInfo, "");
    }

    onAdSourceLoadFilled(placementId, callbackInfo) {
        printLog("onAdSourceLoadFilled", placementId, callbackInfo, "");
    }

    onAdSourceLoadFail(placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceLoadFail", placementId, callbackInfo, errorInfo);
    }
}

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkInterisitalDemo", methodName, placementId, callbackInfo, errorInfo)
}

