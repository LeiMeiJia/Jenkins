package com.aerozhonghuan.jenkins.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.jenkins.java.viewutils.OnClick;
import com.aerozhonghuan.jenkins.java.viewutils.ViewInject;
import com.aerozhonghuan.jenkins.java.viewutils.ViewInjectUtils;
import com.aerozhonghuan.mytools.utils.LogUtils;

/**
 * 测试HandlerThread机制
 * 1、HandlerThread本身就是一个线程，在handleMessage() 方法中进行耗时任务
 * 2、通过发送消息sendMessage开启异步操作
 * 3、HandlerThread在使用完毕后必须手动退出循环队列
 */

public class HanderThreadActivity extends AppCompatActivity {
    private static final String TAG = HanderThreadActivity.class.getSimpleName();
    private Handler testHandler;
    private MyHandlerThread thread;

    @ViewInject(R.id.btn1)
    private Button btn1;

    @ViewInject(R.id.btn2)
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ViewInjectUtils.init(this);

        // 使用自定义HandlerThread
        thread = new MyHandlerThread();
        thread.start();

        // 创建子线程的handler对象
        testHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 子线程使用Looper对象时，需考虑内存泄露问题
        Looper looper = testHandler.getLooper();
        looper.quit();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "thread:" + thread.isAlive());

        // 释放handler资源
        testHandler.removeCallbacksAndMessages(null);
        testHandler = null;
    }

    @OnClick(R.id.btn1)
    private void btn1(View view) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + btn1.getText());
        // 主线程向子线程发送消息
        Message message = Message.obtain();
        message.obj = "主线程向子线程发消息1";
        message.what = 1;
        testHandler.sendMessage(message);
    }

    @OnClick(R.id.btn2)
    private void btn2(View view) {
        LogUtils.logd(TAG, LogUtils.getThreadName() + btn2.getText());
        // 主线程向子线程发送消息
        Message message = Message.obtain();
        message.obj = "主线程向子线程发消息2";
        message.what = 2;
        testHandler.sendMessage(message);
    }

}
