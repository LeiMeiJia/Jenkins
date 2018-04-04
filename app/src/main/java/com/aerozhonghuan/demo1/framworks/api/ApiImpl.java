package com.aerozhonghuan.demo1.framworks.api;


import com.aerozhonghuan.demo1.framworks.model.UserInfo;
import com.aerozhonghuan.demo1.framworks.Configuration;
import com.aerozhonghuan.demo1.framworks.net.ApiResponse;
import com.aerozhonghuan.demo1.framworks.net.HttpEngine;
import com.aerozhonghuan.mytools.utils.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ApiImpl implements Api {

    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";
    private final static String TAG = "ApiImpl";
    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ApiResponse<UserInfo> login(String userName, String password, String deviceId) {
        String url = Configuration.login;
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        params.put("deviceId", deviceId);
        url = url + httpEngine.joinParams(params);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "url:" + url);
        Type type = new TypeToken<ApiResponse<UserInfo>>() {
        }.getType();
        try {
            return httpEngine.getHandle(url, type);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtils.loge(TAG, LogUtils.getThreadName(), e);
            return new ApiResponse<>(-1, TIME_OUT_EVENT_MSG);
        }
    }
    @Override
    public ApiResponse updateUserInfo(String token, String rName) {
        String url = Configuration.updateUserInfo;
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("rName", rName);
        url = url + httpEngine.joinParams(params);
        LogUtils.logd(TAG, LogUtils.getThreadName() + "url:" + url);
        Type type = new TypeToken<ApiResponse>() {
        }.getType();
        try {
            return httpEngine.getHandle(url, type);
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtils.loge(TAG, LogUtils.getThreadName(), e);
            return new ApiResponse(-1, TIME_OUT_EVENT_MSG);
        }
    }

}