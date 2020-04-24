//
//  ATAdWrapper.h
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ATJSBridge.h"
@interface ATAdWrapper : NSObject
+(instancetype) sharedWrapper;
+(void) setDelegates:(NSString*)delegatesJsonStr;
@property(nonatomic, readonly) NSDictionary<NSString*, NSString*> *delegates;
@end
