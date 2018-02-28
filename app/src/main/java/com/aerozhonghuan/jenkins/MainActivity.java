package com.aerozhonghuan.jenkins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aerozhonghuan.jenkins.service.MyService;
import com.aerozhonghuan.mytools.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Handler childHandler;
    private ResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Test", "ip:" + BuildConfig.SERVER_URL);


        HandlerThread  myThread = new HandlerThread("HandlerThread ");
        myThread.start();

        childHandler = new Handler(myThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + childHandler.getLooper().getThread().getName());
                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + msg.obj);
            }
        };

        // IntentService[MyService]-> onReceiveResult() onHandleIntent
        // main-> onReceiveResult() onHandleIntent

        resultReceiver = new ResultReceiver(childHandler) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                // IntentService[MyService]-> onReceiveResult() onHandleIntent
                // main-> onReceiveResult() onHandleIntent
                LogUtils.logd(TAG, LogUtils.getThreadName() + resultData.getString("test"));
            }
        };


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LocalBroadcastActivity.class);
//                startActivity(intent);
//                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + Integer.toHexString(hashCode()));

                Message message = Message.obtain();
                message.obj = "主线程向子线程发消息";
                childHandler.sendMessage(message);


                LogUtils.logd(TAG, LogUtils.getThreadName() + ":" + resultReceiver);
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("resultReceiver", resultReceiver);
                startService(intent);
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
