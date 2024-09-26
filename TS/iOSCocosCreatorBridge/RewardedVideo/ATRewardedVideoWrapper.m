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

// ad bid
static NSString *const kDelegatesBiddingAttemptKey = @"RewardedVideoBiddingAttempt";
static NSString *const kDelegatesBiddingFilledKey = @"RewardedVideoBiddingFilled";
static NSString *const kDelegatesBiddingFailKey = @"RewardedVideoBiddingFail";
static NSString *const kDelegatesAttempKey = @"RewardedVideoAttemp";
static NSString *const kDelegatesLoadFilledKey = @"RewardedVideoLoadFilled";
static NSString *const kDelegatesLoadFailKey = @"RewardedVideoLoadFail";

// again
static NSString *const kDelegatesAgainPlayStartCallbackKey = @"RewardedVideoAgainPlayStart";
static NSString *const kDelegatesAgainPlayEndCallbackKey = @"RewardedVideoAgainPlayEnd";
static NSString *const kDelegatesAgainPlayFailCallbackKey = @"RewardedVideoAgainPlayFail";
static NSString *const kDelegatesAgainClickCallbackKey = @"RewardedVideoAgainClick";
static NSString *const kDelegatesAgainRewardCallbackKey = @"RewardedVideoAgainReward";


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
        
        if ([rawExtra isKindOfClass:[NSDictionary class]]) {
            extra = [[NSMutableDictionary alloc] initWithDictionary:rawExtra];
        }
        if (![extra[kATAdLoadingExtraMediaExtraKey] isKindOfClass:[NSString class]]) {
            [extra removeObjectForKey:kATAdLoadingExtraMediaExtraKey];
        }
    }
    NSLog(@"ATRewardedVideoWrapper::extra:%@",extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSMutableDictionary class]] ? extra : nil delegate:[ATRewardedVideoWrapper sharedWrapper]];
}

+(BOOL) rewardedVideoReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] rewardedVideoReadyForPlacementID:placementID];
}

+(void) entryAdScenarioWithPlacementID:(NSString*)placementID scenarioID:(NSString*)scenarioID{
    NSLog(@"Unity: entryAdScenarioWithPlacementID::array = %@ scenarioID:%@", placementID,scenarioID);
    [[ATAdManager sharedManager] entryRewardedVideoScenarioWithPlacementID:placementID scene:scenarioID];
}

+(NSString*) checkAdStatus:(NSString *)placementID {
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkRewardedVideoLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATRewardedVideoWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJSAdInfo;
}

+(void) showRewardedVideoWithPlacementID:(NSString*)placementID scene:(NSString*)scene {
    NSLog(@"ATRewardedVideoWrapper::showRewardedVideoWithPlacementID:%@ scene:%@", placementID, scene);
    [[ATAdManager sharedManager] showRewardedVideoWithPlacementID:placementID scene:scene inViewController:[UIApplication sharedApplication].delegate.window.rootViewController delegate:[ATRewardedVideoWrapper sharedWrapper]];
}

#pragma mark - delegates
- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::didStartLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAttempKey], placementID, [extra jsonString_AnyThinkJS]]];
    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAttempKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
    

}

- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::didFinishLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFilledKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFilledKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
    

}

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    NSLog(@"ATRewardedVideoWrapper::didFailToLoadADSourceWithPlacementID:%@ error:%@", placementID, error);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
        });
    }


}

// bidding
- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::didStartBiddingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingAttemptKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingAttemptKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }

}

- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::didFinishBiddingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingFilledKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingFilledKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }

}

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{

    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }
    NSLog(@"ATRewardedVideoWrapper::didFailBiddingADSourceWithPlacementID:%@ error:%@", placementID, error);

}

-(void) rewardedVideoAgainDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoAgainDidStartPlayingForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainPlayStartCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainPlayStartCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }

}

-(void) rewardedVideoAgainDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoAgainDidEndPlayingForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainPlayEndCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainPlayEndCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }

}

-(void) rewardedVideoAgainDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary*)extra{
    
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoAgainDidFailToPlayForPlacementID:%@ extra:%@ error:%@", placementID, extra,error);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesAgainPlayFailCallbackKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesAgainPlayFailCallbackKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }


}

-(void) rewardedVideoAgainDidClickForPlacementID:(NSString*)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoAgainDidClickForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainClickCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainClickCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) rewardedVideoAgainDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra{
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoAgainDidRewardSuccessForPlacemenID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainRewardCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAgainRewardCallbackKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATRewardedVideoWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];

        });
    }
}

-(void) didFailToLoadADWithPlacementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATRewardedVideoWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];

        });
    }
}

-(void) rewardedVideoDidStartPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidStartPlayingForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) rewardedVideoDidEndPlayingForPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidEndPlayingForPlacementID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) rewardedVideoDidFailToPlayForPlacementID:(NSString*)placementID error:(NSError*)error extra:(NSDictionary *)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidFailToPlayForPlacementID:%@ error:%@ extra:%@", placementID, error, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesFailedToPlayKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];

        });
    }
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
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}

-(void) rewardedVideoDidRewardSuccessForPlacemenID:(NSString*)placementID extra:(NSDictionary*)extra {
    NSLog(@"ATRewardedVideoWrapper::rewardedVideoDidRewardSuccessForPlacemenID:%@ extra:%@", placementID, extra);
    if ([NSThread isMainThread]) {
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesRewardedKey], placementID, [extra jsonString_AnyThinkJS]]];

    }else {
        dispatch_async(dispatch_get_main_queue(), ^{
            [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesRewardedKey], placementID, [extra jsonString_AnyThinkJS]]];

        });
    }
}
@end
