package com.huaweisoft.retrofitdemo;

/**
 * @author baiaj
 * 模式枚举
 */
public class ModelEnum {

    /**
     * Retrofit的网络请求形式
     */
    public static enum CallAdapter {
        /**
         * 传统模式
         */
        normal,
        /**
         * RxJava模式
         */
        rxjava
    }
}
