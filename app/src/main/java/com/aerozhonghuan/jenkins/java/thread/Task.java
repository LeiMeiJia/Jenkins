package com.aerozhonghuan.jenkins.java.thread;

import android.os.SystemClock;

/**
 * Created by Administrator on 2018/3/20.
 */

public class Task implements Runnable {

    @Override
    public void run() {
        System.out.println("======begin=======");
        SystemClock.sleep(5000);
        System.out.println("======end=======");
    }
}
