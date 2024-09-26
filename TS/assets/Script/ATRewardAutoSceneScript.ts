import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATRewardedVideoAutoAdSDK} from "./AnyThinkAds/ATRewardedAutoVideoTSSDK"

const { ccclass, property } = _decorator;


@ccclass('ATRewardAutoSceneScript')
export class ATRewardAutoSceneScript extends Component {

    private placementId: string = null;
    placementIDs() {
        if (cc.sys.os === cc.sys.OS.IOS) {
            return ["b5b44a0f115321"];
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return ["b5b728e7a08cd4"];
        }
    }

    getPlacementIDString() {
        return this.placementIDs().toLocaleString();
    }

    // use this for initialization
    onLoad(){
        this.placementId = this.placementIDs()[0];
        ATRewardedVideoAutoAdSDK.setAdListener(this);
    }

    // called every frame
    
    back() {
        cc.director.loadScene("MainScene");

    }
    // use this for start placement auto load
    addAutoLoadPlacementIds () {
        ATRewardedVideoAutoAdSDK.addPlacementIds(this.placementIDs());

        printLog("addAutoLoadPlacementIds", this.getPlacementIDString(), "", "")
    }

    removeAutoLoadPlacementId () {
        ATRewardedVideoAutoAdSDK.removePlacementId(this.placementIDs());

        printLog("removeAutoLoadPlacementId", this.getPlacementIDString(), "", "")
    }
    // if need put extradata to s2s,please set extra data before auto load
    setAdExtraData () {
        var setting = {};
        setting[ATRewardedVideoAutoAdSDK.userIdKey] = "test_user_id";
        setting[ATRewardedVideoAutoAdSDK.userDataKey] = "test_user_data";

        ATRewardedVideoAutoAdSDK.setAdExtraData(this.placementId, setting);
    }

    showAd() {
        // ATRewardedVideoAutoAdSDK.showAd(this.placementID());
        ATRewardedVideoAutoAdSDK.showAdInScenario(this.placementId, "f5e54970dc84e6");

        printLog("showAd", this.getPlacementIDString(), "", "")
    }

    entryAdScenario() {
        ATRewardedVideoAutoAdSDK.entryAdScenario(this.placementId, "f5e54970dc84e6");

        printLog("entryAdScenario", this.getPlacementIDString(), "", "")
    }

    checkReady() {
        ATJSSDK.printLog("AnyThinkRewardedVideoAutoDemo::checkReady()   " + (ATRewardedVideoAutoAdSDK.hasAdReady(this.placementId) ? "Ready" : "No"));

        var adStatusInfo = ATRewardedVideoAutoAdSDK.checkAdStatus(this.placementId);
        ATJSSDK.printLog("AnyThinkRewardedVideoAutoDemo::checkAdStatus()   " + adStatusInfo);

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
        printLog("onRewardedVideoAdPlayClicked", placementId, "", "")
    }

    onReward (placementId, callbackInfo) {
        printLog("onReward", placementId, callbackInfo, "")
    }
    //Callbacks added v5.8.10
    onAdSourceBiddingAttempt (placementId, callbackInfo) {
        printLog("onAdSourceBiddingAttempt", placementId, callbackInfo, "")
    }
    
    onAdSourceBiddingFilled (placementId, callbackInfo) {
        printLog("onAdSourceBiddingFilled", placementId, callbackInfo, "")
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

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkRewardedVideoAutoDemo", methodName, placementId, callbackInfo, errorInfo)
}

