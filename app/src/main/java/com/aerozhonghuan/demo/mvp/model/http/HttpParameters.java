package com.aerozhonghuan.demo.mvp.model.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置请求参数
 * Created by Administrator on 2018/2/1.
 */

public class HttpParameters {

    private static final String TAG = HttpParameters.class.getSimpleName();
    private static HttpParameters httpParameters;

    private HttpParameters() {
    }

    public static synchronized HttpParameters getInstance() {
        if (httpParameters == null) {
            httpParameters = new HttpParameters();
        }
        return httpParameters;
    }

    public Map<String, Object> loginGet() {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "linqi");
        params.put("password", "Qweasd1");
        params.put("deviceId", "1111");
        return params;
    }

    public Map<String, Object> loginPost() {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "zhbjfan112");
        params.put("password", "Aa123456");
        params.put("deviceId", "1111");
        params.put("deviceType", "1");
        return params;
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
