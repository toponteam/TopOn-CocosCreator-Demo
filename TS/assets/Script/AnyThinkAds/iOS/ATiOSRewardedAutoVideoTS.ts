import {ATiOSJS} from "./ATiOSJS";
const OC_RV_WRAPPER_CLASS = "ATRewardedVideoAutoAdWrapper";

export const ATiOSRewardedVideoAutoAdJS =  {

    setAdListener : function (listener) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::setAdListener(" + listener + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "setDelegates:", listener);
    },

    setAdExtraData : function (placementId, extra) {

        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::setAdExtraData(" + placementId + ", " + extra + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "setAutoLocalExtra:customDataJSONString:", placementId, extra);
    },   
    
    addPlacementIds : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::addPlacementIds(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "addAutoLoadAdPlacementID:", placementId);
    },    

    removePlacementId : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::removePlacementId(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "removeAutoLoadAdPlacementID:", placementId);
    }, 

    hasAdReady : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "autoLoadRewardedVideoReadyForPlacementID:", placementId);
    }, 

    checkAdStatus : function (placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "checkAutoAdStatus:", placementId);
    }, 
    
    entryAdScenario : function(placementId, scenario) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::entryAdScenario(" + placementId  + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "entryAutoAdScenarioWithPlacementID:scenarioID:", placementId, scenario);
    },

    showAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::showAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "showAutoRewardedVideoWithPlacementID:scenarioID:", placementId, null);   
    },

    showAdInScenario : function(placementId, scenario) {
        ATiOSJS.printJsLog("ATiOSRewardedVideoAutoAdJS::showAdInScenario(" + placementId  + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_RV_WRAPPER_CLASS, "showAutoRewardedVideoWithPlacementID:scenarioID:", placementId, scenario);
    } 

};

