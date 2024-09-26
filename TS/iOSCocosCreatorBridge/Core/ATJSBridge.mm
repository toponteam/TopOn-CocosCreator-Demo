//
//  ATJSBridge.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATJSBridge.h"
#include "cocos/bindings/jswrapper/v8/ScriptEngine.h"


NSString *const kATUnityUserExtraDataKey = @"user_load_extra_data";
NSString *const kATUnityCheckLoadModelAdInfoKey = @"adInfo";

@implementation ATJSBridge
+(void) log:(NSString*)log {
    NSLog(@"%@", log);
}

+(void) callJSMethodWithString:(NSString*)string {
   // string    __NSCFString *    "cc.find('Canvas').getComponent('ATJSSDK').getUserLocationCallback(2)"    0x00000002832db9c0
    NSArray<NSString*>* component = [string componentsSeparatedByString:@"("];
    NSArray<NSString*>* methods = [[component firstObject] componentsSeparatedByString:@"."];
    string = [NSString stringWithFormat:@"cc.find('Canvas').getComponent('%@').%@(%@",[methods firstObject],[methods lastObject],[component lastObject]];
//
// string = @"cc.find('Canvas').getComponent('ATRewardSceneScript').onRewardedVideoAdPlayFailed('b5b44a0f115321', '', '{}')";
//    
    NSLog(@"ATJSBridge:: v:%@", string);
    se::ScriptEngine::getInstance()->evalString(string.UTF8String);
}
@end

@implementation NSJSONSerialization(AnyThink)
+(id)JSONObjectWithString:(NSString *)string options:(NSJSONReadingOptions)opt error:(NSError *__autoreleasing *)error { return [self JSONObjectWithData:[string dataUsingEncoding:NSUTF8StringEncoding] options:opt error:error]; }
@end

@implementation NSDictionary(AnyThink)
-(NSString*)jsonString_AnyThinkJS {
    NSError *error;
    NSData *jsonData;
    
    // 移除 user_load_extra_data 中不支持的 类型
    NSMutableDictionary *extraDictM = [NSMutableDictionary dictionaryWithDictionary:self];
    NSMutableDictionary *extraDataTemp = [NSMutableDictionary dictionary];
    NSMutableDictionary *extraDataDictM = [NSMutableDictionary dictionaryWithDictionary:self[kATUnityUserExtraDataKey]];
    for (NSString *key in extraDataDictM.allKeys) {
        if ([extraDataDictM[key] isKindOfClass:[NSString class]] || [extraDataDictM[key] isKindOfClass:[NSNumber class]]) {
            [extraDataTemp setValue:extraDataDictM[key] forKey:key];
        }
    }
    if ([extraDataTemp count]) {
        [extraDictM setValue:extraDataTemp forKey:kATUnityUserExtraDataKey];
    } else {
        [extraDictM removeObjectForKey:kATUnityUserExtraDataKey];
    }
    
    @try {
        jsonData = [NSJSONSerialization dataWithJSONObject:extraDictM
                                                           options:kNilOptions
                                                             error:&error];
    } @catch (NSException *exception) {
        return @"{}";
    } @finally {}
    
    if (!jsonData) {
        return @"{}";
    } else {
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }
}

-(NSString*)jsonString_AnyThinkJSAdInfo {
    
    NSError *error;
    NSData *jsonData;
    
    // 移除 user_load_extra_data 中不支持的 类型
    NSMutableDictionary *extraDictM = [NSMutableDictionary dictionaryWithDictionary:self[kATUnityCheckLoadModelAdInfoKey]];
    NSMutableDictionary *extraDataTemp = [NSMutableDictionary dictionary];
    NSMutableDictionary *extraDataDictM = [NSMutableDictionary dictionaryWithDictionary:self[kATUnityCheckLoadModelAdInfoKey][kATUnityUserExtraDataKey]];
    for (NSString *key in extraDataDictM.allKeys) {
        if ([extraDataDictM[key] isKindOfClass:[NSString class]] || [extraDataDictM[key] isKindOfClass:[NSNumber class]]) {
            [extraDataTemp setValue:extraDataDictM[key] forKey:key];
        }
    }
    if ([extraDataTemp count]) {
        [extraDictM setValue:extraDataTemp forKey:kATUnityUserExtraDataKey];
    } else {
        [extraDictM removeObjectForKey:kATUnityUserExtraDataKey];
    }
    
    NSMutableDictionary *jsonDictM = [NSMutableDictionary dictionaryWithDictionary:self];
    [jsonDictM setValue:extraDictM forKey:kATUnityCheckLoadModelAdInfoKey];

    
    @try {
        jsonData = [NSJSONSerialization dataWithJSONObject:jsonDictM
                                                           options:kNilOptions
                                                             error:&error];
    } @catch (NSException *exception) {
        return @"{}";
    } @finally {}
    
    if (!jsonData) {
        return @"{}";
    } else {
        return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    }
}

@end

@implementation UIColor (Hex)
// 透明度固定为1，以0x开头的十六进制转换成的颜色
+ (UIColor*) colorWithHex:(long)hexColor;
{
    return [UIColor colorWithHex:hexColor alpha:1.];
}
// 0x开头的十六进制转换成的颜色,透明度可调整
+ (UIColor *)colorWithHex:(long)hexColor alpha:(float)opacity
{
    float red = ((float)((hexColor & 0xFF0000) >> 16))/255.0;
    float green = ((float)((hexColor & 0xFF00) >> 8))/255.0;
    float blue = ((float)(hexColor & 0xFF))/255.0;
    return [UIColor colorWithRed:red green:green blue:blue alpha:opacity];
}
// 颜色转换三：iOS中十六进制的颜色（以#开头）转换为UIColor
+ (UIColor *) colorWithHexString: (NSString *)color
{
    NSString *cString = [[color stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    // String should be 6 or 8 characters
    if ([cString length] < 6) {
        return [UIColor clearColor];
    }
    
    // 判断前缀并剪切掉
    if ([cString hasPrefix:@"0X"])
        cString = [cString substringFromIndex:2];
    if ([cString hasPrefix:@"#"])
        cString = [cString substringFromIndex:1];
    if ([cString length] != 6)
        return [UIColor clearColor];
    
    // 从六位数值中找到RGB对应的位数并转换
    NSRange range;
    range.location = 0;
    range.length = 2;
    
    //R、G、B
    NSString *rString = [cString substringWithRange:range];
    
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    return [UIColor colorWithRed:((float) r / 255.0f) green:((float) g / 255.0f) blue:((float) b / 255.0f) alpha:1.0f];
}
@end
