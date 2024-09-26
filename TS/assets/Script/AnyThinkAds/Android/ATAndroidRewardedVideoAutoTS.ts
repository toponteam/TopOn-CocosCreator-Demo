var classJavaName = "com/anythink/cocosjs/ATRewardedVideoAutoAdJSBridge";
export const ATAndroidRewardedVideoAutoAdTS = {
  
    setAdListener : function (listener) {
        cc.log("Android-setAdListener");
		jsb.reflection.callStaticMethod(classJavaName, "setAdListener", "(Ljava/lang/String;)V", listener);
    },

    addPlacementIds : function (placementIds) {
        cc.log("Android-addPlacementIds");
		jsb.reflection.callStaticMethod(classJavaName, "addPlacementIds", "(Ljava/lang/String;)V", placementIds);
    },

    removePlacementId : function (placementId) {
        cc.log("Android-removePlacementId");
		jsb.reflection.callStaticMethod(classJavaName, "removePlacementId", "(Ljava/lang/String;)V", placementId);
    },

    setAdExtraData : function (placementId, settings) {
        cc.log("Android-setAdExtraData");
		jsb.reflection.callStaticMethod(classJavaName, "setAdExtraData", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, settings);
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
        cc.log("Android-rvautoad_showAd:" + placementId);
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;)V", placementId);
    },

    showAdInScenario : function(placementId, scenario) {
        cc.log("Android-rvautoad_showAdInScenario:" + placementId + "---" + scenario);
		jsb.reflection.callStaticMethod(classJavaName, "show", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, scenario);
    },

    entryAdScenario : function(placementId, scenario) {
         cc.log("Android-entryAdScenario:" + placementId + "---" + scenario);
		 jsb.reflection.callStaticMethod(classJavaName, "entryAdScenario", "(Ljava/lang/String;Ljava/lang/String;)V", placementId, scenario);
    }
  
};

