var classJavaName = "com/anythink/cocosjs/ATJSBridge";
var ATAndroidJS = ATAndroidJS || {
    printJsLog : function(msg) {
        if (undefined != msg && msg != null) {
            jsb.reflection.callStaticMethod("android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", "AT-Cocos-JS", msg);
        }
    },

    initSDK : function(appid, appkey) {
        jsb.reflection.callStaticMethod(classJavaName, "initSDK", "(Ljava/lang/String;Ljava/lang/String;)V", appid, appkey);
    },

    initCustomMap : function(customMap) {      
        jsb.reflection.callStaticMethod(classJavaName, "initCustomMap", "(Ljava/lang/String;)V", customMap);
    },

    setPlacementCustomMap : function(placmentId, customMap) {
        jsb.reflection.callStaticMethod(classJavaName, "setPlacementCustomMap", "(Ljava/lang/String;Ljava/lang/String;)V", placmentId, customMap);
    },

    setGDPRLevel : function(level) {
        jsb.reflection.callStaticMethod(classJavaName, "setGDPRLevel", "(I)V", level);
    },

    getGDPRLevel : function() {
        return jsb.reflection.callStaticMethod(classJavaName, "getGDPRLevel", "()I");
    },

    getUserLocation : function(callbackMethod) {
        jsb.reflection.callStaticMethod(classJavaName, "getUserLocation", "(Ljava/lang/String;)V", callbackMethod);
    },

    showGDPRAuth : function () {
        jsb.reflection.callStaticMethod(classJavaName, "showGDPRAuth", "()V");
    },

    setLogDebug : function (debug) {
        jsb.reflection.callStaticMethod(classJavaName, "setLogDebug", "(Z)V", debug);
    },

    deniedUploadDeviceInfo : function (deniedInfo) {
        jsb.reflection.callStaticMethod(classJavaName, "deniedUploadDeviceInfo", "(Ljava/lang/String;)V", deniedInfo);
    }
  
};

module.exports = ATAndroidJS;