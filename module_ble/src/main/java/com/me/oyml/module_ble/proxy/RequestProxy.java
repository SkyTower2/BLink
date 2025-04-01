package com.me.oyml.module_ble.proxy;

import android.content.Context;

import com.me.oyml.module_ble.BleLog;
import com.me.oyml.module_ble.request.ConnectRequest;
import com.me.oyml.module_ble.request.DescriptorRequest;
import com.me.oyml.module_ble.request.MtuRequest;
import com.me.oyml.module_ble.request.NotifyRequest;
import com.me.oyml.module_ble.request.ReadRequest;
import com.me.oyml.module_ble.request.ReadRssiRequest;
import com.me.oyml.module_ble.request.Rproxy;
import com.me.oyml.module_ble.request.ScanRequest;
import com.me.oyml.module_ble.request.WriteRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RequestProxy implements InvocationHandler {
    private static final String TAG = "RequestProxy";

    private RequestProxy() {
    }

    private Object receiver;

    public static RequestProxy newProxy() {
        return new RequestProxy();
    }

    //Bind the delegate object and return the proxy class
    public Object bindProxy(Context context, Object tar) {
        this.receiver = tar;
        //绑定委托对象，并返回代理类
        BleLog.d("bindProxy: " + "Binding agent successfully");
        Rproxy.init(ConnectRequest.class, MtuRequest.class,
                NotifyRequest.class, ReadRequest.class,
                ReadRssiRequest.class, ScanRequest.class,
                WriteRequest.class, DescriptorRequest.class);
        return Proxy.newProxyInstance(
                tar.getClass().getClassLoader(),
                tar.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(receiver, args);
    }
}
