package com.aerozhonghuan.java.thread;

import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池需考虑资源共享问题
 * 1、使用synchronized后，相当于串行执行，降低了效率
 * 2、使用AtomicInteger可以保持数据唯一，且并发执行
 * 3、使用信号量 Semaphore，当锁被完全占用时，后面线程会被阻塞，进入排队等待
 * <p>
 * ThreadLocal
 * Created by Administrator on 2018/3/20.
 */

public class MyTask implements Runnable {
    private static final String TAG = MyTask.class.getSimpleName();
    private AtomicInteger atomicInteger = new AtomicInteger(100);
    private Semaphore semaphore = new Semaphore(5); // 信号量
    private int i = 100;

    @Override
    public void run() {
        method1();
    }

    private void method1() {
        Log.d(TAG, i + "：开始运行" + "，thread name is：" + Thread.currentThread().getName());
        SystemClock.sleep(5000);
        i--;
        Log.d(TAG, i + "：结束运行" + "，thread name is：" + Thread.currentThread().getName());
    }

    private void method2() {
        Log.d(TAG, atomicInteger.get() + "：开始运行" + "，thread name is：" + Thread.currentThread().getName());
        SystemClock.sleep(50);
        Log.d(TAG, atomicInteger.decrementAndGet() + "：结束运行" + "，thread name is：" + Thread.currentThread().getName());
    }

    private void method3() {
        try {
            semaphore.acquire(); // 获取一把锁
            Log.d(TAG, i + "：开始运行" + "，thread name is：" + Thread.currentThread().getName());
            SystemClock.sleep(100);
            i--;
            Log.d(TAG, i + "：结束运行" + "，thread name is：" + Thread.currentThread().getName());
            semaphore.release(); // 释放一把锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
