package com.aerozhonghuan.java.thread;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池需考虑资源共享问题
 * 1、使用synchronized后，相当于串行执行
 * 2、使用AtomicInteger可以保持数据唯一，且并发执行
 * <p>
 * Created by Administrator on 2018/3/20.
 */

public class Task implements Runnable {
    private static final String TAG = Task.class.getSimpleName();
    private AtomicInteger atomicInteger = new AtomicInteger(100);

    @Override
    public void run() {
        Log.d(TAG, atomicInteger.get() + "：开始运行" + "，thread name is：" + Thread.currentThread().getName());
        SystemClock.sleep(50);
        Log.d(TAG, atomicInteger.decrementAndGet() + "：结束运行" + "，thread name is：" + Thread.currentThread().getName());
    }
}
