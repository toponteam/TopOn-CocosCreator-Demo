
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATRewardedVideoSDK} from "./AnyThinkAds/ATRewardedVideoTSSDK"

const { ccclass, property } = _decorator;

@ccclass('ATTestScript')
export class ATTestScript extends Component {

   

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
      
    }

    showAd(){
        ATRewardedVideoSDK.showAdInScenario(this.placementID(), "f5e54970dc84e6");
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
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdLoaded(" + placementId + ")");
    }

    onRewardedVideoAdFailed (placementId, errorInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdFailed(" + placementId + ", " + errorInfo + ")");
    }

    onRewardedVideoAdPlayStart  (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayStart(" + placementId + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdPlayEnd  (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayEnd(" + placementId + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdPlayFailed (placementId, errorInfo, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdClosed (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdClosed(" + placementId + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdPlayClicked  (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayClicked(" + placementId + ", " + callbackInfo + ")");
    }

    onReward (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onReward(" + placementId + ", " + callbackInfo + ")");
    }
    //Callbacks added v5.8.10
    onAdSourceBiddingAttempt (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceBiddingAttempt(" + placementId + ", " + callbackInfo + ")");
    }
    
    onAdSourceBiddingFilled (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceBiddingFilled(" + placementId + ", " + callbackInfo + ")");
    }
    
    onAdSourceBiddingFail (placementId, errorInfo, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceBiddingFail(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
    }

    onAdSourceAttempt (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceAttemp(" + placementId + ", " + callbackInfo + ")");
    }

    onAdSourceLoadFilled (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceLoadFilled(" + placementId + ", " + callbackInfo + ")");
    }
    
    onAdSourceLoadFail (placementId, errorInfo, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAdSourceLoadFail(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdAgainPlayStart (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdAgainPlayStart(" + placementId + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdAgainPlayEnd (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdAgainPlayEnd(" + placementId + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdAgainPlayFailed (placementId, errorInfo, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdAgainPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
    }

    onRewardedVideoAdAgainPlayClicked (placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdAgainPlayClicked(" + placementId + ", " + callbackInfo + ")");
    }

    onAgainReward(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onAgainReward(" + placementId + ", " + callbackInfo + ")");
    }
}


window["ATRewardSceneScript"] = ATRewardedVideoSDK;