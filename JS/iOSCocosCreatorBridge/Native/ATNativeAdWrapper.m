//
//  ATNativeAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATNativeAdWrapper.h"
#import <AnyThinkNative/AnyThinkNative.h>
static NSString *const kDelegatesLoadedKey = @"NativeLoaded";
static NSString *const kDelegatesLoadFailedKey = @"NativeLoadFail";
static NSString *const kDelegatesShowKey = @"NativeShow";
static NSString *const kDelegatesClickKey = @"NativeClick";
static NSString *const kDelegatesVideoStartKey = @"NativeVideoStart";
static NSString *const kDelegatesVideoEndKey = @"NativeVideoEnd";
static NSString *const kDelegatesCloseButtonTappedKey = @"NativeCloseButtonTapped";

@interface ATCCNativeAdView:ATNativeADView
@property(nonatomic, readonly) UILabel *advertiserLabel;
@property(nonatomic, readonly) UILabel *textLabel;
@property(nonatomic, readonly) UILabel *titleLabel;
@property(nonatomic, readonly) UILabel *ctaLabel;
@property(nonatomic, readonly) UILabel *ratingLabel;
@property(nonatomic, readonly) UIImageView *iconImageView;
@property(nonatomic, readonly) UIImageView *mainImageView;
@property(nonatomic, readonly) UIButton *dislikeButton;
@end
@implementation ATCCNativeAdView
-(void) initSubviews {
    [super initSubviews];
    _advertiserLabel = [UILabel new];
    [self addSubview:_advertiserLabel];
    
    _titleLabel = [UILabel new];
    [self addSubview:_titleLabel];
    
    _textLabel = [UILabel new];
    _textLabel.numberOfLines = 2;
    [self addSubview:_textLabel];
    
    _ctaLabel = [UILabel new];
    _ctaLabel.textAlignment = NSTextAlignmentCenter;
    [self addSubview:_ctaLabel];
    
    _ratingLabel = [UILabel new];
    [self addSubview:_ratingLabel];
    
    _iconImageView = [UIImageView new];
    _iconImageView.layer.cornerRadius = 4.0f;
    _iconImageView.layer.masksToBounds = YES;
    _iconImageView.contentMode = UIViewContentModeScaleAspectFit;
    [self addSubview:_iconImageView];
    
    _mainImageView = [UIImageView new];
    _mainImageView.contentMode = UIViewContentModeScaleAspectFit;
    [self addSubview:_mainImageView];
    
    _dislikeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    UIImage *closeImg = [UIImage imageNamed:@"icon_webview_close" inBundle:[NSBundle bundleWithPath:[[NSBundle mainBundle] pathForResource:@"AnyThinkSDK" ofType:@"bundle"]] compatibleWithTraitCollection:nil];
    [_dislikeButton setImage:closeImg forState:0];
    [self addSubview:_dislikeButton];
}

-(NSArray<UIView*>*)clickableViews {
    NSMutableArray *clickableViews = [NSMutableArray arrayWithObjects:_iconImageView, _ctaLabel, _mainImageView, nil];
    if (self.mediaView != nil) { [clickableViews addObject:self.mediaView]; }
    return clickableViews;
}

-(void) configureMetrics:(NSDictionary*)metrics {
    NSDictionary<NSString*, UILabel*> *viewsDict = @{@"title":_titleLabel, @"cta":_ctaLabel, @"desc":_textLabel, @"icon":_iconImageView, @"mainImage":_mainImageView, @"parent":self, @"rating":_ratingLabel, @"dislike":_dislikeButton};
    [viewsDict enumerateKeysAndObjectsUsingBlock:^(NSString * _Nonnull key, UILabel * _Nonnull obj, BOOL * _Nonnull stop) {
        NSDictionary *metric = metrics[key];
        obj.frame = CGRectMake([metric[@"x"] doubleValue], [metric[@"y"] doubleValue], [metric[@"width"] doubleValue], [metric[@"height"] doubleValue]);
        NSString *bgColorStr = metric[@"backgroundColor"];
        if ([bgColorStr isKindOfClass:[NSString class]]) {
            if ([bgColorStr isEqualToString:@"clearColor"]) {
                obj.backgroundColor = [UIColor clearColor];
            } else {
                obj.backgroundColor = [UIColor colorWithHexString:bgColorStr];
            }
        }
        if ([obj respondsToSelector:@selector(setTextColor:)]) {
            NSString *textColor = metric[@"textColor"];
            if ([bgColorStr isEqualToString:@"clearColor"]) {
                obj.textColor = [UIColor clearColor];
            } else {
                obj.textColor = [UIColor colorWithHexString:textColor];
            }
        }
        
        if ([obj respondsToSelector:@selector(setFont:)]) { obj.font = [UIFont systemFontOfSize:[metric[@"textSize"] doubleValue]]; }
    }];
    self.mediaView.frame = self.mainImageView.frame;
}
@end

