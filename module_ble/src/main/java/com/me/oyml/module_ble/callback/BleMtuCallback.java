package com.me.oyml.module_ble.callback;

import com.me.oyml.module_ble.model.BleDevice;

public abstract class BleMtuCallback<T> {

    public void onMtuChanged(BleDevice device, int mtu, int status) {
    }

}
