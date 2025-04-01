package com.me.oyml.module_ble.request;

import com.me.oyml.module_ble.Ble;
import com.me.oyml.module_ble.BleRequestImpl;
import com.me.oyml.module_ble.annotation.Implement;
import com.me.oyml.module_ble.callback.BleMtuCallback;
import com.me.oyml.module_ble.callback.wrapper.BleWrapperCallback;
import com.me.oyml.module_ble.callback.wrapper.MtuWrapperCallback;
import com.me.oyml.module_ble.model.BleDevice;

@Implement(MtuRequest.class)
public class MtuRequest<T extends BleDevice> implements MtuWrapperCallback<T> {

    private BleMtuCallback<T> bleMtuCallback;
    private final BleWrapperCallback<T> bleWrapperCallback = Ble.options().getBleWrapperCallback();
    private final BleRequestImpl<T> bleRequest = BleRequestImpl.getBleRequest();

    public boolean setMtu(String address, int mtu, BleMtuCallback<T> callback){
        this.bleMtuCallback = callback;
        return bleRequest.setMtu(address, mtu);
    }

    @Override
    public void onMtuChanged(T device, int mtu, int status) {
        if(null != bleMtuCallback){
            bleMtuCallback.onMtuChanged(device, mtu, status);
        }

        if (bleWrapperCallback != null){
            bleWrapperCallback.onMtuChanged(device, mtu, status);
        }
    }
}
