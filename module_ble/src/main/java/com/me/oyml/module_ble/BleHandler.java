package com.me.oyml.module_ble;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class BleHandler extends Handler {
    private static final String TAG = "BleHandler";
    private static BleHandler sHandler;//Handler for manipulating the Ble state

    public static BleHandler of() {
        synchronized (BleHandler.class) {
            if (sHandler == null) {
                HandlerThread handlerThread = new HandlerThread("handler thread");
                handlerThread.start();
                sHandler = new BleHandler(handlerThread.getLooper());
            }
            return sHandler;
        }
    }

    private BleHandler(Looper looper) {
        super(Looper.myLooper());
    }
}
