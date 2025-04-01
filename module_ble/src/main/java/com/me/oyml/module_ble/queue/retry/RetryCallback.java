package com.me.oyml.module_ble.queue.retry;

interface RetryCallback<T> {
    void retry(T device);
}
