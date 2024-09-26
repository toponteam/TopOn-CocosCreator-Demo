//
//  ATNativeSelfRenderView.m
//  AnyThinkSDKDemo
//
//  Created by GUO PENG on 2022/5/7.
//  Copyright © 2022 AnyThink. All rights reserved.
//

#import "ATNativeSelfRenderView.h"
#import <Masonry/Masonry.h>
#import <AnyThinkSDK/ATImageLoader.h>
//#import <SDWebImage/SDWebImage.h>
#import "ATNativeAdWrapper.h"
#import "ATUnityUtilities.h"

@interface ATNativeSelfRenderView()

@property(nonatomic, strong) ATNativeAdOffer *nativeAdOffer;

@end


@implementation ATNativeSelfRenderView

- (void)dealloc{
    NSLog(@"🔥---ATNativeSelfRenderView--销毁");
}

- (instancetype) initWithOffer:(ATNativeAdOffer *)offer{

    if (self = [super init]) {
        
        _nativeAdOffer = offer;
        
        [self addView];
//        [self makeConstraintsForSubviews];
        
        [self setupUI];
    }
    return self;
}

- (void)addView{
    
    self.advertiserLabel = [[UILabel alloc]init];
    self.advertiserLabel.font = [UIFont boldSystemFontOfSize:15.0f];
    self.advertiserLabel.textColor = [UIColor blackColor];
    self.advertiserLabel.textAlignment = NSTextAlignmentLeft;
    self.advertiserLabel.userInteractionEnabled = YES;
    [self addSubview:self.advertiserLabel];
        
    self.titleLabel = [[UILabel alloc]init];
    self.titleLabel.font = [UIFont boldSystemFontOfSize:18.0f];
    self.titleLabel.textColor = [UIColor blackColor];
    self.titleLabel.textAlignment = NSTextAlignmentLeft;
    self.titleLabel.userInteractionEnabled = YES;

    [self addSubview:self.titleLabel];
    
    self.textLabel = [[UILabel alloc]init];
    self.textLabel.font = [UIFont systemFontOfSize:15.0f];
    self.textLabel.textColor = [UIColor blackColor];
    self.textLabel.userInteractionEnabled = YES;

    [self addSubview:self.textLabel];
    
    self.ctaLabel = [[UILabel alloc]init];
    self.ctaLabel.font = [UIFont systemFontOfSize:15.0f];
    self.ctaLabel.textColor = [UIColor blackColor];
    self.ctaLabel.userInteractionEnabled = YES;

    [self addSubview:self.ctaLabel];

    self.ratingLabel = [[UILabel alloc]init];
    self.ratingLabel.font = [UIFont systemFontOfSize:15.0f];
    self.ratingLabel.textColor = [UIColor blackColor];
    self.ratingLabel.userInteractionEnabled = YES;

    [self addSubview:self.ratingLabel];
    
    self.iconImageView = [[UIImageView alloc]init];
    self.iconImageView.layer.cornerRadius = 4.0f;
    self.iconImageView.layer.masksToBounds = YES;
    self.iconImageView.contentMode = UIViewContentModeScaleAspectFit;
    self.iconImageView.userInteractionEnabled = YES;
    [self addSubview:self.iconImageView];
    
    
    self.mainImageView = [[UIImageView alloc]init];
    self.mainImageView.contentMode = UIViewContentModeScaleAspectFit;
    self.mainImageView.userInteractionEnabled = YES;
    [self addSubview:self.mainImageView];
    
    self.logoImageView = [[UIImageView alloc]init];
    self.logoImageView.contentMode = UIViewContentModeScaleAspectFit;
    self.logoImageView.userInteractionEnabled = YES;

    [self addSubview:self.logoImageView];
    
    self.dislikeButton = [UIButton buttonWithType:UIButtonTypeCustom];
    
    UIImage *closeImg = [UIImage imageNamed:@"icon_webview_close" inBundle:[NSBundle bundleWithPath:[[NSBundle mainBundle] pathForResource:@"AnyThinkSDK" ofType:@"bundle"]] compatibleWithTraitCollection:nil];
    
    [self.dislikeButton setImage:closeImg forState:0];
    [self addSubview:self.dislikeButton];
}


