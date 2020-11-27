require("./AnyThinkAds/ATRewardedVideoJSSDK");
require("./AnyThinkAds/ATJSSDK");

cc.Class({
    extends: cc.Component,

    properties: {
        label: {
            default: null,
            type: cc.Label
        },
        
    },

    placementID: function() {
        if (cc.sys.os === cc.sys.OS_IOS) {           
            return "b5b44a0f115321";
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            return "b5b449fb3d89d7";
        }
    },

    // use this for initialization
    onLoad: function () {
        ATRewardedVideoJSSDK.setAdListener(this);
    },

    // called every frame
    update: function (dt) {

    },

     back: function () {
        cc.director.loadScene("AnyThinkDemoScene");
       
    },

    loadAd : function () {
        var setting = {};
        setting[ATRewardedVideoJSSDK.userIdKey] = "test_user_id";
        setting[ATRewardedVideoJSSDK.userDataKey] = "test_user_data";
        ATRewardedVideoJSSDK.loadRewardedVideo(this.placementID(), setting);
    },

    showAd : function () {
        // ATRewardedVideoJSSDK.showAd(this.placementID());
        ATRewardedVideoJSSDK.showAdInScenario(this.placementID(), "f5e54970dc84e6");
    },

    checkReady : function () {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::checkReady()   " + (ATRewardedVideoJSSDK.hasAdReady(this.placementID()) ? "Ready" : "No"));

        var adStatusInfo = ATRewardedVideoJSSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::checkAdStatus()   " + adStatusInfo);

    },

    //Callbacks
    onRewardedVideoAdLoaded:function(placementId) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdLoaded(" + placementId + ")");
    },

    onRewardedVideoAdFailed : function(placementId, errorInfo) {
      ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdFailed(" + placementId + ", " + errorInfo + ")");
    },

    onRewardedVideoAdPlayStart : function(placementId, callbackInfo) {
       ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayStart(" + placementId + ", " + callbackInfo + ")");
    },

    onRewardedVideoAdPlayEnd : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayEnd(" + placementId + ", " + callbackInfo + ")");
    },

    onRewardedVideoAdPlayFailed : function(placementId, errorInfo, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
    },

    onRewardedVideoAdClosed : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdClosed(" + placementId + ", " + callbackInfo + ")");
    },

    onRewardedVideoAdPlayClicked : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onRewardedVideoAdPlayClicked(" + placementId + ", " + callbackInfo + ")");
    },

    onReward : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkRewardedVideoDemo::onReward(" + placementId + ", " + callbackInfo + ")");
    }
   
});
