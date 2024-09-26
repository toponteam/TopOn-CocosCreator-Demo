//
//  ATNativeAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATNativeAdWrapper.h"
#import <AnyThinkNative/AnyThinkNative.h>
#import "ATNativeSelfRenderView.h"
#import <Masonry/Masonry.h>
static NSString *const kDelegatesLoadedKey = @"NativeLoaded";
static NSString *const kDelegatesLoadFailedKey = @"NativeLoadFail";
static NSString *const kDelegatesShowKey = @"NativeShow";
static NSString *const kDelegatesClickKey = @"NativeClick";
static NSString *const kDelegatesVideoStartKey = @"NativeVideoStart";
static NSString *const kDelegatesVideoEndKey = @"NativeVideoEnd";
static NSString *const kDelegatesCloseButtonTappedKey = @"NativeCloseButtonTapped";

static NSString *const kDelegatesBiddingAttemptKey = @"NativeBiddingAttempt";
static NSString *const kDelegatesBiddingFilledKey = @"NativeBiddingFilled";
static NSString *const kDelegatesBiddingFailKey = @"NativeBiddingFail";
static NSString *const kDelegatesAttempKey = @"NativeAttemp";
static NSString *const kDelegatesLoadFilledKey = @"NativeLoadFilled";
static NSString *const kDelegatesLoadFailKey = @"NativeLoadFail";

NSString *const kParsedPropertiesFrameKey = @"frame";
NSString *const kParsedPropertiesBackgroundColorKey = @"background_color";
NSString *const kParsedPropertiesTextColorKey = @"text_color";
NSString *const kParsedPropertiesTextSizeKey = @"text_size";

NSString *const kNativeAssetAdvertiser = @"advertiser_label";
NSString *const kNativeAssetText = @"desc";
NSString *const kNativeAssetTitle = @"title";
NSString *const kNativeAssetCta = @"cta";
NSString *const kNativeAssetRating = @"rating";
NSString *const kNativeAssetIcon = @"icon";
NSString *const kNativeAssetMainImage = @"mainImage";
NSString *const kNativeAssetDislike = @"dislike";
NSString *const kNativeAssetMedia = @"media";

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
        NSLog(@"configureMetrics-View-%@, frame-%@",key,NSStringFromCGRect(obj.frame));
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
            extra = @{kATExtraInfoNativeAdSizeKey:[NSValue valueWithCGSize:CGSizeMake([sizeDict[@"width"] doubleValue], [sizeDict[@"height"] doubleValue])]};
        }
    }
    return extra;
}

@interface ATNativeAdWrapper()<ATNativeADDelegate>

@property(nonatomic, readonly) NSMutableDictionary<NSString*, UIView*>* adViews;
@property(nonatomic, strong) ATNativeSelfRenderView *nativeSelfRenderView;

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
    return statusDict.jsonString_AnyThinkJSAdInfo;
}


+(ATNativePrepareInfo *)prepareWithNativePrepareInfo:(ATNativeSelfRenderView *)selfRenderView{
    
    ATNativePrepareInfo *info = [ATNativePrepareInfo loadPrepareInfo:^(ATNativePrepareInfo * _Nonnull prepareInfo) {
        prepareInfo.textLabel = selfRenderView.textLabel;
        prepareInfo.advertiserLabel = selfRenderView.advertiserLabel;
        prepareInfo.titleLabel = selfRenderView.titleLabel;
        prepareInfo.ratingLabel = selfRenderView.ratingLabel;
        prepareInfo.iconImageView = selfRenderView.iconImageView;
        prepareInfo.mainImageView = selfRenderView.mainImageView;
        prepareInfo.logoImageView = selfRenderView.logoImageView;
        prepareInfo.dislikeButton = selfRenderView.dislikeButton;
        prepareInfo.ctaLabel = selfRenderView.ctaLabel;
        prepareInfo.mediaView = selfRenderView.mediaView;
    }];
    
    return info;
}

