
require("./AnyThinkAds/ATJSSDK");
// cc.ATSDKListener = require("ATJSSDK").ATSDKListener;

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

    // use this for initialization
    onLoad: function () {
        
    },

    // called every frame
    update: function (dt) {

    },

    initSDK: function () {
        ATSDK.setLogDebug(true);
        // ATSDK.initSDK("a5aa1f9deda26d", "4f7b9ac17decb9babec83aac078742c7");
        // ATSDK.initCustomMap({"appcustomkey":"appcustomvalue","appcustomkey1":"appcustomvalue1"});
        // ATSDK.setPlacementCustomMap("placementId",{"placementcustomkey":"placementcustomvalue","placementcustomkey1":"placementcustomvalue1"} );
        // ATSDK.setGDPRLevel(ATSDK.PERSONALIZED);
        // cc.log("Current gdprLevel:" + ATSDK.getGDPRLevel());
        // ATSDK.showGDPRAuth();
        // cc.ATSDKListener.getUserLocationCallback(2);
        ATSDK.getUserLocation(function (userLocation) {
            ATSDK.printLog("userLocation:" + userLocation);
        });
    },

    callback: function (callbackinfo){
        cc.log(callbackinfo);
    }
});
