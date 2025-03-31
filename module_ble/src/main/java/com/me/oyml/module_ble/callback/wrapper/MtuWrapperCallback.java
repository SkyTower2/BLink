package com.me.oyml.module_ble.callback.wrapper;

public interface MtuWrapperCallback<T> {

    void onMtuChanged(T device, int mtu, int status);

}