+(void) rendererWithConfiguration:(ATNativeADConfiguration*)configuration selfRenderView:(UIView *_Nullable)selfRenderView nativeADView:(ATNativeADView *)nativeADView{
    
    if (configuration.nativePrepareInfo) {
        [nativeADView prepareWithNativePrepareInfo:configuration.nativePrepareInfo];
    }

//    self.delegate = configuration.delegate;
    
    nativeADView.selfRenderView = selfRenderView;
    
    if ([nativeADView getCurrentNativeAdRenderType] == ATNativeAdRenderSelfRender && selfRenderView) {
        selfRenderView.frame = CGRectMake(0, 0, configuration.ADFrame.size.width, configuration.ADFrame.size.height);
        [nativeADView addSubview:selfRenderView];
    }
//
//    ATNativeADCache *cache = (ATNativeADCache*)self.adView.nativeAd;
//    cache.scene = _scene;
}

+(void) showNativeWithPlacementID:(NSString*)placementID scene:(NSString*)scene
                          metrics:(NSString*)metricsJSONString {
    NSLog(@"ATNativeAdWrapper::showNativeAdWithPlacementID:%@ scene:%@ metrics:%@", placementID, scene, metricsJSONString);
    dispatch_async(dispatch_get_main_queue(), ^{
        NSDictionary *metrics = [NSJSONSerialization JSONObjectWithString:metricsJSONString options:NSJSONReadingAllowFragments error:nil];
        ATNativeADConfiguration *config = [[ATNativeADConfiguration alloc] init];
        config.delegate = [ATNativeAdWrapper sharedWrapper];
        config.rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
        config.ADFrame = [metrics[@"parent"] isKindOfClass:[NSDictionary class]] ? CGRectMake([metrics[@"parent"][@"x"] doubleValue], [metrics[@"parent"][@"y"] doubleValue], [metrics[@"parent"][@"width"] doubleValue], [metrics[@"parent"][@"height"] doubleValue]) : CGRectZero;
        
        BOOL ready = [[ATAdManager sharedManager] nativeAdReadyForPlacementID:placementID];
        if (!ready) {
            return;
        }
        
        ATNativeAdOffer *offer = [[ATAdManager sharedManager] getNativeAdOfferWithPlacementID:placementID];
        ATNativeSelfRenderView *selfRenderView =  [[ATNativeSelfRenderView alloc] initWithOffer:offer];
        [selfRenderView configureMetrics:metrics];
        NSBundle *bundle = [NSBundle bundleWithPath:[[NSBundle bundleForClass:[self class]] pathForResource:@"AnyThinkSDK" ofType:@"bundle"]];
        UIImage * img = [UIImage imageNamed:@"icon_webview_close" inBundle:bundle compatibleWithTraitCollection:nil];
        [selfRenderView.dislikeButton setImage:img forState:0];
        
        ATNativeADView *adView = [self getNativeADView:config offer:offer selfRenderView:selfRenderView withPlacementId:placementID];
        adView.ctaLabel.hidden = [adView.nativeAd.ctaText length] == 0;
        ATNativePrepareInfo *nativePrepareInfo = [self prepareWithNativePrepareInfo:selfRenderView];
        config.nativePrepareInfo = nativePrepareInfo;
        [offer rendererWithConfiguration:config selfRenderView:selfRenderView nativeADView:adView];
        
    
        if (adView != nil) {
            [ATNativeAdWrapper sharedWrapper].adViews[placementID] = adView;
            [[UIApplication sharedApplication].keyWindow.rootViewController.view addSubview:adView];
        }
    });
}

