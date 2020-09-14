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

    showAdInPosition : function(placementId, position) { 
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInPosition(" + placementId + ", " + position + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:position:", placementId, position);
    },

    showAdInRectangle : function(placementId, showAdRect) {
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInRectangle(" + placementId + ", " + showAdRect + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:rect:", placementId, showAdRect);
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
