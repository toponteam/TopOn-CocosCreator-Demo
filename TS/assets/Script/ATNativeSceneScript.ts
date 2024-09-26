import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATNativeTSSDK,AdViewProperty} from "./AnyThinkAds/ATNativeTSSDK"

const { ccclass, property } = _decorator;

 
@ccclass('ATNativeSceneScript')
export class ATNativeSceneScript extends Component {


    placementID() {
        if (cc.sys.os === cc.sys.OS.IOS) {           
            return "b5b0f5663c6e4a";
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            return "b5aa1fa2cae775";
        } return "b5b0f5663c6e4a";
    }

    start () {
        ATNativeTSSDK.setAdListener(this);
        // [3]
    }

    loadAD(){
        ATNativeTSSDK.loadNative(this.placementID(), ATNativeTSSDK.createLoadAdSize(cc.view.getFrameSize().width, cc.view.getFrameSize().width * 4/5));

        printLog("loadAd", this.placementID(), "", "")
    }

    showAd(){
        var frameSize = cc.view.getFrameSize();
        var frameWidth = frameSize.width;
        var frameHeight = frameSize.height;
        var padding = frameSize.width / 35;

        var parentWidth = frameWidth;
        var parentHeight = frameWidth * 4/5;
        var appIconSize = frameWidth/7;


        var nativeAdViewProperty =  new AdViewProperty();
        nativeAdViewProperty.parent = nativeAdViewProperty.createItemViewProperty(0, frameHeight - parentHeight,parentWidth,parentHeight,"#ffffff","",0);

        nativeAdViewProperty.appIcon = nativeAdViewProperty.createItemViewProperty(0 ,parentHeight - appIconSize, appIconSize ,appIconSize ,"","",0);
        nativeAdViewProperty.cta = nativeAdViewProperty.createItemViewProperty(parentWidth-appIconSize*2 ,parentHeight - appIconSize ,appIconSize*2 ,appIconSize ,"#2095F1" ,"#ffffff" , appIconSize/3);

        nativeAdViewProperty.mainImage = nativeAdViewProperty.createItemViewProperty(padding ,padding, parentWidth-2*padding ,parentHeight - appIconSize - 2*padding,"#ffffff","#ffffff",14);
        
        nativeAdViewProperty.title = nativeAdViewProperty.createItemViewProperty(appIconSize + padding ,parentHeight - appIconSize , parentWidth - 3* appIconSize -2 * padding ,appIconSize/2 ,"" ,"#000000" , appIconSize/3);
        nativeAdViewProperty.desc = nativeAdViewProperty.createItemViewProperty(appIconSize + padding ,parentHeight - appIconSize/2 , parentWidth - 3* appIconSize -2 * padding ,appIconSize/2 ,"#ffffff" ,"#000000" , appIconSize/4);
        // nativeAdViewProperty.adLogo = nativeAdViewProperty.createItemViewProperty(0,0,0,0,"#ffffff","#ffffff",14);
        // nativeAdViewProperty.rating = nativeAdViewProperty.createItemViewProperty(0,0,0,0,"#ffffff","#ffffff",14);
        nativeAdViewProperty.dislike = nativeAdViewProperty.createItemViewProperty(parentWidth - appIconSize/2,0,appIconSize/2,appIconSize/2,"#00ffffff","#ffffff",14);
        nativeAdViewProperty.elements = nativeAdViewProperty.createItemViewProperty(0, parentHeight - appIconSize/2, parentWidth, appIconSize/2,"#7f000000", "#ffffff",14) 
        
        ATNativeTSSDK.showAd(this.placementID(), nativeAdViewProperty);
        // ATNativeTSSDK.showAdInScenario(this.placementID(), nativeAdViewProperty, "f600e5f8b80c14");

        printLog("showAd", this.placementID(), "", "")
    }

    isReady(){
        var hasReady = ATNativeTSSDK.hasAdReady(this.placementID());
        ATJSSDK.printLog("AnyThinkNativeDemo::checkReady() " + (hasReady ? "Ready" : "NO"));

        var adStatusInfo = ATNativeTSSDK.checkAdStatus(this.placementID());
        ATJSSDK.printLog("AnyThinkNativeDemo::checkAdStatus()   " + adStatusInfo);
    }

    removeAD(){
        ATNativeTSSDK.removeAd(this.placementID());

        printLog("removeAd", this.placementID(), "", "")
    }

    back(){
        cc.director.loadScene("MainScene");
    }

    //Callbacks
    onNativeAdLoaded(placementId){
        printLog("onNativeAdLoaded", placementId, "", "")
    }

    onNativeAdLoadFail(placementId, errorInfo) {
        printLog("onNativeAdLoadFail", placementId, "", errorInfo)
    }

    onNativeAdShow(placementId, callbackInfo) {
        printLog("onNativeAdShow", placementId, callbackInfo, "")
    }

    onNativeAdClick(placementId, callbackInfo) {
        printLog("onNativeAdClick", placementId, callbackInfo, "")
    }

    onNativeAdVideoStart(placementId) {
        printLog("onNativeAdVideoStart", placementId, "", "")
    }

    onNativeAdVideoEnd(placementId) {
        printLog("onNativeAdVideoEnd", placementId, "", "")
    }

    onNativeAdCloseButtonTapped(placementId, callbackInfo) {
        printLog("onNativeAdCloseButtonTapped", placementId, callbackInfo, "")

        this.removeAD();
    }

    //Callbacks added v5.8.10
    onAdSourceBiddingAttempt(placementId, callbackInfo) {
        printLog("onAdSourceBiddingAttempt", placementId, callbackInfo, "")
    }

    onAdSourceBiddingFilled(placementId, callbackInfo) {
        printLog("onAdSourceBiddingFilled", placementId, callbackInfo, "")
    }

    onAdSourceBiddingFail(placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceBiddingFail", placementId, callbackInfo, errorInfo)
    }

    onAdSourceAttemp(placementId, callbackInfo) {
        printLog("onAdSourceAttemp", placementId, callbackInfo, "")
    }

    onAdSourceLoadFilled(placementId, callbackInfo) {
        printLog("onAdSourceLoadFilled", placementId, callbackInfo, "")
    }

    onAdSourceLoadFail(placementId, errorInfo, callbackInfo) {
        printLog("onAdSourceLoadFail", placementId, callbackInfo, errorInfo)
    }
}

let printLog = function(methodName, placementId, callbackInfo, errorInfo) {
    ATJSSDK.printLogWithParams("AnyThinkNativeDemo", methodName, placementId, callbackInfo, errorInfo)
}



