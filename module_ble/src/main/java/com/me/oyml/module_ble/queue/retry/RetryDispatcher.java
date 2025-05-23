package com.me.oyml.module_ble.queue.retry;

import com.me.oyml.module_ble.Ble;
import com.me.oyml.module_ble.BleLog;
import com.me.oyml.module_ble.BleStates;
import com.me.oyml.module_ble.callback.BleConnectCallback;
import com.me.oyml.module_ble.model.BleDevice;
import com.me.oyml.module_ble.request.ConnectRequest;
import com.me.oyml.module_ble.request.Rproxy;

import java.util.HashMap;
import java.util.Map;

public class RetryDispatcher<T extends BleDevice> extends BleConnectCallback<T> implements RetryCallback<T> {
    private static final String TAG = "RetryDispatcher";
    private static RetryDispatcher retryDispatcher;
    private final Map<String, Integer> deviceRetryMap = new HashMap<>();

    public static <T extends BleDevice> RetryDispatcher<T> getInstance() {
        if (retryDispatcher == null) {
            retryDispatcher = new RetryDispatcher();
        }
        return retryDispatcher;
    }

    @Override
    public void retry(T device) {
        BleLog.i("正在尝试重试连接第" + deviceRetryMap.get(device.getBleAddress()) + "次重连: " + device.getBleName());
        if (!device.isAutoConnect()) {
            ConnectRequest<T> connectRequest = Rproxy.getRequest(ConnectRequest.class);
            connectRequest.connect(device);
        }
    }

    @Override
    public void onConnectionChanged(BleDevice device) {
        BleLog.i("onConnectionChanged:" + device.getBleName() + "---连接状态:" + device.isConnected());
        if (device.isConnected()) {
            String key = device.getBleAddress();
            deviceRetryMap.remove(key);
        }
    }

    @Override
    public void onConnectFailed(T device, int errorCode) {
        super.onConnectFailed(device, errorCode);
        if (errorCode == BleStates.ConnectError || errorCode == BleStates.ConnectFailed) {
            String key = device.getBleAddress();
            int lastRetryCount = Ble.options().connectFailedRetryCount;
            if (lastRetryCount <= 0) return;
            if (deviceRetryMap.containsKey(key)) {
                lastRetryCount = deviceRetryMap.get(key);
            }
            if (lastRetryCount <= 0) {
                deviceRetryMap.remove(key);
                return;
            }
            deviceRetryMap.put(key, lastRetryCount - 1);
            retry(device);
        }
    }
}
