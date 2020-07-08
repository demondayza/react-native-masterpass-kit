// @ts-ignore
import { NativeEventEmitter, NativeModules } from 'react-native';
const { RNMasterpassKit } = NativeModules;

const eventEmitter: NativeEventEmitter = new NativeEventEmitter(RNMasterpassKit);
export default eventEmitter;