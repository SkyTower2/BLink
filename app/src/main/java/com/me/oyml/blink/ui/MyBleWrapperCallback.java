package com.me.oyml.blink.ui;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

import com.me.oyml.common.utils.KLog;
import com.me.oyml.module_ble.callback.wrapper.BleWrapperCallback;
import com.me.oyml.module_ble.model.BleDevice;
import com.me.oyml.module_ble.utils.ByteUtils;


/**
 * author: jerry
 * date: 20-4-13
 * email: superliu0911@gmail.com
 * des: 例： OTA升级可以再这里实现,与项目其他功能逻辑完全解耦
 */
public class MyBleWrapperCallback extends BleWrapperCallback<BleDevice> {

    private static final String TAG = "MyBleWrapperCallback";

    @Override
    public void onChanged(BleDevice device, BluetoothGattCharacteristic characteristic) {
        super.onChanged(device, characteristic);
        KLog.d("onChanged: " + ByteUtils.toHexString(characteristic.getValue()));
    }

    @Override
    public void onServicesDiscovered(BleDevice device, BluetoothGatt gatt) {
        super.onServicesDiscovered(device, gatt);
        KLog.d("onServicesDiscovered: ");
    }

    @Override
    public void onWriteSuccess(BleDevice device, BluetoothGattCharacteristic characteristic) {
        super.onWriteSuccess(device, characteristic);
        KLog.d("onWriteSuccess: ");
    }

    @Override
    public void onConnectionChanged(BleDevice device) {
        super.onConnectionChanged(device);
        KLog.d("onConnectionChanged: " + device.toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.d("onStop: ");
    }

    @Override
    public void onLeScan(BleDevice device, int rssi, byte[] scanRecord) {
        super.onLeScan(device, rssi, scanRecord);
        KLog.d("onLeScan: " + device.toString());
    }

    @Override
    public void onNotifySuccess(BleDevice device) {
        super.onNotifySuccess(device);
        KLog.d("onNotifySuccess: ");
    }

    @Override
    public void onNotifyFailed(BleDevice device, int failedCode) {
        KLog.d("onNotifyFailed: " + failedCode);
    }

    @Override
    public void onNotifyCanceled(BleDevice device) {
        super.onNotifyCanceled(device);
        KLog.d("onNotifyCanceled: ");
    }

    @Override
    public void onReady(BleDevice device) {
        super.onReady(device);
        KLog.d("onReady: ");
    }

    @Override
    public void onDescWriteSuccess(BleDevice device, BluetoothGattDescriptor descriptor) {
        super.onDescWriteSuccess(device, descriptor);
    }

    @Override
    public void onDescWriteFailed(BleDevice device, int failedCode) {
        super.onDescWriteFailed(device, failedCode);
    }

    @Override
    public void onDescReadFailed(BleDevice device, int failedCode) {
        super.onDescReadFailed(device, failedCode);
    }

    @Override
    public void onDescReadSuccess(BleDevice device, BluetoothGattDescriptor descriptor) {
        super.onDescReadSuccess(device, descriptor);
    }

    @Override
    public void onMtuChanged(BleDevice device, int mtu, int status) {
        super.onMtuChanged(device, mtu, status);
    }

    @Override
    public void onReadSuccess(BleDevice device, BluetoothGattCharacteristic characteristic) {
        super.onReadSuccess(device, characteristic);
    }

}
