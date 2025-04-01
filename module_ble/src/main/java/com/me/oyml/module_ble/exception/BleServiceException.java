package com.me.oyml.module_ble.exception;

public class BleServiceException extends BleException {


    private static final long serialVersionUID = 3871013343556227444L;

    public BleServiceException() {
    }

    public BleServiceException(String message) {
        super(message);
    }

    public BleServiceException(String s, Throwable ex) {
        super(s, ex);
    }
}