- (void)setupUI{
    

    if (self.nativeAdOffer.nativeAd.icon) {
        self.iconImageView.image = self.nativeAdOffer.nativeAd.icon;
    }
    [[ATImageLoader shareLoader]loadImageWithURL:[NSURL URLWithString:self.nativeAdOffer.nativeAd.iconUrl] completion:^(UIImage *image, NSError *error) {
            
        if (!error) {
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.iconImageView setImage:image];
            });
        }
    }];
    
    
    NSLog(@"🔥----iconUrl:%@",self.nativeAdOffer.nativeAd.iconUrl);

    [[ATImageLoader shareLoader]loadImageWithURL:[NSURL URLWithString:self.nativeAdOffer.nativeAd.imageUrl] completion:^(UIImage *image, NSError *error) {
            
        if (!error) {
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.mainImageView setImage:image];
            });

        }
    }];
    
    
    NSLog(@"🔥----imageUrl:%@",self.nativeAdOffer.nativeAd.imageUrl);
    
    [[ATImageLoader shareLoader]loadImageWithURL:[NSURL URLWithString:self.nativeAdOffer.nativeAd.logoUrl] completion:^(UIImage *image, NSError *error) {
            
        if (!error) {
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.logoImageView setImage:image];
            });
        }
    }];
    
        
    self.advertiserLabel.text = self.nativeAdOffer.nativeAd.advertiser;


    self.titleLabel.text = self.nativeAdOffer.nativeAd.title;
  
    self.textLabel.text = self.nativeAdOffer.nativeAd.mainText;
     
    self.ctaLabel.text = self.nativeAdOffer.nativeAd.ctaText;
  
    self.ratingLabel.text = [NSString stringWithFormat:@"%@", self.nativeAdOffer.nativeAd.rating ? self.nativeAdOffer.nativeAd.rating : @""];
    NSLog(@"🔥AnythinkDemo::native文本内容title:%@ ; text:%@ ; cta:%@ ",self.nativeAdOffer.nativeAd.title,self.nativeAdOffer.nativeAd.mainText,self.nativeAdOffer.nativeAd.ctaText);
}

-(void) configureMetrics:(NSDictionary *)metrics {
    NSDictionary<NSString*, UIView*> *views = @{kNativeAssetTitle:_titleLabel, kNativeAssetText:_textLabel, kNativeAssetCta:_ctaLabel, kNativeAssetRating:_ratingLabel, kNativeAssetAdvertiser:_advertiserLabel, kNativeAssetIcon:_iconImageView, kNativeAssetMainImage:_mainImageView, kNativeAssetDislike:_dislikeButton};
    [views enumerateKeysAndObjectsUsingBlock:^(id  _Nonnull key, UIView * _Nonnull obj, BOOL * _Nonnull stop) {
//        CGRect frame = CGRectFromString(metrics[key][kParsedPropertiesFrameKey]);
        NSDictionary *metric = metrics[key];
        CGRect frame = CGRectMake([metric[@"x"] doubleValue], [metric[@"y"] doubleValue], [metric[@"width"] doubleValue], [metric[@"height"] doubleValue]);
        NSLog(@"🔥AnythinkDemo::nativekey-%@,Frame-%@",key,NSStringFromCGRect(frame));
        obj.frame = frame;
        
        if ([obj respondsToSelector:@selector(setBackgroundColor:)] && [metrics[key] containsObjectForKey:@"background_color"]) [obj setBackgroundColor:[metrics[key][@"background_color"] hasPrefix:@"#"] ? [UIColor colorWithHexString:metrics[key][@"background_color"]] : [UIColor clearColor]];
        if ([obj respondsToSelector:@selector(setTextColor:)] && [metrics[key] containsObjectForKey:@"text_color"]) [obj setTextColor:[UIColor colorWithHexString:metrics[key][@"text_color"]]];
        if ([obj respondsToSelector:@selector(setFont:)] && [metrics[key] containsObjectForKey:@"text_size"] && [metrics[key][@"text_size"] respondsToSelector:@selector(doubleValue)]) [obj setFont:[UIFont systemFontOfSize:[metrics[key][@"text_size"] doubleValue]]];
    }];
    NSDictionary *metric = metrics[kNativeAssetMainImage];
    CGRect frame = CGRectMake([metric[@"x"] doubleValue], [metric[@"y"] doubleValue], [metric[@"width"] doubleValue], [metric[@"height"] doubleValue]);
    self.mediaView.frame = frame;
}


@end
