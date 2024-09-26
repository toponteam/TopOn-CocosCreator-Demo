//
//  ATBannerAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATBannerAdWrapper.h"
#import <AnyThinkBanner/AnyThinkBanner.h>
//5.6.6版本以上支持 admob 自适应banner （用到时再import该头文件）
//#import <GoogleMobileAds/GoogleMobileAds.h>

static NSString *kATBannerAdLoadingExtraInlineAdaptiveWidthKey = @"inline_adaptive_width";
static NSString *kATBannerAdLoadingExtraInlineAdaptiveOrientationKey = @"inline_adaptive_orientation";

@interface ATBannerAdWrapper()<ATBannerDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*>* bannerViews;
@end

NSDictionary *parseExtraJsonStr(NSString* jsonStr) {
    NSMutableDictionary *extra = [NSMutableDictionary dictionary];
    if (jsonStr != nil) {
        NSDictionary *extraDict = [NSJSONSerialization JSONObjectWithString:jsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([extraDict isKindOfClass:[NSDictionary class]]) {
            NSLog(@"loadBannerExtraDict = %@", extraDict);
            CGFloat scale = [extraDict[@"usesPixel"] boolValue] ? UIScreen.mainScreen.nativeScale : 1.0f;
            extra[kATAdLoadingExtraBannerAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake([extraDict[@"width"] doubleValue] / scale, [extraDict[@"height"] doubleValue] / scale)];
//            // admob 自适应banner，5.6.6版本以上支持
//            if (extraDict[kATBannerAdLoadingExtraInlineAdaptiveWidthKey] != nil && extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] != nil) {
//                //GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth 自适应
//                //GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth 竖屏
//                //GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth 横屏
//                CGFloat admobBannerWidth = [extraDict[kATBannerAdLoadingExtraInlineAdaptiveWidthKey] doubleValue];
//                GADAdSize admobSize;
//                if ([extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] integerValue] == 1) {
//                    admobSize = GADPortraitAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//                } else if ([extraDict[kATBannerAdLoadingExtraInlineAdaptiveOrientationKey] integerValue] == 2) {
//                    admobSize = GADLandscapeAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//                } else {
//                    admobSize = GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(admobBannerWidth);
//                }
//
//                extra[kATAdLoadingExtraAdmobBannerSizeKey] = [NSValue valueWithCGSize:admobSize.size];
//                extra[kATAdLoadingExtraAdmobAdSizeFlagsKey] = @(admobSize.flags);
//            }
        }
        if (extra[kATAdLoadingExtraBannerAdSizeKey] == nil) {
            extra[kATAdLoadingExtraBannerAdSizeKey] = [NSValue valueWithCGSize:CGSizeMake(320.0f, 50.0f)];
        }
    }
    return extra;
}

CGRect parseRectJsonStr(NSString* jsonStr) {
    CGRect rect = CGRectZero;
    if (jsonStr != nil) {
        NSDictionary *rectDict = [NSJSONSerialization JSONObjectWithString:jsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([rectDict isKindOfClass:[NSDictionary class]]) {
            CGFloat scale = [rectDict[@"usesPixel"] boolValue] ? UIScreen.mainScreen.nativeScale : 1.0f;
            rect = CGRectMake([rectDict[@"x"] doubleValue] / scale, [rectDict[@"y"] doubleValue] / scale, [rectDict[@"width"] doubleValue] / scale, [rectDict[@"height"] doubleValue] / scale);
        }
    }
    return rect;
}

UIEdgeInsets SafeAreaInsets_ATCocosCreatorBanner() {
    return ([[UIApplication sharedApplication].keyWindow respondsToSelector:@selector(safeAreaInsets)] ? [UIApplication sharedApplication].keyWindow.safeAreaInsets : UIEdgeInsetsZero);
}

static NSString *const kDelegatesLoadedKey = @"BannerLoaded";
static NSString *const kDelegatesLoadFailedKey = @"BannerLoadFail";
static NSString *const kDelegatesCloseButtonTappedKey = @"BannerCloseButtonTapped";
static NSString *const kDelegatesClickKey = @"BannerClick";
static NSString *const kDelegatesAutoRefreshKey = @"BannerRefresh";
static NSString *const kDelegatesFailedToRefreshKey = @"BannerRefreshFail";
static NSString *const kDelegatesShowKey = @"BannerShow";
@implementation ATBannerAdWrapper
+(instancetype) sharedWrapper {
    static ATBannerAdWrapper *sharedWrapper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedWrapper = [[ATBannerAdWrapper alloc] init];
    });
    return sharedWrapper;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _bannerViews = [NSMutableDictionary<NSString*, UIView*> new];
    }
    return self;
}

