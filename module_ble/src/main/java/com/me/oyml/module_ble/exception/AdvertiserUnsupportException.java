package com.me.oyml.module_ble.exception;

public class AdvertiserUnsupportException extends BleException {


    private static final long serialVersionUID = 3871013343556227444L;

    public AdvertiserUnsupportException() {
    }

    public AdvertiserUnsupportException(String message) {
        super(message);
    }

    public AdvertiserUnsupportException(String s, Throwable ex) {
        super(s, ex);
    }
}
