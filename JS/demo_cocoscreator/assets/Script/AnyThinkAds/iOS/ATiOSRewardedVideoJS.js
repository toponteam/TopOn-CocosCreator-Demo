var ATiOSJS = require("ATiOSJS");
const OC_RV_WRAPPER_CLASS = "ATRewardedVideoWrapper";
var ATiOSRewardedVideoJS = ATiOSRewardedVideoJS || {
    loadRewardedVideo : function (placementId, extra) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::loadRewardedVideo(" + placementId + ", " + extra + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "loadRewardedVideoWithPlacementID:extra:", placementId, extra);
    },

    setAdListener : function (listener) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::setAdListener(" + listener + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "setDelegates:", listener);
    },

    hasAdReady : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "rewardedVideoReadyForPlacementID:", placementId);
    },

    checkAdStatus : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::checkAdStatus(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "checkAdStatus:", placementId);
    },

    setUserData : function (placementId, userId, userData) {
         cc.log("Android-setUserData");
    },

    showAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::showAd(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "showRewardedVideoWithPlacementID:scene:", placementId, null);
        
    },

    showAdInScenario : function(placementId, scenario) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoJS::showAdInScenario(" + placementId  + ", " + scenario + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "showRewardedVideoWithPlacementID:scene:", placementId, scenario);
    } 
  
};

module.exports = ATiOSRewardedVideoJS;