NSDictionary *parseNativeExtraJsonStr(NSString* jsonStr) {
    NSDictionary *extra = nil;
    if (jsonStr != nil) {
        NSDictionary *sizeDict = [NSJSONSerialization JSONObjectWithString:jsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([sizeDict isKindOfClass:[NSDictionary class]]) {
            extra = @{kExtraInfoNativeAdSizeKey:[NSValue valueWithCGSize:CGSizeMake([sizeDict[@"width"] doubleValue], [sizeDict[@"height"] doubleValue])]};
        }
    }
    return extra;
}

@interface ATNativeAdWrapper()<ATNativeADDelegate>
@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*>* adViews;
@end
@implementation ATNativeAdWrapper
+(instancetype) sharedWrapper {
    static ATNativeAdWrapper *sharedWrapper = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedWrapper = [[ATNativeAdWrapper alloc] init];
    });
    return sharedWrapper;
}

-(instancetype) init {
    self = [super init];
    if (self != nil) {
        _adViews = [NSMutableDictionary<NSString*, UIView*> new];
    }
    return self;
}

+(void) loadNativeWithPlacementID:(NSString*)placementID extra:(NSString*)extraJsonStr {
    NSLog(@"ATNativeAdWrapper::loadNativeAdWithPlacementID:%@ extra:%@", placementID, extraJsonStr);
    NSDictionary *extra = parseNativeExtraJsonStr(extraJsonStr);
    [[ATAdManager sharedManager] loadADWithPlacementID:placementID extra:[extra isKindOfClass:[NSDictionary class]] ? extra : nil delegate:[ATNativeAdWrapper sharedWrapper]];
}

+(BOOL) nativeReadyForPlacementID:(NSString*)placementID {
    NSLog(@"ATNativeAdWrapper::isNativeAdReadyForPlacementID:%@", placementID);
    return [[ATAdManager sharedManager] nativeAdReadyForPlacementID:placementID];
}

+(NSString*) nativeCheckAdStatusForPlacementID:(NSString*)placementID {
    NSLog(@"ATNativeAdWrapper::isNativeAdStatusForPlacementID:%@", placementID);
    ATCheckLoadModel *checkLoadModel = [[ATAdManager sharedManager] checkNativeLoadStatusForPlacementID:placementID];
    NSMutableDictionary *statusDict = [NSMutableDictionary dictionary];
    statusDict[@"isLoading"] = @(checkLoadModel.isLoading);
    statusDict[@"isReady"] = @(checkLoadModel.isReady);
    statusDict[@"adInfo"] = checkLoadModel.adOfferInfo;
    NSLog(@"ATNativeAdWrapper::statusDict = %@", statusDict);
    return statusDict.jsonString_AnyThinkJS;
}

