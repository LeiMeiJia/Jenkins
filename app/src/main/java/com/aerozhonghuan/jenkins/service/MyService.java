package com.aerozhonghuan.jenkins.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.aerozhonghuan.mytools.utils.LogUtils;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        LogUtils.logd(TAG, LogUtils.getThreadName());
        // 使用 ResultReceiver 进行通信
        ResultReceiver resultReceiver = intent.getParcelableExtra("resultReceiver");
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putString("test", "MyService");
            resultReceiver.send(0, bundle);
        }
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
        return null;
    }

}
