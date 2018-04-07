package com.aerozhonghuan.jenkins.mvp.model;

import com.aerozhonghuan.jenkins.network.ApiResponse;
import com.aerozhonghuan.jenkins.network.engine.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 处理网络层回调
 * 1、将网络层数据封装为对象
 * 2、处理业务层回调方法
 * Created by liuk on 2018/4/4 0004.
 */

public abstract class HttpCallback<T> implements HttpResponse {

    private Type type;

    public HttpCallback(Type type) {
        this.type = type;
    }

    // ======统一处理网络层=======
    @Override
    public void responseSuccess(String result) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ApiResponse<T> apiResponse = gson.fromJson(result, type);
        // 处理业务层，需注意内存对象的处理
        int resultCode = apiResponse.getResultCode();
        if (resultCode == 200) { // 业务层200
            onSuccess(apiResponse.getData());
        } else if (resultCode == 509) {
            onFail(resultCode, apiResponse.getMessage());
            onCookieExpired();
        } else {
            onFail(resultCode, apiResponse.getMessage());
        }
    }

    @Override
    public void responseFail(int resultCode, String errorMsg) {
        onFail(resultCode, errorMsg);
    }

    // ======统一处理业务层=======
    abstract public void onSuccess(T t);

    abstract public void onFail(int resultCode, String errorMsg);

    private void onCookieExpired() {

    }
}