+(ATNativeADView *)getNativeADView:(ATNativeADConfiguration *)config offer:(ATNativeAdOffer *)offer selfRenderView:(ATNativeSelfRenderView *)selfRenderView withPlacementId:(NSString*)placementID{
    
    ATNativeADView *nativeADView = [[ATNativeADView alloc]initWithConfiguration:config currentOffer:offer placementID:placementID];
    
    UIView *mediaView = [nativeADView getMediaView];
    
    NSMutableArray *array = [@[selfRenderView.iconImageView,
                               selfRenderView.titleLabel,
                               selfRenderView.textLabel,
                               selfRenderView.ctaLabel,
                               selfRenderView.mainImageView] mutableCopy];
    if (mediaView) {
        [array addObject:mediaView];
        
        selfRenderView.mediaView = mediaView;
        [selfRenderView addSubview:mediaView];
        [mediaView mas_updateConstraints:^(MASConstraintMaker *make) {
            make.edges.equalTo(selfRenderView.mainImageView);
        }];

        [selfRenderView bringSubviewToFront:selfRenderView.logoImageView];
        if (selfRenderView.dislikeButton) {
            [selfRenderView bringSubviewToFront:selfRenderView.dislikeButton];
        }
    }
    [nativeADView registerClickableViewArray:array];
    
    nativeADView.backgroundColor = [UIColor whiteColor];// randomColor;
    
    return nativeADView;
}



+(void) removeNativeWithPlacementID:(NSString*)placementID {
    NSLog(@"ATNativeAdWrapper::removeNativeAdViewWithPlacementID:%@", placementID);
    dispatch_async(dispatch_get_main_queue(), ^{
        [[ATNativeAdWrapper sharedWrapper].adViews[placementID] removeFromSuperview];
        [[ATNativeAdWrapper sharedWrapper].adViews removeObjectForKey:placementID];
    });
}

+(void) entryAdScenarioWithPlacementID:(NSString*)placementID scenarioID:(NSString*)scenarioID{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"Unity: 🔥🔥entryAdScenarioWithPlacementID::array = %@ scenarioID:%@", placementID,scenarioID);
        [[ATAdManager sharedManager] entryNativeScenarioWithPlacementID:placementID scene:scenarioID];
    });
}

#pragma mark - delegates

- (void)didStartLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didStartLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesAttempKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

- (void)didFinishLoadingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFinishLoadingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

- (void)didFailToLoadADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFailToLoadADSourceWithPlacementID:%@ error:%@", placementID, error);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
    });
}

// bidding
- (void)didStartBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didStartBiddingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingAttemptKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

- (void)didFinishBiddingADSourceWithPlacementID:(NSString *)placementID extra:(NSDictionary*)extra{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFinishBiddingADSourceWithPlacementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesBiddingFilledKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

- (void)didFailBiddingADSourceWithPlacementID:(NSString*)placementID extra:(NSDictionary*)extra error:(NSError*)error{
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFailBiddingADSourceWithPlacementID:%@ error:%@", placementID, error);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@', '%@')", self.delegates[kDelegatesBiddingFailKey], placementID, error.userInfo[NSLocalizedDescriptionKey], [extra jsonString_AnyThinkJS]]];
    });
}


-(void) didFinishLoadingADWithPlacementID:(NSString *)placementID {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFinishLoadingADWithPlacementID:%@", placementID);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@')", self.delegates[kDelegatesLoadedKey], placementID]];
    });
}

-(void) didFailToLoadADWithPlacementID:(NSString *)placementID error:(NSError *)error {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didFailToLoadADWithPlacementID:%@ error:%@", placementID, error);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesLoadFailedKey], placementID, error]];
    });
}

-(void) didShowNativeAdInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didShowNativeAdInAdView: placementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesShowKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

-(void) didClickNativeAdInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didClickNativeAdInAdView: placementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesClickKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

-(void) didTapCloseButtonInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didTapCloseButtonInAdView: placementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesCloseButtonTappedKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

-(void) didStartPlayingVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didStartPlayingVideoInAdView: placementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoStartKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
}

-(void) didEndPlayingVideoInAdView:(ATNativeADView *)adView placementID:(NSString *)placementID extra:(NSDictionary *)extra {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"ATNativeAdWrapper::didEndPlayingVideoInAdView: placementID:%@ extra:%@", placementID, extra);
        [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@('%@', '%@')", self.delegates[kDelegatesVideoEndKey], placementID, [extra jsonString_AnyThinkJS]]];
    });
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
