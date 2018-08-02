package com.aerozhonghuan.demo.mvp.model.http;

import android.os.AsyncTask;

import com.aerozhonghuan.demo.mvp.Configuration;
import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.aerozhonghuan.demo.net.http.task.MyAsyncGetTask;
import com.aerozhonghuan.demo.net.http.task.MyAsyncPostTask;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 网络层控制器：需考虑退出页面时取消网络请求
 * Created by liuk on 2018/4/4 0004.
 */

public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private static HttpRequest httpRequest;
    private HttpParameters httpParameters;
    private Executor executor;

    private HttpRequest() {
        httpParameters = HttpParameters.getInstance();
        executor = Executors.newCachedThreadPool();
    }

    public static HttpRequest getInstance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public AsyncTask loginGet(NetCallback callback) {
        // 创建参数
        String url = Configuration.fawLogin;
        Map<String, Object> hashMap = httpParameters.loginGet();
        // 创建接口请求
        MyAsyncGetTask task = new MyAsyncGetTask(url, hashMap, callback);
        // 发送请求
        task.executeOnExecutor(executor);
        return task;
    }

    public void loginPost(NetCallback callback) {
        // 创建参数
        String url = Configuration.hyLogin;
        Map<String, Object> hashMap = httpParameters.loginPost();
        // 创建接口请求
        MyAsyncPostTask task = new MyAsyncPostTask(url, hashMap, callback);
        // 发送请求
        task.executeOnExecutor(executor);
    }

    public void getCars(String token, NetCallback callback) {
        // 创建参数
        String url = Configuration.carPageList;
        Map<String, Object> hashMap = httpParameters.getCars(token);
        // 创建接口请求
        MyAsyncPostTask task = new MyAsyncPostTask(url, hashMap, callback);
        // 发送请求
        task.executeOnExecutor(executor);
    }

    public void updateInfo(String token, NetCallback callback) {
        // 创建参数
        String url = Configuration.updateUserInfo;
        Map<String, Object> hashMap = httpParameters.updateInfo(token);
        // 创建接口请求
        MyAsyncPostTask task = new MyAsyncPostTask(url, hashMap, callback);
        // 发送请求
        task.executeOnExecutor(executor);
    }

    // 取消请求
    public void cancel(AsyncTask task) {
        if (task != null) {
            task.cancel(true);
        }
    }

}
