require("./AnyThinkAds/ATNativeJSSDK")

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
            return "b5b0f5663c6e4a";
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            return "b5aa1fa2cae775";
        }
    },

    // use this for initialization
    onLoad: function () {
        ATNativeJSSDK.setAdListener(this);
    },

    // called every frame
    update: function (dt) {

    },

    back: function () {
        cc.director.loadScene("AnyThinkDemoScene");
       
    },

    loadAd : function () {
        ATNativeJSSDK.loadNative(this.placementID(), ATNativeJSSDK.createLoadAdSize(cc.view.getFrameSize().width, cc.view.getFrameSize().width * 4/5));
    },

    showAd : function () {
        var frameSize = cc.view.getFrameSize();
        var frameWidth = frameSize.width;
        var frameHeight = frameSize.height;
        var padding = frameSize.width / 35;

        var parentWidth = frameWidth;
        var parentHeight = frameWidth * 4/5;
        var appIconSize = frameWidth/7;


        var nativeAdViewProperty = new ATNativeJSSDK.AdViewProperty();
        nativeAdViewProperty.parent = nativeAdViewProperty.createItemViewProperty(0, frameHeight - parentHeight,parentWidth,parentHeight,"#ffffff","",0);

        nativeAdViewProperty.appIcon = nativeAdViewProperty.createItemViewProperty(0 ,parentHeight - appIconSize, appIconSize ,appIconSize ,"","",0);
        nativeAdViewProperty.cta = nativeAdViewProperty.createItemViewProperty(parentWidth-appIconSize*2 ,parentHeight - appIconSize ,appIconSize*2 ,appIconSize ,"#2095F1" ,"#ffffff" , appIconSize/3);

        nativeAdViewProperty.mainImage = nativeAdViewProperty.createItemViewProperty(padding ,padding, parentWidth-2*padding ,parentHeight - appIconSize - 2*padding,"#ffffff","#ffffff",14);
        
        nativeAdViewProperty.title = nativeAdViewProperty.createItemViewProperty(appIconSize + padding ,parentHeight - appIconSize , parentWidth - 3* appIconSize -2 * padding ,appIconSize/2 ,"" ,"#000000" , appIconSize/3);
        nativeAdViewProperty.desc = nativeAdViewProperty.createItemViewProperty(appIconSize + padding ,parentHeight - appIconSize/2 , parentWidth - 3* appIconSize -2 * padding ,appIconSize/2 ,"#ffffff" ,"#000000" , appIconSize/4);
        // nativeAdViewProperty.adLogo = nativeAdViewProperty.createItemViewProperty(0,0,0,0,"#ffffff","#ffffff",14);
        // nativeAdViewProperty.rating = nativeAdViewProperty.createItemViewProperty(0,0,0,0,"#ffffff","#ffffff",14);
        nativeAdViewProperty.dislike = nativeAdViewProperty.createItemViewProperty(parentWidth - appIconSize/2,0,appIconSize/2,appIconSize/2,"#00ffffff","#ffffff",14);

        ATNativeJSSDK.showAd(this.placementID(), nativeAdViewProperty);
        // ATNativeJSSDK.showAdInScenario(this.placementID(), nativeAdViewProperty, "f600e5f8b80c14");

    },

    removeAd : function () {
        ATNativeJSSDK.removeAd(this.placementID());
    },

    checkReady : function () {
        var hasReady = ATNativeJSSDK.hasAdReady(this.placementID());
        ATJSSDK.printLog("AnyThinkNativeDemo::checkReady() " + (hasReady ? "Ready" : "NO"));

        var adStatusInfo = ATNativeJSSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkNativeDemo::checkAdStatus()   " + adStatusInfo);
    },

    onNativeAdLoaded : function (placementId) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdLoaded(" + placementId + ")");
    },

    onNativeAdLoadFail : function(placementId, errorInfo) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdLoadFail(" + placementId + ", " + errorInfo + ")");   
    },

    onNativeAdShow : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdShow(" + placementId + ", " + callbackInfo + ")");
    },

    onNativeAdClick : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdClick(" + placementId + ", " + callbackInfo + ")");
    },

    onNativeAdVideoStart : function(placementId) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdVideoStart(" + placementId + ")");
    },

    onNativeAdVideoEnd : function(placementId) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdVideoEnd(" + placementId + ")");
    },

    onNativeAdCloseButtonTapped : function(placementId, callbackInfo) {
        ATJSSDK.printLog("AnyThinkNativeDemo::onNativeAdCloseButtonTapped(" + placementId + ", " + callbackInfo + ")");

        this.removeAd();
    }
   
});
