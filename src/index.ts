// @ts-ignore
import { NativeModules, EmitterSubscription } from 'react-native';
import { CheckoutParams, PreRegisterParams, ManageCardListParams } from './types';
import eventEmitter from './eventEmitter';

const { RNMasterpassKit } = NativeModules;

function checkout(params: CheckoutParams) {
  RNMasterpassKit.checkout(params);
}

function preRegister(params: PreRegisterParams) {
  RNMasterpassKit.preRegister(params);
}

function manageCardList(params: ManageCardListParams) {
  RNMasterpassKit.manageCardList(params);
}

function registerUserDidCancelEvent(callback): EmitterSubscription {
  return eventEmitter.addListener("masterpassUserDidCancel", callback);
}

function registerPaymentSucceededEvent(callback: ({ transaction: string }) => void): EmitterSubscription {
  return eventEmitter.addListener("masterpassPaymentSucceeded", callback);
}

function registerErrorEvent(callback: ({ code: string }) => void): EmitterSubscription {
  return eventEmitter.addListener("masterpassError", callback);
}

function registerPaymentFailedEvent(callback: ({ transaction: string }) => void): EmitterSubscription {
  return eventEmitter.addListener("masterpassPaymentFailed", callback);
}

function registerInvalidCodeEvent(callback): EmitterSubscription {
  return eventEmitter.addListener("masterpassInvalidCode", callback);
}

function registerUserRegisterEvent(callback): EmitterSubscription {
  return eventEmitter.addListener("masterpassUserRegistered", callback);
}

function registerUserCompletedWalletEvent(callback): EmitterSubscription {
  return eventEmitter.addListener("masterpassUserCompletedWallet", callback);
}

export default {
  checkout,
  preRegister,
  manageCardList,

  registerUserDidCancelEvent,
  registerPaymentSucceededEvent,
  registerErrorEvent,
  registerPaymentFailedEvent,
  registerInvalidCodeEvent,
  registerUserRegisterEvent,
  registerUserCompletedWalletEvent,
};
