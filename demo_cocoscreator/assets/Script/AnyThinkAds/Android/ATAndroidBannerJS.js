var classJavaName = "com/anythink/cocosjs/ATBannerJSBridge";
var ATAndroidBannerJS = ATAndroidBannerJS || {
  
    loadBanner : function (placementId, settings) {
        cc.log("Android-loadBanner:" + settings);
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

    showAdInPosition : function(placementId, position) {
        cc.log("Android-showAdInPosistion");
		jsb.reflection.callStaticMethod(classJavaName, "showWithPosition", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, position);
    },

    showAdInRectangle : function(placementId, showAdRect) {
        cc.log("Android-showAdInRectangle");
		jsb.reflection.callStaticMethod(classJavaName, "showWithRect", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, showAdRect);
    },
    
    removeAd : function(placementId) {
         cc.log("Android-removeAd");
		 jsb.reflection.callStaticMethod(classJavaName, "remove", "(Ljava/lang/String;)V", placementId);
    },

    reShowAd : function(placementId) {
        cc.log("Android-reShowAd");
		jsb.reflection.callStaticMethod(classJavaName, "reshow", "(Ljava/lang/String;)V", placementId);
    },

    hideAd : function(placementId) {
         cc.log("Android-hideAd");
		 jsb.reflection.callStaticMethod(classJavaName, "hide", "(Ljava/lang/String;)V", placementId);
    }
  
};

module.exports = ATAndroidBannerJS;