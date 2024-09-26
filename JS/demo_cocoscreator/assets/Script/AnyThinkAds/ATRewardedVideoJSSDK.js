var ATAndroidRewardedVideoJS = require("./Android/ATAndroidRewardedVideoJS");
var ATiOSRewardedVideoJS = require("./iOS/ATiOSRewardedVideoJS");

var initPlatformBridge = function() {  
    if (cc.sys.os === cc.sys.OS_IOS) {           
        return ATiOSRewardedVideoJS;
    } else if (cc.sys.os === cc.sys.OS_ANDROID) {
        return ATAndroidRewardedVideoJS;
    }
};

var platformBridge = initPlatformBridge();


var ATRewardedVideoSDK = ATRewardedVideoSDK || {
    userIdKey: "userID",
    userDataKey: "media_ext",

    ATRewardedVideoListener : {
        developerCallback : null,

        onRewardedVideoAdLoaded : function (placementId) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded(" + placementId + ")");
            if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdLoaded != null && undefined != this.developerCallback.onRewardedVideoAdLoaded) {
                this.developerCallback.onRewardedVideoAdLoaded(placementId);
            }
        },
        onRewardedVideoAdFailed : function(placementId, errorInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdFailed(" + placementId + ", " + errorInfo + ")");
          if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdFailed != null && undefined != this.developerCallback.onRewardedVideoAdFailed) {
                this.developerCallback.onRewardedVideoAdFailed(placementId, errorInfo);
            }
        },
        onRewardedVideoAdPlayStart : function(placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart(" + placementId + ", " + callbackInfo + ")");
           if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayStart != null && undefined != this.developerCallback.onRewardedVideoAdPlayStart) {
                this.developerCallback.onRewardedVideoAdPlayStart(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayEnd : function(placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd(" + placementId + ", " + callbackInfo + ")");
            if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayEnd != null && undefined != this.developerCallback.onRewardedVideoAdPlayEnd) {
                this.developerCallback.onRewardedVideoAdPlayEnd(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayFailed : function(placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed(" + placementId + ", " + callbackInfo + ")");
            if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayFailed != null && undefined != this.developerCallback.onRewardedVideoAdPlayFailed) {
                this.developerCallback.onRewardedVideoAdPlayFailed(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdClosed : function(placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdClosed(" + placementId + ", " + callbackInfo + ")");
            if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdClosed != null && undefined != this.developerCallback.onRewardedVideoAdClosed) {
                this.developerCallback.onRewardedVideoAdClosed(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayClicked : function(placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked(" + placementId + ", " + callbackInfo + ")");
            if(this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayClicked != null && undefined != this.developerCallback.onRewardedVideoAdPlayClicked) {
                this.developerCallback.onRewardedVideoAdPlayClicked(placementId, callbackInfo);
            }
        },
        onReward : function(placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoJSSDK.ATRewardedVideoListener.onReward(" + placementId + ", " + callbackInfo + ")");
            if(this.developerCallback != null && this.developerCallback.onReward != null && undefined != this.developerCallback.onReward) {
                this.developerCallback.onReward(placementId, callbackInfo);
            }
        }
    },
    
    loadRewardedVideo : function(placementId, settings={}) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.loadRewardedVideo(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setAdListener : function(listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey]= "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded",
        eventJSON[LoadFailCallbackKey]= "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdFailed",
        eventJSON[PlayStartCallbackKey]=  "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart",
        eventJSON[PlayEndCallbackKey]=  "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd",
        eventJSON[PlayFailCallbackKey] = "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed",
        eventJSON[CloseCallbackKey]= "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdClosed",
        eventJSON[ClickCallbackKey]= "ATRewardedVideoJSSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked",
        eventJSON[RewardCallbackKey]= "ATRewardedVideoJSSDK.ATRewardedVideoListener.onReward"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
        } else {
            cc.log("You must run on Android or iOS.");
        }

        this.ATRewardedVideoListener.developerCallback = listener;
    },

    hasAdReady : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
            return platformBridge.hasAdReady(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
        return false;
    },

    checkAdStatus : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
            return platformBridge.checkAdStatus(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
        return "";
    },

    showAd : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAd(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInScenario : function(placementId, scenario="") {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInScenario(placementId, scenario);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    } 
  
};

const LoadedCallbackKey = "RewardedVideoLoaded";
const LoadFailCallbackKey = "RewardedVideoLoadFail";
const PlayStartCallbackKey = "RewardedVideoPlayStart";
const PlayEndCallbackKey = "RewardedVideoPlayEnd";
const PlayFailCallbackKey = "RewardedVideoPlayFail";
const CloseCallbackKey = "RewardedVideoClose";
const ClickCallbackKey = "RewardedVideoClick";
const RewardCallbackKey = "RewardedVideoReward";


window.ATRewardedVideoJSSDK = ATRewardedVideoSDK;