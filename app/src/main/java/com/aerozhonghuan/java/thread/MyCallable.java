package com.aerozhonghuan.java.thread;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * 线程任务，可以返回任务执行结果
 * Created by Administrator on 2018/4/13.
 */

public class MyCallable implements Callable<String> {

    private static final String TAG = MyCallable.class.getSimpleName();

    @Override
    public String call() throws Exception {
        Log.d(TAG, "运行在：" + Thread.currentThread().getName());
        Thread.sleep(5000);
        return "50";
    }
}
