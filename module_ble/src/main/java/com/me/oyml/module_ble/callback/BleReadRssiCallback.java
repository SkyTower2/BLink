package com.me.oyml.module_ble.callback;

public abstract class BleReadRssiCallback<T> {

    public void onReadRssiSuccess(T device, int rssi) {
    }
}
