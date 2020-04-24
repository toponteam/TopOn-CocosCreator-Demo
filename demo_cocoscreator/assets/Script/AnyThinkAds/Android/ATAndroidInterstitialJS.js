var classJavaName = "com/anythink/cocosjs/ATInterstitialJSBridge";
var ATAndroidInterstitialJS = ATAndroidInterstitialJS || {
  
    loadInterstitial : function (placementId) {
        cc.log("Android-loadInterstitial");
		jsb.reflection.callStaticMethod(classJavaName, "load", "(Ljava/lang/String;)V", placementId);
    },

    setAdListener : function (listener) {
        cc.log("Android-setAdListener");
		jsb.reflection.callStaticMethod(classJavaName, "setAdListener", "(Ljava/lang/String;)V", listener);
    },

    hasAdReady : function (placementId) {
         cc.log("Android-hasAdReady");
        return jsb.reflection.callStaticMethod(classJavaName, "isAdReady", "(Ljava/lang/String;)Z", placementId);
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