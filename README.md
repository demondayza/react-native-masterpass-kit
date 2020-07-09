# react-native-masterpass-kit

## Getting started

`$ yarn add react-native-masterpass-kit`

## iOS setup
### Copy framework
You need to copy corresponding `MasterPassKit.framework` to `node_modules/react-native-masterpass-kit/ios/Frameworks`

## Methods
1. `checkout`
    - `code`: string (required) __This is the code created by calling the Masterpass API representing the transaction.__
    - `apiKey`: string (required) __This is the API key provided by Masterpass that will enable the library to be used. This can be found on the Masterpass Portal under the Lib Lite Tokens menu item.__
    - `system`: 'test' | 'live' (required) __Representing the Masterpass backend system to connect to.__
    - `preMsisdn`: string (optional) __This is an optional field if you know the client’s mobile number.__
    - `hash`: string (optional) [Android only] __This allows capability for the app to read the SMS for registration. Details on how to generate the HASH key can be String found here https://developers.google.com/identity/sms-retriever/verify#computing_your_apps_hash_string__
2. `preRegister`
    - `apiKey`: string (required)
    - `system`: 'test' | 'live' (required)
    - `preMsisdn`: string (optional)
3. `manageCardList`
    - `apiKey`: string (required)
    - `system`: 'test' | 'live' (required)
    - `preMsisdn`: string (optional)

## Events
1. `registerUserDidCancelEvent` __Call when user aborted the process__
2. `registerPaymentSucceededEvent` __Call when payment success__
    - transaction: string __This is the reference for the transaction. This reference will be used to tie up the transaction on the 3rd party backend system__
3. `registerErrorEvent` __Call when an error has occurred before the payment__
    - code: string __Error code. Prefer below__
    - location: number [Android only] __This is the location of the error. This can be used to debug the error when requesting support from Oltio.__
4. `registerPaymentFailedEvent` __Call when the payment failed__
    - `transaction`: string __This is the reference for the failed transaction in the event the 3rd party needs to request more information regarding the failure__
5. `registerInvalidCodeEvent` __Call when the code param invalid__
6. `registerUserRegisterEvent` __Call when the user has registered with Masterpass__
7. `registerUserCompletedWalletEvent` __Call when user done with wallet__

## Error codes
    - OTPError
    - NetworkError
    - ExceptionOccored
    - PaymentError
    - InvalidApiKeyParameter
    - InvalidCodeParameter
    - SecureCodeNotSupported
    - Unknown
## Usage
```javascript
import RNMasterpassKit from 'react-native-masterpass-kit';

RNMasterpassKit.checkout({
    code: '6799782555',
    apiKey: '1234567890',
    system: 'test',
});

const subcription = RNMasterpassKit.registerUserDidCancelEvent(() => {
    console.warn('User cancelled');
});
// And later on
subcription.remove();
```
