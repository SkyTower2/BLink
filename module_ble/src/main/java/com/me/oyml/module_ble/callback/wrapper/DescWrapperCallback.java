package com.me.oyml.module_ble.callback.wrapper;

import android.bluetooth.BluetoothGattDescriptor;

public interface DescWrapperCallback<T> {

    void onDescReadSuccess(T device, BluetoothGattDescriptor descriptor);

    void onDescReadFailed(T device, int failedCode);

    void onDescWriteSuccess(T device, BluetoothGattDescriptor descriptor);

    void onDescWriteFailed(T device, int failedCode);
}
