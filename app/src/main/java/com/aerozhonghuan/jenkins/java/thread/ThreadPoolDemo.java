package com.aerozhonghuan.jenkins.java.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ThreadPoolDemo {

    public static void test() {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Task task = new Task();
        threadPoolExecutor.scheduleAtFixedRate(task, 3000, 3000, TimeUnit.SECONDS);
    }
}
