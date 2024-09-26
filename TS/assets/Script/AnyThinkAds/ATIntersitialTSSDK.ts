import {ATAndroidInterstitialJS} from "./Android/ATAndroidIntersitialTS";
import {ATiOSInterstitialJS} from "./iOS/ATiOSIntersitialTS";
import {ATJSSDK} from "./ATJSSDK";

var initPlatformBridge = function () {
    if (cc.sys.os === cc.sys.OS.IOS) {
        return ATiOSInterstitialJS;
    } else if (cc.sys.os === cc.sys.OS.ANDROID) {
        return ATAndroidInterstitialJS;
    }
};

var platformBridge = initPlatformBridge();


export const ATInterstitialSDK = {

    UseInterstitialAsInterstitial: "UseInterstitialAsInterstitial",

    ATInterstitialListener: {
        developerCallback: null,
        onInterstitialAdLoaded: function (placementId) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdLoaded != null && undefined != this.developerCallback.onInterstitialAdLoaded) {
                this.developerCallback.onInterstitialAdLoaded(placementId);
            }
        },

        onInterstitialAdLoadFail: function (placementId, errorInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdLoadFail != null && undefined != this.developerCallback.onInterstitialAdLoadFail) {
                this.developerCallback.onInterstitialAdLoadFail(placementId, errorInfo);
            }
        },

        onInterstitialAdShow: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdShow != null && undefined != this.developerCallback.onInterstitialAdShow) {
                this.developerCallback.onInterstitialAdShow(placementId, callbackInfo);
            }
        },

        onInterstitialAdStartPlayingVideo: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdStartPlayingVideo != null && undefined != this.developerCallback.onInterstitialAdStartPlayingVideo) {
                this.developerCallback.onInterstitialAdStartPlayingVideo(placementId, callbackInfo);
            }
        },

        onInterstitialAdEndPlayingVideo: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdEndPlayingVideo != null && undefined != this.developerCallback.onInterstitialAdEndPlayingVideo) {
                this.developerCallback.onInterstitialAdEndPlayingVideo(placementId, callbackInfo);
            }
        },

        onInterstitialAdFailedToPlayVideo: function (placementId, errorInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdFailedToPlayVideo != null && undefined != this.developerCallback.onInterstitialAdFailedToPlayVideo) {
                this.developerCallback.onInterstitialAdFailedToPlayVideo(placementId, errorInfo);
            }
        },

        onInterstitialAdFailedToShow: function (placementId, errorInfo, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdFailedToShow != null && undefined != this.developerCallback.onInterstitialAdFailedToShow) {
                this.developerCallback.onInterstitialAdFailedToShow(placementId, errorInfo, callbackInfo);
            }
        },

        onInterstitialAdClose: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdClose != null && undefined != this.developerCallback.onInterstitialAdClose) {
                this.developerCallback.onInterstitialAdClose(placementId, callbackInfo);
            }
        },

        onInterstitialAdClick: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onInterstitialAdClick != null && undefined != this.developerCallback.onInterstitialAdClick) {
                this.developerCallback.onInterstitialAdClick(placementId, callbackInfo);
            }
        },
        //added v5.8.10
        onAdSourceBiddingAttempt: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingAttempt != null && undefined != this.developerCallback.onAdSourceBiddingAttempt) {
                this.developerCallback.onAdSourceBiddingAttempt(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFilled: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingFilled != null && undefined != this.developerCallback.onAdSourceBiddingFilled) {
                this.developerCallback.onAdSourceBiddingFilled(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFail: function (placementId, errorInfo, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceBiddingFail != null && undefined != this.developerCallback.onAdSourceBiddingFail) {
                this.developerCallback.onAdSourceBiddingFail(placementId, errorInfo, callbackInfo);
            }
        },
        onAdSourceAttemp: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceAttemp != null && undefined != this.developerCallback.onAdSourceAttemp) {
                this.developerCallback.onAdSourceAttemp(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFilled: function (placementId, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceLoadFilled != null && undefined != this.developerCallback.onAdSourceLoadFilled) {
                this.developerCallback.onAdSourceLoadFilled(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFail: function (placementId, errorInfo, callbackInfo) {
            if (this.developerCallback != null && this.developerCallback.onAdSourceLoadFail != null && undefined != this.developerCallback.onAdSourceLoadFail) {
                this.developerCallback.onAdSourceLoadFail(placementId, errorInfo, callbackInfo);
            }
        }

    },

    loadInterstitial: function (placementId, settings = {}) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.loadInterstitial(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },
   
    setAdListener: function (listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdLoaded",
            eventJSON[LoadFailCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdLoadFail",
            eventJSON[PlayStartCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdStartPlayingVideo",
            eventJSON[PlayEndCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdEndPlayingVideo",
            eventJSON[PlayFailCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdFailedToPlayVideo",
            eventJSON[CloseCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdClose",
            eventJSON[ClickCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdClick",
            eventJSON[ShowCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdShow",
            eventJSON[ShowFailCallbackKey] = " ATInterstitialSDK.ATInterstitialListener.onInterstitialAdFailedToShow",
            //added v5.8.10
            eventJSON[BiddingAttempt] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceBiddingAttempt",
            eventJSON[BiddingFilled] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceBiddingFilled",
            eventJSON[BiddingFail] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceBiddingFail",
            eventJSON[Attemp] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceAttemp",
            eventJSON[LoadFilled] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceLoadFilled",
            eventJSON[LoadFail] = " ATInterstitialSDK.ATInterstitialListener.onAdSourceLoadFail"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
        } else {
            cc.log("You must run on Android or iOS.");
        }

        this.ATInterstitialListener.developerCallback = listener;
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

const LoadedCallbackKey = "InterstitialLoaded";
const LoadFailCallbackKey = "InterstitialLoadFail";
const PlayStartCallbackKey = "InterstitialPlayStart";
const PlayEndCallbackKey = "InterstitialPlayEnd";
const PlayFailCallbackKey = "InterstitialPlayFail";
const CloseCallbackKey = "InterstitialClose";
const ClickCallbackKey = "InterstitialClick";
const ShowCallbackKey = "InterstitialAdShow";
const ShowFailCallbackKey = "InterstitialAdShowFail";

const BiddingAttempt = "InterstitialBiddingAttempt";
const BiddingFilled = "InterstitialBiddingFilled";
const BiddingFail = "InterstitialBiddingFail";
const Attemp = "InterstitialAttemp";
const LoadFilled = "InterstitialLoadFilled";
const LoadFail = "InterstitialLoadFail";

window["ATInterstitialSDK"] = ATInterstitialSDK;