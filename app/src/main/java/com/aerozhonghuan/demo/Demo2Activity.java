package com.aerozhonghuan.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aerozhonghuan.demo.mvp.Configuration;
import com.aerozhonghuan.demo.net.callback.HttpCallback;
import com.aerozhonghuan.demo.net.task.MyAsyncPostTask;
import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Demo2Activity extends AppCompatActivity {

    private final static String TAG = "Demo2Activity";
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        executor = Executors.newCachedThreadPool();

        String url = Configuration.hyLogin;
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "wanghb");
        params.put("password", "Aa123456");
        params.put("deviceId", "1111");
        params.put("deviceType", "1");
        MyAsyncPostTask task = new MyAsyncPostTask(url, params, new HttpCallback() {
            @Override
            public void responseSuccess(String result) {
                LogUtils.logd(TAG, "result:" + result);
            }

            @Override
            public void responseFail(int resultCode, String msg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode);
                LogUtils.logd(TAG, "message:" + msg);
            }
        });
        task.executeOnExecutor(executor);
    }

}