+(void) showNativeWithPlacementID:(NSString*)placementID scene:(NSString*)scene metrics:(NSString*)metricsJSONString {
    NSLog(@"ATNativeAdWrapper::showNativeAdWithPlacementID:%@ scene:%@ metrics:%@", placementID, scene, metricsJSONString);
    dispatch_async(dispatch_get_main_queue(), ^{
        NSDictionary *metrics = [NSJSONSerialization JSONObjectWithString:metricsJSONString options:NSJSONReadingAllowFragments error:nil];
        ATNativeADConfiguration *config = [[ATNativeADConfiguration alloc] init];
        config.delegate = [ATNativeAdWrapper sharedWrapper];
        config.renderingViewClass = [ATCCNativeAdView class];
        config.rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
        config.ADFrame = [metrics[@"parent"] isKindOfClass:[NSDictionary class]] ? CGRectMake([metrics[@"parent"][@"x"] doubleValue], [metrics[@"parent"][@"y"] doubleValue], [metrics[@"parent"][@"width"] doubleValue], [metrics[@"parent"][@"height"] doubleValue]) : CGRectZero;
        ATCCNativeAdView *adView = [[ATAdManager sharedManager] retriveAdViewWithPlacementID:placementID configuration:config scene:scene];
        if (adView != nil) {
            [ATNativeAdWrapper sharedWrapper].adViews[placementID] = adView;
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:adView];
            [adView configureMetrics:metrics];
            
            @try {
                NSDictionary *metric = metrics[@"mainImage"];
                CGRect mainImageFrame = CGRectMake([metric[@"x"] doubleValue], [metric[@"y"] doubleValue], [metric[@"width"] doubleValue], [metric[@"height"] doubleValue]);
                
                NSObject *dataObject = [adView valueForKeyPath:@"currentOffer.customObject"];
                if ([dataObject isKindOfClass:NSClassFromString(@"GDTUnifiedNativeAdDataObject")] && [[dataObject valueForKeyPath:@"isVideoAd"] boolValue]) {
                    //视频广告
                    for (UIView *view in adView.subviews)
                    {
                        if ([NSStringFromClass([view class]) isEqualToString:@"GDTUnifiedNativeAdView"]) {
                            view.frame = mainImageFrame;
                            [adView bringSubviewToFront:view];
                        }
                    }
                } else if ([dataObject isKindOfClass:NSClassFromString(@"BUNativeExpressAdView")]) {
                    for (UIView *view in adView.subviews)
                    {
                        if ([NSStringFromClass([view class]) isEqualToString:@"BUNativeExpressAdView"]) {
                            view.frame = adView.bounds;
                            [adView bringSubviewToFront:view];
                        }
                    }
                } else if ([dataObject isKindOfClass:NSClassFromString(@"BUNativeExpressFeedVideoAdView")]) {
                    for (UIView *view in adView.subviews)
                    {
                        if ([NSStringFromClass([view class]) isEqualToString:@"BUNativeExpressFeedVideoAdView"]) {
                            view.frame = adView.bounds;
                            [adView bringSubviewToFront:view];
                        }
                    }
                } else if ([dataObject isKindOfClass:NSClassFromString(@"GDTNativeExpressAdView")]) {
                    for (UIView *view in adView.subviews)
                    {
                        if ([NSStringFromClass([view class]) isEqualToString:@"UIView"]) {
                            for (UIView *childView in view.subviews)
                            {
                                if ([NSStringFromClass([childView class]) isEqualToString:@"GDTNativeExpressAdView"]) {
                                    view.frame = adView.bounds;
                                    childView.frame = view.bounds;
                                    [adView bringSubviewToFront:view];
                                }
                            }
                        }
                    }
                }
                [adView bringSubviewToFront:adView.dislikeButton];
            } @catch (NSException *exception) {
                //
            } @finally {
                //
            }
        }
    });
}

+(void) removeNativeWithPlacementID:(NSString*)placementID {
    NSLog(@"ATNativeAdWrapper::removeNativeAdViewWithPlacementID:%@", placementID);
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ATNativeAdWrapper sharedWrapper].adViews[placementID] removeFromSuperview];
        [[ATNativeAdWrapper sharedWrapper].adViews removeObjectForKey:placementID];
    });
}
#pragma mark - delegates
-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    NSLog(@"ATNativeAdWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
}

-(void) didFailToLoadADWithPlacementID:(NSString *)placementID error:(NSError *)error {
    NSLog(@"ATNativeAdWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];
}

-(void) didShowNativeAdInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeAdWrapper::didShowNativeAdInAdView: placementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) didClickNativeAdInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeAdWrapper::didClickNativeAdInAdView: placementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) didTapCloseButtonInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeAdWrapper::didTapCloseButtonInAdView: placementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseButtonTappedKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) didStartPlayingVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeAdWrapper::didStartPlayingVideoInAdView: placementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];
}

-(void) didEndPlayingVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    NSLog(@"ATNativeAdWrapper::didEndPlayingVideoInAdView: placementID:%@ extra:%@", placementID, extra);
    [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];
}

- (void)didEnterFullScreenVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    //
}


- (void)didExitFullScreenVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    //
}


- (void)didLoadSuccessDrawWith:(NSArray *)views placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    //
}

@end
