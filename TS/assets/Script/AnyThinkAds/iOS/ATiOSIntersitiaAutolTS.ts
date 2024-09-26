import {ATiOSJS} from "./ATiOSJS";
const OC_WRAPPER_CLASS = "ATInterstitialAutoAdWrapper";
export const ATiOSInterstitialAutoAdTS  = {

    setAdListener : function (listener) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::setAdListener(" + listener + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "setDelegates:", listener);
    },

    setAdExtraData : function (placementId, extra) {
        
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::setAdExtraData(" + placementId + ", " + extra + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "setAutoLocalExtra:customDataJSONString:", placementId, extra);
    },   
    
    addPlacementIds : function (placementId) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::addPlacementIds(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "addAutoLoadAdPlacementID:", placementId);
    },    

    removePlacementId : function (placementId) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::removePlacementId(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "removeAutoLoadAdPlacementID:", placementId);
    }, 

    hasAdReady : function (placementId) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "autoLoadInterstitialAdReadyForPlacementID:", placementId);
    }, 

    checkAdStatus : function (placementId) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::hasAdReady(" + placementId + ")");
        return jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "checkAutoAdStatus:",placementId);
    }, 

    entryAdScenario : function(placementId, scenario) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::entryAdScenario(" + placementId  + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "entryAutoAdScenarioWithPlacementID:scenarioID:", placementId, scenario);
    },


    showAd : function(placementId) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::showAd(" + placementId + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showAutoInterstitialAdWithPlacementID:scenarioID:", placementId, null);   
    },

    showAdInScenario : function(placementId, scenario) {
        ATiOSJS.printJsLog("ATiOSInterstitialAutoAdJS::showAdInScenario(" + placementId  + ", " + scenario + ")");
        jsb.reflection.callStaticMethod(OC_WRAPPER_CLASS, "showAutoInterstitialAdWithPlacementID:scenarioID:", placementId, scenario);
    },

};
