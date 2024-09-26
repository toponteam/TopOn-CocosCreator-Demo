//
//  ATInterstitialAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATInterstitialAdWrapper.h"
#import <AnyThinkInterstitial/AnyThinkInterstitial.h>

NSString *const kLoadUseRVAsInterstitialKey = @"UseRewardedVideoAsInterstitial";
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

+(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkInterstitialLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATInterstitialAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJS;
}

+(void) showInterstitialWithPlacementID:(NSString*)placementID scene:(NSString*)scene {
    NSLog(@"ATInterstitialAdWrapper::showInterstitialWithPlacementID:%@ scene:%@", placementID, scene);
    [[ATAdManager sharedManager] showInterstitialWithPlacementID:placementID scene:scene inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[ATInterstitialAdWrapper sharedWrapper]];
}

#pragma mark - delegates

-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATInterstitialAdWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATInterstitialAdWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];
}

-(void) interstitialDidShowForPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidShowForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) interstitialFailedToShowForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialFailedToShowForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToShowKey], placementID, error, [extra jsonString_AnyThinkJS]]];
}

-(void) interstitialDidFailToPlayVideoForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidFailToPlayVideoForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error, [extra jsonString_AnyThinkJS]]];
}

-(void) interstitialDidStartPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidStartPlayingVideoForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) interstitialDidEndPlayingVideoForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATInterstitialAdWrapper::interstitialDidEndPlayingVideoForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];
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
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
}
@end
