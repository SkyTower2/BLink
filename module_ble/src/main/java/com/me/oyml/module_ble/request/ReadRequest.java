package com.me.oyml.module_ble.request;

import android.bluetooth.BluetoothGattCharacteristic;

import com.me.oyml.module_ble.Ble;
import com.me.oyml.module_ble.BleRequestImpl;
import com.me.oyml.module_ble.annotation.Implement;
import com.me.oyml.module_ble.callback.BleReadCallback;
import com.me.oyml.module_ble.callback.wrapper.BleWrapperCallback;
import com.me.oyml.module_ble.callback.wrapper.ReadWrapperCallback;
import com.me.oyml.module_ble.model.BleDevice;

import java.util.UUID;

@Implement(ReadRequest.class)
public class ReadRequest<T extends BleDevice> implements ReadWrapperCallback<T> {

    private BleReadCallback<T> bleReadCallback;
    private final BleWrapperCallback<T> bleWrapperCallback = Ble.options().getBleWrapperCallback();
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public boolean read(T device, BleReadCallback<T> callback) {
        this.bleReadCallback = callback;
        return bleRequest.readCharacteristic(device.getBleAddress());
    }

    public boolean readByUuid(T device, UUID serviceUUID, UUID characteristicUUID, BleReadCallback<T> callback) {
        this.bleReadCallback = callback;
        return bleRequest.readCharacteristicByUuid(device.getBleAddress(), serviceUUID, characteristicUUID);
    }

    @Override
    public void onReadSuccess(T device, BluetoothGattCharacteristic characteristic) {
        if (bleReadCallback != null) {
            bleReadCallback.onReadSuccess(device, characteristic);
        }

        if (bleWrapperCallback != null) {
            bleWrapperCallback.onReadSuccess(device, characteristic);
        }
    }

    @Override
    public void onReadFailed(T device, int failedCode) {
        if (bleReadCallback != null) {
            bleReadCallback.onReadFailed(device, failedCode);
        }

        if (bleWrapperCallback != null) {
            bleWrapperCallback.onReadFailed(device, failedCode);
        }
    }
}
