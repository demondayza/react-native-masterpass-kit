interface MasterpassKitCommon {
  /**
   * This is the API key provided by Masterpass that will enable the library to be used. This can be found on the Masterpass Portal under the Lib Lite Tokens menu item.
   */
  apiKey: string;
  /**
   * Representing the Masterpass backend system to connect to.
   */
  system: 'test' | 'live';
  /**
   * This is an optional field if you know the client’s mobile number.
   */
  preMsisdn?: string;
}

export interface CheckoutParams extends MasterpassKitCommon {
  /**
   * This is the code created by calling the Masterpass API representing the transaction.
   */
  code: string;

  /**
   * [Android only] This allows capability for the app to read the SMS for registration.
   * Details on how to generate the HASH key can be String found here https://developers.google.com/identity/sms-retriever/verify#computing_your_apps_hash_string
   */
  hash?: string;
}

export interface PreRegisterParams extends MasterpassKitCommon {}

export interface ManageCardListParams extends MasterpassKitCommon {}
