package com.tl.masterpass;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.oltio.liblite.activity.LibLiteActivity;
import com.oltio.liblite.activity.LibLitePreRegActivity;
import com.oltio.liblite.activity.LibLiteWalletActivity;

import javax.annotation.Nullable;

public class MasterpassKitModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public MasterpassKitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        reactContext.addActivityEventListener(mActivityEventListener);

    }

    @Override
    public String getName() {
        return "RNMasterpassKit";
    }

    @ReactMethod
    public void checkout(ReadableMap params) {
        String code = params.getString("code");
        String apiKey = params.getString("apiKey");
        String system = params.getString("system");
        String hash = params.hasKey("hash") ? params.getString("hash") : "";
        String preMSISDN = params.hasKey("preMsisdn") ? params.getString("preMsisdn") : "";
        Intent intent = new Intent(getReactApplicationContext(), LibLiteActivity.class);
        intent.putExtra(LibLiteActivity.IN_CODE, code);
        intent.putExtra(LibLiteActivity.IN_API_KEY, apiKey);
        intent.putExtra(LibLiteActivity.IN_HASH, hash);
        intent.putExtra(LibLiteActivity.IN_SYSTEM, system.toUpperCase());
        intent.putExtra(LibLiteActivity.IN_SUGGESTED_MSISDN, preMSISDN);
        getCurrentActivity().startActivityForResult(intent, 10);
    }

    @ReactMethod
    public void preRegister(ReadableMap params) {
        String apiKey = params.getString("apiKey");
        String system = params.getString("system");
        String preMSISDN = params.getString("preMsisdn");
        Intent intent = new Intent(getReactApplicationContext(), LibLitePreRegActivity.class);
        intent.putExtra(LibLiteActivity.IN_API_KEY, apiKey);
        intent.putExtra(LibLiteActivity.IN_SYSTEM, system.toUpperCase());
        intent.putExtra(LibLiteActivity.IN_SUGGESTED_MSISDN, preMSISDN);
        getCurrentActivity().startActivityForResult(intent, 10);
    }

    @ReactMethod
    public void manageCardList(ReadableMap params) {
        String apiKey = params.getString("apiKey");
        String system = params.getString("system");
        String preMSISDN = params.getString("preMsisdn");
        Intent intent = new Intent(getReactApplicationContext(), LibLiteWalletActivity.class);
        intent.putExtra(LibLiteActivity.IN_API_KEY, apiKey);
        intent.putExtra(LibLiteActivity.IN_SYSTEM, system.toUpperCase());
        intent.putExtra(LibLiteActivity.IN_SUGGESTED_MSISDN, preMSISDN);
        getCurrentActivity().startActivityForResult(intent, 10);
    }

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == 10) {
                if (resultCode == LibLiteActivity.LIBLITE_ERROR) {
                    int errorCode = data.getIntExtra(LibLiteActivity.OUT_ERROR_CODE, -1);
                    int location = data.getIntExtra(LibLiteActivity.OUT_LOCATION, -1);
                    WritableMap map = Arguments.createMap();
                    map.putString("code", getErrorCode(errorCode));
                    map.putInt("location", location);
                    sendEvent("masterpassError", map);
                } else if (resultCode == LibLiteActivity.LIBLITE_PAYMENT_SUCCESS) {
                    String txReference = data.getStringExtra(LibLiteActivity.OUT_TX_REFERENCE);
                    WritableMap map = Arguments.createMap();
                    map.putString("transaction", txReference);
                    sendEvent("masterpassPaymentSucceeded", map);
                } else if (resultCode == LibLiteActivity.LIBLITE_PAYMENT_FAILED) {
                    String txReference = data.getStringExtra(LibLiteActivity.OUT_TX_REFERENCE);
                    WritableMap map = Arguments.createMap();
                    map.putString("transaction", txReference);
                    sendEvent("masterpassPaymentFailed", map);
                } else if (resultCode == LibLiteActivity.LIBLITE_USER_CANCELLED) {
                    int location = data.getIntExtra(LibLiteActivity.OUT_LOCATION, -1);
                    WritableMap map = Arguments.createMap();
                    map.putInt("location", location);
                    sendEvent("masterpassUserDidCancel", map);
                } else if (resultCode == LibLiteActivity.LIBLITE_INVALID_CODE) {
                    sendEvent("masterpassInvalidCode", null);
                } else if (resultCode == LibLiteActivity.LIBLITE_REGISTRATION_SUCCESS) {
                    boolean silent = data.getBooleanExtra(LibLiteActivity.OUT_SILENT_REGISTRATION, false);
                    WritableMap map = Arguments.createMap();
                    map.putBoolean("silent", silent);
                    sendEvent("masterpassUserRegistered", map);
                    sendEvent("masterpassUserCompletedWallet", null);
                }
            }
        }
    };

    private String getErrorCode(int code) {
        switch (code) {
            case 1:
                return "NetworkError";
            case 2:
                return "ExceptionOccored";
            case 3:
                return "PaymentError";
            case 4:
                return "OTPError";
            case 5:
                return "InvalidCodeParameter";
            case 6:
                return "InvalidApiKeyParameter";
            case 7:
                return "SecureCodeNotSupported";
            default:
                return "Unknown";
        }
    }

    private void sendEvent(String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
