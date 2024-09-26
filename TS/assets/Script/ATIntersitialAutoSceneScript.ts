import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATInterstitialAutoAdSDK} from "./AnyThinkAds/ATIntersitialAutoTSSDK"

const { ccclass, property } = _decorator;

 
@ccclass('ATIntersitialAutoSceneScript')
export class ATIntersitialAutoSceneScript extends Component {

    private placementId: string = null;
    placementIDs(){
        if (cc.sys.os === cc.sys.OS.IOS) {
            return ["b5bacad26a752a"];
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return ["b5baca585a8fef"];
        }
    }

    // use this for initialization
    onLoad(){
        this.placementId = this.placementIDs()[0];
    }

    getPlacementIDString() {
        return this.placementIDs().toLocaleString();
    }

    start () {
        ATInterstitialAutoAdSDK.setAdListener(this);
        // [3]
    }

    loadAD(){
        var setting = {};
        setting[ATInterstitialAutoAdSDK.UseRewardedVideoAsInterstitial] = true;

        ATInterstitialAutoAdSDK.setAdExtraData(this.placementId, setting);
        
        ATInterstitialAutoAdSDK.addPlacementIds(this.placementIDs());

        printLog("loadAD", this.getPlacementIDString(), "", "");
    }

    showAd(){
        ATInterstitialAutoAdSDK.showAdInScenario(this.placementId, "f5e54970dc84e6");

        printLog("showAd", this.getPlacementIDString(), "", "");
    }


    isReady(){
        ATJSSDK.printLog("AnyThinkInterstitialAutoAdDemo::checkReady()   " + (ATInterstitialAutoAdSDK.hasAdReady(this.placementId) ? "Ready" : "No"));
        var adStatusInfo = ATInterstitialAutoAdSDK.checkAdStatus(this.placementId);

        
        ATJSSDK.printLog("AnyThinkInterstitialAutoAdDemo::checkAdStatus()   " + adStatusInfo);
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
        printLog("onAdSourceLoadFail", placementId, callbackInfo, "");
    }
}

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkInterstitialAutoAdDemo", methodName, placementId, callbackInfo, errorInfo)
}


