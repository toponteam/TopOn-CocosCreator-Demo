
var ATAndroidBannerJS = require("./Android/ATAndroidBannerJS");
var ATiOSBannerJS = require("./iOS/ATiOSBannerJS");

var initPlatformBridge = function() {  
    if (cc.sys.os === cc.sys.OS_IOS) {           
        return ATiOSBannerJS;
    } else if (cc.sys.os === cc.sys.OS_ANDROID) {
        return ATAndroidBannerJS;
    }
};

var platformBridge = initPlatformBridge();


var ATBannerSDK = ATBannerSDK || {
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
        eventJSON[LoadedCallbackKey]= "ATBannerJSSDK.ATBannerListener.onBannerAdLoaded",
        eventJSON[LoadFailCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdLoadFail",
        eventJSON[CloseCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdCloseButtonTapped",
        eventJSON[ClickCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdClick",
        eventJSON[ShowCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdShow",
        eventJSON[RefreshCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdAutoRefresh",
        eventJSON[RefreshFailCallbackKey]=  "ATBannerJSSDK.ATBannerListener.onBannerAdAutoRefreshFail"
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

window.ATBannerJSSDK = ATBannerSDK;