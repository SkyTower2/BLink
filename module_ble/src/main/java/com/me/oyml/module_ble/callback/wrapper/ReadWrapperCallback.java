package com.me.oyml.module_ble.callback.wrapper;

import android.bluetooth.BluetoothGattCharacteristic;

public interface ReadWrapperCallback<T> {

    void onReadSuccess(T device, BluetoothGattCharacteristic characteristic);

    void onReadFailed(T device, int failedCode);

}
