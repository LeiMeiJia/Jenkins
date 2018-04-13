package com.aerozhonghuan.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aerozhonghuan.demo.mvp.Configuration;
import com.aerozhonghuan.demo.mvp.entity.UserInfo;
import com.aerozhonghuan.demo.mvp.model.ApiResponse;
import com.aerozhonghuan.demo.net.callback.HttpCallback;
import com.aerozhonghuan.demo.net.task.MyAsyncGetTask;
import com.aerozhonghuan.jenkins.R;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Demo1Activity extends AppCompatActivity {

    private final static String TAG = "Demo1Activity";
    private UserInfo mUserInfo;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        executor = Executors.newCachedThreadPool();

        login();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCars(mUserInfo.getToken());
                update(mUserInfo.getToken());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.logd(TAG, ">>>>>>>>>");
        // 退出页面时，取消请求回调
    }

    private void login() {
        String url = Configuration.fawLogin;
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "linqi");
        params.put("password", "Qweasd1");
        params.put("deviceId", "1111");
        MyAsyncGetTask task = new MyAsyncGetTask(url, params, new HttpCallback() {
            @Override
            public void responseSuccess(String result) {
                LogUtils.logd(TAG, "result:" + result);
                Type type = new TypeToken<ApiResponse<UserInfo>>() {
                }.getType();
                Gson gson = new Gson();
                ApiResponse<UserInfo> apiResponse = gson.fromJson(result, type);
                UserInfo userInfo = apiResponse.getData();
                LogUtils.logd(TAG, "userInfo:" + userInfo);
                mUserInfo = userInfo;
            }

            @Override
            public void responseFail(int resultCode, String msg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode);
                LogUtils.logd(TAG, "message:" + msg);
            }
        });
        task.executeOnExecutor(executor);
    }

    private void getCars(String token) {
        String url = Configuration.carPageList;
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("token", token);
        hashMap.put("page_number", 1);
        hashMap.put("page_size", 20);
        hashMap.put("processCode", "00040005");
        MyAsyncGetTask task = new MyAsyncGetTask(url, hashMap, new HttpCallback() {
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

    private void update(String token) {
        String url = Configuration.updateUserInfo;
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("rName", "经销商");
        MyAsyncGetTask task = new MyAsyncGetTask(url, params, new HttpCallback() {
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
