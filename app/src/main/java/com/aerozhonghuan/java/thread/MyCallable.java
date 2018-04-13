package com.aerozhonghuan.java.thread;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("运行在" + Thread.currentThread().getName());
        Thread.sleep(5000);
        return "50";
    }
}
