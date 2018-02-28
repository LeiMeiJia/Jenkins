package com.aerozhonghuan.jenkins.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 *  IntentService，可以看做是Service和HandlerThread的结合体，
 * 在完成了使命之后会自动停止，适合需要在工作线程处理UI无关任务的场景。
 * Created by Administrator on 2018/2/28.
 */

public class MyService extends IntentService {
    private static final String TAG = MyService.class.getSimpleName();

    public MyService() {
        super("MyService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ResultReceiver resultReceiver = intent.getParcelableExtra("resultReceiver");
        LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + resultReceiver);
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString("test", "onHandleIntent");
            resultReceiver.send(0, bundle);
        }
    }


}
