package com.aerozhonghuan.jenkins.thread;

import android.os.Looper;

import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * Created by liuk on 2018/3/15 0015.
 */

public class HandlerThreadTest extends Thread {

    private static final String TAG = HandlerThreadTest.class.getSimpleName();
    Looper mLooper;

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
        LogUtils.logd("MainActivity", LogUtils.getThreadName() + "HandlerThreadTest结束运行1");
    }

    public Looper getLooper() {
        if (!isAlive()) {
            LogUtils.logd("MainActivity", LogUtils.getThreadName() + "isAlive false");
            return null;
        }

        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }

    public boolean quit() {
        LogUtils.logd("MainActivity", LogUtils.getThreadName() + "quit");
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }
}
