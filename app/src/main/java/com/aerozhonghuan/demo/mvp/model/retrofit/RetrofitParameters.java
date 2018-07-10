package com.aerozhonghuan.demo.mvp.model.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 设置请求参数
 * Created by Administrator on 2018/2/1.
 */

public class RetrofitParameters {

    private static final String TAG = RetrofitParameters.class.getSimpleName();
    private static final String contentType = "application/json;charset=UTF-8";
    private static RetrofitParameters retrofitParameters;

    private RetrofitParameters() {
    }

    public static synchronized RetrofitParameters getInstance() {
        if (retrofitParameters == null) {
            retrofitParameters = new RetrofitParameters();
        }
        return retrofitParameters;
    }

    public Map<String, Object> loginGet() {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "linqi");
        params.put("password", "Qweasd1");
        params.put("deviceId", "1111");
        return params;
    }

    public RequestBody loginPost() {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "zhbjfan112");
        params.put("password", "Aa123456");
        params.put("deviceId", "1111");
        params.put("deviceType", "1");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String data = gson.toJson(params);
        return RequestBody.create(MediaType.parse(contentType), data);
    }

    public Map<String, Object> getCars(String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("page_number", 1);
        params.put("page_size", 20);
        params.put("processCode", "00040005");
        return params;
    }

    public Map<String, Object> updateInfo(String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("rName", "测试");
        return params;
    }

}
