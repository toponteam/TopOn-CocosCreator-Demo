//
//  ATAdWrapper.m
//  AnyThinkSDKDemo
//
//  Created by Martin Lau on 2020/4/16.
//  Copyright © 2020 AnyThink. All rights reserved.
//

#import "ATAdWrapper.h"

NSString *const kLoadUseRVAsInterstitialKey = @"UseRewardedVideoAsInterstitial";

@interface ATAdWrapper()
@end
@implementation ATAdWrapper
+(instancetype) sharedWrapper {
    return nil;
}

+(void) setDelegates:(NSString*)delegatesJsonStr {
    NSLog(@"%@::setDelegates:%@", NSStringFromClass(self), delegatesJsonStr);
    [[self sharedWrapper] configureDelegates:delegatesJsonStr];
}

-(void) configureDelegates:(NSString*)delegatesJsonStr {
    if ([delegatesJsonStr isKindOfClass:[NSString class]]) { _delegates = [[NSJSONSerialization JSONObjectWithString:delegatesJsonStr options:NSJSONReadingFragmentsAllowed error:nil] copy]; }
}

+ (NSArray *)jsonStrToArray:(NSString *)jsonString{
   
    
    NSError *error;
    NSArray *array = [NSArray array];
    
    @try {
        NSData *jsonData = [jsonString dataUsingEncoding:NSUTF8StringEncoding];

        array = [NSJSONSerialization JSONObjectWithData:jsonData
                                                            options:NSJSONReadingMutableContainers
                                                              error:&error];
    } @catch (NSException *exception) {
        NSLog(@"jsonStrToArray --- exception:%@",exception);
    } @finally {}

    return array;
}


@end
