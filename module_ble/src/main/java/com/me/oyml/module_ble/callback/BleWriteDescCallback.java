package com.me.oyml.module_ble.callback;


import android.bluetooth.BluetoothGattDescriptor;

public abstract class BleWriteDescCallback<T> {

    public void onDescWriteSuccess(T device, BluetoothGattDescriptor descriptor) {
    }

    public void onDescWriteFailed(T device, int failedCode) {
    }

}
