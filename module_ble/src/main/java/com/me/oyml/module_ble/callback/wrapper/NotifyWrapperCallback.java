package com.me.oyml.module_ble.callback.wrapper;

import android.bluetooth.BluetoothGattCharacteristic;

public interface NotifyWrapperCallback<T> {

    void onChanged(T device, BluetoothGattCharacteristic characteristic);

    void onNotifySuccess(T device);

    void onNotifyFailed(T device, int failedCode);

    void onNotifyCanceled(T device);
}
