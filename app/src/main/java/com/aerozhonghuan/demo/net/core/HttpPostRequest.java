package com.aerozhonghuan.demo.net.core;

import android.util.Log;

import java.util.Map;

// POST请求网络层
public class HttpPostRequest {

    private final static String TAG = "HttpPostRequest";
    private static HttpPostRequest instance = null;
    private HttpEngine engine;

    private HttpPostRequest() {
        engine = new HttpEngine.Builder()
                .setRequestMethod("POST")
                .setContentType("application/json")
                .builder();
    }

    public synchronized static HttpPostRequest getInstance() {
        if (instance == null) {
            instance = new HttpPostRequest();
        }
        return instance;
    }

    public String post(String urlStr, Map<String, Object> hashMap) {
        String result = engine.doPost(urlStr, hashMap);
        Log.d(TAG, "post : result =" + result);
        return result;
    }

}