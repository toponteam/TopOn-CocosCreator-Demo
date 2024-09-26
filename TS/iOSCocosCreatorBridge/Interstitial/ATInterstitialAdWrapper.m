//
//  ATInterstitialAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATInterstitialAdWrapper.h"
#import <AnyThinkInterstitial/AnyThinkInterstitial.h>

@interface ATInterstitialAdWrapper()<ATInterstitialDelegate>
@end
static NSString *const kDelegatesLoadedKey = @"InterstitialLoaded";
static NSString *const kDelegatesLoadFailedKey = @"InterstitialLoadFail";
static NSString *const kDelegatesShowKey = @"InterstitialAdShow";
static NSString *const kDelegatesVideoStartKey = @"InterstitialPlayStart";
static NSString *const kDelegatesVideoEndKey = @"InterstitialPlayEnd";
static NSString *const kDelegatesCloseKey = @"InterstitialClose";
static NSString *const kDelegatesClickKey = @"InterstitialClick";
static NSString *const kDelegatesFailedToPlayKey = @"InterstitialPlayFail";
static NSString *const kDelegatesFailedToShowKey = @"InterstitialAdFailedToShow";


static NSString *const kDelegatesBiddingAttemptKey = @"InterstitialBiddingAttempt";
static NSString *const kDelegatesBiddingFilledKey = @"InterstitialBiddingFilled";
static NSString *const kDelegatesBiddingFailKey = @"InterstitialBiddingFail";
static NSString *const kDelegatesAttempKey = @"InterstitialAttemp";
static NSString *const kDelegatesLoadFilledKey = @"InterstitialLoadFilled";
static NSString *const kDelegatesLoadFailKey = @"InterstitialLoadFail";

@implementation ATInterstitialAdWrapper
+(instancetype) sharedWrapper {
    static ATInterstitialAdWrapper *sharedWrapper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedWrapper = [[ATInterstitialAdWrapper alloc] init];
    });
    return sharedWrapper;
}

+(void) loadInterstitialWithPlacementID:(NSString*)placementID extra:(NSString*)extraJsonStr {
    NSLog(@"ATInterstitialAdWrapper::loadInterstitialWithPlacementID:%@ extra:%@", placementID, extraJsonStr);
    NSDictionary *extra = nil;
    if (extraJsonStr != nil) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithString:extraJsonStr options:NSJSONReadingAllowFragments error:nil];
        NSLog(@"extraDict = %@", extraDict);
        if (extraDict[kLoadUseRVAsInterstitialKey] != nil) {
            extra = @{kATInterstitialExtraUsesRewardedVideo:@([extraDict[kLoadUseRVAsInterstitialKey] boolValue])};
        }
    }
    NSLog(@"loadInterstitialExtra = %@", extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSDictionary class]] ? extra : nil delegate:[ATInterstitialAdWrapper sharedWrapper]];
}

+(BOOL) interstitialReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATInterstitialAdWrapper::interstitialReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] interstitialReadyForPlacementID:placementID];
}

+(void) entryAdScenarioWithPlacementID:(NSString*)placementID scenarioID:(NSString*)scenarioID{
    NSLog(@"Unity: 🔥🔥entryAdScenarioWithPlacementID::array = %@ scenarioID:%@", placementID,scenarioID);
    [[ATAdManager sharedManager] entryInterstitialScenarioWithPlacementID:placementID scene:scenarioID];
}


+(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkInterstitialLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATInterstitialAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJSAdInfo;
}

+(void) showInterstitialWithPlacementID:(NSString*)placementID scene:(NSString*)scene {
    NSLog(@"ATInterstitialAdWrapper::showInterstitialWithPlacementID:%@ scene:%@", placementID, scene);
    [[ATAdManager sharedManager] showInterstitialWithPlacementID:placementID scene:scene inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[ATInterstitialAdWrapper sharedWrapper]];
}

#pragma mark - delegates

- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{

    NSLog(@"ATInterstitialAdWrapper::didStartLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAttempKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAttempKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }

}

- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATInterstitialAdWrapper::didFinishLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }

}

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    
    NSLog(@"ATInterstitialAdWrapper::didFailToLoadADSourceWithPlacementID:%@ error:%@", placementID, error);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }
}

- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{

    
    NSLog(@"ATInterstitialAdWrapper::didStartBiddingADSourceWithPlacementID:%@ extra:%@,extraStr:%@ kDelegatesBiddingAttemptKey:%@", placementID, extra,[extra jsonString_AnyThinkJS],self.delegates[kDelegatesBiddingAttemptKey]);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingAttemptKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingAttemptKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }
}

- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATInterstitialAdWrapper::didFinishBiddingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }

}

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    NSLog(@"ATInterstitialAdWrapper::didFailBiddingADSourceWithPlacementID:%@ error:%@", placementID, error);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
        });
    }
    
}



-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATInterstitialAdWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];

        });
    }
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID  error:(NSError*)error {
    NSLog(@"ATInterstitialAdWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailKey], placementID, error]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailKey], placementID, error]];
        });
    }
    

}

-(void) interstitialDidShowForPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidShowForPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
    
}

-(void) interstitialFailedToShowForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialFailedToShowForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToShowKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToShowKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }
    
}

-(void) interstitialDidFailToPlayVideoForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidFailToPlayVideoForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }
    
}

-(void) interstitialDidStartPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidStartPlayingVideoForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) interstitialDidEndPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidEndPlayingVideoForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) interstitialDidCloseForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidCloseForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }
}

-(void) interstitialDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidClickForPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }
    
}
@end
