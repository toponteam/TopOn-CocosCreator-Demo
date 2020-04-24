
var ATAndroidJS = require("./Android/ATAndroidJS");
var ATiOSJS = require("./iOS/ATiOSJS");
var isDebugLog = false;

var initPlatformBridge = function() {  
    if (cc.sys.os === cc.sys.OS_IOS) {           
        return ATiOSJS;
    } else if (cc.sys.os === cc.sys.OS_ANDROID) {
        return ATAndroidJS;
    }
};

var platformBridge = initPlatformBridge();


var ATSDK = ATSDK || {

    kATUserLocationUnknown : 0,
    kATUserLocationInEU : 1,
    kATUserLocationOutOfEU : 2,


    PERSONALIZED : 0,
    NONPERSONALIZED :1,
    UNKNOWN : 2,

    ATSDKListener : {
        userLocationCallback : null,

        getUserLocationCallback : function(userLocation) {
            if(undefined != this.userLocationCallback && this.userLocationCallback != null ){
                this.userLocationCallback(userLocation);
            }
        }
    },
    
    initSDK : function(appId, appKey) {

        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.initSDK(appId, appKey);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    initCustomMap : function(customMap) {
        if (undefined != platformBridge && platformBridge != null) {
            if(undefined != customMap && customMap != null) {
                platformBridge.initCustomMap(JSON.stringify(customMap));
            }
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setPlacementCustomMap : function(placmentId, customMap) {
        if (undefined != platformBridge && platformBridge != null) {
            if(undefined != customMap && customMap != null) {
                 platformBridge.setPlacementCustomMap(placmentId, JSON.stringify(customMap));
            }
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setGDPRLevel : function(level) {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setGDPRLevel(level);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    getGDPRLevel : function() {
        if (undefined != platformBridge && platformBridge != null) {
            return platformBridge.getGDPRLevel();
        } else {
            cc.log("You must run on Android or iOS.");
        }
        return this.UNKNOWN;
    },

    getUserLocation : function(userLocationCallback) {
        this.ATSDKListener.userLocationCallback = userLocationCallback;
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.getUserLocation(GetUserLocationJsCallback);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showGDPRAuth : function () {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.showGDPRAuth();
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    setLogDebug : function (debug) {
        isDebugLog = debug;
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.setLogDebug(debug);
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    printLog : function(msg) {
        if (undefined != msg && null != msg && isDebugLog && platformBridge != null ) {
            if (undefined != platformBridge && platformBridge != null) {
                platformBridge.printJsLog(msg); 
            } else {
                cc.log("You must run on Android or iOS.");
            }
                
        }
    }
  
};

const GetUserLocationJsCallback = "ATJSSDK.ATSDKListener.getUserLocationCallback";

window.ATJSSDK = ATSDK;