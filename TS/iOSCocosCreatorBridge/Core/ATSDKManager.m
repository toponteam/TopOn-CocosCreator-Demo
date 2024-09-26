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
//        if ([customData isKindOfClass:[NSDictionary class]]) { [[ATAPI sharedInstance] setCustomData:customData forPlacementID:placementID]; }
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
    
    [[ATAPI sharedInstance] getUserLocationWithCallback:^(ATUserLocation location) { [ATJSBridge callJSMethodWithString:[NSString stringWithFormat:@"%@(%ld)", callback, location]];
        
    }];
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

+ (void)showDebuggerUIWithDebugKey:(NSString *)debugKey {
    // 获取当前 window 的根控制器
    UIViewController *rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    
    // 获取 ATDebuggerAPI 类
    Class ATDebuggerAPIClass = NSClassFromString(@"ATDebuggerAPI");
    if (!ATDebuggerAPIClass) {
        NSLog(@"ATDebuggerAPI class not found");
        return;
    }
    
    // 获取 sharedInstance 方法
    SEL sharedInstanceSelector = NSSelectorFromString(@"sharedInstance");
    if (![ATDebuggerAPIClass respondsToSelector:sharedInstanceSelector]) {
        NSLog(@"ATDebuggerAPI sharedInstance method not found");
        return;
    }
    
    // 调用 sharedInstance 方法
    id (*sharedInstanceIMP)(id, SEL) = (id (*)(id, SEL))[ATDebuggerAPIClass methodForSelector:sharedInstanceSelector];
    id sharedInstance = sharedInstanceIMP(ATDebuggerAPIClass, sharedInstanceSelector);
    
    // 获取 showDebuggerInViewController:showType:debugkey: 方法
    SEL showDebuggerSelector = NSSelectorFromString(@"ATDebuggerAPI showDebuggerInViewController:showType:debugkey:");
    if (![sharedInstance respondsToSelector:showDebuggerSelector]) {
        NSLog(@"ATDebuggerAPI showDebuggerInViewController:showType:debugkey: method not found");
        return;
    }
    
    // 调用方法
    void (*showDebuggerIMP)(id, SEL, UIViewController *, NSInteger, NSString *) = (void (*)(id, SEL, UIViewController *, NSInteger, NSString *))[sharedInstance methodForSelector:showDebuggerSelector];
    showDebuggerIMP(sharedInstance, showDebuggerSelector, rootViewController, 0, debugKey);
}

@end
