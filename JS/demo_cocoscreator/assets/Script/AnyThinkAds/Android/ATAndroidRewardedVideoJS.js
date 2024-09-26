var classJavaName = "com/anythink/cocosjs/ATRewardedVideoJSBridge";
var ATAndroidRewardedVideoJS = ATAndroidRewardedVideoJS || {
  
    loadRewardedVideo : function (placementId, settings) {
        cc.log("Android-loadRewardedVideo");
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
        cc.log("Android-rv_showAd:" + placementId);
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;)V", placementId);
    },

    showAdInScenario : function(placementId, scenario) {
        cc.log("Android-rv_showAdInScenario:" + placementId + "---" + scenario);
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, scenario);
    }
  
};

module.exports = ATAndroidRewardedVideoJS;