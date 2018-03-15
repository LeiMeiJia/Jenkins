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
import android.widget.Button;

import com.aerozhonghuan.jenkins.service.TestServiceActivity;
import com.aerozhonghuan.jenkins.thread.HandlerThreadTest;
import com.aerozhonghuan.jenkins.viewutils.OnClick;
import com.aerozhonghuan.jenkins.viewutils.ViewInject;
import com.aerozhonghuan.jenkins.viewutils.ViewUtilsDemo;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler childHandler;

    @ViewInject(R.id.btn1)
    private Button btn1;

    @ViewInject(R.id.btn2)
    private Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Test", "ip:" + BuildConfig.SERVER_URL);
        ViewUtilsDemo.init(this);
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
        test();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 子线程使用Looper对象时，需考虑内存泄露问题

        // Looper退出后，Looper对象是否还在，是否变为null；
        // quitSafely和quit的区别
        Looper looper = testHandler.getLooper();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "=====1111");
        looper.quit();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "=====2222");
        Looper looper1 = thread.getLooper();
        LogUtils.logd(TAG, LogUtils.getThreadName() + "=====3333");
        LogUtils.logd(TAG, LogUtils.getThreadName() + "thread:" + thread.quit());
        LogUtils.logd(TAG, LogUtils.getThreadName() + "looper:" + (looper==null));
        LogUtils.logd(TAG, LogUtils.getThreadName() + "looper1:" + (thread.getLooper()==null));
        //
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
        childHandler.sendMessage(message);
    }


    private Handler testHandler;
    private HandlerThreadTest thread;

    private void test() {
         thread = new HandlerThreadTest();
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

}
