package com.me.oyml.common.utils;

import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * 线程池管理类
 */
public class ThreadPoolUtil {

    /**
     * 定义线程池类型及参数
     */
    public enum PoolType {
        IO("IO", 4, 7),
        CALCULATOR("calculator", 1, Thread.MAX_PRIORITY),
        FILE("file", 1, 3),
        DATABASE("database", 12, 3),
        SOCKET("socket", 2, 3),
        INSTRUCTION("instruction", 8, 3),
        OTHER("other", 3, 3),
        SUBSCRIBE("subscribe", 6, 3);

        public final String name;
        public final int size;
        public final int priority;

        PoolType(String name, int size, int priority) {
            this.name = name;
            this.size = size;
            this.priority = priority;
        }
    }

    // 缓存所有线程池
    private static final Map<PoolType, EasyThread> poolMap = new ConcurrentHashMap<>();

    /**
     * 获取 EasyThread 实例（懒加载 + 线程安全）
     */
    public static EasyThread get(PoolType type) {
        return poolMap.computeIfAbsent(type, t ->
                EasyThread.Builder.createFixed(t.size)
                        .setName(t.name)
                        .setPriority(t.priority)
                        .setCallback(new DefaultCallback())
                        .build()
        );
    }

    /**
     * 获取线程池的 Executor 接口
     */
    public static Executor getExecutor(PoolType type) {
        return get(type);
    }

    /**
     * 默认线程池任务回调，可统一日志
     */
    private static class DefaultCallback implements Callback {
        @Override
        public void onError(String threadName, Throwable t) {
            KLog.e("线程 [" + threadName + "] 出错：" + t.getMessage(), t);
        }

        @Override
        public void onCompleted(String threadName) {
            KLog.i("线程 [" + threadName + "] 执行完成");
        }

        @Override
        public void onStart(String threadName) {
            KLog.d("线程 [" + threadName + "] 开始执行");
        }
    }

    //使用方式
    // 获取数据库线程池执行任务
//ThreadPoolUtil.getExecutor(ThreadPoolUtil.PoolType.DATABASE).execute(() -> {
//        // 处理数据库操作
//    });
}