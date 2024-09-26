
import {ATAndroidJS} from "./Android/ATAndroidJS"
import {ATiOSJS} from "./iOS/ATiOSJS";

import { _decorator, Component, Node } from 'cc';
const { ccclass, property } = _decorator;

var isDebugLog = false;

var initPlatformBridge = function() {  
    if (cc.sys.os === cc.sys.OS.IOS) {           
        return ATiOSJS;
    } else if (cc.sys.os === cc.sys.OS.ANDROID) {
        return ATAndroidJS;
    }
};

var platformBridge = initPlatformBridge();

export const ATJSSDK = {

    kATUserLocationUnknown : 0,
    kATUserLocationInEU : 1,
    kATUserLocationOutOfEU : 2,


    PERSONALIZED : 0,
    NONPERSONALIZED :1,
    UNKNOWN : 2,
    
    
    //for android and ios
    OS_VERSION_NAME : "os_vn",
    OS_VERSION_CODE : "os_vc",
    APP_PACKAGE_NAME : "package_name",
    APP_VERSION_NAME : "app_vn",
    APP_VERSION_CODE : "app_vc",

    BRAND : "brand",
    MODEL : "model",
    DEVICE_SCREEN_SIZE : "screen",
    MNC : "mnc",
    MCC : "mcc",

    LANGUAGE : "language",
    TIMEZONE : "timezone",
    USER_AGENT : "ua",
    ORIENTATION : "orient",
    NETWORK_TYPE : "network_type",

    //for android
    INSTALLER : "it_src",
    ANDROID_ID : "android_id",
    GAID : "gaid",
    MAC : "mac",
    IMEI : "imei",
    OAID : "oaid",
    
    //for ios
    IDFA : "idfa",
    IDFV : "idfv",

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
    },

    printLogWithParams : function(tag, methodName, placementId, callbackInfo, errorInfo) {
        this.printLog(tag + "::" + methodName + "()" + "\nplacementId=" + placementId + "\ncallbackInfo=" + callbackInfo + "\nerrorInfo=" + errorInfo);
    },

    deniedUploadDeviceInfo : function (deniedInfo) {
        if (undefined != platformBridge && platformBridge != null) {
			
			if (deniedInfo != null) {
				var length = deniedInfo.length;
				var deniedInfoString = "";
				for (var i = 0; i < length; i++) {
					var info = deniedInfo[i];
					if (i == 0) {
						deniedInfoString = info;
					} else {
						deniedInfoString = deniedInfoString + "," + info;
					}
				}
				
				cc.log("test__" + deniedInfoString)
				
				platformBridge.deniedUploadDeviceInfo(deniedInfoString);
			}
			
        } else {
            cc.log("You must run on Android or iOS.");
        }
    },

    showDebuggerUI : function (debugKey) {
        if (undefined != platformBridge && platformBridge != null) {
            platformBridge.showDebuggerUI(debugKey);
        }
    }
  
};

const GetUserLocationJsCallback = " ATJSSDK.ATSDKListener.getUserLocationCallback";

const VersionCode = 'v1.1.0';

window["ATJSSDK"] = ATJSSDK;
