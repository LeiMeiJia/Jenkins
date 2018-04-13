package com.aerozhonghuan.java.thread;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MyFutureTask extends FutureTask<String> {

    public MyFutureTask(@NonNull Callable<String> callable) {
        super(callable);
    }
    @Override
    protected void done() {
        super.done();
        try {
            String result = get();
            System.out.println("任务结束于" + Thread.currentThread().getName());
            System.out.println("任务结束于" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "  result=" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            System.out.println("任务被取消于" + Thread.currentThread().getName());
            System.out.println("任务被取消于" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        }
    }
}
