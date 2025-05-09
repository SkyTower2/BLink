package com.me.oyml.module_ble.proxy;

import com.me.oyml.module_ble.callback.BleConnectCallback;
import com.me.oyml.module_ble.callback.BleMtuCallback;
import com.me.oyml.module_ble.callback.BleNotifyCallback;
import com.me.oyml.module_ble.callback.BleReadCallback;
import com.me.oyml.module_ble.callback.BleReadRssiCallback;
import com.me.oyml.module_ble.callback.BleScanCallback;
import com.me.oyml.module_ble.callback.BleWriteCallback;
import com.me.oyml.module_ble.callback.BleWriteEntityCallback;
import com.me.oyml.module_ble.model.BleDevice;
import com.me.oyml.module_ble.model.EntityData;
import com.me.oyml.module_ble.request.ConnectRequest;
import com.me.oyml.module_ble.request.MtuRequest;
import com.me.oyml.module_ble.request.NotifyRequest;
import com.me.oyml.module_ble.request.ReadRequest;
import com.me.oyml.module_ble.request.ReadRssiRequest;
import com.me.oyml.module_ble.request.Rproxy;
import com.me.oyml.module_ble.request.ScanRequest;
import com.me.oyml.module_ble.request.WriteRequest;

import java.util.UUID;

public class RequestImpl<T extends BleDevice> implements RequestListener<T> {

    public static RequestImpl newRequestImpl() {
        return new RequestImpl();
    }

    @Override
    public void startScan(BleScanCallback<T> callback, long scanPeriod) {
        ScanRequest<T> request = Rproxy.getRequest(ScanRequest.class);
        request.startScan(callback, scanPeriod);
    }

    @Override
    public void stopScan() {
        ScanRequest request = Rproxy.getRequest(ScanRequest.class);
        request.stopScan();
    }

    @Override
    public boolean connect(T device, BleConnectCallback<T> callback) {
        ConnectRequest<T> request = Rproxy.getRequest(ConnectRequest.class);
        return request.connect(device, callback);
    }

    @Override
    public boolean connect(String address, BleConnectCallback<T> callback) {
        ConnectRequest<T> request = Rproxy.getRequest(ConnectRequest.class);
        return request.connect(address, callback);
    }

    @Override
    public void notify(T device, BleNotifyCallback<T> callback) {
        NotifyRequest<T> request = Rproxy.getRequest(NotifyRequest.class);
        request.notify(device, true, callback);
    }

    @Override
    public void cancelNotify(T device, BleNotifyCallback<T> callback) {
        NotifyRequest<T> request = Rproxy.getRequest(NotifyRequest.class);
        request.notify(device, false, callback);
    }

    @Override
    public void enableNotify(T device, boolean enable, BleNotifyCallback<T> callback) {
        NotifyRequest<T> request = Rproxy.getRequest(NotifyRequest.class);
        request.notify(device, enable, callback);
    }

    @Override
    public void enableNotifyByUuid(T device, boolean enable, UUID serviceUUID, UUID characteristicUUID, BleNotifyCallback<T> callback) {
        NotifyRequest<T> request = Rproxy.getRequest(NotifyRequest.class);
        request.notifyByUuid(device, enable, serviceUUID, characteristicUUID, callback);
    }

    @Override
    public void disconnect(T device) {
        ConnectRequest request = Rproxy.getRequest(ConnectRequest.class);
        request.disconnect(device);
    }

    @Override
    public void disconnect(T device, BleConnectCallback<T> callback) {
        ConnectRequest<T> request = Rproxy.getRequest(ConnectRequest.class);
        request.disconnect(device, callback);
    }

    @Override
    public boolean read(T device, BleReadCallback<T> callback) {
        ReadRequest<T> request = Rproxy.getRequest(ReadRequest.class);
        return request.read(device, callback);
    }

    @Override
    public boolean readByUuid(T device, UUID serviceUUID, UUID characteristicUUID, BleReadCallback<T> callback) {
        ReadRequest<T> request = Rproxy.getRequest(ReadRequest.class);
        return request.readByUuid(device, serviceUUID, characteristicUUID, callback);
    }

    @Override
    public boolean readRssi(T device, BleReadRssiCallback<T> callback) {
        ReadRssiRequest<T> request = Rproxy.getRequest(ReadRssiRequest.class);
        return request.readRssi(device, callback);
    }

    @Override
    public boolean write(T device, byte[] data, BleWriteCallback<T> callback) {
        WriteRequest<T> request = Rproxy.getRequest(WriteRequest.class);
        return request.write(device, data, callback);
    }

    @Override
    public boolean writeByUuid(T device, byte[] data, UUID serviceUUID, UUID characteristicUUID, BleWriteCallback<T> callback) {
        WriteRequest<T> request = Rproxy.getRequest(WriteRequest.class);
        return request.writeByUuid(device, data, serviceUUID, characteristicUUID, callback);
    }

    @Override
    public void writeEntity(T device, byte[] data, int packLength, int delay, BleWriteEntityCallback<T> callback) {
        WriteRequest<T> request = Rproxy.getRequest(WriteRequest.class);
        request.writeEntity(device, data, packLength, delay, callback);
    }

    @Override
    public void writeEntity(EntityData entityData, BleWriteEntityCallback<T> callback) {
        WriteRequest<T> request = Rproxy.getRequest(WriteRequest.class);
        request.writeEntity(entityData, callback);
    }

    @Override
    public void cancelWriteEntity() {
        WriteRequest<T> request = Rproxy.getRequest(WriteRequest.class);
        request.cancelWriteEntity();
    }

    @Override
    public boolean setMtu(String address, int mtu, BleMtuCallback<T> callback) {
        MtuRequest<T> request = Rproxy.getRequest(MtuRequest.class);
        return request.setMtu(address, mtu, callback);
    }
}
