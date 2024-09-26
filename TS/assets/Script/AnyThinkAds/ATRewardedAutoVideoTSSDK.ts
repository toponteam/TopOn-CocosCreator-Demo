import {ATAndroidRewardedVideoAutoAdTS} from "./Android/ATAndroidRewardedVideoAutoTS";
import {ATiOSRewardedVideoAutoAdJS} from "./iOS/ATiOSRewardedAutoVideoTS";
import {ATJSSDK} from "./ATJSSDK";

var initPlatformBridge = function () {
    if (cc.sys.os === cc.sys.OS.IOS) {
        return ATiOSRewardedVideoAutoAdJS;
    } else if (cc.sys.os === cc.sys.OS.ANDROID) {
        return ATAndroidRewardedVideoAutoAdTS;
    }
};

var platformBridge = initPlatformBridge();
export const ATRewardedVideoAutoAdSDK = {
    userIdKey: "userID",
    userDataKey: "media_ext",

    ATRewardedVideoListener: {
        developerCallback: null,

        onRewardedVideoAdLoaded: function (placementId) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded(" + placementId + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdLoaded != null && undefined != this.developerCallback.onRewardedVideoAdLoaded) {
                this.developerCallback.onRewardedVideoAdLoaded(placementId);
            }
        },
        onRewardedVideoAdFailed: function (placementId, errorInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdFailed(" + placementId + ", " + errorInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdFailed != null && undefined != this.developerCallback.onRewardedVideoAdFailed) {
                this.developerCallback.onRewardedVideoAdFailed(placementId, errorInfo);
            }
        },
        onRewardedVideoAdPlayStart: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayStart != null && undefined != this.developerCallback.onRewardedVideoAdPlayStart) {
                this.developerCallback.onRewardedVideoAdPlayStart(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayEnd: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayEnd != null && undefined != this.developerCallback.onRewardedVideoAdPlayEnd) {
                this.developerCallback.onRewardedVideoAdPlayEnd(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayFailed: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayFailed != null && undefined != this.developerCallback.onRewardedVideoAdPlayFailed) {
                this.developerCallback.onRewardedVideoAdPlayFailed(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdClosed: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdClosed(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdClosed != null && undefined != this.developerCallback.onRewardedVideoAdClosed) {
                this.developerCallback.onRewardedVideoAdClosed(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayClicked: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayClicked != null && undefined != this.developerCallback.onRewardedVideoAdPlayClicked) {
                this.developerCallback.onRewardedVideoAdPlayClicked(placementId, callbackInfo);
            }
        },
        onReward: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onReward(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onReward != null && undefined != this.developerCallback.onReward) {
                this.developerCallback.onReward(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayStart: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayStart(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayStart != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayStart) {
                this.developerCallback.onRewardedVideoAdAgainPlayStart(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayEnd: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayEnd(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayEnd != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayEnd) {
                this.developerCallback.onRewardedVideoAdAgainPlayEnd(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayFailed: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayFailed != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayFailed) {
                this.developerCallback.onRewardedVideoAdAgainPlayFailed(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayClicked: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayClicked(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayClicked != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayClicked) {
                this.developerCallback.onRewardedVideoAdAgainPlayClicked(placementId, callbackInfo);
            }
        },
        onAgainReward: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onAgainReward(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAgainReward != null && undefined != this.developerCallback.onAgainReward) {
                this.developerCallback.onAgainReward(placementId, callbackInfo);
            }
        }
    },

    setAdExtraData: function (placementId, settings = {}) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdExtraData(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    addPlacementIds : function (placementIds){

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.addPlacementIds(JSON.stringify(placementIds));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    removePlacementId : function (placementIds){

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.removePlacementId(JSON.stringify(placementIds));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },
    
    setAdListener: function (listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded",
        eventJSON[LoadFailCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdFailed",
        eventJSON[PlayStartCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart",
        eventJSON[PlayEndCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd",
        eventJSON[PlayFailCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed",
        eventJSON[CloseCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdClosed",
        eventJSON[ClickCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked",
        eventJSON[RewardCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onReward",
        //playAgain listener
        eventJSON[AgainPlayStartCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayStart",
        eventJSON[AgainPlayEndCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayEnd",
        eventJSON[AgainPlayFailCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayFailed",
        eventJSON[AgainClickCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayClicked",
        eventJSON[AgainRewardCallbackKey] = "ATRewardedVideoAutoAdSDK.ATRewardedVideoListener.onAgainReward"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
            platformBridge.set
        } else {
            cc.log("You must run on Android or iOS.");
        }

        this.ATRewardedVideoListener.developerCallback = listener;
    },

    hasAdReady: function (placementId) {
        if (undefined != platformBridge && platformBridge != null) {
            return platformBridge.hasAdReady(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
        return false;
    },

    checkAdStatus: function (placementId) {
        if (undefined != platformBridge && platformBridge != null) {
            return platformBridge.checkAdStatus(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
        return "";
    },

    showAd: function (placementId) {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.showAd(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInScenario: function (placementId, scenario = "") {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.showAdInScenario(placementId, scenario);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    entryAdScenario: function (placementId, scenario = "") {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.entryAdScenario(placementId, scenario);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    }

};

const LoadedCallbackKey = "RewardedVideoAutoAdLoaded";
const LoadFailCallbackKey = "RewardedVideoAutoAdLoadFail";
const PlayStartCallbackKey = "RewardedVideoAutoAdPlayStart";
const PlayEndCallbackKey = "RewardedVideoAutoAdPlayEnd";
const PlayFailCallbackKey = "RewardedVideoAutoAdPlayFail";
const CloseCallbackKey = "RewardedVideoAutoAdClose";
const ClickCallbackKey = "RewardedVideoAutoAdClick";
const RewardCallbackKey = "RewardedVideoAutoAdReward";

const AgainPlayStartCallbackKey = "RewardedVideoAutoAdAgainPlayStart";
const AgainPlayEndCallbackKey = "RewardedVideoAutoAdAgainPlayEnd";
const AgainPlayFailCallbackKey = "RewardedVideoAutoAdAgainPlayFail";
const AgainClickCallbackKey = "RewardedVideoAutoAdAgainClick";
const AgainRewardCallbackKey = "RewardedVideoAutoAdAgainReward";

window["ATRewardedVideoAutoAdSDK"] = ATRewardedVideoAutoAdSDK;