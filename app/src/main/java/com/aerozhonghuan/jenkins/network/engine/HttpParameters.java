package com.aerozhonghuan.jenkins.network.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 设置请求参数
 * Created by Administrator on 2018/2/1.
 */

public class HttpParameters {

    private static final String TAG = HttpParameters.class.getSimpleName();
    private static final String contentType = "application/json;charset=UTF-8";
    private static HttpParameters httpParameters;

    private HttpParameters() {
    }

    public static synchronized HttpParameters getInstance() {
        if (httpParameters == null) {
            httpParameters = new HttpParameters();
        }
        return httpParameters;
    }

    public HashMap<String, String> loginGet(String username, String password, String deviceId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", username);
        hashMap.put("password", password);
        hashMap.put("deviceId", deviceId);
        return hashMap;
    }

    public HashMap<String, Object> carPageList(String token, int page_number, int page_size, String processCode) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token", token);
        hashMap.put("page_number", page_number);
        hashMap.put("page_size", page_size);
        hashMap.put("processCode", processCode);
        return hashMap;
    }

    public RequestBody loginPost(String username, String password, String deviceId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", username);
        hashMap.put("password", password);
        hashMap.put("deviceId", deviceId);
        hashMap.put("deviceType", "1");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String data = gson.toJson(hashMap);
        return RequestBody.create(MediaType.parse(contentType), data);
    }

}
