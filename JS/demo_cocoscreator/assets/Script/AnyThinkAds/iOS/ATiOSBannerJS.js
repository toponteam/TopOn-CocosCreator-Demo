var ATiOSJS = require("ATiOSJS");
const OC_WRAPPER_CLASS = "ATBannerAdWrapper";
var ATiOSBannerJS = ATiOSBannerJS || {
    loadBanner: function(placementId, extra) {
        ATiOSJS.printJsLog("ATiOSBannerJS::loadBanner(" + placementId + ", " + extra + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "loadBannerWithPlacementID:extra:", placementId, extra);
    },

    setAdListener : function (listener) {
        ATiOSJS.printJsLog("ATiOSBannerJS::setAdListener(" + listener + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "setDelegates:", listener);
    },
  
    hasAdReady : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "bannerReadyForPlacementID:", placementId);
    },

    checkAdStatus : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::checkAdStatus(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "bannerCheckAdStatusForPlacementID:", placementId);
    },

    showAdInPosition : function(placementId, position) { 
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInPosition(" + placementId + ", " + position + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:scene:position:", placementId, null, position);
    },

    showAdInPositionAndScenario : function(placementId, position, scenario) { 
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInPositionAndScenario(" + placementId + ", " + position + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:scene:position:", placementId, scenario, position);
    },

    showAdInRectangle : function(placementId, showAdRect) {
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInRectangle(" + placementId + ", " + showAdRect + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:scene:rect:", placementId, null, showAdRect);
    },

    showAdInRectangleAndScenario : function(placementId, showAdRect, scenario) {
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInRectangleAndScenario(" + placementId + ", " + showAdRect + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:scene:rect:", placementId, scenario, showAdRect);
    },

    removeAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::removeAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "removeAd:", placementId);
    },

    reShowAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::reShowAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "reShowAd:", placementId);
    },

    hideAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::hideAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "hideAd:", placementId);
    }
};

module.exports = ATiOSBannerJS;
