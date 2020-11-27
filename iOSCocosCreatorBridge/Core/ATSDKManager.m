//
//  ATSDKManager.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATSDKManager.h"
#import <AnyThinkSDK/AnyThinkSDK.h>
#import "ATJSBridge.h"
@implementation ATSDKManager
+(void) startWithAppID:(NSString*)appID appKey:(NSString*)appKey {
    NSLog(@"ATSDKManager::startWithAppID:%@ appKey:%@", appID, appKey);
    if ([appID isKindOfClass:[NSString class]] && [appKey isKindOfClass:[NSString class]]) {
        NSError *error = nil;
        if (![[ATAPI sharedInstance] startWithAppID:appID appKey:appKey error:&error]) {
            NSLog(@"AnyThinkSDK has failed to start with appID:%@, appKey:%@, error:%@", appID, appKey, error);
        }
    } else {
        NSLog(@"AnyThinkSDK has failed to start; appID & appKey should be of NSString.");
    }
}

+(void) setCustomData:(NSString*)customJsonStr {
    NSLog(@"ATSDKManager::setCustomData:%@", customJsonStr);
    if ([customJsonStr isKindOfClass:[NSString class]]) {
        NSDictionary *customData = [NSJSONSerialization JSONObjectWithString:customJsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([customData isKindOfClass:[NSDictionary class]]) { [[ATAPI sharedInstance] setCustomData:customData]; }
    }
}

+(void) setCustomData:(NSString*)customDataJsonStr forPlacementID:(NSString*)placementID {
    NSLog(@"ATSDKManager::setCustomData:%@ forPlacementID:%@", customDataJsonStr, placementID);
    if ([customDataJsonStr isKindOfClass:[NSString class]] && [placementID isKindOfClass:[NSString class]]) {
        NSDictionary *customData = [NSJSONSerialization JSONObjectWithString:customDataJsonStr options:NSJSONReadingAllowFragments error:nil];
        if ([customData isKindOfClass:[NSDictionary class]]) { [[ATAPI sharedInstance] setCustomData:customData forPlacementID:placementID]; }
    }
}

+(void) setDataConsent:(NSNumber*)consent {
    NSLog(@"ATSDKManager::setDataConsent:%@", consent);
    [[ATAPI sharedInstance] setDataConsentSet:[@{@(0):@(ATDataConsentSetPersonalized), @(1):@(ATDataConsentSetNonpersonalized), @(2):@(ATDataConsentSetUnknown)}[consent] integerValue] consentString:nil];
}

+(NSNumber*) dataConsent {
    NSLog(@"ATSDKManager::dataConsent");
    return @{@(ATDataConsentSetPersonalized):@0, @(ATDataConsentSetNonpersonalized):@1, @(ATDataConsentSetUnknown):@2}[@([[ATAPI sharedInstance] dataConsentSet])];
}

+(void) getUserLocationWithCallback:(NSString*)callback {
    NSLog(@"ATSDKManager::getUserLocationWithCallback:%@", callback);
    [[ATAPI sharedInstance] getUserLocationWithCallback:^(ATUserLocation location) { [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@(%ld)", callback, location]]; }];
}

+(void) presentDataConsentDialog {
    NSLog(@"ATSDKManager::presentDataConsentDialog");
    [[ATAPI sharedInstance] presentDataConsentDialogInViewController:[UIApplication sharedApplication].keyWindow.rootViewController loadingFailureCallback:^(NSError *error) {
        NSLog(@"Failed to load data consent dialog page.");
    } dismissalCallback:^{
        //
    }];
}

+(void) setDebugLog:(BOOL)log {
    NSLog(@"ATSDKManager::setDataConsent:%@", log ? @"YES" : @"NO");
    [ATAPI setLogEnabled:log];
}

+(void) deniedUploadDeviceInfo:(NSString*)deniedInfo {
    NSArray *deniedInfoArray = [deniedInfo componentsSeparatedByString:@","];
    NSLog(@"ATSDKManager::deniedUploadDeviceInfo before：%@ after: %@", deniedInfo, deniedInfoArray);
    [[ATAPI sharedInstance] setDeniedUploadInfoArray:deniedInfoArray];
}

@end
