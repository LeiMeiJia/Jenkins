package com.aerozhonghuan.jenkins.mvp.model;

import com.aerozhonghuan.jenkins.network.api.HttpApi;
import com.aerozhonghuan.jenkins.network.engine.HttpRequest;
import com.aerozhonghuan.jenkins.network.engine.HttpResponse;
import com.aerozhonghuan.mytools.utils.LogUtils;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 网络层控制器：需考虑退出页面时取消网络请求
 * Created by liuk on 2018/4/4 0004.
 */

public class HttpEngine {

    private static final String TAG = HttpEngine.class.getSimpleName();
    private static HttpEngine httpEngine;
    private HttpParameters httpParameters;
    private HttpRequest httpRequest;
    private HttpApi httpApi;
    private HashMap<String, Call<ResponseBody>> calls = new HashMap<>();

    private HttpEngine() {
        httpParameters = HttpParameters.getInstance();
        httpRequest = HttpRequest.getInstance();
        httpApi = httpRequest.getHttpApi();
    }

    public static HttpEngine getInstance() {
        if (httpEngine == null) {
            httpEngine = new HttpEngine();
        }
        return httpEngine;
    }

    public void loginGet(String username, String password, String deviceId, HttpResponse httpResponse) {
        // 创建参数
        HashMap<String, String> hashMap = httpParameters.loginGet(username, password, deviceId);
        // 创建接口请求
        Call<ResponseBody> call = httpApi.loginGet(hashMap);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "loginGet call:" + call);
        // 发送请求
        httpRequest.sendHttpRequest(call, httpResponse);
        calls.put("loginGet", call);
    }

    public void loginPost(String username, String password, String deviceId, HttpResponse httpResponse) {
        // 创建参数
        RequestBody requestBody = httpParameters.loginPost(username, password, deviceId);
        // 创建接口请求
        Call<ResponseBody> call = httpApi.loginPost(requestBody);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "loginPost call:" + call);
        // 发送请求
        httpRequest.sendHttpRequest(call, httpResponse);
        calls.put("loginPost", call);
    }

    public void carPageList(String token, int page_size, int page_number, String processCode, HttpResponse httpResponse) {
        // 创建参数
        HashMap<String, Object> hashMap = httpParameters.carPageList(token, page_size, page_number, processCode);
        // 创建接口请求
        Call<ResponseBody> call = httpApi.carPageList(hashMap);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "carPageList call:" + call);
        // 发送请求
        httpRequest.sendHttpRequest(call, httpResponse);
        calls.put("carPageList", call);
    }

    public void cancelRequest(String tag) {
        Call<ResponseBody> call = calls.get(tag);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "call:" + call);
        if (call != null && !call.isCanceled()) {
            LogUtils.logd(TAG, LogUtils.getThreadName() + "cancel:" + call.isCanceled());
            call.cancel();
            LogUtils.logd(TAG, LogUtils.getThreadName() + "取消请求：" + call.isCanceled());
            calls.remove(tag);
        }
    }

}
