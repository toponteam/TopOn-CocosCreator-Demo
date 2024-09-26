//
//  ATRewardedVideoWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATRewardedVideoWrapper.h"
#import <AnyThinkRewardedVideo/AnyThinkRewardedVideo.h>

@interface ATRewardedVideoWrapper()<ATRewardedVideoDelegate>
@end

static NSString *const kDelegatesLoadedKey = @"RewardedVideoLoaded";
static NSString *const kDelegatesLoadFailedKey = @"RewardedVideoLoadFail";
static NSString *const kDelegatesVideoStartKey = @"RewardedVideoPlayStart";
static NSString *const kDelegatesVideoEndKey = @"RewardedVideoPlayEnd";
static NSString *const kDelegatesCloseKey = @"RewardedVideoClose";
static NSString *const kDelegatesClickKey = @"RewardedVideoClick";
static NSString *const kDelegatesRewardedKey = @"RewardedVideoReward";
static NSString *const kDelegatesFailedToPlayKey = @"RewardedVideoPlayFail";

static NSString *const kShowExtraSceneKey = @"scenario";
@implementation ATRewardedVideoWrapper
+(instancetype) sharedWrapper {
    static ATRewardedVideoWrapper *sharedWrapper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedWrapper = [[ATRewardedVideoWrapper alloc] init];
    });
    return sharedWrapper;
}

+(void) loadRewardedVideoWithPlacementID:(NSString*)placementID extra:(NSString*)extraJsonStr {
    NSLog(@"ATRewardedVideoWrapper::loadRewardedVideoWithPlacementID:%@ extra:%@", placementID, extraJsonStr);
    NSMutableDictionary *extra = nil;
    if (extraJsonStr != nil) {
        NSDictionary *rawExtra = [NSJSONSerialization JSONObjectWithString:extraJsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([rawExtra isKindOfClass:[NSDictionary class]]) { extra = [[NSMutableDictionary alloc] initWithDictionary:rawExtra];}
        if (![extra[kATAdLoadingExtraMediaExtraKey] isKindOfClass:[NSString class]]) { [extra removeObjectForKey:kATAdLoadingExtraMediaExtraKey];}
    }
    NSLog(@"ATRewardedVideoWrapper::extra:%@",extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSMutableDictionary class]] ? extra : nil delegate:[ATRewardedVideoWrapper sharedWrapper]];
}

+(BOOL) rewardedVideoReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] rewardedVideoReadyForPlacementID:placementID];
}

+(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkRewardedVideoLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATRewardedVideoWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJS;
}

+(void) showRewardedVideoWithPlacementID:(NSString*)placementID scene:(NSString*)scene {
    NSLog(@"ATRewardedVideoWrapper::showRewardedVideoWithPlacementID:%@ scene:%@", placementID, scene);
    [[ATAdManager sharedManager] showRewardedVideoWithPlacementID:placementID scene:scene inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[ATRewardedVideoWrapper sharedWrapper]];
}
#pragma mark - delegates
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATRewardedVideoWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATRewardedVideoWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];
}

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidStartPlayingForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidEndPlayingForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidFailToPlayForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error, [extra jsonString_AnyThinkJS]]];
}

-(void) rewardedVideoDidCloseForPlacementID:(NSString*)placementID rewarded:(BOOL)rewarded extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidCloseForPlacementID:%@ rewarded:%@ extra:%@", placementID, rewarded ? @"1" : @"0", extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseKey], placementID, [extra jsonString_AnyThinkJS]]];
    } else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseKey], placementID, [extra jsonString_AnyThinkJS]]];
        });
    }
}

-(void) rewardedVideoDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidClickForPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidRewardSuccessForPlacemenID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesRewardedKey], placementID, [extra jsonString_AnyThinkJS]]];
}
@end
