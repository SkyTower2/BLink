package com.me.oyml.module_ble.request;

import android.bluetooth.BluetoothGattCharacteristic;

import com.me.oyml.module_ble.Ble;
import com.me.oyml.module_ble.BleRequestImpl;
import com.me.oyml.module_ble.annotation.Implement;
import com.me.oyml.module_ble.callback.BleNotifyCallback;
import com.me.oyml.module_ble.callback.wrapper.BleWrapperCallback;
import com.me.oyml.module_ble.callback.wrapper.NotifyWrapperCallback;
import com.me.oyml.module_ble.model.BleDevice;

import java.util.UUID;

@Implement(NotifyRequest.class)
public class NotifyRequest<T extends BleDevice> implements NotifyWrapperCallback<T> {

    private static final String TAG = "NotifyRequest";
    private BleNotifyCallback<T> notifyCallback;
    private final BleWrapperCallback<T> bleWrapperCallback = Ble.options().getBleWrapperCallback();
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public void notify(T device, boolean enable, BleNotifyCallback<T> callback) {
        notifyCallback = callback;
        bleRequest.setCharacteristicNotification(device.getBleAddress(), enable);
    }

    public void notifyByUuid(T device, boolean enable, UUID serviceUUID, UUID characteristicUUID, BleNotifyCallback<T> callback) {
        notifyCallback = callback;
        bleRequest.setCharacteristicNotificationByUuid(device.getBleAddress(), enable, serviceUUID, characteristicUUID);
    }

    @Deprecated
    public void cancelNotify(T device, BleNotifyCallback<T> callback) {
        notifyCallback = callback;
        bleRequest.setCharacteristicNotification(device.getBleAddress(), false);
    }

    @Override
    public void onChanged(final T device, final BluetoothGattCharacteristic characteristic) {
        if (null != notifyCallback) {
            notifyCallback.onChanged(device, characteristic);
        }

        if (bleWrapperCallback != null) {
            bleWrapperCallback.onChanged(device, characteristic);
        }
    }

    @Override
    public void onNotifySuccess(final T device) {
        if (null != notifyCallback) {
            notifyCallback.onNotifySuccess(device);
        }
        if (bleWrapperCallback != null) {
            bleWrapperCallback.onNotifySuccess(device);
        }
    }

    @Override
    public void onNotifyFailed(T device, int failedCode) {
        if (null != notifyCallback) {
            notifyCallback.onNotifyFailed(device, failedCode);
        }

        if (bleWrapperCallback != null) {
            bleWrapperCallback.onNotifyFailed(device, failedCode);
        }
    }

    @Override
    public void onNotifyCanceled(T device) {
        if (null != notifyCallback) {
            notifyCallback.onNotifyCanceled(device);
        }

        if (bleWrapperCallback != null) {
            bleWrapperCallback.onNotifyCanceled(device);
        }
    }
}
