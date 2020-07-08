//
//  RNMasterpassKit.h
//  MasterpassKit
//
//  Created by Tuan Luong on 7/8/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "MasterPassKit.h"

static NSString *const kEventMasterpassUserDidCancel = @"masterpassUserDidCancel";
static NSString *const kEventMasterpassPaymentSucceeded = @"masterpassPaymentSucceeded";
static NSString *const kEventMasterpassError = @"masterpassError";
static NSString *const kEventMasterpassPaymentFailed = @"masterpassPaymentFailed";
static NSString *const kEventMasterpassInvalidCode = @"masterpassInvalidCode";
static NSString *const kEventMasterpassUserRegistered = @"masterpassUserRegistered";
static NSString *const kEventMasterpassUserCompletedWallet = @"masterpassUserCompletedWallet";

NS_ASSUME_NONNULL_BEGIN

@interface RNMasterpassKit : RCTEventEmitter <RCTBridgeModule, MPMasterPassDelegate>

@end

NS_ASSUME_NONNULL_END
