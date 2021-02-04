
require("./AnyThinkAds/ATJSSDK");

cc.Class({
    extends: cc.Component,

    properties: {
        label: {
            default: null,
            type: cc.Label
        },
        // defaults, set visually when attaching this script to the Canvas
        text: 'Hello, World!'
    },

    start : function(){
        cc.log("Main Scene start.......");

        ATJSSDK.setLogDebug(true);

        var customMap = {
            "appCustomKey1": "appCustomValue1",
            "appCustomKey2" : "appCustomValue2"
        };
        ATJSSDK.initCustomMap(customMap);

        //RewardedVideo PlacementID
        var customPlacementId = "";
        if (cc.sys.os === cc.sys.OS_IOS) {           
            customPlacementId = "b5b44a0f115321";
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            customPlacementId = "b5b449fb3d89d7";
        }
        var placementCustomMap = {
            "placementCustomKey1": "placementCustomValue1",
            "placementCustomKey2" : "placementCustomValue2"
        };

        ATJSSDK.setPlacementCustomMap(customPlacementId, placementCustomMap);
        // ATJSSDK.setGDPRLevel(ATJSSDK.PERSONALIZED); 

        var GDPRLevel = ATJSSDK.getGDPRLevel();
        ATJSSDK.printLog("Current GDPR Level :" + GDPRLevel);


        if (cc.sys.os === cc.sys.OS_IOS) {           
            ATJSSDK.initSDK("a5b0e8491845b3", "7eae0567827cfe2b22874061763f30c9");
        } else if (cc.sys.os === cc.sys.OS_ANDROID) {
            ATJSSDK.initSDK("a5aa1f9deda26d", "4f7b9ac17decb9babec83aac078742c7");
        }

        
        ATJSSDK.getUserLocation(function (userLocation) {
             ATJSSDK.printLog("getUserLocation callback userLocation :" + userLocation);

            if (userLocation === ATJSSDK.kATUserLocationInEU) {
                if(ATJSSDK.getGDPRLevel() === ATJSSDK.UNKNOWN) {
                    ATJSSDK.showGDPRAuth();
                }
            }
        });

    },

    // use this for initialization
    onLoad: function () {   

    },

    // called every frame
    update: function (dt) {

    },

    gotoRewardedVideoScene: function () {
        cc.director.loadScene("RewardedVideoDemoScene");
    },
    gotoNativeScene: function () {
       cc.director.loadScene("NativeAdDemoScene");
    },
    gotoBannerScene: function () {
       cc.director.loadScene("BannerDemoScene");
    },
    gotoInterstitialScene: function () {
       cc.director.loadScene("InterstitialDemoScene");
    }
});