+(void) loadBannerWithPlacementID:(NSString*)placementID extra:(NSString*)extraJsonStr {
    NSLog(@"ATBannerAdWrapper::loadBannerWithPlacementID:%@ extra:%@", placementID, extraJsonStr);
    NSDictionary *extra = parseExtraJsonStr(extraJsonStr);
    NSLog(@"loadBannerExtra = %@", extra);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSDictionary class]] ? extra : nil delegate:[ATBannerAdWrapper sharedWrapper]];
}

+(BOOL) bannerReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::bannerReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] bannerAdReadyForPlacementID:placementID];
}

+(NSString*) bannerCheckAdStatusForPlacementID:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::bannerAdStatusForPlacementID:%@", placementID);
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkBannerLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATBannerAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJS;
}

+(void) showBannerWithPlacementID:(NSString*)placementID scene:(NSString*)scene position:(NSString*)position {
    NSLog(@"ATBannerAdWrapper::showBannerWithPlacementID:%@ scene:%@ position:%@", placementID, scene, position);
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID scene:scene];
        NSLog(@"bannerViewFrame = %@", NSStringFromCGRect(bannerView.frame));
        if (bannerView != nil) {
            [ATBannerAdWrapper sharedWrapper].bannerViews[placementID] = bannerView;
            bannerView.delegate = [ATBannerAdWrapper sharedWrapper];
            bannerView.frame = CGRectMake((CGRectGetWidth(UIScreen.mainScreen.bounds) - CGRectGetWidth(bannerView.bounds)) / 2.0f, [@{@"top":@(SafeAreaInsets_ATCocosCreatorBanner().top), @"bottom":@(CGRectGetHeight(UIScreen.mainScreen.bounds) - SafeAreaInsets_ATCocosCreatorBanner().bottom - CGRectGetHeight(bannerView.bounds))}[position] doubleValue] , CGRectGetWidth(bannerView.bounds), CGRectGetHeight(bannerView.bounds));
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:bannerView];
        }
    });
}

+(void) showBannerWithPlacementID:(NSString*)placementID scene:(NSString*)scene rect:(NSString*)rectJsonStr {
    NSLog(@"ATBannerAdWrapper::showBannerWithPlacementID:%@ scene:%@ rect:%@", placementID, scene, rectJsonStr);
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID scene:scene];
        if (bannerView != nil) {
            [ATBannerAdWrapper sharedWrapper].bannerViews[placementID] = bannerView;
            bannerView.delegate = [ATBannerAdWrapper sharedWrapper];
            bannerView.frame = parseRectJsonStr(rectJsonStr);
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:bannerView];
        }
    });
}

+(void) hideAd:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::hideAd:%@", placementID);
    dispatch_async(dispatch_get_main_queue(), ^{
        [ATBannerAdWrapper sharedWrapper].bannerViews[placementID].hidden = YES;
    });
}

+(void) reShowAd:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::reShowAd:%@", placementID);
    dispatch_async(dispatch_get_main_queue(), ^{
        [ATBannerAdWrapper sharedWrapper].bannerViews[placementID].hidden = NO;
    });
}

+(void) removeAd:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::removeAd:%@", placementID);
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ATBannerAdWrapper sharedWrapper].bannerViews[placementID] removeFromSuperview];
        [[ATBannerAdWrapper sharedWrapper].bannerViews removeObjectForKey:placementID];
    });
}
#pragma mark - delegates
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATBannerAdWrapper::bannerView:didFinishLoadingADWithPlacementID:%@", placementID);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
}

-(void) didFailToLoadADWithPlacementID:(NSString *)placementID error:(NSError *)error {
    NSLog(@"ATBannerAdWrapper::bannerView:didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];
}

-(void) bannerView:(ATBannerView*)bannerView failedToAutoRefreshWithPlacementID:(NSString*)placementID error:(NSError*)error {
    NSLog(@"ATBannerAdWrapper::bannerView:failedToAutoRefreshWithPlacementID:%@ error:%@", placementID, error);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesFailedToRefreshKey], placementID, error]];
}

-(void) bannerView:(ATBannerView*)bannerView didShowAdWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATBannerAdWrapper::bannerView:didShowAdWithPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) bannerView:(ATBannerView*)bannerView didClickWithPlacementID:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATBannerAdWrapper::bannerView:didClickWithPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) bannerView:(ATBannerView*)bannerView didAutoRefreshWithPlacement:(NSString*)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATBannerAdWrapper::bannerView:didAutoRefreshWithPlacement:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAutoRefreshKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) bannerView:(ATBannerView*)bannerView didTapCloseButtonWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra {
    NSLog(@"ATBannerAdWrapper::bannerView:didTapCloseButtonWithPlacementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseButtonTappedKey], placementID, [extra jsonString_AnyThinkJS]]];
}

- (void)bannerView:(ATBannerView *)bannerView didCloseWithPlacementID:(NSString *)placementID extra:(NSDictionary *)extra {
    //
}
@end
