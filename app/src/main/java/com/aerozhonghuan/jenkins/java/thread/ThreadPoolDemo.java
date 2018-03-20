package com.aerozhonghuan.jenkins.java.thread;

import android.os.SystemClock;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 解决线程并发问题
 * Created by Administrator on 2018/3/20.
 */

public class ThreadPoolDemo {


    public static void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
//            method1();
//            method2();
            method3();
        }
    }

    /**
     * 1、使用同步锁
     */
    private static synchronized void method1() {
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "进来了");
        SystemClock.sleep(1000);
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "出去了");
    }

    /**
     * 2、使用信号量进行处理
     * 当锁被完全占用时，后面线程会被阻塞，进入排队等待
     */
    private static Semaphore semaphore = new Semaphore(5); // 信号量

    private static void method2() throws InterruptedException {
        semaphore.acquire(); // 获取一把锁
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "进来了");
        SystemClock.sleep(1000);
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "出去了");
        semaphore.release(); // 释放一把锁
    }

    /**
     * 3、使用线程池
     * <p>
     * Executor executor1 = Executors.newCachedThreadPool();// 缓存线程池
     * Executor executor2 = Executors.newFixedThreadPool(5); //  固定线程个数的线程池
     * Executor executor3 = Executors.newSingleThreadExecutor(); // 单个线程的线程池
     * Executor executor4 = Executors.newScheduledThreadPool(5); // 计划任务的线程池
     */
    private static Executor executor = Executors.newFixedThreadPool(5);

    private static void method3() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("ThreadName=" + Thread.currentThread().getName() + "进来了");
                SystemClock.sleep(1000);
                System.out.println("ThreadName=" + Thread.currentThread().getName() + "出去了");
            }
        });
    }
}
