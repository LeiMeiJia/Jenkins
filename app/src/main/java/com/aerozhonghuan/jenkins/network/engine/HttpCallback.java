package com.aerozhonghuan.jenkins.network.engine;


import com.aerozhonghuan.jenkins.network.ApiResponse;

/**
 * 网络层回调方法
 * Created by Administrator on 2018/2/1.
 */

public interface HttpCallback<T> {
    void requestSuccess(ApiResponse<T> apiResponse);

    void requestFail(int resultCode, String errorMsg);
}
