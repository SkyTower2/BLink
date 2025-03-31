package com.me.oyml.module_ble.callback.wrapper;

import android.bluetooth.BluetoothGattCharacteristic;

public interface WriteWrapperCallback<T> {
    void onWriteSuccess(T device, BluetoothGattCharacteristic characteristic);

    void onWriteFailed(T device, int failedCode);
}
