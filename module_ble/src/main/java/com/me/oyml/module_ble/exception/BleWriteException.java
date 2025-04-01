package com.me.oyml.module_ble.exception;

public class BleWriteException extends BleException {

    private static final long serialVersionUID = -6886122979840622897L;

    public BleWriteException() {
    }

    public BleWriteException(String message) {
        super(message);
    }

    public BleWriteException(String s, Throwable ex) {
        super(s, ex);
    }
}
