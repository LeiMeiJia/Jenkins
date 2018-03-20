package com.aerozhonghuan.jenkins.service;

import android.os.Looper;

import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * Created by liuk on 2018/3/15 0015.
 */

public class MyHandlerThread extends Thread {

    private static final String TAG = MyHandlerThread.class.getSimpleName();
    Looper mLooper;

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "HandlerThreadTest结束运行");
    }

    public Looper getLooper() {
        if (!isAlive()) {
            LogUtils.logd(TAG, LogUtils.getThreadName() + "isAlive false");
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
        LogUtils.logd(TAG, LogUtils.getThreadName() + "quit");
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }
}
