package com.aerozhonghuan.demo.mvp.model.retrofit;

import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.aerozhonghuan.demo.net.retrofit.HttpEngine;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 网络层控制器：需考虑退出页面时取消网络请求
 * Created by liuk on 2018/4/4 0004.
 */

public class RetrofitRequest {

    private static final String TAG = RetrofitRequest.class.getSimpleName();
    private static RetrofitRequest retrofitRequest;
    private RetrofitParameters retrofitParameters;
    private HttpEngine httpEngine;
    private RetrofitApi retrofitApi;

    private RetrofitRequest() {
        retrofitParameters = RetrofitParameters.getInstance();
        httpEngine = HttpEngine.getInstance();
        retrofitApi = httpEngine.getRetrofitApi();
    }

    public static RetrofitRequest getInstance() {
        if (retrofitRequest == null) {
            retrofitRequest = new RetrofitRequest();
        }
        return retrofitRequest;
    }

    public Call<ResponseBody> loginGet(NetCallback callback) {
        // 创建参数
        Map<String, Object> hashMap = retrofitParameters.loginGet();
        // 创建接口请求
        Call<ResponseBody> call = retrofitApi.loginGet(hashMap);
        // 发送请求
        httpEngine.sendHttpRequest(call, callback);
        return call;
    }

    public void loginPost(NetCallback callback) {
        // 创建参数
        RequestBody requestBody = retrofitParameters.loginPost();
        // 创建接口请求
        Call<ResponseBody> call = retrofitApi.loginPost(requestBody);
        // 发送请求
        httpEngine.sendHttpRequest(call, callback);
    }

    public void getCars(String token, NetCallback callback) {
        // 创建参数
        Map<String, Object> hashMap = retrofitParameters.getCars(token);
        // 创建接口请求
        Call<ResponseBody> call = retrofitApi.getCars(hashMap);
        // 发送请求
        httpEngine.sendHttpRequest(call, callback);
    }

    public void updateInfo(String token, NetCallback callback) {
        // 创建参数
        Map<String, Object> hashMap = retrofitParameters.updateInfo(token);
        // 创建接口请求
        Call<ResponseBody> call = retrofitApi.updateInfo(hashMap);
        // 发送请求
        httpEngine.sendHttpRequest(call, callback);
    }

    // 取消请求
    public void cancel(Call<ResponseBody> call) {
        httpEngine.cancelHttpRequest(call);
    }

}
