import {ATAndroidBannerTS} from "./Android/ATAndroidBannerTS";
import {ATiOSBannerJS} from "./iOS/ATiOSBannerTS";
import {ATJSSDK} from "./ATJSSDK";

var initPlatformBridge = function () {
    if (cc.sys.os === cc.sys.OS.IOS) {
        return ATiOSBannerJS;
    } else if (cc.sys.os === cc.sys.OS.ANDROID) {
        return ATAndroidBannerTS;
    }
};

var platformBridge = initPlatformBridge();



export const ATBannerSDK = {
    kATBannerAdLoadingExtraBannerAdSizeStruct: "banner_ad_size_struct",
	kATBannerAdShowingPositionTop : "top",
    kATBannerAdShowingPositionBottom : "bottom",
	
	kATBannerAdInlineAdaptiveWidth : "inline_adaptive_width",
	kATBannerAdInlineAdaptiveOrientation : "inline_adaptive_orientation",
	kATBannerAdInlineAdaptiveOrientationCurrent : 0,
	kATBannerAdInlineAdaptiveOrientationPortrait : 1,
	kATBannerAdInlineAdaptiveOrientationLandscape : 2,

	kATBannerAdAdaptiveWidth : "adaptive_width",
	kATBannerAdAdaptiveOrientation : "adaptive_orientation",
	kATBannerAdAdaptiveOrientationCurrent : 0,
	kATBannerAdAdaptiveOrientationPortrait : 1,
	kATBannerAdAdaptiveOrientationLandscape : 2,


    ATBannerListener : {
        developerCallback : null,

        onBannerAdLoaded : function (placementId) {
            if(this.developerCallback != null && this.developerCallback.onBannerAdLoaded != null && undefined != this.developerCallback.onBannerAdLoaded) {
                this.developerCallback.onBannerAdLoaded(placementId);
            }
        },

        onBannerAdLoadFail : function(placementId, errorInfo) {
          if(this.developerCallback != null && this.developerCallback.onBannerAdLoadFail != null && undefined != this.developerCallback.onBannerAdLoadFail) {
                this.developerCallback.onBannerAdLoadFail(placementId, errorInfo);
            }
        },

        onBannerAdShow : function(placementId, callbackInfo) {
           if(this.developerCallback != null && this.developerCallback.onBannerAdShow != null && undefined != this.developerCallback.onBannerAdShow) {
                this.developerCallback.onBannerAdShow(placementId, callbackInfo);
            }
        },

        onBannerAdClick : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onBannerAdClick != null && undefined != this.developerCallback.onBannerAdClick) {
                this.developerCallback.onBannerAdClick(placementId, callbackInfo);
            }
        },

        onBannerAdAutoRefresh : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onBannerAdAutoRefresh != null && undefined != this.developerCallback.onBannerAdAutoRefresh) {
                this.developerCallback.onBannerAdAutoRefresh(placementId, callbackInfo);
            }
        },

        onBannerAdAutoRefreshFail : function(placementId, errorInfo) {
            if(this.developerCallback != null && this.developerCallback.onBannerAdAutoRefreshFail != null && undefined != this.developerCallback.onBannerAdAutoRefreshFail) {
                this.developerCallback.onBannerAdAutoRefreshFail(placementId, errorInfo);
            }
        },

        onBannerAdCloseButtonTapped : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onBannerAdCloseButtonTapped != null && undefined != this.developerCallback.onBannerAdCloseButtonTapped) {
                this.developerCallback.onBannerAdCloseButtonTapped(placementId, callbackInfo);
            }
        },
        //added v5.8.10
        onAdSourceBiddingAttempt : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onAdSourceBiddingAttempt != null && undefined != this.developerCallback.onAdSourceBiddingAttempt) {
                this.developerCallback.onAdSourceBiddingAttempt(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFilled : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onAdSourceBiddingFilled != null && undefined != this.developerCallback.onAdSourceBiddingFilled) {
                this.developerCallback.onAdSourceBiddingFilled(placementId, callbackInfo);
            }
        },
        onAdSourceBiddingFail: function (placementId, errorInfo, callbackInfo)  {
            if(this.developerCallback != null && this.developerCallback.onAdSourceBiddingFail != null && undefined != this.developerCallback.onAdSourceBiddingFail) {
                this.developerCallback.onAdSourceBiddingFail(placementId, errorInfo, callbackInfo);
            }
        },
        onAdSourceAttemp: function (placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onAdSourceAttemp != null && undefined != this.developerCallback.onAdSourceAttemp) {
                this.developerCallback.onAdSourceAttemp(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFilled : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onAdSourceLoadFilled != null && undefined != this.developerCallback.onAdSourceLoadFilled) {
                this.developerCallback.onAdSourceLoadFilled(placementId, callbackInfo);
            }
        },
        onAdSourceLoadFail : function(placementId, errorInfo, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onAdSourceLoadFail != null && undefined != this.developerCallback.onAdSourceLoadFail) {
                this.developerCallback.onAdSourceLoadFail(placementId, errorInfo, callbackInfo);
            }
        }
        
    },
    
    loadBanner : function(placementId, settings={}) {

        if (undefined != platformBridge && platformBridge != null) {
			
			if (settings.hasOwnProperty(ATBannerSDK.kATBannerAdLoadingExtraBannerAdSizeStruct)) {
				var loadAdSize = settings[ATBannerSDK.kATBannerAdLoadingExtraBannerAdSizeStruct];
				settings["width"] = loadAdSize["width"];
				settings["height"] = loadAdSize["height"];
			}
			
            platformBridge.loadBanner(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },
    
    setAdListener : function(listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey]= "ATBannerSDK.ATBannerListener.onBannerAdLoaded",
        eventJSON[LoadFailCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdLoadFail",
        eventJSON[CloseCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdCloseButtonTapped",
        eventJSON[ClickCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdClick",
        eventJSON[ShowCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdShow",
        eventJSON[RefreshCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdAutoRefresh",
        eventJSON[RefreshFailCallbackKey]=  "ATBannerSDK.ATBannerListener.onBannerAdAutoRefreshFail",
        //added v5.8.10
        eventJSON[BiddingAttempt]= "ATBannerSDK.ATBannerListener.onAdSourceBiddingAttempt",
        eventJSON[BiddingFilled]= "ATBannerSDK.ATBannerListener.onAdSourceBiddingFilled",
        eventJSON[BiddingFail]= "ATBannerSDK.ATBannerListener.onAdSourceBiddingFail",
        eventJSON[Attemp]= "ATBannerSDK.ATBannerListener.onAdSourceAttemp",
        eventJSON[LoadFilled]= "ATBannerSDK.ATBannerListener.onAdSourceLoadFilled",
        eventJSON[LoadFail]= "ATBannerSDK.ATBannerListener.onAdSourceLoadFail"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
        } else {
            cc.log("You must run on Android or iOS.");
        }

        this.ATBannerListener.developerCallback = listener;
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

    showAdInPosition : function(placementId, position) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInPosition(placementId, position);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInPositionAndScenario : function(placementId, position, scenario) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInPosition(placementId, position, scenario);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInRectangle : function(placementId, showAdRect) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInRectangle(placementId, JSON.stringify(showAdRect));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInRectangleAndScenario : function(placementId, showAdRect, scenario) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInRectangle(placementId, JSON.stringify(showAdRect), scenario);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    removeAd : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.removeAd(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    reShowAd : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.reShowAd(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    hideAd : function(placementId) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.hideAd(placementId);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    createLoadAdSize : function(width, height) {
    	var loadAdSize = {};
    	loadAdSize["width"] = width;
    	loadAdSize["height"] = height;
    	return loadAdSize;
    },

    createShowAdRect : function(x, y, width, height) {
    	var adRect = {};
    	adRect["x"] = x;
    	adRect["y"] = y;
    	adRect["width"] = width;
    	adRect["height"] = height;
    	return adRect;
    }

  
};

const LoadedCallbackKey = "BannerLoaded";
const LoadFailCallbackKey = "BannerLoadFail";
const CloseCallbackKey = "BannerCloseButtonTapped";
const ClickCallbackKey = "BannerClick";
const ShowCallbackKey = "BannerShow";
const RefreshCallbackKey = "BannerRefresh";
const RefreshFailCallbackKey = "BannerRefreshFail";

const BiddingAttempt = "BannerBiddingAttempt";
const BiddingFilled = "BannerBiddingFilled";
const BiddingFail = "BannerBiddingFail";
const Attemp = "BannerAttemp";
const LoadFilled = "BannerLoadFilled";
const LoadFail = "BannerLoadFail";

window["ATBannerSDK"] = ATBannerSDK;