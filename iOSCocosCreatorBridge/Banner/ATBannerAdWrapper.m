//
//  ATBannerAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATBannerAdWrapper.h"
#import <AnyThinkBanner/AnyThinkBanner.h>

@interface ATBannerAdWrapper()<ATBannerDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*>* bannerViews;
@end

NSDictionary *parseExtraJsonStr(NSString* jsonStr) {
    NSDictionary *extra = nil;
    if (jsonStr != nil) {
        NSDictionary *sizeDict = [NSJSONSerialization JSONObjectWithString:jsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([sizeDict isKindOfClass:[NSDictionary class]]) {
            CGFloat scale = [sizeDict[@"usesPixel"] boolValue] ? UIScreen.mainScreen.nativeScale : 1.0f;
            extra = @{kATAdLoadingExtraBannerAdSizeKey:[NSValue valueWithCGSize:CGSizeMake([sizeDict[@"width"] doubleValue] / scale, [sizeDict[@"height"] doubleValue] / scale)]};
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
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSDictionary class]] ? extra : nil delegate:[ATBannerAdWrapper sharedWrapper]];
}

+(BOOL) bannerReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::bannerReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] bannerAdReadyForPlacementID:placementID];
}

+(void) showBannerWithPlacementID:(NSString*)placementID position:(NSString*)position {
    NSLog(@"ATBannerAdWrapper::showBannerWithPlacementID:%@ position:%@", placementID, position);
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID];
        if (bannerView != nil) {
            [ATBannerAdWrapper sharedWrapper].bannerViews[placementID] = bannerView;
            bannerView.delegate = [ATBannerAdWrapper sharedWrapper];
            bannerView.frame = CGRectMake((CGRectGetWidth(UIScreen.mainScreen.bounds) - CGRectGetWidth(bannerView.bounds)) / 2.0f, [@{@"top":@(SafeAreaInsets_ATCocosCreatorBanner().top), @"bottom":@(CGRectGetHeight(UIScreen.mainScreen.bounds) - SafeAreaInsets_ATCocosCreatorBanner().bottom - CGRectGetHeight(bannerView.bounds))}[position] doubleValue] , CGRectGetWidth(bannerView.bounds), CGRectGetHeight(bannerView.bounds));
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:bannerView];
        }
    });
}

+(void) showBannerWithPlacementID:(NSString*)placementID rect:(NSString*)rectJsonStr {
    NSLog(@"ATBannerAdWrapper::showBannerWithPlacementID:%@ rect:%@", placementID, rectJsonStr);
    dispatch_async(dispatch_get_main_queue(), ^{
        ATBannerView *bannerView = [[ATAdManager sharedManager] retrieveBannerViewForPlacementID:placementID];
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

+(void) rewoveAd:(NSString*)placementID {
    NSLog(@"ATBannerAdWrapper::rewoveAd:%@", placementID);
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
