package com.aerozhonghuan.jenkins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.aerozhonghuan.jenkins.mvp.entity.UserInfo;
import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * 局部通知管理器
 * <p>
 * 从部分源码可以看出两点：
 * <p>
 * 1、在获取LocalBroadcastManager对象实例的时候，这里用了单例模式。并且把外部传进来的Context 转化成了ApplicationContext，
 * 有效的避免了当前Context的内存泄漏的问题。
 * 2、在LocalBroadcastManager构造函数中创建了一个Handler.可见 LocalBroadcastManager 的本质上是通过Handler机制发送和接收消息的。
 * 3、在创建Handler的时候，用了context.getMainLooper() , 说明这个Handler是在Android 主线程中创建的，广播接收器的接收消息的时候会在Android 主线程，
 * 所以我们决不能在广播接收器里面做耗时操作，以免阻塞UI。
 * 4、LocalBroadcastManager注册广播只能通过代码注册的方式。传统的广播可以通过代码和xml两种方式注册。
 */
public class LocalBroadcastActivity extends AppCompatActivity {

    private LocalBroadcastManager localBroadcastManager;
    private MyLocalBroadcast myLocalBroadcast;
    private static final String TAG = LocalBroadcastActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra("test");
        LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + userInfo);

        // 创建本地广播
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        LogUtils.logd(TAG, "LocalBroadcastManager:" + localBroadcastManager);
        // 在子线程发送广播
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(LocalBroadcastActivity.this);
                Intent intent = new Intent("test");
                intent.putExtra("data", "子线程发过来的消息");
                localBroadcastManager.sendBroadcast(intent);
                LogUtils.logd(TAG, "LocalBroadcastManager:" + localBroadcastManager);
            }
        }).start();

        // 注册广播接收者
        myLocalBroadcast = new MyLocalBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("test");
        localBroadcastManager.registerReceiver(myLocalBroadcast, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(myLocalBroadcast);
    }

    private class MyLocalBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.logd(TAG, "LocalBroadcastManager:" + intent.getStringExtra("data"));
        }
    }
}
