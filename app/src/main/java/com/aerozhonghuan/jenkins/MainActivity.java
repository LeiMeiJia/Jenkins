package com.aerozhonghuan.jenkins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aerozhonghuan.jenkins.service.TestServiceActivity;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler childHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Test", "ip:" + BuildConfig.SERVER_URL);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestServiceActivity.class);
                startActivity(intent);
            }
        });

        // 创建HandlerThread对象
        HandlerThread myThread = new HandlerThread("HandlerThread");
        myThread.start();

        // 创建子线程的handler对象
        childHandler = new Handler(myThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
                if (1 == msg.what) {
                    SystemClock.sleep(2000);
                    LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
                } else if (2 == msg.what) {
                    SystemClock.sleep(3000);
                    LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
                }
            }
        };

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.logd(TAG, LogUtils.getThreadName());
                // 主线程向子线程发送消息
                Message message = Message.obtain();
                message.obj = "主线程向子线程发消息1";
                message.what = 1;
                childHandler.sendMessage(message);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.logd(TAG, LogUtils.getThreadName());
                // 主线程向子线程发送消息
                Message message = Message.obtain();
                message.obj = "主线程向子线程发消息2";
                message.what = 2;
                childHandler.sendMessage(message);
            }
        });

    }

    private class MyThread extends Thread {
        public Looper childLooper;

        public MyThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            childLooper = Looper.myLooper();
            Looper.loop();
        }
    }


}
