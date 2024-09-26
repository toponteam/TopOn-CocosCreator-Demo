
var ATAndroidNativeJS = require("./Android/ATAndroidNativeJS");
var ATiOSNativeJS = require("./iOS/ATiOSNativeJS");
var initPlatformBridge = function() {  
    if (cc.sys.os === cc.sys.OS_IOS) {           
        return ATiOSNativeJS;
    } else if (cc.sys.os === cc.sys.OS_ANDROID) {
        return ATAndroidNativeJS;
    }
};

var platformBridge = initPlatformBridge();


var ATNativeSDK = ATNativeSDK || {   

    ATNativeListener : {
        developerCallback : null,

        onNativeAdLoaded : function (placementId) {
            if(this.developerCallback != null && this.developerCallback.onNativeAdLoaded != null && undefined != this.developerCallback.onNativeAdLoaded) {
                this.developerCallback.onNativeAdLoaded(placementId);
            }
        },

        onNativeAdLoadFail : function(placementId, errorInfo) {
          if(this.developerCallback != null && this.developerCallback.onNativeAdLoadFail != null && undefined != this.developerCallback.onNativeAdLoadFail) {
                this.developerCallback.onNativeAdLoadFail(placementId, errorInfo);
            }
        },

        onNativeAdShow : function(placementId, callbackInfo) {
           if(this.developerCallback != null && this.developerCallback.onNativeAdShow != null && undefined != this.developerCallback.onNativeAdShow) {
                this.developerCallback.onNativeAdShow(placementId, callbackInfo);
            }
        },

        onNativeAdClick : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onNativeAdClick != null && undefined != this.developerCallback.onNativeAdClick) {
                this.developerCallback.onNativeAdClick(placementId, callbackInfo);
            }
        },

        onNativeAdVideoStart : function(placementId) {
            if(this.developerCallback != null && this.developerCallback.onNativeAdVideoStart != null && undefined != this.developerCallback.onNativeAdVideoStart) {
                this.developerCallback.onNativeAdVideoStart(placementId,);
            }
        },

        onNativeAdVideoEnd : function(placementId) {
            if(this.developerCallback != null && this.developerCallback.onNativeAdVideoEnd != null && undefined !=this.developerCallback.onNativeAdVideoEnd) {
                this.developerCallback.onNativeAdVideoEnd(placementId);
            }
        },

        onNativeAdCloseButtonTapped : function(placementId, callbackInfo) {
            if(this.developerCallback != null && this.developerCallback.onNativeAdCloseButtonTapped != null && undefined != this.developerCallback.onNativeAdCloseButtonTapped) {
                this.developerCallback.onNativeAdCloseButtonTapped(placementId, callbackInfo);
            }
        }
        
    },
    
    loadNative : function(placementId, settings={}) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.loadNative(placementId, JSON.stringify(settings));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setAdListener : function(listener) {
        var eventJSON = {};
        eventJSON[LoadedCallbackKey]="ATNativeJSSDK.ATNativeListener.onNativeAdLoaded",
        eventJSON[LoadFailCallbackKey]= "ATNativeJSSDK.ATNativeListener.onNativeAdLoadFail",
        eventJSON[CloseCallbackKey]="ATNativeJSSDK.ATNativeListener.onNativeAdCloseButtonTapped",
        eventJSON[ClickCallbackKey]= "ATNativeJSSDK.ATNativeListener.onNativeAdClick",
        eventJSON[ShowCallbackKey]= "ATNativeJSSDK.ATNativeListener.onNativeAdShow",
        eventJSON[VideoStartKey]= "ATNativeJSSDK.ATNativeListener.onNativeAdVideoStart",
        eventJSON[VideoEndKey]= "ATNativeJSSDK.ATNativeListener.onNativeAdVideoEnd"

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setAdListener(JSON.stringify(eventJSON));
        } else {
            cc.log("You must run on Android or iOS.");
        }

        this.ATNativeListener.developerCallback = listener;
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

    showAd : function(placementId, adViewProperty) {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAd(placementId, JSON.stringify(adViewProperty.getAdViewProperty()));
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showAdInScenario : function(placementId, adViewProperty, scenario="") {
        if (undefined != platformBridge && platformBridge != null) {
           platformBridge.showAdInScenario(placementId, JSON.stringify(adViewProperty.getAdViewProperty()), scenario);
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

    createLoadAdSize : function(width, height) {
        var loadAdSize = {};
        loadAdSize["width"] = width;
        loadAdSize["height"] = height;
        return loadAdSize;
    },

    AdViewProperty : cc.Class({

        parent : null,
        appIcon : null,
        mainImage: null,
        title:null,
        desc:null,
        adLogo:null,
        cta:null,
        rating:null,
        dislike:null,

        createItemViewProperty: function(x, y ,width ,height ,backgroundColor ,textColor ,textSize, isCustomClick = false) {
            var itemProperty = {};
            itemProperty["x"] = x;
            itemProperty["y"] = y;
            itemProperty["width"] = width;
            itemProperty["height"] = height;
            itemProperty["backgroundColor"] = backgroundColor;
            itemProperty["textColor"] = textColor;
            itemProperty["textSize"] = textSize;
            itemProperty["isCustomClick"] = isCustomClick;

            return itemProperty;
        },

        getAdViewProperty: function() {
            var nativeViewProperty = {};

            if(this.parent != null){
                nativeViewProperty["parent"] = this.parent; 
            }
            
            if(this.appIcon != null){
                nativeViewProperty["icon"] = this.appIcon;
            }
            
            if(this.mainImage != null){
                nativeViewProperty["mainImage"] = this.mainImage;
            }
           
            if(this.title != null){
                nativeViewProperty["title"] = this.title;
            }
           
            if(this.desc != null){
                nativeViewProperty["desc"] = this.desc;
            }

            if(this.adLogo != null){
                nativeViewProperty["adLogo"] = this.adLogo;
            }

            if(this.cta != null){
                nativeViewProperty["cta"] = this.cta;
            }

            if(this.rating != null){
                nativeViewProperty["rating"] = this.rating;
            }

            if(this.dislike != null){
                nativeViewProperty["dislike"] = this.dislike;
            }
           
            return nativeViewProperty;
        }
    })
  
};

const LoadedCallbackKey = "NativeLoaded";
const LoadFailCallbackKey = "NativeLoadFail";
const CloseCallbackKey = "NativeCloseButtonTapped";
const ClickCallbackKey = "NativeClick";
const ShowCallbackKey = "NativeShow";
const VideoStartKey = "NativeVideoStart";
const VideoEndKey = "NativeVideoEnd";

window.ATNativeJSSDK = ATNativeSDK;