package com.me.oyml.blink.bean;


import com.me.oyml.module_ble.model.BleDevice;
import com.me.oyml.module_ble.model.ScanRecord;

public class BleRssiDevice extends BleDevice {
    private ScanRecord scanRecord;
    private int rssi;
    private long rssiUpdateTime;

    public BleRssiDevice(String address, String name) {
        super(address, name);
    }

    public ScanRecord getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(ScanRecord scanRecord) {
        this.scanRecord = scanRecord;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public long getRssiUpdateTime() {
        return rssiUpdateTime;
    }

    public void setRssiUpdateTime(long rssiUpdateTime) {
        this.rssiUpdateTime = rssiUpdateTime;
    }
}
