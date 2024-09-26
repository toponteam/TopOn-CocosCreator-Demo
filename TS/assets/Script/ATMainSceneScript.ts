import * as cc from 'cc';
import { _decorator, Component, Node } from 'cc';
import {ATJSSDK} from "./AnyThinkAds/ATJSSDK";
import {ATiOSBannerJS} from "db://assets/Script/AnyThinkAds/iOS/ATiOSBannerTS";
import {ATAndroidBannerTS} from "db://assets/Script/AnyThinkAds/Android/ATAndroidBannerTS";

const { ccclass, property } = _decorator;
 
@ccclass('AnyThinkMainScript')
export class AnyThinkMainScript extends Component {
   

    start () {
        console.log("start");
        cc.log("Main Scene start.......");

        ATJSSDK.setLogDebug(true);

        var customMap = {
            "appCustomKey1": "appCustomValue1",
            "appCustomKey2" : "appCustomValue2"
        };
        ATJSSDK.initCustomMap(customMap);

        //RewardedVideo PlacementID
        var customPlacementId = "";
        if (cc.sys.os === cc.sys.OS.IOS) {           
            customPlacementId = "b5b44a0f115321";
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            customPlacementId = "b5b449fb3d89d7";
        }
        var placementCustomMap = {
            "placementCustomKey1": "placementCustomValue1",
            "placementCustomKey2" : "placementCustomValue2"
        };

        ATJSSDK.setPlacementCustomMap(customPlacementId, placementCustomMap);
        // ATJSSDK.setGDPRLevel(ATJSSDK.PERSONALIZED); 

        var GDPRLevel = ATJSSDK.getGDPRLevel();
        ATJSSDK.printLog("Current GDPR Level :" + GDPRLevel);


        if (cc.sys.os === cc.sys.OS.IOS) {           
            ATJSSDK.initSDK("a5b0e8491845b3", "7eae0567827cfe2b22874061763f30c9");
        } else if (cc.sys.os === cc.sys.OS.ANDROID) {
            ATJSSDK.initSDK("a5aa1f9deda26d", "4f7b9ac17decb9babec83aac078742c7");
        }

        
        ATJSSDK.getUserLocation(function (userLocation) {
             ATJSSDK.printLog("getUserLocation callback userLocation :" + userLocation);

            if (userLocation === ATJSSDK.kATUserLocationInEU) {
                if(ATJSSDK.getGDPRLevel() === ATJSSDK.UNKNOWN) {
                    ATJSSDK.showGDPRAuth();
                }
            }
        });
        // [3]
    }

    click(): void {
        console.log("click");
    }
    
    gotoReward():void{
        cc.director.loadScene("RewardScene");
    }

    gotoIntersitial():void{
        cc.director.loadScene("IntersitialScene");
    }

    gotoBanner():void{
        cc.director.loadScene("BannerScene");
    }

    gotoNative():void{
        cc.director.loadScene("NativeScene");
    }

    gotoAutoReward():void{
        cc.director.loadScene("RewardAutoScene");
    }

    gotoAutoIntersitial():void{
        cc.director.loadScene("IntersitialAutoScene");
    }

    showDebuggerUI():void{
        ATJSSDK.showDebuggerUI("");
    }
}


