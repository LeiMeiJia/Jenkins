package com.aerozhonghuan.jenkins.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 *  IntentService，可以看做是Service和HandlerThread的结合体，底层通过Handler处理异步任务
 * 在完成了使命之后会自动停止，适合需要在工作线程处理UI无关任务的场景。
 * Created by Administrator on 2018/2/28.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.logd(TAG, LogUtils.getThreadName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.logd(TAG, LogUtils.getThreadName());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    // 运行在子线程中，底层其实是HandlerThread
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogUtils.logd(TAG, LogUtils.getThreadName());
        int resultCode = intent.getIntExtra("resultCode", -1);
        if (1 == resultCode) {
            SystemClock.sleep(5000);
        } else if (0 == resultCode) {
            SystemClock.sleep(2000);
        }
        LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode);

        // 使用 ResultReceiver 进行通信
        ResultReceiver resultReceiver = intent.getParcelableExtra("resultReceiver");
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString("test", "onHandleIntent");
            resultReceiver.send(0, bundle);
        }
    }

}
