package com.me.oyml.module_ble.request;

import com.me.oyml.module_ble.BleRequestImpl;
import com.me.oyml.module_ble.annotation.Implement;
import com.me.oyml.module_ble.callback.BleReadRssiCallback;
import com.me.oyml.module_ble.model.BleDevice;

import com.me.oyml.module_ble.callback.wrapper.ReadRssiWrapperCallback;

@Implement(ReadRssiRequest.class)
public class ReadRssiRequest<T extends BleDevice> implements ReadRssiWrapperCallback<T> {

    private BleReadRssiCallback<T> readRssiCallback;
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public boolean readRssi(T device, BleReadRssiCallback<T> callback) {
        this.readRssiCallback = callback;
        boolean result = false;
        if (bleRequest != null) {
            result = bleRequest.readRssi(device.getBleAddress());
        }
        return result;
    }

    @Override
    public void onReadRssiSuccess(T device, int rssi) {
        if (readRssiCallback != null) {
            readRssiCallback.onReadRssiSuccess(device, rssi);
        }
    }
}
