#import <DigitsKit/DigitsKit.h>
#import "RNDigits.h"

@implementation RNDigits

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(logout)
{
    [[Digits sharedInstance] logOut];
}

RCT_EXPORT_METHOD(view:(RCTResponseSenderBlock)callback)
{
  Digits *digits = [Digits sharedInstance];
  [digits authenticateWithCompletion:^(DGTSession *session, NSError *error) {
    if (error) {
      callback(@[error.localizedDescription, [NSNull null]]);
    } else {
       NSDictionary *auth = @{
                              @"consumerKey": [[digits authConfig] consumerKey],
                              @"consumerSecret": [[digits authConfig] consumerSecret],
                              @"authToken": session.authToken,
                              @"authTokenSecret": session.authTokenSecret,
                              @"userId": session.userID,
                              @"phoneNumber": session.phoneNumber
                              };
       callback(@[[NSNull null], auth]);
    }
  }];
}


@end
