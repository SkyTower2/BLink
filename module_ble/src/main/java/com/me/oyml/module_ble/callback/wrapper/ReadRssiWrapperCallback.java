package com.me.oyml.module_ble.callback.wrapper;

public interface ReadRssiWrapperCallback<T> {

    void onReadRssiSuccess(T device, int rssi);
}
