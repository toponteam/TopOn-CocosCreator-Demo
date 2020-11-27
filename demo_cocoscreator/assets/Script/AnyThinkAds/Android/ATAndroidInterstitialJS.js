var classJavaName = "com/anythink/cocosjs/ATInterstitialJSBridge";
var ATAndroidInterstitialJS = ATAndroidInterstitialJS || {
	
	loadInterstitial : function (placementId, settings) {
        cc.log("Android-loadInterstitial");
		jsb.reflection.callStaticMethod(classJavaName, "load", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, settings);
    },

    setAdListener : function (listener) {
        cc.log("Android-setAdListener");
		jsb.reflection.callStaticMethod(classJavaName, "setAdListener", "(Ljava/lang/String;)V", listener);
    },

    hasAdReady : function (placementId) {
         cc.log("Android-hasAdReady");
        return jsb.reflection.callStaticMethod(classJavaName, "isAdReady", "(Ljava/lang/String;)Z", placementId);
    },

    checkAdStatus : function(placementId) {
        cc.log("Android-checkAdStatus:" + placementId);
        return jsb.reflection.callStaticMethod(classJavaName, "checkAdStatus", "(Ljava/lang/String;)Ljava/lang/String;", placementId);
    },

    showAd : function(placementId) {
        cc.log("Android-showAd:" + placementId);
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;)V", placementId);
    },

    showAdInScenario : function(placementId, scenario) {
         cc.log("Android-showAdInScenario:" + placementId + "---" + scenario);
		 jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, scenario);
    }

};

module.exports = ATAndroidInterstitialJS;