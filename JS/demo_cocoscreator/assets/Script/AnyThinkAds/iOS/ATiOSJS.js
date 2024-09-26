const OC_ATSDK_MANAGER_CLASS = "ATSDKManager";
const OC_BIRDGE_CLASS = "ATJSBridge";
var ATiOSJS = ATiOSJS || {
    initSDK : function(appid, appkey) {
    	this.printJsLog("ATiOSJS::initSDK(" + appid + "," + appkey + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "startWithAppID:appKey:", appid, appkey);
    },

    initCustomMap : function(customMap) {
        this.printJsLog("ATiOSJS::initCustomMap(" + customMap + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "setCustomData:", customMap);
    },

    setPlacementCustomMap : function(placmentId, customMap) {
        this.printJsLog("ATiOSJS::setPlacementCustomMap(" + placmentId + ", " + customMap + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "setCustomData:forPlacementID:", customMap, placmentId);
    },

    setGDPRLevel : function(level) {
        this.printJsLog("ATiOSJS::setGDPRLevel(" + level + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "setDataConsent:", level);
    },

    getGDPRLevel : function() {
        this.printJsLog("ATiOSJS::getGDPRLevel()");
        return jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "dataConsent");
    },

    getUserLocation : function(callbackMethod) {
        this.printJsLog("ATiOSJS::getUserLocation(" + callbackMethod + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "getUserLocationWithCallback:", callbackMethod);
    },

    showGDPRAuth : function () {
        this.printJsLog("ATiOSJS::showGDPRAuth()");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "presentDataConsentDialog");
    },
    
    setLogDebug : function (debug) {
        this.printJsLog("ATiOSJS::setLogDebug(" + debug + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "setDebugLog:", debug);
    },
    
    printJsLog : function(msg) {
        if (undefined != msg && msg != null) {
            jsb.reflection.callStaticMethod(OC_BIRDGE_CLASS, "log:", msg);
        }
    },

    deniedUploadDeviceInfo : function (deniedInfo) {
        this.printJsLog("ATiOSJS::deniedUploadDeviceInfo(" + deniedInfo + ")");
        jsb.reflection.callStaticMethod(OC_ATSDK_MANAGER_CLASS, "deniedUploadDeviceInfo:", deniedInfo);
    }
  
};

module.exports = ATiOSJS;