# react-native-masterpass-kit

## Getting started

`$ yarn add react-native-masterpass-kit`

## iOS setup
### Copy framework
You need to copy corresponding `MasterPassKit.framework` to `node_modules/react-native-masterpass-kit/ios/Frameworks`

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
