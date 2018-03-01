package com.aerozhonghuan.jenkins.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.aerozhonghuan.mytools.utils.ToastUtils;

/**
 * 使用ResultReceiver在Activity与Service之间进行通信
 */

public class TestServiceActivity extends AppCompatActivity {
    private static final String TAG = TestServiceActivity.class.getSimpleName();
    private Handler childHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + Integer.toHexString(hashCode()));


        HandlerThread myThread = new HandlerThread("HandlerThread");
        myThread.start();

        LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + (myThread.getLooper() == Looper.getMainLooper()));

        childHandler = new Handler(myThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + childHandler.getLooper().getThread().getName());
                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
            }
        };
        //
        testService();

        // 测试IntentService进行异步操作
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestServiceActivity.this, MyIntentService.class);
                intent.putExtra("resultCode", 0);
                startService(intent);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestServiceActivity.this, MyIntentService.class);
                intent.putExtra("resultCode", 1);
                startService(intent);
            }
        });
    }


    /**
     * 创建ResultReceiver对象时，只需要传入一个Handler。
     * 当handler对象为空时，则直接在调用send方法的线程中执行onReceiveResult回调
     * 当handler对象不为空，就是控制回调函数执行在创建Handler的线程。
     */
    private void testService() {
        ResultReceiver resultReceiver = new ResultReceiver(childHandler) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
//                 IntentService[MyIntentService]-> onReceiveResult()
//                 main-> onReceiveResult()
                // Toast的创建需要依赖Handler，存在handler的话，子线程也可以弹出toast
                ToastUtils.getToast(getApplication(), resultData.getString("test"));
                LogUtils.logd(TAG, LogUtils.getThreadName());
            }
        };

//        Intent intent = new Intent(this, MyService.class);
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("resultReceiver", resultReceiver);
        startService(intent);
    }

    private void testHandler() {

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Message message = Message.obtain();
//                message.obj = "主线程向子线程发消息";
//                childHandler.sendMessage(message);
            }
        });
    }


    private class MyThread extends Thread {
        public Looper childLooper;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            childLooper = Looper.myLooper();
            Looper.loop();
        }
    }

}
