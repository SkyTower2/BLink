package com.me.oyml.common.mmkv;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * MMKV工具类
 * 用来替代SP存储
 *
 * @author ml
 * @date 2024-11-07 18:10
 */
public class MMKVUtils {
    private static MMKV mmkv;

    /**
     * Application中初始化MMKV
     */
    public static void init(Context context) {
        String rootDir = MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
    }

    /**
     * 指定保存位置初始化
     */
    public static void init(String dir) {
        String rootDir = MMKV.initialize(dir);
        mmkv = MMKV.defaultMMKV();
    }

    public static void putBoolean(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public static Boolean getBoolean(String key) {
        return mmkv.decodeBool(key, false);
    }

    public static Boolean getBoolean(String key, boolean defValue) {
        return mmkv.decodeBool(key, defValue);
    }

    public static void putInteger(String key, int value) {
        mmkv.encode(key, value);
    }

    public static int getInteger(String key) {
        return mmkv.decodeInt(key, 1);
    }

    public static int getInteger(String key, int defValue) {
        return mmkv.decodeInt(key, defValue);
    }

    public static void putString(String key, String value) {
        mmkv.encode(key, value);
    }

    public static String getString(String key) {
        return mmkv.decodeString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    /**
     * 移除数据Key
     *
     * @param key 数据Key
     */
    public static void removeKey(String key) {
        mmkv.removeValueForKey(key);
    }
}
