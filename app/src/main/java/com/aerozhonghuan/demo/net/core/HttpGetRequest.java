package com.aerozhonghuan.demo.net.core;

import android.util.Log;

import java.util.Map;

// GET请求网络层
public class HttpGetRequest {

    private final static String TAG = "HttpGetRequest";
    private static HttpGetRequest instance = null;
    private HttpEngine engine;

    private HttpGetRequest() {
        engine = new HttpEngine.Builder().builder();
    }

    public synchronized static HttpGetRequest getInstance() {
        if (instance == null) {
            instance = new HttpGetRequest();
        }
        return instance;
    }

    public String get(String urlStr, Map<String, Object> hashMap) {
        String result = engine.doGet(urlStr, hashMap);
        Log.d(TAG, "get : result =" + result);
        return result;
    }
}