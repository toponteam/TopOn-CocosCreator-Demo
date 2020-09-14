var ATiOSJS = require("ATiOSJS");
const OC_WRAPPER_CLASS = "ATNativeAdWrapper";
var ATiOSNativeJS = ATiOSNativeJS || {
    loadNative : function(placementId, settings) {
        ATiOSJS.printJsLog("ATiOSBannerJS::loadNative(" + placementId + ", " + settings + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "loadNativeWithPlacementID:extra:", placementId, settings);
    },

    setAdListener : function (listener) {
        ATiOSJS.printJsLog("ATiOSNativeJS::setAdListener(" + listener + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "setDelegates:", listener);
    },
  
    hasAdReady : function(placementId) {
        ATiOSJS.printJsLog("ATiOSNativeJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "nativeReadyForPlacementID:", placementId);
    },

    showAd : function(placementId, adViewProperty) { 
        ATiOSJS.printJsLog("ATiOSNativeJS::showAd(" + placementId + ", " + adViewProperty + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showNativeWithPlacementID:metrics:", placementId, adViewProperty);
    },

    removeAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSNativeJS::removeAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "removeNativeWithPlacementID:", placementId);
    }
};

module.exports = ATiOSNativeJS;
