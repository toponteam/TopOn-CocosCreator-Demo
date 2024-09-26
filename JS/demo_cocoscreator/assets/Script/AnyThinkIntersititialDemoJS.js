require("./AnyThinkAds/ATInterstitialJSSDK")


cc.Class({
    extends: cc.Component,

    properties: {
        label: {
            default: null,
            type: cc.Label
        }
    },

    placementID: function() {
        if (cc.sys.os === cc.sys.OS_IOS) {           
            return "b5bacad26a752a";
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            return "b5baca53984692";
        }
    },
    // use this for initialization
    onLoad: function () {
        ATInterstitialJSSDK.setAdListener(this);
    },

    // called every frame
    update: function (dt) {

    },

    back: function () {
        cc.director.loadScene("AnyThinkDemoScene");
       
    },

    loadAd : function () {
		var setting = {};
        setting[ATInterstitialJSSDK.UseRewardedVideoAsInterstitial] = true;
        ATInterstitialJSSDK.loadInterstitial(this.placementID(), setting);
    },

    showAd : function () {
        // ATInterstitialJSSDK.showAd(this.placementID());
        ATInterstitialJSSDK.showAdInScenario(this.placementID(), "f5e549727efc49");
    },

    checkReady : function () {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::checkReady()   " + (ATInterstitialJSSDK.hasAdReady(this.placementID()) ? "Ready" : "No"));

        var adStatusInfo = ATInterstitialJSSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkInterstitialDemo::checkAdStatus()   " + adStatusInfo);

    },

    onInterstitialAdLoaded : function (placementId) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdLoaded(" + placementId + ")");
    },

    onInterstitialAdLoadFail : function(placementId, errorInfo) {
         ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdLoadFail(" + placementId + ", " + errorInfo + ")");   
    },

    onInterstitialAdShow : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdShow("  + placementId + ", " + callbackInfo + ")");
    },

    onInterstitialAdStartPlayingVideo : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdStartPlayingVideo("  + placementId + ", " + callbackInfo + ")");
    },

    onInterstitialAdEndPlayingVideo : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdEndPlayingVideo("  + placementId + ", " + callbackInfo + ")");
    },

    onInterstitialAdFailedToPlayVideo : function(placementId, errorInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdFailedToPlayVideo(" + placementId + ", " + errorInfo + ")");
    },

    onInterstitialAdFailedToShow : function(placementId) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdFailedToShow(" + placementId + ")");
    },

    onInterstitialAdClose : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdClose("  + placementId + ", " + callbackInfo + ")");
    },

    onInterstitialAdClick : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkInterstitialDemo::onInterstitialAdClick("  + placementId + ", " + callbackInfo + ")");
    }
   
});
