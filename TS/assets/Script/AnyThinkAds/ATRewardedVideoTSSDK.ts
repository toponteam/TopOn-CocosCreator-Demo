import {ATAndroidRewardedVideoJS} from "./Android/ATAndroidRewardedVideoTS";
import {ATiOSRewardedVideoTS} from "./iOS/ATiOSRewardedVideoTS";
import {ATJSSDK} from "./ATJSSDK";

var initPlatformBridge = function () {
    if (cc.sys.os === cc.sys.OS.IOS) {
        return ATiOSRewardedVideoTS;
    } else if (cc.sys.os === cc.sys.OS.ANDROID) {
        return ATAndroidRewardedVideoJS;
    }
};

var platformBridge = initPlatformBridge();


export const ATRewardedVideoSDK =  {
    userIdKey: "userID",
    userDataKey: "media_ext",

    ATRewardedVideoListener: {
        developerCallback: null,

        onRewardedVideoAdLoaded: function (placementId) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded(" + placementId + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdLoaded != null && undefined != this.developerCallback.onRewardedVideoAdLoaded) {
                this.developerCallback.onRewardedVideoAdLoaded(placementId);
            }
        },
        onRewardedVideoAdFailed: function (placementId, errorInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdFailed(" + placementId + ", " + errorInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdFailed != null && undefined != this.developerCallback.onRewardedVideoAdFailed) {
                this.developerCallback.onRewardedVideoAdFailed(placementId, errorInfo);
            }
        },
        onRewardedVideoAdPlayStart: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayStart != null && undefined != this.developerCallback.onRewardedVideoAdPlayStart) {
                this.developerCallback.onRewardedVideoAdPlayStart(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayEnd: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayEnd != null && undefined != this.developerCallback.onRewardedVideoAdPlayEnd) {
                this.developerCallback.onRewardedVideoAdPlayEnd(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayFailed: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayFailed != null && undefined != this.developerCallback.onRewardedVideoAdPlayFailed) {
                this.developerCallback.onRewardedVideoAdPlayFailed(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdClosed: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdClosed(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdClosed != null && undefined != this.developerCallback.onRewardedVideoAdClosed) {
                this.developerCallback.onRewardedVideoAdClosed(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdPlayClicked: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdPlayClicked != null && undefined != this.developerCallback.onRewardedVideoAdPlayClicked) {
                this.developerCallback.onRewardedVideoAdPlayClicked(placementId, callbackInfo);
            }
        },
        onReward: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onReward(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onReward != null && undefined != this.developerCallback.onReward) {
                this.developerCallback.onReward(placementId, callbackInfo);
            }
        },
        //added v5.8.10
        onAdSourceBiddingAttempt: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingAttempt(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingAttempt != null && undefined != this.developerCallback.onAdSourceBiddingAttempt) {
                this.developerCallback.onAdSourceBiddingAttempt(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFilled: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingFilled(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingFilled != null && undefined != this.developerCallback.onAdSourceBiddingFilled) {
                this.developerCallback.onAdSourceBiddingFilled(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFail: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingFail(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingFail != null && undefined != this.developerCallback.onAdSourceBiddingFail) {
                this.developerCallback.onAdSourceBiddingFail(placementId, errorInfo, callbackInfo);
            }
        },
        onAdSourceAttemp: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceAttemp(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceAttemp != null && undefined != this.developerCallback.onAdSourceAttemp) {
                this.developerCallback.onAdSourceAttemp(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFilled: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceLoadFilled(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceLoadFilled != null && undefined != this.developerCallback.onAdSourceLoadFilled) {
                this.developerCallback.onAdSourceLoadFilled(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFail: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceLoadFail(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAdSourceLoadFail != null && undefined != this.developerCallback.onAdSourceLoadFail) {
                this.developerCallback.onAdSourceLoadFail(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayStart: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayStart(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayStart != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayStart) {
                this.developerCallback.onRewardedVideoAdAgainPlayStart(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayEnd: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayEnd(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayEnd != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayEnd) {
                this.developerCallback.onRewardedVideoAdAgainPlayEnd(placementId, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayFailed: function (placementId, errorInfo, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayFailed(" + placementId + ", " + errorInfo + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayFailed != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayFailed) {
                this.developerCallback.onRewardedVideoAdAgainPlayFailed(placementId, errorInfo, callbackInfo);
            }
        },
        onRewardedVideoAdAgainPlayClicked: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayClicked(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onRewardedVideoAdAgainPlayClicked != null && undefined != this.developerCallback.onRewardedVideoAdAgainPlayClicked) {
                this.developerCallback.onRewardedVideoAdAgainPlayClicked(placementId, callbackInfo);
            }
        },
        onAgainReward: function (placementId, callbackInfo) {
            ATJSSDK.printLog("ATRewardedVideoSDK.ATRewardedVideoListener.onAgainReward(" + placementId + ", " + callbackInfo + ")");
            if (this.developerCallback != null && this.developerCallback.onAgainReward != null && undefined != this.developerCallback.onAgainReward) {
                this.developerCallback.onAgainReward(placementId, callbackInfo);
            }
        }
    },

    loadRewardedVideo: function (placementId, settings = {}) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.loadRewardedVideo(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setAdListener: function (listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdLoaded",
            eventJSON[LoadFailCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdFailed",
            eventJSON[PlayStartCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayStart",
            eventJSON[PlayEndCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayEnd",
            eventJSON[PlayFailCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayFailed",
            eventJSON[CloseCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdClosed",
            eventJSON[ClickCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdPlayClicked",
            eventJSON[RewardCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onReward",
            //added v5.8.10
            eventJSON[BiddingAttempt] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingAttempt",
            eventJSON[BiddingFilled] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingFilled",
            eventJSON[BiddingFail] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceBiddingFail",
            eventJSON[Attemp] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceAttemp",
            eventJSON[LoadFilled] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceLoadFilled",
            eventJSON[LoadFail] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAdSourceLoadFail",
            //added v5.8.10 playAgain listener
            eventJSON[AgainPlayStartCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayStart",
            eventJSON[AgainPlayEndCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayEnd",
            eventJSON[AgainPlayFailCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayFailed",
            eventJSON[AgainClickCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onRewardedVideoAdAgainPlayClicked",
            eventJSON[AgainRewardCallbackKey] = "ATRewardedVideoSDK.ATRewardedVideoListener.onAgainReward"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
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
    },
    testMethod : function(){
        cc.log("testing You must run on Android or iOS.");
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

const BiddingAttempt = "RewardedVideoBiddingAttempt";
const BiddingFilled = "RewardedVideoBiddingFilled";
const BiddingFail = "RewardedVideoBiddingFail";
const Attemp = "RewardedVideoAttemp";
const LoadFilled = "RewardedVideoLoadFilled";
const LoadFail = "RewardedVideoLoadFail";

const AgainPlayStartCallbackKey = "RewardedVideoAgainPlayStart";
const AgainPlayEndCallbackKey = "RewardedVideoAgainPlayEnd";
const AgainPlayFailCallbackKey = "RewardedVideoAgainPlayFail";
const AgainClickCallbackKey = "RewardedVideoAgainClick";
const AgainRewardCallbackKey = "RewardedVideoAgainReward";

window["ATRewardedVideoSDK"] = ATRewardedVideoSDK;