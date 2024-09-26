import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATRewardedVideoSDK} from "./AnyThinkAds/ATRewardedVideoTSSDK"

const { ccclass, property } = _decorator;

 
@ccclass('ATRewardSceneScript')
export class ATRewardSceneScript extends Component {

    static testMethod(){
        console.log("load reward111222");
        cc.log("load reward111222");
    }

    placementID() {
        if (cc.sys.os === cc.sys.OS.IOS) {
            return "b5b44a0f115321";
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return "b5b449fb3d89d7";
        }
    }

    start () {
        ATRewardedVideoSDK.setAdListener(this);
        // [3]
    }

    loadAD(){
        var setting = {};
        setting[ATRewardedVideoSDK.userIdKey] = "test_user_id";
        setting[ATRewardedVideoSDK.userDataKey] = "test_user_data";
        ATRewardedVideoSDK.loadRewardedVideo(this.placementID(), setting);
        
        printLog("loadAD", this.placementID(), "", "")
    }

    showAd(){
        ATRewardedVideoSDK.showAdInScenario(this.placementID(), "f5e54970dc84e6");

        printLog("showAd", this.placementID(), "", "")
    }

    //Callbacks
    

    isReady(){
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::checkReady()   " + (ATRewardedVideoSDK.hasAdReady(this.placementID()) ? "Ready" : "No"));

        var adStatusInfo = ATRewardedVideoSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::checkAdStatus()   " + adStatusInfo);
    }

    back(){
        cc.director.loadScene("MainScene");
    }

    //Callbacks
    onRewardedVideoAdLoaded (placementId) {
        printLog("onRewardedVideoAdLoaded", placementId, "", "")
    }

    onRewardedVideoAdFailed (placementId, errorInfo) {
        printLog("onRewardedVideoAdFailed", placementId, "", errorInfo)
    }

    onRewardedVideoAdPlayStart  (placementId, callbackInfo) {
        printLog("onRewardedVideoAdPlayStart", placementId, callbackInfo, "")
    }

    onRewardedVideoAdPlayEnd  (placementId, callbackInfo) {
        printLog("onRewardedVideoAdPlayEnd", placementId, callbackInfo, "")
    }

    onRewardedVideoAdPlayFailed (placementId, errorInfo, callbackInfo) {
        printLog("onRewardedVideoAdPlayFailed", placementId, callbackInfo, errorInfo)
    }

    onRewardedVideoAdClosed (placementId, callbackInfo) {
        printLog("onRewardedVideoAdClosed", placementId, callbackInfo, "")
    }

    onRewardedVideoAdPlayClicked  (placementId, callbackInfo) {
        printLog("onRewardedVideoAdPlayClicked", placementId, callbackInfo, "")
    }

    onReward (placementId, callbackInfo) {
        printLog("onReward", placementId, callbackInfo, "")
    }
    //Callbacks added v5.8.10
    onAdSourceBiddingAttempt (placementId, callbackInfo) {
        printLog("onAdSourceBiddingAttempt", placementId, callbackInfo, "")
    }
    
    onAdSourceBiddingFilled (placementId, callbackInfo) {
        printLog("onAdSourceBiddingFilled", placementId, "", "")
    }
    
    onAdSourceBiddingFail (placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceBiddingFail", placementId, callbackInfo, errorInfo)
    }

    onAdSourceAttempt (placementId, callbackInfo) {
        printLog("onAdSourceAttempt", placementId, callbackInfo, "")
    }

    onAdSourceLoadFilled (placementId, callbackInfo) {
        printLog("onAdSourceLoadFilled", placementId, callbackInfo, "")
    }
    
    onAdSourceLoadFail (placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceLoadFail", placementId, callbackInfo, errorInfo)
    }

    onRewardedVideoAdAgainPlayStart (placementId, callbackInfo) {
        printLog("onRewardedVideoAdAgainPlayStart", placementId, callbackInfo, "")
    }

    onRewardedVideoAdAgainPlayEnd (placementId, callbackInfo) {
        printLog("onRewardedVideoAdAgainPlayEnd", placementId, callbackInfo, "")
    }

    onRewardedVideoAdAgainPlayFailed (placementId, errorInfo, callbackInfo) {
        printLog("onRewardedVideoAdAgainPlayFailed", placementId, callbackInfo, errorInfo)
    }

    onRewardedVideoAdAgainPlayClicked (placementId, callbackInfo) {
        printLog("onRewardedVideoAdAgainPlayClicked", placementId, callbackInfo, "")
    }

    onAgainReward(placementId, callbackInfo) {
        printLog("onAgainReward", placementId, callbackInfo, "")
    }
}


window["ATRewardSceneScript"] = ATRewardedVideoSDK;

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkRewardedVideoDemo", methodName, placementId, callbackInfo, errorInfo)
}
