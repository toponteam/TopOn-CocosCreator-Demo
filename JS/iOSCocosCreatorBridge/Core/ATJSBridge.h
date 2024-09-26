//
//  ATJSBridge.h
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
@interface ATJSBridge : NSObject
+(void) callJSMethodWithString:(NSString*)string;
@end

@interface NSJSONSerialization(AnyThink)
+(id)JSONObjectWithString:(NSString *)string options:(NSJSONReadingOptions)opt error:(NSError *__autoreleasing *)error;
@end

@interface NSDictionary(AnyThink)
-(NSString*)jsonString_AnyThinkJS;
@end

@interface UIColor (Hex)
+ (UIColor *)colorWithHex:(long)hexColor;
+ (UIColor *)colorWithHex:(long)hexColor alpha:(float)opacity;
+ (UIColor *)colorWithHexString:(NSString *)color;
@end
