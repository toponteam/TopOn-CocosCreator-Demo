var classJavaName = "com/anythink/cocosjs/ATNativeJSBridge";
var ATAndroidBannerJS = ATAndroidBannerJS || {
  
    loadNative : function (placementId, settings) {
        cc.log("Android-loadNative");
		jsb.reflection.callStaticMethod(classJavaName, "load", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, settings);
    },

    setAdListener : function (listener) {
        cc.log("Android-setAdListener");
		jsb.reflection.callStaticMethod(classJavaName, "setAdListener", "(Ljava/lang/String;)V", listener);
    },

    hasAdReady : function (placementId) {
         cc.log("Android-hasAdReady");
        return jsb.reflection.callStaticMethod(classJavaName, "isAdReady", "(Ljava/lang/String;)Z", placementId);;
    },

    checkAdStatus : function(placementId) {
        cc.log("Android-checkAdStatus:" + placementId);
        return jsb.reflection.callStaticMethod(classJavaName, "checkAdStatus", "(Ljava/lang/String;)Ljava/lang/String;", placementId);
    },

    showAd : function(placementId, adViewProperty) {
        cc.log("Android-showAd");
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", placementId, adViewProperty, "");
    },

    showAdInScenario : function(placementId, adViewProperty, scenario) {
        cc.log("Android-showAdInScenario");
        jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", placementId, adViewProperty, scenario);
    },

    removeAd : function(placementId) {
         cc.log("Android-removeAd");
		 jsb.reflection.callStaticMethod(classJavaName, "remove", "(Ljava/lang/String;)V", placementId);
    }
  
};

module.exports = ATAndroidBannerJS;