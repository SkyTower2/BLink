package com.me.oyml.module_ble.callback;

import android.bluetooth.BluetoothGattCharacteristic;

public abstract class BleNotifyCallback<T> {
    /**
     * MCU data sent to the app when the data callback call is setNotify
     *
     * @param device         ble device object
     * @param characteristic characteristic
     */
    public abstract void onChanged(T device, BluetoothGattCharacteristic characteristic);

    public void onNotifySuccess(T device) {
    }

    public void onNotifyFailed(T device, int failedCode) {
    }

    public void onNotifyCanceled(T device) {
    }

}
