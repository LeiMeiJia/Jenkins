package com.aerozhonghuan.jenkins.java.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 *
 * ThreadPoolExecutor(int corePoolSize,  // 核心池大小
 *                    int maximumPoolSize,  // 最大池大小
 *                    long keepAliveTime,   // 任务执行完后存活的时间
 *                    TimeUnit unit,        // 时间单位
 *                    BlockingQueue<Runnable> workQueue, // 工作队列
 *                    ThreadFactory threadFactory) // 创建线程
 * 线程池对象中存在两种数据结构：
 * 1、用于存储Thread的集合（包括核心池和最大池） 2、工作队列（存储即将要执行的任务 ）
 *
 * 只有执行任务时，才会开始创建线程
 * 1、线程池的创建过程，先创建核心池大小的线程数
 * 2、当需要创建的线程数超过核心池数时，进入工作队列创建线程
 * 3、当任务队列数量满后，继续创建线程，直到超过最大池数量
 * 4、任务执行完后线程销毁，只会保留核心池的线程，这个时候线程并没有销毁
 *    当核心池的线程数量设置为0时，
 *
 * Created by Administrator on 2018/3/20.
 */

public class MyThreadPool {

    public void method() {

        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(100); //100是最大上限

        // 创建一个线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {

            AtomicInteger integer = new AtomicInteger(0);
            @Override
            public Thread newThread(@NonNull Runnable r) {
                // 创建一个线程，把任务r赋值给该线程
                Thread thread = new Thread(r);
                thread.setName("MyThread:"+integer.getAndIncrement());
                return thread;
            }
        };
        // 同时最大并发数时110
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 2, TimeUnit.SECONDS, blockingQueue, threadFactory);

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


}