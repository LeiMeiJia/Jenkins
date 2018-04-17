package com.aerozhonghuan.java.thread;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 在 FutureTask 类中，线程执行完毕以及取消任务后，done()方法会被调用。
 * 1、可以在done()方法中调用get()获取到任务执行结果，且不会阻塞当前线程，done()方法执行在工作线程中
 * 2、调用 FutureTask 类 cancel() 方法后，会抛出 CancellationException 异常，可以在done()方法中捕获进行处理。此时done()方法执行在当前线程中
 * Created by Administrator on 2018/4/13.
 */

public class MyFutureTask extends FutureTask<String> {

    private static final String TAG = MyFutureTask.class.getSimpleName();

    public MyFutureTask(@NonNull Callable<String> callable) {
        super(callable);
    }

    @Override
    protected void done() {
        super.done();
        Log.d(TAG, "done()" + Thread.currentThread().getName());
        try {
            String result = get();
            Log.d(TAG, "任务结束于：" + Thread.currentThread().getName() + "  result=" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            Log.d(TAG, "任务被取消于：" + Thread.currentThread().getName());
        }
    }
}
