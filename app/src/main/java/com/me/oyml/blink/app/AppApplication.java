package com.me.oyml.blink.app;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.me.oyml.blink.R;
import com.me.oyml.blink.bean.BleRssiDevice;
import com.me.oyml.blink.ui.activity.MainActivity;
import com.me.oyml.blink.callback.MyBleWrapperCallback;
import com.me.oyml.common.crash.CaocConfig;
import com.me.oyml.common.utils.KLog;
import com.me.oyml.common.base.BaseApplication;
import com.me.oyml.module_ble.Ble;
import com.me.oyml.module_ble.BleLog;
import com.me.oyml.module_ble.model.BleFactory;
import com.me.oyml.module_ble.utils.UuidUtils;

import java.util.UUID;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AppApplication extends BaseApplication {

    private static AppApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        //是否开启打印日志
        KLog.init(true);

        //初始化全局异常崩溃
        initCrash();

        //初始化蓝牙
        initBle();

        if (BuildConfig.DEBUG) {      // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
    }


    /**
     * 初始化蓝牙
     */
    private void initBle() {
        Ble.options()
                .setLogBleEnable(true)//设置是否输出打印蓝牙日志
                .setThrowBleException(true)//设置是否抛出蓝牙异常
                .setLogTAG("AndroidBLE")//设置全局蓝牙操作日志TAG
                .setAutoConnect(false)//设置是否自动连接
                .setIgnoreRepeat(false)//设置是否过滤扫描到的设备(已扫描到的不会再次扫描)
                .setConnectFailedRetryCount(3)//连接异常时（如蓝牙协议栈错误）,重新连接次数
                .setConnectTimeout(10 * 1000)//设置连接超时时长
                .setScanPeriod(12 * 1000)//设置扫描时长
                .setMaxConnectNum(7)//最大连接数量
                .setUuidService(UUID.fromString(UuidUtils.uuid16To128("fd00")))//设置主服务的uuid
                .setUuidWriteCha(UUID.fromString(UuidUtils.uuid16To128("fd01")))//设置可写特征的uuid
                .setUuidReadCha(UUID.fromString(UuidUtils.uuid16To128("fd02")))//设置可读特征的uuid （选填）
                .setUuidNotifyCha(UUID.fromString(UuidUtils.uuid16To128("fd03")))//设置可通知特征的uuid （选填，库中默认已匹配可通知特征的uuid）
                .setFactory(new BleFactory<BleRssiDevice>() {//实现自定义BleDevice时必须设置
                    @Override
                    public BleRssiDevice create(String address, String name) {
                        return new BleRssiDevice(address, name);//自定义BleDevice的子类
                    }
                })
                .setBleWrapperCallback(new MyBleWrapperCallback())
                .create(mApplication, new Ble.InitCallback() {
                    @Override
                    public void success() {
                        BleLog.d("蓝牙初始化成功");
                    }

                    @Override
                    public void failed(int failedCode) {
                        BleLog.e("蓝牙初始化失败：" + failedCode);
                    }
                });
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
