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

    showAdInPosistion : function(placementId, position) { 
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInPosistion(" + placementId + ", " + position + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:position:", placementId, position);
    },

    showAdInRectangle : function(placementId, showAdRect) {
        ATiOSJS.printJsLog("ATiOSBannerJS::showAdInRectangle(" + placementId + ", " + showAdRect + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showBannerWithPlacementID:rect:", placementId, showAdRect);
    },

    rewoveAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSBannerJS::rewoveAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "rewoveAd:", placementId);
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
