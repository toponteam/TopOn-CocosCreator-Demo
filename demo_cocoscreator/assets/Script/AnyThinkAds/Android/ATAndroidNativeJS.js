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

    showAd : function(placementId, adViewProperty) {
        cc.log("Android-showAd");
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, adViewProperty);
    },
    
    removeAd : function(placementId) {
         cc.log("Android-removeAd");
		 jsb.reflection.callStaticMethod(classJavaName, "remove", "(Ljava/lang/String;)V", placementId);
    }
  
};

module.exports = ATAndroidBannerJS;