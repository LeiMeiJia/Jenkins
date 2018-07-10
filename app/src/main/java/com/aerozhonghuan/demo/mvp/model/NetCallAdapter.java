package com.aerozhonghuan.demo.mvp.model;

import com.aerozhonghuan.demo.mvp.model.ApiResponse;
import com.aerozhonghuan.demo.mvp.model.ResponseCallback;
import com.aerozhonghuan.demo.net.callback.NetCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * 中间层，关联网络层与业务层回调方法
 * Created by liuk on 2018/7/9 0009.
 */

public class NetCallAdapter<T> implements NetCallback {

    private ResponseCallback responseCallback;
    private Type type;

    public NetCallAdapter(Type type, ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
        this.type = type;
    }

    // 网络层回调方法
    @Override
    public void netSuccess(String result) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        // 业务层回调方法
        ApiResponse<T> apiResponse = gson.fromJson(result, type);
        int resultCode = apiResponse.getResultCode();
        if (resultCode == 200) { // 业务层200
            responseCallback.onSuccess(apiResponse.getData());
        } else if (resultCode == 509) {
            responseCallback.onCookieExpired();
            responseCallback.onFail(resultCode, apiResponse.getMessage());
        } else {
            responseCallback.onFail(resultCode, apiResponse.getMessage());
        }
    }

    @Override
    public void netFail(int resultCode, String msg) {
        responseCallback.onFail(resultCode, msg);
    }
}
