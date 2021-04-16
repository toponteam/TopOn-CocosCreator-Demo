require("./AnyThinkAds/ATBannerJSSDK")

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
            return "b5bacaccb61c29";
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            return "b5baca4f74c3d8";
        }
    },

    // use this for initialization
    onLoad: function () {
       ATBannerJSSDK.setAdListener(this);
    },

    // called every frame
    update: function (dt) {

    },

    back: function () {
        cc.director.loadScene("AnyThinkDemoScene");
       
    },

    loadAd : function () {
        ATJSSDK.printLog("AnyThinkBannerDemo::loadAd(" + this.placementID() + ")");
		var setting = {};
        setting[ATBannerJSSDK.kATBannerAdLoadingExtraBannerAdSizeStruct] = ATBannerJSSDK.createLoadAdSize(cc.view.getFrameSize().width, 150);
        setting[ATBannerJSSDK.kATBannerAdAdaptiveWidth] = cc.view.getFrameSize().width;
		// setting[ATBannerJSSDK.kATBannerAdAdaptiveOrientation] = ATBannerJSSDK.kATBannerAdAdaptiveOrientationCurrent;
		setting[ATBannerJSSDK.kATBannerAdAdaptiveOrientation] = ATBannerJSSDK.kATBannerAdAdaptiveOrientationPortrait;
		// setting[ATBannerJSSDK.kATBannerAdAdaptiveOrientation] = ATBannerJSSDK.kATBannerAdAdaptiveOrientationLandscape;
        ATBannerJSSDK.loadBanner(this.placementID(), setting);
    },

    showAd : function () {
        // ATBannerJSSDK.showAdInRectangle(this.placementID(), ATBannerJSSDK.createShowAdRect(0, 150, cc.view.getFrameSize().width, 150));
        ATBannerJSSDK.showAdInPosition(this.placementID(), ATBannerJSSDK.kATBannerAdShowingPositionBottom);

        // ATBannerJSSDK.showAdInRectangleAndScenario(this.placementID(), ATBannerJSSDK.createShowAdRect(0, 150, cc.view.getFrameSize().width, 150), "f600e6039e152c");
        // ATBannerJSSDK.showAdInPositionAndScenario(this.placementID(), ATBannerJSSDK.kATBannerAdShowingPositionBottom, "f600e6039e152c");
    },

    removeAd : function () {
        ATBannerJSSDK.removeAd(this.placementID());
    },

    reShowAd : function () {
        ATBannerJSSDK.reShowAd(this.placementID());
    },

    hideAd : function() {
        ATBannerJSSDK.hideAd(this.placementID());
    },

    checkReady : function () {
        var hasReady = ATBannerJSSDK.hasAdReady(this.placementID());
        ATJSSDK.printLog("AnyThinkBannerDemo::checkReady() " + (hasReady ? "Ready" : "NO"));

        var adStatusInfo = ATBannerJSSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkBannerDemo::checkAdStatus()   " + adStatusInfo);

    },

    onBannerAdLoaded : function (placementId) {
         ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdLoaded(" + placementId + ")");
    },

    onBannerAdLoadFail : function(placementId, errorInfo) {
        ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdLoadFail(" + placementId + ", " + errorInfo + ")");   
    },

    onBannerAdShow : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdShow(" + placementId  + ", " + callbackInfo + ")");
    },

    onBannerAdClick : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdClick(" + placementId  + ", " + callbackInfo + ")");
    },

    onBannerAdAutoRefresh : function(placementId, callbackInfo) {
       ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdAutoRefresh(" + placementId  + ", " + callbackInfo + ")");
    },

    onBannerAdAutoRefreshFail : function(placementId, errorInfo) {
        ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdAutoRefreshFail(" + placementId + ", " + errorInfo + ")");   
    },

    onBannerAdCloseButtonTapped : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkBannerDemo::onBannerAdCloseButtonTapped(" + placementId  + ", " + callbackInfo + ")");
    }    

   
});
