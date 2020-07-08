//
//  RNMasterpassKit.m
//  MasterpassKit
//
//  Created by Tuan Luong on 7/8/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import "RNMasterpassKit.h"

#import <React/RCTConvert.h>
#import <React/RCTUtils.h>

@implementation RCTConvert (RNMasterpassKit)

+(MPSystem)MPSystem:(NSString*)value {
    if ([value isEqualToString:@"live"]) {
        return MPSystemLive;
    }
    return MPSystemTest;
}

+(NSString*)MPErrorString:(MPError)error {
    switch (error) {
        case MPErrorOTPError:
            return @"OTPError";
        case MPErrorNetworkError:
            return @"NetworkError";
        case MPErrorExceptionOccored:
            return @"ExceptionOccored";
        case MPErrorPaymentError:
            return @"PaymentError";
        case MPErrorInvalidApiKeyParameter:
            return @"InvalidApiKeyParameter";
        case MPErrorInvalidCodeParameter:
            return @"InvalidCodeParameter";
        case MPErrorSecureCodeNotSupported:
            return @"SecureCodeNotSupported";
        default:
            return @"Unknown";
    }
}

@end

@implementation RNMasterpassKit

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(checkout:(NSDictionary *)params)
{
    NSString* code = [RCTConvert NSString:params[@"code"]];
    NSString* apiKey = [RCTConvert NSString:params[@"apiKey"]];
    MPSystem system = [RCTConvert MPSystem:params[@"system"]];
    NSString* preMSISDN = [RCTConvert NSString:params[@"preMsisdn"]];
    
    UIViewController* topViewController = RCTPresentedViewController();
    MPMasterPass* masterpass = [[MPMasterPass alloc] init];
    [masterpass checkoutWithCode:code apiKey:apiKey system:system controller:topViewController delegate:self preMSISDN:preMSISDN];
}

RCT_EXPORT_METHOD(preRegister:(NSDictionary *)params)
{
    NSString* apiKey = [RCTConvert NSString:params[@"apiKey"]];
    MPSystem system = [RCTConvert MPSystem:params[@"system"]];
    NSString* preMSISDN = [RCTConvert NSString:params[@"preMsisdn"]];
    
    UIViewController* topViewController = RCTPresentedViewController();
    MPMasterPass* masterpass = [[MPMasterPass alloc] init];
    [masterpass preRegister:apiKey system:system controller:topViewController delegate:self preMSISDN:preMSISDN];
}

RCT_EXPORT_METHOD(manageCardList:(NSDictionary *)params)
{
    NSString* apiKey = [RCTConvert NSString:params[@"apiKey"]];
    MPSystem system = [RCTConvert MPSystem:params[@"system"]];
    NSString* preMSISDN = [RCTConvert NSString:params[@"preMsisdn"]];
    
    UIViewController* topViewController = RCTPresentedViewController();
    MPMasterPass* masterpass = [[MPMasterPass alloc] init];
    [masterpass WalletManagement:apiKey system:system controller:topViewController delegate:self preMSISDN:preMSISDN];
}

// MARK: - MPMasterPassDelegate
-(void)masterpassUserDidCancel {
    NSLog(@"masterpassUserDidCancel");
    [self sendEventWithName:kEventMasterpassUserDidCancel body:nil];
}

-(void)masterpassPaymentSucceededWithTransactionReference:(NSString *)transactionReference {
    NSLog(@"masterpassPaymentSucceededWithTransactionReference");
    [self sendEventWithName:kEventMasterpassPaymentSucceeded body:@{
        @"transaction": transactionReference,
    }];
}

-(void)masterpassError:(MPError)error {
    NSLog(@"masterpassError");
    [self sendEventWithName:kEventMasterpassError body:@{
        @"code": [RCTConvert MPErrorString:error],
    }];
}

-(void)masterpassPaymentFailedWithTransactionReference:(NSString *)transactionReference {
    NSLog(@"masterpassPaymentFailedWithTransactionReference");
    [self sendEventWithName:kEventMasterpassPaymentFailed body:@{
        @"transaction": transactionReference,
    }];
}

-(void)masterpassInvalidCode {
    NSLog(@"masterpassInvalidCode");
    [self sendEventWithName:kEventMasterpassInvalidCode body:nil];
}

-(void)masterpassUserRegistered {
    NSLog(@"masterpassUserRegistered");
    [self sendEventWithName:kEventMasterpassUserRegistered body:nil];
}

-(void)masterpassUserCompletedWallet {
    NSLog(@"masterpassUserCompletedWallet");
    [self sendEventWithName:kEventMasterpassUserCompletedWallet body:nil];
}

-(NSArray<NSString *> *)supportedEvents {
    return @[
        kEventMasterpassUserDidCancel,
        kEventMasterpassPaymentSucceeded,
        kEventMasterpassError,
        kEventMasterpassPaymentFailed,
        kEventMasterpassInvalidCode,
        kEventMasterpassUserRegistered,
        kEventMasterpassUserCompletedWallet
    ];
}
@end
