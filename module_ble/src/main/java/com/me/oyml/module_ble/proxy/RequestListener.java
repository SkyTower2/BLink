package com.me.oyml.module_ble.proxy;

import com.me.oyml.module_ble.callback.BleConnectCallback;
import com.me.oyml.module_ble.callback.BleMtuCallback;
import com.me.oyml.module_ble.callback.BleNotifyCallback;
import com.me.oyml.module_ble.callback.BleReadCallback;
import com.me.oyml.module_ble.callback.BleReadRssiCallback;
import com.me.oyml.module_ble.callback.BleScanCallback;
import com.me.oyml.module_ble.callback.BleWriteCallback;
import com.me.oyml.module_ble.callback.BleWriteEntityCallback;
import com.me.oyml.module_ble.model.EntityData;

import java.util.UUID;

public interface RequestListener<T> {

    void startScan(BleScanCallback<T> callback, long scanPeriod);

    void stopScan();

    boolean connect(T device, BleConnectCallback<T> callback);

    boolean connect(String address, BleConnectCallback<T> callback);

    void notify(T device, BleNotifyCallback<T> callback);

    void cancelNotify(T device, BleNotifyCallback<T> callback);

    void enableNotify(T device, boolean enable, BleNotifyCallback<T> callback);

    void enableNotifyByUuid(T device, boolean enable, UUID serviceUUID, UUID characteristicUUID, BleNotifyCallback<T> callback);

    void disconnect(T device);

    void disconnect(T device, BleConnectCallback<T> callback);

    boolean read(T device, BleReadCallback<T> callback);

    boolean readByUuid(T device, UUID serviceUUID, UUID characteristicUUID, BleReadCallback<T> callback);

    boolean readRssi(T device, BleReadRssiCallback<T> callback);

    boolean write(T device, byte[] data, BleWriteCallback<T> callback);

    boolean writeByUuid(T device, byte[] data, UUID serviceUUID, UUID characteristicUUID, BleWriteCallback<T> callback);

    void writeEntity(T device, final byte[] data, int packLength, int delay, BleWriteEntityCallback<T> callback);

    void writeEntity(EntityData entityData, BleWriteEntityCallback<T> callback);

    void cancelWriteEntity();

    boolean setMtu(String address, int mtu, BleMtuCallback<T> callback);

}
